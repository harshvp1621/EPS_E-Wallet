package com.journaldev.barcodevisionapi;

import java.security.MessageDigest;

public class GenSHA256 {
    public static String genSHA256Hash(String data){
        String res = null;
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte [] hash = digest.digest(data.getBytes("UTF-8"));
            return bytesToHex(hash);

        }catch (Exception e){
            e.printStackTrace();
        }
        return  res;
    }
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private static String bytesToHex(byte[] hash){
        char [] hexChars = new char[hash.length * 2];
        for(int j = 0;j < hash.length;j++){
            int v = hash[j] & 0xFF;
            hexChars[j*2] = HEX_ARRAY[v >>> 4];
            hexChars[j*2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

}
