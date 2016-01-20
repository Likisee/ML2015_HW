package project;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import util.CommonUtil;
import util.FileUtil;

public class AdaBoost {
	
	public static double [] u = null;
	public static int [][] test_result = null;
	
	private static ArrayList<Integer> getSortedSeq(int index, double [][] x) {
		TreeMap<Double, ArrayList<Integer>> sort = new TreeMap<Double, ArrayList<Integer>>();
		for(int i = 0 ; i < x.length; i++) {
			if(sort.containsKey(x[i][index])) {
				ArrayList<Integer> temp = sort.get(x[i][index]);
				temp.add(i);
				sort.put(x[i][index], temp);
			} else {
				ArrayList<Integer> temp = new ArrayList<Integer>();
				temp.add(i);
				sort.put(x[i][index], temp);
			}
		}
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(Map.Entry<Double, ArrayList<Integer>> entry: sort.entrySet()) {
			result.addAll(entry.getValue());
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
			
			for(int i = 0; i < thetaArr.size() / 1; i++) {
				double theta = thetaArr.get(i * 1);
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
		int sign = (int)result[1];
		double theta = result[2];
		double err = result[4];
		double sumU = 0;
		for(int i = 0; i < u.length; i++) {
			sumU += u[i];
		}
		double errRate = err / sumU;
		result[4] = errRate;
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
	
	private static double getErrRateLittleG(int index, int sign, double theta, double [][] x, double [] y) {
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
	
	@Deprecated
	private static void insertTestResult(double [] indexArr, double [] signArr, double [] thetaArr, double [] alphaArr, double [][] x, double [] y, int t) {
		double [] yAnswer = new double[x.length];
		for(int i = 0; i <= t; i++) {
			int index = (int)indexArr[i];
			int sign = (int)signArr[i];
			double theta = thetaArr[i];
			double alpha = alphaArr[i];
			for(int j = 0; j < x.length; j++) {
				if(sign == 1) {
					yAnswer[j] += alpha * (x[j][index]>=theta?1:-1);
				} else {
					yAnswer[j] += alpha * (x[j][index]<=theta?1:-1);
				}
			}
		}
		
//		for(int i = 0; i < x.length; i++) {
//			if(yAnswer[i] < 0) {
//				test_result[i][t] = -1;
//			} else {
//				test_result[i][t] = 1;
//			}
//		}
		for(int i = 0; i < x.length; i++) {
			if(yAnswer[i] < 0) {
				test_result[i][0] += -1;
			} else {
				test_result[i][0] += 1;
			}
		}
	}

	public static void main(String[] args) {

		// read the training & testing data
		List<String> linesTrain = FileUtil.readFileContent(CommonUtil.getHwFile("project", "20160112_train.txt"), null);
		List<String> linesTest = FileUtil.readFileContent(CommonUtil.getHwFile("project", "20160112_test.txt"), null);

		// read into array
		String[] lineData = null;
		double[][] arrTrainX = new double[linesTrain.size()][5];
		double[] arrTrainY = new double[linesTrain.size()];
		for(int i = 0; i < linesTrain.size(); i++) {
			lineData = linesTrain.get(i).trim().split(" ");
			arrTrainX[i][0] = Double.parseDouble(lineData[0]);
			arrTrainX[i][1] = Double.parseDouble(lineData[1]);
			arrTrainX[i][2] = Double.parseDouble(lineData[2]);
			arrTrainX[i][3] = Double.parseDouble(lineData[3]);
			arrTrainX[i][4] = Double.parseDouble(lineData[4]);
			arrTrainY[i] = Double.parseDouble(lineData[5]);
		}
		double[][] arrTestX = new double[linesTest.size()][5];
		double[] arrTestY = new double[linesTest.size()];
		for(int i = 0; i < linesTest.size(); i++) {
			lineData = linesTest.get(i).trim().split(" ");
			arrTestX[i][0] = Double.parseDouble(lineData[0]);
			arrTestX[i][1] = Double.parseDouble(lineData[1]);
			arrTestX[i][2] = Double.parseDouble(lineData[2]);
			arrTestX[i][3] = Double.parseDouble(lineData[3]);
			arrTestX[i][4] = Double.parseDouble(lineData[4]);
			arrTestY[i] = Double.parseDouble(lineData[5]);
		}

		// initiate parameters
		int ROUND = 1000;
		u = new double[arrTrainX.length];
		double [] indexArr = new double[ROUND];
		double [] signArr = new double[ROUND];
		double [] thetaArr = new double[ROUND];
		double [] alphaArr = new double[ROUND];

//		test_result = new int[arrTestX.length][ROUND];
		test_result = new int[arrTestX.length][1];
		
		// initiate u
		for(int i = 0; i < u.length; i++) {
			u[i] = 1.0 / u.length;
		}
		
		double minErrRate = Double.MAX_VALUE; // for q16
		// start iteration
		for(int i = 0; i < ROUND; i++) {
			
			double [] result = getResult(arrTrainX, arrTrainY);
			indexArr[i] = result[0];
			signArr[i] = result[1];
			thetaArr[i] = result[2];
			alphaArr[i] = result[3];
			
			// update minErrRate
			System.out.println("itr " + (i+1) + ": et: " + result[4]);
			if(minErrRate > result[4]) {
				minErrRate = result[4];
			}

			System.out.println("itr " + (i+1) + ": eINg: " + getErrRateLittleG((int)indexArr[i], (int)signArr[i], thetaArr[i], arrTrainX, arrTrainY));
			insertTestResult(indexArr, signArr, thetaArr, alphaArr, arrTestX, arrTestY, i);

			StringBuffer sbf = new StringBuffer("");
			for(int j = 0; j < test_result.length; j++) {
				sbf.append((((double)test_result[j][0] / ROUND + 1) * 0.5) + "\r\n");
			}		
			FileUtil.writeFileContent("C:\\libsvm-3.20\\windows\\AdaBoost.csv", sbf.toString(), null, false);
			
			System.out.println(new Date());
		}
		
//		StringBuffer sbf = new StringBuffer("");
//		for(int i = 0; i < test_result.length; i++) {
//			for(int j = 0; j < test_result[0].length; j++) {
//				if(test_result[i][j] == -1) {
//					sbf.append("0,");
//				} else {
//					sbf.append("1,");					
//				}
//			}
//			sbf.append("\r\n");
//		}
//		FileUtil.writeFileContent("C:\\libsvm-3.20\\windows\\AdaBoost.csv", sbf.toString(), null, false);
	}

}
