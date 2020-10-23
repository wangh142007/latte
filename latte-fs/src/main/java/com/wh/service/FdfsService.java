package com.wh.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author ：Wang Hao
 * @date ：Created in 2020/10/23 14:32
 */
public interface FdfsService {

    String upload(MultipartFile file, String fileExtName) throws Exception;


    public String uploadOSS(MultipartFile file, String userId, String fileExtName) throws Exception;
}
