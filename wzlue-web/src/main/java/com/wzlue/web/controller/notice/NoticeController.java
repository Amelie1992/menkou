
package com.wzlue.web.controller.notice;

import com.wzlue.common.base.R;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wzlue.web.controller.sys.AbstractController;

import com.wzlue.notice.entity.NoticeEntity;
import com.wzlue.notice.service.NoticeService;
import com.wzlue.common.utils.PageUtils;
import com.wzlue.common.base.Query;
import com.wzlue.common.base.R;




/**
 * 
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-10-28 14:34:50
 */
@RestController
@RequestMapping("/notice/notice")
public class NoticeController extends AbstractController{
	@Autowired
	private NoticeService noticeService;
	
	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<NoticeEntity> noticeList = noticeService.queryList(query);
		int total = noticeService.queryTotal(query);
		
		return R.page(noticeList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	@RequiresPermissions("notice:notice:info")
	public R info(@PathVariable("id") Long id){
		NoticeEntity notice = noticeService.queryObject(id);
		
		return R.ok().put("notice", notice);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	@RequiresPermissions("notice:notice:save")
	public R save(@RequestBody NoticeEntity notice){
		noticeService.save(notice);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	@RequiresPermissions("notice:notice:update")
	public R update(@RequestBody NoticeEntity notice){
		noticeService.update(notice);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("notice:notice:delete")
	public R delete(@RequestBody Long[] ids){
		noticeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
