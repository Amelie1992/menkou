package com.wzlue.recruitment.entity;

import com.wzlue.common.annotation.Excel;
import com.wzlue.sys.entity.SysImageEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 门店招聘
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-30 10:49:06
 */
@Data
public class ShopRecruitmentEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Excel(name = "序号", cellType = Excel.ColumnType.NUMERIC)
    private Long rownum;
    //
    private Long id;
    //招聘单位（名称）
    @Excel(name = "招聘单位", width = 30)
    private String recruitmentFirm;
    //招聘单位logo
    private String logo;
    //招聘标题
    @Excel(name = "招聘标题", width = 30)
    private String title;
    //薪资待遇（元/月）月薪
//    @Excel(name = "薪资待遇（元/月）")
    private String salary;//配置id
    //薪资待遇（元/月）月薪
    @Excel(name = "薪资待遇（元/月）")
    private String salaryStr;
    //时薪（元/时）
    private String hourlyWage;
    //省
    private String province;
    //市
    private String city;
    //区
    private String area;
    //街道
    private String street;

    //经度
    private String longitude;
    //纬度
    private String latitude;
    //企业规模 （人）
    private String enterpriseSize;
    //企业年限 （年）
    private String enterpriseAge;
    //招聘人数 （人）
    @Excel(name = "招聘人数 （人）")
    private Integer numberOfRecruitment;
    //具体位置
    @Excel(name = "公司地址", width = 36)
    private String position;
    //职位描述
    private String jobDescription;
    //招聘要求
    private String jobRequirement;
    //工作职责 2019.11.05改为 薪资待遇
    private String jobResponsibilities;
    //工作时间
    private String workTime;
    //公司福利
    private String companyBenefits;
    //推荐标记：1  推荐岗位
    @Excel(name = "推荐岗位",readConverterExp = "1=√,0=×")
    private Integer recommendFlag;
    //名企标志：1    灵活用工
    @Excel(name = "灵活用工",readConverterExp = "1=√,0=×，")
    private Integer famousFlag;
    //门店招聘暂停标识:1开始2暂停
    @Excel(name = "状态",readConverterExp = "1=开始,2=暂停")
    private Integer shopSuspendFlag;
    //平台招聘暂停标识:1开始2暂停
    private Integer platformSuspendFlag;
    // 应用ID
    private String appId;
    //
    private String createId;
    //
    @Excel(name = "发布时间",dateFormat="yyyy-MM-dd")
    private Date createDate;
    //
    private String updateId;
    //
    private Date updateDate;
    //软删除标识：1已删除 2未删除
    private Integer delFlag;
    //已入职（人）
    private Integer entryNum;
    //打卡设置是否存在
    private Integer clockInFlag;
    //标签
    private List<ShopRecruitmentLabelEntity> labelList;
    //返费
    private List<FeeReturnEntity> feeReturnList;
    //返费奖励（元）
    private BigDecimal totalReward;

    //返费奖励
    private String totalRewardShow;
    //省
    private String provinceStr;
    //市
    private String cityStr;
    //区
    private String areaStr;
    //街道
    private String streetStr;
    //归属：1门店 ； 2平台
    private Integer belongTo;
    //用户与招聘单位间的距离(单位：km)
    private Double distance;
    //上平台的申请材料
    private ApplicationMaterialsEntity applicationMaterials;
    //是否缴纳保证金：1是；2否
    private Integer hasEarnest;
    //保证金（元）
    private BigDecimal earnest;
    //平台审核状态：1待审核；2通过；3不通过
    private Integer reviewState;
    //审核信息
    private String reviewMsg;
    //追溯（原门店招聘id）
    private Long trace;
    //平台名称
    private String wxName;
    // 是否缴纳保证金
    private String isEarnest;
    //入职标识（已有人入过职）
    private Integer entryFlag;


    //企业信息
    private String enterpriseInfo;

    //单个保证金（元/人）
    private BigDecimal earnestOne;

    //返费方式 1：按天；2：按小时
    private Integer feeReturnType;

    //每日工时
    private Integer workHours;

    //轮播图
    private List<SysImageEntity> banner;

    //推荐入职xxx天
    private Integer entryDays;
    //奖励xxx钱
    //推荐奖励金 (门店招聘belongTo=1)
    private BigDecimal bonus;


    //门店（belongTo=1） 自定义岗位种类
    //少数民族：1
    private Integer minorityFlag;
    //学生工：1
    private Integer studentFlag;
    //大龄工：1
    private Integer olderFlag;
    //一手单：1
    private Integer firstHandFlag;

    //浏览量
    private String pageView;
}
