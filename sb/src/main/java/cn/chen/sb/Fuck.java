package cn.chen.sb;

import org.springframework.util.DigestUtils;

import java.math.BigDecimal;

public class Fuck {

    public static void main(String[] args) {
        /*String code = "fb114bcb695bac0f110bd9119034e429" + 2459518;
        String out = DigestUtils.md5DigestAsHex(code.getBytes());
        System.out.println(out);*/

        //
//        String url = "https%3A%2F%2Fitem.jd.com%2F100018768506.html";
        String url = "https://item.jd.com/100018768506.html?fuck";
        String domain = url.split("\\?")[1];
        System.out.println(domain);
    }

}
