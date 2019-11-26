package com.wzlue.web.controller.common;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wzlue.common.ueditor.ActionEnter;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wzlue.common.exception.RRException;
import com.wzlue.common.utils.DateUtils;
import com.wzlue.web.common.config.UeditorConfig;

@RestController
//@CrossOrigin
@RequestMapping("/api/ueditor")
public class ApiUeditorController {
	@Value("${fileupload.filepath}")
	String filepath;
	@Autowired
	UeditorConfig ueditorConfig;

	@RequestMapping("")
	public Object ueditor(String action, MultipartFile upfile, HttpServletRequest req, HttpServletResponse resp){
		if("config".equals(action)){
			Map<String, Object> config = new HashMap<String, Object>();
			config.put("imageActionName", ueditorConfig.getImageActionName());
			config.put("imageUrlPrefix", ueditorConfig.getImageUrlPrefix());
			config.put("imagePath", ueditorConfig.getImagePath());
			config.put("imageFieldName", ueditorConfig.getImageFieldName());
			config.put("imageMaxSize", ueditorConfig.getImageMaxSize());
			config.put("imageAllowFiles", ueditorConfig.getImageAllowFiles());
			return config;
		}else if("uploadimage".equals(action)){
			if (upfile.isEmpty()) {
				throw new RRException("上传文件不能为空");
			}
			
			String filename = upfile.getOriginalFilename();
			
			String suffix = filename.substring(filename.lastIndexOf("."), filename.length());
			
			String uuid = UUID.randomUUID().toString();
			
			String currDate = DateUtils.format(new Date(), "yyyyMMdd");
			
			try {
				FileUtils.writeByteArrayToFile(new File(filepath + "/fileupload/" + currDate + "/" + uuid + suffix), upfile.getBytes());
				Map<String, String> result = new HashMap<String, String>();
				result.put("state", "SUCCESS");
				result.put("type", suffix);
				result.put("size", String.valueOf(upfile.getSize()));
				result.put("url", "/" + currDate + "/" + uuid + suffix);
				return result;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	@RequestMapping(value = "/exec")
	public String exec(HttpServletRequest request) throws Exception {
		request.setCharacterEncoding("utf-8");
		String rootPath = request.getRealPath("/");
		return new ActionEnter( request, rootPath ).exec();
	}
}
