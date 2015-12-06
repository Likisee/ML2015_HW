package hw4;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import util.CommonUtil;
import util.FileUtil;

public class q19 {

	public static double getSign(double output) {
		if(output > 0) {
			return 1.0;
		} else {
			return -1.0;
		}
	}
	
	private static int foldCnt = 5;
	
	public static double[][] initDataX(List<String> lines, List<Integer> foldList) {

		// check begin & end
		int N = lines.size();
		int foldSize = N / foldCnt;

		// get dim
		int dim = ("1 " + lines.get(0).trim()).split(" ").length - 1;

		// initiate
		double[][] X = new double[foldSize * foldList.size()][dim];
		double[] y = new double[foldSize * foldList.size()];

		// initiate values
		int count = 0;
		for(int i = 0; i < foldList.size(); i++) {
			for(int j = 0; j < foldSize; j++) {
				String[] xy = ("1 " + lines.get(foldSize * foldList.get(i) + j)).trim().split(" ");
				for(int k = 0; k < dim; k++) {
					X[count][k] = Double.valueOf(xy[k]);
				}
				y[count] = Double.valueOf(xy[dim]);
				count++;
			}
		}
		return X;
	}
	
	public static double [] initDataY(List<String> lines, List<Integer> foldList) {
		
		// check begin & end
		int N = lines.size();
		int foldSize = N / foldCnt;

		// get dim
		int dim = ("1 " + lines.get(0).trim()).split(" ").length - 1;

		// initiate
		double[][] X = new double[foldSize * foldList.size()][dim];
		double[] y = new double[foldSize * foldList.size()];

		// initiate values
		int count = 0;
		for(int i = 0; i < foldList.size(); i++) {
			for(int j = 0; j < foldSize; j++) {
				String[] xy = ("1 " + lines.get(foldSize * foldList.get(i) + j)).trim().split(" ");
				for(int k = 0; k < dim; k++) {
					X[count][k] = Double.valueOf(xy[k]);
				}
				y[count] = Double.valueOf(xy[dim]);
				count++;
			}
		}
		return y;
	}
	
	public static double[] getWReg(double [][] X, double [] y, double lambda) {
		RealMatrix xMatrix 	= MatrixUtils.createRealMatrix(X);
		RealMatrix xMatrixT = xMatrix.transpose();
		RealMatrix yMatrix 	= MatrixUtils.createColumnRealMatrix(y);
		RealMatrix iMatrix 	= MatrixUtils.createRealIdentityMatrix(xMatrix.getColumnDimension());
		return new LUDecomposition(xMatrixT.multiply(xMatrix).add(iMatrix.scalarMultiply(lambda))).getSolver().getInverse().multiply(xMatrixT).multiply(yMatrix).getColumn(0);
	}
	
	public static double getE(double [][] X, double [] y, double [] wReg) {
		
		// get N & dim
		int N = X.length;
		int dim = X[0].length;
		
		double errCnt = 0;
		double s = 0;
		for(int i = 0; i < N; i++) {
			s = 0;
			for(int j = 0; j < dim; j++) {
				s += wReg[j] * X[i][j];
			}
			errCnt += (getSign(s) != y[i] ? 1 : 0);
		}
		return errCnt / N;
	}
	
	public static void main(String[] args) {
		// read the training & testing data
		List<String> linesTrain = FileUtil.readFileContent(CommonUtil.getHwFile("hw4", "hw4_train.dat"), null);
		List<String> linesTest = FileUtil.readFileContent(CommonUtil.getHwFile("hw4", "hw4_test.dat"), null);
		double[] lambdaArr = { 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10 };		
		
		// Q19, Q20: Variable Initial
		double [][] XTrain = null;
		double [] yTrain = null;
		double [][] XValidate = null;
		double [] yValidate = null;

		ArrayList<Integer> foldFullList = new ArrayList<Integer>();
		for(int i = 0; i < foldCnt; i++) {
			foldFullList.add(i);
		}
		double [][] XTest = null;
		double [] yTest = null;
		XTest = initDataX(linesTest, foldFullList);
		yTest = initDataY(linesTest, foldFullList);
		
		double [] wReg;
		double lambda;
		double bestEcvLambda = 0;
		double bestEcv = Double.MAX_VALUE;
		double sumEcv = 0;

		// Q19
		for(int i = 0; i < lambdaArr.length; i++) {
			sumEcv = 0;
			lambda = Math.pow(10, lambdaArr[i]);
//			System.out.println("Lambda: " + lambda);
			
			for(int j = 0; j < foldCnt; j++) {
				
				// prepare foldTestList & foldValidateList
				ArrayList<Integer> foldTestList = new ArrayList<Integer>();
				ArrayList<Integer> foldValidateList = new ArrayList<Integer>();
				for(int k = 0; k < foldCnt; k++) {
					if (j == k) {
						foldValidateList.add(k);
					} else {
						foldTestList.add(k);
					}
				}
				
				// get XTrain & yTrain
				XTrain = initDataX(linesTrain, foldTestList);
				yTrain = initDataY(linesTrain, foldTestList);
				
				// get XValidate & yValidate
				XValidate = initDataX(linesTrain, foldValidateList);
				yValidate = initDataY(linesTrain, foldValidateList);
				
				// get wReg
				wReg = getWReg(XTrain, yTrain, lambda);
				
				// get ecv
				double ecv = getE(XValidate, yValidate, wReg);
				sumEcv += ecv;
				
//				System.out.println("wReg: [" + wReg[0] + ", " + wReg[1] + ", " + wReg[2] + "]");
//				System.out.println("Rount " + (j + 1) + ": " + "Ecv: " + ecv);
			}
			
			if (sumEcv / foldCnt < bestEcv) {
				System.out.println("Ecv: " + sumEcv / foldCnt + " (A BETTER FOUND!!)");
				bestEcvLambda = lambda;
				bestEcv = sumEcv / foldCnt;
			} else {
				System.out.println("Ecv: " + sumEcv / foldCnt);
			}
		}
		System.out.println();
		System.out.println("bestEcvLambda: " + bestEcvLambda);
		System.out.println("bestEcv: " + bestEcv);
		
		// Q20
		System.out.println();
		System.out.println("Q20");
		XTrain = initDataX(linesTrain, foldFullList);
		yTrain = initDataY(linesTrain, foldFullList);
		wReg = getWReg(XTrain, yTrain, bestEcvLambda);
		System.out.println("Lambda: " + bestEcvLambda);
		System.out.println("wReg: [" + wReg[0] + ", " + wReg[1] + ", " + wReg[2] + "]");
		System.out.println("Ein: " + getE(XTrain, yTrain, wReg));
		System.out.println("Eout: " + getE(XTest, yTest, wReg));
		
	}

}
