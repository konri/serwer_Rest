package com.engineer.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by Konrad on 23.09.2015.
 */
public class EncryptionUtils {
    private final static EncryptionUtils instance = new EncryptionUtils();
    private final String keyEncrypted = "Hop76498Kon65728"; //key to encrypt todo: see maybe get it from such file?
    private Cipher cipher;
    private final Key aesKey = new SecretKeySpec(keyEncrypted.getBytes(),"AES");


    private EncryptionUtils(){
        try {
            cipher = Cipher.getInstance("AES");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EncryptionUtils getInstance(){
        return instance;
    }

    /**
     * decrypt password (rozszyfrowac)
     * @param encryptedPassword encrypted password
     * @return decrypt password with key (plain text of password)
     */
    public String getDecryptedPassword(String encryptedPassword) {
        String decryptedPassword = "";
        String [] str = encryptedPassword.split(":");

        byte[] encyptedByte = new byte[str.length];
        for (int i = 0; i < str.length; i++) {
            encyptedByte[i] =  Byte.decode(str[i]);
        }

        try {
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            decryptedPassword = new String(cipher.doFinal(encyptedByte));
        } catch (Exception e) {
            e.printStackTrace();
        }


        return decryptedPassword;
    }

    /**
     * encrypt password
     * @param decryptedPassword decrypted password
     * @return encrypt password with the key(password encrypt)
     */
    public String getEncryptedPassword(String decryptedPassword){
        String encryptedPassword;
        try{
            cipher.init(Cipher.ENCRYPT_MODE,aesKey);
            byte[] encrypted = cipher.doFinal(decryptedPassword.getBytes());

            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < encrypted.length; i++){
                sb.append((int)encrypted[i]);
                if (i < encrypted.length - 1) sb.append(":");
            }
            encryptedPassword = sb.toString();
        }
        catch (Exception e){
            encryptedPassword = "";
        }
        return encryptedPassword;
    }


}
