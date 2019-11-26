package com.wzlue.sys.AgentDemo;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.wzlue.common.base.R;
import com.wzlue.smsCode.entity.SmsCodeEntity;
import lombok.Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

/**
 * 短信代理平台HTTP接口Java开发示�?
 * 具体接口说明请查看文档《短信代理平台HTTP接口文档.docx�?
 * 短信发送接口与其他业务接口区别�?
 * 1.请求地址端口不同，详细查看示例说�?
 * 2.短信发送采用POST方式并设置请求头信息，而其他业务接口采用GET方式;
 *
 * @author Peng_Hu
 * @ClassName: ApiDemo4Java
 * @Description: TODO
 * @date 2017�?�?4�?上午11:44:20
 */
@Data
public class ApiDemo4Java {


    /**
     * 账号
     */
//        static String ACCOUNT_SID = "账号";
    public static String ACCOUNT_SID = "ACCOUNT_SID";

    /**
     * APIKEY   秘钥
     */
//        static String ACCOUNT_APIKEY = "秘钥";
    public static String ACCOUNT_APIKEY = "ACCOUNT_APIKEY";


    /**
     * utf8编码
     */
    static final String CHARSET_UTF8 = "utf-8";
    static final String CHARSET_GBK = "gbk";

    /**
     * 短信发送接口请求地址
     */
    static String SendSmsHttpPostUrl = "http://101.37.77.10:7090/api/rest";
    /**
     * 其他HTTP接口地址
     */
    static String OtherApiHttpGetUrl = "http://101.37.77.10:7099/api/rest";


//    static Map<String, String> smsCode = new HashMap<String, String>(); //后期存放redis


    //发送验证码
    //    您的验证码：@1@，@2@分钟之内有效，为保证账户安全，请勿泄漏给他人。
    //                                   账户        秘钥           模板id        验证码          有效时间    手机号
    public static String sendCode(String sid, String apikey, String tplId, String smsCode, String yxq, String mobile) {

//        int code = (int) ((Math.random() * 9 + 1) * 1000);
//        smsCode.put(mobile, String.valueOf(code));

        return sendTplSms(sid, apikey, tplId, "@1@=" + smsCode + "||@2@=" + yxq, mobile, "");

    }

//    //验证验证码
//    public static boolean checkCode(String mobile, String code) {
//
//        if (code.equals(smsCode.get(mobile))) {
//            return true;
//        }
//        return false;
//    }


    //供人通知（用人方）
    //    小伙伴@1@给您发送了一条供人信息，赶紧去查看哦！
    //                               账户        秘钥           模板id        内容            手机号
    public static void sendGR(String sid, String apikey, String tplId, String content, String mobile) {

        sendTplSms(sid, apikey, tplId, "@1@=" + content, mobile, "");
    }


    //供人审核通过通知（供人方）
    //    恭喜您，您发送的供人信息已被@1@确认接收，请登录后台及时查看哦！
    //                               账户        秘钥           模板id        内容            手机号
    public static void sendSH(String sid, String apikey, String tplId, String content, String mobile) {

        sendTplSms(sid, apikey, tplId, "@1@=" + content, mobile, "");
    }

    /**
     * @param args void
     */
    public static void main(String[] args) {
        /******获取短信账号信息**************/
//		queryUser();

        /******查询国际短信资费******/
        queryUserGjPrices();
        /******获取某个模板信息**************/
//		queryTemplateById("4C08CFDDB8494DA6931364D3AEC41352");

        /******获取账号所有模板信�?*************/
//		queryTemplateByAccount();

//		Random r = new Random();
        /******发送普通短�?*************/
//		sendSms("【短信签名】您的验证码�?+r.nextInt(10000), "13813815251,15952026969,13813865117,15951977097,18061695105","01703");

        /******发送模板短�?*************/
//		sendTplSms("4C08CFDDB8494DA6931364D3AEC41352", "@1@="+r.nextInt(10000)+"||@2@=短信应用", "15951977097","");

        /*****发送国际短�?*******/
        sendGlobalSms("this is test sms,the virefy is 8888.", "890000", "");

        /*****获取状态报�?*******/
//		queryRpt();

        /*****获取上行短信********/
//		queryMO();
    }

