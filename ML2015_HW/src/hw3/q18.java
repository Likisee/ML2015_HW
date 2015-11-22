package hw3;

import java.util.ArrayList;
import java.util.List;

import Util.CommonUtil;
import Util.FileUtil;

public class q18 {
		
	private static int N = 0;
	private static int dim = 0;
	private static double [] w = null;
	private static double [] gradient = null;
	private static double [][] X = null;
	private static double [] y = null;
		
	public static double getSigmoid(double s) {
		return (1.0 / (1.0 + Math.exp(-s)));
	}
	
	public static double getOutput01(double output) {
		if(output > 0.5) {
			return 1.0;
		} else {
			return -1.0;
		}
	}
	
	public static void initData(List<String> linesTrain) {
		
		// get N & dim
		N = linesTrain.size();
		dim = linesTrain.get(0).trim().split(" ").length - 1;
		
		// initiate
		X = new double [N][dim];
		y = new double [N];
		
		// initiate values
		for(int i = 0; i < N; i++) {
			String [] xy = linesTrain.get(i).trim().split(" ");
			for(int j = 0; j < dim; j++) {
				X[i][j] = Double.valueOf(xy[j]);
			}
			y[i] = Double.valueOf(xy[dim]);
		}
	}
	
	public static void updateGradient() {
		for(int i = 0; i < N; i++) {
			double s = 0;
			for(int j = 0; j < dim; j++) {
				s += -1 * y[i] * w[j] * X[i][j];
			}
			for(int j = 0; j < dim; j++) {
				gradient[j] = gradient[j] + getSigmoid(s) * (-1 * y[i] * X[i][j]);
			}
		}
		for(int j = 0; j < dim; j++) {
			gradient[j] = gradient[j] / N;
		}
	}
	
	public static void updateGradientStochastic(int itr) {
		int i = itr % N;
		double s = 0;
		for(int j = 0; j < dim; j++) {
			s += -1 * y[i] * w[j] * X[i][j];
		}
		for(int j = 0; j < dim; j++) {
			gradient[j] = gradient[j] + getSigmoid(s) * (-1 * y[i] * X[i][j]);
		}
	}

	public static void updateW(double ita) {
		for(int j = 0; j < dim; j++) {
			w[j] = w[j] - ita * gradient[j];
		}
	}
	
	public static double getEOut() {
		double errCnt = 0;
		double s = 0;
		for(int i = 0; i < N; i++) {
			s = 0;
			for(int j = 0; j < dim; j++) {
				s += w[j] * X[i][j];
			}
			errCnt += (getOutput01(getSigmoid(s)) != y[i] ? 1 : 0);
		}
		return errCnt / N;
	}
	
	public static void main(String[] args) {
		// read the training & testing data
		List<String> linesTrain = FileUtil.readFileContent(CommonUtil.getHwFile("hw3", "hw3_train.dat"), null);
		List<String> linesTest = FileUtil.readFileContent(CommonUtil.getHwFile("hw3", "hw3_test.dat"), null);
		
		double ita = 0;
		double T = 0;
		ArrayList<Double> wFinal = null;
		
		
		
		// Q18: Implement the fixed learning rate gradient descent algorithm
		ita = 0.001;
		T = 2000;
		wFinal = new ArrayList<Double>();
		
		initData(linesTrain);
		w = new double [dim]; // default 0.0
		for(int i = 0; i < T; i++) {
			gradient = new double[dim];	// default 0.0
			updateGradient();
			updateW(ita);
		}
		for(int j = 0; j < dim; j++) {
			wFinal.add(w[j]);
		}
		System.out.println("w: " + wFinal);
		
		initData(linesTest);
		System.out.println("eOut: " + getEOut());
		
		
		
		// Q19: Implement the fixed learning rate gradient descent algorithm
		ita = 0.01;
		T = 2000;
		wFinal = new ArrayList<Double>();
		
		initData(linesTrain);
		w = new double [dim]; // default 0.0
		for(int i = 0; i < T; i++) {
			gradient = new double[dim];	// default 0.0
			updateGradient();
			updateW(ita);
		}
		for(int j = 0; j < dim; j++) {
			wFinal.add(w[j]);
		}
		System.out.println("w: " + wFinal);
		
		initData(linesTest);
		System.out.println("eOut: " + getEOut());
		
		
		
		// Q20: Implement the fixed learning rate stochastic gradient descent algorithm
		ita = 0.001;
		T = 2000;
		wFinal = new ArrayList<Double>();
		
		initData(linesTrain);
		w = new double [dim]; // default 0.0
		for(int i = 0; i < T; i++) {
			gradient = new double[dim];	// default 0.0
			updateGradientStochastic(i);
			updateW(ita);
		}
		for(int j = 0; j < dim; j++) {
			wFinal.add(w[j]);
		}
		System.out.println("w: " + wFinal);
		
		initData(linesTest);
		System.out.println("eOut: " + getEOut());
		
	}

}
