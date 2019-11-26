package com.wzlue.provideStaff.service;

import com.wzlue.provideStaff.entity.ProvideStaffEntity;

import java.util.List;
import java.util.Map;

/**
 * 供人信息
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-10-24 16:17:25
 */
public interface ProvideStaffService {

    ProvideStaffEntity queryObject(Long id);

    List<ProvideStaffEntity> queryList(Map<String, Object> map);

    int queryTotal(Map<String, Object> map);

    void save(ProvideStaffEntity provideStaff);

    void update(ProvideStaffEntity provideStaff);

    void delete(Long id);

    void deleteBatch(Long[] ids);

    List<ProvideStaffEntity> examineList(Map<String, Object> map);

    int examineTotal(Map<String, Object> map);

    //审核通过
    void auditOk(Long[] ids);

    //审核失败
    void auditNo(Map<String, Object> params);
}
