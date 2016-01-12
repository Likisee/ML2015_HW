package project;

import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import util.FileUtil;

public class FeatureExtractorLineByLine {
	
	public static void main(String[] args) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date time = null;
		BufferedReader br = null;
		String line = null;
		String [] lineArr = null;
		StringBuffer sbf = null;
		try {
			// truth_train
			HashMap<String, Boolean> enrollmentPass = new HashMap<String, Boolean>();
			br = new BufferedReader(new FileReader("D:\\ML_final_project\\truth_train.csv"));
			while ((line = br.readLine()) != null) {
				lineArr = line.split(",");
				if(lineArr[1].equals("1")) {
					enrollmentPass.put(lineArr[0], false);
				} else {
					enrollmentPass.put(lineArr[0], true);
				}
			}
			
			// (0) HARDRULE: 沒考試紀錄 一切搞屁
			
			// (1) HARDRULE: 好學生/壞學生
			HashMap<String, Double> usernamePassCnt = new HashMap<String, Double>();
			HashMap<String, Integer> usernameTotalCnt = new HashMap<String, Integer>();
			HashMap<String, Double> usernamePassRate = new HashMap<String, Double>();
			HashSet<String> goodStudent = new HashSet<String>();
			HashSet<String> badStudent = new HashSet<String>();
			br = new BufferedReader(new FileReader("D:\\ML_final_project\\enrollment_train.csv"));
			while ((line = br.readLine()) != null) {
				lineArr = line.split(",");
				usernamePassCnt.put(lineArr[1], 0.0);
				usernameTotalCnt.put(lineArr[1], 0);
			}
			br = new BufferedReader(new FileReader("D:\\ML_final_project\\enrollment_train.csv"));
			while ((line = br.readLine()) != null) {
				lineArr = line.split(",");
				if(enrollmentPass.containsKey(lineArr[0]) && enrollmentPass.get(lineArr[0])) {
					usernamePassCnt.put(lineArr[1], usernamePassCnt.get(lineArr[1]) + 1);
					usernameTotalCnt.put(lineArr[1], usernameTotalCnt.get(lineArr[1]) + 1);
				} else {
					usernameTotalCnt.put(lineArr[1], usernameTotalCnt.get(lineArr[1]) + 1);
				}
			}
			double rate = 0;
			for(Map.Entry<String, Double> entry : usernamePassCnt.entrySet()) {
				rate = usernamePassCnt.get(entry.getKey()) / usernameTotalCnt.get(entry.getKey());
				usernamePassRate.put(entry.getKey(), rate);
				if(rate == 1) {
					goodStudent.add(entry.getKey());
				}
				if(rate == 0) {
					badStudent.add(entry.getKey());
				}
			}
			sbf = new StringBuffer("");
			br = new BufferedReader(new FileReader("D:\\ML_final_project\\enrollment_test.csv")); // 21366users,9422old,11944new
			while ((line = br.readLine()) != null) {
				lineArr = line.split(",");
				if(goodStudent.contains(lineArr[1])) {
					sbf.append(lineArr[0] + ",0\r\n"); // 0=不會被當
				} else if(badStudent.contains(lineArr[1])) {
					sbf.append(lineArr[0] + ",1\r\n"); // 1=會被當
				} else {
					sbf.append(lineArr[0] + ",-\r\n");
				}
			}
			FileUtil.writeFileContent("D:\\ML_final_project\\myGoodBadStudent.csv", sbf.toString(), null, false);
			
			// (2) TA的features大部分是以count為主,沒有時間軸上density的概念：Event Density (改藍色部分)
			// (3) 作業越早寫越用功? 越晚寫有得抄?
			// (4) 是否有按時上課? early bird...
			// (5) 是否去討論區參考後再作答... (時間序列概念)
			
			// object.csv
			// course_id,module_id,category,children,start
			// category: [vertical, discussion, outlink, course_info, about, problem, course, category, chapter, sequential, peergrading, combinedopenended, html, dictation, video, static_tab]
//			HashMap<String, Date> courseStartTime = new HashMap<String, Date>();
//			HashMap<String, Date> moduleStartTime = new HashMap<String, Date>();
//			HashMap<String, String> moduleCourse = new HashMap<String, String>();
//			br = new BufferedReader(new FileReader("D:\\ML_final_project\\object.csv"));
//			while ((line = br.readLine()) != null) {
//				lineArr = line.split(",");
//				if(!lineArr[4].equals("null") && !lineArr[4].equals("start")) {
//					time = sdf.parse(lineArr[4]);
//					if(!courseStartTime.containsKey(lineArr[0])) {
//						courseStartTime.put(lineArr[0], time);
//					} else if(time.getTime() < courseStartTime.get(lineArr[0]).getTime()){
//						courseStartTime.put(lineArr[0], time);
//					}
//					if(!moduleStartTime.containsKey(lineArr[1])) {
//						moduleStartTime.put(lineArr[1], time);
//					} else if(time.getTime() < moduleStartTime.get(lineArr[1]).getTime()){
//						moduleStartTime.put(lineArr[1], time);
//					}
//					moduleCourse.put(lineArr[1], lineArr[0]);
//				}
//			}
			
			// log_train
			// enrollment_id,time,source,event,object
			HashMap<String, Date> enrollmentStartTime = new HashMap<String, Date>();
			br = new BufferedReader(new FileReader("D:\\ML_final_project\\log_train.csv"));
			while ((line = br.readLine()) != null) {
				lineArr = line.split(",");
				if(!lineArr[1].equals("null")  && !lineArr[1].equals("time")) {
					time = sdf.parse(lineArr[1]);
					if(!enrollmentStartTime.containsKey(lineArr[0])) {
						enrollmentStartTime.put(lineArr[0], time);
					} else if(time.getTime() < enrollmentStartTime.get(lineArr[0]).getTime()) {
						enrollmentStartTime.put(lineArr[0], time);
					}
				}
			}
			br = new BufferedReader(new FileReader("D:\\ML_final_project\\log_test.csv"));
			while ((line = br.readLine()) != null) {
				lineArr = line.split(",");
				if(!lineArr[1].equals("null")  && !lineArr[1].equals("time")) {
					time = sdf.parse(lineArr[1]);
					if(!enrollmentStartTime.containsKey(lineArr[0])) {
						enrollmentStartTime.put(lineArr[0], time);
					} else if(time.getTime() < enrollmentStartTime.get(lineArr[0]).getTime()) {
						enrollmentStartTime.put(lineArr[0], time);
					}
				}
			}
			
			// (2) features: 針對 log_num, 倒數幾天的log_num!!
			// log_train
			// enrollment_id,time,source,event,object
			int lineCnt = 0;
			long after15days = 15*24*60*60;
			long after20days = 20*24*60*60;
			long after25days = 25*24*60*60;
			long after28days = 28*24*60*60;
			long after30days = 30*24*60*60;
			HashMap<String, Integer> enrollmentEventCount15 = new HashMap<String, Integer>();
			HashMap<String, Integer> enrollmentEventCount20 = new HashMap<String, Integer>();
			HashMap<String, Integer> enrollmentEventCount25 = new HashMap<String, Integer>();
			HashMap<String, Integer> enrollmentEventCount28 = new HashMap<String, Integer>();
			HashMap<String, Integer> enrollmentEventCount30 = new HashMap<String, Integer>();
			br = new BufferedReader(new FileReader("D:\\ML_final_project\\log_train.csv"));
			long timeDiff = 0;
			while ((line = br.readLine()) != null) {
				lineCnt ++;
				lineArr = line.split(",");
				if(!lineArr[1].equals("null")  && !lineArr[1].equals("time")) {
					time = sdf.parse(lineArr[1]);
					if(enrollmentStartTime.containsKey(lineArr[0])) {
						timeDiff = (time.getTime() - enrollmentStartTime.get(lineArr[0]).getTime()) / 1000;
						if(timeDiff > after15days) {
							if(!enrollmentEventCount15.containsKey(lineArr[0])) {
								enrollmentEventCount15.put(lineArr[0], 1);
							} else {
								enrollmentEventCount15.put(lineArr[0], enrollmentEventCount15.get(lineArr[0]) + 1);
							}
						}
						if(timeDiff > after20days) {
							if(!enrollmentEventCount20.containsKey(lineArr[0])) {
								enrollmentEventCount20.put(lineArr[0], 1);
							} else {
								enrollmentEventCount20.put(lineArr[0], enrollmentEventCount20.get(lineArr[0]) + 1);
							}
						}
						if(timeDiff > after25days) {
							if(!enrollmentEventCount25.containsKey(lineArr[0])) {
								enrollmentEventCount25.put(lineArr[0], 1);
							} else {
								enrollmentEventCount25.put(lineArr[0], enrollmentEventCount25.get(lineArr[0]) + 1);
							}
						}
						if(timeDiff > after28days) {
							if(!enrollmentEventCount28.containsKey(lineArr[0])) {
								enrollmentEventCount28.put(lineArr[0], 1);
							} else {
								enrollmentEventCount28.put(lineArr[0], enrollmentEventCount28.get(lineArr[0]) + 1);
							}
						}
						if(timeDiff > after30days) {
							if(!enrollmentEventCount30.containsKey(lineArr[0])) {
								enrollmentEventCount30.put(lineArr[0], 1);
							} else {
								enrollmentEventCount30.put(lineArr[0], enrollmentEventCount30.get(lineArr[0]) + 1);
							}
						}
					}
				}
			}
			sbf = new StringBuffer("");
			br = new BufferedReader(new FileReader("D:\\ML_final_project\\enrollment_train.csv"));
			while ((line = br.readLine()) != null) {
				lineArr = line.split(",");
				sbf.append(lineArr[0]);
				if(enrollmentEventCount15.containsKey(lineArr[0])) {
					sbf.append("," + enrollmentEventCount15.get(lineArr[0]));
				} else {
					sbf.append(",0");
				}
				if(enrollmentEventCount20.containsKey(lineArr[0])) {
					sbf.append("," + enrollmentEventCount20.get(lineArr[0]));
				} else {
					sbf.append(",0");
				}
				if(enrollmentEventCount25.containsKey(lineArr[0])) {
					sbf.append("," + enrollmentEventCount25.get(lineArr[0]));
				} else {
					sbf.append(",0");
				}
				if(enrollmentEventCount28.containsKey(lineArr[0])) {
					sbf.append("," + enrollmentEventCount28.get(lineArr[0]));
				} else {
					sbf.append(",0");
				}
				if(enrollmentEventCount30.containsKey(lineArr[0])) {
					sbf.append("," + enrollmentEventCount30.get(lineArr[0]));
				} else {
					sbf.append(",0");
				}
				sbf.append("\r\n");
			}
			FileUtil.writeFileContent("D:\\ML_final_project\\myEvent1520252830_train.csv", sbf.toString(), null, false);
			
			// log_test
			// enrollment_id,time,source,event,object
			lineCnt = 0;
			after15days = 15*24*60*60;
			after20days = 20*24*60*60;
			after25days = 25*24*60*60;
			after28days = 28*24*60*60;
			after30days = 30*24*60*60;
			enrollmentEventCount15 = new HashMap<String, Integer>();
			enrollmentEventCount20 = new HashMap<String, Integer>();
			enrollmentEventCount25 = new HashMap<String, Integer>();
			enrollmentEventCount28 = new HashMap<String, Integer>();
			enrollmentEventCount30 = new HashMap<String, Integer>();
			br = new BufferedReader(new FileReader("D:\\ML_final_project\\log_test.csv"));
			while ((line = br.readLine()) != null) {
				lineCnt ++;
				lineArr = line.split(",");
				if(!lineArr[1].equals("null")  && !lineArr[1].equals("time")) {
					time = sdf.parse(lineArr[1]);
					if(enrollmentStartTime.containsKey(lineArr[0])) {
						timeDiff = (time.getTime() - enrollmentStartTime.get(lineArr[0]).getTime()) / 1000;
						if(timeDiff > after15days) {
							if(!enrollmentEventCount15.containsKey(lineArr[0])) {
								enrollmentEventCount15.put(lineArr[0], 1);
							} else {
								enrollmentEventCount15.put(lineArr[0], enrollmentEventCount15.get(lineArr[0]) + 1);
							}
						}
						if(timeDiff > after20days) {
							if(!enrollmentEventCount20.containsKey(lineArr[0])) {
								enrollmentEventCount20.put(lineArr[0], 1);
							} else {
								enrollmentEventCount20.put(lineArr[0], enrollmentEventCount20.get(lineArr[0]) + 1);
							}
						}
						if(timeDiff > after25days) {
							if(!enrollmentEventCount25.containsKey(lineArr[0])) {
								enrollmentEventCount25.put(lineArr[0], 1);
							} else {
								enrollmentEventCount25.put(lineArr[0], enrollmentEventCount25.get(lineArr[0]) + 1);
							}
						}
						if(timeDiff > after28days) {
							if(!enrollmentEventCount28.containsKey(lineArr[0])) {
								enrollmentEventCount28.put(lineArr[0], 1);
							} else {
								enrollmentEventCount28.put(lineArr[0], enrollmentEventCount28.get(lineArr[0]) + 1);
							}
						}
						if(timeDiff > after30days) {
							if(!enrollmentEventCount30.containsKey(lineArr[0])) {
								enrollmentEventCount30.put(lineArr[0], 1);
							} else {
								enrollmentEventCount30.put(lineArr[0], enrollmentEventCount30.get(lineArr[0]) + 1);
							}
						}
					}
				}
			}
			sbf = new StringBuffer("");
			br = new BufferedReader(new FileReader("D:\\ML_final_project\\enrollment_test.csv"));
			while ((line = br.readLine()) != null) {
				lineArr = line.split(",");
				sbf.append(lineArr[0]);
				if(enrollmentEventCount15.containsKey(lineArr[0])) {
					sbf.append("," + enrollmentEventCount15.get(lineArr[0]));
				} else {
					sbf.append(",0");
				}
				if(enrollmentEventCount20.containsKey(lineArr[0])) {
					sbf.append("," + enrollmentEventCount20.get(lineArr[0]));
				} else {
					sbf.append(",0");
				}
				if(enrollmentEventCount25.containsKey(lineArr[0])) {
					sbf.append("," + enrollmentEventCount25.get(lineArr[0]));
				} else {
					sbf.append(",0");
				}
				if(enrollmentEventCount28.containsKey(lineArr[0])) {
					sbf.append("," + enrollmentEventCount28.get(lineArr[0]));
				} else {
					sbf.append(",0");
				}
				if(enrollmentEventCount30.containsKey(lineArr[0])) {
					sbf.append("," + enrollmentEventCount30.get(lineArr[0]));
				} else {
					sbf.append(",0");
				}
				sbf.append("\r\n");
			}
			FileUtil.writeFileContent("D:\\ML_final_project\\myEvent1520252830_test.csv", sbf.toString(), null, false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
