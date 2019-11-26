
package com.wzlue.app.controller.doorway;

import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.doorway.entity.TDoorwayAboutUsEntity;
import com.wzlue.doorway.entity.TDoorwayHotlineEntity;
import com.wzlue.doorway.entity.TDoorwayTelephoneEntity;
import com.wzlue.doorway.service.TDoorwayAboutUsService;
import com.wzlue.doorway.service.TDoorwayHotlineService;
import com.wzlue.doorway.service.TDoorwayTelephoneService;
import com.wzlue.app.controller.sys.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.wzlue.common.utils.EscapeUtil.unescape;


/**
 * 门口电话表
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-01 16:08:30
 */
@RestController
@RequestMapping("/app/tdoorwaytelephone")
public class TDoorwayTelephoneController extends AbstractController {
    @Autowired
    private TDoorwayTelephoneService tDoorwayTelephoneService;
    @Autowired
    private TDoorwayAboutUsService tDoorwayAboutUsService;
    @Autowired
    private TDoorwayHotlineService tDoorwayHotLineService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
//        query.put("limit", 1);
        List<TDoorwayTelephoneEntity> tDoorwayAboutUsEntityList = tDoorwayTelephoneService.queryList(query);
        int total = tDoorwayTelephoneService.queryTotal(query);

        return R.page(tDoorwayAboutUsEntityList, total);
    }

    /**
     * 平台公司简介
     */
    @RequestMapping("/aboutUs")
    public R aboutUs(@RequestParam Map<String, Object> params) {
        TDoorwayAboutUsEntity aboutUs = null;
        Query query = new Query(params);
        query.put("limit", 1);
        List<TDoorwayAboutUsEntity> tDoorwayAboutUsEntityList = tDoorwayAboutUsService.queryList(query);
        if (null != tDoorwayAboutUsEntityList && tDoorwayAboutUsEntityList.size() > 0) {
            aboutUs = tDoorwayAboutUsEntityList.get(0);
            if (null != aboutUs && null != aboutUs.getProfile()) {
                aboutUs.setProfile(unescape(aboutUs.getProfile()));
            }
        }
        return R.ok().put("aboutUs", aboutUs);
    }


    /**
     * 平台热线
     */
    @RequestMapping("/hotline")
    public R hotline(@RequestParam Map<String, Object> params) {
        TDoorwayHotlineEntity hotline = null;
        Query query = new Query(params);
        query.put("limit", 1);
        List<TDoorwayHotlineEntity> tDoorwayHotlineEntityList = tDoorwayHotLineService.queryList(query);
        if (null != tDoorwayHotlineEntityList && tDoorwayHotlineEntityList.size() > 0) {
            hotline = tDoorwayHotlineEntityList.get(0);
        }
        return R.ok().put("hotline", hotline);
    }

}
