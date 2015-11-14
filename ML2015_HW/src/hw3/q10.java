package hw3;

import java.util.ArrayList;

public class q10 {

	private static double E(double u, double v) {
		return Math.exp(u) + Math.exp(2 * v) + Math.exp(u * v) + Math.pow(u, 2) - 2 * u * v + 2 * Math.pow(v, 2) - 3 * u - 2 * v;
	}

	private static double Edu(double u, double v) {
		return Math.exp(u) + v * Math.exp(u * v) + 2 * u - 2 * v - 3;
	}

	private static double Edv(double u, double v) {
		return 2 * Math.exp(2 * v) + u * Math.exp(u * v) - 2 * u + 4 * v - 2;
	}

	private static double Edudu(double u, double v) {
		return Math.exp(u) + Math.pow(v, 2) * Math.exp(u * v) + 2;
	}

	private static double Edudv(double u, double v) {
		return u * v * Math.exp(u * v) - 2;
	}

	private static double Edvdv(double u, double v) {
		return 4 * Math.exp(2 * v) + Math.pow(u, 2) * Math.exp(u * v) + 4;
	}

	public static void main(String[] args) {
		int updateMax = 5;
		ArrayList<Double> uList = new ArrayList<Double>();
		ArrayList<Double> vList = new ArrayList<Double>();
		ArrayList<Double> duList = new ArrayList<Double>();
		ArrayList<Double> dvList = new ArrayList<Double>();
		ArrayList<Double> duduList = new ArrayList<Double>();
		ArrayList<Double> dudvList = new ArrayList<Double>();
		ArrayList<Double> dvdvList = new ArrayList<Double>();
		uList.add(0.0);
		vList.add(0.0);
		for(int i = 0; i <= updateMax; i++) {
			double edu = Edu(uList.get(i), vList.get(i));
			double edv = Edv(uList.get(i), vList.get(i));
			double edudu = Edudu(uList.get(i), vList.get(i));
			double edudv = Edudv(uList.get(i), vList.get(i));
			double edvdv = Edvdv(uList.get(i), vList.get(i));
			duList.add(edu);
			dvList.add(edv);
			duduList.add(edudu);
			dudvList.add(edudv);
			dvdvList.add(edvdv);
			double nextU = uList.get(i) - duList.get(i) / duduList.get(i);
			double nextV = vList.get(i) - dvList.get(i) / dvdvList.get(i);
			uList.add(nextU);
			vList.add(nextV);
		}
		for(int i = 0; i <= updateMax; i++) {
			System.out.println("update: " + i);
			System.out.println("E: " + E(uList.get(i), vList.get(i)));
			System.out.println("u: " + uList.get(i));
			System.out.println("v: " + vList.get(i));
			System.out.println("du: " + duList.get(i));
			System.out.println("dv: " + dvList.get(i));
			System.out.println("dudu: " + duduList.get(i));
			System.out.println("dudv: " + dudvList.get(i));
			System.out.println("dudv: " + dvdvList.get(i));
			System.out.println();
		}
	}

}
