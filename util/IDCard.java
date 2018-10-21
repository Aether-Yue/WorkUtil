/**
 * 
 */
package com.sinosoft.platform.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 中华人民共和国身份证工具类
 * @version 1.0
 * @author Darker.Wang
 * @date 2017-2-9 上午11:04:15
 * @company SINOSOFT
 */
public class IDCard {
	/**
	 * 存放校验码
	 */
	public Map<String,String> modMap = new HashMap<String,String>();
	/**
	 * 存放权值码
	 */
	public List<Integer> power = new ArrayList<Integer>();
	private String msg = null;
	/**
	 * 获取校验信息
	 * @return
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * 设置校验信息
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public IDCard() {
		// TODO Auto-generated constructor stub
		modMap.put("0", "1");
		modMap.put("1", "0");
		modMap.put("2", "X");
		modMap.put("3", "9");
		modMap.put("4", "8");
		modMap.put("5", "7");
		modMap.put("6", "6");
		modMap.put("7", "5");
		modMap.put("8", "4");
		modMap.put("9", "3");
		modMap.put("10", "2");
		power.add(7);
		power.add(9);
		power.add(10);
		power.add(5);
		power.add(8);
		power.add(4);
		power.add(2);
		power.add(1);
		power.add(6);
		power.add(3);
		power.add(7);
		power.add(9);
		power.add(10);
		power.add(5);
		power.add(8);
		power.add(4);
		power.add(2);
	}
	/**
	 * 校验身份证是否正确，调用getMsg()获得校验结果
	 * @param idNo
	 * @return
	 */
	public boolean isCard(String idNo) {
		if (idNo == null || "".equals(idNo)) {
			msg = "NO IDNO";
			System.out.println("- "+msg);
			return false;
		}
		if(idNo.length() != 15 && idNo.length() != 18){
			msg = "LENGTH IS WRONG";
			System.out.println("- IDNO: "+idNo+" "+msg);
			return false;
		}
		//15 位身份证校验规则
		Pattern p15 = Pattern
				.compile("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$");
		//18位身份证校验规则
		Pattern p18 = Pattern
				.compile("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$");
		if (idNo.length() == 15) {
			Matcher matcher = p15.matcher(idNo);
			boolean result = matcher.matches();
			if(result){
				showIDInfo(idNo);
			}else{
				msg = "不是有效15位身份证";
				System.out.println("- IDNO: "+idNo+" "+msg);
			}
			return result;
		}
		if (idNo.length() == 18) {
			Matcher matcher = p18.matcher(idNo);
			boolean result = matcher.matches();
			if(result){
				result = showIDInfo(idNo);
			}else{
				msg = "不是有效18位身份证";
				System.out.println("- IDNO: "+idNo+" "+msg);
			}
			return result;
		}
		msg = "NOT SUPORTED";
		System.out.println("- IDNO: "+idNo+" "+msg);
		return false;
	}
	/**
	 * 获取身份证校验码：18位身份证可用
	 * @param idNo
	 * @return
	 */
	public String getCode(String idNo){
		char[] idChars = idNo.toCharArray();
		int sum = 0;
		for(int i = 0; i < idChars.length-1; i++){
			String idc = new String(""+idChars[i]);
			int a = Integer.parseInt(idc);
			int b = power.get(i);
			sum += a*b;
		}
		int mode = sum % 11;
		String code = modMap.get(mode+"");
		return code;
	}
	/**
	 * 显示身份证信息
	 * @param idNo
	 * @return
	 */
	public boolean showIDInfo(String idNo){
		if(idNo.length() == 15){
			String birthDay = "19"+idNo.substring(6, 12);
			String sex = idNo.substring(14,15);
			msg = idNo+","+sexFormat(sex)+","+birthFormat(birthDay);
			System.out.println("  "+msg);
			return true;
		}
		if(idNo.length() == 18){
			String birthDay = idNo.substring(6, 14);
			String sex = idNo.substring(16, 17);
			String code = idNo.substring(17);
			String realCode = getCode(idNo);
			String ok = "有效";
			boolean check = realCode.equals(code);
			if(!check){
				ok = "无效";
			}
			msg = idNo+","+sexFormat(sex)+","+birthFormat(birthDay)+",验证码:"+code+",状态:"+ok;
			System.out.println("  "+msg);
			if(!check){
				msg = msg+",最后一位校验码应该为:"+realCode;
				System.out.println("- 最后一位校验码不对，应该为："+realCode+"\n");
				return false;
			}
			return true;
		}
		return false;
	}
	/**
	 * 生日格式化
	 * @param birthDay
	 * @return
	 */
	private String birthFormat(String birthDay ){
		if(birthDay.contains("-")){
			return birthDay;
		}
		if(birthDay.contains("/")){
			return birthDay.replace("/", "-");
		}
		try{
			return birthDay.substring(0, 4)+"-"+birthDay.substring(4, 6)
					+"-"+birthDay.substring(6);
		}catch(Exception e){
			msg = "日期不符规则";
			System.out.println("- "+msg);
		}
		return birthDay;
	}
	/**
	 * 性别格式化
	 * @param sex
	 * @return
	 */
	private String sexFormat(String sex ){
		if(sex == null || "".equals(sex)){
			return "未知";
		}
		if(Integer.parseInt(sex) % 2 == 0){
			return "女";
		}
		return "男";
	}

}
