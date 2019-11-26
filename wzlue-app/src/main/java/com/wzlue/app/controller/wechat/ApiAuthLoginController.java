package com.wzlue.app.controller.wechat;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.wzlue.app.common.config.JwtUtils;
import com.wzlue.common.base.R;
import com.wzlue.common.exception.RRException;
import com.wzlue.common.utils.ConfigConstant;
import com.wzlue.common.utils.DateUtils;
import com.wzlue.sys.entity.SysUserEntity;
import com.wzlue.sys.form.LoginForm;
import com.wzlue.sys.service.SysUserService;
import com.wzlue.wechat.config.WxMpConfiguration;
import com.wzlue.wechat.entity.WxAppEntity;
import com.wzlue.wechat.entity.WxUserEntity;
import com.wzlue.wechat.service.WxAppService;
import com.wzlue.wechat.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信网页授权登陆
 *
 * @author lm
 */
@Slf4j
@RestController
@RequestMapping("/app/wechat/auth")
public class ApiAuthLoginController {

    @Autowired
    private WxAppService wxAppService;

    @Autowired
    private WxUserService wxUserService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SysUserService sysUserService;

    @Value("${authurl.host}")
    private String host;

    /**
     * 构造网页授权url，让用户点击
     *
     * @param request
     * @param response
     * @param appId    公众号 appid
     * @return url
     */
    @GetMapping("/goto_auth_url")
    public String gotoPreAuthUrl(HttpServletRequest request, HttpServletResponse response,
                                 @RequestParam("appId") String appId) {


        if (StringUtils.isBlank(appId)) {
            throw new RRException("appId不能为空");
        }

        // 查询appid是否在数据中
        WxAppEntity appEntity = wxAppService.getById(appId);
        if (null == appEntity && !appId.equals(appEntity.getId())) {
            log.error("goto_auth_url appEntity IS NULL");
            throw new RRException("appId 参数错误");
        }

        final WxMpService wxMpService = WxMpConfiguration.getMpService(appId);

        String url = host + "/app/wechat/auth/login?appId=" + appId;

        url = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);

