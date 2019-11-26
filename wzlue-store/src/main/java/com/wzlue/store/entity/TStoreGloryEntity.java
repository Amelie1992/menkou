package com.wzlue.store.entity;

import com.wzlue.common.annotation.CreateTime;
import com.wzlue.common.annotation.Excel;
import com.wzlue.common.annotation.UpdateTime;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 门店光荣榜表
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-31 09:44:21
 */
@Data
public class TStoreGloryEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    private Long rownum;

    //编号
    private Long gloryId;
    //标题
    @Excel(name = "标题")
    private String gloryTitle;
    //内容
    private String gloryContent;
    //排序
    private Long glorySort;
    //应用编号
    private String appId;
    //创建者
    private String createId;
    //创建时间
    @CreateTime
    @Excel(name = "创建时间",dateFormat = "yyyy-MM-dd")
    private Date createDate;
    //更新者
    private String updateId;
    //更新时间
    @UpdateTime
    private Date updateDate;

    @Excel(name = "公众号")
    private String wname;



}
