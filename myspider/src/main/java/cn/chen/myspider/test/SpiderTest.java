package cn.chen.myspider.test;

import cn.chen.myspider.util.ResourceUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

//        String url = "https://www.jd.com/";
        String url = "https://www.pclady.com.cn/";
        HttpGet get = new HttpGet(url);

        // 代理主机
//        HttpHost proxyHost = new HttpHost("114.239.145.232", 9999);
        HttpHost proxyHost = new HttpHost("200.147.153.131", 80); // 131测试成功, 总的来说, 网上找的免费代理ip大部分都不行

        // 设置请求配置的代理地址
        RequestConfig reqConfig = RequestConfig.custom()
                .setConnectTimeout(10000) // 设置连接超时时间, 单位毫秒
                .setSocketTimeout(10000) // 设置读取超时时间, 单位毫秒
                .setProxy(proxyHost) // 设置代理
                .build();

        // 设置请求的配置
        get.setConfig(reqConfig);

        // 设置用户代理
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.128 Safari/537.36");
        CloseableHttpResponse response = httpClient.execute(get);
        HttpEntity entity = response.getEntity();
        String out = EntityUtils.toString(entity, "GBK");

        System.out.println(out);
    }

    /**
     * csdn模拟登录尚未成功, 下回试试时尚网
     *
     * @throws Exception
     */
    @Test
    public void test4() throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        String url = "https://passport.csdn.net/account/verify";

        RequestConfig reqConfig = RequestConfig.custom()
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .build();

        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", "13433906245"));
        params.add(new BasicNameValuePair("password", "18319128948cjf"));

        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(params, "UTF-8");

        HttpPost post = new HttpPost(url);
        // 设置 代理&请求参数
        post.setConfig(reqConfig);
        post.setEntity(formEntity);

        // 设置用户代理
        post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.128 Safari/537.36");

        CloseableHttpResponse response = httpClient.execute(post);
        HttpEntity entity = response.getEntity();
        String out = EntityUtils.toString(entity, "UTF-8");
        System.out.println(out);
    }
}
