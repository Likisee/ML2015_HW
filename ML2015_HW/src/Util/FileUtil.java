package Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class FileUtil {
	
	public static String defaultEncoding = "UTF-8";

	public static List<String> readFileContent(String filePath, String encoding) {
		return readFileContent(new File(filePath), encoding);
	}

	public static List<String> readFileContent(File file, String encoding) {
		try {
			List<String> lines = null;
			if (encoding == null) {
				lines = FileUtils.readLines(file);
			} else {
				lines = FileUtils.readLines(file, encoding);
			}
			return lines;
		} catch (IOException e) {
			return new ArrayList<String>();
		}
	}

	public static void writeFileContent(String filePath, String content, String encoding, boolean append) {
		writeFileContent(new File(filePath), content, encoding, append);
	}

	public static void writeFileContent(File file, String content, String encoding, boolean append) {

		try {
			if (encoding == null) {
				FileUtils.write(file, content, append);
			} else {
				FileUtils.write(file, content, encoding, append);
			}
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
