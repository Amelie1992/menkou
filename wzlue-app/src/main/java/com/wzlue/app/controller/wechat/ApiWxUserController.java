package com.wzlue.app.controller.wechat;


import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.wzlue.app.common.annotation.Login;
import com.wzlue.app.common.annotation.LoginUser;

import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.R;

import com.wzlue.common.config.SMSConfig;
import com.wzlue.common.exception.RRException;
import com.wzlue.smsCode.entity.SmsCodeEntity;
import com.wzlue.smsCode.entity.WxAppSmsAccountEntity;
import com.wzlue.smsCode.service.SmsCodeService;
import com.wzlue.smsCode.service.WxAppSmsAccountService;
import com.wzlue.store.service.TStoreIntegralRecordService;
import com.wzlue.sys.AgentDemo.ApiDemo4Java;
import com.wzlue.wechat.entity.WxUserEntity;
import com.wzlue.wechat.service.WxUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.wzlue.sys.AgentDemo.ApiDemo4Java.ACCOUNT_APIKEY;
import static com.wzlue.sys.AgentDemo.ApiDemo4Java.ACCOUNT_SID;


/**
 * 微信用户
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-24 11:11:41
 */
@Slf4j
@RestController
@RequestMapping("/app/wechat/wxuser")
public class ApiWxUserController {

    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private TStoreIntegralRecordService tStoreIntegralRecordService;
    @Autowired
    private SmsCodeService smsCodeService;
    @Autowired
    private WxAppSmsAccountService wxAppSmsAccountService;

    /**
     * 通过id查询微信用户
     *
     * @param userEntity
     * @return R
     */
    @Login
    @GetMapping("/info")
    public R getById(@LoginUser WxUserEntity userEntity) {

        WxUserEntity wxUserEntity = wxUserService.getById(userEntity);

        return R.ok().put("wxuser", wxUserEntity);
    }

    /**
     * 更新微信用户手机号码和微信号
     * <p>
     * 新手任务 共用接口
     *
     * @param userEntity id(openid) 手机号phone  微信号weChat  验证码smsCode
     * @return R
     */
    @SysLog("更新微信用户手机号码和微信号")
//    @Login            @LoginUser WxUserEntity wxUser
    @PostMapping("/updatePhoneWeChat")
    public R updatePhoneWeChat(@RequestBody WxUserEntity userEntity) {
        Date currentTime = new Date();
        Map<String, Object> map = new HashMap<>();
        map.put("openid", userEntity.getId());
        map.put("mobile", userEntity.getPhone());
        List<SmsCodeEntity> codeList = smsCodeService.queryList(map);   //不限今天
        if (null != codeList && codeList.size() > 0) {
            for (SmsCodeEntity code : codeList) {
                if (code.getCode().equals(userEntity.getSmsCode())) {
                    if (currentTime.after(code.getExpirationTime())) {
                        return R.error(1, "您的验证码已失效");   //超过10分钟
                    } else if (code.getState() == 2) {
                        return R.error(1, "您的验证码已失效");   //不是最新的验证码
                    } else {
                        WxUserEntity user = new WxUserEntity();
                        user.setId(userEntity.getId());
                        user.setPhone(userEntity.getPhone());
                        user.setWeChat(userEntity.getWeChat());
                        wxUserService.update(user);
                        WxUserEntity service = wxUserService.getById(userEntity.getId());
                        // 是新手任务
                        if (service != null && service.getNewTask() == 0) {
                            // 是否完成新手任务
                            if (StrUtil.isNotBlank(service.getPhone()) && StrUtil.isNotBlank(service.getWeChat())) {
                                //验证新手任务得积分前提条件
                                try {
                                    tStoreIntegralRecordService.validateOfdoNewbieTask(userEntity.getId());
                                    tStoreIntegralRecordService.doNewbieTask(userEntity.getId());
                                } catch (Exception e) {
                                    throw new RRException(e.getMessage());
                                }
                            }
                        }
                        return R.ok();
                    }
                }
            }
        }
        return R.error(2, "您的验证码不正确");
    }


