package com.sinosoft.platform.core.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.DecimalFormat;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

/**
 * StringUtils 继承  org.springframework.util.StringUtils
 * @author Darker.Wang
 * @date 2016-8-11 下午5:44:25
 * @company SINOSOFT
 */
public class StringUtils  extends org.springframework.util.StringUtils{

	/**
	 * 该函数得到c_Str中的第c_i个以c_Split分割的字符串
	 * 
	 * @param c_Str
	 *            目标字符串
	 * @param c_i
	 *            位置
	 * @param c_Split
	 *            分割符
	 * @return 如果发生异常，则返回空
	 */
	public static String getStr(String c_Str, int c_i, String c_Split) {
		String t_Str1 = "";
		String t_Str2 = "";
		String t_strOld = "";
		int i = 0;
		int i_Start = 0;
		t_Str1 = c_Str;
		t_Str2 = c_Split;
		i = 0;
		try {
			while (i < c_i) {
				i_Start = t_Str1.indexOf(t_Str2, 0);
				if (i_Start >= 0) {
					i += 1;
					t_strOld = t_Str1;
					t_Str1 = t_Str1.substring(i_Start + t_Str2.length(), t_Str1
							.length());
				} else {
					if (i != c_i - 1) {
						t_Str1 = "";
					}
					break;
				}
			}

			if (i_Start >= 0) {
				t_Str1 = t_strOld.substring(0, i_Start);
			}
		} catch (Exception ex) {
			t_Str1 = "";
		}
		return t_Str1;
	}
	
	
	 /**
	 * 字符串替换函数，替换时不区分大小写
	 * 
	 * @param strMain
	 *            String 原串
	 * @param strFind
	 *            String 查找字符串
	 * @param strReplaceWith
	 *            String 替换字符串,在替换时不区分大小写
	 * @return String 替换后的字符串，如果原串为空或者为""，则返回""
	 */
    public static String replaceEx(String strMain, String strFind, String strReplaceWith)
    {
        if (strMain == null || strMain.equals(""))
        {
            return "";
        }

        StringBuffer tSBql = new StringBuffer();
		// 小写化仅仅是为了确定替换的位置
        String tStrMain = strMain.toLowerCase();
        String tStrFind = strFind.toLowerCase();

        int intStartIndex = 0;
        int intEndIndex = 0;

        while ((intEndIndex = tStrMain.indexOf(tStrFind, intStartIndex)) > -1)
        {
			// 拼凑真实sql
            tSBql.append(strMain.substring(intStartIndex, intEndIndex));
			// 替换变量为查询值
            tSBql.append(strReplaceWith);

            intStartIndex = intEndIndex + strFind.length();
        }
        tSBql.append(strMain.substring(intStartIndex, strMain.length()));

        return tSBql.toString();
    }


