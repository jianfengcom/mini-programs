package com.oldboy.spider.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Articles {
    private Integer id;
    private String title;
    private String content;
    private String tag;
    private String publishtime;
    private int peoples; // 讲究
    private C1 c1;
    private C2 c2;
    private String other1;
    private String other2;
    private String other3;
}
