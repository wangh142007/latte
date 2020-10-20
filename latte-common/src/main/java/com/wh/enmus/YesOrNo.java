package com.wh.enmus;

/**
 * @program: latte
 * @description:
 * @author: wh
 * @create: 2020-01-14 19:29
 */
public enum YesOrNo {
    /**
     *
     */
    NO(0,"否"),
    /**
     *
     */
    YES(1,"是");

    public final Integer type;
    public final String value;

    YesOrNo(Integer type, String value) {
        this.type = type;
        this.value = value;
    }


}
