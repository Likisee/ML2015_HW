package hw1;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import Util.CommonUtil;
import Util.ContainerUtil;
import Util.FileUtil;

public class q15 {

	private static int getSign(double threshold, double value) {
		if(value - threshold> 0) {
			return 1;
		} else {
			return -1;
		}
	}
	
	private static double getWeightSum(double weight, double ... values) {
		double weightSum = 0.0;
		for (double value : values) {
			weightSum += weight * value;
		}
		return weightSum;
	}
	
	private static double getWeight(double weight, String trainingData) {
		trainingData = trainingData.replace(" ", "\t");
		List<Double> data = ContainerUtil.getRealList(trainingData, "\t");
		
		double weightSum = getWeightSum(weight, data.get(0).doubleValue(), data.get(1).doubleValue(), data.get(2).doubleValue(), data.get(3).doubleValue());
		int sign = getSign(0, weightSum);
		
		if(sign == data.get(4).intValue()) {
			return weight;
		} else {
			return weight + getWeightSum(data.get(4).intValue(), data.get(0).doubleValue(), data.get(1).doubleValue(), data.get(2).doubleValue(), data.get(3).doubleValue());
		}
	}
	
	private static double getWeightQ17(double weight, String trainingData, double factor) {
		trainingData = trainingData.replace(" ", "\t");
		List<Double> data = ContainerUtil.getRealList(trainingData, "\t");
		
		double weightSum = getWeightSum(weight, data.get(0).doubleValue(), data.get(1).doubleValue(), data.get(2).doubleValue(), data.get(3).doubleValue());
		int sign = getSign(0, weightSum);
		
		if(sign == data.get(4).intValue()) {
			return weight;
		} else {
			return weight + factor * getWeightSum(data.get(4).intValue(), data.get(0).doubleValue(), data.get(1).doubleValue(), data.get(2).doubleValue(), data.get(3).doubleValue());
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// read the training data
		List<String> lines = FileUtil.readFileContent(CommonUtil.getHwFile("hw1", "hw1_15_train.dat"), null);
		
		// PLA: Q15
//		int updateCnt = 0;
//		double w = 0.0;
//		for (int i = 0; i < lines.size(); i++) {
//			double newW = getWeight(w, (String)lines.get(i));
//			if(w != newW) {
//				System.out.println("line: " + (i+1) + ", " + "w: " + w + ", " + "newW: " + newW);
//				updateCnt ++;
//				w = newW;
//			} else {
//				System.out.println("line: " + (i+1) + ", PASS");
//			}
//		}
//		System.out.println("updateCnt: " + updateCnt);
		
		// PLA: Q16
		Random rand = new Random();
		int sumUpdateCnt = 0;
		for(int round = 0; round < 2000; round++) {
			int seedIndex = rand.nextInt(400);
			int updateCnt = 0;
			
			Collections.shuffle(lines);
			double w = 0.0;
			for (int i = seedIndex; i < lines.size(); i++) {
//				double newW = getWeight(w, (String)lines.get(i));
				double newW = getWeightQ17(w, (String)lines.get(i), 0.5);
				if(w != newW) {
					updateCnt ++;
					w = newW;
				}
			}			
			for (int i = 0; i < seedIndex; i++) {
//				double newW = getWeight(w, (String)lines.get(i));
				double newW = getWeightQ17(w, (String)lines.get(i), 0.5);
				if(w != newW) {
					updateCnt ++;
					w = newW;
				}
			}
			System.out.println("round: " + round + ", " + "updateCnt: " + updateCnt);
			
			sumUpdateCnt = sumUpdateCnt + updateCnt;
			System.out.println("sumUpdateCnt: " + sumUpdateCnt);
		}
		System.out.println();
		System.out.println("avgUpdateCnt: " + (sumUpdateCnt / 2000.0));
		

	}

}
