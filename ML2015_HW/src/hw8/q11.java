package hw8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import util.CommonUtil;
import util.FileUtil;

public class q11 {

	private static int size = 0;
	private static double [][] x_train = null;
	private static double [] y_train = null;
	private static double [][] x_test = null;
	private static double [] y_test = null;
	
	private static int getKNNknbor(int k, double [] xm) {
		
		TreeMap<Double, ArrayList<Integer>> sortMap = new TreeMap<Double, ArrayList<Integer>>();
		ArrayList<Integer> arr = null;
		for(int i = 0; i < y_train.length; i++) {
			double distance = 0;
			for(int j = 0; j < size; j++) {
				distance += Math.pow((x_train[i][j] - xm[j]), 2);
			}
			if(sortMap.containsKey(distance)) {
				arr = sortMap.get(distance);
				arr.add(i);
				sortMap.put(distance, arr);
			} else {
				arr = new ArrayList<Integer>();
				arr.add(i);
				sortMap.put(distance, arr);
			}
		}
		
		ArrayList<Integer> sortArray = new ArrayList<Integer>();
		for(Map.Entry<Double, ArrayList<Integer>> entry: sortMap.entrySet()) {
			sortArray.addAll(entry.getValue());
		}
		
		int sum = 0;
		for(int i = 0; i < k; i++) {
			sum += y_train[sortArray.get(i)];
		}
		if(sum >= 0) {
			return 1;
		} else {
			return -1;
		}
	}
	
	private static int getKNNuniform(double gumma, double [] xm) {
		
		double sum = 0;
		for(int i = 0; i < y_train.length; i++) {
			double distance = 0;
			for(int j = 0; j < size; j++) {
				distance += Math.pow((x_train[i][j] - xm[j]), 2);
			}
			sum += y_train[i] * Math.exp(-gumma * distance);
		}
		
		if(sum >= 0) {
			return 1;
		} else {
			return -1;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Load Data
		List<String> lines = null;
		String [] line = null;
		
		lines = FileUtil.readFileContent(CommonUtil.getHwFile("hw8", "hw8_train.dat"), null);
		size = lines.get(0).trim().split(" ").length - 1;
		x_train = new double [lines.size()][size];
		y_train = new double [lines.size()];
		for(int i = 0; i < lines.size(); i++) {
			line = lines.get(i).trim().split(" ");
			for(int j = 0; j < line.length - 1; j++) {
				x_train[i][j] = Double.parseDouble(line[j]);
			}
			y_train[i] = Double.parseDouble(line[line.length -1]);
		}
		
		lines = FileUtil.readFileContent(CommonUtil.getHwFile("hw8", "hw8_test.dat"), null);
		x_test = new double [lines.size()][size];
		y_test = new double [lines.size()];
		for(int i = 0; i < lines.size(); i++) {
			line = lines.get(i).trim().split(" ");
			for(int j = 0; j < line.length - 1; j++) {
				x_test[i][j] = Double.parseDouble(line[j]);
			}
			y_test[i] = Double.parseDouble(line[line.length -1]);
		}
		
		int[] kArr = new int[] { 1, 3, 5, 7, 9 };
		
		// 11&12
		System.out.println();
		System.out.println("11&12");
		for(int kIndex = 0; kIndex < kArr.length; kIndex++) {
			int k = kArr[kIndex];
			double errCnt = 0;
			for(int i = 0; i < y_train.length; i++) { //
				if(y_train[i] != getKNNknbor(k, x_train[i])) {
					errCnt++;
				}
			}
			System.out.println(k + ": " + (errCnt / y_train.length));
		}

		// 13&14
		System.out.println();
		System.out.println("13&14");
		for(int kIndex = 0; kIndex < kArr.length; kIndex++) {
			int k = kArr[kIndex];
			double errCnt = 0;
			for(int i = 0; i < y_test.length; i++) { //
				if(y_test[i] != getKNNknbor(k, x_test[i])) {
					errCnt++;
				}
			}
			System.out.println(k + ": " + (errCnt / y_test.length));
		}
		
		double[] gummaArr = new double[] { 0.001, 0.1, 1, 10, 100 };

		// 15&16
		System.out.println();
		System.out.println("15&16");
		for(int gIndex = 0; gIndex < gummaArr.length; gIndex++) {
			double gumma = gummaArr[gIndex];
			double errCnt = 0;
			for(int i = 0; i < y_train.length; i++) { //
				if(y_train[i] != getKNNuniform(gumma, x_train[i])) {
					errCnt++;
				}
			}
			System.out.println(gumma + ": " + (errCnt / y_train.length));
		}
		
		// 17&18
		System.out.println();
		System.out.println("17&18");
		for(int gIndex = 0; gIndex < gummaArr.length; gIndex++) {
			double gumma = gummaArr[gIndex];
			double errCnt = 0;
			for(int i = 0; i < y_test.length; i++) { //
				if(y_test[i] != getKNNuniform(gumma, x_test[i])) {
					errCnt++;
				}
			}
			System.out.println(gumma + ": " + (errCnt / y_test.length));
		}
	}
}
