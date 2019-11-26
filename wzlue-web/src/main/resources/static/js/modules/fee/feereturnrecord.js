$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'fee/feeReturnRecord/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '入职员工id', field: 'jobId'},
            {title: '数额', field: 'amount'},
            {title: '类型：1返费2提现 ', field: 'type'},
            {title: '备注', field: 'remark'},
            {title: '', field: 'appId'},
            {title: '删除标志(1已删除2未删除)', field: 'delFlag'},
            {title: '', field: 'createId'},
            {title: '', field: 'createDate'},
            {title: '', field: 'updateId'},
            {title: '', field: 'updateDate'}
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
        feeReturnRecord: {},
        //验证字段
        fields: {
            jobId: {
                message: '入职员工id验证失败',
                validators: {
                    notEmpty: {
                        message: '入职员工id不能为空'
                    },
                },
            }, amount: {
                message: '数额验证失败',
                validators: {
                    notEmpty: {
                        message: '数额不能为空'
                    },
                },
            }, type: {
                message: '类型：1返费2提现 验证失败',
                validators: {
                    notEmpty: {
                        message: '类型：1返费2提现 不能为空'
                    },
                },
            }, remark: {
                message: '备注验证失败',
                validators: {
                    notEmpty: {
                        message: '备注不能为空'
                    },
                },
            }, appId: {
                message: '验证失败',
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                },
            }, delFlag: {
                message: '删除标志(1已删除2未删除)验证失败',
                validators: {
                    notEmpty: {
                        message: '删除标志(1已删除2未删除)不能为空'
                    },
                },
            }, createId: {
                message: '验证失败',
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                },
            }, createDate: {
                message: '验证失败',
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                },
            }, updateId: {
                message: '验证失败',
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                },
            }, updateDate: {
                message: '验证失败',
                validators: {
                    notEmpty: {
                        message: '不能为空'
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
            vm.feeReturnRecord = {};
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
            var url = vm.feeReturnRecord.id == null ? "fee/feeReturnRecord/save" : "fee/feeReturnRecord/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.feeReturnRecord),
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
                    url: baseURL + "fee/feeReturnRecord/delete",
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
            $.get(baseURL + "fee/feeReturnRecord/info/" + id, function (r) {
                vm.feeReturnRecord = r.feeReturnRecord;
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