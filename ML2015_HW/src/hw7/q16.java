package hw7;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import util.CommonUtil;
import util.FileUtil;

public class q16 {

	private static Random rand = new Random();
	
	private static int yIndex = 2;
	private static int ROUND = 30000;
	
	private static ArrayList<Integer> getNewNodesTrain(int max, int count) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		while(result.size() < count) {
			int index = rand.nextInt(max);
//			if(!result.contains(index)) { // comment 拿掉就變成不可以重複取
				result.add(index);
//			}
		}
		Collections.sort(result);
		return result;
	}
	
	private static ArrayList<ArrayList<Double>> getNewNodesTrain(ArrayList<ArrayList<Double>> nodesTrain, int count) {
		ArrayList<ArrayList<Double>> result = new ArrayList<ArrayList<Double>>();
		ArrayList<Integer> newNodesTrain = getNewNodesTrain(nodesTrain.size(), count);
		for(int i = 0; i < newNodesTrain.size(); i++) {
			result.add(nodesTrain.get(newNodesTrain.get(i)));
		}
		return result;
	}
	
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
		
		double eInSum = 0;
		int [] eInBinCount = new int[20]; // 每個bin是0.05
		
		for(int round = 0; round < ROUND; round++) {
			
			ArrayList<NodeHW7> waiting = new ArrayList<NodeHW7>();
			NodeHW7 root = new NodeHW7();
//			root.nodes = getNewNodesTrain(nodesTrain, nodesTrain.size() / 2); // N' = N / 2 (不可重複取)
//			root.nodes = getNewNodesTrain(nodesTrain, (int) (0.8 * nodesTrain.size())); // N' = 0.8 * N (不可重複取)
//			root.nodes = getNewNodesTrain(nodesTrain, rand.nextInt(nodesTrain.size()) + 1); // N' = rand() * N (不可重複取)
			root.nodes = getNewNodesTrain(nodesTrain, nodesTrain.size()); // N' = N (可重複取)
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
//				subRoot.info(yIndex); // 輸出  branch function nodes
				if(left.getGini(yIndex) > 0) { // 還可以分下去
					waiting.add(left);	
				}
				if(right.getGini(yIndex) > 0) { // 還可以分下去
					waiting.add(right);	
				}
			}
			
			// eIn -> eInBinCount
			double eInCnt = 0;
			for(int i = 0; i < nodesTrain.size(); i++) {
				if(predict(root, nodesTrain.get(i)) != nodesTrain.get(i).get(yIndex)) {
					eInCnt ++;
				}
			}
			double eIn = eInCnt / nodesTrain.size();
			eInSum += eIn;
			int index = (int) (eIn * 20 == 20 ? 19 : eIn * 20);
			eInBinCount[index]++;
//			System.out.println("Round " + round + ": " + eIn);
		}
		
		// Q16: eInAvg (Coursera)
		System.out.println("eInAvg: " + eInSum / ROUND);

		// Q16: Histogram (NTU)
		DecimalFormat df = new DecimalFormat("0.00");
		for(int i = 0; i < eInBinCount.length; i++) {
			System.out.println(df.format(i * 0.05) + ": " + eInBinCount[i]);
		}
	
	}

}
