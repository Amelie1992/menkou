package com.wzlue.store.service.impl;

import com.wzlue.common.base.R;
import com.wzlue.common.utils.DateUtils;
import com.wzlue.store.dao.TStoreIntegralRecordDao;
import com.wzlue.store.dao.TStoreSignInRecordDao;
import com.wzlue.store.entity.TStoreIntegralRecordEntity;
import com.wzlue.store.entity.TStoreSignInRecordEntity;
import com.wzlue.store.service.TStoreSignInRecordService;
import com.wzlue.store.service.TStoreWxUserService;
import com.wzlue.sys.common.config.IntegralConfig;
import com.wzlue.sys.common.config.IntegralSetting;
import com.wzlue.wechat.entity.WxUserEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("tStoreSignInRecordService")
public class TStoreSignInRecordServiceImpl implements TStoreSignInRecordService {
	@Autowired
	private TStoreSignInRecordDao tStoreSignInRecordDao;

	@Autowired
	private TStoreIntegralRecordDao tStoreIntegralRecordDao;

	@Autowired
	private TStoreWxUserService tStoreWxUserService;

	@Autowired
	private IntegralConfig integralConfig;
	
	@Override
	public TStoreSignInRecordEntity queryObject(Long id){
		return tStoreSignInRecordDao.queryObject(id);
	}
	
	@Override
	public List<TStoreSignInRecordEntity> queryList(Map<String, Object> map){
		return tStoreSignInRecordDao.queryList(map);
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return tStoreSignInRecordDao.queryTotal(map);
	}


	/**
	 * 签到
	 * @param map
	 */
	@Override
	@Transactional
	public R signIn(Map<String, Object> map) {
		String userId = (String)map.get("userId");//入参用户唯一标识id
		if(StringUtils.isBlank(userId)){
			return R.error(1,"入参用户id获取异常！");
		}
		int count_date = 0;//连续签到天数
		int integral = 0 ;//积分奖励
		//查询今天是否已经签到
		TStoreSignInRecordEntity tStoreSignInRecordEntity = new TStoreSignInRecordEntity();
		tStoreSignInRecordEntity.setUserId(userId);
		tStoreSignInRecordEntity.setCreateTime(new Date());
		int num = tStoreSignInRecordDao.queryTodaySignInRecord(tStoreSignInRecordEntity);
		if(num>0){
			return R.error(2,"今日已经签到！");
		}


		//未签到就去查询该用户信息
		WxUserEntity wxUser = tStoreWxUserService.queryObjectByUserId(userId);

		//查询昨日是否签到，如签到返回连续签到天数，若没有签到，记录连续天数为1；

		TStoreSignInRecordEntity yesSignInRecordModel = new TStoreSignInRecordEntity();
		yesSignInRecordModel.setUserId(userId);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String beforeDay = DateUtils.getPreDateByDate(formatter.format(tStoreSignInRecordEntity.getCreateTime()));
		ParsePosition pos = new ParsePosition(0);
		//前一天打卡记录
		yesSignInRecordModel.setCreateTime(formatter.parse(beforeDay, pos));

		TStoreSignInRecordEntity yesSignInRecord = tStoreSignInRecordDao.queryYesterdaySignInRecord(yesSignInRecordModel);
		if (yesSignInRecord == null) {
			count_date = 1;
		} else  {
			count_date = yesSignInRecord.getCountDate()+1;
		}


		//查询正常积分规则
		IntegralSetting.SignInRule signInRule = integralConfig.getIntegralSetting().getSignInRule();
		Integer score = signInRule.getScore();
		List<IntegralSetting.ContinuousAwardRule> awardRules = signInRule.getAwardRules();
		//查询额外积分规则
		Integer addIntegral = getAddIntegral(count_date, awardRules);
		//赋值规则:正常+额外
		integral = score + addIntegral.intValue() ;

		//判断用户的总积分newIntegral是否大于2000
		int newIntegral = integral;
		if(wxUser.getIntegral()!= null){
			newIntegral = wxUser.getIntegral() + integral;
		}

		//判断上限
		if (newIntegral>2000) {
			return R.error(3,"签到积分已上限！");
		}



		tStoreSignInRecordEntity.setCountDate(count_date);
		tStoreSignInRecordEntity.setAppId(wxUser.getAppId());
		tStoreSignInRecordEntity.setOpenId(wxUser.getOpenId());
		tStoreSignInRecordEntity.setRemarks("签到");
		//保存今日签到信息
		tStoreSignInRecordDao.save(tStoreSignInRecordEntity);



		TStoreIntegralRecordEntity integralRecord = new TStoreIntegralRecordEntity();
		integralRecord.setUserId(userId);
		integralRecord.setAppId(wxUser.getAppId());
		integralRecord.setIntegralType(1);//1:签到方式
		integralRecord.setIntegral(integral);
		integralRecord.setOpenId(wxUser.getOpenId());
		integralRecord.setRemarks("签到");
		//保存以签到类型的积分记录
		tStoreIntegralRecordDao.save(integralRecord);


		wxUser.setIntegral(newIntegral);
		tStoreWxUserService.update(wxUser);
		return R.ok().put("totalIntegral",newIntegral).put("integral",integral);


	}






	/**
	 * 签到
	 * @param map
	 */
	@Override
	public R checkSignIn(Map<String, Object> map) {

		String userId = (String)map.get("userId");//入参用户唯一标识id
		if(StringUtils.isBlank(userId)){
			return R.error(1,"入参用户id获取异常！");
		}

		//查询今天是否已经签到
		TStoreSignInRecordEntity tStoreSignInRecordEntity = new TStoreSignInRecordEntity();
		tStoreSignInRecordEntity.setUserId(userId);
		tStoreSignInRecordEntity.setCreateTime(new Date());
		int num = tStoreSignInRecordDao.queryTodaySignInRecord(tStoreSignInRecordEntity);
		if(num>0){
			return R.error(2,"今日已经签到！");
		}

		return R.ok("未签到！");


	}



	 public  Integer getAddIntegral(int count_date,List<IntegralSetting.ContinuousAwardRule> awardRules){

		Integer addIntegral = 0;

		 for (IntegralSetting.ContinuousAwardRule awardRule : awardRules) {
			 Integer days = awardRule.getDays();
			 if (count_date >= days.intValue()) {
				 addIntegral = awardRule.getAddscore();
			 }
		 }


		 return addIntegral;




	 }


	@Override
	@Transactional
	public void save(TStoreSignInRecordEntity tStoreSignInRecord){
		tStoreSignInRecordDao.save(tStoreSignInRecord);
	}
	
	@Override
	@Transactional
	public void update(TStoreSignInRecordEntity tStoreSignInRecord){
		tStoreSignInRecordDao.update(tStoreSignInRecord);
	}
	
	@Override
	@Transactional
	public void delete(Long id){
		tStoreSignInRecordDao.delete(id);
	}
	
	@Override
	@Transactional
	public void deleteBatch(Long[] ids){
		tStoreSignInRecordDao.deleteBatch(ids);
	}
	
}
