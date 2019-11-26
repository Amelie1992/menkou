package com.wzlue.recruitment.service.impl;

import com.wzlue.recruitment.dao.ApplicationMaterialsDao;
import com.wzlue.recruitment.dao.FeeReturnDao;
import com.wzlue.recruitment.dao.ShopRecruitmentLabelDao;
import com.wzlue.recruitment.entity.ApplicationMaterialsEntity;
import com.wzlue.recruitment.entity.FeeReturnEntity;
import com.wzlue.recruitment.entity.ShopRecruitmentLabelEntity;
import com.wzlue.recruitment.service.FeeReturnService;
import com.wzlue.sys.dao.SysImageDao;
import com.wzlue.sys.entity.SysImageEntity;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wzlue.recruitment.dao.ShopRecruitmentDao;
import com.wzlue.recruitment.entity.ShopRecruitmentEntity;
import com.wzlue.recruitment.service.ShopRecruitmentService;


@Service("shopRecruitmentService")
public class ShopRecruitmentServiceImpl implements ShopRecruitmentService {
    @Autowired
    private ShopRecruitmentDao shopRecruitmentDao;
    @Autowired
    private ShopRecruitmentLabelDao shopRecruitmentLabelDao;
    @Autowired
    private FeeReturnDao feeReturnDao;
    @Autowired
    private ApplicationMaterialsDao applicationMaterialsDao;
    @Autowired
    private SysImageDao sysImageDao;

    @Override
    public ShopRecruitmentEntity queryObject(Long id) {
        return shopRecruitmentDao.queryObject(id);
    }

