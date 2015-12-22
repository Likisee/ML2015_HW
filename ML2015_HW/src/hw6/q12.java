package hw6;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import util.CommonUtil;
import util.FileUtil;

public class q12 {
	
	@Deprecated
	private static void testDataDistinct(List<String> linesTrain, List<String> linesTest) {
		// Just for Data Check
		String[] lineData = null;
		ArrayList<String> test = null;
		test = new ArrayList<String>();
		for(int i = 0; i < linesTrain.size(); i++) {
			lineData = linesTrain.get(i).trim().split(" ");
			if(!test.contains(lineData[0])) {
				test.add(lineData[0]); // 100
			}
		}
		System.out.println(test.size());
		test = new ArrayList<String>();
		for(int i = 0; i < linesTrain.size(); i++) {
			lineData = linesTrain.get(i).trim().split(" ");
			if(!test.contains(lineData[1])) {
				test.add(lineData[1]); // 100
			}
		}
//		System.out.println(test.size());
//		test = new ArrayList<String>();
//		for(int i = 0; i < linesTest.size(); i++) {
//			lineData = linesTest.get(i).trim().split(" ");
//			if(!test.contains(lineData[0])) {
//				test.add(lineData[0]); // 1000
//			}
//		}
//		System.out.println(test.size());
//		test = new ArrayList<String>();
//		for(int i = 0; i < linesTest.size(); i++) {
//			lineData = linesTest.get(i).trim().split(" ");
//			if(!test.contains(lineData[1])) {
//				test.add(lineData[1]);
//			}
//		}
//		System.out.println(test.size()); // 998
	}
	
	public static double [] u = null;
	
	private static ArrayList<Integer> getSortedSeq(int index, double [][] x) {
		TreeMap<Double, Integer> sort = new TreeMap<Double, Integer>();
		for(int i = 0 ; i < x.length; i++) {
			sort.put(x[i][index], i);
		}
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(Map.Entry<Double, Integer> entry: sort.entrySet()) {
			result.add(entry.getValue());
		}
		return result;
	}
	
	private static double[] getResult(double [][] x, double [] y) {
		double [] result = new double[5]; // 0:index 1:sign 2:theta 3:alpha 4:err
		result[4] = Double.MAX_VALUE;
		
		// check each theta
		for(int index = 0; index < x[0].length; index++) {
			
			// prepare thetaArr
			List<Integer> sortedSeq = getSortedSeq(index, x);
			ArrayList<Double> thetaArr = new ArrayList<Double>();
			thetaArr.add(-1.0); // 負無限大
			for(int i = 0; i < sortedSeq.size() - 1; i++) {
				thetaArr.add((x[sortedSeq.get(i)][index] + x[sortedSeq.get(i+1)][index]) / 2);
			}
			thetaArr.add(1.0); // 正無限大
			
			for(int i = 0; i < thetaArr.size(); i++) {
				double theta = thetaArr.get(i);
				// sign = +1;
				double errPositive = 0;
				for(int j = 0; j < x.length; j++) {
					errPositive += (((x[j][index]>=theta?1:-1)*y[j])==-1?1:0)*u[j];
				}
				if(errPositive < result[4]) {
					result[0] = index;
					result[1] = +1;
					result[2] = theta;
					result[4] = errPositive;
				}
				// sign = -1;			
				double errNegative = 0;
				for(int j = 0; j < x.length; j++) {
					errNegative += (((x[j][index]<=theta?1:-1)*y[j])==-1?1:0)*u[j];
				}
				if(errNegative < result[4]) {
					result[0] = index;
					result[1] = -1;
					result[2] = theta;
					result[4] = errNegative;
				}
			}
		}
		
		// calculate factor -> alpha
		int index = (int)result[0];
		double sign = result[1];
		double theta = result[2];
		double err = result[4];
		double sumU = 0;
		for(int i = 0; i < u.length; i++) {
			sumU += u[i];
		}
		double errRate = err / sumU;
		double factor = Math.sqrt( (1-errRate)/errRate );
	    double alpha = Math.log(factor);
	    result[3] = alpha;
		
	    // update u
	    for(int i = 0; i < u.length; i++) {
	    	if(sign == 1) {
		    	if((x[i][index]>=theta?1:-1)!=y[i]) {
		    		u[i] = u[i] * factor;
		    	} else {
		    		u[i] = u[i] / factor;
		    	}
	    	} else {
		    	if((x[i][index]<=theta?1:-1)!=y[i]) {
		    		u[i] = u[i] * factor;
		    	} else {
		    		u[i] = u[i] / factor;
		    	}
	    	}
	    }
   	   return result;
	}
	
