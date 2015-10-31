package hw2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import Util.CommonUtil;
import Util.FileUtil;

public class q19 {

	private static double s = 1.0;
	private static double theta = 1.0;

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
	
	public static HashMap<String, ArrayList<Double>> getInputDataOutputData(List<String> linesTrain, int index) {
		HashMap<String, ArrayList<Double>> result = new HashMap<String, ArrayList<Double>>();
		TreeMap<Double, ArrayList<Double>> data= new TreeMap<Double, ArrayList<Double>>();
		ArrayList<Double> InputData = new ArrayList<Double>();
		ArrayList<Double> OutputData = new ArrayList<Double>();
		double x = 0.0, y = 0.0;
		ArrayList<Double> yList = null;
		for(int i = 0; i < linesTrain.size(); i++) {
			x = getValue(linesTrain.get(i), index);
			y = getValue(linesTrain.get(i), 9);
			if(data.containsKey(x)) {
				yList = data.get(x);
				yList.add(y);
				data.put(x, yList);
			} else {
				yList = new ArrayList<Double>();
				yList.add(y);
				data.put(x, yList);
			}	
		}
		
		for(Map.Entry<Double , ArrayList<Double>> entry : data.entrySet()) {
			x = entry.getKey();
			yList = entry.getValue();
			for(int i = 0; i < yList.size(); i++) {
				InputData.add(x);
				OutputData.add(yList.get(i));
			}
		}
		result.put("InputData", InputData);
		result.put("OutputData", OutputData);
		return result;
	}
	
	private static double getValue(String oneline, int index) {
		return Double.parseDouble(oneline.trim().split(" ")[index]);
	}
	
	public static double getMinEIn(ArrayList<Double> trainingInputData, ArrayList<Double> trainingOutputData) {
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
	
	private static double getMinEIn(ArrayList<Double> trainingInputData, ArrayList<Double> trainingOutputData, double s) {
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
	
	private static double getEIn(ArrayList<Double> trainingInputData, ArrayList<Double> trainingOutputData, double s, double theta) {
		double eCnt = 0;
		for(int i = 0; i < trainingInputData.size() - 1; i++) {
			if(trainingOutputData.get(i) != s * getSign(trainingInputData.get(i) - theta)) {
				eCnt++;
			}
		}
		return eCnt / trainingInputData.size();
	}
	
	public static double getEOut(ArrayList<Double> testingInputData, ArrayList<Double> testingOutputData, double s, double theta) {
		return getEIn(testingInputData, testingOutputData, s, theta);
	}
	
	public static void main(String[] args) {
		// read the training & testing data
		List<String> linesTrain = FileUtil.readFileContent(CommonUtil.getHwFile("hw2", "hw2_train.dat"), null);
		List<String> linesTest = FileUtil.readFileContent(CommonUtil.getHwFile("hw2", "hw2_test.dat"), null);
		
		// Q19
		double bestEIn = Double.MAX_VALUE, bestS = 0.0, bestTheta = 0.0;
		double eIn = 0.0;
		for(int i = 0; i < 9; i++) { // 總共九組訓練資料 (0~8)
			HashMap<String, ArrayList<Double>> inputDataOutputData = getInputDataOutputData(linesTrain, i);
			ArrayList<Double> InputData = inputDataOutputData.get("InputData");
			ArrayList<Double> OutputData = inputDataOutputData.get("OutputData");
			eIn = getMinEIn(InputData, OutputData);
			if(eIn < bestEIn) {
				System.out.println("i: " + i + ", " + "eIn: " + eIn + ", " + "s: " + s + ", " + "theta: " + theta);
				bestEIn = eIn;
				bestS = s;
				bestTheta = theta;
			}
		}
		System.out.println("bestEIn: " + bestEIn + ", " + "bestS: " + bestS + ", " + "bestTheta: " + bestTheta);
		
		// Q20
		double bestEOut = Double.MAX_VALUE;
		double eOut = 0.0;
		for(int i = 0; i < 9; i++) { // 總共九組測試資料 (0~8)
			HashMap<String, ArrayList<Double>> inputDataOutputData = getInputDataOutputData(linesTest, i);
			ArrayList<Double> InputData = inputDataOutputData.get("InputData");
			ArrayList<Double> OutputData = inputDataOutputData.get("OutputData");
			eOut = getEOut(InputData, OutputData, bestS, bestTheta);
			if(eOut < bestEOut) {
				System.out.println("i: " + i + ", " + "eOut: " + eOut);
				bestEOut = eOut;
			}
		}
		System.out.println("bestEOut: " + bestEOut);
		
	}

}
