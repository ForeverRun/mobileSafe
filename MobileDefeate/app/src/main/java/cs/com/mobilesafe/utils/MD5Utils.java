package cs.com.mobilesafe.utils;

import java.io.File;
import java.io.FileInputStream;
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
    /**
     * 获取到文件的MD5(病毒特征码)
     * @param sourceDir
     * @return
     * 主动防御
     */
    public static String getFileMd5(String sourceDir) {

        File file = new File(sourceDir);

        try {
            FileInputStream fis = new FileInputStream(file);

            byte[] buffer = new byte[1024];

            int len = -1;
            //获取到数字摘要
            MessageDigest messageDigest = MessageDigest.getInstance("md5");

            while ((len = fis.read(buffer)) != -1) {

                messageDigest.update(buffer, 0, len);

            }
            byte[] result = messageDigest.digest();

            StringBuffer sb = new StringBuffer();

            for (byte b : result) {
                int number = b & 0xff; // 加盐 +1 ;
                String hex = Integer.toHexString(number);
                if (hex.length() == 1) {
                    sb.append("0" + hex);
                } else {
                    sb.append(hex);
                }
            }
            return sb.toString();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
