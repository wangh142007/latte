package com.wh.service;


import com.wh.utils.PagedGridResult;

public interface ItemsESService {

    public PagedGridResult searchItems(String keywords,
                                       String sort,
                                       Integer page,
                                       Integer pageSize);

}
