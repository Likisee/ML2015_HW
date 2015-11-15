package hw3;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class q14 {


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
		x = new double [N][6];
		double x1, x2;
		for(int i = 0; i < N; i++) {
			x1 = 1.0 * uid.sample() / precision;
			x2 = 1.0 * uid.sample() / precision;
			x[i][0] = 1.0;
			x[i][1] = x1;
			x[i][2] = x2;
			x[i][3] = x1 * x2;
			x[i][4] = x1 * x1;
			x[i][5] = x2 * x2;
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
	
	private static double getEIn(double w0, double w1, double w2, double w3, double w4, double w5) {
		double eCnt = 0;
		double sum = 0;
		for(int i = 0; i < N; i++) {
			sum = 0;
			sum += x[i][0] * w0;
			sum += x[i][1] * w1;
			sum += x[i][2] * w2;
			sum += x[i][3] * w3;
			sum += x[i][4] * w4;
			sum += x[i][5] * w5;
			eCnt += getSign(sum) != y[i] ? 1 : 0;
		}
		return eCnt / N;
	}

	public static void main(String[] args) {
		final int round = 1000;
		
		double sumEIn = 0.0, sumEOut = 0.0;
		double eIn = 0.0, eOut = 0.0;
		
		double w0 = 0.0, sumW0 = 0.0;
		double w1 = 0.0, sumW1 = 0.0;
		double w2 = 0.0, sumW2 = 0.0;
		double w3 = 0.0, sumW3 = 0.0;
		double w4 = 0.0, sumW4 = 0.0;
		double w5 = 0.0, sumW5 = 0.0;
		
		OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
		
		for(int i = 0; i < round; i++) {
			
			// get w0~w5
			getInit();
			regression.newSampleData(y, x);
			double [] weight = regression.estimateRegressionParameters();
			w0 = weight[1]; sumW0 += weight[1];
			w1 = weight[2]; sumW1 += weight[2];
			w2 = weight[3]; sumW2 += weight[3];
			w3 = weight[4]; sumW3 += weight[4];
			w4 = weight[5]; sumW4 += weight[5];
			w5 = weight[6]; sumW5 += weight[6];
			
			// eIn
			eIn = getEIn(w0, w1, w2, w3, w4, w5);
			sumEIn += eIn;
			
			// eOut
			getInit();
			eOut = getEIn(w0, w1, w2, w3, w4, w5);
			sumEOut += eOut;
			System.out.println("round: " + (i + 1) + ", eIn:" + eIn + ", eOut:" + eOut + ", w3:" + w3);

		}
		System.out.println();
		
		System.out.println("w0: " + (sumW0 / round));				// Q14: w0: -2.832375813577342E12
		System.out.println("w1: " + (sumW1 / round));				// Q14: w1: -3.7525657044850315E-5
		System.out.println("w2: " + (sumW2 / round));				// Q14: w2: -0.002193501613745655
		System.out.println("w3: " + (sumW3 / round));				// Q14: w3: 0.004604323179583428
		System.out.println("w4: " + (sumW4 / round));				// Q14: w4: 1.5582397491198134
		System.out.println("w5: " + (sumW5 / round));				// Q14: w5: 1.5613045342197651
		System.out.println("avgErrorIn: " + (sumEIn / round));		// Q15: avgErrorIn: 0.4975230000000007
		System.out.println("avgErrorOut: " + (sumEOut / round));	// Q15: avgErrorOut: 0.4970960000000004
		System.out.println();
		
		// SPECIAL: eOut by Golden W
		w0 = -1;
		w1 = -0.05;
		w2 = 0.08;
		w3 = 0.13;
		w4 = 1.5;
		w5 = 1.5;
		eOut = 0.0;
		sumEOut = 0.0;
		for(int i = 0; i < round; i++) {
			// eOut
			getInit();
			eOut = getEIn(w0, w1, w2, w3, w4, w5);
			sumEOut += eOut;
//			System.out.println("round: " + (i + 1) + ", eOut:" + eOut);
		}
		System.out.println("(Golden Parameters) avgErrorOut: " + (sumEOut / round));	// Q15: (Golden Parameters) avgErrorIn: 0.500184

	}

}
