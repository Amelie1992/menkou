$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'provide/pUploadHistory/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '门店招聘id', field: 'shopRecruitmentId'},
            {title: '成功条数', field: 'num'},
            {title: '备注', field: 'remark'},
            {title: '状态：1待审核 2通过 3拒绝', field: 'status'},
            {title: '拒绝原因', field: 'reason'},
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
        pUploadHistory: {},
        //验证字段
        fields: {
            shopRecruitmentId: {
                message: '门店招聘id验证失败',
                validators: {
                    notEmpty: {
                        message: '门店招聘id不能为空'
                    },
                },
            }, num: {
                message: '成功条数验证失败',
                validators: {
                    notEmpty: {
                        message: '成功条数不能为空'
                    },
                },
            }, remark: {
                message: '备注验证失败',
                validators: {
                    notEmpty: {
                        message: '备注不能为空'
                    },
                },
            }, status: {
                message: '状态：1待审核 2通过 3拒绝验证失败',
                validators: {
                    notEmpty: {
                        message: '状态：1待审核 2通过 3拒绝不能为空'
                    },
                },
            }, reason: {
                message: '拒绝原因验证失败',
                validators: {
                    notEmpty: {
                        message: '拒绝原因不能为空'
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
            vm.pUploadHistory = {};
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
            var url = vm.pUploadHistory.id == null ? "provide/pUploadHistory/save" : "provide/pUploadHistory/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.pUploadHistory),
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
                    url: baseURL + "provide/pUploadHistory/delete",
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
            $.get(baseURL + "provide/pUploadHistory/info/" + id, function (r) {
                vm.pUploadHistory = r.pUploadHistory;
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