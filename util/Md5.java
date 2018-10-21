package com.sinosoft.platform.core.util;
/*****************************************************
 * HISTORY
 * 
 * 2013/04/11 yangzd 创建文件
 * 
 * YYYY/MM/DD ----- 增加新功能
 * 
 *****************************************************/
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
/**
 * Md5 操作类，用于加密 解密方式是将比较字符串加密后与加密存储着的字符串比较
 * @author Darker.Wang
 * @date 2016-7-26 下午7:37:11
 * @company SINOSOFT
 */
public class Md5 {
	protected final transient Logger logger = Logger.getLogger(Md5.class);
	public static final String ALGORIGTHM_MD5 = "MD5";
	public static final String ENCODEING = "utf-8";
	public static final int CACHE_SIZE = 2048;
	/**
	 * 字符串转换成MD5码，用于登录加密等
	 * @param str
	 * @return
	 */
	public String Conversion(String str) {

		if (str == null) {
			return "";
		} else {
			String value = null;
			MessageDigest md5 = null;
			try {
				md5 = MessageDigest.getInstance(ALGORIGTHM_MD5);
			} catch (NoSuchAlgorithmException ex) {
				logger.getClass();
			}
			sun.misc.BASE64Encoder baseEncoder = new sun.misc.BASE64Encoder();
			try {
				value = baseEncoder.encode(md5.digest(str.getBytes(ENCODEING)));
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return value;
		}
	}
	/**
	 * 字节获取MD5码
	 * @param aByte
	 * @return
	 */
	public static String getMD5Code(byte[] aByte) {
		String md51 = DigestUtils.md5Hex(aByte);
		return md51.toString();
	}

	/**
	 * 指定文件获取MD5码
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String getMD5Code(File file) throws IOException {
		if (file == null || !file.exists() || !file.isFile()) {
			return null;
		}
		InputStream in = new FileInputStream(file);
		return getMD5Code(in);
	}

	/**
	 * 输入流获取MD5码
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static String getMD5Code(InputStream in) throws IOException {
		if (in == null) {
			return null;
		}
		byte[] b = new byte[1024];
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] rb = null;
		int i = in.read(b);
		while (i != -1) {
			out.write(b, 0, b.length);
			i = in.read(b);
		}
		rb = out.toByteArray();
		return getMD5Code(rb);
	}

	/**
	 * <p>
	 * 生成文件MD5值
	 * <p>
	 * <p>
	 * 在进行文件校验时，文件读取的缓冲大小[CACHE_SIZE]需与该方法的一致，否则校验失败
	 * </p>
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String createFileMD5(String filePath) throws Exception {
		String md5 = "";
		File file = new File(filePath);
		if (file.exists()) {
			MessageDigest messageDigest = getMD5();
			FileInputStream in = new FileInputStream(file);
			FileChannel fileChannel = in.getChannel();
			MappedByteBuffer byteBuffer = fileChannel.map(
					FileChannel.MapMode.READ_ONLY, 0, file.length());
			messageDigest.update(byteBuffer);
			fileChannel.close();
			in.close();
			byte data[] = messageDigest.digest();
			md5 = byteArrayToHexString(data);
		}
		return md5;
	}

	/**
	 * <p>
	 * MD5摘要字节数组转换为16进制字符串
	 * </p>
	 * 
	 * @param data
	 *            MD5摘要
	 * @return
	 */
	private static String byteArrayToHexString(byte[] data) {
		// 用来将字节转换成 16 进制表示的字符
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		// 每个字节用 16 进制表示的话，使用两个字符，所以表示成 16 进制需要 32 个字符
		char arr[] = new char[16 * 2];
		int k = 0; // 表示转换结果中对应的字符位置
		// 从第一个字节开始，对 MD5 的每一个字节转换成 16 进制字符的转换
		for (int i = 0; i < 16; i++) {
			byte b = data[i]; // 取第 i 个字节
			// 取字节中高 4 位的数字转换, >>>为逻辑右移，将符号位一起右移
			arr[k++] = hexDigits[b >>> 4 & 0xf];
			// 取字节中低 4 位的数字转换
			arr[k++] = hexDigits[b & 0xf];
		}
		// 换后的结果转换为字符串
		return new String(arr);
	}

	private static MessageDigest getMD5() throws NoSuchAlgorithmException {
		return MessageDigest.getInstance(ALGORIGTHM_MD5);
	}

	/**
	 * 获取该输入流的MD5值
	 * 
	 * @param is
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String getMD5(InputStream is) throws NoSuchAlgorithmException,
			IOException {
		StringBuffer md5 = new StringBuffer();
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] dataBytes = new byte[1024];
		int nread = 0;
		while ((nread = is.read(dataBytes)) != -1) {
			md.update(dataBytes, 0, nread);
		}
		byte[] mdbytes = md.digest();
		// convert the byte to hex format
		for (int i = 0; i < mdbytes.length; i++) {
			md5.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16)
					.substring(1));
		}
		return md5.toString();
	}

	/**
	 * 获取该文件的MD5值
	 */
	public static String getMD5(File file) throws NoSuchAlgorithmException,
			IOException {
		FileInputStream fis = new FileInputStream(file);
		return getMD5(fis);
	}

	/**
	 * 获取指定路径文件的MD5值
	 */
	public static String getMD5(String path) throws NoSuchAlgorithmException,
			IOException {
		FileInputStream fis = new FileInputStream(path);
		return getMD5(fis);
	}

	/**
	 * 校验该输入流的MD5值 6
	 */
	public static boolean md5CheckSum(InputStream is, String toBeCheckSum)
			throws NoSuchAlgorithmException, IOException {
		return getMD5(is).equals(toBeCheckSum);
	}

	/**
	 * 校验该文件的MD5值
	 */
	public static boolean md5CheckSum(File file, String toBeCheckSum)
			throws NoSuchAlgorithmException, IOException {
		return getMD5(file).equals(toBeCheckSum);
	}

	/**
	 * 校验指定路径文件的MD5值
	 */
	public static boolean md5CheckSum(String path, String toBeCheckSum)
			throws NoSuchAlgorithmException, IOException {
		return getMD5(path).equals(toBeCheckSum);
	}

	public static void main(String[] args) throws Exception {
		String filePath = "C:\\Users\\Arrow\\Desktop\\I_000033LINC2798043394_016.zip";
		String md51 = DigestUtils.md5Hex(filePath);
		String md5 = Md5.getMD5Code(new File(filePath));
		String md52 = Md5.createFileMD5(filePath);
		System.out.println("md5:" + md5);
		System.out.println("md51:" + md51);
		System.out.println("md52:" + md52);
	}
}
