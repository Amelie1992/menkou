package com.wzlue.recruitment.service;

import com.wzlue.recruitment.entity.PUploadHistoryEntity;
import java.util.List;
import java.util.Map;

/**
 * 供人历史清单表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 16:13:50
 */
public interface PUploadHistoryService {
	
	PUploadHistoryEntity queryObject(Long id);
	
	List<PUploadHistoryEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(PUploadHistoryEntity pUploadHistory);
	
	void update(PUploadHistoryEntity pUploadHistory);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

	/**
	 * 审核通过
	 * @param id  主键
	 */
    void auditOk(Long[] id);

	/**
	 * 审核失败
	 * @param params ids 主键
	 * @param params content 审核失败内容
	 */
	void auditNo(Map<String, Object> params);

	//门店确认供人
	void confirm(Map<String, Object> map);
}
