package hw4;

import java.util.List;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import Util.CommonUtil;
import Util.FileUtil;

public class q13 {

	public static double getSign(double output) {
		if(output > 0) {
			return 1.0;
		} else {
			return -1.0;
		}
	}
	
	public static double [][] initDataX(List<String> lines) {
		
		// get N & dim
		int N = lines.size();
		int dim = ("1 " + lines.get(0).trim()).split(" ").length - 1;
		
		// initiate
		double [][] X = new double [N][dim];
		double [] y = new double [N];
		
		// initiate values
		for(int i = 0; i < N; i++) {
			String [] xy = ("1 " + lines.get(i)).trim().split(" ");
			for(int j = 0; j < dim; j++) {
				X[i][j] = Double.valueOf(xy[j]);
			}
			y[i] = Double.valueOf(xy[dim]);
		}
		return X;
	}
	
	public static double [] initDataY(List<String> lines) {
		
		// get N & dim
		int N = lines.size();
		int dim = ("1 " + lines.get(0).trim()).split(" ").length - 1;
		
		// initiate
		double [][] X = new double [N][dim];
		double [] y = new double [N];
		
		// initiate values
		for(int i = 0; i < N; i++) {
			String [] xy = ("1 " + lines.get(i)).trim().split(" ");
			for(int j = 0; j < dim; j++) {
				X[i][j] = Double.valueOf(xy[j]);
			}
			y[i] = Double.valueOf(xy[dim]);
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
		
		double [][] XTrain = null;
		double [] yTrain = null;
		XTrain = initDataX(linesTrain);
		yTrain = initDataY(linesTrain);
		
		double [][] XTest = null;
		double [] yTest = null;
		XTest = initDataX(linesTest);
		yTest = initDataY(linesTest);
		
		double [] wReg;
		double lambda;
		double bestEinLambda;
		double bestEin;
		double bestEoutLambda;
		double bestEout;		
		
		// Q13
//		System.out.println();
//		System.out.println("Q13");
//		lambda = 11.26;
//		wReg = getWReg(XTrain, yTrain, lambda);
//		System.out.println("Lambda: " + lambda);
//		System.out.println("wReg: [" + wReg[0] + ", " + wReg[1] + ", " + wReg[2] + "]");
//		System.out.println("Ein: " + getE(XTrain, yTrain, wReg));
//		System.out.println("Eout: " + getE(XTest, yTest, wReg));
		
		// Q14 & Q15
//		double[] lambdaArr = { 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10 };
//		System.out.println();
//		System.out.println("Q14 & Q15");
//		bestEinLambda = 0;
//		bestEin = Double.MAX_VALUE;
//		bestEoutLambda = 0;
//		bestEout = Double.MAX_VALUE;
//		for(int i = 0; i < lambdaArr.length; i++) {
//			lambda = Math.pow(10, lambdaArr[i]);
//			wReg = getWReg(XTrain, yTrain, lambda);
////			System.out.println("Lambda: " + lambda);
////			System.out.println("wReg: [" + wReg[0] + ", " + wReg[1] + ", " + wReg[2] + "]");
//			double ein = getE(XTrain, yTrain, wReg);
//			if (ein < bestEin) {
//				System.out.print("Ein: " + ein + " (A BETTER FOUND!!)");
//				bestEinLambda = lambda;
//				bestEin = ein;
//			} else {
//				System.out.print("Ein: " + ein);
//			}
//			
//			double eout = getE(XTest, yTest, wReg);
//			if (eout < bestEout) {
//				System.out.println(", Eout: " + eout + " (A BETTER FOUND!!)");
//				bestEoutLambda = lambda;
//				bestEout = eout;
//			} else {
//				System.out.println(", Eout: " + eout);
//			}
//		}
//		System.out.println();
//		System.out.println("bestEinLambda: " + bestEinLambda);
//		System.out.println("bestEin: " + bestEin);
//		System.out.println("bestEoutLambda: " + bestEoutLambda);
//		System.out.println("bestEout: " + bestEout);

		
		
		
	}

}
