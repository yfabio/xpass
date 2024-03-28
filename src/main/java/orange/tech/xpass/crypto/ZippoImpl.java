package orange.tech.xpass.crypto;

import java.util.Base64;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ZippoImpl implements Zippo {
				
	private final String mySecretKey;
			
	public ZippoImpl(Environment env) {		
		mySecretKey = env.getProperty("SECRET_KEY");			
	}
	
	@Override
	public String encrypt(String raw) {					
		byte[] encryptAES = CryptoUtil.encryptAES(mySecretKey.getBytes(), raw.getBytes());
		byte[] base64Encoded = Base64.getEncoder().encode(encryptAES);
		return new String(base64Encoded);
	}

	@Override
	public String decrypt(String base64Encoded) {
		byte[] encryptAES = Base64.getDecoder().decode(base64Encoded.getBytes());
		byte[] decrepted = CryptoUtil.decryptAES(mySecretKey.getBytes(), encryptAES);
		return new String(decrepted);
	}
	
	
}
