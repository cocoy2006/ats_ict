package molab.main.java.util;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Logger;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class Security {
	
	private static final Logger log = Logger.getLogger(Security.class.getName());

	public static boolean setLastTime(String file, long lastTime) {
		File f = new File(file);
		if(!f.exists()) {
			log.severe("Important files miss.");
			System.exit(0);
			return false;
		}
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			bw.write(Base64.encode(Security.encrypt(String.valueOf(lastTime).getBytes())));
			bw.close();
			return true;
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		return false;
	}
	
	public static long getLastTime(String file) {
		File f = new File(file);
		if(!f.exists()) {
			log.severe("Important files miss.");
			System.exit(0);
			return 0;
		}
		long lastTime = 0;
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String ciperText = br.readLine();
			br.close();
			if(ciperText == null) {
				return 0;
			}
			lastTime = Long.parseLong(new String(Security.decrypt(Base64.decode(ciperText)), "UTF8"));
		} catch (Exception e) {
			log.severe(e.getMessage());
		}
		return lastTime;
	}
	
	public static Key getKey() throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
		String key = "molab~123";
    	DESKeySpec dks = new DESKeySpec(key.getBytes());  
        SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
        SecretKey sk = kf.generateSecret(dks);
        return sk;
	}
	
	public static byte[] encrypt(byte[] str) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, InvalidKeySpecException {
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, getKey());
		return cipher.doFinal(str);
	}
		
	public static byte[] decrypt(byte[] str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, getKey());
		return cipher.doFinal(str);
	}
	
}