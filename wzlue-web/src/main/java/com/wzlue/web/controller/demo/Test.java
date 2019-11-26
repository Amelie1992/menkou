package com.wzlue.web.controller.demo;

import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.LogSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Controller
public class Test {

    private  Logger logger = LoggerFactory.getLogger(Test.class);

    @SysLog("/wx")
    @ResponseBody
    @RequestMapping(value = "/wx", method = {RequestMethod.GET,RequestMethod.POST}, produces = { "application/json;charset=utf-8" })
    public String getWxUserInfo(HttpServletRequest request,
                                @RequestParam(required = false) String echostr,
                                @RequestParam(required = false) String signature,
                                @RequestParam(required = false) String timestamp,
                                @RequestParam(required =false) String nonce
    ) {
        try {

            //只需要把微信请求的 echostr, 返回给微信就可以了
            logger.info("测试来过：echostr===================" + echostr);
            logger.info("测试来过: signature===================" + signature);
            logger.info("测试来过: timestamp===================" + timestamp);
            logger.info("测试来过: nonce===================" + nonce);
            return echostr;
        } catch (Exception e) {
            logger.info("测试微信公众号的接口配置信息发生异常：", e);
            return "错误！！！";
        }

    }
}