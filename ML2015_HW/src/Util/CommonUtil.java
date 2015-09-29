package Util;

import java.io.File;
import java.io.IOException;

public class CommonUtil {
	
	public static String getProjectRoot() {
		File file2 = new File(CommonUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		return file2.getParent();
	}
	
	public static File getHwFile(String hwName, String fileName) {
		return new File(getProjectRoot() + File.separator + "ntumlone" + File.separator + hwName + File.separator + fileName);
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		File file1 = new File("/");
		System.out.println(file1.getCanonicalPath()); // C:\
		
		File file2 = new File(CommonUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		System.out.println(file2.getParent());
	}

}
