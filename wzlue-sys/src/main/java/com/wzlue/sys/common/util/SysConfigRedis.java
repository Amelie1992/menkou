package com.wzlue.sys.common.util;

import com.wzlue.common.utils.RedisKeys;
import com.wzlue.common.utils.RedisUtils;
import com.wzlue.sys.entity.SysConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 系统配置Redis
 *
 * @author chenshun
 * @email wzlue.com
 * @date 2017/7/18 21:08
 */
@Component
public class SysConfigRedis {

    public void saveOrUpdate(SysConfigEntity config) {
        if(config == null){
            return ;
        }
        String key = RedisKeys.getSysConfigKey(config.getKey());
        RedisUtils.set(key, config);
    }

    public void delete(String configKey) {
        String key = RedisKeys.getSysConfigKey(configKey);
        RedisUtils.delete(key);
    }

    public SysConfigEntity get(String configKey){
        String key = RedisKeys.getSysConfigKey(configKey);
        return RedisUtils.get(key, SysConfigEntity.class);
    }
}
