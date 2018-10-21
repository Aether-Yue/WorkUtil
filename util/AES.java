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
	 * ��Կ�㷨
	 */
	private static final String KEY_ALGORITHM = "AES";

	// �����㷨/����ģʽ/��䷽ʽ
	private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";

	/**
	 * ����
	 * 
	 * @param data
	 *            ����������
	 * @param key
	 *            ��Կ
	 * @return byte[] ��������
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String key) throws Exception {
		return encrypt(data, key.getBytes(), DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * ����
	 * 
	 * @param data
	 *            ����������
	 * @param key
	 *            ��Կ
	 * @return byte[] ��������
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, String key) throws Exception {
		return decrypt(data, key.getBytes(), DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * ����
	 * 
	 * @param data
	 *            ����������
	 * @param key
	 *            ��Կ
	 * @param cipherAlgorithm
	 *            �����㷨/����ģʽ/��䷽ʽ
	 * @return byte[] ��������
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] data, byte[] key,
			String cipherAlgorithm) throws Exception {
		// ʵ����
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// ʹ����Կ��ʼ��������Ϊ����ģʽ
		Key k = new SecretKeySpec(key, KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, k);
		// ִ�в���
		return cipher.doFinal(data);
	}

	/**
	 * ����
	 * 
	 * @param data
	 *            ����������
	 * @param key
	 *            ��Կ
	 * @param cipherAlgorithm
	 *            �����㷨/����ģʽ/��䷽ʽ
	 * @return byte[] ��������
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] data, byte[] key,
			String cipherAlgorithm) throws Exception {
		// ʵ����
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// ʹ����Կ��ʼ��������Ϊ����ģʽ
		SecretKeySpec k = new SecretKeySpec(key, KEY_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, k);
		// ִ�в���
		return cipher.doFinal(data);
	}

	public static void main(String[] args) throws Exception {

		String key = "1234567890123456";

		String data = "AES����dasdf#@%%^&*!!(#)(_+(!_)_#@($()*(%.,;;'sdfl;";
		System.out.println("����ǰ����:" + data);

		// ����
		System.out.println();
		byte[] encryptData = encrypt(data.getBytes(), key);

		// ����
		System.out.println();
		byte[] decryptData = decrypt(encryptData, key);
		System.out.println("���ܺ�����:" + new String(decryptData));

	}
}
