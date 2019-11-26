package com.wzlue.web.controller.provide;

import com.wzlue.common.base.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wzlue.common.utils.EasyPoiUtil;
import com.wzlue.common.utils.ValidateUtil;
import com.wzlue.recruitment.entity.PUploadHistoryEntity;
import com.wzlue.recruitment.entity.ProvidePersonnelEntity;
import com.wzlue.recruitment.service.PUploadHistoryService;
import com.wzlue.recruitment.service.ProvidePersonnelService;
import com.wzlue.sys.entity.SysAreaEntity;
import com.wzlue.sys.entity.SysCityInfoEntity;
import com.wzlue.sys.service.SysAreaService;
import com.wzlue.sys.service.SysCityInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.wzlue.web.controller.sys.AbstractController;

import com.wzlue.common.base.Query;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 供人信息表
 * 
 * @author wzlue
 * @email wzlue.com
 * @date 2019-08-07 16:13:50
 */
@RestController
@RequestMapping("/provide/providePersonnel")
public class ProvidePersonnelController extends AbstractController{
	@Autowired
	private ProvidePersonnelService providePersonnelService;
	@Autowired
	private SysCityInfoService sysCityInfoService;
	@Autowired
	private PUploadHistoryService pUploadHistoryService;

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		if(!isStore(query)){
			return R.page(new Object[]{},0);
		}
		//appId=当前登录人的
		List<ProvidePersonnelEntity> providePersonnelList = providePersonnelService.queryList(query);
		for (ProvidePersonnelEntity pro:providePersonnelList) {
			if (pro.getSex()==1){
				pro.setGenderName("男");
			} else {
				pro.setGenderName("女");
			}
		}
		int total = providePersonnelService.queryTotal(query);

		return R.page(providePersonnelList,total);
	}

	/**
	 * 列表
	 */
	@RequestMapping("/blist")
	public R blist(@RequestParam Map<String, Object> params){
		//appId=当前登录人的
		List<ProvidePersonnelEntity> providePersonnelList = providePersonnelService.queryList(params);
		for (ProvidePersonnelEntity pro:providePersonnelList) {
			if (pro.getSex()==1){
				pro.setGenderName("男");
			} else {
				pro.setGenderName("女");
			}
		}
		int total = providePersonnelService.queryTotal(params);

		return R.page(providePersonnelList,total);
	}


	/**
	 * 各门店向我供人
	 */
	@RequestMapping("/list2")
	public R list2(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);

		List<ProvidePersonnelEntity> providePersonnelList = providePersonnelService.queryList(query);
		int total = providePersonnelService.queryTotal(query);

		return R.page(providePersonnelList,total);
	}
	
	
	/**
	 * 信息
	 */
	@RequestMapping("/info/{id}")
	//@RequiresPermissions("provide:providePersonnel:info")
	public R info(@PathVariable("id") Long id){
		ProvidePersonnelEntity providePersonnel = providePersonnelService.queryObject(id);
		
		return R.ok().put("providePersonnel", providePersonnel);
	}
	
	/**
	 * 保存
	 */
	@RequestMapping("/save")
	//@RequiresPermissions("provide:providePersonnel:save")
	public R save(@RequestBody ProvidePersonnelEntity providePersonnel){
		providePersonnel.setAppId(getUser().getAppId());
		providePersonnelService.save(providePersonnel);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@RequestMapping("/update")
	//@RequiresPermissions("provide:providePersonnel:update")
	public R update(@RequestBody ProvidePersonnelEntity providePersonnel){
		providePersonnelService.update(providePersonnel);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("provide:providePersonnel:delete")
	public R delete(@RequestBody Long[] ids){
		providePersonnelService.deleteBatch(ids);
		
		return R.ok();
	}

	/**
	 * 上传excel
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/upload")
	@Transactional
	public R importExcel(@RequestParam(value = "excelFile", required = false) MultipartFile file,String remark,String shopRecruitmentId)
	{
		R r = R.ok();
		int errorCount=0;
		if (file != null)
		{
			//添加一条上传记录
			PUploadHistoryEntity pUploadHistory = new PUploadHistoryEntity();
			pUploadHistory.setShopRecruitmentId(Long.valueOf(shopRecruitmentId));//招聘id
			pUploadHistory.setRemark(remark);//备注
			pUploadHistory.setAppId(getUser().getAppId());
			pUploadHistoryService.save(pUploadHistory);

			//省
			Map<String,String> provinceMap = sysCityInfoService.queryByCodeToMap("-1");
			//性别
			List<String> genterMap = new ArrayList<String>();
			genterMap.add("男");
			genterMap.add("女");
			//读取excel
			List<ProvidePersonnelEntity> list = EasyPoiUtil.importExcel(file, 0, 1, ProvidePersonnelEntity.class);
			List<ProvidePersonnelEntity> listOK=new ArrayList<ProvidePersonnelEntity>();
			List<ProvidePersonnelEntity> listError=new ArrayList<ProvidePersonnelEntity>();
			if(list.size()>0)
			{

				for (int i = 0; i < list.size(); i++)
				{
					//判断省是否存在
					String provinceId=provinceMap.get(list.get(i).getProvince());
					if(ValidateUtil.isNullOrEmpty(provinceId))
					{
						list.get(i).setErrorReason("省不存在");
						listError.add(list.get(i));
						continue;
					}
					else
					{
						//存入省code
						list.get(i).setProvince(String.valueOf(provinceId));
						//根据对应的省查询市集合
						Map<String,String> cityMap=sysCityInfoService.queryByCodeToMap(provinceId);
						//判断市是否存在
						if(ValidateUtil.isNullOrEmpty(list.get(i).getCity())){
							list.get(i).setErrorReason("市不能为空");
							listError.add(list.get(i));
							continue;
						}
						String cityId=cityMap.get(list.get(i).getCity());
						if(ValidateUtil.isNullOrEmpty(cityId))
						{
							//查询省名字
							SysCityInfoEntity sysCityInfo = new SysCityInfoEntity();
							sysCityInfo.setSubCityCode("-1");
							sysCityInfo.setCityCode(list.get(i).getProvince());
							sysCityInfo = sysCityInfoService.queryNameByCode(sysCityInfo);

							list.get(i).setErrorReason(sysCityInfo.getCityName()+"下不存在"+list.get(i).getCity());
							listError.add(list.get(i));
							continue;
						}
						else
						{
							//存入市code
							list.get(i).setCity(String.valueOf(cityId));
						}
					}
					// 判断性别是否存在
					String gender = list.get(i).getGenderName();
					if (genterMap.contains(gender)) {
						if ("男".equals(gender)) {
							list.get(i).setSex(1);
						} else {
							list.get(i).setSex(2);
						}
					} else {
						list.get(i).setErrorReason("性别输入有误，请检查表数据");
						listError.add(list.get(i));
						continue;
					}
					list.get(i).setShopRecruitmentId(Long.valueOf(shopRecruitmentId));
					list.get(i).setUploadHistoryId(pUploadHistory.getId());
					list.get(i).setCreateDate(new Date());
					list.get(i).setStatus(1);
					list.get(i).setAppId(getUser().getAppId());
					listOK.add(list.get(i));
				}
				System.out.println(listError);
				if(listOK.size()>0)
				{
					providePersonnelService.saveList(listOK);
				}
				if(listError.size()>0)
				{
					errorCount=listError.size();
					r.put("listError", listError);
				}
				pUploadHistory.setNum(Long.valueOf(listOK.size()));//成功条数
				pUploadHistoryService.update(pUploadHistory);
			}
		}
		r.put("errorCount", errorCount);
		return r;
	}

}
