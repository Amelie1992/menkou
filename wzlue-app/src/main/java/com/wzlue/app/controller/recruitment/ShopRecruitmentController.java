
package com.wzlue.app.controller.recruitment;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.utils.LocationUtils2;
import com.wzlue.app.common.annotation.Login;
import com.wzlue.app.controller.sys.AbstractController;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.recruitment.dao.ShopRecruitmentDao;
import com.wzlue.recruitment.entity.ShopRecruitmentEntity;
import com.wzlue.recruitment.entity.ShopRecruitmentLabelEntity;
import com.wzlue.recruitment.service.ShopRecruitmentService;
import com.wzlue.sys.dao.SysAreaDao;
import com.wzlue.sys.entity.SysAreaEntity;
import com.wzlue.sys.service.SysAreaService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.wzlue.common.utils.EscapeUtil.unescape;
import static com.wzlue.common.utils.LocationUtils.getDistance;


/**
 * 招聘
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-30 10:49:06
 */
@RestController
@RequestMapping("/app/recruitment")
public class ShopRecruitmentController extends AbstractController {
    @Autowired
    private ShopRecruitmentService shopRecruitmentService;
    @Autowired
    private SysAreaDao sysAreaDao;

    @Autowired
    private ShopRecruitmentDao shopRecruitmentDao;

