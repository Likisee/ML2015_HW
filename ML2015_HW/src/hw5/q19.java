package hw5;

import java.util.List;

import util.CommonUtil;
import util.FileUtil;
import libsvm.*;

public class q19 {

	public static void main(String[] args) {
		
		// read the training & testing data
		List<String> linesTrain = FileUtil.readFileContent(CommonUtil.getHwFile("hw5", "features.train"), null);
		List<String> linesTest = FileUtil.readFileContent(CommonUtil.getHwFile("hw5", "features.test"), null);
		
		// re-write the train
		StringBuffer sbf = new StringBuffer("");
		for(int i = 0; i < linesTrain.size(); i++) {
			String [] oneLineData = linesTrain.get(i).trim().replaceAll("\\s+", " ").split(" ");
			if(Double.parseDouble(oneLineData[0]) == 0) {
				sbf.append("+1");
			} else {
				sbf.append("-1");
			}
			sbf.append(" 1:" + oneLineData[1]);
			sbf.append(" 2:" + oneLineData[2]);
			sbf.append("\r\n");
		}
		FileUtil.writeFileContent("C:\\libsvm-3.20\\windows\\q19.txt", sbf.toString(), null, false);
		
		sbf = new StringBuffer("");
		for(int i = 0; i < linesTest.size(); i++) {
			String [] oneLineData = linesTest.get(i).trim().replaceAll("\\s+", " ").split(" ");
			if(Double.parseDouble(oneLineData[0]) == 0) {
				sbf.append("+1");
			} else {
				sbf.append("-1");
			}
			sbf.append(" 1:" + oneLineData[1]);
			sbf.append(" 2:" + oneLineData[2]);
			sbf.append("\r\n");
		}
		FileUtil.writeFileContent("C:\\libsvm-3.20\\windows\\q19_test.txt", sbf.toString(), null, false);
		
		// initiate problem
		svm_problem problem = new svm_problem();
		problem.l = linesTrain.size();
		problem.x = new svm_node[problem.l][];
		problem.y = new double[problem.l]; 
		for(int i = 0; i < linesTrain.size(); i++) {
			String [] oneLineData = linesTrain.get(i).trim().replaceAll("\\s+", " ").split(" ");
			if(Double.parseDouble(oneLineData[0]) == 0) {
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
		params.kernel_type = 2;
		params.degree = 2;
		params.C = 0.1;

//		w = model.SVs' * model.sv_coef;
//		b = -model.rho;
		
//		params.gamma = Math.pow(10, 4);
//		svm_model model = libsvm.svm.svm_train(problem, params); // EXCEPTION!!
		
		double [] gamma = {0, 1, 2, 3, 4};
//		for(int i = 0; i < c.length; i++) {
//			params.gamma = Math.pow(10, gamma[i]);
//			svm_model model = libsvm.svm.svm_train(problem, params);			
//		}

		
	}

}
