$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'clock/cMemberClock/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '微信用户唯一标识', field: 'openId'},
            {title: '入职用户id', field: 'userNo'},
            {title: '门店招聘id', field: 'shopRecruitmentId'},
            {title: '打卡时间', field: 'clockTime'},
            {title: '打卡类型(1打卡2补卡)', field: 'clockType'},
            {title: '打卡地址', field: 'clockAddress'},
            {title: '备注说明', field: 'remark'},
            {title: '设备id', field: 'deviceId'},
            {title: '逻辑删除标记（1已删除2未删除）', field: 'delFlag'},
            {title: '应用ID', field: 'appId'},
            {title: '创建者', field: 'createId'},
            {title: '创建时间', field: 'createDate'},
            {title: '更新者', field: 'updateId'},
            {title: '更新时间', field: 'updateDate'}
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
        showList: true,
        title: null,
        cMemberClock: {},
        //验证字段
        fields: {
            openId: {
                message: '微信用户唯一标识验证失败',
                validators: {
                    notEmpty: {
                        message: '微信用户唯一标识不能为空'
                    },
                },
            }, userNo: {
                message: '入职用户id验证失败',
                validators: {
                    notEmpty: {
                        message: '入职用户id不能为空'
                    },
                },
            }, shopRecruitmentId: {
                message: '门店招聘id验证失败',
                validators: {
                    notEmpty: {
                        message: '门店招聘id不能为空'
                    },
                },
            }, clockTime: {
                message: '打卡时间验证失败',
                validators: {
                    notEmpty: {
                        message: '打卡时间不能为空'
                    },
                },
            }, clockType: {
                message: '打卡类型(1打卡2补卡)验证失败',
                validators: {
                    notEmpty: {
                        message: '打卡类型(1打卡2补卡)不能为空'
                    },
                },
            }, clockAddress: {
                message: '打卡地址验证失败',
                validators: {
                    notEmpty: {
                        message: '打卡地址不能为空'
                    },
                },
            }, remark: {
                message: '备注说明验证失败',
                validators: {
                    notEmpty: {
                        message: '备注说明不能为空'
                    },
                },
            }, deviceId: {
                message: '设备id验证失败',
                validators: {
                    notEmpty: {
                        message: '设备id不能为空'
                    },
                },
            }, delFlag: {
                message: '逻辑删除标记（1已删除2未删除）验证失败',
                validators: {
                    notEmpty: {
                        message: '逻辑删除标记（1已删除2未删除）不能为空'
                    },
                },
            }, appId: {
                message: '应用ID验证失败',
                validators: {
                    notEmpty: {
                        message: '应用ID不能为空'
                    },
                },
            }, createId: {
                message: '创建者验证失败',
                validators: {
                    notEmpty: {
                        message: '创建者不能为空'
                    },
                },
            }, createDate: {
                message: '创建时间验证失败',
                validators: {
                    notEmpty: {
                        message: '创建时间不能为空'
                    },
                },
            }, updateId: {
                message: '更新者验证失败',
                validators: {
                    notEmpty: {
                        message: '更新者不能为空'
                    },
                },
            }, updateDate: {
                message: '更新时间验证失败',
                validators: {
                    notEmpty: {
                        message: '更新时间不能为空'
                    },
                },
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
            vm.cMemberClock = {};
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
            var url = vm.cMemberClock.id == null ? "clock/cMemberClock/save" : "clock/cMemberClock/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.cMemberClock),
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
                    url: baseURL + "clock/cMemberClock/delete",
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
            $.get(baseURL + "clock/cMemberClock/info/" + id, function (r) {
                vm.cMemberClock = r.cMemberClock;
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.title = "";
            //刷新 如需条件查询common.js
            $("#table").BTF5();
        }
    }
});