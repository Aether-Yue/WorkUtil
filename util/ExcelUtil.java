package com.sinosoft.platform.core.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * Excel 操作工具类
 * 
 * @author Darker.Wang
 * @date 2016-8-11 下午5:32:55
 * @company SINOSOFT
 */
public class ExcelUtil {
	int mapIndex = 0;

	/**
	 * @Title: exportExcel
	 * @Description: 导出Excel的方法
	 * @param workbook
	 * @param sheetNum
	 *            (sheet的位置，0表示第一个表格中的第一个sheet)
	 * @param sheetTitle
	 *            （sheet的名称）
	 * @param headers
	 *            （表格的标题）
	 * @param result
	 *            （表格的数据）
	 * @param out
	 *            （输出流）
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void exportExcel(HSSFWorkbook workbook, int sheetNum,
			String sheetTitle, String[] headers, List<Map> result,
			OutputStream out) throws Exception {
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(sheetNum, sheetTitle);
		// 设置表格默认列宽度为20个字节
		sheet.setDefaultColumnWidth(20);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		// style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		// font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		// style.setFont(font);

		// 指定当单元格内容显示不下时自动换行
		style.setWrapText(true);

		// 产生表格标题行
		HSSFRow row = sheet.createRow(0);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellStyle(style);
			HSSFRichTextString text = new HSSFRichTextString(headers[i]);
			cell.setCellValue(text.toString());
		}
		// 遍历集合数据，产生数据行
		if (result != null) {
			// int rowIndex=1;
			// int cellIndex=0;
			for (int i = 0; i < result.size(); i++) {
				row = sheet.createRow(i + 1);
				Map<String, Object> map = result.get(i);
				for (int j = 0; j < map.size(); j++) {
					HSSFCell cell = row.createCell(j);
					if (map.get(headers[j]) == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(map.get(headers[j]) + "");
					}
				}
				mapIndex++;
			}
		}
	}
	/**
	 * 报表导出同构方法
	 * @param workbook
	 * @param sheetNum
	 * @param sheetName
	 * @param results
	 * @param out
	 * @throws Exception
	 */
	public void exportExcel(HSSFWorkbook workbook, int sheetNum,
			String sheetName,List<Object[]> results,OutputStream out) throws Exception {
		// 生成一个表格
		HSSFSheet sheet = workbook.createSheet();
		workbook.setSheetName(sheetNum, sheetName);
		// 设置表格默认列宽度为20个字节
		sheet.setDefaultColumnWidth(16);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式:FillPattern+FillForegroundColor一起构成设置背景颜色
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		font.setColor(HSSFFont.COLOR_NORMAL);
		font.setFontHeightInPoints((short) 10);
//		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("微软雅黑");
		// 把字体应用到当前的样式
		style.setFont(font);
		// 指定当单元格内容显示不下时自动换行
		style.setWrapText(false);
		
		// 生成一个Head样式
		HSSFCellStyle styleHead = workbook.createCellStyle();
		HSSFFont fontHead = workbook.createFont();
		fontHead.setFontHeightInPoints((short)10);
		fontHead.setColor(HSSFFont.COLOR_NORMAL);
		fontHead.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		fontHead.setFontName("微软雅黑");
		styleHead.setFont(fontHead);
		//FillPattern+FillForegroundColor一起构成设置背景颜色
		styleHead.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		styleHead.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		styleHead.setBorderTop(HSSFCellStyle.BORDER_THIN);
		styleHead.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		styleHead.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		styleHead.setBorderRight(HSSFCellStyle.BORDER_THIN);
		styleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 产生表格标题行
//		HSSFRow row = sheet.createRow(0);
		// 遍历集合数据，产生数据行
		if (results != null) {
			HSSFRow row = null;
			for (int r = 0; r < results.size(); r++) {
				row = sheet.createRow(r);
				Object[] rowData = results.get(r);
				for (int c = 0; c < rowData.length; c++) {
					HSSFCell cell = row.createCell(c);
					if (rowData[c] == null) {
						cell.setCellValue("");
					} else {
						cell.setCellValue(rowData[c] + "");
					}
					//前两列设置为标题
					if(r <= 1){
						cell.setCellStyle(styleHead);
					}else{
						cell.setCellStyle(style);	
					}
				}
				mapIndex++;
			}
		}
	}
	/**
	 * 导出到指定文件
	 * @param map
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public File writeLocal(List<Map> map, String fileName,String[] header) throws Exception {
		File file = new File(fileName);
		if(!file.exists()){
			File fileDir = new File(file.getParent());
			if(!fileDir.exists()){
				file.mkdirs();
			}
			fileDir = null;
		}
		OutputStream out = new FileOutputStream(file, false);// 每次刷新写
		HSSFWorkbook workbook = new HSSFWorkbook();
		exportExcel(workbook, 0, "KPI", header, map, out);
		workbook.write(out);
		out.close();
		System.out.println("- KPI 生成临时文件完毕："+file.getAbsolutePath());
		return file;
	}

	// 测试数据：（再D盘下生成test.xls文件，并有多个sheet）
	@SuppressWarnings({ })
	public static void main(String[] args) {
		try {
			ExcelUtil util = new ExcelUtil();
			String name = "D:\\helloNimei.xls";
			OutputStream out = new FileOutputStream(name);
			List<Object[]> results = new ArrayList<Object[]>();
			for (int i = 0; i < 5; i++) {
				Object[] objs = new Object[10];
				for(int j = 0; j < 10 ; j++){
					objs[j] = new String("行："+i+" 列："+j);
				}
				results.add(objs);
			}
			HSSFWorkbook workbook = new HSSFWorkbook();
			util.exportExcel(workbook, 0, "ceshi", results, out);
			workbook.write(out);
			out.close();
			System.out.println("输出完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
