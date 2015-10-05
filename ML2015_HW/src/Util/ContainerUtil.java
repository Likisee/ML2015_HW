package Util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ContainerUtil {
	
	public static String HBaseDelim = "~\\|";
	
	public static List<String> getStringList(String str, String delim) {
		List<String> result = new ArrayList<String>();
		String [] strArr = str.split(delim);
		for(int i = 0; i < strArr.length; i++) {
			result.add(strArr[i]);
		}
		return result;
	}
	
	public static List<Double> getDoubleList(String str, String delim) {
		List<Double> result = new ArrayList<Double>();
		String [] strArr = str.split(delim);
		for(int i = 0; i < strArr.length; i++) {
			result.add(Double.valueOf(strArr[i]));
		}
		return result;
	}
	
	public static String getStringConcate(List<String> lst, String delim) {
		String result = "";
		for(int i = 0; i < lst.size() - 1; i++) {
			result += lst.get(i) + delim;
		}
		result += lst.get(lst.size() - 1);
		return result;
	}
	
	public static String getStringConcate(List<Double> lst, String delim, DecimalFormat df) {
		String result = "";
		for(int i = 0; i < lst.size() - 1; i++) {
			result += df.format(lst.get(i)) + delim;
		}
		result += df.format(lst.get(lst.size() - 1));
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
