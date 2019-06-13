package com.qbtech.mall.admin.thirdpart.qiniu;

import com.google.gson.Gson;
import com.qbtech.mall.admin.api.CartRestImpl;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

/**
 * 参考七牛官方文档
 * https://developer.qiniu.com/kodo/sdk/1239/java
 *
 * https://blog.csdn.net/qq_33285543/article/details/82284686
 * 七牛上传案例
 * */
@Service
public class QiniuUploadFileServiceImpl implements IQiniuUploadFileService,InitializingBean {
	private static final Logger logger = LoggerFactory.getLogger(QiniuUploadFileServiceImpl.class);

	@Autowired
    private UploadManager uploadManager;
 
    @Autowired
    private BucketManager bucketManager;
 
    @Autowired
    private Auth auth;
 
    @Value("${qiniu.Bucket}")
    private String bucket;

	@Value("${qiniu.cdn.prefix}")
    private String DOMAIN;

	@Value("${qiniu.picstyle}")
	private String STYLE;


 
    /**
     * 定义七牛云上传的相关策略
     */
    private StringMap putPolicy;
 
 
    /**
     * 以文件的形式上传
     * @param file
     * @return
     * @throws QiniuException
     */
    @Override
    public Response uploadFile(File file) throws QiniuException {
        Response response = this.uploadManager.put(file, null, getUploadToken());
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(file, null, getUploadToken());
            retry++;
        }
        return response;
    }
 
    /**
     * 以流的形式上传
     * @param inputStream
     * @return
     * @throws QiniuException
     */
    @Override
    public Response uploadFile(InputStream inputStream) throws QiniuException {
        Response response = this.uploadManager.put(inputStream, null, getUploadToken(), null, null);
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = this.uploadManager.put(inputStream, null, getUploadToken(), null, null);
            retry++;
        }
        return response;
    }
 
    /**
     * 删除七牛云上的相关文件
     * @param key
     * @return
     * @throws QiniuException
     */
    @Override
    public Response delete(String key) throws QiniuException {
        Response response = bucketManager.delete(this.bucket, key);
        int retry = 0;
        while (response.needRetry() && retry++ < 3) {
            response = bucketManager.delete(bucket, key);
        }
        return response;
    }

	@Override
	public  List<String> uploadImgs(MultipartFile[] file) {
		List<String> list = new LinkedList<>();

		for (int i = 0; i < file.length; i++) {
			String fileName = file[i].getOriginalFilename();

//			if (!dest.getParentFile().exists()) {
//				dest.getParentFile().mkdirs();
//				try {
//					dest.createNewFile();
//				} catch (IOException e) {
//					logger.info("创建文件异常",e);
//				}
//			}
//			BufferedOutputStream out = null;
//			out = new BufferedOutputStream(new FileOutputStream(dest));
//			out.write(file[i].getBytes());
			try {

				Response mRespone= uploadFile(file[i].getInputStream());
				MyPutRet ret =  new Gson().fromJson(mRespone.bodyString(), MyPutRet.class);
				if(mRespone.isOK()){
					//如果不需要对图片进行样式处理，则使用以下方式即可
					//return DOMAIN + ret.key;
					String url = DOMAIN+ ret.key+ STYLE;
					list.add(url);
				}

			} catch (QiniuException e) {
				logger.info("上传图片失败",e);
			} catch (FileNotFoundException e) {
				logger.info("未找到文件",e);
			} catch (IOException e) {
				logger.info("io异常",e);
			}
		}
		return list;
	}


	@Override
    public void afterPropertiesSet() throws Exception {
        this.putPolicy = new StringMap();
		putPolicy.put("callbackBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"fsize\":$(fsize)}");
        //putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
	    //自定义上传名称
	    //putPolicy.put("saveKey", UUID.randomUUID());


    }

	class MyPutRet {
		public String key;
		public String hash;
		public String bucket;
		public long fsize;
	}
    /**
     * 获取上传凭证
     * @return
     */
    private String getUploadToken() {
        return this.auth.uploadToken(bucket, null, 3600, putPolicy);
    }
}

