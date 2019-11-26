$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'store/tstorewxuser/list?sidx=subscribe_time&order=desc',
        columns: [
            {checkbox: true, width: '60px'},
            {title: 'openid', field: 'id', visible: false},
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
            // {title: '返费余额', field: 'country'},
            {title: '所在国家', field: 'country'},
            {title: '所在省份', field: 'province'},
            {title: '所在城市', field: 'city'},
            {title: '用户语言', field: 'language'},
            {title: '手机号码', field: 'phone'},
            {title: '积分', field: 'integral'},
            {
                title: '奖励金', field: 'bonus', formatter: function (value, row, index) {
                    if (isBlank(value)) {
                        return "0";
                    } else {
                        return value;
                    }
                }
            },
            /*{title: '备注', field: 'remark', width: '10%'},*/
            {
                title: '门店状态', field: 'appstate',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-success">有效</span>'
                    } else if (value == 0) {
                        return '<span class="label label-default">无效</span>'
                    }
                }

            },
            {
                title: '操作',
                width: '15%',
                formatter: function (value, row, index) {

                    let mes = '';
                    /* if (hasPermission('store:tstorewxuser:list')) {*/
                    mes += '<button type="button" class="btn btn-success btn-sm" id="btn_signList">签到记录</button>&nbsp;';
                    /*}*/
                    /*if (hasPermission('store:tstorewxuser:list')) {*/
                    mes += '<button type="button" class="btn btn-primary btn-sm" id="btn_integralList">积分记录</button>&nbsp;';
                    /*}*/
                    if (hasPermission('store:tstorewxuser:editIntegral') && row.appstate != null && row.appstate == 1) {
                        mes += '<button type="button" class="btn btn-danger btn-sm" id="btn_editIntegral">积分修改</button>&nbsp;';
                    }

                    mes += '<button type="button" class="btn btn-info btn-sm" id="btn_bonusList">奖励金记录</button>&nbsp;';

                    mes += '<button type="button" class="btn btn-warning btn-sm" id="btn_bonusTX">奖励金提现</button>&nbsp;';

                    mes += '<button type="button" class="btn btn-adn btn-sm" id="btn_prizeList">积分抽奖</button>&nbsp;';

                    //onclick="withdraw(' + row.id + ',' + row.bonus + ')"
                    return mes;

                },
                events: operateEvent
            }
        ],
        //条件查询
        queryParams: {}
    });
    //表单提交
    $("form").FM({
        fields: vm.fields,
        success: vm.saveOrUpdate

    })


    //列表
    $("#table2").BT({
        url: baseURL + 'store/tstoresigninrecord/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '用户姓名', field: 'wname'},
            {title: '签到时间', field: 'createTime'},
            {title: '备注', field: 'remarks'},
            {title: '连续签到天数', field: 'countDate'}
        ],
        /*//条件查询
        queryParams:{}*/
    });
    /*//表单提交
    $("form").FM({
        fields : vm.fields,
        success : vm.saveOrUpdate

    })*/


    //列表
    $("#table3").BT({
        url: baseURL + 'store/tstoreintegralrecord/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '用户姓名', field: 'wname'},
            {
                title: '积分类型', field: 'integralType',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-success">签到</span>';
                    } else if (value == 2) {
                        return '<span class="label label-primary">新手任务</span>';
                    } else if (value == 3) {
                        return '<span class="label label-danger">积分修改</span>';
                    } else if (value == 4) {
                        return '<span class="label label-warning">积分抽奖</span>';
                    }
                }
            },
            {title: '积分', field: 'integral'},
            {title: '备注', field: 'remarks'},

            {title: '创建时间', field: 'createTime'}


        ],
        /*//条件查询
        queryParams:{}*/
    });

    //奖励金记录列表
    $("#table4").BT({
        url: baseURL + 'jobApplication/bonusrecord/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '用户姓名', field: 'wname'},
            {
                title: '类型', field: 'type',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-success">推荐奖励</span>';
                    } else if (value == 2) {
                        return '<span class="label label-warning">提现</span>';
                    }
                }
            },
            {title: '金额', field: 'amount'},
            {title: '备注', field: 'remark'},
            {title: '创建时间', field: 'createDate'}


        ],
        /*//条件查询
        queryParams:{}*/
    });

    //积分中奖记录列表
    $("#table5").BT({
        url: baseURL + 'draw/integraldrawrecord/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '用户姓名', field: 'wname'},
            {title: '消耗积分', field: 'consumeIntegral'},
            {title: '奖品', field: 'prizeName'},
            {
                title: '奖项', field: 'prizeSort', formatter: function (value, row, index) {
                    if (value == 1) {
                        return "一等奖";
                    } else if (value == 2) {
                        return "二等奖";
                    } else if (value == 3) {
                        return "三等奖";
                    } else if (value == 4) {
                        return "四等奖";
                    } else if (value == 5) {
                        return "五等奖";
                    } else if (value == 6) {
                        return "六等奖";
                    } else if (value == 7) {
                        return "七等奖";
                    } else if (value == 8) {
                        return "八等奖";
                    }
                }
            },
            {title: '中奖时间', field: 'createTime'},
            {
                title: '状态', field: 'state',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-success">未兑奖</span>';
                    } else if (value == 2) {
                        return '<span class="label label-warning">已兑奖</span>';
                    }
                }
            },
            {title: '兑奖时间', field: 'exchangeTime'}

        ],
        /*//条件查询
        queryParams:{}*/
    });
});


