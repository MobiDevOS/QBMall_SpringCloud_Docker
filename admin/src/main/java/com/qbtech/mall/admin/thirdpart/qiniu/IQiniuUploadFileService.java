package com.qbtech.mall.admin.thirdpart.qiniu;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * 七牛上传文件服务
 */
public interface IQiniuUploadFileService {

    Response uploadFile(File file) throws QiniuException;

    Response uploadFile(InputStream inputStream) throws QiniuException;

    Response delete(String key) throws QiniuException;


    /**
     * 多文件上传
     * @param file
     * @return
     */
     List<String> uploadImgs(MultipartFile[] file);




}