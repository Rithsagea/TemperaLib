package com.rithsagea.tempera.math;

public class MathUtil {
	public static boolean within(int val, int low, int high) {
		return val >= low && val <= high;
	}
	
	public static double clamp(double val, double low, double high) {
		if(val < low) return low;
		if(val > high) return high;
		
		return val;
	}
}
