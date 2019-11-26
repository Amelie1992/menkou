
package com.wzlue.app.controller.store;

import cn.hutool.core.util.EscapeUtil;
import cn.hutool.core.util.StrUtil;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.store.entity.TStoreContactUsEntity;
import com.wzlue.store.entity.TStoreTelephoneEntity;
import com.wzlue.store.service.TStoreContactUsService;
import com.wzlue.store.service.TStoreTelephoneService;
import com.wzlue.app.controller.sys.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.wzlue.common.utils.EscapeUtil.unescape;


/**
 * 门店电话表
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 16:10:28
 */
@RestController
@RequestMapping("/app/tstoretelephone")
public class TStoreTelephoneController extends AbstractController {
    @Autowired
    private TStoreTelephoneService tStoreTelephoneService;
    @Autowired
    private TStoreContactUsService tStoreContactUsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        String appId = (String) query.get("appId");
        if (StrUtil.isBlank(appId)) {
            return R.page(new Object[]{}, 0);
        }
        List<TStoreTelephoneEntity> tStoreTelephoneList = tStoreTelephoneService.queryListByParam(query);
        int total = tStoreTelephoneService.queryTotalByParam(query);

        return R.page(tStoreTelephoneList, total);
    }

    /**
     * 门店联系我们
     */
    @RequestMapping("/contactUs")
    public R contactUs(@RequestParam Map<String, Object> params) {
        //查询列表数据
        TStoreContactUsEntity contactUs = null;
        Query query = new Query(params);
        String appId = (String) query.get("appId");
        if (StrUtil.isBlank(appId)) {
            return R.ok();
        }
        List<TStoreContactUsEntity> tStoreContactUsList = tStoreContactUsService.queryListByParam(query);
        if (null != tStoreContactUsList && tStoreContactUsList.size() > 0) {
            contactUs = tStoreContactUsList.get(0);
            if (null != contactUs && null != contactUs.getContent()) {
                contactUs.setContent(unescape(contactUs.getContent()));
            }
        }

        return R.ok().put("contactUs", contactUs);
    }


}
