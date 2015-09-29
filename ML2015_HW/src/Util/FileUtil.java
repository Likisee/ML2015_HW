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

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