	/**
	 * 把数字金额转换为中文大写金额 author: HST
	 * 
	 * @param money
	 *            数字金额(double)
	 * @return 中文大写金额(String)
	 */
	public static String getChnMoney(double money) {
		String ChnMoney = "";
		String s0 = "";

		// 在原来版本的程序中，getChnMoney(585.30)得到的数据是585.29。

		if (money == 0.0) {
			ChnMoney = "人民币零元整";
			return ChnMoney;
		}

		if (money < 0) {
			s0 = "负";
			money *= (-1);
		}

		String sMoney = new DecimalFormat("0").format(money * 100);

		int nLen = sMoney.length();
		String sInteger;
		String sDot;
		if (nLen < 2) {
			// add by JL at 2004-9-14
			sInteger = "";
			if (nLen == 1) {
				sDot = "0" + sMoney.substring(nLen - 1, nLen);
			} else {
				sDot = "0";
			}
		} else {
			sInteger = sMoney.substring(0, nLen - 2);
			sDot = sMoney.substring(nLen - 2, nLen);
		}

		String sFormatStr = formatStr(sInteger);

		String s1 = getChnM(sFormatStr.substring(0, 4), "亿");

		String s2 = getChnM(sFormatStr.substring(4, 8), "万");

		String s3 = getChnM(sFormatStr.substring(8, 12), "");

		String s4 = getDotM(sDot);

		if (s1.length() > 0 && s1.substring(0, 1).equals("0")) {
			s1 = s1.substring(1, s1.length());
		}
		if (s1.length() > 0
				&& s1.substring(s1.length() - 1, s1.length()).equals("0")
				&& s2.length() > 0 && s2.substring(0, 1).equals("0")) {
			s1 = s1.substring(0, s1.length() - 1);
		}
		if (s2.length() > 0
				&& s2.substring(s2.length() - 1, s2.length()).equals("0")
				&& s3.length() > 0 && s3.substring(0, 1).equals("0")) {
			s2 = s2.substring(0, s2.length() - 1);
		}
		if (s4.equals("00")) {
			s4 = "";
			if (s3.length() > 0
					&& s3.substring(s3.length() - 1, s3.length()).equals("0")) {
				s3 = s3.substring(0, s3.length() - 1);
			}
		}
		if (s3.length() > 0
				&& s3.substring(s3.length() - 1, s3.length()).equals("0")
				&& s4.length() > 0 && s4.substring(0, 1).equals("0")) {
			s3 = s3.substring(0, s3.length() - 1);
		}
		if (s4.length() > 0
				&& s4.substring(s4.length() - 1, s4.length()).equals("0")) {
			s4 = s4.substring(0, s4.length() - 1);
		}
		if (s3.equals("0")) {
			s3 = "";
			s4 = "0" + s4;
		}

		ChnMoney = s0 + s1 + s2 + s3 + "元" + s4;
		if (ChnMoney.substring(0, 1).equals("0")) {
			ChnMoney = ChnMoney.substring(1, ChnMoney.length());
		}
		for (int i = 0; i < ChnMoney.length(); i++) {
			if (ChnMoney.substring(i, i + 1).equals("0")) {
				ChnMoney = ChnMoney.substring(0, i) + "零"
						+ ChnMoney.substring(i + 1, ChnMoney.length());
			}
		}

		if (sDot.substring(1, 2).equals("0")) {
			ChnMoney += "整";
		}

		return ChnMoney;
	}

	/**
	 * 得到money的角分信息
	 * 
	 * @param sIn
	 *            String
	 * @return String
	 */
	private static String getDotM(String sIn) {
		String sMoney = "";
		if (!sIn.substring(0, 1).equals("0")) {
			sMoney += getNum(sIn.substring(0, 1)) + "角";
		} else {
			sMoney += "0";
		}
		if (!sIn.substring(1, 2).equals("0")) {
			sMoney += getNum(sIn.substring(1, 2)) + "分";
		} else {
			sMoney += "0";
		}

		return sMoney;
	}

	/**
	 * 添加仟、佰、拾等单位信息
	 * 
	 * @param strUnit
	 *            String
	 * @param digit
	 *            String
	 * @return String
	 */
	private static String getChnM(String strUnit, String digit) {
		String sMoney = "";
		boolean flag = false;

		if (strUnit.equals("0000")) {
			sMoney += "0";
			return sMoney;
		}
		if (!strUnit.substring(0, 1).equals("0")) {
			sMoney += getNum(strUnit.substring(0, 1)) + "仟";
		} else {
			sMoney += "0";
			flag = true;
		}
		if (!strUnit.substring(1, 2).equals("0")) {
			sMoney += getNum(strUnit.substring(1, 2)) + "佰";
			flag = false;
		} else {
			if (!flag) {
				sMoney += "0";
				flag = true;
			}
		}
		if (!strUnit.substring(2, 3).equals("0")) {
			sMoney += getNum(strUnit.substring(2, 3)) + "拾";
			flag = false;
		} else {
			if (!flag) {
				sMoney += "0";
				flag = true;
			}
		}
		if (!strUnit.substring(3, 4).equals("0")) {
			sMoney += getNum(strUnit.substring(3, 4));
		} else {
			if (!flag) {
				sMoney += "0";
				flag = true;
			}
		}

		if (sMoney.substring(sMoney.length() - 1, sMoney.length()).equals("0")) {
			sMoney = sMoney.substring(0, sMoney.length() - 1) + digit.trim()
					+ "0";
		} else {
			sMoney += digit.trim();
		}
		return sMoney;
	}

