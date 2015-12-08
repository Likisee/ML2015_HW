package hw5;

import java.util.HashMap;
import java.util.List;

import util.CommonUtil;
import util.FileUtil;
import util.HBaseUtil;

public class q17 {

	public static void main(String[] args) {

		double [] c = {-6, -4, -2, 0, 2};

		// build look-up map
		List<String> linesTrain = FileUtil.readFileContent(CommonUtil.getHwFile("hw5", "q16.txt"), null);
		HashMap<String, Integer> mapAlphaLookup = new HashMap<String, Integer>();
		for(int i = 0; i < linesTrain.size(); i++) {
			String [] oneLineData = linesTrain.get(i).trim().replaceAll("\\s+", " ").split(" ");
			Double x1 = Double.parseDouble(oneLineData[1].substring(2));
			Double x2 = Double.parseDouble(oneLineData[2].substring(2));
			if(oneLineData[0].equals("+1")) {
				mapAlphaLookup.put(HBaseUtil.concate(x1.toString(), x2.toString()), 1);
			} else {
				mapAlphaLookup.put(HBaseUtil.concate(x1.toString(), x2.toString()), -1);
			}
		}
		
		// calculation sumAlpha
		for(int cIndex = 0; cIndex < c.length; cIndex++) {
			double sumAlpha = 0;
			List<String> linesResult = FileUtil.readFileContent(CommonUtil.getHwFile("hw5", "q16." + ((int)c[cIndex]) + ".model"), null);
			for(int i = 11; i < linesResult.size(); i++) { // SV info starts from index 11
				String [] oneLineData = linesResult.get(i).trim().replaceAll("\\s+", " ").split(" ");
				Double yn = Double.parseDouble(oneLineData[0]);
				Double x1 = Double.parseDouble(oneLineData[1].substring(2));
				Double x2 = Double.parseDouble(oneLineData[2].substring(2));
				sumAlpha += yn / mapAlphaLookup.get(HBaseUtil.concate(x1.toString(), x2.toString()));
			}
			System.out.println(c[cIndex] + " sumAlpha: " + sumAlpha );
		}

	}

}
