$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'qrcode/wxappqrcode/appList',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '公众号名称', field: 'appname'},
            {title: '二维码个数', field: 'count'},
            {
                title: '操作', field: 'appId', formatter: function (value, row, index) {
                    var ss = '';
                    /* if (!isBlank(row.count) && row.count>0) {*/
                    ss += '<button type="button" class="btn btn-success btn-sm" id="codeList">二维码记录</button>';
                    /*  }*/
                    return ss;
                }, events: vm.events
            }
        ],
        //条件查询
        queryParams: {}
    });
    //二维码记录
    $("#table2").BT({
        url: baseURL + 'qrcode/wxappqrcode/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '公众号名称', field: 'appname'},
            {title: '编号', field: 'sceneId'},
            {title: '业务员姓名', field: 'name'},
            {title: '业务员电话', field: 'phone'},
            {title: '创建时间', field: 'createTime'},
            {
                title: '操作', field: 'id', formatter: function (value, row, index) {
                    var ss = '<button type="button" class="btn btn-primary btn-sm" id="codeDetail">详情</button>&nbsp;';
                    ss += '<button type="button" class="btn btn-adn btn-sm" id="wxUserList">推广记录</button>';
                    return ss;
                }, events: vm.events
            }
        ],
        //条件查询
        // queryParams: {}
    });
    //吸粉记录
    $("#table3").BT({
        url: baseURL + 'store/tstorewxuser/list?sidx=subscribe_time&order=desc',
        columns: [
            {checkbox: true, width: '60px'},
            {
                title: '头像', field: 'headimgUrl', formatter: function (value, row, index) {
                    return '<img width="50px" height="50px" src=' + value + '>';
                }
            },
            {title: '门店', field: 'wname'},
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
            {title: '手机号码', field: 'phone'},
            {
                title: '新手任务', field: 'newTask', width: '2%', formatter: function (value, row, index) {
                    if (value == 0) {
                        return '<span class="label label-default">未完成</span>'
                    } else if (value == 1) {
                        return '<span class="label label-success">已完成</span>'
                    }

                }
            },
            {title: '积分', field: 'integral'},
            {
                title: '操作', field: 'id', width: '2%', formatter: function (value, row, index) {
                    return '<button type="button" class="btn btn-info btn-sm" id="jobList">报名记录</button>';
                }, events: vm.events
            }
        ],
        //条件查询
        // queryParams: {}
    });
    //报名记录
    $("#table4").BT({
        url: baseURL + 'jobApplication/jobapplication/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '微信昵称', field: 'nickName'},
            {title: '姓名', field: 'realName'},
            {title: '手机号', field: 'phone'},
            {
                title: '报名类型', field: 'type', formatter: function (value, row, index) {
                    if (value == 1) {
                        return "主动报名";
                    } else if (value == 2) {
                        return "招聘报名";
                    }
                }
            },
            {
                title: '状态',
                field: 'stateFeedback',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return "已报名";
                    } else if (value == 2) {
                        return "已面试";
                    } else if (value == 3) {
                        return "未录用";
                    } else if (value == 4) {
                        return "已入职";
                    } else if (value == 5) {
                        return "已离职";
                    } else if (value == 6) {
                        return "其他";
                    }
                }
            },
            {title: '报名时间', field: 'createDate'},
            /* {
                 title: '操作', field: 'id', formatter: function (value, row, index) {
                     var str = '';
                     str += "<a href='javascript:void(0)' class='showInfo'>" + "详情" + "</a>";
                     str += '&nbsp;<a href="javascript:void(0)" class="feedback">' + "反馈" + "</a>";
                     if (!isBlank(row.type) && row.type == 2) {  //招聘报名
                         str += '&nbsp;<a href="javascript:void(0)" class="zpDetail">' + "招聘详情" + "</a>";

                     }
                     return str;
                 }, events: vm.events
             }*/
        ],
        //条件查询
        // queryParams: {}
    });

    //表单提交
    $("form").FM({
        fields: vm.fields,
        success: vm.saveOrUpdate

    })

});


