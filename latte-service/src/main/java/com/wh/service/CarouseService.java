package com.wh.service;

import com.wh.pojo.Carousel;

import java.util.List;

/**
 * @program: latte
 * @description:
 * @author: wh
 * @create: 2020-01-21 18:11
 */
public interface CarouseService {

    /**
     * 查询所有轮播图列表
     *
     * @param isShow
     * @return
     */
    List<Carousel> queryAll(Integer isShow);
}
