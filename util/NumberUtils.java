package com.sinosoft.platform.core.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

/**
 * 工具类（数字工具）
 * @author Darker.Wang
 * @date 2016-8-11 下午5:41:29
 * @company SINOSOFT
 */
public class NumberUtils extends org.springframework.util.NumberUtils {

	/**
	 * 设置数字精度 需要格式化的数据
	 * 
	 * @param value
	 *            BigDecimal
	 * @param precision
	 *            String 精度描述（0.00表示精确到小数点后两位）
	 * @return BigDecimal
	 */
	public static BigDecimal setPrecision(BigDecimal bigDecimal,
			String precision) {
		return new BigDecimal(new DecimalFormat(precision).format(bigDecimal));
	}

	/**
	 * 设置数字精度 需要格式化的数据
	 * 
	 * @param value
	 *            double
	 * @param precision
	 *            String 精度描述（0.00表示精确到小数点后两位)
	 * @return double
	 */
	public static double setPrecision(double value, String precision) {
		return Double.parseDouble(new DecimalFormat(precision).format(value));
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static double roundDouble(double value, int scale) {

		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal b = new BigDecimal(Double.toString(value));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param v
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static BigDecimal round(double value, int scale) {

		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal b = new BigDecimal(Double.toString(value));
		BigDecimal one = new BigDecimal("1");
		return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 提供精确的小数位四舍五入处理。
	 * 
	 * @param value
	 *            需要四舍五入的数字
	 * @param scale
	 *            小数点后保留几位
	 * @return 四舍五入后的结果
	 */
	public static BigDecimal round(BigDecimal value, int scale) {

		if (scale < 0) {
			throw new IllegalArgumentException(
					"The scale must be a positive integer or zero");
		}

		BigDecimal one = new BigDecimal("1");
		return value.divide(one, scale, BigDecimal.ROUND_HALF_UP);
	}

	/**
	 * 将数字前补零，长度为size 若size<obj.length 返回本身
	 * 
	 * @param obj
	 * @param size
	 * @return
	 */
	public static String getNumber(int obj, int size) {
		int length = ("" + obj).length();
		if (size <= length) {
			return "" + obj;
		}
		String rtvObj = "";
		for (int i = 0; i < size - length; i++) {
			rtvObj += "0";
		}
		rtvObj = rtvObj + obj;
		return rtvObj;
	}
	/**
	 * 将数字前补零，长度为size 若size<obj.length 返回本身
	 * @param obj
	 * @param size
	 * @return
	 */
	public static String getNumber(String obj, int size) {
		int length = obj.length();
		if (size <= length) {
			return obj;
		}
		StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < size - length; i++) {
			sb.append("0");
		}
		sb.append(obj);
		return sb.toString();
	}
	/**
	 * 获取阿拉伯数字和中文数字的对应关系
	 * 
	 * @param value
	 *            String
	 * @return String
	 */
	public static String getNumCH(String value) {
		String sNum = "";
		Integer I = new Integer(value);
		int iValue = I.intValue();
		switch (iValue) {
		case 0:
			sNum = "零";
			break;
		case 1:
			sNum = "壹";
			break;
		case 2:
			sNum = "贰";
			break;
		case 3:
			sNum = "叁";
			break;
		case 4:
			sNum = "肆";
			break;
		case 5:
			sNum = "伍";
			break;
		case 6:
			sNum = "陆";
			break;
		case 7:
			sNum = "柒";
			break;
		case 8:
			sNum = "捌";
			break;
		case 9:
			sNum = "玖";
			break;
		}
		return sNum;
	}

	/**
	 * 如果一个字符串数字中小数点后全为零，则去掉小数点及零
	 * 
	 * @param Value
	 *            String
	 * @return String
	 */
	public static String getInt(String Value) {
		if (Value == null) {
			return null;
		}
		String result = "";
		boolean mflag = true;
		int m = 0;
		m = Value.lastIndexOf(".");
		if (m == -1) {
			result = Value;
		} else {
			for (int i = m + 1; i <= Value.length() - 1; i++) {
				if (Value.charAt(i) != '0') {
					result = Value;
					mflag = false;
					break;
				}
			}
			if (mflag) {
				result = Value.substring(0, m);
			}
		}
		return result;
	}

	/**
	 * 得到近似值
	 * 
	 * @param aValue
	 *            double
	 * @return double
	 */
	public static double getApproximation(double aValue) {
		if (java.lang.Math.abs(aValue) <= 0.01) {
			aValue = 0;
		}
		return aValue;
	}

	/**
	 * 获取指定长度的随机数
	 * @param length 大于等于 0 有效,小于等于 0 则返回 -1
	 * @return
	 */
	public static String getRandom(int length){
		if(length <= 0){
			return "-1";
		}
		Random random = new Random();
		StringBuffer sb = new StringBuffer("");
		for(int i = 0; i< length; i++){
			sb.append(random.nextInt(10));
		}
		random = null;
		return sb.toString();
	}
	/**
	 * 获取系统自动序列号：SN-系统当前时间戳四位随机数
	 * @return
	 */
	public static String getSerialNo(){
		return "SN-"+getTimeSerialNo();
	}
	/**
	 * 获取时间序列号yyyymmddhhmmss+四位随机数
	 * @return
	 */
	public static String getTimeSerialNo(){
		return DateUtils.getCurrentDateAndTime()+getRandom(4);
	}
	public static void main(String[] args) {
//		System.out.println(getNumber("1234",10));
//		System.out.println(getNumCH("1"));
		System.out.println(getRandom(10));	
		
	}
}
