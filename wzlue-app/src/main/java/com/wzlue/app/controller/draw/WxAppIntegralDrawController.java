
package com.wzlue.app.controller.draw;

import com.wzlue.app.common.annotation.Login;
import com.wzlue.app.common.annotation.LoginUser;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.draw.entity.IntegralDrawPrizeEntity;
import com.wzlue.draw.entity.IntegralDrawRecordEntity;
import com.wzlue.draw.entity.WxAppIntegralDrawEntity;
import com.wzlue.draw.service.IntegralDrawPrizeService;
import com.wzlue.draw.service.IntegralDrawRecordService;
import com.wzlue.draw.service.WxAppIntegralDrawService;
import com.wzlue.store.entity.TStoreIntegralRecordEntity;
import com.wzlue.store.service.TStoreIntegralRecordService;
import com.wzlue.store.service.TStoreWxUserService;
import com.wzlue.wechat.entity.WxUserEntity;
import com.wzlue.wechat.service.WxUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 门店（自定义）积分抽奖活动   （付费开通）
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-11-13 09:45:01
 */
@RestController
@RequestMapping("/app/integraldraw")
public class WxAppIntegralDrawController {
    @Autowired
    private WxAppIntegralDrawService wxAppIntegralDrawService;
    @Autowired
    private IntegralDrawPrizeService integralDrawPrizeService;
    @Autowired
    private IntegralDrawRecordService integralDrawRecordService;
    @Autowired
    private TStoreIntegralRecordService tStoreIntegralRecordService;
    @Autowired
    private TStoreWxUserService tStoreWxUserService;


    /**
     * 抽奖配置信息
     */
    @GetMapping("/info")
    public R info(@RequestParam String appId) {
        WxAppIntegralDrawEntity wxAppIntegralDraw = wxAppIntegralDrawService.queryObject(appId);
        if (null == wxAppIntegralDraw) {
            return R.error(1, "尚未开通积分抽奖功能");
        } else if (null == wxAppIntegralDraw.getPrizeList() || wxAppIntegralDraw.getPrizeList().size() == 0) {
            return R.error(2, "尚未配置奖项信息");
        } else if (wxAppIntegralDraw.getState() == 2) {
            return R.error(3, "积分抽奖功能已被禁用");
        }

        return R.ok().put("draw", wxAppIntegralDraw);
    }

