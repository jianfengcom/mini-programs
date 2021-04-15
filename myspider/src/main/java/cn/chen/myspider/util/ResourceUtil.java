package cn.chen.myspider.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class ResourceUtil {

    /**
     * 从流中读取字符串
     * @param in
     * @throws Exception
     */
    public static String ReadInputStreamAsString(InputStream in) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] temp = new byte[1024];
        int len;
        while ((len = in.read(temp)) != -1) {
            out.write(temp, 0, len);
        }
        out.close();
        in.close();
        return new String(out.toByteArray());
    }

}
