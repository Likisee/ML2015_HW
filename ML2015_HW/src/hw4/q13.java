package hw4;

import java.util.List;

import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import Util.CommonUtil;
import Util.FileUtil;

public class q13 {

	private static int N = 0;
	private static int dim = 0;
	private static double [][] X = null;
	private static double [] y = null;
		
	public static double getSign(double output) {
		if(output > 0) {
			return 1.0;
		} else {
			return -1.0;
		}
	}
	
	public static void initData(List<String> linesTrain) {
		
		// get N & dim
		N = linesTrain.size();
		dim = ("1 " + linesTrain.get(0).trim()).split(" ").length - 1;
		
		// initiate
		X = new double [N][dim];
		y = new double [N];
		
		// initiate values
		for(int i = 0; i < N; i++) {
			String [] xy = ("1 " + linesTrain.get(i)).trim().split(" ");
			for(int j = 0; j < dim; j++) {
				X[i][j] = Double.valueOf(xy[j]);
			}
			y[i] = Double.valueOf(xy[dim]);
		}
	}
	
	public static double[] getWReg(double lambda) {
		RealMatrix xMatrix 	= MatrixUtils.createRealMatrix(X);
		RealMatrix xMatrixT = xMatrix.transpose();
		RealMatrix yMatrix 	= MatrixUtils.createColumnRealMatrix(y);
		RealMatrix iMatrix 	= MatrixUtils.createRealIdentityMatrix(dim); // RealMatrix iMatrix = MatrixUtils.createRealIdentityMatrix(xMatrix.getColumnDimension());
		return new LUDecomposition(xMatrixT.multiply(xMatrix).add(iMatrix.scalarMultiply(lambda))).getSolver().getInverse().multiply(xMatrixT).multiply(yMatrix).getColumn(0);
	}
	
	public static double getE(double [] wReg) {
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
		
		double [] wReg;
		double[] lambdaArr = { 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -8, -9, -10 };
		double lambda;
		double bestEinLambda;
		double bestEin;
		double bestEoutLambda;
		double bestEout;		
		
		// Q13
		System.out.println();
		System.out.println("Q13");
		initData(linesTrain);
		lambda = 11.26;
		wReg = getWReg(lambda);
		System.out.println("Lambda: " + lambda);
		System.out.println("wReg: [" + wReg[0] + ", " + wReg[1] + ", " + wReg[2] + "]");
		System.out.println("Ein: " + getE(wReg));
		initData(linesTest);
		System.out.println("Eout: " + getE(wReg));
		
		// Q14 & Q15: best Ein (min Ein)
//		System.out.println();
//		System.out.println("Q14 & Q15");
//		bestEinLambda = 0;
//		bestEin = Double.MAX_VALUE;
//		bestEoutLambda = 0;
//		bestEout = Double.MAX_VALUE;
//		initData(linesTrain);
//		for(int i = 0; i < lambdaArr.length; i++) {
//			initData(linesTrain);
//			lambda = Math.pow(10, lambdaArr[i]);
//			wReg = getWReg(lambda);
////			System.out.println("Lambda: " + lambda);
////			System.out.println("wReg: [" + wReg[0] + ", " + wReg[1] + ", " + wReg[2] + "]");
//			double ein = getE(wReg);
//			if (ein < bestEin) {
//				System.out.print("Ein: " + ein + " (A BETTER FOUND!!)");
//				bestEinLambda = lambda;
//				bestEin = ein;
//			} else {
//				System.out.print("Ein: " + ein);
//			}
//			
//			initData(linesTest);
//			double eout = getE(wReg);
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

		
		
		
		
		
		
		
//		double ita = 0;
//		double T = 0;
//		ArrayList<Double> wFinal = null;
//		
//		
//		
//		// Q18: Implement the fixed learning rate gradient descent algorithm
//		ita = 0.001;
//		T = 2000;
//		wFinal = new ArrayList<Double>();
//		
//		initData(linesTrain);
//		w = new double [dim]; // default 0.0
//		for(int i = 0; i < T; i++) {
//			gradient = new double[dim];	// default 0.0
//			updateGradient();
//			updateW(ita);
//		}
//		for(int j = 0; j < dim; j++) {
//			wFinal.add(w[j]);
//		}
//		System.out.println("w: " + wFinal);
//		
//		initData(linesTest);
//		System.out.println("eOut: " + getEOut());
//		
//		
//		
//		// Q19: Implement the fixed learning rate gradient descent algorithm
//		ita = 0.01;
//		T = 2000;
//		wFinal = new ArrayList<Double>();
//		
//		initData(linesTrain);
//		w = new double [dim]; // default 0.0
//		for(int i = 0; i < T; i++) {
//			gradient = new double[dim];	// default 0.0
//			updateGradient();
//			updateW(ita);
//		}
//		for(int j = 0; j < dim; j++) {
//			wFinal.add(w[j]);
//		}
//		System.out.println("w: " + wFinal);
//		
//		initData(linesTest);
//		System.out.println("eOut: " + getEOut());
//		
//		
//		
//		// Q20: Implement the fixed learning rate stochastic gradient descent algorithm
//		ita = 0.001;
//		T = 2000;
//		wFinal = new ArrayList<Double>();
//		
//		initData(linesTrain);
//		w = new double [dim]; // default 0.0
//		for(int i = 0; i < T; i++) {
//			gradient = new double[dim];	// default 0.0
//			updateGradientStochastic(i);
//			updateW(ita);
//		}
//		for(int j = 0; j < dim; j++) {
//			wFinal.add(w[j]);
//		}
//		System.out.println("w: " + wFinal);
//		
//		initData(linesTest);
//		System.out.println("eOut: " + getEOut());
		
	}

}
