import java.io.*;
import java.util.*;
import java.lang.*;

/* Represents an instance or weight vector and contains
 * functionality
 */

public class Example {
	public Hashtable values;
	public double classification;
	public double bias;
	public double eucSize;

	// Constructor for an instance
	public Example() {
		values = new Hashtable();
	}

	// Constructor for weight vector w
	public Example(int n) {
		values = new Hashtable();
		for (int i = 0; i < n; i++) {
			values.put(i, new Double(0));
		}
		bias = 0;
	}

	// Add a feature/value pair to the vector and update eucSize
	public void addPair(double featureNumber, double val) {
		values.put((int) featureNumber, new Double(val));
		eucSize = euclideanSize();

	}

	public void setClassification(int cl) {
		classification = cl;
	}

	public double getClassification() {
		return classification;
	}

	public void setBias(double b) {
		bias = b;
	}

	public double getBias() {
		return bias;
	}

	public int getSize() {
		return values.size();
	}

	// Set feature f to v, removing previous f entry if necessary
	public void setPair(int f, double v) {
		if (values.containsKey(f)) {
			values.remove(f);
		}
		values.put(f, v);
		eucSize = euclideanSize();
	}

	// Return an int array of all features used by this vector
	public int[] keyArray() {

		Object[] myObjs = values.keySet().toArray();
		int n = myObjs.length;
		int[] myInts = new int[n];

		for (int i = 0; i < n; i++) {
			myInts[i] = ((Integer) myObjs[i]).intValue();
		}
		return myInts;
	}

	// Return the euclidean size of this vector
	public double euclideanSize() {
		Enumeration myKeys = values.keys();
		double sumVal = 0;
		while (myKeys.hasMoreElements()) {
			Object o = myKeys.nextElement();
			Double dO = (Double) values.get(o);
			double d = dO.doubleValue();
			sumVal = sumVal + Math.pow(d, 2);
		}
		sumVal = Math.pow(sumVal, .5);
		return sumVal;
	}

	// Return the value of stored of feature f,
	// return 0 if there is no entry
	public double getValueByFeature(int f) {
		Object o = values.get(f);
		if (o == null) {
			return 0;
		} else {
			return ((Double) (o)).doubleValue();
		}
	}

	public String toString() {
		return "Class " + classification + " euclidean " + eucSize;
	}
}