        return url;

    }


    /**
     * 登陆
     *
     * @param code  微信code
     * @param appId 公众号appid
     * @return
     */
    @PostMapping("/login")
    public R login(@RequestParam("code") String code, @RequestParam("appId") String appId) {

        if (StrUtil.isBlank(code) || StrUtil.isBlank(appId)) {
            log.error("auth_login CODE  IS NULL");
            throw new RRException("登陆失败！", ConfigConstant.AUTH_LOGIN_CODE);
        }

        try {

            // 查询appid是否在数据中
            WxAppEntity appEntity = wxAppService.getById(appId);
            if (null == appEntity || !appId.equals(appEntity.getId())) {
                log.error("auth_login appEntity IS NULL");
                throw new RRException("登陆失败", ConfigConstant.AUTH_LOGIN_APPID);
            }

            final WxMpService wxMpService = WxMpConfiguration.getMpService(appId);

            // 获取accessToken
            WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);

            WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);
            if (null == wxMpUser) {
                log.error("auth_login wxMpUser IS NULL");
                throw new RRException("登陆失败", ConfigConstant.AUTH_LOGIN_NULL);
            }

            // 查询微信用户
            WxUserEntity wxUser = wxUserService.getByAppIdOpenId(appEntity.getId(), wxMpUser.getOpenId());
            // 没有查询到，根据 wxMpUser 插入用户.
            if (null == wxUser) {
                WxMpUserService wxMpUserService = WxMpConfiguration.getMpService(appId).getUserService();

                WxMpUser userWxInfo = wxMpUserService.userInfo(wxMpUser.getOpenId(), null);

                wxUser = new WxUserEntity();
                wxUser.setId(IdUtil.simpleUUID());
                wxUser.setNewTask(0);
                wxUser.setDelFlag("0");
                wxUser.setCreateDate(LocalDateTime.now());
                wxUser.setSubscribeNum(1);
                setWxUserValue(appEntity, wxUser, userWxInfo);

                wxUserService.save(wxUser);
            }

            String token = jwtUtils.generateToken(wxUser.getId());
            //生成token
            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("expire", jwtUtils.getExpire());
            map.put("openid",wxUser.getId());

            log.info("auth_login IS OK", map.toString());
            return R.ok(map);

        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("授权登陆失败", e);
            throw new RRException("登陆失败");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RRException("授权登陆失败");
        }

    }

    /**
     * 平台登陆
     *
     * @param form 登录表单
     * @return
     */
    @PostMapping("/loginP")
    public R loginP(@RequestBody LoginForm form) {

        try {
            SysUserEntity user = sysUserService.queryByUserName(form.getUsername());

            //账号不存在、密码错误
            if (user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
                return R.error("账号或密码不正确");
            }

            // 是否门店
            if (StrUtil.isNotBlank(user.getAppId()) && StrUtil.equals(user.getStore(), "1"))
                // 返回所属门店
                return R.ok().put("appName", user.getAppName()).put("mendianAppId",user.getAppId());
            else
                return R.error("不是门店管理员");
        } catch (Exception e) {
            throw new RRException("登陆失败");
        }

    }

    private void setWxUser(WxUserEntity wxUser, WxMpUser wxMpUser, WxAppEntity wxApp) {
        wxUser.setId(IdUtil.simpleUUID());
        wxUser.setAppType(ConfigConstant.WX_APP_TYPE_2);
        wxUser.setAppId(wxApp.getId());
        wxUser.setSubscribe(ConfigConstant.SUBSCRIBE_TYPE_YES);
        wxUser.setSubscribeScene(wxMpUser.getSubscribeScene());
        wxUser.setOpenId(wxMpUser.getOpenId());
        wxUser.setNickName(wxMpUser.getNickname());
        wxUser.setSex(String.valueOf(wxMpUser.getSex()));
        wxUser.setCity(wxMpUser.getCity());
        wxUser.setCountry(wxMpUser.getCountry());
        wxUser.setProvince(wxMpUser.getProvince());
        wxUser.setLanguage(wxMpUser.getLanguage());
        wxUser.setRemark(wxMpUser.getRemark());
        wxUser.setHeadimgUrl(wxMpUser.getHeadImgUrl());
        wxUser.setTagidList(new Long[]{});
        wxUser.setQrSceneStr(wxMpUser.getQrSceneStr());
        wxUser.setUnionId(wxMpUser.getUnionId());
        wxUser.setCreateDate(LocalDateTime.now());
        wxUser.setUpdateDate(LocalDateTime.now());
    }
    public  void setWxUserValue(WxAppEntity wxApp, WxUserEntity wxUser, WxMpUser userWxInfo) {
        wxUser.setAppType(ConfigConstant.WX_APP_TYPE_2);
        wxUser.setAppId(wxApp.getId());
        wxUser.setSubscribe(ConfigConstant.SUBSCRIBE_TYPE_YES);
        wxUser.setSubscribeScene(userWxInfo.getSubscribeScene());
        wxUser.setSubscribeTime(DateUtils.timestamToDatetime(userWxInfo.getSubscribeTime() * 1000));
        wxUser.setOpenId(userWxInfo.getOpenId());
        wxUser.setNickName(userWxInfo.getNickname());
        wxUser.setSex(String.valueOf(userWxInfo.getSex()));
        wxUser.setCity(userWxInfo.getCity());
        wxUser.setCountry(userWxInfo.getCountry());
        wxUser.setProvince(userWxInfo.getProvince());
        wxUser.setLanguage(userWxInfo.getLanguage());
        wxUser.setRemark(userWxInfo.getRemark());
        wxUser.setHeadimgUrl(userWxInfo.getHeadImgUrl());
        wxUser.setUnionId(userWxInfo.getUnionId());
        wxUser.setGroupId(JSONObject.toJSONString(userWxInfo.getGroupId()));
        wxUser.setTagidList(userWxInfo.getTagIds());
        wxUser.setQrSceneStr(userWxInfo.getQrSceneStr());
        wxUser.setSceneId(Integer.valueOf(userWxInfo.getQrScene()));//参数值
    }
}
