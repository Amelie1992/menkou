
package com.wzlue.app.controller.feeReturn;

import com.wzlue.app.common.annotation.Login;
import com.wzlue.app.common.annotation.LoginUser;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.jobApplication.entity.FeeReturnRecordEntity;
import com.wzlue.jobApplication.service.FeeReturnRecordService;
import com.wzlue.recruitment.entity.ShopRecruitmentEntity;
import com.wzlue.recruitment.service.ShopRecruitmentService;
import com.wzlue.wechat.entity.WxUserEntity;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * （招聘）返费
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 17:12:55
 */
@Slf4j
@RestController
@RequestMapping("/app/feeReturn")
public class ApiFeeReturnController {

    @Autowired
    private FeeReturnRecordService feeReturnRecordService;

    @Autowired
    private ShopRecruitmentService shopRecruitmentService;

    /**
     * 记录
     * type 类型 1返费记录  2提现记录
     *
     * userId 用户id @RequestAttribute("userId")
     */
    @Login
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params, @RequestAttribute("userId") String userId) {
        params.put("openid", userId);//用户id==对应多个入职过的id
        //查询列表数据(入职用户所有的返费，包括其他已离职的)
        List<FeeReturnRecordEntity> feeReturnRecordList = feeReturnRecordService.userFeeReturn(params);
        //List<FeeReturnRecordEntity> feeReturnRecordList = feeReturnRecordService.queryList(params);//当前入职的返费
        for (FeeReturnRecordEntity feeReturnRecord : feeReturnRecordList) {
            ShopRecruitmentEntity shopRecruitmentEntity = shopRecruitmentService.queryObject(feeReturnRecord.getJobApplication().getShopRecruitmentId());
            feeReturnRecord.setCompanyName(shopRecruitmentEntity.getRecruitmentFirm());
        }
        int total = feeReturnRecordList.size();
        return R.page(feeReturnRecordList, total);
    }

}
