package hw1;

public class q14 {

	private static int getEval(int[] arr) {
		boolean hasA = false, hasB = false, hasC = false, hasD = false;
		for (int i = 0; i < 5; i++) {
			if (arr[i] == 0) {
				hasA = true;
			} else if (arr[i] == 1) {
				hasB = true;
			} else if (arr[i] == 2) {
				hasC = true;
			} else if (arr[i] == 3) {
				hasD = true;
			}
		}
		if ((hasA && hasB) || (hasC && hasD)) {
			return 0;
		} else {
			return 1;
		}
	}

	public static void main(String[] args) {

		int[] arr = { 0, 0, 0, 0, 0 };
		int validCnt = 0, totalCnt = 0;

		for (int i = 0; i < 4; i++) {
			arr[0] = i;
			for (int j = 0; j < 4; j++) {
				arr[1] = j;
				for (int k = 0; k < 4; k++) {
					arr[2] = k;
					for (int l = 0; l < 4; l++) {
						arr[3] = l;
						for (int m = 0; m < 4; m++) {
							arr[4] = m;
							validCnt += getEval(arr);
							totalCnt++;
						}
					}
				}

			}
		}

		System.out.println("validCnt: " + validCnt);
		System.out.println("totalCnt: " + totalCnt);

	}

}
