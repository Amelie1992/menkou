$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'draw/wxappintegraldraw/appList',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '公众号名称', field: 'appname'},
            {
                title: '状态', field: 'state', formatter: function (value, row, index) {
                    if (isBlank(value)) {
                        return "暂未开通";
                    } else if (value == 1) {
                        return "启用";
                    } else if (value == 2) {
                        return "禁用";
                    }
                }
            },
            {title: '开通时间', field: 'createTime'},
            {
                title: '操作', field: 'id', formatter: function (value, row, index) {
                    var ss = '';
                    if (!isBlank(row.state) && row.state == 1) {
                        ss = '<button type="button" class="btn btn-success btn-sm" id="customized">定制</button>';
                    }
                    return ss;
                }, events: vm.events
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

});


var vm = new Vue({
    el: '#rrapp',
    data: {
        user_Store: userStore,
        showList: true,
        title: null,
        integraldraw: {},
        prize1: {
            sort: 1
        },
        prize2: {
            sort: 2
        },
        prize3: {
            sort: 3
        },
        prize4: {
            sort: 4
        },
        prize5: {
            sort: 5
        },
        prize6: {
            sort: 6
        },
        prize7: {
            sort: 7
        },
        prize8: {
            prize: '谢谢参与',
            sort: 8
        },
        categoryList: [],   //公众号
        queryParams: {
            appId: '',
            state: ''
        },
        events: {
            //配置积分抽奖
            'click #customized': function (event, value, row, index) {
                $.ajaxSettings.async = false;
                vm.getInfo(row.appId);
                $.ajaxSettings.async = true;
                vm.integraldraw.appId = row.appId;
                vm.title = "定制";
                vm.showList = false;
            }
        },
        //验证字段
        fields: {
            consume: {
                validators: {
                    notEmpty: {
                        message: '单次抽奖消耗不能为空'
                    }, regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '单次抽奖消耗为小于6位的正整数'
                    }
                }
            }, prize1: {
                validators: {
                    notEmpty: {
                        message: '一等奖不能为空'
                    }, stringLength: {
                        max: 20,
                        message: '奖项名的长度在20字符以内'
                    }
                }
            }, prize2: {
                validators: {
                    notEmpty: {
                        message: '二等奖不能为空'
                    }, stringLength: {
                        max: 20,
                        message: '奖项名的长度在20字符以内'
                    }
                }
            }, prize3: {
                validators: {
                    notEmpty: {
                        message: '三等奖不能为空'
                    }, stringLength: {
                        max: 20,
                        message: '奖项名的长度在20字符以内'
                    }
                }
            }, prize4: {
                validators: {
                    notEmpty: {
                        message: '四等奖不能为空'
                    }, stringLength: {
                        max: 20,
                        message: '奖项名的长度在20字符以内'
                    }
                }
            }, prize5: {
                validators: {
                    notEmpty: {
                        message: '五等奖不能为空'
                    }, stringLength: {
                        max: 20,
                        message: '奖项名的长度在20字符以内'
                    }
                }
            }, prize6: {
                validators: {
                    notEmpty: {
                        message: '六等奖不能为空'
                    }, stringLength: {
                        max: 20,
                        message: '奖项名的长度在20字符以内'
                    }
                }
            }, prize7: {
                validators: {
                    notEmpty: {
                        message: '七等奖不能为空'
                    }, stringLength: {
                        max: 20,
                        message: '奖项名的长度在20字符以内'
                    }
                }
            }, prize8: {
                validators: {
                    notEmpty: {
                        message: '八等奖不能为空'
                    }, stringLength: {
                        max: 20,
                        message: '奖项名的长度在20字符以内'
                    }
                }
            }, probability1: {
                validators: {
                    notEmpty: {
                        message: '中奖概率不能为空'
                    }, regexp: {
                        regexp: /^(?:0|[1-9][0-9]?|100)$/,
                        message: '中奖概率为100以内的非负整数'
                    }
                }
            }, probability2: {
                validators: {
                    notEmpty: {
                        message: '中奖概率不能为空'
                    }, regexp: {
                        regexp: /^(?:0|[1-9][0-9]?|100)$/,
                        message: '中奖概率为100以内的非负整数'
                    }
                }
            }, probability3: {
                validators: {
                    notEmpty: {
                        message: '中奖概率不能为空'
                    }, regexp: {
                        regexp: /^(?:0|[1-9][0-9]?|100)$/,
                        message: '中奖概率为100以内的非负整数'
                    }
                }
            }, probability4: {
                validators: {
                    notEmpty: {
                        message: '中奖概率不能为空'
                    }, regexp: {
                        regexp: /^(?:0|[1-9][0-9]?|100)$/,
                        message: '中奖概率为100以内的非负整数'
                    }
                }
            }, probability5: {
                validators: {
                    notEmpty: {
                        message: '中奖概率不能为空'
                    }, regexp: {
                        regexp: /^(?:0|[1-9][0-9]?|100)$/,
                        message: '中奖概率为100以内的非负整数'
                    }
                }
            }, probability6: {
                validators: {
                    notEmpty: {
                        message: '中奖概率不能为空'
                    }, regexp: {
                        regexp: /^(?:0|[1-9][0-9]?|100)$/,
                        message: '中奖概率为100以内的非负整数'
                    }
                }
            }, probability7: {
                validators: {
                    notEmpty: {
                        message: '中奖概率不能为空'
                    }, regexp: {
                        regexp: /^(?:0|[1-9][0-9]?|100)$/,
                        message: '中奖概率为100以内的非负整数'
                    }
                }
            }, probability8: {
                validators: {
                    notEmpty: {
                        message: '中奖概率不能为空'
                    }, regexp: {
                        regexp: /^(?:0|[1-9][0-9]?|100)$/,
                        message: '中奖概率为100以内的非负整数'
                    }
                }
            }, stock1: {
                validators: {
                    notEmpty: {
                        message: '库存不能为空'
                    }, regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '库存为小于6位的正整数'
                    }
                }
            }, stock2: {
                validators: {
                    notEmpty: {
                        message: '库存不能为空'
                    }, regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '库存为小于6位的正整数'
                    }
                }
            }, stock3: {
                validators: {
                    notEmpty: {
                        message: '库存不能为空'
                    }, regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '库存为小于6位的正整数'
                    }
                }
            }, stock4: {
                validators: {
                    notEmpty: {
                        message: '库存不能为空'
                    }, regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '库存为小于6位的正整数'
                    }
                }
            }, stock5: {
                validators: {
                    notEmpty: {
                        message: '库存不能为空'
                    }, regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '库存为小于6位的正整数'
                    }
                }
            }, stock6: {
                validators: {
                    notEmpty: {
                        message: '库存不能为空'
                    }, regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '库存为小于6位的正整数'
                    }
                }
            }, stock7: {
                validators: {
                    notEmpty: {
                        message: '库存不能为空'
                    }, regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '库存为小于6位的正整数'
                    }
                }
            }, stock8: {
                validators: {
                    /* notEmpty: {
                         message: '库存不能为空'
                     },*/ regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '库存为小于6位的正整数'
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
        //申请开通
        apply: function () {
            alert("请拨打平台热线：400-000-3060");
            return;
        },
        //启用
        enable: function () {
            var grid = $('#table').bootstrapTable('getSelections');
            if (!grid.length) {
                alert("请选择一条记录");
                return;
            }

            var flag = true;
            $.each(grid, function (idx, item) {
                if (isBlank(item.state)) {
                    flag = false;
                    return;
                }
            });

            if (!flag) {
                alert("请选中开通积分抽奖功能的门店");
                return;
            }

            var appIds = getSelectedRowsId("appId");
            if (appIds == null) {
                return;
            }

            confirm('确定要启用选中门店的积分抽奖功能？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "draw/wxappintegraldraw/enable",
                    contentType: "application/json",
                    data: JSON.stringify(appIds),
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
        //禁用
        forbid: function () {
            var grid = $('#table').bootstrapTable('getSelections');
            if (!grid.length) {
                alert("请选择一条记录");
                return;
            }

            var flag = true;
            $.each(grid, function (idx, item) {
                if (isBlank(item.state)) {
                    flag = false;
                    return;
                }
            });

            if (!flag) {
                alert("请选中开通积分抽奖功能的门店");
                return;
            }

            var appIds = getSelectedRowsId("appId");
            if (appIds == null) {
                return;
            }

            confirm('确定要禁用选中门店的积分抽奖功能？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "draw/wxappintegraldraw/forbid",
                    contentType: "application/json",
                    data: JSON.stringify(appIds),
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
        //开通 积分抽奖功能
        open: function () {
            var grid = $('#table').bootstrapTable('getSelections');
            if (!grid.length) {
                alert("请选择一条记录");
                return;
            }

            var flag = true;
            $.each(grid, function (idx, item) {
                if (!isBlank(item.state)) {
                    flag = false;
                    return;
                }
            });

            if (!flag) {
                alert("请选中尚未开通积分抽奖功能的门店");
                return;
            }

            var appIds = getSelectedRowsId("appId");
            if (appIds == null) {
                return;
            }

            confirm('确定要为选中门店开通积分抽奖功能？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "draw/wxappintegraldraw/open",
                    contentType: "application/json",
                    data: JSON.stringify(appIds),
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
        query: function () {
            vm.reload();
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

            var total = Number(vm.prize1.probability) + Number(vm.prize2.probability) + Number(vm.prize3.probability) + Number(vm.prize4.probability) +
                Number(vm.prize5.probability) + Number(vm.prize6.probability) + Number(vm.prize7.probability) + Number(vm.prize8.probability);
            if (total !== 100) {
                alert("中奖概率之和须等于100");
                return;
            }
            vm.integraldraw.prizeList = [];
            vm.integraldraw.prizeList.push(vm.prize1);
            vm.integraldraw.prizeList.push(vm.prize2);
            vm.integraldraw.prizeList.push(vm.prize3);
            vm.integraldraw.prizeList.push(vm.prize4);
            vm.integraldraw.prizeList.push(vm.prize5);
            vm.integraldraw.prizeList.push(vm.prize6);
            vm.integraldraw.prizeList.push(vm.prize7);
            vm.integraldraw.prizeList.push(vm.prize8);
            // var url = vm.integraldraw.createTime == null ? "draw/wxappintegraldraw/save" : "draw/wxappintegraldraw/update";
            $.ajax({
                type: "POST",
                // url: baseURL + url,
                url: baseURL + "draw/wxappintegraldraw/update",
                contentType: "application/json",
                data: JSON.stringify(vm.integraldraw),
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
            var appIds = getSelectedRowsId("appId");
            if (appIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "draw/wxappintegraldraw/delete",
                    contentType: "application/json",
                    data: JSON.stringify(appIds),
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
        getInfo: function (appId) {
            $.get(baseURL + "draw/wxappintegraldraw/info/" + appId, function (r) {
                vm.integraldraw = r.wxAppIntegralDraw;
                if (null != vm.integraldraw && null != vm.integraldraw.prizeList && vm.integraldraw.prizeList.length > 0) {
                    for (var i in vm.integraldraw.prizeList) {
                        var prize = vm.integraldraw.prizeList[i];
                        if (prize.sort == 1) {
                            vm.prize1 = prize;
                        } else if (prize.sort == 2) {
                            vm.prize2 = prize;
                        } else if (prize.sort == 3) {
                            vm.prize3 = prize;
                        } else if (prize.sort == 4) {
                            vm.prize4 = prize;
                        } else if (prize.sort == 5) {
                            vm.prize5 = prize;
                        } else if (prize.sort == 6) {
                            vm.prize6 = prize;
                        } else if (prize.sort == 7) {
                            vm.prize7 = prize;
                        } else if (prize.sort == 8) {
                            vm.prize8 = prize;
                        }
                    }
                } else {
                    vm.integraldraw = {};
                    vm.prize1 = {sort: 1};
                    vm.prize2 = {sort: 2};
                    vm.prize3 = {sort: 3};
                    vm.prize4 = {sort: 4};
                    vm.prize5 = {sort: 5};
                    vm.prize6 = {sort: 6};
                    vm.prize7 = {sort: 7};
                    vm.prize8 = {
                        prize: '谢谢参与',
                        sort: 8
                    };
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.title = "";
            //刷新 如需条件查询common.js
            $("#table").BTF5(vm.queryParams);
            $("form").RF();


        },
        showTableList: function (event) {
            vm.showList = true;
            vm.title = "";
            //刷新 如需条件查询common.js
            // $("#table").BTF5(vm.queryParams);
            $("form").RF();


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







