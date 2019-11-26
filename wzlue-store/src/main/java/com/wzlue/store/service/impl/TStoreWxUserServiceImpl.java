package com.wzlue.store.service.impl;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.base.R;
import com.wzlue.store.dao.TStoreIntegralRecordDao;
import com.wzlue.store.dao.TStoreWxUserDao;
import com.wzlue.store.entity.TStoreIntegralRecordEntity;
import com.wzlue.store.service.TStoreWxUserService;
import com.wzlue.wechat.entity.WxUserEntity;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("tStoreWxUserService")
public class TStoreWxUserServiceImpl implements TStoreWxUserService {

	@Autowired
	private TStoreWxUserDao tStoreWxUserDao;


	@Autowired
	private TStoreIntegralRecordDao tStoreIntegralRecordDao;

	@Override
	public WxUserEntity queryObjectByUserId(String userId){
		return tStoreWxUserDao.queryObjectByUserId(userId);
	}


	@Override
	public List<WxUserEntity> queryList(Map<String, Object> map) {
		return tStoreWxUserDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return tStoreWxUserDao.queryTotal(map);
	}


	@Override
	@Transactional
	public void update(WxUserEntity wxUserEntity){
		tStoreWxUserDao.update(wxUserEntity);
	}


	/**
	 * 修改用户的总积分，并增加一条调整类型的积分记录
	 * @param id
	 * @param integral
	 * @return
	 */
	@Override
	@Transactional
	public R editIntegral(String id, int integral) {
		WxUserEntity wxUser = tStoreWxUserDao.queryObjectByUserId(id);

		if (StrUtil.isBlank(wxUser.getAppId())) {
			return R.error("门店id不可为空");
		}

		Map<String,Object> map = new HashMap<>();
		map.put("appId",wxUser.getAppId());
		int i = tStoreWxUserDao.checkUserOfWxAppActive(map);

		if (i != 1) {
			return R.error("门店已失效不可做积分修改");
		}

		//判断上限
		if (integral>2000) {
			return R.error("积分上限不允许超过2000");
		}

		int changeIntegral = integral - wxUser.getIntegral().intValue();



		wxUser.setIntegral(integral);
		tStoreWxUserDao.update(wxUser);

		TStoreIntegralRecordEntity integralRecord = new TStoreIntegralRecordEntity();
		integralRecord.setUserId(id);
		integralRecord.setAppId(wxUser.getAppId());
		integralRecord.setIntegralType(3);//1:签到方式;2:新手任务；3：积分修改
		integralRecord.setIntegral(changeIntegral);
		integralRecord.setOpenId(wxUser.getOpenId());
		integralRecord.setRemarks("积分修改");
		//保存以签到类型的积分记录
		tStoreIntegralRecordDao.save(integralRecord);

		return R.ok();
	}

	@Override
	public List<WxUserEntity> queryToExport(Map<String, Object> map) {
		return tStoreWxUserDao.queryToExport(map);
	}
    @Override
    public List<WxUserEntity> queryToExportByIds(Object[] ids) {
        return tStoreWxUserDao.queryToExportByIds(ids);
    }
}