    @Override
    public List<ShopRecruitmentEntity> queryList(Map<String, Object> map) {
        return shopRecruitmentDao.queryList(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return shopRecruitmentDao.queryTotal(map);
    }

    @Override
    public void save(ShopRecruitmentEntity shopRecruitment) {
        shopRecruitmentDao.save(shopRecruitment);
        //轮播图
        List<SysImageEntity> banners = shopRecruitment.getBanner();
        if (null != banners && banners.size() > 0) {
            for (SysImageEntity banner : banners) {
                banner.setType("shop_recruitment"); //关联表
                banner.setCode(shopRecruitment.getId()); //关联表id
            }
            sysImageDao.saveBatch(banners);
        }

        //标签
        List<ShopRecruitmentLabelEntity> labelList = shopRecruitment.getLabelList();
        if (null != labelList && labelList.size() > 0) {
            for (ShopRecruitmentLabelEntity label : labelList) {
                label.setShopRecruitmentId(shopRecruitment.getId());
            }
            shopRecruitmentLabelDao.saveList(labelList);
        }
        //返费等级
        List<FeeReturnEntity> feeReturnList = shopRecruitment.getFeeReturnList();
        if (null != feeReturnList && feeReturnList.size() > 0) {
            for (FeeReturnEntity feeReturn : feeReturnList) {
                feeReturn.setRecruitmentId(shopRecruitment.getId());
            }
            feeReturnDao.saveList(feeReturnList);
        }
        //上平台招聘
        ApplicationMaterialsEntity applicationMaterials = shopRecruitment.getApplicationMaterials();
        if (null != applicationMaterials) {
            applicationMaterials.setRecruitmentId(shopRecruitment.getId());
            applicationMaterialsDao.save(applicationMaterials);
            List<SysImageEntity> images = applicationMaterials.getImages();
            if (null != images && images.size() > 0) {
                for (SysImageEntity image : images) {
                    image.setType("application_materials"); //关联表名
                    image.setCode(applicationMaterials.getId());    //关联表id
                }
                sysImageDao.saveBatch(images);
            }
        }
    }

    @Override
    public void update(ShopRecruitmentEntity shopRecruitment) {
        shopRecruitmentDao.update(shopRecruitment);
        //轮播图---先删除在添加
        sysImageDao.deleteByTypeAndCode("shop_recruitment", shopRecruitment.getId());
        List<SysImageEntity> banners = shopRecruitment.getBanner();
        if (null != banners && banners.size() > 0) {
            for (SysImageEntity banner : banners) {
                banner.setType("shop_recruitment"); //关联表
                banner.setCode(shopRecruitment.getId()); //关联表id
            }
            sysImageDao.saveBatch(banners);
        }
        //标签---先删除再添加
        shopRecruitmentLabelDao.deleteByRecruitmentId(shopRecruitment.getId());
        List<ShopRecruitmentLabelEntity> labelList = shopRecruitment.getLabelList();
        if (null != labelList && labelList.size() > 0) {
            for (ShopRecruitmentLabelEntity label : labelList) {
                label.setShopRecruitmentId(shopRecruitment.getId());
            }
            shopRecruitmentLabelDao.saveList(labelList);
        }
        //返费等级---先删除再添加
        feeReturnDao.deleteByRecruitmentId(shopRecruitment.getId());
        List<FeeReturnEntity> feeReturnList = shopRecruitment.getFeeReturnList();
        if (null != feeReturnList && feeReturnList.size() > 0) {
            for (FeeReturnEntity feeReturn : feeReturnList) {
                feeReturn.setRecruitmentId(shopRecruitment.getId());
            }
            feeReturnDao.saveList(feeReturnList);
        }
        //上平台招聘申请材料---先删除再添加
        ApplicationMaterialsEntity application = applicationMaterialsDao.queryByRecruitmentId(shopRecruitment.getId());
        if (null != application) {
            sysImageDao.deleteByTypeAndCode("application_materials", application.getId());
            applicationMaterialsDao.delete(application.getId());
        }
        ApplicationMaterialsEntity applicationMaterials = shopRecruitment.getApplicationMaterials();
        if (null != applicationMaterials) {
            applicationMaterials.setRecruitmentId(shopRecruitment.getId());
            applicationMaterialsDao.save(applicationMaterials);
            List<SysImageEntity> images = applicationMaterials.getImages();
            if (null != images && images.size() > 0) {
                for (SysImageEntity image : images) {
                    image.setType("application_materials"); //关联表名
                    image.setCode(applicationMaterials.getId());    //关联表id
                }
                sysImageDao.saveBatch(images);
            }
        }
    }

    @Override
    public void delete(Long id) {
        shopRecruitmentDao.delete(id);
    }

    @Override
    public void deleteBatch(Long[] ids) {
        shopRecruitmentDao.deleteBatch(ids);
    }

    @Override
    public void updateShopSuspendFlag(Map<String, Object> map) {
        shopRecruitmentDao.updateShopSuspendFlag(map);
    }


    @Override
    public void toExamineSuccessPl(Map<String, Object> map) {
        shopRecruitmentDao.updateHavingStateAndMsg(map);
    }

    @Override
    public void toExamineFailedPl(Map<String, Object> map) {
        shopRecruitmentDao.updateHavingStateAndMsg(map);
    }

    @Override
    public List<ShopRecruitmentEntity> queryPlatFormList(Map<String, Object> map) {
        return shopRecruitmentDao.queryPlatFormList(map);
    }

    @Override
    public int queryPlatFormTotal(Map<String, Object> map) {
        return shopRecruitmentDao.queryPlatFormTotal(map);
    }

    //检验是否重复转发
    @Override
    public Integer exist(Long id) {
        return shopRecruitmentDao.exist(id);
    }


    @Override
    public int queryTotalByIds(Map<String, Object> map) {
        return shopRecruitmentDao.queryTotalByIds(map);
    }


    @Override
    public List<ShopRecruitmentEntity> queryListByPlatform(Map<String, Object> map) {
        return shopRecruitmentDao.queryListByPlatform(map);
    }

    @Override
    public int queryTotalByPlatform(Map<String, Object> map) {
        return shopRecruitmentDao.queryTotalByPlatform(map);
    }

    @Override
    public void updateaErnest(ShopRecruitmentEntity entity) {
        shopRecruitmentDao.update(entity);
    }


    @Override
    public List<ShopRecruitmentEntity> queryListByIds(Object[] ids) {
        return shopRecruitmentDao.queryListByIds(ids);
    }

    @Override
    public List<ShopRecruitmentEntity> queryListAll(Map<String, Object> map) {
        return shopRecruitmentDao.queryListAll(map);
    }

    @Override
    public void pageView(Long id) {
        shopRecruitmentDao.pageView(id);
    }


}
