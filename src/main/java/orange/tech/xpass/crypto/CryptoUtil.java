package orange.tech.xpass.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import orange.tech.xpass.exception.ApplicationException;


public class CryptoUtil {
	
	
	public static byte[] encryptAES(byte[] keyBytes, byte[] dataBytes) throws ApplicationException {
		return handleAES(keyBytes,dataBytes,Cipher.ENCRYPT_MODE);
	}
	
	

	public static byte[] decryptAES(byte[] keyBytes, byte[] dataBytes) throws ApplicationException {
		return handleAES(keyBytes,dataBytes,Cipher.DECRYPT_MODE);
	}

	
	
	private static byte[] handleAES(byte[] keyBytes, byte[] dataBytes, int mode) throws ApplicationException {
	
		if(keyBytes == null || keyBytes.length != 16) {
			throw new ApplicationException("The key was invalid");
		}
		
		if(dataBytes == null) {
			throw new ApplicationException("There wasn't any data");
		}
		
		try {
			SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
			
			Cipher cipher = Cipher.getInstance("AES");
			
			cipher.init(mode, key);
			
			return cipher.doFinal(dataBytes);
		} catch (Exception e) {
			throw new ApplicationException(e);
		}		
	}

}
