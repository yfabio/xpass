package orange.tech.xpass.crypto;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ZippoImpl implements Zippo {
	
	private static final String ALGORITHM = "AES";
	
	private SecretKey key;
	
	private final String mySecretKey;
			
	public ZippoImpl(Environment env) {		
		mySecretKey = env.getProperty("MY_SECRET_KEY");		
		key = getSecretKey();
	}
	
	@Override
	public String encrypt(String raw) {					
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] cipherText = cipher.doFinal(raw.getBytes());
			byte[] base64Encoded = Base64.getEncoder().encode(cipherText);
			return new String(base64Encoded);
		} catch (Exception e) {
			throw new RuntimeException("unable to encrypt");
		}
	}

	@Override
	public String decrypt(String cipherText) {
					
		try {
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(cipherText));
			return new String(plainText);
		} catch (Exception e) {
			throw new RuntimeException("unable to decrypt");
		} 
		
	}
	
	private SecretKey getSecretKey() {				
		
		if(mySecretKey == null || mySecretKey.getBytes().length != 16) {
			throw new RuntimeException("The key length was invalid");
		}					
		
		SecretKeySpec key = new SecretKeySpec(mySecretKey.getBytes(), "AES");
		
		return key;
	}
}
