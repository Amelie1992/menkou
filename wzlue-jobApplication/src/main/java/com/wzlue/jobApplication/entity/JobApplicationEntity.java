package com.wzlue.jobApplication.entity;

import com.wzlue.common.annotation.Excel;
import com.wzlue.recruitment.entity.ShopRecruitmentEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 用户报名表（求职/应聘）
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-30 17:10:18
 */

@Data
public class JobApplicationEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    private Long rownum;
    //
    private Long id;
    //微信用户的唯一标识
    private String openid;
    //微信昵称
    @Excel(name = "微信昵称")
    private String nickName;
    //姓名
    @Excel(name = "姓名")
    private String realName;
    //手机号
    @Excel(name = "手机号")
    private String phone;
    //性别：1男；2女
    @Excel(name = "性别",readConverterExp = "1=男,2=女")
    private Integer sex;
    //微信号
    @Excel(name = "微信号")
    private String wechatNumber;
    //年龄
    @Excel(name = "年龄/岁")
    private Integer age;
    //工作经验
    private String workExperience;
    //应聘岗位类型(配置id)
    private Long jobId;
    //期望薪资(配置id)
    private Integer expectedSalary;
    //期望工作地-省
    private String expectedProvince;
    //期望工作地-市
    private String expectedCity;
    //期望工作地-区
    private String expectedArea;
    //期望工作地-街道
    private String expectedStreet;


    //门店招聘id
    private Long shopRecruitmentId;
    //平台招聘id
    private Long platformRecruitmentId;
    //报名类型：1主动报名（求职）；2招聘报名（应聘）; 3推荐报名
    @Excel(name = "报名类型",readConverterExp = "1=主动报名,2=招聘报名")//,3=推荐报名
    private Integer type;
    //状态反馈：1已报名；2已面试；3未录用；4已入职；5已离职；6其他
    @Excel(name = "状态",readConverterExp = "1=已报名,2=已面试,3=未录用,4=已入职,5=已离职,6=其他")
    private Integer stateFeedback;
    //反馈信息
    private String feedback;
    //入职时间
    private Date hiredate;
    //打卡返费奖励（元）
    private BigDecimal reward;
    // 应用ID
    private String appId;
    //
    private String createId;
    //
    @Excel(name = "报名时间",dateFormat="yyyy-MM-dd")
    private Date createDate;
    //
    private String updateId;
    //
    private Date updateDate;
    //软删除标识：1已删除 2未删除
    private Integer delFlag;

    //关联打卡表
    private ClockInEntity clockInEntity;
    //关联招聘
    private ShopRecruitmentEntity shopRecruitmentEntity;
    //归属：1门店 ； 2平台
    private Integer belongTo;

    //应聘岗位类型
    private String jobStr;
    //期望薪资
    private String salaryStr;
    //期望工作地-省
    private String provinceStr;
    //期望工作地-市
    private String cityStr;
    //期望工作地-区
    private String areaStr;
    //期望工作地-街道
    private String streetStr;


    //分配状态 0：未分配，1：已分配
    private Integer distributionStatus;

    //分配的appId
    private String  latestAppId;

    //公众号名称
    private String  wname;

    //门店反馈（1接收 2拒收）
    private Integer shopFeedback;

    //门店的反馈备注
    private String shopRemark;

    //对应平台应聘ID
    private Long platformJobId;


    private String recruitmentFirm;

    private String recruitmentTitle;

    //推荐人openid
    private String recommenderOpenid;




}
