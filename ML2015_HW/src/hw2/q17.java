package hw2;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;

import Util.FileUtil;

public class q17 {

	private static double s = 1.0;
	private static double theta = 1.0;

	private static int precision = 100; // TA can try to increase the precision, to decrease the impact of zero in [-precision, precision]
	private static UniformIntegerDistribution uid = new UniformIntegerDistribution(-precision, precision);

	public static int getSign(double output) {
		if(output > 0) {
			return 1;
		} else {
			return -1;
		}
	}

	public static int getFlipSign(int sign) {
		return -1 * sign;
	}
	
	public static ArrayList<Double> getTrainingInputData(int dataSize) {
		ArrayList<Double> result = new ArrayList<Double>();
		for(int i = 0; i < dataSize; i++) {
			result.add(1.0 * uid.sample() / precision);
		}
		Collections.sort(result);
		return result;
	}
	
	public static ArrayList<Integer> getTrainingOutputData(ArrayList<Double> trainingInputData) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(int i = 0; i < trainingInputData.size(); i++) {
			if(Math.abs(1.0 * uid.sample() / precision) < 0.2) {
				result.add(getFlipSign(getSign(trainingInputData.get(i))));
			} else {
				result.add(getSign(trainingInputData.get(i)));
			}
		}
		return result;
	}
	
	public static double getMinEIn(ArrayList<Double> trainingInputData, ArrayList<Integer> trainingOutputData) {
		double minEIn;
		double minEInPositiveS = getMinEIn(trainingInputData, trainingOutputData, 1);
		double thetaPositiveS = theta;
		double minEInNegtiveS = getMinEIn(trainingInputData, trainingOutputData, -1);
		double thetaNegtiveS = theta;
		if(minEInPositiveS < minEInNegtiveS) {
			s = 1;
			theta = thetaPositiveS;
			minEIn = minEInPositiveS;
		} else { // minEInNegtiveS < minEInPositiveS
			s = -1;
			theta = thetaNegtiveS;
			minEIn = minEInNegtiveS;
		}
		return minEIn;
	}
	
	private static double getMinEIn(ArrayList<Double> trainingInputData, ArrayList<Integer> trainingOutputData, double s) {
		double minEIn = Double.MAX_VALUE;
		for(int i = 0; i < trainingInputData.size() - 1; i++) {
			double currenttheta = (trainingInputData.get(i) + trainingInputData.get(i + 1)) / 2.0;
			double currentEIn = getEIn(trainingInputData, trainingOutputData, s, currenttheta);
			if(currentEIn < minEIn) {
				theta = currenttheta;
				minEIn = currentEIn;
			}
		}
		return minEIn;
	}
	
	private static double getEIn(ArrayList<Double> trainingInputData, ArrayList<Integer> trainingOutputData, double s, double theta) {
		double eCnt = 0;
		for(int i = 0; i < trainingInputData.size() - 1; i++) {
			if(trainingOutputData.get(i) != s * getSign(trainingInputData.get(i) - theta)) {
				eCnt++;
			}
		}
		return eCnt / trainingInputData.size();
	}
	
	public static double getEOut(double s, double theta) {
		return 0.5 + 0.3 * s * (Math.abs(theta) - 1.0);
	}

	public static void main(String[] args) {
		final int dataSize = 20;
		final int round = 5000;
		
		double sumEIn = 0.0, sumEOut = 0.0;
		double eIn = 0.0, eOut = 0.0;
		
		StringBuilder calLog = new StringBuilder();
		
		for(int i = 0; i < 5000; i++) {
			
			ArrayList<Double> trainingInputData = getTrainingInputData(dataSize);
			ArrayList<Integer> trainingOutputData = getTrainingOutputData(trainingInputData);
			
			eIn = getMinEIn(trainingInputData, trainingOutputData);
			eOut = getEOut(s, theta);
			sumEIn += eIn;
			sumEOut += eOut;
			
//			System.out.println("round: " + (i + 1) + ", " + "s: " + s + ", " + "theta: " + theta);
			System.out.println("round: " + (i + 1) + ", " + "eIn: " + eIn + ", " + "eOut: " + eOut);
//			calLog.append("round: " + (i + 1) + ", " + "s: " + s + ", " + "theta: " + theta + "\r\n");
			calLog.append("round: " + (i + 1) + ", " + "eIn: " + eIn + ", " + "eOut: " + eOut + "\r\n");
		}
		System.out.println();
		System.out.println("avgErrorIn: " + (1.0 * sumEIn / round));	// Q17: avgErrorIn: 0.15624999999999983
		System.out.println("avgErrorOut: " + (1.0 * sumEOut / round));	// Q18: avgErrorOut: 0.2597491999999994
		
//		FileUtil.writeFileContent("d:\\q17.txt", calLog.toString(), null, false);
	}

}
