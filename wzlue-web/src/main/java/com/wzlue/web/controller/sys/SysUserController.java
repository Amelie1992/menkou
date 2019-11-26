package com.wzlue.web.controller.sys;

import cn.hutool.core.util.StrUtil;
import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.common.exception.RRException;
import com.wzlue.common.utils.Constant;
import com.wzlue.common.validator.Assert;
import com.wzlue.common.validator.ValidatorUtils;
import com.wzlue.common.validator.group.AddGroup;
import com.wzlue.common.validator.group.UpdateGroup;
import com.wzlue.sys.entity.SysUserEntity;
import com.wzlue.sys.form.PasswordForm;
import com.wzlue.sys.service.SysUserRoleService;
import com.wzlue.sys.service.SysUserService;
import com.wzlue.wechat.entity.WxAppEntity;
import com.wzlue.wechat.service.WxAppService;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @author chenshun
 * @email wzlue.com
 * @date 2016年10月31日 上午10:40:10
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private WxAppService wxAppService;

    /**
     * 所有用户列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params) {
        //只有超级管理员（角色），才能查看所有管理员列表， 其他只能看到自己创建的后台用户
        if (getUserId() != Constant.SUPER_ADMIN) {//不是超级管理员
            params.put("createUserId", getUserId());
        }
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(getUserId());
        for (Long roleId : roleIdList) {
            if (roleId.toString().equals("1")) {//超级管理员角色
                params.put("createUserId", null);
            }
        }

        //查询列表数据
        Query query = new Query(params);
        List<SysUserEntity> userList = sysUserService.queryList(query);
        int total = sysUserService.queryTotal(query);

        return R.page(userList, total);
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    public R info() {
        return R.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @RequestMapping("/password")
    public R password(@RequestBody PasswordForm form) {
        Assert.isBlank(form.getNewPassword(), "新密码不为能空");

        //sha256加密
        String password = new Sha256Hash(form.getPassword(), getUser().getSalt()).toHex();
        //sha256加密
        String newPassword = new Sha256Hash(form.getNewPassword(), getUser().getSalt()).toHex();

        //更新密码
        int count = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (count == 0) {
            return R.error("原密码不正确");
        }

        return R.ok();
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.queryObject(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, AddGroup.class);

        // 门店账号 保存用户设置门店信息
        if (StrUtil.isNotBlank(getUser().getAppId()) && StrUtil.equals(getUser().getStore(), "1")) {
            user.setStore(getUser().getStore());
            user.setAppId(getUser().getAppId());
            user.setAppName(getUser().getAppName());
        }

        user.setCreateUserId(getUserId());
        sysUserService.save(user);

        return R.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody SysUserEntity user) {
        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        //不是超级管理员（角色） 只能修改自己创建的后台用户
        if (getUserId() != Constant.SUPER_ADMIN) {//不是admin
            user.setCreateUserId(getUserId());
        }

        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(getUserId());
        for (Long roleId : roleIdList) {
            if (roleId.toString().equals("1")) {//超级管理员角色
                user.setCreateUserId(null);
            }
        }

        sysUserService.update(user);

        return R.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.error("当前用户不能删除");
        }

        sysUserService.deleteBatch(userIds);

        return R.ok();
    }


    /**
     * 分配门店
     *
     * @param id    系统用户id
     * @param appId 微信公众号 appid
     * @return R
     */
    @SysLog("分配门店")
    @RequestMapping("/bindApp")
    @RequiresPermissions("sys:user:bindApp")
    public R bindApp(@RequestParam("userId") Long id, @RequestParam("appId") String appId) {


        // 只有超管可以分配门店
        if (getUserId() != 1L)
            throw new RRException("没有权限，分配门店");

        // appid 是否有效

        WxAppEntity appEntity = wxAppService.getById(appId);
        if (null == appEntity)
            throw new RRException("绑定的门店不存在");
        else if (!StrUtil.equals(appEntity.getId(), appId))
            throw new RRException("绑定的门店不存在");


        // 系统用户是否绑定过门店
        SysUserEntity sysUserEntity = sysUserService.queryObject(id);
        if (StrUtil.isNotBlank(sysUserEntity.getAppId()) || StrUtil.equals(sysUserEntity.getStore(), "1"))
            throw new RRException("系统用户已绑定过门店");

        // 绑定门店
        sysUserEntity = new SysUserEntity();
        sysUserEntity.setUserId(id);
        sysUserEntity.setAppId(appEntity.getId());
        sysUserEntity.setAppName(appEntity.getName());
        sysUserEntity.setStore("1");

        if (sysUserService.bindApp(sysUserEntity))
            return R.ok();
        else
            return R.error();

    }
}
