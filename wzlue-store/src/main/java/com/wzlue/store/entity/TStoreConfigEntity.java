package com.wzlue.store.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 门店配置表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 09:44:21
 */
@Data
public class TStoreConfigEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//配置编号
	private Long configId;
	//配置类型
	private Long configType;
	//配置内容
	private String configValue;
	//最小范围
	private Long rangeMin;
	//最大范围
	private Long rangeMax;
	//排序
	private Long configSort;

	//删选条件id
    //标记：1推荐岗位；2返费岗位；3灵活用工；4少数民族；5学生工；6大龄工；7一手单
    private Integer label;
    //后缀（对应图标）
	private Integer Suffix;

}
