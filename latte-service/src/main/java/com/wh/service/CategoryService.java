package com.wh.service;

import com.wh.pojo.Carousel;
import com.wh.pojo.Category;
import com.wh.pojo.vo.CategoryVO;
import com.wh.pojo.vo.NewItemsVO;

import java.util.List;

/**
 * @program: latte
 * @description:
 * @author: wh
 * @create: 2020-01-21 18:11
 */
public interface CategoryService {

    /**
     * 查询所有一级分类
     *
     * @return
     */
    List<Category> queryAllRootLevelCat();

    /**
     * 根据一级分类id查询子分类信息
     *
     * @param rootCatId
     * @return
     */
    List<CategoryVO> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的6条最新商品数据
     *
     * @param rootCatId
     * @return
     */
    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
