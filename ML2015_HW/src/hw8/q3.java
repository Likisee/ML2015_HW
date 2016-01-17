package hw8;

public class q3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int max = 0;
		
		max = Integer.MIN_VALUE;
		for(int x = 1; x <= 36; x++) {
			int count = 10 * x + x * 1;
			if(count > max) {
				max = count;
//				System.out.println("x = " + x);
//				System.out.println(count);
			}
		}
		System.out.println(max);
		
		max = Integer.MIN_VALUE;
		for(int x = 1; x <= 36; x++) {
			for(int y = 1; y <= 36 - x; y++) {
				int count = 10 * x + x * y + y * 1;
				if(count > max) {
					max = count;
//					System.out.println("x = " + x + ", y = " + y);
//					System.out.println(count);
				}
			}
		}
		System.out.println(max);

		max = Integer.MIN_VALUE;
		for(int x = 1; x <= 36; x++) {
			for(int y = 1; y <= 36 - x; y++) {
				for(int z = 1; z <= 36 - x - y; z++) {
					int count = 10 * x + x * y + y * z + z * 1;
					if(count > max) {
						max = count;
//						System.out.println("x = " + x + ", y = " + y + ", z = " + z);
//						System.out.println(count);
					}
				}
			}
		}
		System.out.println(max);
		
		
		max = Integer.MIN_VALUE;
		for(int x = 1; x <= 36; x++) {
			for(int y = 1; y <= 36 - x; y++) {
				for(int z = 1; z <= 36 - x - y; z++) {
					for(int z0 = 1; z0 <= 36 - x - y - z; z0++) {
						int count = 10 * x + x * y + y * z + z * z0 + z0 * 1;
						if(count > max) {
							max = count;
//							System.out.println("x = " + x + ", y = " + y + ", z = " + z + ", z0 = " + z0);
//							System.out.println(count);
						}
					}
				}
			}
		}
		System.out.println(max);
		
		max = Integer.MIN_VALUE;
		for(int x = 1; x <= 36; x++) {
			for(int y = 1; y <= 36 - x; y++) {
				for(int z = 1; z <= 36 - x - y; z++) {
					for(int z0 = 1; z0 <= 36 - x - y - z; z0++) {
						for(int z1 = 1; z1 <= 36 - x - y - z - z0; z1++) {
							int count = 10 * x + x * y + y * z + z * z0 + z0 * z1 + z1 * 1;
							if(count > max) {
								max = count;
							}
						}
					}
				}
			}
		}
		System.out.println(max);
	}

}