    @SysLog("发送短信验证码")
//    @Login            @LoginUser WxUserEntity wxUser
    @PostMapping("/sendCode")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "string", value = "openid", paramType = "query", required = true),
            @ApiImplicitParam(name = "phone", dataType = "string", value = "手机号", paramType = "query", required = true)
    })
    @Transactional
    public R sendCode(@RequestBody WxUserEntity wxUser) {
        Date createTime = new Date();

        if (StrUtil.isNotBlank(wxUser.getId())) {

            String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
            if (StrUtil.isNotBlank(wxUser.getPhone()) && ReUtil.contains(regex, wxUser.getPhone())) {

                //TD 请求间隔时间验证
                Map<String, Object> params = new HashMap<>();
                params.put("openid", wxUser.getId());
                params.put("mobile", wxUser.getPhone());
                params.put("sidx", "create_time");
                params.put("odrer", "desc");
                params.put("offset", 0);
                params.put("limit", 1);
                List<SmsCodeEntity> smsCodeEntities = smsCodeService.queryList(params);
                if (null != smsCodeEntities && smsCodeEntities.size() > 0) {
                    SmsCodeEntity latestSmsCode = smsCodeEntities.get(0);
                    if (createTime.before(latestSmsCode.getPermissibleRequestTime())) { //2分钟之内
                        return R.error(3, "距离上次请求时间不足" + latestSmsCode.getIntervalTime() + "分钟");
                    }
                }

                //（该用户该手机今天的）计数
                Map<String, Object> map = new HashMap<>();
                map.put("openid", wxUser.getId());
                map.put("mobile", wxUser.getPhone());
                map.put("today", "today");
                int total = smsCodeService.queryTotal(map);
                Integer toplimit = Integer.valueOf(SMSConfig.getConfig("toplimit"));  //一人一天请求上限：5次
                if (total >= toplimit) {
                    return R.error(4, "您今日的验证码发送量已达上限");
                } else {
                    WxUserEntity wxUserEntity = wxUserService.queryObject(wxUser.getId());
//                    String mendian = SMSConfig.getConfig(wxUserEntity.getAppId());
                    WxAppSmsAccountEntity smsAccount = wxAppSmsAccountService.queryObject(wxUserEntity.getAppId());

//                    if (StrUtil.isNotBlank(mendian)) {
                    if (null != smsAccount) {
//                        String sid = SMSConfig.getConfig(mendian + ACCOUNT_SID);    //账户
//                        String apikey = SMSConfig.getConfig(mendian + ACCOUNT_APIKEY);  //秘钥
//                        String tplId = SMSConfig.getConfig(mendian + "tplId");  //模板id
                        String yxq = SMSConfig.getConfig("Term_Of_Validity");   //有效时间: 10分钟
                        String intervalTime = SMSConfig.getConfig("Interval_Time"); //请求间隔时间: 2分钟
                        String smsCode = String.valueOf((Math.random() * 9 + 1) * 1000).substring(0, 4);//验证码

//                        String resultJson = ApiDemo4Java.sendCode(sid, apikey, tplId, smsCode, yxq, wxUser.getPhone());  //验证码接口
                        String resultJson = ApiDemo4Java.sendCode(smsAccount.getAccountSid(), smsAccount.getAccountApikey(),
                                smsAccount.getTplId(), smsCode, yxq, wxUser.getPhone());  //验证码接口
                        Map resultMap = JSON.parseObject(resultJson, Map.class);
                        Integer code = Integer.valueOf(resultMap.get("code").toString());
                        String msg = (String) resultMap.get("msg");
                        if (code == 0) {
                            //TD  验证码存入数据库
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(createTime);
                            calendar.add(calendar.MINUTE, Integer.parseInt(yxq));

                            Calendar instance = Calendar.getInstance();
                            instance.setTime(createTime);
                            instance.add(instance.MINUTE, Integer.parseInt(intervalTime));

                            SmsCodeEntity smsCodeEntity = new SmsCodeEntity();
                            smsCodeEntity.setOpenid(wxUser.getId());
                            smsCodeEntity.setMobile(wxUser.getPhone());
                            smsCodeEntity.setCode(smsCode); //验证码
                            smsCodeEntity.setCreateTime(createTime);
                            smsCodeEntity.setEffectiveTime(yxq);    //有效时间
                            smsCodeEntity.setExpirationTime(calendar.getTime());    //过期时间
                            smsCodeEntity.setIntervalTime(intervalTime);    //请求间隔时间
                            smsCodeEntity.setPermissibleRequestTime(instance.getTime());    //请求允许时间
                            smsCodeEntity.setCount(total + 1);
                            smsCodeService.save(smsCodeEntity);

                            //删除该用户手机号 今天之前的验证码
                            smsCodeService.deleteBeforeToday(smsCodeEntity);
                            //该用户手机号 之前的验证码失效
                            smsCodeService.invalid(smsCodeEntity);
                            return R.ok(msg);
                        } else {
                            return R.error(code, msg);
                        }
                    } else {
                        return R.error(5, "此公众号未开通短信验证码功能");
                    }
                }
            } else {
                return R.error(2, "手机号未通过验证");
            }
        } else {
            return R.error(1, "缺少openid");
        }
    }


}
