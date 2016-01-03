package hw7;

import java.util.ArrayList;

public class NodeHW7 {
	
	public int index = 0;
	public double theta = 0;
	
	public ArrayList<ArrayList<Double>> nodes = new ArrayList<ArrayList<Double>>();
	
	public NodeHW7 left = null;
	public NodeHW7 right = null;
	
	public double getGini(int yIndex) {
		double size = nodes.size();
		double positiveCnt = 0;
		for(int i = 0; i < nodes.size(); i++) {
			if(nodes.get(i).get(yIndex).doubleValue() == 1) {
				positiveCnt++;
			}
		}
		return (1 - Math.pow(positiveCnt / size, 2) - Math.pow((size - positiveCnt) / size, 2));
	}
	
	public void info(int yIndex) {
		if(left != null) {
			System.out.println("index: " + index);
			System.out.println("theta: " + theta);
			System.out.println("left: " + left.nodes.size());
			System.out.println("right: " + right.nodes.size());
			System.out.println();
		} else {
			System.out.println("leaf: " + nodes.get(0).get(yIndex));
			System.out.println();		
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
