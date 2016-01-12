package project;

import java.util.List;
import java.util.Random;

import util.FileUtil;

public class FeatureExtractorToSVM {
	
	private static Random rand = new Random();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		StringBuffer sbf = null;
		StringBuffer sbf2 = null;
		String [] oneLineData = null;
		
		// 0: ID
		
		// 1: user_log_num
		// 2: course_log_num
		// 3: take_course_num
		// 4: take_user_num
		// 5: log_num
		
		// 6: server_nagivate
		// 7: server_access
		// 8: server_problem
		// 9: browser_access
		// 10: browser_problem
		// 11: browser_page_close
		// 12: browser_video
		// 13: server_discussion
		// 14: server_wiki
		
		// 15: chapter_count
		// 16: sequential_count
		// 17: video_count

		List<String> linesTrainAns = FileUtil.readFileContent("D:\\ML_final_project\\truth_train.csv", null);
		System.out.println(linesTrainAns.size());
		
		List<String> linesTrainX = FileUtil.readFileContent("D:\\ML_final_project\\sample_train_x.csv", null);
		System.out.println(linesTrainX.size());
		
		List<String> linesTrainMy = FileUtil.readFileContent("D:\\ML_final_project\\myEvent1520252830_train.csv", null);
		System.out.println(linesTrainMy.size());
		
		sbf = new StringBuffer("");
		sbf2 = new StringBuffer("");
		for(int i = 0; i < linesTrainAns.size(); i++) {
			
			oneLineData = linesTrainAns.get(i).trim().split(",");
			sbf.append(oneLineData[1]);
			
			oneLineData = linesTrainX.get(i+1).trim().split(",");
//			sbf.append(" 1:" + oneLineData[1]);
//			sbf.append(" 2:" + oneLineData[2]);
//			sbf.append(" 3:" + oneLineData[3]);
//			sbf.append(" 4:" + oneLineData[4]);
//			sbf.append(" 5:" + oneLineData[5]);
			double log_num = Double.parseDouble(oneLineData[5]);
//			sbf.append(" 6:" + oneLineData[6]);
//			sbf.append(" 7:" + oneLineData[7]);
//			sbf.append(" 8:" + oneLineData[8]);
//			sbf.append(" 9:" + oneLineData[9]);
//			sbf.append(" 10:" + oneLineData[10]);
//			sbf.append(" 11:" + oneLineData[11]);
//			sbf.append(" 12:" + oneLineData[12]);
//			sbf.append(" 13:" + oneLineData[13]);
//			sbf.append(" 14:" + oneLineData[14]);
//			sbf.append(" 15:" + oneLineData[15]);
//			sbf.append(" 16:" + oneLineData[16]);
//			sbf.append(" 17:" + oneLineData[17]);
			
			oneLineData = linesTrainMy.get(i+1).trim().split(",");
			sbf.append(" 1:" + Double.parseDouble(oneLineData[1])/log_num);
			sbf.append(" 2:" + Double.parseDouble(oneLineData[2])/log_num);
			sbf.append(" 3:" + Double.parseDouble(oneLineData[3])/log_num);
			sbf.append(" 4:" + Double.parseDouble(oneLineData[4])/log_num);
			sbf.append(" 5:" + log_num/5128);
			
			// 20160112
			sbf2.append(Double.parseDouble(oneLineData[1])/log_num);
			sbf2.append(" " + Double.parseDouble(oneLineData[2])/log_num);
			sbf2.append(" " + Double.parseDouble(oneLineData[3])/log_num);
			sbf2.append(" " + Double.parseDouble(oneLineData[4])/log_num);
			sbf2.append(" " + log_num/5128);
			oneLineData = linesTrainAns.get(i).trim().split(",");
			if(oneLineData[1].equals("0")) {
				sbf2.append(" -1\r\n");
			} else {
				sbf2.append(" " + oneLineData[1] + "\r\n");
			}
			
			sbf.append("\r\n");
		}
		FileUtil.writeFileContent("C:\\libsvm-3.20\\windows\\final_train.txt", sbf.toString(), null, false);
		FileUtil.writeFileContent("C:\\libsvm-3.20\\windows\\20160112_train.txt", sbf2.toString(), null, false);
		
		List<String> linesTestX = FileUtil.readFileContent("D:\\ML_final_project\\sample_test_x.csv", null);
		System.out.println(linesTestX.size());
		
		List<String> linesTestMy = FileUtil.readFileContent("D:\\ML_final_project\\myEvent1520252830_test.csv", null);
		System.out.println(linesTestMy.size());
		
		sbf = new StringBuffer("");
		sbf2 = new StringBuffer("");
		for(int i = 1; i < linesTestX.size(); i++) {
			
			sbf.append("1");
			
			oneLineData = linesTestX.get(i).trim().split(",");
//			sbf.append(" 1:" + oneLineData[1]);
//			sbf.append(" 2:" + oneLineData[2]);
//			sbf.append(" 3:" + oneLineData[3]);
//			sbf.append(" 4:" + oneLineData[4]);
//			sbf.append(" 5:" + oneLineData[5]);
			double log_num = Double.parseDouble(oneLineData[5]);
//			sbf.append(" 6:" + oneLineData[6]);
//			sbf.append(" 7:" + oneLineData[7]);
//			sbf.append(" 8:" + oneLineData[8]);
//			sbf.append(" 9:" + oneLineData[9]);
//			sbf.append(" 10:" + oneLineData[10]);
//			sbf.append(" 11:" + oneLineData[11]);
//			sbf.append(" 12:" + oneLineData[12]);
//			sbf.append(" 13:" + oneLineData[13]);
//			sbf.append(" 14:" + oneLineData[14]);
//			sbf.append(" 15:" + oneLineData[15]);
//			sbf.append(" 16:" + oneLineData[16]);
//			sbf.append(" 17:" + oneLineData[17]);
			
			oneLineData = linesTestMy.get(i).trim().split(",");
			sbf.append(" 1:" + Double.parseDouble(oneLineData[1])/log_num);
			sbf.append(" 2:" + Double.parseDouble(oneLineData[2])/log_num);
			sbf.append(" 3:" + Double.parseDouble(oneLineData[3])/log_num);
			sbf.append(" 4:" + Double.parseDouble(oneLineData[4])/log_num);
			sbf.append(" 5:" + log_num/7697);
			
			// 20160112
			sbf2.append(Double.parseDouble(oneLineData[1])/log_num);
			sbf2.append(" " + Double.parseDouble(oneLineData[2])/log_num);
			sbf2.append(" " + Double.parseDouble(oneLineData[3])/log_num);
			sbf2.append(" " + Double.parseDouble(oneLineData[4])/log_num);
			sbf2.append(" " + log_num/7697);
			sbf2.append(" 1\r\n");
			
			sbf.append("\r\n");
		}
		FileUtil.writeFileContent("C:\\libsvm-3.20\\windows\\final_test.txt", sbf.toString(), null, false);
		FileUtil.writeFileContent("C:\\libsvm-3.20\\windows\\20160112_test.txt", sbf2.toString(), null, false);
		
//		List<String> linesSubmission = FileUtil.readFileContent("D:\\ML_final_project\\sampleSubmission.csv", null);
//		System.out.println(linesSubmission.size());
		
	}

}
