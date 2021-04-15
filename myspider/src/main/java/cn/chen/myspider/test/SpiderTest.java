package cn.chen.myspider.test;

import cn.chen.myspider.util.ResourceUtil;
import org.junit.Test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SpiderTest {

    @Test
    public void test1() throws Exception {
        URL url = new URL("https://www.jd.com/");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        int code = conn.getResponseCode();
        if (code == 200) {
            InputStream in = conn.getInputStream();
            String out = ResourceUtil.ReadInputStreamAsString(in);
            System.out.println(out);
        }
    }
}