    /**
     * 我的奖品列表
     */
    @Login
    @GetMapping("/prizeList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "state", dataType = "int", value = "状态：1未兑奖；2已兑奖", paramType = "query")
    })
    public R prizeList(@RequestParam Map<String, Object> params, @LoginUser WxUserEntity wxUser) {
        //查询列表数据
        params.put("openid", wxUser.getId());
        params.put("type", 1);//只查已中奖
//        Query query = new Query(params);//不分页

        List<IntegralDrawRecordEntity> integralDrawRecordList = integralDrawRecordService.queryList(params);
        int total = integralDrawRecordService.queryTotal(params);

        return R.page(integralDrawRecordList, total);
    }

    /**
     * 积分抽奖接口
     */
    @Login
    @PostMapping("/draw")
    @Transactional
    public R draw(@LoginUser WxUserEntity wxUser) {
        WxAppIntegralDrawEntity wxAppIntegralDraw = wxAppIntegralDrawService.queryObject(wxUser.getAppId());
        if (null == wxAppIntegralDraw) {
            return R.error(1, "尚未开通积分抽奖功能");
        } else if (null == wxAppIntegralDraw.getPrizeList() || wxAppIntegralDraw.getPrizeList().size() == 0) {
            return R.error(2, "尚未配置奖项信息");
        } else if (wxAppIntegralDraw.getState() == 2) {
            return R.error(3, "积分抽奖功能已被禁用");
        }

        Integer integral = wxUser.getIntegral();//用户积分
        Integer consume = wxAppIntegralDraw.getConsume();//单次抽奖消耗的积分
        if (null == integral || integral == 0 || integral < consume) {
            return R.error(4, "积分不足");
        }

        //按照一至八等奖排序
        List<IntegralDrawPrizeEntity> prizeList = wxAppIntegralDraw.getPrizeList();

        int sort = 8;//排序1~8等奖 八等奖 未中奖
        int type = 2;//未中奖
        double random = Math.floor(Math.random() * 10000);//10000以内的随机数

        double prize1 = prizeList.get(0).getProbability() * 10000 / 100.0;//中奖区间数
        double prize2 = prize1 + prizeList.get(1).getProbability() * 10000 / 100.0;
        double prize3 = prize2 + prizeList.get(2).getProbability() * 10000 / 100.0;
        double prize4 = prize3 + prizeList.get(3).getProbability() * 10000 / 100.0;
        double prize5 = prize4 + prizeList.get(4).getProbability() * 10000 / 100.0;
        double prize6 = prize5 + prizeList.get(5).getProbability() * 10000 / 100.0;
        double prize7 = prize6 + prizeList.get(6).getProbability() * 10000 / 100.0;
        double prize8 = prize7 + prizeList.get(7).getProbability() * 10000 / 100.0;

        if (random >= 0 && random <= prize1 && prizeList.get(0).getStock() > 0) {
            sort = 1;

        } else if (random > prize1 && random <= prize2 && prizeList.get(1).getStock() > 0) {
            sort = 2;

        } else if (random > prize2 && random <= prize3 && prizeList.get(2).getStock() > 0) {
            sort = 3;

        } else if (random > prize3 && random <= prize4 && prizeList.get(3).getStock() > 0) {
            sort = 4;

        } else if (random > prize4 && random <= prize5 && prizeList.get(4).getStock() > 0) {
            sort = 5;

        } else if (random > prize5 && random <= prize6 && prizeList.get(5).getStock() > 0) {
            sort = 6;

        } else if (random > prize6 && random <= prize7 && prizeList.get(6).getStock() > 0) {
            sort = 7;

        } else {
            sort = 8;
        }

        //扣减积分
        WxUserEntity wxUserEntity = new WxUserEntity();
        wxUserEntity.setId(wxUser.getId());
        wxUserEntity.setIntegral(integral - consume);
        tStoreWxUserService.update(wxUserEntity);

        //积分记录
        TStoreIntegralRecordEntity integralRecord = new TStoreIntegralRecordEntity();
        integralRecord.setUserId(wxUser.getId());
        integralRecord.setOpenId(wxUser.getOpenId());
        integralRecord.setAppId(wxUser.getAppId());
        integralRecord.setRemarks("积分抽奖");
        integralRecord.setIntegral(0-consume);//负数
        integralRecord.setIntegralType(4);
        tStoreIntegralRecordService.save(integralRecord);

        //中奖 减库存
        if (sort != 8) {
            type = 1;

            IntegralDrawPrizeEntity prizeEntity = new IntegralDrawPrizeEntity();
            prizeEntity.setId(prizeList.get(sort - 1).getId());
            prizeEntity.setStock(prizeList.get(sort - 1).getStock() - 1);
            prizeEntity.setUpdateTime(new Date());
            integralDrawPrizeService.update(prizeEntity);
        }

        //中奖记录
        IntegralDrawRecordEntity drawRecord = new IntegralDrawRecordEntity();
        drawRecord.setOpenid(wxUser.getId());
        drawRecord.setIntegralRecordId(integralRecord.getId());
        drawRecord.setConsumeIntegral(consume);
        drawRecord.setPrizeId(prizeList.get(sort - 1).getId());
        drawRecord.setPrizeName(prizeList.get(sort - 1).getPrize());
        drawRecord.setPrizeSort(sort);
        drawRecord.setType(type);
        integralDrawRecordService.save(drawRecord);

        return R.ok().put("prize", prizeList.get(sort - 1).getPrize()).put("sort", sort).put("integral", wxUserEntity.getIntegral());
    }


}
