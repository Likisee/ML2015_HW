package util;

import java.util.ArrayList;
import java.util.List;

public class HBaseUtil {
	
	public static String concat = "~|";
	public static String delim = "~\\|";

	public static String concate(String... args) {
		String result = "";
		for(int i = 0; i < args.length; i++) {
			result += args[i] + concat;
		}
		if(args.length > 0) {
			result = result.substring(0, result.length() - concat.length());
		}
		return result;
	}

	public static List<String> split(String data, Integer limit) {
		List<String> result = new ArrayList<String>();
		if(data == null) {
			return result;
		}
		String [] dataArr = null;
		if(limit == null) {
			dataArr = data.split(delim);
		} else {
			dataArr = data.split(delim, limit);
		}
		for(int i = 0; i < dataArr.length; i++) {
			result.add(dataArr[i]);
		}
		return result;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String data = concate("abc", "efg", "");
		System.out.println(data);
		System.out.println(split(data, null));
		System.out.println(split(data, 3));
	}

}
