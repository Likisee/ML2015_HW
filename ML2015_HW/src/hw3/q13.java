package hw3;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class q13 {


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
	
	private static int N = 1000;
	private static double [][] x = null;
	private static double [] y = null;
	
	public static void getInit() {
		getTrainingInputData();
		getTrainingOutputData();
	}
	
	public static void getTrainingInputData() {
		x = new double [N][3];
		double x1, x2;
		for(int i = 0; i < N; i++) {
			x1 = 1.0 * uid.sample() / precision;
			x2 = 1.0 * uid.sample() / precision;
			x[i][0] = 1.0;
			x[i][1] = x1;
			x[i][2] = x2;
		}
	}
	
	public static void getTrainingOutputData() {
		y = new double [N];

		for(int i = 0; i < N; i++) {
			if(Math.abs(1.0 * uid.sample() / precision) < 0.1) {
				y[i] = getFlipSign(getSign(Math.pow(x[i][1], 2) + Math.pow(x[i][2], 2) - 0.6));
			} else {
				y[i] = getSign(Math.pow(x[i][1], 2) + Math.pow(x[i][2], 2) - 0.6);
			}
		}
	}
	
	private static double getEIn(double w0, double w1, double w2) {
		double eCnt = 0;
		double sum = 0;
		for(int i = 0; i < N; i++) {
			sum = 0;
			sum += x[i][0] * w0;
			sum += x[i][1] * w1;
			sum += x[i][2] * w2;
			eCnt += getSign(sum) != y[i] ? 1 : 0;
		}
		return eCnt / N;
	}

	public static void main(String[] args) {
		final int round = 1000;
		
		double sumEIn = 0.0, sumEOut = 0.0;
		double eIn = 0.0, eOut = 0.0;
		
		OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
		
		for(int i = 0; i < round; i++) {
			
			getInit();
			regression.newSampleData(y, x);
			double [] weight = regression.estimateRegressionParameters();
			eIn = getEIn(weight[1], weight[2], weight[3]);
			sumEIn += eIn;
			System.out.println("round: " + (i + 1) + ", eIn:" + eIn);

		}
		System.out.println();
		System.out.println("avgErrorIn: " + (sumEIn / round));	// Q13: avgErrorIn: 0.5014959999999998

	}

}
