package cn.chen.myspider.test;

import cn.chen.myspider.util.ResourceUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
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

    @Test
    public void test2() throws Exception {
        // builder模式
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        String url = "https://www.jd.com/";
        HttpGet get = new HttpGet(url);

        // 执行请求, 返回响应
        CloseableHttpResponse response = httpClient.execute(get);

        // 取得响应的内容(实体)
        HttpEntity entity = response.getEntity();
        String out = EntityUtils.toString(entity, "UTF-8");

        System.out.println(out);
    }

    @Test
    public void test3() throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        String url = "https://www.jd.com/";
        HttpGet get = new HttpGet(url);

        // 代理主机
        HttpHost proxyHost = new HttpHost("114.239.145.232", 9999);
//        HttpHost proxyHost = new HttpHost("192.168.57.69", 1080);

        // 设置请求配置的代理地址
        RequestConfig.Builder builder = RequestConfig.custom();
        builder.setProxy(proxyHost);
        RequestConfig reqConfig = builder.build();

        // 设置请求的配置
        get.setConfig(reqConfig);

        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.128 Safari/537.36");
        CloseableHttpResponse response = httpClient.execute(get);
        HttpEntity entity = response.getEntity();
        String out = EntityUtils.toString(entity, "UTF-8");

        System.out.println(out);
    }
}
