package com.oldboy.spider.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class C2 {
    private Integer id;
    private String name;
    private String comm;
    private C1 c1;
}
