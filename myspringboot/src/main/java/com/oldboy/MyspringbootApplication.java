package com.oldboy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@Controller//@RestController
public class MyspringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyspringbootApplication.class, args);
    }

    /*@RequestMapping("/rest")
    public String rest() {
        return "welcome sb!!!";
    }*/

    @RequestMapping("/unrest")
    public String unrest() {
        return "1";
    }

}
