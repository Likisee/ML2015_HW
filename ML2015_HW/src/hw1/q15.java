package hw1;

import java.util.List;

import Util.CommonUtil;
import Util.FileUtil;

public class q15 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<String> lines = FileUtil.getFileContent(CommonUtil.getHwFile("hw1", "hw1_15_train.dat"));
		for (int i = 0; i < lines.size(); i++) {
			System.out.println(lines.get(i));
		}
	}

}
