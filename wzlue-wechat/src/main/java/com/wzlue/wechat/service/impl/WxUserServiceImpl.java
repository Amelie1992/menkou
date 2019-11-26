package com.wzlue.wechat.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.wzlue.common.utils.ConfigConstant;
import com.wzlue.common.utils.DateUtils;
import com.wzlue.wechat.config.WxMpConfiguration;
import com.wzlue.wechat.entity.WxAppEntity;
import com.wzlue.wechat.service.WxAppService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpUserService;
import me.chanjar.weixin.mp.api.WxMpUserTagService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.result.WxMpUserList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import com.wzlue.wechat.dao.WxUserDao;
import com.wzlue.wechat.entity.WxUserEntity;
import com.wzlue.wechat.service.WxUserService;
import org.springframework.transaction.annotation.Transactional;


@Service("wxUserService")
public class WxUserServiceImpl implements WxUserService {
    @Autowired
    private WxUserDao wxUserDao;
    @Autowired
    private WxAppService wxAppService;

    @Override
    public WxUserEntity queryObject(String id) {
        return wxUserDao.queryObject(id);
    }

    @Override
    public List<WxUserEntity> queryList(Map<String, Object> map) {
        return wxUserDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return wxUserDao.queryTotal(map);
    }

    @Override
    public void save(WxUserEntity wxUser) {
        wxUserDao.save(wxUser);
    }

    @Override
    public void update(WxUserEntity wxUser) {
        wxUserDao.update(wxUser);
    }

    @Override
    public void delete(String id) {
        wxUserDao.delete(id);
    }

    @Override
    public void deleteBatch(String[] ids) {
        wxUserDao.deleteBatch(ids);
    }

    @Override
    public WxUserEntity getByAppIdOpenId(String appId, String openId) {
        return wxUserDao.getByAppIdOpenId(appId, openId);
    }

    @Override
    public void updateById(WxUserEntity wxUser) {
        wxUserDao.update(wxUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRemark(WxUserEntity entity) throws WxErrorException {
        String id = entity.getId();
        String remark = entity.getRemark();
        String appId = entity.getAppId();
        String openId = entity.getOpenId();
        entity = new WxUserEntity();
        entity.setId(id);
        entity.setRemark(remark);
        wxUserDao.update(entity);
        WxMpUserService wxMpUserService = WxMpConfiguration.getMpService(appId).getUserService();
        wxMpUserService.userUpdateRemark(openId, remark);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void tagging(String taggingType, String appId, Long tagId, String[] openIds) throws WxErrorException {
        WxMpUserTagService wxMpUserTagService = WxMpConfiguration.getMpService(appId).getUserTagService();
        WxUserEntity wxUser;
        if ("tagging".equals(taggingType)) {
            for (String openId : openIds) {
                wxUser = this.getByAppIdOpenId(appId, openId);
                Long[] tagidList = wxUser.getTagidList();
                List<Long> list = Arrays.asList(tagidList);
                list = new ArrayList<>(list);
                if (!list.contains(tagId)) {
                    list.add(tagId);
                    tagidList = list.toArray(new Long[list.size()]);
                    wxUser.setTagidList(tagidList);
                    this.updateById(wxUser);
                }
            }
            wxMpUserTagService.batchTagging(tagId, openIds);
        }
        if ("unTagging".equals(taggingType)) {
            for (String openId : openIds) {
                wxUser = this.getByAppIdOpenId(appId, openId);
                Long[] tagidList = wxUser.getTagidList();
                List<Long> list = Arrays.asList(tagidList);
                list = new ArrayList<>(list);
                if (list.contains(tagId)) {
                    list.remove(tagId);
                    tagidList = list.toArray(new Long[list.size()]);
                    wxUser.setTagidList(tagidList);
                    this.updateById(wxUser);
                }
            }
            wxMpUserTagService.batchUntagging(tagId, openIds);
        }
    }

    @Override
    public WxUserEntity getById(String id) {
        return wxUserDao.queryObject(id);
    }

    @Override
    public WxUserEntity getById(WxUserEntity userEntity) {
        return wxUserDao.getById(userEntity);
    }

    @Override
    public WxUserEntity queryByOpenid(String openid) {
        return wxUserDao.queryByOpenid(openid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void synchroWxUser(String appId, Long userId) throws WxErrorException {
        //先将已关注的用户取关
        WxUserEntity wxUser = new WxUserEntity();
        wxUser.setSubscribe(ConfigConstant.SUBSCRIBE_TYPE_NO);

        wxUserDao.updateByAppidSub(wxUser, appId, ConfigConstant.SUBSCRIBE_TYPE_YES);

        String nextOpenid = null;
        WxMpUserService wxMpUserService = WxMpConfiguration.getMpService(appId).getUserService();
        WxAppEntity wxApp = wxAppService.getById(appId);
        this.recursionGet(wxApp, wxMpUserService, nextOpenid, userId);
    }

    /**
     * 递归获取
     *
     * @param nextOpenid
     * @param userId
     */
    void recursionGet(WxAppEntity wxApp, WxMpUserService wxMpUserService, String nextOpenid, Long userId) throws WxErrorException {
        WxMpUserList userList = wxMpUserService.userList(nextOpenid);
        List<WxUserEntity> listWxUser = new ArrayList<>();
        WxUserEntity wxUser;
        for (String openid : userList.getOpenids()) {
            WxMpUser userWxInfo = wxMpUserService.userInfo(openid, null);
            wxUser = this.getByAppIdOpenId(wxApp.getId(), openid);
            if (wxUser == null) {//用户未存在
                wxUser = new WxUserEntity();
                wxUser.setSubscribeNum(1);
                wxUser.setId(IdUtil.simpleUUID());
                wxUser.setCreateId(userId.toString());
                wxUser.setCreateDate(LocalDateTime.now());
                wxUser.setNewTask(0);
                wxUser.setDelFlag("0");
                wxUser.setSubscribeNum(1);
            } else {
                wxUser.setUpdateDate(LocalDateTime.now());
                wxUser.setUpdateId(String.valueOf(userId));
            }
            setWxUserValue(wxApp, wxUser, userWxInfo);
            listWxUser.add(wxUser);
        }

        wxUserDao.saveOrUpdateBatch(listWxUser);

        if (nextOpenid != null && userList.getCount() <= 10000) {
            this.recursionGet(wxApp, wxMpUserService, userList.getNextOpenid(), userId);
        }
    }
    public void setWxUserValue(WxAppEntity wxApp, WxUserEntity wxUser, WxMpUser userWxInfo) {
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
    }
}
