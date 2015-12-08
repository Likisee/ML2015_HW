package hw5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import util.BatUtils;
import util.CommonUtil;
import util.FileUtil;

public class q20 {

	public static void main(String[] args) {
		
		// read the training & testing data
		List<String> linesTrain = FileUtil.readFileContent(CommonUtil.getHwFile("hw5", "features.train"), null);
		
		// re-write the train
		StringBuffer sbf = new StringBuffer("");
		for(int i = 0; i < linesTrain.size(); i++) {
			String [] oneLineData = linesTrain.get(i).trim().replaceAll("\\s+", " ").split(" ");
			if(Double.parseDouble(oneLineData[0]) == 0) {
				sbf.append("+1");
			} else {
				sbf.append("-1");
			}
			sbf.append(" 1:" + oneLineData[1]);
			sbf.append(" 2:" + oneLineData[2]);
			sbf.append("\r\n");
		}
		FileUtil.writeFileContent("C:\\libsvm-3.20\\windows\\q20.txt", sbf.toString(), null, false);
		
		// real train & test
		linesTrain = FileUtil.readFileContent("C:\\libsvm-3.20\\windows\\q20.txt", null);
		int size = linesTrain.size();
		Random rand = new Random();
		HashMap<Integer, Integer> mapHistogramFood = new HashMap<Integer, Integer>();
		mapHistogramFood.put(0, 0);
		mapHistogramFood.put(1, 0);
		mapHistogramFood.put(2, 0);
		mapHistogramFood.put(3, 0);
		mapHistogramFood.put(4, 0);
		for(int itr = 0; itr < 100; itr++) {
			ArrayList<Integer> selectedList = new ArrayList<Integer>();
			while(selectedList.size() < 1000) {
				int select = rand.nextInt(size);
				if(!selectedList.contains(select)) {
					selectedList.add(select);
				}
			}
			StringBuffer sbfTrain = new StringBuffer("");
			StringBuffer sbfTest = new StringBuffer("");
			for(int i = 0; i < linesTrain.size(); i++) {
				if(selectedList.contains(i)) {
					sbfTrain.append(linesTrain.get(i) + "\r\n");
				} else {
					sbfTest.append(linesTrain.get(i) + "\r\n");
				}
			}
			FileUtil.writeFileContent("C:\\libsvm-3.20\\windows\\q20_train_1000.txt", sbfTrain.toString(), null, false);
			FileUtil.writeFileContent("C:\\libsvm-3.20\\windows\\q20_rest.txt", sbfTest.toString(), null, false);
			int bestGamma = getBestGamma();
			mapHistogramFood.put(bestGamma, mapHistogramFood.get(bestGamma) + 1);
			System.out.println("bestGamma: " + bestGamma);
		}
		System.out.println();
		for(Entry<Integer, Integer> entry : mapHistogramFood.entrySet()) {
			System.out.println("gamma: " + entry.getKey() + ", count: " + entry.getValue());
		}
	}
	
	private static int getBestGamma() {
		int bestGamma = 0;
		int bestAccuracyCnt = Integer.MIN_VALUE;
		for(int gamma = 0; gamma < 5; gamma++) {
			int accuracyCnt = getAccuracyCnt(gamma);
			if(accuracyCnt > bestAccuracyCnt) {
				bestGamma = gamma;
				bestAccuracyCnt = accuracyCnt;
			}
		}
		return bestGamma;
	}
	
	private static int getAccuracyCnt(int gamma) {
		BatUtils.run("C:\\libsvm-3.20\\windows\\q20_" + gamma + ".bat");
		String data = FileUtil.readFileContent("C:\\libsvm-3.20\\windows\\q20_eval_result.txt", null).get(0);
		data = data.substring(data.indexOf("(")+1, data.indexOf(")"));
		System.out.println(data);
		return Integer.parseInt(data.split("/")[0]);
	}

}
