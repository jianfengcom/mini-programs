package cn.chen.myspider.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import okhttp3.OkHttpClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProxyIpUtil {

    private static List<String> ipArray;

    public static List<String> getIpArray() {
        return ipArray;
    }

    public static void setIpArray(List<String> ipArray) {
        ProxyIpUtil.ipArray = ipArray;
    }

    public static List<String> getIpPool() throws Exception {
        String url = "http://192.168.100.62/vpn/mgr/v2/vpnlist?apicode=kLoB6fqvbw";

        HttpGet get = new HttpGet(url);

        // 设置请求配置的代理地址
        RequestConfig reqConfig = RequestConfig.custom()
                .setConnectTimeout(10000) // 设置连接超时时间, 单位毫秒
                .setSocketTimeout(10000) // 设置读取超时时间, 单位毫秒
//                .setProxy(proxyHost) // 设置代理
                .build();

        // 设置请求配置
        get.setConfig(reqConfig);
        // 设置用户代理 setHeader

        // ip池
        String result = get(get);
        List<String> proxyIpList = new ArrayList<String>();
        if (StringUtils.isNotBlank(result)) {
            JSONArray array = JSON.parseArray(result);
            for (int i = 0; i < array.size(); i++) {
                proxyIpList.add(array.getJSONObject(i).getString("vpn_dockerip"));
            }
        }
        return proxyIpList;
    }


    public static String get(HttpGet get) throws Exception {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        CloseableHttpResponse response = httpClient.execute(get);

        HttpEntity entity = response.getEntity();
        return EntityUtils.toString(entity, "UTF-8");
    }

    /**
     * 检测ip、端口连通性
     */
    public static boolean isHostConnectable(String host, int port) {
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            System.out.println("代理IP：" + host + ":" + port + ",连接不上，可能在重拨中");
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 检测IP池是否有更新
     * todo: 有则更新mc的值
     */
    public static void checkIpPool() throws Exception {
        if (ipArray != null && ipArray.size() > 0) {
            List<String> newIpList = getIpPool();
            //出现新的IP
            if (!ipArray.get(0).equals(newIpList.get(0))) {
                ipArray = newIpList;
            }
        } else {
            ipArray = getIpPool();
        }
    }

    /**
     * 获取代理的IP与PORT
     */
    public static String[] getProxy(int index) throws Exception {
        List<String[]> proxyList = new ArrayList<String[]>();
        while (ipArray == null || ipArray.size() == 0) {
            checkIpPool();
        }
        for (String ip : ipArray) {
            String[] proxy = new String[]{ip, "8080"};
            proxyList.add(proxy);
        }
        if (index - 1 > proxyList.size()) {
            return proxyList.get(0);
        }
        return proxyList.get(index);
    }

    /**
     * 获取OkHttpClient
     * todo: OkHttpClient模拟表单提交请求
     */
    public OkHttpClient getOkClient(String mcKey) throws Exception {
        int index = 0;
        int failNum = 0;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);

        // mc

        String[] proxys = ProxyIpUtil.getProxy(index);
        while (!ProxyIpUtil.isHostConnectable(proxys[0], Integer.parseInt(proxys[1]))) {
            failNum += 1;
            index += 1;
            // 连续失败9次，则重新获取IP池
            if (failNum == 9) {
                checkIpPool();
                index = 0;
            }
            proxys = ProxyIpUtil.getProxy(index);
        }
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxys[0], Integer.parseInt(proxys[1])));
        builder.proxy(proxy);

        // mc
        return builder.followRedirects(false).followSslRedirects(false).build();
    }
}
