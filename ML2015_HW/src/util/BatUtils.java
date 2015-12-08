package util;
import java.io.IOException;
import java.io.InputStream;

public class BatUtils {
	
	public static void run(String batPath){
		try {
			Runtime runtime = Runtime.getRuntime();
			Process p1 = runtime.exec("cmd /c start " + batPath);
		    InputStream is = p1.getInputStream();
		    int i = 0;
		    while( (i = is.read() ) != -1) {
		    	System.out.println((char)i);
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
}
