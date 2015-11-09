package cs.com.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Tang on 2015/11/8.
 */
public class MD5Utils {
    public static String encode(String password){
        try {
            MessageDigest mg = MessageDigest.getInstance("MD5");
            byte[] by = mg.digest(password.getBytes());

            StringBuffer sb = new StringBuffer();
            for (byte b:by) {
                //获取低八位有效值
                int i = b & 0xff;
                String hexString = Integer.toHexString(b);
                if(hexString.length() < 2){
                    //如果是一位的话，补0
                    hexString +="0";
                }
                sb.append(hexString);
            }
            return  sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
