package com.wzlue.sys.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.wzlue.sys.service.SysConfigService;

@Configuration
public class IntegralConfig {
	
	@Autowired
	private SysConfigService configService;
	
	@Bean
	public IntegralSetting getIntegralSetting(){
		String value = configService.queryByKey("INTEGRAL_SETTING");
		return new Gson().fromJson(value, IntegralSetting.class);
	}

}
