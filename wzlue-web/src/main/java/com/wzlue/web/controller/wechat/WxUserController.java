package com.wzlue.web.controller.wechat;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.common.utils.WxReturnCode;
import com.wzlue.web.controller.sys.AbstractController;
import com.wzlue.wechat.entity.WxAutoReplyEntity;
import com.wzlue.wechat.entity.WxUserEntity;
import com.wzlue.wechat.service.WxAutoReplyService;
import com.wzlue.wechat.service.WxUserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信用户
 *
 * @author wzlue
 * @email wzlue.com
 * @date 2019-07-24 11:11:41
 */
@Slf4j
@RestController
@RequestMapping("/wechat/wxuser")
public class WxUserController extends AbstractController {

    @Autowired
    private WxUserService wxUserService;
    @Autowired
    private WxAutoReplyService wxAutoReplyService;

    /**
     * 分页查询
     *
     * @param params tagidList 标签
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("wechat:wxuser:list")
    public R getWxUserPage(@RequestParam Map<String, Object> params) {

        //查询列表数据
        Query query = new Query(params);

        if (!isStore(query)) {
            return R.page(new Object[]{}, 0);

        }
        List<WxUserEntity> wxUserEntities = wxUserService.queryList(query);
        int count = wxUserService.queryTotal(query);
        return R.page(wxUserEntities, count);
    }
    /**
     * 分页查询
     *
     * @param params tagidList 标签
     * @return
     */
    @GetMapping("/replyList")
    @RequiresPermissions("wechat:wxuser:list")
    public R replyList(@RequestParam Map<String, Object> params) {

        //查询列表数据
        Query query = new Query(params);

        if (!isStore(query)) {
            return R.page(new Object[]{}, 0);

        }
        List<WxAutoReplyEntity> wxAutoReplyEntities = wxAutoReplyService.queryList(query);
        int count = wxAutoReplyService.queryTotal(query);
        return R.page(wxAutoReplyEntities, count);
    }
    /**
     * getReplyInfo获取详情
     *
     * @param params tagidList 标签
     * @return
     */
    @RequestMapping("/getReplyInfo/{id}")
    @RequiresPermissions("wechat:wxuser:list")
    public R getReplyInfo(@PathVariable("id")String id) {
        WxAutoReplyEntity wxAutoReplyEntity = wxAutoReplyService.queryObject(id);
        return R.ok().put("data",wxAutoReplyEntity);
    }


    /**
     * 通过id查询微信用户
     *
     * @param id id
     * @return R
     */
    @GetMapping("/info/{id}")
    @RequiresPermissions("wechat:wxuser:info")
    public R getById(@PathVariable("id") String id) {
        WxUserEntity wxUserEntity = wxUserService.getById(id);

        return R.ok().put("wxuser", wxUserEntity);
    }

    /**
     * 新增微信用户
     *
     * @param wxUser 微信用户
     * @return R
     */
    @SysLog("新增微信用户")
    @PostMapping("save")
    @RequiresPermissions("wechat:wxuser:save")
    public R save(@RequestBody WxUserEntity wxUser) {
        wxUserService.save(wxUser);
        return R.ok();
    }

    /**
     * 修改微信用户
     *
     * @param wxUser 微信用户
     * @return R
     */
    @SysLog("修改微信用户")
    @PutMapping("updateById")
    @RequiresPermissions("wechat:wxuser:update")
    public R updateById(@RequestBody WxUserEntity wxUser) {
        wxUserService.updateById(wxUser);
        return R.ok();
    }

    /**
     * 通过id删除微信用户
     *
     * @param id id
     * @return R
     */
    @SysLog("删除微信用户")
    @DeleteMapping("/{id}")
    @RequiresPermissions("wechat:wxuser:delete")
    public R removeById(@PathVariable String id) {
        wxUserService.delete(id);
        return R.ok();
    }