	private static double getErrRate(int index, double sign, double theta, double [][] x, double [] y) {
		double errCnt = 0;
		if(sign == 1) { 
			for(int i = 0; i < x.length; i++) {
				if((x[i][index]>=theta?1:-1)!=y[i]) {
					errCnt++;
				}
			}
		} else {
			for(int i = 0; i < x.length; i++) {
				if((x[i][index]<=theta?1:-1)!=y[i]) {
					errCnt++;
				}
			}
		}
		return errCnt / x.length;
	}

	public static void main(String[] args) {

		// read the training & testing data
		List<String> linesTrain = FileUtil.readFileContent(CommonUtil.getHwFile("hw6", "hw2_adaboost_train.dat"), null);
		List<String> linesTest = FileUtil.readFileContent(CommonUtil.getHwFile("hw6", "hw2_adaboost_test.dat"), null);

		// read into array
		String[] lineData = null;
		double[][] arrTrainX = new double[linesTrain.size()][2];
		double[] arrTrainY = new double[linesTrain.size()];
		double[][] arrTestX = new double[linesTest.size()][2];
		double[] arrTestY = new double[linesTrain.size()];
		for(int i = 0; i < linesTrain.size(); i++) {
			lineData = linesTrain.get(i).trim().split(" ");
			arrTrainX[i][0] = Double.parseDouble(lineData[0]);
			arrTrainX[i][1] = Double.parseDouble(lineData[1]);
			arrTrainY[i] = Double.parseDouble(lineData[2]);
		}
		for(int i = 0; i < linesTrain.size(); i++) {
			lineData = linesTest.get(i).trim().split(" ");
			arrTestX[i][0] = Double.parseDouble(lineData[0]);
			arrTestX[i][1] = Double.parseDouble(lineData[1]);
			arrTestY[i] = Double.parseDouble(lineData[2]);
		}

		// initiate parameters
		int ROUND = 300;
		u = new double[arrTrainX.length];
		double [] indexArr = new double[ROUND];
		double [] signArr = new double[ROUND];
		double [] thetaArr = new double[ROUND];
		double [] alphaArr = new double[ROUND];

		// initiate u
		for(int i = 0; i < u.length; i++) {
			u[i] = 1.0 / u.length;
		}
		
		// start iteration
		for(int i = 0; i < ROUND; i++) {
			
			// q15
			double sumU= 0.0;
			for(int j = 0; j < u.length; j++) {
				sumU += u[j];
			}
			System.out.println("U" + (i+1) + ": sumU: " + sumU);
			
			double [] result = getResult(arrTrainX, arrTrainY);
			indexArr[i] = result[0];
			signArr[i] = result[1];
			thetaArr[i] = result[2];
			alphaArr[i] = result[3];
			
			// q12 & q13
			System.out.println("itr " + (i+1) + ": eIN: " + getErrRate((int)indexArr[i], signArr[i], thetaArr[i], arrTrainX, arrTrainY) + ", index: " + (int)indexArr[i]);
			if(i == 0) {
				System.out.println("itr " + (i+1) + ": alpha: " + alphaArr[i]);
			}
			
		}
	}

}
