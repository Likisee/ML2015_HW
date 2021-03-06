package hw7;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import util.CommonUtil;
import util.FileUtil;

public class q13 {
	
	private static int yIndex = 2;
	
	private static ArrayList<Double> getThetaCandidate(ArrayList<ArrayList<Double>> nodes, int index) {
		ArrayList<Double> result = new ArrayList<Double>();
		ArrayList<Double> sort = new ArrayList<Double>();
		for(int i = 0; i < nodes.size(); i++) {
			sort.add(nodes.get(i).get(index));
		}
		Collections.sort(sort);
		for(int i = 0; i < sort.size() - 1; i++) {
			result.add((sort.get(i) + sort.get(i+1)) / 2);
		}
		return result;
	}

	private static void showTree(NodeHW7 root) {
		root.info(yIndex);
		if(root.left != null || root.right != null ) {
			showTree(root.right);
			showTree(root.left);
		}
	}
	
	private static double predict(NodeHW7 root, ArrayList<Double> node) {
		NodeHW7 currentRoot = root;
		while(currentRoot.left != null) {
			if(node.get(currentRoot.index) < currentRoot.theta) {
				currentRoot = currentRoot.left;
			} else {
				currentRoot = currentRoot.right;
			}
		}
		return currentRoot.nodes.get(0).get(yIndex);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// read the training & testing data
		List<String> lines;
		lines = FileUtil.readFileContent(CommonUtil.getHwFile("hw7", "hw7_train.dat"), null);
		ArrayList<ArrayList<Double>> nodesTrain = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < lines.size(); i++) {
			String [] line = lines.get(i).split(" ");
			ArrayList<Double> node = new ArrayList<Double>();
			node.add(Double.parseDouble(line[0]));
			node.add(Double.parseDouble(line[1]));
			node.add(Double.parseDouble(line[2]));
			nodesTrain.add(node);
		}
		lines = FileUtil.readFileContent(CommonUtil.getHwFile("hw7", "hw7_test.dat"), null);
		ArrayList<ArrayList<Double>> nodesTest = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < lines.size(); i++) {
			String [] line = lines.get(i).split(" ");
			ArrayList<Double> node = new ArrayList<Double>();
			node.add(Double.parseDouble(line[0]));
			node.add(Double.parseDouble(line[1]));
			node.add(Double.parseDouble(line[2]));
			nodesTest.add(node);
		}
		
		ArrayList<NodeHW7> waiting = new ArrayList<NodeHW7>();
		NodeHW7 root = new NodeHW7();
		root.nodes = nodesTrain;
		if(root.getGini(yIndex) > 0) { // 還可以分下去
			waiting.add(root);			
		}
		while(waiting.size() > 0) {
			// find minGini / bestTheta
			NodeHW7 subRoot = waiting.remove(waiting.size() - 1);
			NodeHW7 left = null;
			NodeHW7 right = null;
			double minGini = Double.MAX_VALUE;
			int bestIndex = 0;
			double bestTheta = 0;
			for(int index = 0; index < 2; index++) { // 0,1
				ArrayList<Double> thetaCandidate = getThetaCandidate(subRoot.nodes, index);
				for(int i = 0; i < thetaCandidate.size(); i++) {
					double theta = thetaCandidate.get(i);
					left = new NodeHW7();
					right = new NodeHW7();
					for(int j = 0; j < subRoot.nodes.size(); j++) {
						if(subRoot.nodes.get(j).get(index) < theta) {
							left.nodes.add(subRoot.nodes.get(j));
						} else {
							right.nodes.add(subRoot.nodes.get(j));
						}
						
					}
					double newGini = left.nodes.size() * left.getGini(yIndex) + right.nodes.size() * right.getGini(yIndex);
					if(newGini < minGini) {
						minGini = newGini;
						bestIndex = index;
						bestTheta = theta;
					}
				}
			}
			
			// generate two children
			left = new NodeHW7();
			right = new NodeHW7();
			for(int j = 0; j < subRoot.nodes.size(); j++) {
				if(subRoot.nodes.get(j).get(bestIndex) < bestTheta) {
					left.nodes.add(subRoot.nodes.get(j));
				} else {
					right.nodes.add(subRoot.nodes.get(j));
				}
				
			}
			subRoot.index = bestIndex;
			subRoot.theta = bestTheta;
			subRoot.left = left;
			subRoot.right = right;
			subRoot.info(yIndex); // Q13: 輸出  branch function nodes
			if(left.getGini(yIndex) > 0) { // 還可以分下去
				waiting.add(left);	
			}
			if(right.getGini(yIndex) > 0) { // 還可以分下去
				waiting.add(right);	
			}
		}
		
//		showTree(root); // Q13: 輸出  branch function nodes (含LEAF值,RIGHT-FIRST-DFS,有點醜,大概只有我自己看得懂,但是畢竟本題不是要考資料結構,所已將就將就!!)

//		System.out.println(predict(root, nodesTrain.get(0))); // Test一下, [0.757222 0.633831 -1] -> -1
		
		// Q14: eIn
		double eInCnt = 0;
		for(int i = 0; i < nodesTrain.size(); i++) {
			if(predict(root, nodesTrain.get(i)) != nodesTrain.get(i).get(yIndex)) {
				eInCnt ++;
			}
		}
		System.out.println(eInCnt);
		System.out.println(nodesTrain.size());
		System.out.println(eInCnt / nodesTrain.size());
		System.out.println();
		
		// Q15: eOut
		double eOutCnt = 0;
		for(int i = 0; i < nodesTest.size(); i++) {
			if(predict(root, nodesTest.get(i)) != nodesTest.get(i).get(yIndex)) {
				eOutCnt ++;
			}
		}
		System.out.println(eOutCnt);
		System.out.println(nodesTest.size());
		System.out.println(eOutCnt / nodesTest.size());
		System.out.println();
	}

}
