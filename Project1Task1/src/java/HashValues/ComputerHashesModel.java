package HashValues;

/*
 * @author Gao Jiang
 * 
 * This file is the Model component of the MVC, and it models the business
 * logic for the web application.  In this case, the business logic involves
 * doing md5 or sha-1 encrypt for the plain text and also transfer the result 
 * into a hexadecimal text and base64 text.
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.BASE64Encoder;

public class ComputerHashesModel {
    /**
     * This function is used to do MD5 encryption
     * @param plainText The input text to be encrypted
     * @return The byte array of the encrypted values 
     */
    public byte[] doMD5 (String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte[] md5HashValue = md.digest();
            return md5HashValue;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ComputerHashesModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * This function is used to do SHA-1 encryption
     * @param plainText The input text to be encrypted
     * @return The byte array of the encrypted values
     */
    public byte[] doSHA (String plainText) {
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            sha.update(plainText.getBytes());
            byte[] shaHashValue = sha.digest();
            return shaHashValue;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ComputerHashesModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
    }
    
    /**
     * This function is used to transfer the encrypted byte value into hexadecimal string text
     * @param hashByte The input byte array to be transferred
     * @return The hexadecimal string text
     */
    public String getHexString (byte[] hashByte) {
        String resultString = "";
        
        for (int i = 0; i < hashByte.length; i++) {
            resultString += Integer.toString((hashByte[i] & 0xff) + 0x100, 16).substring(1);
        }
        return resultString;
    }
    
    /**
     * This function is used to transfer the encrypted byte value into base64 string text
     * @param hashByte The input byte array to be transferred
     * @return The base64 string text
     */
    public String getBase64 (byte[] hashByte) {
        String resultString;
        BASE64Encoder encoder = new BASE64Encoder();
        resultString = encoder.encode(hashByte);
        
        return resultString;
    }
}
