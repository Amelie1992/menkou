package com.wzlue.recruitment.service;

import com.wzlue.recruitment.entity.ShopRecruitmentLabelEntity;

import java.util.List;
import java.util.Map;

/**
 * 门店招聘-标签
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-30 10:51:26
 */
public interface ShopRecruitmentLabelService {
	
	ShopRecruitmentLabelEntity queryObject(Long id);
	
	List<ShopRecruitmentLabelEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(ShopRecruitmentLabelEntity shopRecruitmentLabel);
	
	void update(ShopRecruitmentLabelEntity shopRecruitmentLabel);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);
}