    /**
     * 列表
     */
    @GetMapping("/shopList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appId", dataType = "string", value = "应用Id", paramType = "query", required = true),
            @ApiImplicitParam(name = "belongTo", dataType = "int", value = "归属：1门店；2平台", paramType = "query", required = true),
            @ApiImplicitParam(name = "search", dataType = "string", value = "按照：职位/企业 检索", paramType = "query"),
            @ApiImplicitParam(name = "label", dataType = "string", value = "标记：1推荐岗位；2返费岗位；3灵活用工；4少数民族；5学生工；6大龄工；7一手单", paramType = "query"),
            @ApiImplicitParam(name = "nearestToMe", dataType = "int", value = "离我最近：1", paramType = "query"),
            @ApiImplicitParam(name = "hasEarnest", dataType = "int", value = "是否(确实)缴纳保证金：1是", paramType = "query"),
            @ApiImplicitParam(name = "jd", dataType = "string", value = "经度", paramType = "query"),
            @ApiImplicitParam(name = "wd", dataType = "string", value = "纬度", paramType = "query"),
            @ApiImplicitParam(name = "province", dataType = "string", value = "省id", paramType = "query"),
            @ApiImplicitParam(name = "city", dataType = "string", value = "市id", paramType = "query"),
            @ApiImplicitParam(name = "area", dataType = "string", value = "区id", paramType = "query"),
            @ApiImplicitParam(name = "enterpriseSize", dataType = "string", value = "工厂规模（配置id）", paramType = "query"),
            @ApiImplicitParam(name = "salary", dataType = "string", value = "薪资待遇（配置id）", paramType = "query"),
            @ApiImplicitParam(name = "feeReturn", dataType = "string", value = "返费金额（配置id）", paramType = "query"),
            @ApiImplicitParam(name = "page", dataType = "int", value = "页码", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "limit", dataType = "int", value = "一页几条", paramType = "query", defaultValue = "10"),
            @ApiImplicitParam(name = "sameCity", dataType = "int", value = "同城好单：1", paramType = "query")
    })
    public R list(@RequestParam Map<String, Object> params) {
        List<ShopRecruitmentEntity> shopRecruitmentList;
        int total = 0;

        if (null != params.get("hasEarnest") && params.get("hasEarnest").toString().equals("1")) {
            params.put("hasEarnest", null);
            params.put("isEarnest", 1);//确实缴纳
        }
        //查询列表数据
        Query query = new Query(params);
        query.put("delFlag", 2);
        String belongTo = (String) query.get("belongTo");

        if (StrUtil.isBlank(belongTo) || (!"1".equals(belongTo) && !"2".equals(belongTo)))
            return R.page(null, 0);

        if (belongTo.equals("1")) {
            query.put("shopSuspendFlag", 1);
        } else if (belongTo.equals("2")) { //平台
            query.remove("appId");
            query.put("platformSuspendFlag", 1);
            query.put("reviewState", 2);  //审核通过
        }
        //根据标记检索     标记：1推荐岗位；2返费岗位；3灵活用工；4少数民族；5学生工；6大龄工；7一手单
        Object label = query.get("label");
        if (null != label) {
            if (label.toString().equals("1")) {
                query.put("recommendFlag", 1);
            } else if (label.toString().equals("2")) {
                query.put("feeReturnFlag", 1);
            } else if (label.toString().equals("3")) {
                query.put("famousFlag", 1);
            } else if (label.toString().equals("4")) {
                query.put("minorityFlag", 1);
            } else if (label.toString().equals("5")) {
                query.put("studentFlag", 1);
            } else if (label.toString().equals("6")) {
                query.put("olderFlag", 1);
            } else if (label.toString().equals("7")) {
                query.put("firstHandFlag", 1);
            }
        }

        Object jd = query.get("jd");    //用户经度
        Object wd = query.get("wd");    //用户纬度
        if (null != jd && null != wd && StringUtils.isNotBlank(jd.toString()) && StringUtils.isNotBlank(wd.toString())) {
            //同城好单
            Object sameCity = query.get("sameCity");
            if (null != sameCity && sameCity.toString().equals("1")) {
                Map<String, Object> location = LocationUtils2.getLocation(jd.toString(), wd.toString());
                Object province = location.get("province");
                Object city = location.get("city");

                if (province != null && city != null) {
                    String pStr = province.toString();
                    String cStr = city.toString();
                    //慢查询
                /*    int count1 = 0;
                    int count2 = 0;

                    HashMap<String, Object> map = new HashMap<>();
                    map.put("parentid", 0);
                    List<SysAreaEntity> pList = sysAreaDao.areaList(map);
                    for (SysAreaEntity area : pList) {
                        count1 = area.getAreaname().indexOf(pStr);
                        if (count1 == 0) {
                            count1 = area.getShortname().indexOf(pStr);
                        }
                        if (count1 > 0) {
                            map.put("parentid", area.getId());
                            List<SysAreaEntity> cList = sysAreaDao.queryList(map);
                            for (SysAreaEntity areaEntity : cList) {
                                count2 = areaEntity.getAreaname().indexOf(cStr);
                                if (count2 == 0) {
                                    count2 = areaEntity.getShortname().indexOf(cStr);
                                }
                                if (count2 > 0) {
                                    query.put("city",areaEntity.getId());
                                    shopRecruitmentList = shopRecruitmentService.queryList(query);
                                }
                            }
                        }
                    }*/

                    List<SysAreaEntity> provinceL = sysAreaDao.queryCodeByName(pStr);
                    List<SysAreaEntity> cityL = sysAreaDao.queryCodeByName(cStr);
                    if (null != provinceL && null != cityL && provinceL.size() > 0 && cityL.size() > 0) {
                        for (SysAreaEntity provinceE : provinceL) {
                            for (SysAreaEntity cityE : cityL) {
                                if (cityE.getParentid().toString().equals(provinceE.getId().toString())) {
                                    query.put("city", cityE.getId());
                                }
                            }
                        }
                    }

                }
            }
        }

        if (null != jd && null != wd && StringUtils.isNotBlank(jd.toString()) && StringUtils.isNotBlank(wd.toString())) {
            //不分页
            Object limit = query.get("limit");
            Object offset = query.get("offset");
            query.put("limit", null);
            query.put("offset", null);
            List<ShopRecruitmentEntity> all = shopRecruitmentService.queryList(query);
            //计算距离
            for (ShopRecruitmentEntity recruitment : all) {
                double distance = getDistance(Double.valueOf(wd.toString()), Double.valueOf(jd.toString()),
                        Double.valueOf(recruitment.getLatitude()), Double.valueOf(recruitment.getLongitude()));
                ShopRecruitmentEntity shopRecruitmentEntity = new ShopRecruitmentEntity();
                shopRecruitmentEntity.setId(recruitment.getId());
                shopRecruitmentEntity.setDistance(distance);
                shopRecruitmentDao.update(shopRecruitmentEntity);
                    /* 造成后续轮播图、标签、返费、平台资质的丢失
                    shopRecruitmentService.update(shopRecruitmentEntity);*/

            }

                 /*Collections.sort(shopRecruitmentList, new Comparator<ShopRecruitmentEntity>() {
                    @Override
                    public int compare(ShopRecruitmentEntity o1, ShopRecruitmentEntity o2) {
                        return o1.getDistance().compareTo(o2.getDistance());
                    }
                });*/

            //离我最近(按照距离排序)
            Object nearestToMe = query.get("nearestToMe");
            if (null != nearestToMe && nearestToMe.toString().equals("1")) {
                //按照距离排序
                query.put("sidx", "distance");
                query.put("order", "asc");
                //分页
                query.put("limit", limit);
                query.put("offset", offset);
            }
        }

        shopRecruitmentList = shopRecruitmentService.queryList(query);
        if (shopRecruitmentList != null && !shopRecruitmentList.isEmpty()) {
            for (ShopRecruitmentEntity recruitment : shopRecruitmentList) {
                if (recruitment.getFeeReturnType() != null && recruitment.getFeeReturnType() == 1 && recruitment.getTotalReward() != null) {
                    recruitment.setTotalRewardShow(recruitment.getTotalReward() + "");
                } else if (recruitment.getFeeReturnType() != null && recruitment.getFeeReturnType() == 2 && recruitment.getTotalReward() != null) {
                    recruitment.setTotalRewardShow(recruitment.getTotalReward() + "/时");
                }

                //月薪
                if (StrUtil.isNotBlank(recruitment.getSalaryStr())) {
//                    recruitment.setSalary(recruitment.getSalaryStr());
                    //例如：2000~4000元   去掉元
                    int index = recruitment.getSalaryStr().indexOf("元");
                    if (index != -1) {
                        String str = recruitment.getSalaryStr().substring(0, index) + recruitment.getSalaryStr().substring(index + 1);
                        recruitment.setSalary(str);
                    } else {
                        recruitment.setSalary(recruitment.getSalaryStr());
                    }
                }
            }
        }

        total = shopRecruitmentService.queryTotal(query);

        return R.page(shopRecruitmentList, total);
    }

    /**
     * 信息
     */
    @GetMapping("/shopInfo/{id}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "jd", dataType = "string", value = "经度", paramType = "query"),
            @ApiImplicitParam(name = "wd", dataType = "string", value = "纬度", paramType = "query")
    })
