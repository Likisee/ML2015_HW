package hw1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import Util.CommonUtil;
import Util.ContainerUtil;
import Util.FileUtil;

public class q15 {
	
	private static Random rand = new Random();
	
	private static int getSign(double threshold, double weightSum) {
		if (weightSum - threshold > 0) {
			return 1;
		} else {
			return -1;
		}
	}

	private static double getWeightSum(List<Double> weights, List<Double> values) {
		double weightSum = 0.0;
		int upper = Math.min(weights.size(), values.size());
		for (int i = 0; i < upper; i++) {
			weightSum += weights.get(i) * values.get(i);
		}
		return weightSum;
	}

	private static List<Double> getWeight(List<Double> weights, String trainingData, double factor) {
		List<Double> data = new ArrayList<Double>();
		data.add(1.0);

		trainingData = trainingData.replace(" ", "\t");
		data.addAll(ContainerUtil.getDoubleList(trainingData, "\t"));

		double weightSum = getWeightSum(weights, data);
		int sign = getSign(0, weightSum);

		if (sign == data.get(5).intValue()) {
			return weights;
		} else {
			double w0 = weights.get(0) + factor * data.get(0) * data.get(5);
			double w1 = weights.get(1) + factor * data.get(1) * data.get(5);
			double w2 = weights.get(2) + factor * data.get(2) * data.get(5);
			double w3 = weights.get(3) + factor * data.get(3) * data.get(5);
			double w4 = weights.get(4) + factor * data.get(4) * data.get(5);
			weights = new ArrayList<Double>();
			weights.add(w0);
			weights.add(w1);
			weights.add(w2);
			weights.add(w3);
			weights.add(w4);
			return weights;
		}
	}
	
	private static List<Double> getNewWeights() {
		List<Double> weights = new ArrayList<Double>();
		weights.add(0.0);
		weights.add(0.0);
		weights.add(0.0);
		weights.add(0.0);
		weights.add(0.0);
		return weights;
	}
	
	private static List<String> getRandomLines(List<String> lines) {
		List<String> result = new ArrayList<String>();
		int seedIndex = rand.nextInt(lines.size());
		for (int i = seedIndex; i < lines.size(); i++) {
			result.add(lines.get(i));
		}
		for (int i = 0; i < seedIndex; i++) {
			result.add(lines.get(i));
		}
		return result;
	}

