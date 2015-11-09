package cs.com.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Tang on 2015/11/6.
 */
public class StreamUtils {
    public static String readFromStream(InputStream is){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] by = new byte[1024];
        int len = 0;
        try {
            while((len = is.read(by))!= -1){
                bos.write(by,0,len);
            }
            String text = new String(bos.toByteArray());
            bos.close();
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