	/**
	 * 格式化字符
	 * 
	 * @param sIn
	 *            String
	 * @return String
	 */
	private static String formatStr(String sIn) {
		int n = sIn.length();
		String sOut = sIn;
		// int i = n % 4;

		for (int k = 1; k <= 12 - n; k++) {
			sOut = "0" + sOut;
		}
		return sOut;
	}

	/**
	 * 获取阿拉伯数字和中文数字的对应关系
	 * 
	 * @param value
	 *            String
	 * @return String
	 */
	private static String getNum(String value) {
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

	// 金额千分位格式化
	public static String fmtMicrometer(String text) {
		DecimalFormat df = null;
		if (text.indexOf(".") > 0) {
			if (text.length() - text.indexOf(".") - 1 == 0) {
				df = new DecimalFormat("###,##0.");
			} else if (text.length() - text.indexOf(".") - 1 == 1) {
				df = new DecimalFormat("###,##0.0");
			} else {
				df = new DecimalFormat("###,##0.00");
			}
		} else {
			df = new DecimalFormat("###,##0");
		}
		double number = 0.0;
		try {
			number = Double.parseDouble(text);
		} catch (Exception e) {
			number = 0.0;
		}
		return df.format(number);
	}

	/**
	 * 字符串替换指定字符
	 * 
	 * @param target
	 *            目标字符串
	 * @param start
	 *            开始位置
	 * @param end
	 *            结束位置
	 * @param code
	 *            字符
	 * @return String
	 */
	public static String replaceWithSpecialChar(String target, int start,
			int end, char code) {
		if (target != null && end > start && start < target.length()) {
			end = end > target.length() ? target.length() : end;
			String subStr = "";
			for (int i = start; i < end; i++) {
				subStr = subStr + code;
			}
			target = target.substring(0, start) + subStr
					+ target.subSequence(end, target.length());
		}
		return target;
	}
	/**
	 * JSON 数据格式化输出
	 * @param msg
	 * @return
	 */
	public static String formatJSON(String msg){
		if(msg == null || msg.equals("")){
			return "";
		}
		StringBuilder sb = new StringBuilder();
		char last = '\0';
		char current = '\0';
		int indent = 0;
		for(int i = 0; i < msg.length(); i++){
			last = current;
			current = msg.charAt(i);
			switch(current){
			case '{':
			case '[':
				sb.append(current);
				sb.append('\n');
				indent ++;
				addIndentBlank(sb,indent);
				break;
			case '}':
			case ']':
				sb.append('\n');
				indent --;
				addIndentBlank(sb,indent);
				sb.append(current);
				break;
			case ',':
				sb.append(current);
				if(last != '\\'){
					sb.append('\n');
					addIndentBlank(sb,indent);	
				}
				break;
			default :
				sb.append(current);
				break;
			}
		}
		return sb.toString();
	}
	private static void addIndentBlank(StringBuilder sb,int indent){
		for(int i = 0; i<indent; i++){
			sb.append("\t");
		}
	}
	/**
	 * XML 数据格式化输出
	 * @param msg
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public static String formatXML(String msg) throws DocumentException, IOException{
		Document tDoc = DocumentHelper.parseText(msg);
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		StringWriter sw = new StringWriter();
		XMLWriter xmlWriter = new XMLWriter(sw,format);
		xmlWriter.write(tDoc);
		sw.close();
		return sw.toString();
	}
	/**
	 * 将流转换成字符串,默认字符集 UTF-8
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String getString(InputStream in) throws IOException{
		ByteArrayOutputStream bao  = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		while((len = in.read(data)) != -1){
			bao.write(data, 0, len);
		}
		String rtvStr = new String(bao.toByteArray(),"UTF-8");
		bao.close();
		bao = null;
		return rtvStr;
	}
	/**
	 * 将流转换成字符串,制定字符集
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String getString(InputStream in,String encoding) throws IOException{
		ByteArrayOutputStream bao  = new ByteArrayOutputStream();
		byte[] data = new byte[1024];
		int len = 0;
		while((len = in.read(data)) != -1){
			bao.write(data, 0, len);
		}
		String rtvStr = new String(bao.toByteArray(),encoding);
		bao.close();
		bao = null;
		return rtvStr;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(fmtMicrometer("123444.22"));

	}

}
