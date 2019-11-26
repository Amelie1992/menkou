package com.wzlue.provideStaff.entity;

import com.wzlue.recruitment.entity.ShopRecruitmentEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 供人信息
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-10-24 16:17:25
 */
@Data
public class ProvideStaffEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    //
    private Long id;
    //招聘id
    private Long  shopRecruitmentId;
    //供人人数
    private Integer number;
    //最小年龄
    private Integer minAge;
    //最大年龄
    private Integer maxAge;
    //人员类型：1社会；2学生
    private Integer type;
    //省
    private String province;
    //市
    private String city;
    //区
    private String area;
    //街道
    private String street;
    //到岗时间
    private String arrivalTime;
    //备注
    private String remarks;
    //联系人
    private String contacts;
    //联系方式
    private String phone;
    //
    private String appId;
    //删除标志(1已删除2未删除)
    private Integer delFlag;
    //
    private String createId;
    //
    private Date createDate;
    //
    private String updateId;
    //
    private Date updateDate;
    //状态：1待审核；2通过；3拒绝
    private Integer state;
    //审核信息
    private String msg;

    //招聘单位
    private String recruitmentFirm;
    //招聘标题
    private String recruitmentTitle;

    //省
    private String provinceStr;
    //市
    private String cityStr;

    //招聘信息
    private ShopRecruitmentEntity shopRecruitment;

}