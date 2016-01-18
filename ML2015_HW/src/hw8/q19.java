package hw8;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import util.CommonUtil;
import util.FileUtil;

public class q19 {
	
	private static Random rand = new Random();

	private static int size = 0;
	private static double [][] x = null;
	
	private static double getEinFromKMeans(int K, int updateRound) {
		double result = 0;
		double [][] center = getIntitialCenters(K);
		int [] category = new int[x.length];
		for(int i = 0; i < updateRound; i++) {
			category = updateCategory(center);
			center = updateCenter(center, category);
		}
		result = calEIn(center, category);
		return result;
	}
	
	private static double [][] getIntitialCenters(int K) {
		ArrayList<Integer> selected = new ArrayList<Integer>();
		while(selected.size() < K) {
			int newCenter = rand.nextInt(x.length);
			if(!selected.contains(newCenter)) {
				selected.add(newCenter);
			}
		}
		double [][] result = new double [K][size];
		for(int i = 0; i < selected.size(); i++) {
			result[i] = x[selected.get(i)];
		}
		return result;
	}
	
	private static int [] updateCategory(double [][] center) {
		int [] result = new int [x.length];
		TreeMap<Double, Integer> sort = new TreeMap<Double, Integer>();
		for(int i = 0; i < x.length; i++) {
			sort = new TreeMap<Double, Integer>();
			for(int j = 0; j < center.length; j++) {
				double distance = 0;
				for(int k = 0; k < size; k++) {
					distance += Math.pow((x[i][k] - center[j][k]), 2);
				}
				sort.put(distance, j);
			}
			for(Map.Entry<Double, Integer> entry: sort.entrySet()) {
				result[i] = entry.getValue();
				break; // get min distance
			}
		}
		return result;
	}
	
	private static double [][] updateCenter(double [][] center, int [] category) {
		double [][] result = new double [center.length][size];
		for(int i = 0; i < center.length; i++) {
			int count = 0;
			double [] newCenter =  new double [size];
			for(int j = 0; j < category.length; j++) {
				if(category[j] == i) {
					count ++;
					for(int k = 0; k < size; k++) {
						newCenter[k] += x[j][k];
					}
				}
			}
			for(int k = 0; k < size; k++) {
				newCenter[k] /= count;
			}
			result[i] = newCenter;
		}
		return result;
	}
	
	private static double calEIn(double [][] center, int [] category) {
		double result = 0;
		for(int i = 0; i < x.length; i++) {
			double distance = 0;
			for(int k = 0; k < size; k++) {
				distance += Math.pow((x[i][k] - center[category[i]][k]), 2);
			}
			result += distance;
		}
		return result / x.length;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Load Data
		List<String> lines = null;
		String [] line = null;
		
		lines = FileUtil.readFileContent(CommonUtil.getHwFile("hw8", "hw8_nolabel_train.dat"), null);
		size = lines.get(0).trim().split(" ").length;
		x = new double [lines.size()][size];
		for(int i = 0; i < lines.size(); i++) {
			line = lines.get(i).trim().split(" ");
			for(int j = 0; j < line.length; j++) {
				x[i][j] = Double.parseDouble(line[j]);
			}
		}
		
		int[] kArr = new int[] { 2, 4, 6, 8, 10 };
		int updateRound = 50; // update 50 times (Why magic 50? Because almost no difference after 50 times.)
		int itrMax = 500;
		
		// 19&20
		System.out.println();
		System.out.println("19&20");
		for(int kIndex = 0; kIndex < kArr.length; kIndex++) {
			int K = kArr[kIndex];
			double eInSum = 0;
			for(int itr = 0; itr < itrMax; itr++) {
				double eIn = getEinFromKMeans(K, updateRound);
				eInSum += eIn;
			}
			System.out.println(K + ": " + (eInSum / itrMax));
		}
	}
}
