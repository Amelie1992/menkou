package com.wzlue.common.utils;

public class EscapeUtil {

    //html反转义   将转义“'，&，<，"，>”五个字符
    public static String unescape(String content) {
        if (content == null) {
            return "";
        }
        String html = content;
        html = html.replace("&lt;", "<").replace("&gt;", ">").replace("&amp;", "&")
                .replace("&quot;", "\"").replace("&#39;", "'");
        return html;

    }
}
