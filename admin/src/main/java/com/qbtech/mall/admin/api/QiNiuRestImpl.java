package com.qbtech.mall.admin.api;

import com.google.gson.Gson;
import com.qbtech.mall.admin.thirdpart.qiniu.IQiniuUploadFileService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/qiniu")
public class QiNiuRestImpl {

	@Autowired
	IQiniuUploadFileService qiniuUploadFileService;



	/**
	 * 上传图片文件七牛云
	 * @param files
	 * @return
	 */
	@RequestMapping(value="/imgs", method = RequestMethod.POST)
	public String uploadImg(@RequestParam("file") MultipartFile[] files) {
		List<String> mResult = qiniuUploadFileService.uploadImgs(files);

		if(mResult==null){
			mResult = new ArrayList<>();
		}
		Gson mGson = new Gson();
		return mGson.toJson(mResult);
	}

	public String uploadFile(){
		return null;
	}

}
