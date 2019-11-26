package com.wzlue.web.controller.wechat;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.wzlue.common.annotation.SysLog;
import com.wzlue.wechat.config.WxMpConfiguration;
import com.wzlue.wechat.entity.ReplyTextMessage;
import com.wzlue.wechat.entity.WxAutoReplyEntity;
import com.wzlue.wechat.service.WxAutoReplyService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lm
 */
@Slf4j
@RestController
@RequestMapping("/portal/{appid}")
public class WxPortalController {
    @Autowired
    private WxAutoReplyService replyService;

    @SysLog("portalGet")
    @GetMapping(produces = "text/plain;charset=utf-8")
    public String authGet(@PathVariable String appid,
                          @RequestParam(name = "signature", required = false) String signature,
                          @RequestParam(name = "timestamp", required = false) String timestamp,
                          @RequestParam(name = "nonce", required = false) String nonce,
                          @RequestParam(name = "echostr", required = false) String echostr) {

        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature,
                timestamp, nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        final WxMpService wxService = WxMpConfiguration.getMpService(appid);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%d]的配置，请核实！", appid));
        }

        if (wxService.checkSignature(timestamp, nonce, signature)) {
            return echostr;
        }

        return "非法请求";
    }

    @SysLog("portalPost")
    @PostMapping(produces = "text/plain;charset=utf-8")
    public void authGet(@PathVariable String appid,
                        @RequestParam(name = "signature", required = false) String signature,
                        @RequestParam(name = "timestamp", required = false) String timestamp,
                        @RequestParam(name = "nonce", required = false) String nonce,
                        @RequestParam(name = "echostr", required = false) String echostr, HttpServletRequest request,
                        HttpServletResponse response
    ) {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("\n接收到来自微信服务器的认证消息：[{}, {}, {}, {}]", signature,
                timestamp, nonce, echostr);
        if (StringUtils.isAnyBlank(signature, timestamp, nonce)) {
            throw new IllegalArgumentException("请求参数非法，请核实!");
        }

        final WxMpService wxService = WxMpConfiguration.getMpService(appid);
        if (wxService == null) {
            throw new IllegalArgumentException(String.format("未找到对应appid=[%d]的配置，请核实！", appid));
        }
        Map<String, String> map = requestToMap(request);
        if (map.size() > 0) {
            List<WxAutoReplyEntity> wxAutoReplyEntities = replyService.queryDefault(appid);
            if (map.get("Event") != null && map.get("Event").equals("subscribe")) {
                String content = "欢迎关注该公众号";
                for (WxAutoReplyEntity wxAutoReplyEntity : wxAutoReplyEntities) {
                    if (wxAutoReplyEntity != null && "1".equals(wxAutoReplyEntity.getType())) {
                        content = wxAutoReplyEntity.getRepContent();
                        break;
                    }
                }
                String replyTextMessage = getReplyTextMessage(content, map.get("FromUserName"), map.get("ToUserName"));
                pw.println(replyTextMessage);
                return;
            }
            String content = "已收到您的留言";
            List<WxAutoReplyEntity> list = replyService.findList(appid, "3", "2", map.get("Content"), null);
            if(list.size()>0){
                content="";
                for (WxAutoReplyEntity wxAutoReplyEntity : list) {
                    content+=wxAutoReplyEntity.getRepContent()+"\n";
                }
                String replyTextMessage = getReplyTextMessage(content, map.get("FromUserName"), map.get("ToUserName"));
                System.out.println("**************************分割线****************************WxPortalController.java");
                System.out.println(replyTextMessage);
                pw.println(replyTextMessage);
                return;
            }
            for (WxAutoReplyEntity wxAutoReplyEntity : wxAutoReplyEntities) {
                System.out.println(wxAutoReplyEntity != null && "3".equals(wxAutoReplyEntity.getType()) && wxAutoReplyEntity.getReqKey().equals(appid+"_default"));
                if (wxAutoReplyEntity != null && "3".equals(wxAutoReplyEntity.getType()) && wxAutoReplyEntity.getReqKey().equals(appid+"_default")) {
                    content = wxAutoReplyEntity.getRepContent();
                    break;
                }
            }
            String replyTextMessage = getReplyTextMessage(content, map.get("FromUserName"), map.get("ToUserName"));
            System.out.println("**************************分割线****************************WxPortalController.java");
            System.out.println(replyTextMessage);
            pw.println(replyTextMessage);

        }

//        if (wxService.checkSignature(timestamp, nonce, signature)) {
//            return echostr;
//        }

//        return "非法请求";
    }

    public Map<String, String> requestToMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap();
        SAXReader reader = new SAXReader();
        InputStream ins = null;
        try {
            ins = request.getInputStream();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Document doc = null;
        try {
            doc = reader.read(ins);
        } catch (DocumentException e1) {
            e1.printStackTrace();
        }
        Element root = doc.getRootElement();
        @SuppressWarnings("unchecked")
        List<Element> list = root.elements();
        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }
        try {
            ins.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return map;
    }

    //    回复文本消息
    private String getReplyTextMessage(String content, String fromUserName, String toUserName) {

        ReplyTextMessage we = new ReplyTextMessage();
        we.setMessageType("text");
        we.setCreateTime(new Long(new Date().getTime()).toString());
        we.setContent(content);
        we.setToUserName(fromUserName);
        we.setFromUserName(toUserName);
        XStream xstream = new XStream(new DomDriver());
        xstream.alias("xml", ReplyTextMessage.class);
        xstream.aliasField("ToUserName", ReplyTextMessage.class, "toUserName");
        xstream.aliasField("CreateTime", ReplyTextMessage.class, "createTime");
        xstream.aliasField("MsgType", ReplyTextMessage.class, "messageType");
        xstream.aliasField("Content", ReplyTextMessage.class, "content");
        xstream.aliasField("FromUserName", ReplyTextMessage.class, "fromUserName");
        String xml = xstream.toXML(we);
        return xml;
    }
}
