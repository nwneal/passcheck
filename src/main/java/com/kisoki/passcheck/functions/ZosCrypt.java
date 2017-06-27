/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kisoki.passcheck.functions;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author nwneal
 */
public class ZosCrypt {
    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static String masterPass = "";
    
    public static String encryptDBCred(String user, String pass) {
            String encStr = "user="+user+"&password="+pass;
            return encrypt(encStr, masterPass);
    }
    
    public static String decryptDBCred(String encStr) {
        String cred = decrypt(encStr, masterPass);
        return cred;
    }
    
    public static String decryptDB(String dbUrl) {
        String[] url = dbUrl.split("\\?");
        String cred = decrypt(url[1], masterPass);
        return url[0]+"?"+cred;
    }
    
    public static String md5Hash(String str) {
        byte[] bytesOfMessage;    
        try {
            bytesOfMessage = str.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bytesOfMessage);
            return String.format("%064x", new java.math.BigInteger(1, digest));
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ZosCrypt.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ZosCrypt.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
        
    }
 
    public static void setKey(String myKey) 
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16); 
            secretKey = new SecretKeySpec(key, "AES");
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } 
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
 
    public static String encrypt(String strToEncrypt, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            //return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
            return Base64.encodeBase64String(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
 
    public static String decrypt(String strToDecrypt, String secret) 
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            //return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            return new String(cipher.doFinal(Base64.decodeBase64(strToDecrypt)));
        } 
        catch (Exception e) 
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }    
}
