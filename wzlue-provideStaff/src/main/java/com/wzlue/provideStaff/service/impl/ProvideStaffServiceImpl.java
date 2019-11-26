package com.wzlue.provideStaff.service.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import com.wzlue.common.config.SMSConfig;
import com.wzlue.notice.dao.NoticeDao;
import com.wzlue.notice.entity.NoticeEntity;
import com.wzlue.provideStaff.dao.ProvideStaffDao;
import com.wzlue.provideStaff.entity.ProvideStaffEntity;
import com.wzlue.provideStaff.service.ProvideStaffService;
import com.wzlue.recruitment.dao.ApplicationMaterialsDao;
import com.wzlue.recruitment.dao.ShopRecruitmentDao;
import com.wzlue.recruitment.entity.ApplicationMaterialsEntity;
import com.wzlue.recruitment.entity.ShopRecruitmentEntity;
import com.wzlue.smsCode.dao.WxAppSmsAccountDao;
import com.wzlue.smsCode.entity.WxAppSmsAccountEntity;
import com.wzlue.smsCode.service.WxAppSmsAccountService;
import com.wzlue.sys.AgentDemo.ApiDemo4Java;
import com.wzlue.wechat.dao.WxAppDao;
import com.wzlue.wechat.entity.WxAppEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;


@Service("provideStaffService")
public class ProvideStaffServiceImpl implements ProvideStaffService {
    @Autowired
    private ProvideStaffDao provideStaffDao;
    @Autowired
    private ShopRecruitmentDao shopRecruitmentDao;
    @Autowired
    private ApplicationMaterialsDao applicationMaterialsDao;
    @Autowired
    private NoticeDao noticeDao;
    @Autowired
    private WxAppDao wxAppDao;
    @Autowired
    private WxAppSmsAccountDao wxAppSmsAccountDao;

    @Override
    public ProvideStaffEntity queryObject(Long id) {
        return provideStaffDao.queryObject(id);
    }

    @Override
    public List<ProvideStaffEntity> queryList(Map<String, Object> map) {
        return provideStaffDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return provideStaffDao.queryTotal(map);
    }

    @Override
    public void save(ProvideStaffEntity provideStaff) {
        provideStaffDao.save(provideStaff);
        //TD 短信提醒·用人方
        //shopRecruitment      applicationMaterials        phone
        ApplicationMaterialsEntity applicationMaterialsEntity = applicationMaterialsDao.queryByRecruitmentId(provideStaff.getShopRecruitmentId());
        if (null != applicationMaterialsEntity) {
            String phone = applicationMaterialsEntity.getPhone();
            String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
            if (StrUtil.isNotBlank(phone) && ReUtil.contains(regex, phone)) {
//                String sid = SMSConfig.getConfig("mkmz.ACCOUNT_SID");
//                String apikey = SMSConfig.getConfig("mkmz.ACCOUNT_APIKEY");
//                String tplIdGR = SMSConfig.getConfig("mkmz.tplIdGR");
                WxAppEntity wxAppEntity = wxAppDao.queryObject(provideStaff.getAppId());
//                ApiDemo4Java.sendGR(sid, apikey, tplIdGR, wxAppEntity.getName(), phone);

                String mkmzAppId = SMSConfig.getConfig("mkmzAppId");
                WxAppSmsAccountEntity smsAccount = wxAppSmsAccountDao.queryObject(mkmzAppId);
                if (null != smsAccount) {
                    ApiDemo4Java.sendGR(smsAccount.getAccountSid(), smsAccount.getAccountApikey(), smsAccount.getTplIdGr(),
                            wxAppEntity.getName(), phone);
                }

            }
        }


    }

    @Override
    public void update(ProvideStaffEntity provideStaff) {
        provideStaffDao.update(provideStaff);
    }

    @Override
    public void delete(Long id) {
        provideStaffDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        provideStaffDao.deleteBatch(ids);
    }

    @Override
    public List<ProvideStaffEntity> examineList(Map<String, Object> map) {
        return provideStaffDao.examineList(map);
    }

    @Override
    public int examineTotal(Map<String, Object> map) {
        return provideStaffDao.examineTotal(map);
    }

    @Override
    @Transactional
    public void auditOk(Long[] ids) {

        if (null != ids && ids.length > 0)
            for (Long id : ids) {
                ProvideStaffEntity provideStaff = new ProvideStaffEntity();
                provideStaff.setId(id);
                provideStaff.setState(2);
                provideStaffDao.update(provideStaff);

                ProvideStaffEntity provideStaffEntity = provideStaffDao.queryObject(id);
                ShopRecruitmentEntity shopRecruitmentEntity = shopRecruitmentDao.queryObject(provideStaffEntity.getShopRecruitmentId());
                WxAppEntity demand = wxAppDao.queryObject(shopRecruitmentEntity.getAppId());//用人方
                WxAppEntity supply = wxAppDao.queryObject(provideStaffEntity.getAppId());//供人方

                //TD 平台通知（xxx门店的xxx招聘 已经接受xxx门店的供人）
                NoticeEntity notice = new NoticeEntity();
                notice.setBelongTo(2);
                notice.setType(1);
                notice.setDemandAppId(shopRecruitmentEntity.getAppId());
                notice.setSupplyAppId(provideStaffEntity.getAppId());
                notice.setShopRecruitmentId(provideStaffEntity.getShopRecruitmentId());
                notice.setProvideStaffId(id);
                notice.setContent(demand.getName() + " 已接受 " + supply.getName() + " 的供人");
                noticeDao.save(notice);

                //TD 短息提醒·供人方
                String phone = provideStaffEntity.getPhone();
                String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
                if (StrUtil.isNotBlank(phone) && ReUtil.contains(regex, phone)) {
//                    String sid = SMSConfig.getConfig("mkmz.ACCOUNT_SID");
//                    String apikey = SMSConfig.getConfig("mkmz.ACCOUNT_APIKEY");
//                    String tplIdSH = SMSConfig.getConfig("mkmz.tplIdSH");
//                    ApiDemo4Java.sendSH(sid, apikey, tplIdSH, demand.getName(), phone);
                    String mkmzAppId = SMSConfig.getConfig("mkmzAppId");
                    WxAppSmsAccountEntity smsAccount = wxAppSmsAccountDao.queryObject(mkmzAppId);
                    if (null != smsAccount) {
                        ApiDemo4Java.sendSH(smsAccount.getAccountSid(), smsAccount.getAccountApikey(), smsAccount.getTplIdSh(),
                                demand.getName(), phone);
                    }
                }
            }

    }

    @Override
    @Transactional
    public void auditNo(Map<String, Object> params) {
        Long[] ids = new Long[0];
        String content = null;
        if (null != params.get("ids")) {
            ids = (Long[]) params.get("ids");
        }
        if (null != params.get("content")) {
            content = (String) params.get("content");

        }
        if (null != ids && ids.length > 0) {
            for (Long id : ids) {
                ProvideStaffEntity provideStaff = new ProvideStaffEntity();
                provideStaff.setId(Long.valueOf(id + ""));
                provideStaff.setState(3);
                provideStaff.setMsg(content);
                provideStaffDao.update(provideStaff);

            }
        }


    }
}
