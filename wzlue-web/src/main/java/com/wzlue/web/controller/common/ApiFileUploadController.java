package com.wzlue.web.controller.common;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wzlue.common.base.R;
import com.wzlue.common.exception.RRException;
import com.wzlue.common.utils.DateUtils;

@RestController
@RequestMapping("/web/file")
public class ApiFileUploadController {

//   公众号校验文件 路径
    @Value("${fileupload.filepath1}")
    String filePath1;

    @Value("${fileupload.filepath2}")
    String filePath2;
//
	@Value("${fileupload.filepath}")
	String filePath;

	@Value("${fileupload.server}")
	String serverUrl;


	/**
	 * 上传文件
	 */
	@RequestMapping("/ckeditorUpload")
	public String ckeditorUpload(@RequestParam("upload") MultipartFile file, String CKEditorFuncNum) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}

		String filename = file.getOriginalFilename();

		String suffix = filename.substring(filename.lastIndexOf("."), filename.length());

		String uuid = UUID.randomUUID().toString();

		String currDate = DateUtils.format(new Date(), "yyyyMMdd");

		FileUtils.writeByteArrayToFile(new File(filePath + "/fileupload/" + currDate + "/" + uuid + suffix), file.getBytes());

		StringBuffer sb=new StringBuffer();
	    sb.append("<script type=\"text/javascript\">");
	    sb.append("window.parent.CKEDITOR.tools.callFunction("+ CKEditorFuncNum + ",'" +serverUrl + "/" + currDate + "/" + uuid + suffix +"')");
	    sb.append("</script>");

		return sb.toString();
	}


	/**
	 * 上传文件
	 */
	@RequestMapping("/upload")
	public R upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}

		String filename = file.getOriginalFilename();

		String suffix = filename.substring(filename.lastIndexOf("."), filename.length());

		String uuid = UUID.randomUUID().toString();

		String currDate = DateUtils.format(new Date(), "yyyyMMdd");

		FileUtils.writeByteArrayToFile(new File(filePath + "/fileupload/" + currDate + "/" + uuid + suffix), file.getBytes());

		return R.ok().put("url", serverUrl + "/" + currDate + "/" + uuid + suffix);
	}

	/**
	 * 上传文件(Base64)
	 */
	@RequestMapping("/uploadBase64")
	public R uploadBase64(String base64File) throws Exception {
		if (base64File.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}

		Base64 base64 = new Base64();
		// 解密
		byte[] b = base64.decode(base64File);

		String uuid = UUID.randomUUID().toString();

		String currDate = DateUtils.format(new Date(), "yyyyMMdd");

		FileUtils.writeByteArrayToFile(new File(filePath + "/fileupload/" + currDate + "/" + uuid + ".jpg"), b);

		return R.ok().put("url", serverUrl + "/" + currDate + "/" + uuid + ".jpg");
	}


	@RequestMapping("/uploadFiles")
	public R upload(@RequestParam("files") MultipartFile [] files) throws Exception {
		if (files.length <= 0) {
			throw new RRException("上传文件不能为空");
		}
		for (MultipartFile file : files ) {

			String filename = file.getOriginalFilename();

			String suffix = filename.substring(filename.lastIndexOf("."), filename.length());

			String uuid = UUID.randomUUID().toString();

			String currDate = DateUtils.format(new Date(), "yyyyMMdd");

			FileUtils.writeByteArrayToFile(new File(filePath + "/fileinput/" + currDate + "/" + uuid + suffix), file.getBytes());
		}

//		return R.ok().put("url", serverUrl + "/" + currDate + "/" + uuid + suffix);
		return R.ok();
	}


    /**
     * 上传公众号校验文件
     */
    @RequestMapping("/uploadCheckFile")
    public R uploadCheckFile(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }

        String filename = file.getOriginalFilename();

        String suffix = filename.substring(filename.lastIndexOf("."), filename.length());

        String uuid = UUID.randomUUID().toString();

        String currDate = DateUtils.format(new Date(), "yyyyMMdd");

        File file1 = new File(filePath1 +filename);

        File file2 = new File(filePath2 + filename);

        FileUtils.writeByteArrayToFile(file1, file.getBytes());

        FileUtils.copyFile(file1,file2);

        return R.ok().put("url", serverUrl + "/" + currDate + "/" + uuid + suffix);
    }
}
