package Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileUtil {
	
	public static List<String> getFileContent(String filePath) {
		return getFileContent(new File(filePath));
	}
	
	public static List<String> getFileContent(File file) {
		try {
			List<String> lines = FileUtils.readLines(file, "UTF-8");
			return lines;
		} catch (IOException e) {
			return new ArrayList<String>();
		}
	}
	
	public static void writeFileContent(String filePath, String content, String encoding, boolean append) {
		writeFileContent(new File(filePath), content, encoding, append);
	}
	
	public static void writeFileContent(File file, String content, String encoding, boolean append) {
		encoding = encoding==null?"UTF-8":encoding;
		try {
			FileUtils.write(file, content, encoding, append);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileUtil.writeFileContent("D:\\HAPPY.TXT", "HelloWorld", null, false);
	}

}
