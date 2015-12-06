package hw5;

public class q4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double [] alpha = {0, 0.4857048695492317, 0.9214672205745343, 0.8887164347987063, 0.1502852229699299, 0.3681704323551298, 0};
		double [][] x = { {1,0},{0,1},{0,-1},{-1,0},{0,2},{0,-2},{-2,0} };
		double [] y = { -1,-1,-1,1,1,1,1 };
		double [][] z = new double[7][6];
		for(int i = 0; i < y.length; i++) {
			z[i][0] = 1;
			z[i][1] = Math.sqrt(2) * x[i][0];
			z[i][2] = Math.sqrt(2) * x[i][1];
			z[i][3] = x[i][0] * x[i][0];
			z[i][4] = x[i][0] * x[i][1];
			z[i][5] = x[i][1] * x[i][1];
		}
		
		double [] w = new double[6];
		for(int i = 0; i < w.length; i++) {
			w[0] += alpha[i] * y[i] * z[i][0];
			w[1] += alpha[i] * y[i] * z[i][1];
			w[2] += alpha[i] * y[i] * z[i][2];
			w[3] += alpha[i] * y[i] * z[i][3];
			w[4] += alpha[i] * y[i] * z[i][4];
			w[5] += alpha[i] * y[i] * z[i][5];
		}
		for(int i = 0; i < w.length; i++) {
			System.out.println("w[" + i + "]: " + w[i]);
		}
		
		for(int i = 0; i < y.length; i++) {
			double b = 0; 
			for(int j = 0; j < w.length; j++) {
				b += w[j] * z[i][j]; 
			}
			System.out.println("b: " + (y[i] - b));
		}
	

	}

}