//    @RequiresPermissions("recruitment:shoprecruitment:info")
    public R info(@PathVariable("id") Long id, @RequestParam Map<String, Object> params) {
        ShopRecruitmentEntity shopRecruitment = shopRecruitmentService.queryObject(id);
        if (null != shopRecruitment) {
            shopRecruitment.setJobDescription(unescape(shopRecruitment.getJobDescription()));
            shopRecruitment.setJobRequirement(unescape(shopRecruitment.getJobRequirement()));
            shopRecruitment.setJobResponsibilities(unescape(shopRecruitment.getJobResponsibilities()));
            shopRecruitment.setCompanyBenefits(unescape(shopRecruitment.getCompanyBenefits()));
            shopRecruitment.setEnterpriseInfo(unescape(shopRecruitment.getEnterpriseInfo()));


            if (shopRecruitment.getFeeReturnType() != null && shopRecruitment.getFeeReturnType() == 1 && shopRecruitment.getTotalReward() != null) {
                shopRecruitment.setTotalRewardShow(shopRecruitment.getTotalReward() + "");
            } else if (shopRecruitment.getFeeReturnType() != null && shopRecruitment.getFeeReturnType() == 2 && shopRecruitment.getTotalReward() != null) {
                shopRecruitment.setTotalRewardShow(shopRecruitment.getTotalReward() + "/时");
            }

            if (StrUtil.isNotBlank(shopRecruitment.getSalaryStr())) {
//                shopRecruitment.setSalary(shopRecruitment.getSalaryStr());
                //例如：2000~4000元   去掉元
                int index = shopRecruitment.getSalaryStr().indexOf("元");
                if (index != -1) {
                    String str = shopRecruitment.getSalaryStr().substring(0, index) + shopRecruitment.getSalaryStr().substring(index + 1);
                    shopRecruitment.setSalary(str);
                } else {
                    shopRecruitment.setSalary(shopRecruitment.getSalaryStr());
                }
            }
        }

        //计算距离
        Object jd = params.get("jd");    //用户经度
        Object wd = params.get("wd");    //用户纬度
        if (null != jd && null != wd && StringUtils.isNotBlank(jd.toString()) && StringUtils.isNotBlank(wd.toString())) {
            double distance = getDistance(Double.valueOf(wd.toString()), Double.valueOf(jd.toString()),
                    Double.valueOf(shopRecruitment.getLatitude()), Double.valueOf(shopRecruitment.getLongitude()));
            shopRecruitment.setDistance(distance);
        }
        return R.ok().put("shopRecruitment", shopRecruitment);
    }

    /**
     * 统计 浏览量
     */
    @GetMapping("/pageView/{id}")
    public R pageView(@PathVariable("id") Long id) {
        shopRecruitmentService.pageView(id);
        return R.ok();
    }
}
