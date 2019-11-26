package com.wzlue.qrcode.dao;

import com.wzlue.qrcode.entity.WxAppQrCodeEntity;
import com.wzlue.common.base.BaseDao;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 带参二维码
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-18 11:47:43
 */
@Mapper
public interface WxAppQrCodeDao extends BaseDao<WxAppQrCodeEntity> {
    List<WxAppQrCodeEntity> queryAppList(Map<String, Object> map);

    int queryAppTotal(Map<String, Object> map);
	
}
