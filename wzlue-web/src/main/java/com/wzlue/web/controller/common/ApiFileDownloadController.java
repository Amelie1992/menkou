package com.wzlue.web.controller.common;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@RestController
@RequestMapping("/web/fileDownload")
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

    @GetMapping("/downLoadFiles")
    public  @ResponseBody
    void downLoadFiles(String  urls, HttpServletResponse response) {
        if (!StringUtils.isBlank(urls)){
            String [] urlList = urls.split(",");
            //后台分隔数组 循环 读取file 然后二进制流写出知道了     你现在  这样    写一个a标签    然后rurl'
            for (String url: urlList) {
                String fileName = url.substring(url.indexOf("/fileupload") + 1, url.length());

                File file = new File(filePath+'/'+fileName);

                response.setCharacterEncoding("utf-8");
                response.setContentType("application/octet-stream");
                response.addHeader("Content-Length", "" + file.length());
//                response.setHeader("Content-Disposition", "attachment;filename=" + filePath + "/" + fileName);
                response.setHeader("Content-Disposition", "attachment;filename="+ filePath+"/"+fileName);
                byte[] buff = new byte[1024];
                BufferedInputStream bis = null;
                OutputStream os = null; //输出流
                try {
                    os = response.getOutputStream();
                    // filePath + url是指欲下载的文件的路径
                    // 以流的形式下载文件
                    bis = new BufferedInputStream(new FileInputStream(file));//文件输入流
                    int i ;
                    while ((i=bis.read(buff)) != -1) {
                        os.write(i);
//                        os.flush();
//                        i = bis.read(buff);     你把他骑起来   我看看
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                            os.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }else{
            System.out.printf("下载失败,文件异常");
        }
    }
    @GetMapping("/getFileExist")
    public Boolean getFileExist(String urlStr){
        URL url = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int state = conn.getResponseCode();
            if(state == 200){
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }





    /**
     * 通用下载请求
     *
     * @param fileName 文件名称
     * @param delete 是否删除
     */
    //@GetMapping("/commonDownload")
    @RequestMapping("/commonDownload")
    public void fileDownload(String fileName, Boolean delete, HttpServletResponse response, HttpServletRequest request)
    {
        try
        {
            if (!FileUtils.isValidFilename(fileName))
            {
                throw new Exception(StrUtil.format("文件名称({})非法，不允许下载。 ", fileName));
            }
            String realFileName = System.currentTimeMillis() + fileName.substring(fileName.indexOf("_") + 1);
            String downloadFilePath = filePath +"/"+ fileName;

            response.setCharacterEncoding("utf-8");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, realFileName));
            FileUtils.writeBytes(downloadFilePath, response.getOutputStream());
            if (delete)
            {
                FileUtils.deleteFile(downloadFilePath);
            }
        }
        catch (Exception e)
        {
            log.error("下载文件失败", e);
        }
    }
}
