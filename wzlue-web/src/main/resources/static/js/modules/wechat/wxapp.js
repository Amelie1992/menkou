$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'wechat/wxapp/list',
        columns: [
            {checkbox: true, width: '60px'},
            {
                title: '公众号头像', field: 'headLogo',
                formatter: function (value, row, index) {
                    return '<img width="50px" height="50px" src=' + value + '>';
                }
            },
            {title: '公众号名称', field: 'name'},
            {
                title: '接入状态', field: 'state',
                formatter: function (value, row, index) {
                    if (value == '1') {
                        return '<span class="label label-success">已接入</span>';
                    } else if (value == '0') {
                        return '<span class="label label-primary">未接入</span>';
                    }
                }
            },
            {
                title: '使用状态', field: 'enabled',
                formatter: function (value, row, index) {
                    if (value == '1') {

                        return '<span class="label label-default">暂停</span>';
                    } else {
                        return '<span class="label label-success">使用</span>';
                    }
                }
            },
            {
                title: '有效期', field: 'expDate',
                formatter: function (value, row, index) {
                    if (row.expType == '1') {
                        return '<span class="label label-success">不限</span>';

                    } else if (new Date().getTime() > new Date(value).getTime()) {
                        return '<span class="label label-default">到期</span>';
                    }
                    else {
                        return value;
                    }
                }
            },
            {
                title: '门店资质', field: 'qualified',
                formatter: function (value, row, index) {
                    if (value == '0') {
                        return '<span class="label label-default">关闭</span>';
                    } else {
                        var conten = '<span class="label label-success">开启</span>';

                        if (hasPermission('wechat:wxapp:editQualified')) {
                            conten = conten + '&nbsp;<span class="label label-primary" style="cursor: pointer" onclick="showUe(\'' + row.id + '\') ">编辑</span>'
                        }
                        return conten;
                    }
                }
            },
            {title: '微信原始标识', field: 'weixinSign'},
            {title: 'AppID', field: 'id'},
            {title: '应用密钥', field: 'secret'},
            {title: 'token', field: 'token'},
            {title: 'EncodingAESKey', field: 'aesKey'},
            {title: '主体名称', field: 'principalName'},
            {title: '备注信息', field: 'remarks'},
            {
                title: '操作',
                width: '10%',
                formatter: function (value, row, index) {

                    let mes = '';
                    if (hasPermission('wechat:wxmenu:list')) {
                        mes += '<button type="button" class="btn btn-primary btn-sm" id="menu_page">菜单管理</button>&nbsp;';
                    }
                    if (hasPermission('wechat:wxuser:list')) {
                        mes += '<button type="button" class="btn btn-primary btn-sm" id="user_page">用户管理</button>&nbsp;';
                    }
                    if (hasPermission('wechat:wxuser:list')) {
                        mes += '<button type="button" class="btn btn-primary btn-sm" id="reply_page">回复管理</button>&nbsp;';
                    }

                    return mes;

                },
                events: operateEvent
            }
        ],
        //条件查询
        queryParams: {
            sidx: 'create_date',
            order: 'desc'
        }
    });
    //表单提交
    $("form").FM({
        fields: vm.fields,
        success: vm.saveOrUpdate,
    })

});

window.operateEvent = {
    'click #menu_page': function (event, value, row, index) {
        showMenu(row.id)
    },
    'click #user_page': function (event, value, row, index) {
        showUser(row.id)
    },
    'click #reply_page': function (event, value, row, index) {
        showReply(row.id)
    }
};

// 显示Ue 并设置内容
function showUe(id) {
    var description = '';
    var data = $('#table').bootstrapTable('getData');
    for (var i = 0; i < data.length; i++) {
        if (data[i].id == id) {
            description = data[i].description;
            break;
        }
    }

    vm.showUe(id, description);
}

// 显示Menu 并查询菜单
function showMenu(id) {
    vm.wxApp.id = id;
    vm.queryMenu(id);
    vm.showList = 'menu';

}

// 显示用户 并查询用户
function showUser(id) {
    vm.showList = 'user';
    vm.wxApp.id = id;
    vm.queryUser(id);

}

// 显示用户 并查询用户
function showReply(id) {
    $("#replyTable").bootstrapTable("destroy")
    vm.showList = 'reply';
    vm.wxApp.id = id;
    vm.getReplyDefault();
    $("#replyTable").BT({
        url: baseURL + 'wechat/wxuser/replyList',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '关键词', field: 'reqKey'},
            {title: '回复内容', field: 'repContent'},
        ],
        //条件查询
        queryParams: {
            sidx: 'create_date',
            order: 'desc',
            appId: vm.wxApp.id
        }
    })
    // vm.queryUser(id);

}


