package com.wzlue.web.controller.sys;

import com.wzlue.common.annotation.SysLog;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;
import com.wzlue.common.utils.Constant;
import com.wzlue.common.validator.ValidatorUtils;
import com.wzlue.sys.entity.SysRoleEntity;
import com.wzlue.sys.service.SysRoleMenuService;
import com.wzlue.sys.service.SysRoleService;
import com.wzlue.sys.service.SysUserRoleService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理
 * 
 * @author chenshun
 * @email wzlue.com
 * @date 2016年11月8日 下午2:18:33
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 角色列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:role:list")
	public R list(@RequestParam Map<String, Object> params){
		//只有超级管理员（角色），才能查看所有角色列表， 其他只能看到自己创建的角色列表
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
		List<SysRoleEntity> list = sysRoleService.queryList(query);
		int total = sysRoleService.queryTotal(query);
		
//		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		
		return R.page(list,total);
	}
	
	/**
	 * 角色列表
	 */
	@RequestMapping("/select")
	@RequiresPermissions("sys:role:select")
	public R select(){
		Map<String, Object> params = new HashMap<>();

        //只有超级管理员（角色），才能查看所有角色列表， 其他只能看到自己创建的角色列表
        if (getUserId() != Constant.SUPER_ADMIN) {//不是超级管理员
            params.put("createUserId", getUserId());
        }
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(getUserId());
        for (Long roleId : roleIdList) {
            if (roleId.toString().equals("1")) {//超级管理员角色
                params.put("createUserId", null);
            }
        }

		List<SysRoleEntity> list = sysRoleService.queryList(params);
		
		return R.ok().put("list", list);
	}
	
	/**
	 * 角色信息
	 */
	@RequestMapping("/info/{roleId}")
	@RequiresPermissions("sys:role:info")
	public R info(@PathVariable("roleId") Long roleId){
		SysRoleEntity role = sysRoleService.queryObject(roleId);
		
		//查询角色对应的菜单
		List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
		role.setMenuIdList(menuIdList);
		
		return R.ok().put("role", role);
	}
	
	/**
	 * 保存角色
	 */
	@SysLog("保存角色")
	@RequestMapping("/save")
	@RequiresPermissions("sys:role:save")
	public R save(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		role.setCreateUserId(getUserId());
		sysRoleService.save(role);
		
		return R.ok();
	}
	
	/**
	 * 修改角色
	 */
	@SysLog("修改角色")
	@RequestMapping("/update")
	@RequiresPermissions("sys:role:update")
	public R update(@RequestBody SysRoleEntity role){
		ValidatorUtils.validateEntity(role);
		
		role.setCreateUserId(getUserId());
		sysRoleService.update(role);
		
		return R.ok();
	}
	
	/**
	 * 删除角色
	 */
	@SysLog("删除角色")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:role:delete")
	public R delete(@RequestBody Long[] roleIds){
		sysRoleService.deleteBatch(roleIds);
		
		return R.ok();
	}
}
