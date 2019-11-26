package com.wzlue.app.controller.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping("/app/fileDownload")
public class ApiFileDownloadController {

    @Value("${fileupload.filepath}")
    String filePath;


    @GetMapping("/downLoad")
    public void downLoad(String url, HttpServletResponse response) {
        String fileName = url.substring(url.lastIndexOf("/") + 1, url.length());
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null; //输出流
        try {
            os = response.getOutputStream();
            // filePath + url是指欲下载的文件的路径
            // 以流的形式下载文件
            bis = new BufferedInputStream(new FileInputStream(new File(filePath + url)));//文件输入流
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
