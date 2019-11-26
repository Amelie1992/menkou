package com.wzlue.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 编码规则
 */
public class NumberUtils {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

	public static String getOrderNumder() {
		String newDate = sdf.format(new Date());
		String result = "";
		Random random = new Random();
		for (int i = 0; i < 3; i++) {
			result += random.nextInt(10);
		}
		return newDate + result;
	}

	public static void main(String[] args) {
		System.out.println(getOrderNumder());
	}

}