	public static void main(String[] args) {

		// read the training data
		List<String> lines = FileUtil.readFileContent(CommonUtil.getHwFile("hw1", "hw1_15_train.dat"), null);
		//Collections.shuffle(lines); // seems NOT necessary

		// PLA: Q15: finalUpdateCnt: 45
//		List<Double> weights = getNewWeights();
//		int itr = 1;
//		int finalUpdateCnt = 0;
//		int updateCnt = 0;
//		for (int i = 0; i < lines.size(); i++) {
//			List<Double> newWeights = getWeight(weights, (String) lines.get(i), 1.0);
//			if ((weights.get(0).doubleValue() != newWeights.get(0).doubleValue()) 
//					|| (weights.get(1).doubleValue() != newWeights.get(1).doubleValue()) 
//					|| (weights.get(2).doubleValue() != newWeights.get(2).doubleValue()) 
//					|| (weights.get(3).doubleValue() != newWeights.get(3).doubleValue()) 
//					|| (weights.get(4).doubleValue() != newWeights.get(4).doubleValue())) {
//				System.out.println("itr: " + itr + ", " + "line: " + (i + 1) + ", " + "weights: " + weights + ", " + "newWeights: " + newWeights);
//				updateCnt++;
//				weights = newWeights;
//			} else {
//				System.out.println("itr: " + itr + ", " + "line: " + (i + 1) + ", PASS");
//			}
//
//			// halts?
//			System.out.println("itr: " + itr + ", " + "updateCnt: " + updateCnt);
//			if (i == lines.size() - 1) {
//				if (finalUpdateCnt != updateCnt) {
//					itr++;
//					finalUpdateCnt = updateCnt;
//					i = -1; // new for-loop
//				} else {
//					break; // no new update -> halts
//				}
//			}
//		}
//		System.out.println("finalUpdateCnt: " + finalUpdateCnt);

		// PLA: Q16+Q17
		int sumUpdateCnt16= 0, sumUpdateCnt17= 0;
		TreeMap<Integer, Integer> result16 = new TreeMap<Integer, Integer>();
		TreeMap<Integer, Integer> result17 = new TreeMap<Integer, Integer>();
		for (int round = 0; round < 2000; round++) {
			List<String> tmpLines = getRandomLines(lines);
			List<Double> weights16 = getNewWeights();
			List<Double> weights17 = getNewWeights();
			int finalUpdateCnt16 = 0, finalUpdateCnt17 = 0;
			int updateCnt16 = 0, updateCnt17 = 0;

			// Q16
			for (int i = 0; i < tmpLines.size(); i++) {
				List<Double> newWeights = getWeight(weights16, (String) tmpLines.get(i), 1.0); // Q16: avgUpdateCnt: 36.843
				if ((weights16.get(0).doubleValue() != newWeights.get(0).doubleValue()) 
						|| (weights16.get(1).doubleValue() != newWeights.get(1).doubleValue()) 
						|| (weights16.get(2).doubleValue() != newWeights.get(2).doubleValue()) 
						|| (weights16.get(3).doubleValue() != newWeights.get(3).doubleValue()) 
						|| (weights16.get(4).doubleValue() != newWeights.get(4).doubleValue())) {
					updateCnt16++;
					weights16 = newWeights;
				}

				// halts?
				if (i == lines.size() - 1) {
					if (finalUpdateCnt16 != updateCnt16) {
						finalUpdateCnt16 = updateCnt16;
						i = -1; // new for-loop
					} else {
						break; // no new update -> halts
					}
				}
			}
			sumUpdateCnt16 = sumUpdateCnt16 + updateCnt16;
			System.out.println("round: " + (round + 1) + ", " + "finalUpdateCnt16: " + finalUpdateCnt16 + ", " + "sumUpdateCnt16: " + sumUpdateCnt16);
			if(result16.containsKey(finalUpdateCnt16)) {
				result16.put(finalUpdateCnt16, result16.get(finalUpdateCnt16) + 1);
			} else {
				result16.put(finalUpdateCnt16, 1);
			}
			
			// Q17
			for (int i = 0; i < tmpLines.size(); i++) {
				List<Double> newWeights = getWeight(weights17, (String)tmpLines.get(i), 0.5); // Q17: avgUpdateCnt: 37.493
				if ((weights17.get(0).doubleValue() != newWeights.get(0).doubleValue()) 
						|| (weights17.get(1).doubleValue() != newWeights.get(1).doubleValue()) 
						|| (weights17.get(2).doubleValue() != newWeights.get(2).doubleValue()) 
						|| (weights17.get(3).doubleValue() != newWeights.get(3).doubleValue()) 
						|| (weights17.get(4).doubleValue() != newWeights.get(4).doubleValue())) {
					updateCnt17++;
					weights17 = newWeights;
				}

				// halts?
				if (i == lines.size() - 1) {
					if (finalUpdateCnt17 != updateCnt17) {
						finalUpdateCnt17 = updateCnt17;
						i = -1; // new for-loop
					} else {
						break; // no new update -> halts
					}
				}
			}
			sumUpdateCnt17 = sumUpdateCnt17 + updateCnt17;
			System.out.println("round: " + (round + 1) + ", " + "finalUpdateCnt17: " + finalUpdateCnt17 + ", " + "sumUpdateCnt17: " + sumUpdateCnt17);
			if(result17.containsKey(finalUpdateCnt17)) {
				result17.put(finalUpdateCnt17, result17.get(finalUpdateCnt17) + 1);
			} else {
				result17.put(finalUpdateCnt17, 1);
			}
		}
		System.out.println();
		System.out.println("avgUpdateCnt16: " + (sumUpdateCnt16 / 2000.0));
		for (Map.Entry<Integer, Integer> entry : result16.entrySet()) {
			System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
		}
		System.out.println();
		System.out.println("avgUpdateCnt17: " + (sumUpdateCnt17 / 2000.0));
		for (Map.Entry<Integer, Integer> entry : result17.entrySet()) {
			System.out.println("key: " + entry.getKey() + ", value: " + entry.getValue());
		}
	}
}
