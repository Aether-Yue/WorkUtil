/**
 * 
 */
package test;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author xiaohaisheng
 * 
 */
public class AES {
	/**
	 * 密钥算法
	 */
	private static final String KEY_ALGORITHM = "AES";

	// 加密算法/工作模式/填充方式
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String key) throws Exception {
		return encrypt(data, key.getBytes(), DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String key) throws Exception {
		return decrypt(data, key.getBytes(), DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 加密
	 * 
	 * @param data
	 *            待加密数据
	 * @param key
	 *            密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key,
			String cipherAlgorithm) throws Exception {
		// 实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 使用密钥初始化，设置为加密模式
		Key k = new SecretKeySpec(key, KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 * 
	 * @param data
	 *            待解密数据
	 * @param key
	 *            密钥
	 * @param cipherAlgorithm
	 *            加密算法/工作模式/填充方式
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key,
			String cipherAlgorithm) throws Exception {
		// 实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 使用密钥初始化，设置为解密模式
		SecretKeySpec k = new SecretKeySpec(key, KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		// 执行操作
		return cipher.doFinal(data);
	}

	public static void main(String[] args) throws Exception {

		String key = "1234567890123456";

		String data = "AES数据dasdf#@%%^&*!!(#)(_+(!_)_#@($()*(%.,;;'sdfl;";
		System.out.println("加密前数据:" + data);

		// 加密
		System.out.println();
		byte[] encryptData = encrypt(data.getBytes(), key);

		// 解密
		System.out.println();
		byte[] decryptData = decrypt(encryptData, key);
		System.out.println("解密后数据:" + new String(decryptData));

	}
}
