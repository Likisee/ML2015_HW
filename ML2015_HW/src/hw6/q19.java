package hw6;

import java.util.List;

import util.CommonUtil;
import util.FileUtil;
import libsvm.*;

public class q19 {

	public static void main(String[] args) {
		
		// read the training & testing data
		List<String> lines = FileUtil.readFileContent(CommonUtil.getHwFile("hw6", "hw2_lssvm_all.dat"), null);
		
		// re-write the train
		StringBuffer sbf = new StringBuffer("");
		for(int i = 0; i < 400; i++) {
			String [] oneLineData = lines.get(i).trim().replaceAll("\\s+", " ").split(" ");
			sbf.append(oneLineData[10]);
			sbf.append(" 1:" + oneLineData[0]);
			sbf.append(" 2:" + oneLineData[1]);
			sbf.append(" 3:" + oneLineData[2]);
			sbf.append(" 4:" + oneLineData[3]);
			sbf.append(" 5:" + oneLineData[4]);
			sbf.append(" 6:" + oneLineData[5]);
			sbf.append(" 7:" + oneLineData[6]);
			sbf.append(" 8:" + oneLineData[7]);
			sbf.append(" 9:" + oneLineData[8]);
			sbf.append(" 10:" + oneLineData[9]);
			sbf.append("\r\n");
		}
		FileUtil.writeFileContent("C:\\libsvm-3.20\\windows\\q19_train.txt", sbf.toString(), null, false);
		
		sbf = new StringBuffer("");
		for(int i = 400; i < lines.size(); i++) {
			String [] oneLineData = lines.get(i).trim().replaceAll("\\s+", " ").split(" ");
			sbf.append(oneLineData[10]);
			sbf.append(" 1:" + oneLineData[0]);
			sbf.append(" 2:" + oneLineData[1]);
			sbf.append(" 3:" + oneLineData[2]);
			sbf.append(" 4:" + oneLineData[3]);
			sbf.append(" 5:" + oneLineData[4]);
			sbf.append(" 6:" + oneLineData[5]);
			sbf.append(" 7:" + oneLineData[6]);
			sbf.append(" 8:" + oneLineData[7]);
			sbf.append(" 9:" + oneLineData[8]);
			sbf.append(" 10:" + oneLineData[9]);
			sbf.append("\r\n");
		}
		FileUtil.writeFileContent("C:\\libsvm-3.20\\windows\\q19_test.txt", sbf.toString(), null, false);
				
	}

}