    @PostMapping("/synchron")
    @RequiresPermissions("wechat:wxuser:synchro")
    public R synchron(@RequestBody WxUserEntity wxUser) {

        try {
            // 门店账号
            if (StrUtil.isNotBlank(getUser().getAppId()) && StrUtil.equals(getUser().getStore(), "1")) {
                wxUser.setAppId(getUser().getAppId());
            }

            wxUserService.synchroWxUser(wxUser.getAppId(), getUserId());
            return new R();
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("同步微信用户失败", e);
            return WxReturnCode.wxErrorExceptionHandler(e);
        }
    }
    @PostMapping("/getReplyDefault")
    @RequiresPermissions("wechat:wxuser:synchro")
    public R getReplyDefault(@RequestBody WxUserEntity wxUser) {

        try {
            List<WxAutoReplyEntity> list=wxAutoReplyService.queryDefault(wxUser.getAppId());
            Map<String,String> result=new HashMap<>();
            for (WxAutoReplyEntity wxAutoReplyEntity : list) {
                if(wxAutoReplyEntity.getType() != null && wxAutoReplyEntity.getType().equals("1")){
                    result.put("followId",wxAutoReplyEntity.getId());
                    result.put("followText",wxAutoReplyEntity.getRepContent());
                }
                if(wxAutoReplyEntity.getType() != null && wxAutoReplyEntity.getType().equals("3") && wxAutoReplyEntity.getReqKey().equals(wxUser.getAppId()+"_default")){
                    result.put("defaultId",wxAutoReplyEntity.getId());
                    result.put("defaultText",wxAutoReplyEntity.getRepContent());
                }
            }
//            wxUserService.synchroWxUser(, getUserId());
            return new R().put("data",result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("查询默认配置失败", e);
//            return WxReturnCode.wxErrorExceptionHandler(e);
        }
        return R.error("查询默认配置失败");
    }
    @PostMapping("/updateReplyDefault")
    @RequiresPermissions("wechat:wxuser:synchro")
    public R updateReplyDefault(@RequestBody Map<String,String> params) {

        try {
            WxAutoReplyEntity followReply = new WxAutoReplyEntity();
            Map<String,String> result=new HashMap<>();
            followReply.setAppId(params.get("appId"));
            followReply.setId(params.get("followId"));
            followReply.setRepType("text");
            followReply.setType("1");
            followReply.setRepContent(params.get("followText"));
            if(followReply.getId() == null){
                String id = IdUtil.simpleUUID();
                result.put("followId",id);
                result.put("followText",params.get("followText"));
                followReply.setId(id);
                wxAutoReplyService.save(followReply);
            }else{
                result.put("followId",params.get("followId"));
                result.put("followText",params.get("followText"));
                wxAutoReplyService.update(followReply);
            }
            WxAutoReplyEntity defaultReply = new WxAutoReplyEntity();
            defaultReply.setAppId(params.get("appId"));
            defaultReply.setId(params.get("defaultId"));
            defaultReply.setRepType("text");
            defaultReply.setType("3");
            defaultReply.setReqKey(params.get("appId")+"_default");
            defaultReply.setRepContent(params.get("defaultText"));
            if(defaultReply.getId() == null){
//                defaultReply.setId();
                String id = IdUtil.simpleUUID();
                defaultReply.setId(id);
                result.put("defaultId",id);
                result.put("defaultText",params.get("defaultText"));
                wxAutoReplyService.save(defaultReply);
            }else{
                result.put("defaultId",params.get("defaultId"));
                result.put("defaultText",params.get("defaultText"));
                wxAutoReplyService.update(defaultReply);
            }
//            wxUserService.synchroWxUser(, getUserId());
            return R.ok("修改默认回复配置成功").put("data",result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("修改默认回复配置失败", e);
//            return WxReturnCode.wxErrorExceptionHandler(e);
        }
        return R.error("修改默认回复配置失败");
    }
    @PostMapping("/addReply")
    @RequiresPermissions("wechat:wxuser:synchro")
    public R addReply(@RequestBody WxAutoReplyEntity reply) {

        try {
            Map<String,String> result=new HashMap<>();
            if(reply.getId() != null){
                wxAutoReplyService.update(reply);
                return R.ok("修改成功");
            }
            reply.setId(IdUtil.simpleUUID());
            reply.setRepType("text");
            reply.setType("3");
            wxAutoReplyService.save(reply);
            return R.ok("保存成功").put("data",result);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("保存失败", e);
//            return WxReturnCode.wxErrorExceptionHandler(e);
        }
        return R.error("保存失败");
    }
    @PostMapping("/deleteReply")
    @RequiresPermissions("wechat:wxuser:synchro")
    public R deleteReply(@RequestBody String[] ids) {
            wxAutoReplyService.deleteBatch(ids);
        return R.ok("删除成功");
    }

    /**
     * 修改微信用户备注
     *
     * @param wxUser
     * @return
     */
    @SysLog("修改微信用户备注")
    @PutMapping("/remark")
    @RequiresPermissions("wechat:wxuser:remark')")
    public R remark(@RequestBody WxUserEntity wxUser) {
        try {
            wxUserService.updateRemark(wxUser);
            return R.ok();
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("修改微信用户备注失败", e);
            return WxReturnCode.wxErrorExceptionHandler(e);
        }
    }

    /**
     * 打标签
     *
     * @param data
     * @return
     */
    @PutMapping("/tagid-list")
    @RequiresPermissions("wechat:wxuser:tagging')")
    public R tagidList(@RequestBody JSONObject data) {
        try {
            String appId = data.getString("appId");
            String taggingType = data.getString("taggingType");
            JSONArray tagIdsArray = data.getJSONArray("tagIds");
            JSONArray openIdsArray = data.getJSONArray("openIds");
            String[] openIds = openIdsArray.toArray(new String[0]);
            for (Object tagId : tagIdsArray) {
                wxUserService.tagging(taggingType, appId, Long.valueOf(String.valueOf(tagId)), openIds);
            }
            return new R();
        } catch (WxErrorException e) {
            e.printStackTrace();
            log.error("修改微信用户备注失败", e);
            return WxReturnCode.wxErrorExceptionHandler(e);
        }
    }
}
