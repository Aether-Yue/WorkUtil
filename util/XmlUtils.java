/**
 * 
 */
package com.sinosoft.ummp.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.ebao.accident.insurance.dto.AccidentGroupPolicyBenefitDTO;
import com.ebao.accident.insurance.dto.AccidentGroupPolicyDTO;
import com.ebao.accident.insurance.dto.AccidentGroupPolicyInsuredDTO;
import com.sinosoft.platform.core.exception.CoreException;
import com.sinosoft.platform.core.util.DateUtils;
import com.thoughtworks.xstream.XStream;


/**
 * @author Darker.Wang
 * @date 2017-10-23 下午2:24:24
 * @company SINOSOFT
 */
public class XmlUtils {

	/**
	 * @param args
	 * @throws ParseException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @throws CoreException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws ParseException, IllegalArgumentException, CoreException, ClassNotFoundException, IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		AccidentGroupPolicyDTO dto1 = new AccidentGroupPolicyDTO();
		dto1.setApplicationDate(DateUtils.getCalendar(DateUtils.getCurrentDateTime()));
		dto1.setPremium(new BigDecimal(100));
		AccidentGroupPolicyInsuredDTO[] abjs0 = new AccidentGroupPolicyInsuredDTO[10];
		AccidentGroupPolicyBenefitDTO[] objs1 = new AccidentGroupPolicyBenefitDTO[2];
		for(int i =0;i<10;i++){
			AccidentGroupPolicyInsuredDTO dto2 = new AccidentGroupPolicyInsuredDTO();
			for(int j = 0; j<2 ;j++){
				AccidentGroupPolicyBenefitDTO obj = new AccidentGroupPolicyBenefitDTO();
				obj.setBirthday(DateUtils.getCalendar(DateUtils.getCurrentDateTime()));
				obj.setCritCode("001"+(j+1));
				obj.setCritType("01");
				obj.setGender("1");
				obj.setName("测试");
				obj.setOrderNo((j+1)+"");
				obj.setRelationshipWithInsured("01");
				objs1[j] = obj;
			}
			dto2.setName("省长："+i);
			dto2.setAccidentGroupPolicyBenefitDTOs(objs1);
			abjs0[i] = dto2;
		}
		dto1.setAccidentGroupPolicyInsuredDTOs(abjs0);
		String x = bean2Xml(dto1);
		System.out.println(x);
	}

	/**
	 * 序列化到XML，默认字符集UTF-8
	 * @return
	 */
	public static String serialize2Xml(Object obj){
		if(obj != null){
			XStream xtream = new XStream();
			String xmlObj = xtream.toXML(obj);
			return xmlObj;
		}
		return null;
	}
	/* (non-Javadoc)
	 * @see com.sinosoft.ummp.application.DTOBuildService#map2Obj(java.lang.Class, java.math.BigDecimal)
	 */
	public static String bean2Xml(Object obj) throws CoreException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		StringBuffer sb = new StringBuffer();
		Class<?> clazz = obj.getClass();
		System.out.println("- dealXmlObject Class Name :　"+clazz.getName());
		sb.append("<"+clazz.getName()+">");
		//获取包括私有共有属性
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			field.setAccessible(true);
			String name = field.getName();
			Class<?> type = field.getType();
			String classNameChild = type.toString();
			System.out.println("- field type : "+type.toString()+"   field name : "+name);
			//set方法名获取
			String methodName = "get"+name.substring(0, 1).toUpperCase()+name.substring(1);
			//获取方法
			Method method = null;
			try{
				method = clazz.getMethod(methodName);
				Object tRtvObj = method.invoke(obj);
				if(tRtvObj == null){
					sb.append("<"+name+"/>");
				}else{
					if(tRtvObj instanceof org.apache.axis.description.TypeDesc){
						System.out.println("- bean2Xml: org.apache.axis.description.TypeDesc 不需要显示");
						continue;
					}
					if(tRtvObj.getClass().equals(obj.getClass())){
						System.out.println("- 自己撸自己："+obj.getClass().getName());
						continue;
					}
					sb.append("<"+name+">");
					if(classNameChild.startsWith("class [L")){
						System.out.println("- field is array and analizy it... ");
						System.out.println("- "+name+" className : "+classNameChild);
						classNameChild = classNameChild.replace("class [L", "").replace(";", "");
						// 递归处理
						List<Object> lObj = Arrays.asList(tRtvObj);
						for(Object o : lObj){
							Object[] os = (Object[]) o;
							for(Object tos : os){
								sb.append(getValue(tos));	
							}
						}
					}else{
						sb.append(getValue(tRtvObj));
					}
					sb.append("</"+name+">");
				}
			}catch(NoSuchMethodException ne){
				System.out.println("- obj class ："+clazz.getName()+" 无方法："+methodName+" 继续");
				continue;
			}
			catch(Exception e){
				e.printStackTrace();
				CoreException ce = new CoreException();
				String msg = "对象："+clazz.getName()+" 执行方法："+methodName+" 异常 ";
				ce.setMessage(msg);
				throw ce;
			}
		}
		sb.append("</"+clazz.getName()+">");
		return sb.toString();
	}
	/**
	 * 获取对应对象的值
	 * @param tRtvObj
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws ClassNotFoundException 
	 * @throws CoreException 
	 * @throws IllegalArgumentException 
	 */
	private static String getValue(Object tRtvObj) throws IllegalArgumentException, CoreException, ClassNotFoundException, IllegalAccessException, InvocationTargetException{
		if(tRtvObj == null){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		// tRtvObj 是一个单一的对象
		if(tRtvObj instanceof java.lang.String){
			sb.append(tRtvObj.toString());
		}
		else if(tRtvObj instanceof java.util.Calendar){
			java.util.Calendar cal = (Calendar) tRtvObj;
			Date date = cal.getTime();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String value = format.format(date);
			sb.append(value);
		}
		else if(tRtvObj instanceof java.util.Date){
			java.util.Date date = (Date) tRtvObj;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String value = format.format(date);
			sb.append(value);
		}
		else if(tRtvObj instanceof java.lang.Boolean){
			sb.append(Boolean.parseBoolean(tRtvObj.toString()));
		}
		else if(tRtvObj instanceof java.lang.Short){
			sb.append(Short.parseShort(tRtvObj.toString()));
		}
		else if(tRtvObj instanceof java.lang.Integer){
			sb.append(Integer.getInteger(tRtvObj.toString()));
		}
		else if(tRtvObj instanceof java.math.BigDecimal){
			sb.append(new java.math.BigDecimal(tRtvObj.toString()));
		}
		else if(tRtvObj instanceof java.lang.Double){
			sb.append(Double.parseDouble(tRtvObj.toString()));
		}
		else if(tRtvObj instanceof java.lang.Float){
			sb.append(Float.parseFloat(tRtvObj.toString()));
		}
		else if(tRtvObj instanceof java.lang.Byte){
			sb.append(Byte.parseByte(tRtvObj.toString()));
		}
		else{
			sb.append(bean2Xml(tRtvObj));
		}
		return sb.toString();
	}
}