var vm = new Vue({
    el: '#rrapp',
    data: {
        user_Store: userStore,
        title: null,
        showList: true,
        showList2: false,//二维码记录
        showList3: false,//吸粉记录
        showList4: false,//报名记录
        codeDetailPage: false,//二维码详情
        wxAppQrCode: {},
        categoryList: [],   //公众号
        queryParams: {
            appId: ''
        },
        params2: {},
        params3: {},
        params4: {
            type: '',
            stateFeedback: ''
        },
        events: {
            //二维码记录
            'click #codeList': function (event, value, row, index) {
                vm.params2.appId = row.appId;
                vm.reloadTwo()
            },
            //二维码详情
            'click #codeDetail': function (event, value, row, index) {
                $.ajaxSettings.async = false;
                vm.getInfo(row.id);
                $.ajaxSettings.async = true;
                vm.title = "详情";
                vm.showList = false;
                vm.showList2 = false;
                vm.showList3 = false;
                vm.showList4 = false;
                vm.codeDetailPage = true;
            },
            //吸粉记录
            'click #wxUserList': function (event, value, row, index) {
                vm.params3.appId = row.appId;
                vm.params3.sceneId = row.sceneId;
                vm.reloadThree()
            },
            //报名记录                                                                                                                                                                                                                                                        p-
            'click #jobList': function (e, value, row, index) {
                vm.params4.openid = row.id;
                vm.reloadFour()
            },


        },
        //验证字段
        fields: {
            name: {
                validators: {
                    /* notEmpty: {
                         message: '推广大使不能为空'
                     }, */stringLength: {
                        max: 20,
                        message: '推广大使的长度在20字符以内'
                    }
                }
            }, phone: {
                validators: {
                    /* notEmpty: {
                         message: '手机号不能为空'
                     },*/ regexp: {
                        regexp: /^1[345678]\d{9}$/,
                        message: '手机号不正确'
                    }
                }
            }, remarks: {
                validators: {
                    /* notEmpty: {
                         message: '备注不能为空'
                     }, */stringLength: {
                        max: 200,
                        message: '备注的长度在200字符以内'
                    }
                }
            }, appId: {
                validators: {
                    notEmpty: {
                        message: '公众号不能为空'
                    }
                }
            }
        }
    },
    methods: {
        //申请二维码
        apply: function () {
            alert("请拨打平台热线：400-000-3060");
            return;
        },
        //生成二维码
        generate: function () {
            vm.wxAppQrCode = {
                appId: vm.params2.appId
            };
            confirm('确定要为该门店生成推广二维码吗？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "qrcode/wxappqrcode/generate",
                    contentType: "application/json",
                    data: JSON.stringify(vm.wxAppQrCode),
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function (index) {
                                vm.reloadTwo();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });

        },
        query: function () {
            vm.reload();
        },
        query2: function () {
            vm.reloadTwo();
        },
        query3: function () {
            vm.params3.createTime = $("#createTime").val();
            vm.reloadThree();
        },
        query4: function () {
            vm.reloadFour();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.integraldraw = {};
        },
        update: function (event) {
            var appId = getSelectedRowId("appId");
            if (appId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(appId)
        },
        //表单验证
        validate: function () {
            var bl = $('form').VF();//启用验证
            if (!bl) {
                return;
            }
        },
        saveOrUpdate: function (event) {

            var url = vm.wxAppQrCode.createTime == null ? "qrcode/wxappqrcode/save" : "qrcode/wxappqrcode/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.wxAppQrCode),
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
            /*var appIds = getSelectedRowsId("appId");
            if (appIds == null) {
                return;
            }*/
            var grid = $('#table2').bootstrapTable('getSelections');
            var ids = [];
            $.each(grid, function (idx, item) {
                ids[idx] = item["id".toString()];
            });

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "qrcode/wxappqrcode/delete",
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
            $.get(baseURL + "qrcode/wxappqrcode/info/" + id, function (r) {
                vm.wxAppQrCode = r.wxAppQrCode;
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.showList2 = false;
            vm.showList3 = false;
            vm.showList4 = false;
            vm.codeDetailPage = false;
            vm.title = "";
            //刷新 如需条件查询common.js
            $("#table").BTF5(vm.queryParams);
            // $("form").RF();
        },
        //二维码记录
        reloadTwo: function (event) {
            vm.title = "二维码记录";
            vm.showList = false;
            vm.showList2 = true;
            vm.showList3 = false;
            vm.showList4 = false;
            vm.codeDetailPage = false;
            $("#table2").BTF5(vm.params2);
        },
        //吸粉记录
        reloadThree: function (event) {
            vm.title = "推广记录";
            vm.showList = false;
            vm.showList2 = false;
            vm.showList3 = true;
            vm.showList4 = false;
            vm.codeDetailPage = false;
            $("#table3").BTF5(vm.params3);
        },
        reloadFour: function (event) {
            vm.title = "报名记录";
            vm.showList = false;
            vm.showList2 = false;
            vm.showList3 = false;
            vm.showList4 = true;
            vm.codeDetailPage = false;
            $("#table4").BTF5(vm.params4);
        },
        //返回一级列表
        showTableList: function (event) {
            vm.showList = true;
            vm.showList2 = false;
            vm.showList3 = false;
            vm.showList4 = false;
            vm.codeDetailPage = false;
            vm.title = "";
            vm.queryParams = {
                appId: ''
            };
            vm.params2 = {};
            vm.params3 = {};
            vm.params4 = {
                type: '',
                stateFeedback: ''
            };
            // $("form").RF();
            $("#table").BTF5(vm.queryParams);
            // $("#appIdParam").val("");//无效
            $('.selectpicker').selectpicker('val', '');
            $('.selectpicker').selectpicker('refresh');
        },

        //推广记录  下载
        exportExcel: function () {
            // var gids = getSelectedRowsIds("id");
            var grid = $('#table3').bootstrapTable('getSelections');
            var ids = [];
            $.each(grid, function (idx, item) {
                ids[idx] = item["id".toString()];
            });

            if (ids == null || ids.length == 0) {
                $.table.exportExcel('所有', '/store/tstorewxuser/export/0', '推广记录', vm.params3);
            } else if (ids != null && ids.length > 0) {
                vm.params3.ids = ids;
                $.table.exportExcel('选中', '/store/tstorewxuser/export/1', '推广记录', vm.params3);
            }

        },

        getCategory: function () {
            // 加载菜单树
            $.get(baseURL + "wechat/wxapp/selectlist", function (r) {
                vm.categoryList = r.wxAppList;
            })
        }
    },
    created: function () {
        this.getCategory();
    },
    watch: {}
});







