package cn.chen.myspider.test.pc;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/*
    2021-05-12
 */
public class Fuck {

    @SuppressWarnings("Duplicates")
    @Test
    public void parseJd() throws Exception {
        String url = "";
        
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(url);

        HttpHost proxyHost = new HttpHost("200.147.153.131", 80); // 131测试成功, 总的来说, 网上找的免费代理ip大部分都不行

        // 设置请求配置的代理地址
        RequestConfig reqConfig = RequestConfig.custom()
                .setConnectTimeout(3000) // 设置连接超时时间, 单位毫秒
                .setSocketTimeout(3000) // 设置读取超时时间, 单位毫秒
                //.setProxy(proxyHost) // 设置代理
                .build();

        // 设置请求配置
        post.setConfig(reqConfig);

        JSONObject params = new JSONObject();
        long[] productIds = {9411, 84, 9527};
        params.put("productIds", productIds);

        // 传值时传递的是json字符串，这样的好处是在服务端无需建立参数模型，直接接收String，便于后期维护。
        StringEntity stringEntity = new StringEntity(params.toJSONString(),"UTF-8");
        post.setEntity(stringEntity);

        // 设置用户代理
        // post.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.128 Safari/537.36");

        CloseableHttpResponse response = httpClient.execute(post);
        HttpEntity entity = response.getEntity();
        String out = EntityUtils.toString(entity, "UTF-8");

        System.out.println(out);
    }
}
