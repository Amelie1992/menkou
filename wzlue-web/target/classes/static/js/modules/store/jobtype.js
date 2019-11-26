$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'store/wxappjobtype/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '公众号名称', field: 'appname'},
            {
                title: '展现形式', field: 'number', formatter: function (value, row, index) {
                    if (!isBlank(value)) {
                        return value + "个";
                    }
                }
            },
            {title: '岗位种类1', field: 'button11'},
            {title: '岗位种类2', field: 'button22'},
            {title: '岗位种类3', field: 'button33'},
            {title: '岗位种类4', field: 'button44'},
            {title: '岗位种类5', field: 'button55'},
            {title: '岗位种类6', field: 'button66'},
            {title: '岗位种类7', field: 'button77'},
            {title: '岗位种类8', field: 'button88'}
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
        jobtype: {},
        typeList: [],   //岗位种类
        lastButton: {
            configId: '',
            configValue: ''
        },   //全部岗位
        categoryList: [],   //公众号
        appIdList: [],
        queryParams: {
            appId: ''
        },
        //验证字段
        fields: {
            number: {
                validators: {
                    notEmpty: {
                        message: '展现形式不能为空'
                    }
                }
            }, button1: {
                validators: {
                    notEmpty: {
                        message: '岗位种类1不能为空'
                    }
                }
            }, button2: {
                validators: {
                    notEmpty: {
                        message: '岗位种类2不能为空'
                    }
                }
            }, button3: {
                validators: {
                    notEmpty: {
                        message: '岗位种类3不能为空'
                    }
                }
            }, button4: {
                validators: {
                    notEmpty: {
                        message: '岗位种类4不能为空'
                    }
                }
            }, button5: {
                validators: {
                    notEmpty: {
                        message: '岗位种类5不能为空'
                    }
                }
            }, button6: {
                validators: {
                    notEmpty: {
                        message: '岗位种类6不能为空'
                    }
                }
            }, button7: {
                validators: {
                    notEmpty: {
                        message: '岗位种类7不能为空'
                    }
                }
            }, button8: {
                validators: {
                    notEmpty: {
                        message: '岗位种类8不能为空'
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
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.jobtype = {
                number: 4
            };
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
            var buttons;
            if (vm.jobtype.number == 4) {
                buttons = [];
                // buttons.push(vm.jobtype.button1).push(vm.jobtype.button2).push(vm.jobtype.button3);
                buttons.push(vm.jobtype.button1);
                buttons.push(vm.jobtype.button2);
                buttons.push(vm.jobtype.button3);

                vm.jobtype.button5 = -1;
                vm.jobtype.button6 = -1;
                vm.jobtype.button7 = -1;
                vm.jobtype.button8 = -1;

            } else if (vm.jobtype.number == 5) {
                buttons = [];
                buttons.push(vm.jobtype.button1);
                buttons.push(vm.jobtype.button2);
                buttons.push(vm.jobtype.button3);
                buttons.push(vm.jobtype.button4);

                vm.jobtype.button6 = -1;
                vm.jobtype.button7 = -1;
                vm.jobtype.button8 = -1;

            } else if (vm.jobtype.number == 8) {
                buttons = [];
                buttons.push(vm.jobtype.button1);
                buttons.push(vm.jobtype.button2);
                buttons.push(vm.jobtype.button3);
                buttons.push(vm.jobtype.button4);
                buttons.push(vm.jobtype.button5);
                buttons.push(vm.jobtype.button6);
                buttons.push(vm.jobtype.button7);
            }

            //判断数组是否含重复元素
            var hash = {};
            var flag = false;
            for (var i in buttons) {
                if (hash[buttons[i]]){
                    flag = true;
                    break;
                }
                hash[buttons[i]] = true;
            }

            if (flag) {
                alert("岗位种类重复");
                return;
            }


            var url = vm.jobtype.createTime == null ? "store/wxappjobtype/save" : "store/wxappjobtype/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.jobtype),
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
                    url: baseURL + "store/wxappjobtype/delete",
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
            $.get(baseURL + "store/wxappjobtype/info/" + appId, function (r) {
                vm.jobtype = r.wxAppJobType;
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
        },
        //岗位种类
        getConfig: function () {
            $.get(baseURL + "store/tstoreconfig/list", {
                "configType": 7,
                "sidx": "config_sort",
                "order": "asc",
                "page": 1,
                "offset": 0,
                "limit": 10000
            }, function (r) {
                vm.typeList = r.rows;
                vm.typeList.forEach(function (item) {
                    if (item.configValue == "全部岗位") {
                        var index = vm.typeList.indexOf(item);
                        vm.lastButton = vm.typeList[index];
                        vm.typeList.splice(index, 1);
                    }
                })
            })
        }
    },
    created: function () {
        this.getCategory();
        this.getConfig();
    },
    watch: {
        // "showList": function (value) {
        //     if (!value) {
        //         $("#lastButton option:first").prop("selected",'selected');
        //     }
        // }
        "jobtype.number": function (value) {
            if (value == 4) {
                this.jobtype.button4 = this.lastButton.configId;
            } else if (value == 5) {
                this.jobtype.button5 = this.lastButton.configId;
            } else if (value == 8) {
                this.jobtype.button8 = this.lastButton.configId;
            }
        }

    }
});







