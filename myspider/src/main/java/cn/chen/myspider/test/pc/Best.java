package cn.chen.myspider.test.pc;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

/**
 * 聚超值相关
 */
public class Best {

    /**
     * capture_best
     * cn.pconline.bestbuy.capture.service.topic.JingDongService.getInfo(JingDongService.java:43)
     * 问题: 爆料列表-添加 无法解析 京东商品链接
     * 解决: 设置新的用户代理
     */
    @SuppressWarnings("Duplicates")
    @Test
    public void parseJd() throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        String url = "https://item.jd.com/100018768506.html";
        HttpGet get = new HttpGet(url);

        HttpHost proxyHost = new HttpHost("200.147.153.131", 80); // 131测试成功, 总的来说, 网上找的免费代理ip大部分都不行

        // 设置请求配置的代理地址
        RequestConfig reqConfig = RequestConfig.custom()
                .setConnectTimeout(10000) // 设置连接超时时间, 单位毫秒
                .setSocketTimeout(10000) // 设置读取超时时间, 单位毫秒
                //.setProxy(proxyHost) // 设置代理
                .build();

        // 设置请求配置
        get.setConfig(reqConfig);

        // 设置用户代理
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.128 Safari/537.36");

        CloseableHttpResponse response = httpClient.execute(get);
        HttpEntity entity = response.getEntity();
        String out = EntityUtils.toString(entity, "UTF-8");

        System.out.println(out);
    }

    /**
     * POST请求, json类型参数
     * 简版
     */
    @SuppressWarnings("Duplicates")
    public void jsonPost(String url, JSONObject params) throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost(url);

        // 设置请求配置的代理地址
        RequestConfig reqConfig = RequestConfig.custom()
                .setConnectTimeout(3000) // 设置连接超时时间, 单位毫秒
                .setSocketTimeout(3000) // 设置读取超时时间, 单位毫秒
                .build();

        // 设置请求配置
        post.setConfig(reqConfig);

        // 传值时传递的是json字符串，这样的好处是在服务端无需建立参数模型，直接接收String。
        StringEntity stringEntity = new StringEntity(params.toJSONString(), "UTF-8");
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        HttpEntity entity = response.getEntity();
        String out = EntityUtils.toString(entity, "UTF-8");

        System.out.println(out);
    }
    /*@RequestMapping("/run")
    @ResponseBody
    public String run(@RequestBody String jsonStr) {
        JSONObject jo = JSONObject.parseObject(jsonStr);JSONArray ja = jo.getJSONArray("productIds");for (int i = 0; i < ja.size(); i++) { System.out.println(ja.getLongValue(i)); }
        return "run";
    }*/
}
