package com.sinosoft.ummp.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanMap;

/**
 * javabean工具类
 * @author Darker.Wang
 * @date 2017-1-6 20:59:45
 *
 */
public class BeanUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * 把javabean对象转化成map对象
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,String> bean2Map(Object obj){
		Map<String,String> defaultParam = new HashMap<String,String>();
		BeanMap bmap = new BeanMap(obj);
		defaultParam = bmap;
		return defaultParam;
	}

}
