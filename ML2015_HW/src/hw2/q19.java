package hw2;

import java.util.List;

import Util.CommonUtil;
import Util.FileUtil;

public class q19 {

	public static void main(String[] args) {
		// read the training & testing data
		List<String> linesTrain = FileUtil.readFileContent(CommonUtil.getHwFile("hw2", "hw2_train.dat"), null);
		List<String> linesTest = FileUtil.readFileContent(CommonUtil.getHwFile("hw2", "hw1_test.dat"), null);
	}

}
