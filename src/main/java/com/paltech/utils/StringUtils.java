package com.paltech.utils;

import org.apache.commons.lang3.RandomStringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Allowing to generate strings that are usually used in creating test data
 * @author isabella.huynh
 * created on Nov/9/2019
 */

public class StringUtils {
    /**
     * Generating a numeric string
     * @param length of this string
     * @return a numeric string with a length
     */
    public static String generateNumeric(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public static String generateNumeric(int min, int max) {
        try {
            Random ran = new Random();
            return String.valueOf(ran.nextInt(max - min + 1) + min);
        }catch (Exception ex) {
            System.out.println("Exception:"+ ex);
            return "";
        }
    }

    /**
     * Generating a alphabetic x`string
     * @param length of this string
     * @return a alphabetic string with a length
     */
    public static String generateAlphabetic(int length) {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String generateString(String prefix, int length) {
        return prefix + "_" + RandomStringUtils.randomAlphabetic(length - (prefix.length() + 1));
    }

    /**
     * Generating a password at least 1 letter and 1 number
     *
     * @param length of password
     * @return a generated password
     */
    public static String generatePassword(int length) {
        if (length < 2) {
            System.out.println("Error: length of password must be more than or equal 2");
            return null;
        }
        int lengthLetter = length / 2;
        int lengthNumber = length - lengthLetter;
        return String.format("%s%s", RandomStringUtils.randomAlphabetic(lengthLetter), RandomStringUtils.randomNumeric(lengthNumber));
    }

    /**
     * Generating an email with prefix 'auto.' and suffix is '@yopmail.com'. After prefix can be number or alphabetic or alphanumeric
     *
     * @param lengthEmail must be > 17
     * @param isNumber    boolean
     * @param isLetter    boolean
     * @return email
     */
    public static String generateEmail(int lengthEmail, boolean isNumber, boolean isLetter) {
        if (lengthEmail < 17) {
            System.out.println("Error: lengthEmail must be more than 17");
            return null;
        }

        String standard = "auto.%s@yopmail.com";
        int length = lengthEmail - 17;
        if (isNumber && isLetter) {
            int lengthLetter = length / 2;
            int lengthNumber = length - lengthLetter;
            String alphanumeric = String.format("%s%s", RandomStringUtils.randomAlphabetic(lengthLetter), RandomStringUtils.randomNumeric(lengthNumber));
            return String.format(standard, alphanumeric);
        }
        if (!isNumber) {
            return String.format(standard, RandomStringUtils.randomAlphabetic(length));
        } else {
            return String.format(standard, RandomStringUtils.randomNumeric(length));
        }
    }

    /**
     * Format the number of currency based on US country
     * @param val e.g. 1200.01
     * @return 1,200.01
     */
    public static String formatCurrency (String val){
        Locale locale = new Locale("en", "US");
        NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
        return formatter.format(Double.parseDouble(val)).replace("$", "");
    }

    public static String encodeURL(String val){
        try {
            return URLEncoder.encode(val, "UTF-8");
        } catch (UnsupportedEncodingException ex){
            System.out.println("Exception: UnsupportedEncodingException at encodeURL");
            return "";
        }
    }

    public static boolean isListContainText(List<ArrayList<String>> list, String value, int index)
    {
        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i).get(index).equals(value))
                return true;
        }
        return false;
    }
    public static boolean isListContainText(ArrayList<String> list, String value, int index)
    {
        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(index).equals(value))
                return true;
        }
        return false;
    }

    private static final String ALGO = "AES";
    private static final String code = "MeritoSecretKeys";

    /**
     * Encrypt a string with AES algorithm.
     *
     * @param data is a string
     * @return the encrypted string
     */
    public static String encrypt(String data) throws Exception {
        Key key = generateKey(code);
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encVal = c.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encVal);
    }

    /**
     * Decrypt a string with AES algorithm.
     *
     * @param encryptedData is a string
     * @return the decrypted string
     */
    public static String decrypt(String encryptedData) throws Exception {
        Key key = generateKey(code);
        Cipher c = Cipher.getInstance(ALGO);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decordedValue = Base64.getDecoder().decode(encryptedData);
        byte[] decValue = c.doFinal(decordedValue);
        return new String(decValue);
    }

    /**
     * Generate a new encryption key.
     */
    private static Key generateKey(String key) throws Exception {
        byte[] bytesOfMessage = key.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] b = md.digest(bytesOfMessage); //Returns the SHA 256 hash and converts it into byte
// Continue with your code
        return new SecretKeySpec(key.getBytes(), ALGO);
    }
}
