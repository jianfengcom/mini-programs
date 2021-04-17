package cn.chen.myspider.util;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResourceUtil {

    /**
     * 从流中读取字符串
     *
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

    /**
     * 请求参数
     *
     * @param paramsMap
     * @return
     */
    public static List<NameValuePair> mapAsNameValuePairList(Map<String, String> paramsMap) {
        List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            nameValuePairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return nameValuePairList;
    }

}