var vm = new Vue({
    el: '#rrapp',
    data: {
        thumbnail: [],
        appId: '',
        showRightFlag: false,//右边配置显示默认详情还是配置详情
        menu: {
            button: []
        },//横向菜单
        isActive: -1,// 一级菜单点中样式
        isSubMenuActive: -1,// 一级菜单点中样式
        isSubMenuFlag: -1,// 二级菜单显示标志
        tempObj: {},//右边临时变量，作为中间值牵引关系
        tempSelfObj: {
            //一些临时值放在这里进行判断，如果放在tempObj，由于引用关系，menu也会多了多余的参数
        },
        //回复标签菜单
        replyActive: 'default',
        replyEditActive:'list',
        //ReplyDefault默认回复集合
        replyDefault: {},
        reply:{},
        visible2: false,//素材内容  "选择素材"按钮弹框显示隐藏
        tableData: [], //素材内容弹框数据,
        menuName: '',
        showConfigurContent: true,
        nameMaxlength: 0,//名称最大长度
        menuOptions: [{
            value: 'view',
            label: '跳转网页'
        }/*, {
            value: 'miniprogram',
            label: '跳转小程序'
        }, {
            value: 'click',
            label: '点击回复'
        }, {
            value: 'view_limited',
            label: '跳转图文消息'
        }, {
            value: 'scancode_push',
            label: '扫码直接返回结果'
        }, {
            value: 'scancode_waitmsg',
            label: '扫码回复'
        }*/
            , {
                value: 'view_limited',
                label: '跳转图文消息'
            }, {
                value: 'pic_sysphoto',
                label: '系统拍照发图'
            }, {
                value: 'pic_photo_or_album',
                label: '拍照或者相册'
            }, {
                value: 'pic_weixin',
                label: '微信相册'
            }, {
                value: 'location_select',
                label: '选择地理位置'
            }],
        dialogNewsVisible: false,
        saveLoading: false,
        hackResetWxReplySelect: false,

        params: {
            name: '',  // 公众号名称
            nickName: '' // 微信用户昵称
        },
        showList: 'list',
        title: null,
        wxApp: {},
        checkFile: [],
        //验证字段
        fields: {
            name: {
                message: '微信号名称验证失败',
                validators: {
                    notEmpty: {
                        message: '微信号名称不能为空'
                    },
                },
            },
            expDate: {
                message: '有效时间验证失败',
                validators: {
                    notEmpty: {
                        message: '有效时间不能为空'
                    },
                },
            },
            weixinSign: {
                message: '微信原始标识验证失败',
                validators: {
                    notEmpty: {
                        message: '微信原始标识不能为空'
                    },
                },
            }, id: {
                message: 'AppId验证失败',
                validators: {
                    notEmpty: {
                        message: 'AppId不能为空'
                    },
                },
            }, secret: {
                message: '应用密钥验证失败',
                validators: {
                    notEmpty: {
                        message: '应用密钥不能为空'
                    },
                },
            }, token: {
                message: 'token验证失败',
                validators: {
                    notEmpty: {
                        message: 'token不能为空'
                    },
                },
            }
        }
    },
    methods: {
        //关注回复和默认回复
        getReplyDefault: function () {
            var _this = this;
            $.ajax({
                type: "POST",
                url: baseURL + 'wechat/wxuser/getReplyDefault',
                contentType: "application/json",
                data: JSON.stringify({appId: vm.wxApp.id}),
                success: function (r) {
                    if (r.code === 0) {
                        if (r.data != null) {
                            _this.replyDefault = r.data;
                        }
                    } else {
                        alert(r.msg);
                    }
                },
                error: function (e) {
                    alert(e);
                }
            });
        },
        //修改关注回复和默认回复
        updateReplyDefault: function () {
            var _this = this;
            this.replyDefault.appId = vm.wxApp.id;
            $.ajax({
                type: "POST",
                url: baseURL + 'wechat/wxuser/updateReplyDefault',
                contentType: "application/json",
                data: JSON.stringify(_this.replyDefault),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            _this.replyDefault = r.data;
                        });
                    } else {
                        alert(r.msg);
                    }
                },
                error: function (e) {
                    alert(e);
                }
            });
        },
        addMessage: function () {
            vm.replyEditActive="add"
        },
        saveMessage:function () {
            var _this = this;
            this.reply.appId = vm.wxApp.id;
            $.ajax({
                type: "POST",
                url: baseURL + 'wechat/wxuser/addReply',
                contentType: "application/json",
                data: JSON.stringify(_this.reply),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            _this.reloadMessage();
                        });
                    } else {
                        alert(r.msg);
                    }
                },
                error: function (e) {
                    alert(e);
                }
            });
        },
        resultMessage:function () {
            vm.replyEditActive="list"
        },
        reloadMessage:function () {
            vm.replyEditActive="list"
            $("#replyTable").BTF5({appId:vm.wxApp.id});
        },
        updateMessage: function () {
            var grid = $('#replyTable').bootstrapTable('getSelections');
            console.log(grid);
            if(!grid.length){
                alert("请选择一条记录");
                return ;
            }

            if(grid.length > 1){
                alert("只能选择一条记录");
                return ;
            }
            $.ajax({
                type: "POST",
                url: baseURL + 'wechat/wxuser/getReplyInfo/'+grid[0].id,
                contentType: "application/json",
                success: function (r) {
                    if (r.code === 0) {
                      vm.reply=r.data;
                      vm.replyEditActive='add';
                    } else {
                        alert(r.msg);
                    }
                },
                error: function (e) {
                    alert(e);
                }
            });
        },
        delMessage: function () {
            var grid = $('#replyTable').bootstrapTable('getSelections');
            console.log(grid);
            if(!grid.length){
                alert("请选择一条或多条记录");
                return ;
            }
            var ids=grid.map((data)=>data.id);
            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type:'post',
                    url: baseURL + "wechat/wxuser/deleteReply",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#replyTable").BTF5({appId: vm.wxApp.id});
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            })
        },

        // 同步微信用户
        synchron: function () {

            layer.confirm('同步用户需要一定时间，用户量越大、用时越久，请耐心等待，勿重复提交；确认此操作吗?', function () {
                layer.closeAll();
                layer.load(0, {shade: 0.3});
                $.ajax({
                    type: "POST",
                    url: baseURL + 'wechat/wxuser/synchron',
                    contentType: "application/json",
                    data: JSON.stringify({appId: vm.wxApp.id}),
                    success: function (r) {
                        layer.closeAll();
                        if (r.code === 0) {
                            alert('操作成功', function (index) {
                                vm.refreshUser();
                            });
                        } else {
                            alert(r.msg);
                        }
                    },
                    error: function (e) {
                        layer.closeAll();
                        alert(e);
                    }
                });
            })
        },
        querySuCai() {
            let _that = this;

            $.get(baseURL + 'wechat/wxmaterial/list', {appId: vm.wxApp.id, type: 'news'}, function (r) {
                _that.thumbnail = r.material.items;
            });
        },
        refreshMenu: function () {
            $('#menuTable').bootstrapTable('refresh');
        },
        // 查询公众号菜单
        queryMenu: function () {
            this.getMenuFun();
        },
        // 刷新微信用户
        refreshUser: function () {
            let temp = {
                appId: vm.wxApp.id,
                nickName: vm.params.nickName,
                sidx: 'subscribe_time',
                order: 'desc'
            };

            $("#userTable").BTF5(temp);
        },
        // 查询微信用户
        queryUser: function () {

            $("#userTable").BT({
                url: baseURL + 'wechat/wxuser/list',
                columns: [
                    {checkbox: true, width: '60px'},
                    {
                        title: '头像', field: 'headimgUrl', formatter: function (value, row, index) {
                        return '<img width="50px" height="50px" src=' + value + '>';
                    }
                    },
                    {title: '昵称', field: 'nickName', width: '8%'},
                    {
                        title: '是否订阅', field: 'subscribe', width: '2%', formatter: function (value, row, index) {
                        if (value == '0') {
                            return '<span class="label label-success">已关注</span>'
                        } else if (value == '1') {
                            return '<span class="label label-default">取消关注</span>'
                        }

                    }
                    },
                    {title: '关注时间', field: 'subscribeTime'},
                    {
                        title: '性别', field: 'sex', width: '2%', formatter: function (value, row, index) {
                        if (value == '1') {
                            return '男';
                        } else if (value == '0') {
                            return '女';
                        } else {
                            return '未知';
                        }
                    }
                    },
                    {title: '所在国家', field: 'country'},
                    {title: '所在省份', field: 'province'},
                    {title: '所在城市', field: 'city'},
                    {title: '用户语言', field: 'language'},
                    // {title: '关注次数', field: 'subscribeNum'},
                    {title: '备注', field: 'remark', width: '10%'}


                ],
                //条件查询
                queryParams: {
                    appId: vm.wxApp.id,
                    sidx: 'subscribe_time',
                    order: 'desc'
                }
            });
        },
        query: function () {
            let temp = {
                name: vm.params.name,
                sidx: 'create_date',
                order: 'desc'
            };
            $("#table").BTF5(temp);
        },
        add: function () {
            vm.showList = 'update';
            vm.title = "新增";
            vm.wxApp = {headLogo: ''};
            vm.checkFile = [];
        },
        update: function (event) {
            var id = getSelectedRowId("id");
            if (id == null) {
                return;
            }
            vm.showList = 'update';
            vm.title = "修改";

            vm.getInfo(id)
        },
        showUe: function (id, description) {
            vm.wxApp.id = id;
            ue.setContent($.isEmptyObject(description) ? '' : description);
            vm.showList = 'qualified';
        },
        updateQualified: function () {
            if (ue.getContentLength(true) > 500) {
                alert("门店资质最多输入500个字符");
                return;
            }

            vm.wxApp.description = ue.getContent();
            $.ajax({
                type: "POST",
                url: baseURL + 'wechat/wxapp/editQualified',
                contentType: "application/json",
                data: JSON.stringify(vm.wxApp),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        enabled: function () {
            var ids = getSelectedRowsId("id");
            if (ids == null) {
                return;
            }
            confirm('确定要暂停/启用 选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "wechat/wxapp/enabled",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        qualified: function () {
            var ids = getSelectedRowsId("id");
            if (ids == null) {
                return;
            }
            confirm('确定要开启/关闭 选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "wechat/wxapp/qualified",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        //表单验证
        validate: function () {
            var bl = $('form').VF();//启用验证
            if (!bl) {
                return;
            }
        },
        createQrCode: function (val) {

            var url = "wechat/wxapp/qrCode";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify({id: 'wxb3d2e46d6e63123f', sceneStr: '1'}),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.title == '新增' ? "wechat/wxapp/save" : "wechat/wxapp/update";

            if (vm.wxApp.headLogo == '' || vm.wxApp.headLogo == undefined) {
                alert('请上传头像.');
                return;
            }

            if (vm.checkFile == null || vm.checkFile.length == 0) {
                alert("请上传校验文件");
                return;
            } else if (vm.checkFile.length > 10) {
                alert("校验文件不得超过10份");
                return;
            }
            vm.wxApp.checkFile = vm.checkFile;

            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.wxApp),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var ids = getSelectedRowsId("id");
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "DELETE",
                    url: baseURL + "wechat/wxapp/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (id) {
            vm.checkFile = [];
            $.get(baseURL + "wechat/wxapp/info/" + id, function (r) {
                vm.wxApp = r.wxApp;
                vm.checkFile = r.checkFile;
            });
        },
        reload: function (event) {
            vm.showList = 'list';
            vm.title = "";
            vm.params = {name: '', nickName: ''};
            $("#userTable").bootstrapTable('destroy');
            $("#menuTable").bootstrapTable('destroy');
            //刷新 如需条件查询common.js
            let temp = {
                sidx: 'create_date',
                order: 'desc'
            };
            $("#table").BTF5(temp);
            $("form").RF();
        },
        deleteTempObj() {
            this.$delete(this.tempObj, 'repName');
            this.$delete(this.tempObj, 'repUrl');
            this.$delete(this.tempObj, 'content');
        },
        openMaterial() {
            this.querySuCai();
            this.dialogNewsVisible = true;
        },
        selectMaterial(item) {
            if (item.content.articles.length > 1) {
                this.$alert('您选择的是多图文，将默认跳转第一篇', '提示', {
                    confirmButtonText: '确定'
                })
            }
            this.dialogNewsVisible = false;
            this.tempObj.media_id = item.mediaId;
            this.tempObj.mediaName = item.name;
            this.tempObj.url = item.url;
            item.mediaType = this.tempObj.mediaType;
            item.content.articles = item.content.articles.slice(0, 1);
            this.tempObj.content = item.content;
        },
        breaks() {
            this.$router.$avueRouter.closeTag();
            this.$router.go(-1);
        },
        getMenuFun() {

            $.get(baseURL + "wechat/wxmenu/list/", {appId: this.wxApp.id}, function (r) {
                vm.menu.button = JSON.parse(r.msg).button;
            });

        },
        handleClick(tab, event) {
            this.tempObj.mediaType = tab.name
        },
        saveFun() {
            $.ajax({
                type: "POST",
                url: baseURL + 'wechat/wxmenu/save',
                contentType: "application/json",
                data: JSON.stringify({
                    strWxMenu: this.menu,
                    appId: this.wxApp.id
                }),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        // 一级菜单点击事件
        menuFun(i, item) {
            this.hackResetWxReplySelect = false;//销毁组件
            this.$nextTick(function () {
                this.hackResetWxReplySelect = true//重建组件
            });
            this.showRightFlag = true;//右边菜单
            this.tempObj = item;//这个如果放在顶部，flag会没有。因为重新赋值了。
            this.tempSelfObj.grand = "1";//表示一级菜单
            this.tempSelfObj.index = i;//表示一级菜单索引
            this.isActive = i; //一级菜单选中样式
            this.isSubMenuFlag = i; //二级菜单显示标志
            this.isSubMenuActive = -1; //二级菜单去除选中样式
            this.nameMaxlength = 10;
            if (item.sub_button && item.sub_button.length > 0) {
                this.showConfigurContent = false
            } else {
                this.showConfigurContent = true
            }
        },
        // 二级菜单点击事件
        subMenuFun(subItem, index, k) {
            this.hackResetWxReplySelect = false;//销毁组件
            this.$nextTick(function () {
                this.hackResetWxReplySelect = true//重建组件
            });
            this.showRightFlag = true;//右边菜单
            this.tempObj = subItem;//将点击的数据放到临时变量，对象有引用作用
            this.tempSelfObj.grand = "2";//表示二级菜单
            this.tempSelfObj.index = index;//表示一级菜单索引
            this.tempSelfObj.secondIndex = k;//表示二级菜单索引
            this.isSubMenuActive = index + "" + k; //二级菜单选中样式
            this.isActive = -1;//一级菜单去除样式
            this.showConfigurContent = true;
            this.nameMaxlength = 7
        },
        // 添加横向一级菜单
        addMenu() {
            let menuKeyLength = this.menuKeyLength;
            let addButton = {
                name: "菜单名称",
                sub_button: []
            }
            this.$set(this.menu.button, menuKeyLength, addButton);
            this.menuFun(this.menuKeyLength - 1, addButton)
        },
        // 添加横向二级菜单
        addSubMenu(i, item) {
            if (!item.sub_button || item.sub_button.length <= 0) {
                this.$set(item, 'sub_button', []);
                this.$delete(item, 'type');
                this.$delete(item, 'appid');
                this.$delete(item, 'pagepath');
                this.$delete(item, 'url');
                this.$delete(item, 'key');
                this.$delete(item, 'media_id');
                this.$delete(item, 'textContent');
                this.showConfigurContent = false;
            }
            let subMenuKeyLength = item.sub_button.length;//获取二级菜单key长度
            let addButton = {
                name: "子菜单名称"
            }
            this.$set(item.sub_button, subMenuKeyLength, addButton);
            this.subMenuFun(item.sub_button[subMenuKeyLength], i, subMenuKeyLength)
        },
        //删除当前菜单
        deleteMenu(obj) {
            let _this = this;

            confirm('此操作将永久删除该文件, 是否继续?', function () {
                _this.deleteData();// 删除菜单数据
                _this.tempObj = {};
                _this.showRightFlag = false;
                this.isActive = -1;
                this.isSubMenuActive = -1;
            });
        },
        // 删除菜单数据
        deleteData() {
            // 一级菜单的删除方法
            if (this.tempSelfObj.grand == "1") {
                this.menu.button.splice(this.tempSelfObj.index, 1);
            }
            // 二级菜单的删除方法
            if (this.tempSelfObj.grand == "2") {
                this.menu.button[this.tempSelfObj.index].sub_button.splice(this.tempSelfObj.secondIndex, 1);
            }

            alert('删除成功!')
        }
    },
    computed: {
        // menuObj的长度，当长度 小于  3 才显示一级菜单的加号
        menuKeyLength: function () {
            return this.menu.button.length;
        }
    }

});