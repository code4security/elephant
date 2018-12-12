package com.sjhy.platform.client.utils;

public class MathUtil {
	/**
	 * Returns distance between two sets of coords
	 * 
	 * @param x1
	 *            first x coord
	 * @param y1
	 *            first y coord
	 * @param x2
	 *            second x coord
	 * @param y2
	 *            second y coord
	 * @return distance between sets of coords
	 */
	public static double getDistance(int x1, int y1, int x2, int y2)
	{
		// using long to avoid possible overflows when multiplying
		int dx = x2 - x1;
		int dy = y2 - y1;

		// return Math.hypot(x2 - x1, y2 - y1); // Extremely slow
		// return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)); // 20 times faster than hypot
		return Math.sqrt(dx * dx + dy * dy); // 10 times faster then previous line
	}

}