    /**
     * 获取账号国际短信资费
     *
     * @return String
     */
    public static String queryUserGjPrices() {
        //签名
        String sign = "";
        try {
            sign = md5Digest(ACCOUNT_SID + ACCOUNT_APIKEY);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //请求url
        StringBuilder url = new StringBuilder();
        url.append(OtherApiHttpGetUrl).append("/user/price/gets").append("?sid=").append(ACCOUNT_SID).append("&sign=").append(sign);
        String resultJson = sendHttpGet(url.toString());
        System.out.println("/user/price/gets=" + resultJson);
        return resultJson;
    }

    /**
     * 发送国际短�?
     * 支持同时发送国际短信和国内短信
     *
     * @param content 必填	短信内容
     * @param mobiles 手机号码，多个以英文逗号隔开，最�?0000个号码，（号码前面必须添加国家区号，区号+号码）如:8613800000000,61423547790,85264859465
     * @param extno   自定义扩展，建议1-3位，只适用国内短信
     * @return String
     */
    public static String sendGlobalSms(String content, String mobiles, String extno) {
        String sign = "";
        try {
            StringBuilder signStr = new StringBuilder();
            signStr.append(ACCOUNT_SID).append(ACCOUNT_APIKEY).append(mobiles);
            //签名=md5(sid+apikey+mobile)
            sign = md5Digest(signStr.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder url = new StringBuilder();
        url.append(SendSmsHttpPostUrl).append("/sms/sendGlobalSms");//.append("?sid=").append(ACCOUNT_SID).append("&sign=").append(sign);
        System.out.println(url);
        Map<String, String> params = new HashMap<String, String>();
        params.put("sid", ACCOUNT_SID);
        params.put("sign", sign);
        params.put("mobile", mobiles);
        params.put("content", content);
        params.put("extno", extno);
        String resultJson = sendHttpPost(url.toString(), params);
        System.out.println("/sms/sendGlobalSms=" + resultJson);
        return resultJson;
    }

    /**
     * 发送普通短�?
     *
     * @param content 短信内容
     * @param mobiles 手机号码  （多个以英文逗号隔开，最�?0000个号码）
     * @param extno   1.	自定义签名时，填写报备签名所对应的扩展子�?
     *                2.	强制签名时，自定义扩展，建议1-3�?
     * @return String
     */
    public static String sendSms(String content, String mobiles, String extno) {
        String sign = "";
        try {
            /**
             * 签名认证 sign=md5(sid+apikey+tplid+mobile+content);
             * 若sid和apikey确定无误，请求返回值返�?005错误码（sign参数签名认证错误），
             * 处理方法：短信内容包含中文字符，请采用utf-8或gb2312转码后进行md5签名后提交发�?
             */
            StringBuilder signStr = new StringBuilder();
            signStr.append(ACCOUNT_SID).append(ACCOUNT_APIKEY).append(mobiles).append(content);
            sign = md5Digest(signStr.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder url = new StringBuilder();
        url.append(SendSmsHttpPostUrl).append("/sms/sendSms");

        //添加POST请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("sid", ACCOUNT_SID);
        params.put("sign", sign);
        params.put("mobile", mobiles);
        params.put("content", content);
        //params.put("time", "20170222120222");  //定时短信：格�?yyyyMMddmmhhss
        params.put("extno", extno);

        //发送Http Post请求
        String resultJson = sendHttpPost(url.toString(), params);
        System.out.println("/sms/sendTplSms=" + resultJson);
        return resultJson;
    }


    /**
     * 发送模板短�?
     *
     * @param tplId   模板编号
     * @param content 参数值，多个参数以“||”隔开   �?@1@=HY001||@2@=3281
     * @param mobiles 手机号码，多个以英文逗号隔开，最�?0000个号�?
     * @param extno   自定义扩展，建议1-3�?
     * @return String
     */
//  public static String sendTplSms(String tplId, String content, String mobiles, String extno) {
    public static String sendTplSms(String sid, String apikey, String tplId, String content, String mobiles, String extno) {
        //签名
        String sign = "";
        try {
            /**
             * 签名认证 sign=md5(sid+apikey+tplid+mobile+content);
             * 若sid和apikey确定无误，请求返回值返�?005错误码（sign参数签名认证错误），
             * 处理方法：短信内容包含中文字符，请采用utf-8或gb2312转码后进行md5签名后提交发�?
             */
            StringBuilder signStr = new StringBuilder();
//            signStr.append(ACCOUNT_SID).append(ACCOUNT_APIKEY).append(tplId).append(mobiles).append(content);
            signStr.append(sid).append(apikey).append(tplId).append(mobiles).append(content);

            sign = md5Digest(signStr.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuilder url = new StringBuilder();
        url.append(SendSmsHttpPostUrl).append("/sms/sendTplSms");
        //添加POST请求参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("sid", sid);
        params.put("sign", sign);
        params.put("tplid", tplId);
        params.put("mobile", mobiles);
        params.put("content", content);
        params.put("extno", extno);

        //发送Http Post请求
        String resultJson = sendHttpPost(url.toString(), params);
        System.out.println("/sms/sendTplSms=" + resultJson);
        return resultJson;
    }

    /**
     * 查询账号信息
     * /user/get?sid={sid}&sign={sign}
     *
     * @return json字符�?详细描述请参考接口文�?
     * String
     */
//    public static String queryUser() {
    public static String queryUser(String sid, String apikey) {
        String sign = "";
        try {
            //签名认证 sign=md5(sid+apikey);
//            sign = md5Digest(ACCOUNT_SID + ACCOUNT_APIKEY);
            sign = md5Digest(sid + apikey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //请求url
        StringBuilder url = new StringBuilder();
//        url.append(OtherApiHttpGetUrl).append("/user/get").append("?sid=").append(ACCOUNT_SID).append("&sign=").append(sign);
        url.append(OtherApiHttpGetUrl).append("/user/get").append("?sid=").append(sid).append("&sign=").append(sign);

        //发送Http Get请求
        String resultJson = sendHttpGet(url.toString());
        System.out.println("/user/get=" + resultJson);
        return resultJson;
    }


    /**
     * 查询某个模板信息
     * /tpl/gets?sid={sid}&sign={sign}
     *
     * @return String
     */
    public static String queryTemplateByAccount() {
        String sign = "";
        try {
            //签名认证 sign=md5(sid+apikey);
            sign = md5Digest(ACCOUNT_SID + ACCOUNT_APIKEY);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //请求url
        StringBuilder url = new StringBuilder();
        url.append(OtherApiHttpGetUrl).append("/tpl/gets").append("?sid=").append(ACCOUNT_SID).append("&sign=").append(sign);

        //发送Http Get请求
        String resultJson = sendHttpGet(url.toString());
        System.out.println("/tpl/gets=" + resultJson);
        return resultJson;
    }

    /**
     * 查询某个模板信息
     * /tpl/get?sid={sid}&sign={sign}&tplid={tplid}
     *
     * @return String
     */
    public static String queryTemplateById(String tplId) {
        //签名
        String sign = "";
        try {
            //签名认证 sign=md5(sid+apikey);
            sign = md5Digest(ACCOUNT_SID + ACCOUNT_APIKEY + tplId);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //请求url
        StringBuilder url = new StringBuilder();
        url.append(OtherApiHttpGetUrl).append("/tpl/get").append("?sid=").append(ACCOUNT_SID).append("&sign=").append(sign).append("&tplid=").append(tplId);

        //发送Http Get请求
        String resultJson = sendHttpGet(url.toString());
        System.out.println("/tpl/get=" + resultJson);
        return resultJson;
    }

    /**
     * 获取状态报�?
     * 每次请求只能获取未被获取过的短信状态报告，已获取的报告不会重复获取;单次请求最多获�?0000条状态报告记录�?
     *
     * @return json字符�?详细描述请参考接口文�?
     * String
     */
    public static String queryRpt() {
        String sign = "";
        try {
            //签名认证 sign=md5(sid+apikey);
            sign = md5Digest(ACCOUNT_SID + ACCOUNT_APIKEY);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //请求url
        StringBuilder url = new StringBuilder();
        url.append(OtherApiHttpGetUrl).append("/report/query").append("?sid=").append(ACCOUNT_SID).append("&sign=").append(sign);

        //发送Http Get请求
        String resultJson = sendHttpGet(url.toString());
        System.out.println("resultJson=" + resultJson);
        return resultJson;
    }


    /**
     * 获取上行状态报�?
     * 每次请求只能获取未被获取过的上行短信，已获取的上行短信不会重复获�?单次请求最多获�?0000条上行短信记�?
     *
     * @return json字符�?详细描述请参考接口文�?
     * String
     */
    public static String queryMO() {
        String sign = "";
        try {
            //签名认证 sign=md5(sid+apikey);
            sign = md5Digest(ACCOUNT_SID + ACCOUNT_APIKEY);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //请求url
        StringBuilder url = new StringBuilder();
        url.append(OtherApiHttpGetUrl).append("/deliver/query").append("?sid=").append(ACCOUNT_SID).append("&sign=").append(sign);

        //发送Http Get请求
        String resultJson = sendHttpGet(url.toString());
        System.out.println("resultJson=" + resultJson);
        return resultJson;
    }


    /***
     *
     * @param apiUrl 接口请求地址
     * @param paramsMap 请求参数集合
     * @return json字符�?详细描述请参考接口文�?
     * String
     */
    private static String sendHttpGet(String apiUrl) {
        String responseText = "";
        BufferedReader br = null;
        try {
            URL url = new URL(apiUrl);
            URLConnection connection = url.openConnection();
            //读取响应返回�?
            InputStream is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, CHARSET_UTF8));
            String temp = "";
            while ((temp = br.readLine()) != null) {
                responseText += temp;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return responseText;
    }


    /***
     *
     * @param apiUrl 接口请求地址
     * @param paramsMap 请求参数集合
     * @return json字符�?详细描述请参考接口文�?
     * String
     */
    private static String sendHttpPost(String apiUrl, Map<String, String> paramsMap) {
        String responseText = "";
        BufferedReader br = null;
        StringBuilder params = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = paramsMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            try {
                params.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue(), CHARSET_UTF8)).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
//			params.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
        }
        try {
            URL url = new URL(apiUrl);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), CHARSET_UTF8);
            out.write(params.toString()); //post的关键所在！
            out.flush();
            out.close();
            //读取响应返回�?
            InputStream is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, CHARSET_UTF8));
            String temp = "";
            while ((temp = br.readLine()) != null) {
                responseText += temp;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (br != null) {
                br.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseText;
    }


    private static String md5Digest(String src) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(src.getBytes(CHARSET_UTF8));
        return byte2HexStr(b);
    }

    private static String byte2HexStr(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; ++i) {
            String s = Integer.toHexString(b[i] & 0xFF);
            if (s.length() == 1)
                sb.append("0");
            sb.append(s.toUpperCase());
        }
        return sb.toString();
    }

}

