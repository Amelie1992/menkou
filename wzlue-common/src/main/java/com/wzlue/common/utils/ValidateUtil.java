/**
 * Copyright (C) 2018 FZJT Co. Ltd.
 *
 *
 * @className:com.wzlue.ask.common.utils.ValidateUtil
 * @description:
 * 
 * @version:v1.0.0 
 * @author:QianTao
 * 
 * Modification History:
 * Date         Author      Version     Description
 * -----------------------------------------------------------------
 * 2018年11月27日    	QianTao  	v1.0.0        create
 *
 *
 */
package com.wzlue.common.utils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * java校验工具类
 * 
 * @className:com.wzlue.ask.common.utils.ValidateUtil
 * @description:
 * @version:v1.0.0
 * @date:2018年11月27日 上午9:36:15
 * @author:QianTao
 */
public class ValidateUtil
{
	// 身份证号
	public static String idCardValidate = "^^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X|x)$";
	// 手机号码
	public static String telephoneValidate = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
	// 邮箱
	public static String emailValidate = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
	// IP地址：
	public static String IPValidate = "\\d+\\.\\d+\\.\\d+\\.\\d+";
	// 零和非零开头的数字：
	public static String intValidate = "^(0|[1-9][0-9]*)$";
	// 非零开头的最多带两位小数的数字：
	public static String doubleValidate = "^([1-9][0-9]*)+(.[0-9]{1,2})?$";
	// 必须是中文
	public static String isChineseValidate = "[\\u3400-\\u9FBF]+";

	/**
	 * 非空校验
	 * 
	 * @Description:
	 * @param str
	 * @return
	 * @version:v1.0
	 * @author:QianTao
	 * @date:2018年11月27日 上午9:45:50
	 */
	public static boolean isNullOrEmpty(Object obj)
	{
		if (null == obj || "".equals(obj))
		{
			return true;
		}
		return false;
	}

	/**
	 * 正则校验
	 * 
	 * @Description:
	 * @param str
	 *            校验字段
	 * @param reg
	 *            正则
	 * @return
	 * @version:v1.0
	 * @author:QianTao
	 * @date:2018年11月27日 下午1:26:58
	 */
	public static boolean patternValidate(String str, String reg)
	{
		Pattern pattern = Pattern.compile(reg);// 正则验证
		if (isNullOrEmpty(str))
		{
			return false;
		}
		if (pattern.matcher(str.trim()).find())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * 校验字符串长度
	 * @Description:
	 * @param str
	 * @param maxLength
	 * @return
	 * @version:v1.0
	 * @author:QianTao
	 * @date:2018年11月27日 下午1:34:53
	 */
	public static boolean validateLength(String str,int maxLength) 
	{
		int strLenght=StringLength(str);
		if(strLenght>maxLength) 
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	/**
	 * 计算字符串长度，中文占两个字符
	 * 
	 * @Description:
	 * @param value
	 * @return
	 * @version:v1.0
	 * @author:QianTao
	 * @date:2018年11月27日 下午1:28:01
	 */
	public static int StringLength(String value)
	{
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		for (int i = 0; i < value.length(); i++)
		{
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese))
			{
				valueLength += 2;
			}
			else
			{
				valueLength += 1;
			}
		}
		return valueLength;
	}
	
	public static boolean isContain (List<String> list ,String value)
	{
	    for(String s:list)
	    {
	        if(s.toString().equalsIgnoreCase(value))
	            return true;
	    }
	    return false;
	}
}