//操作方法
window.operateEvent = {

    'click #btn_signList': function (e, value, row, index) {
        vm.title = "签到记录";
        vm.showList = false;
        vm.params2.userId = row.id;
        vm.reloadTwo();
    },
    'click #btn_integralList': function (e, value, row, index) {
        vm.title = "积分记录";
        vm.showList = false;
        vm.params3.userId = row.id;
        vm.reloadThree();
    },
    'click #btn_editIntegral': function (e, value, row, index) {
        vm.title = "积分修改";
        editIntegral(row.id);
    },
    //奖励金提现
    'click #btn_bonusTX': function (e, value, row, index) {
        withdraw(row.id, row.bonus);
    },
    //奖励金记录
    'click #btn_bonusList': function (e, value, row, index) {
        vm.title = "奖励金记录";
        vm.showList = false;
        vm.params4.openid = row.id;
        vm.reloadFour();
    },
    //积分抽奖 奖品记录
    'click #btn_prizeList': function (e, value, row, index) {
        vm.title = "积分中奖记录";
        vm.showList = false;
        vm.params5.openid = row.id;
        vm.reloadFive();
    }

};

//奖励金提现
function withdraw(id, bonus) {
    layer.open({
        title: '提现',
        type: 1,
        area: ['420px', '160px'], //宽高
        content: '<div><input id="amount" type="text" oninput="inputnum2(this)" class="form-control" placeholder="请输入提现金额,可保留2位小数"/></div>',
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            if (isBlank(bonus)) {
                bonus = 0;
            }
            var amount = $("#amount").val();
            if (amount == null || amount == '') {
                alert("提现金额不能为空!");
                return;
            } else if (amount <= 0) {
                alert("提现金额需大于0!");
                return;
            } else if (amount > bonus) {
                alert("提现金额不能大于奖励金!");
                return;
            }
            var url = "jobApplication/bonusrecord/withdraw";//奖励金提现
            $.ajax({
                type: "GET",
                url: baseURL + url,
                data: {
                    openid: id,
                    amount: amount
                },
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            layer.closeAll();
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        }
    });
};

//input输入框只能输入数字和 小数点后两位
function inputnum2(obj, val) {
    obj.value = obj.value.replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
    obj.value = obj.value.replace(/^\./g, ""); //验证第一个字符是数字
    obj.value = obj.value.replace(/\.{2,}/g, ""); //只保留第一个, 清除多余的
    obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
}

//修改个人总积分
function editIntegral(id) {
    layer.open({
        title: '修改个人总积分',
        type: 1,
        area: ['220px', '160px'], //宽高
        content: '<div class="form-group"><input id="integral"  type="text" oninput="inputnum(this)" class="form-control" placeholder="请输入想要修改的个人积分" /></div>',
        btn: ['确定', '取消'],
        yes: function (index, layero) {

            var integral = $("#integral").val();
            if (integral == null || integral == '') {
                alert("不能为空!")
                return;
            }
            var url = "/store/tstorewxuser/editIntegral";//修改个人总积分
            $.get(baseURL + "store/tstorewxuser/editIntegral", {"id": id, "integral": integral}, function (r) {
                if (r.code === 0) {
                    alert('操作成功', function () {
                        layer.closeAll();
                        vm.reload();
                    });
                } else {
                    alert(r.msg);
                }
            });
        }
    });
};


function inputnum(obj) {
    var a = obj.value;
    obj.value = obj.value.replace(/[^\d]/g, ""); //清除"数字"和"."以外的字符

    if (a.length > 1) {
        obj.value = obj.value.replace(/\b(0+)/gi, "");
    }

}


var vm = new Vue({
    el: '#rrapp',
    data: {
        user_Store: userStore,
        showList: true,
        title: null,
        wxUser: {},
        showList2: false,//签到记录
        showList3: false,//积分记录
        showList4: false,//奖励金记录
        showList5: false,//积分中奖记录
        params: {
            appId: '',
            sidx: 'subscribe_time',
            order: 'desc'
        },
        params2: {},
        params3: {
            integralType: ''
        },
        params4: {
            type: ''
        },
        params5: {
            type: 1,//只查已中奖
            state: ''
        },
        appIdList: [],
        integralTypeList: [
            {'label': '签到', 'value': 1},
            {'label': '新手任务', 'value': 2},
            {'label': '积分修改', 'value': 3},
            {'label': '积分抽奖', 'value': 4}
        ],


        //验证字段
        fields: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        query2: function () {

            vm.params2.createTime = $("#createTime").val();

            vm.reloadTwo();
        },
        query3: function () {
            vm.params3.createTime = $("#createTime2").val();
            vm.reloadThree();
        },
        query4: function () {
            vm.params4.createTime = $("#createTime3").val();
            vm.reloadFour();
        },
        query5: function () {
            vm.params5.createTime = $("#createTime4").val();
            vm.reloadFive();
        },
        add: function (id) {
            vm.title = "新增";
            vm.clockIn = {
                memberId: id,
                type: 1
            };
        },
        update: function (event) {
            var id = getSelectedRowId("id");
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        //表单验证
        validate: function () {
            var bl = $('form').VF();//启用验证
            if (!bl) {
                return;
            }
        },
        saveOrUpdate: function (event) {
            var url = vm.wxUser.id == null ? "store/wxuser/save" : "store/wxuser/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.wxUser),
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
                    type: "POST",
                    url: baseURL + "store/wxuser/delete",
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
            $.get(baseURL + "store/wxuser/info/" + id, function (r) {
                vm.wxUser = r.wxUser;
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.showList2 = false;
            vm.showList3 = false;
            vm.showList4 = false;
            vm.showList5 = false;
            vm.title = "";
            //刷新 如需条件查询common.js
            $("#table").BTF5(vm.params);
        },
        //返回一级菜单
        showTableList: function () {
            vm.showList = true;
            vm.showList2 = false;
            vm.showList3 = false;
            vm.showList4 = false;
            vm.showList5 = false;
            vm.title = "";
            vm.params = {
                appId: '',
                sidx: 'subscribe_time',
                order: 'desc'
            };
            vm.params2 = {};
            vm.params3 = {
                integralType: ''
            };
            vm.params4 = {
                type: ''
            };
            vm.params5 = {
                type: 1,
                state: ''
            }
        },
        //签到记录列表刷新
        reloadTwo: function (event) {
            vm.title = "签到记录";
            vm.showList2 = true;
            vm.showList3 = false;
            vm.showList4 = false;
            vm.showList5 = false;
            $("#table2").BTF5(vm.params2);
            /* $("#formTwo").RF();*/
        },
        //积分记录列表刷新
        reloadThree: function (event) {
            vm.title = "积分记录";
            vm.showList2 = false;
            vm.showList3 = true;
            vm.showList4 = false;
            vm.showList5 = false;
            $("#table3").BTF5(vm.params3);
            /* $("#formThree").RF();*/
        },
        //奖励金记录列表刷新
        reloadFour: function (event) {
            vm.title = "奖励金记录";
            vm.showList2 = false;
            vm.showList3 = false;
            vm.showList4 = true;
            vm.showList5 = false;
            $("#table4").BTF5(vm.params4);
        },
        //我的奖品记录列表刷新
        reloadFive: function (event) {
            vm.title = "积分中奖记录";
            vm.showList2 = false;
            vm.showList3 = false;
            vm.showList4 = false;
            vm.showList5 = true;
            $("#table5").BTF5(vm.params5);
        },
        //积分抽奖 奖品兑换
        exchange: function (event) {
            /* var ids = getSelectedRowsId("id");
             if (ids == null) {
                 return;
             }*/
            var grid = $('#table5').bootstrapTable('getSelections');
            if (!grid.length) {
                alert("请选择一条记录");
                return;
            }
            var ids = [];
            $.each(grid, function (idx, item) {
                ids[idx] = item["id".toString()];
            });

            confirm('确定要兑换选中的奖品？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "draw/integraldrawrecord/exchange",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                vm.reloadFive();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
    },
    created: function () {

    }
});