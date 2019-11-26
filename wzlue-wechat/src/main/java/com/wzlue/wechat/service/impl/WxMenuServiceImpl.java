package com.wzlue.wechat.service.impl;

import cn.hutool.core.util.IdUtil;
import com.wzlue.wechat.config.WxMpConfiguration;
import com.wzlue.wechat.dao.WxAppDao;
import com.wzlue.wechat.dao.WxMenuDao;
import com.wzlue.wechat.entity.Menu;
import com.wzlue.wechat.entity.MenuButtonEntity;
import com.wzlue.wechat.entity.WxAppEntity;
import com.wzlue.wechat.entity.WxMenuEntity;
import com.wzlue.wechat.service.WxMenuService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 自定义菜单
 *
 * @author JL
 * @date 2019-03-27 16:52:10
 */
@Service
public class WxMenuServiceImpl implements WxMenuService {

    @Autowired
    private WxAppDao wxAppDao;
    @Autowired
    private WxMenuDao wxMenuDao;

    /***
     * 获取WxApp下的菜单树结构
     * @param appId
     * @return
     */
    @Override
    public String getWxMenuButton(String appId) {
        //查出一级菜单
        List<WxMenuEntity> listWxMenu = wxMenuDao.selectMenu(appId, "-1", 1);

        Menu menu = new Menu();
        List<MenuButtonEntity> listMenuButton = new ArrayList<>();
        MenuButtonEntity menuButton;
        List<MenuButtonEntity> subButtons;
        MenuButtonEntity subButton;
        if(listWxMenu!=null&&listWxMenu.size()>0){
            for(WxMenuEntity wxMenu : listWxMenu){
                menuButton = new MenuButtonEntity();
                menuButton.setName(wxMenu.getName());
                String type = wxMenu.getType();
                if(StringUtils.isNotBlank(type)){//无二级菜单
                    menuButton.setType(type);
                    setButtonValue(menuButton,wxMenu);
                }else{//有二级菜单
                    //查出二级菜单
                    List<WxMenuEntity> listWxMenu1 = wxMenuDao.selectMenu(appId, wxMenu.getId(), 1);

                    subButtons = new ArrayList<>();
                    for(WxMenuEntity wxMenu1 : listWxMenu1){
                        subButton = new MenuButtonEntity();
                        String type1 = wxMenu1.getType();
                        subButton.setName(wxMenu1.getName());
                        subButton.setType(type1);
                        setButtonValue(subButton,wxMenu1);
                        subButtons.add(subButton);
                    }
                    menuButton.setSubButtons(subButtons);
                }
                listMenuButton.add(menuButton);
            }
        }
        menu.setButton(listMenuButton);
        return menu.toString();
    }

    void setButtonValue(MenuButtonEntity menuButton, WxMenuEntity wxMenu) {
        menuButton.setKey(wxMenu.getId());
        menuButton.setUrl(wxMenu.getUrl());
        menuButton.setContent(wxMenu.getContent());
        menuButton.setRepContent(wxMenu.getRepContent());
        menuButton.setMediaId(wxMenu.getRepMediaId());
        menuButton.setRepType(wxMenu.getRepType());
        menuButton.setRepName(wxMenu.getRepName());
        menuButton.setAppId(wxMenu.getMaAppId());
        menuButton.setPagePath(wxMenu.getMaPagePath());
        menuButton.setUrl(wxMenu.getUrl());
        menuButton.setRepUrl(wxMenu.getRepUrl());
        menuButton.setRepHqUrl(wxMenu.getRepHqUrl());
        menuButton.setRepDesc(wxMenu.getRepDesc());
        menuButton.setRepThumbMediaId(wxMenu.getRepThumbMediaId());
        menuButton.setRepThumbUrl(wxMenu.getRepThumbUrl());
    }

    /**
     * 保存并发布菜单
     *
     * @param
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveAndRelease(String appId, String strWxMenu) throws WxErrorException {
        Menu menu = Menu.fromJson(strWxMenu);
        List<MenuButtonEntity> buttons = menu.getButton();
        //先删除
        wxMenuDao.deleteByAppID(appId);

        WxAppEntity wxApp = wxAppDao.queryObject(appId);
        LocalDateTime now = LocalDateTime.now();
        WxMenuEntity wxMenu = null;
        WxMenuEntity wxMenu1 = null;
        int sort1 = 1;
        int sort2 = 1;
        //入库
        for(MenuButtonEntity menuButton : buttons){
            wxMenu = new WxMenuEntity();
            setWxMenuValue(wxMenu,menuButton,wxApp);
            wxMenu.setSort(sort1);
            wxMenu.setParentId("-1");
            wxMenu.setId(IdUtil.simpleUUID());
            wxMenu.setCreateTime(now);
            wxMenuDao.save(wxMenu);
            menuButton.setKey(wxMenu.getId());
            sort1++;
            for(MenuButtonEntity menuButton1 : menuButton.getSubButtons()){
                wxMenu1 = new WxMenuEntity();
                setWxMenuValue(wxMenu1,menuButton1,wxApp);
                wxMenu1.setSort(sort2);
                wxMenu1.setParentId(wxMenu.getId());
                wxMenu1.setId(IdUtil.simpleUUID());
                wxMenu.setCreateTime(now);
                wxMenuDao.save(wxMenu1);
                menuButton1.setKey(wxMenu1.getId());
                sort2++;
            }
        }
        //创建菜单
        WxMpConfiguration.getMpService(appId).getMenuService().menuCreate(menu.toString());
    }

    void setWxMenuValue(WxMenuEntity wxMenu, MenuButtonEntity menuButton, WxAppEntity wxApp) {
        wxMenu.setId(menuButton.getKey());
        wxMenu.setAppId(wxApp.getId());
        wxMenu.setType(menuButton.getType());
        wxMenu.setName(menuButton.getName());
        wxMenu.setUrl(menuButton.getUrl());
        wxMenu.setRepMediaId(menuButton.getMediaId());
        wxMenu.setRepType(menuButton.getRepType());
        wxMenu.setRepName(menuButton.getRepName());
        wxMenu.setMaAppId(menuButton.getAppId());
        wxMenu.setMaPagePath(menuButton.getPagePath());
        wxMenu.setRepContent(menuButton.getRepContent());
        wxMenu.setContent(menuButton.getContent());
        wxMenu.setRepUrl(menuButton.getRepUrl());
        wxMenu.setRepHqUrl(menuButton.getRepHqUrl());
        wxMenu.setRepDesc(menuButton.getRepDesc());
        wxMenu.setRepThumbMediaId(menuButton.getRepThumbMediaId());
        wxMenu.setRepThumbUrl(menuButton.getRepThumbUrl());
        menuButton.setRepUrl(null);
        menuButton.setRepDesc(null);
        menuButton.setRepHqUrl(null);
        menuButton.setContent(null);
        menuButton.setRepContent(null);
        menuButton.setRepType(null);
        menuButton.setRepName(null);
        menuButton.setRepThumbMediaId(null);
        menuButton.setRepThumbUrl(null);
    }

    @Override
    public WxMenuEntity getById(String eventKey) {
        return wxMenuDao.getById(eventKey);
    }
}
