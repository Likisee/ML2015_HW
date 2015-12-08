package hw5;

import java.util.List;

import util.CommonUtil;
import util.FileUtil;
import libsvm.*;

public class q16 {

	public static void main(String[] args) {
		
		// read the training & testing data
		List<String> linesTrain = FileUtil.readFileContent(CommonUtil.getHwFile("hw5", "features.train"), null);
		List<String> linesTest = FileUtil.readFileContent(CommonUtil.getHwFile("hw5", "features.test"), null);
		
		// re-write the train
		StringBuffer sbf = new StringBuffer("");
		for(int i = 0; i < linesTrain.size(); i++) {
			String [] oneLineData = linesTrain.get(i).trim().replaceAll("\\s+", " ").split(" ");
			if(Double.parseDouble(oneLineData[0]) == 8) {
				sbf.append("+1 ");
			} else {
				sbf.append("-1 ");
			}
			sbf.append(" 1:" + oneLineData[1]);
			sbf.append(" 2:" + oneLineData[2]);
			sbf.append("\r\n");
		}
		FileUtil.writeFileContent("C:\\libsvm-3.20\\windows\\q16.txt", sbf.toString(), null, false);
		
		// initiate problem
		svm_problem problem = new svm_problem();
		problem.l = linesTrain.size();
		problem.x = new svm_node[problem.l][];
		problem.y = new double[problem.l]; 
		for(int i = 0; i < linesTrain.size(); i++) {
			String [] oneLineData = linesTrain.get(i).trim().replaceAll("\\s+", " ").split(" ");
			if(Double.parseDouble(oneLineData[0]) == 8) {
				problem.y[i] = +1;
			} else {
				problem.y[i] = -1;
			}
			
			svm_node[] x = new svm_node[2];
			x[0] = new svm_node();
			x[0].index = 1;
			x[0].value = Double.parseDouble(oneLineData[1]);
			x[1] = new svm_node();
			x[1].index = 2;
			x[1].value = Double.parseDouble(oneLineData[2]);
			problem.x[i] = x;
		}
		
		// initiate params & train
		svm_parameter params = new svm_parameter();
		params.kernel_type = 1;
		params.degree = 2;
		params.gamma = 1;
		params.coef0 = 1;

//		w = model.SVs' * model.sv_coef;
//		b = -model.rho;
		
//		params.C = Math.pow(10, -2);
//		svm_model model = libsvm.svm.svm_train(problem, params); // EXCEPTION!!
		
		double [] c = {-6, -4, -2, 0, 2};
//		for(int i = 0; i < c.length; i++) {
//			params.C = Math.pow(10, c[i]);
//			svm_model model = libsvm.svm.svm_train(problem, params);			
//		}

		// calculation Ein
		double [][] w = {{-0.999976,	6.49E-07,	2.66E-07},
				{-0.999945,	5.90E-06,	4.68E-05},
				{-0.999258,	-4.24E-04,	2.61E-04},
				{-1.00347,	-6.55E-04,	1.20E-04},
				{-1.00347,	5.93E-02,	6.96E-03},};
		for(int wIndex = 0; wIndex < w.length; wIndex++) {
			double eCnt = 0;
			int ans = 0;
			for(int i = 0; i < linesTrain.size(); i++) {
				String [] oneLineData = linesTrain.get(i).trim().replaceAll("\\s+", " ").split(" ");
				if(Double.parseDouble(oneLineData[0]) == 8) {
					ans = 1;
				} else {
					ans = -1;
				}
				if(ans * (w[wIndex][0] + w[wIndex][1]*Double.parseDouble(oneLineData[1]) + w[wIndex][2]*Double.parseDouble(oneLineData[2])) < 0) {
					eCnt++;
				}
			}
//			System.out.println(c[wIndex] + " eCnt: " + eCnt );
			System.out.println(c[wIndex] + " eIn: " + (eCnt / linesTrain.size()) );
		}

	}

}
