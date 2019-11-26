$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'provide/providePersonnel/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '门店招聘id', field: 'shopRecruitmentId'},
            {title: '上传历史清单id', field: 'uploadHistoryId'},
            {title: '姓名', field: 'name'},
            {title: '性别:1男；2女', field: 'sex'},
            {title: '年龄', field: 'age'},
            {title: '省', field: 'province'},
            {title: '市', field: 'city'},
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
        providePersonnel: {},
        //验证字段
        fields: {
            shopRecruitmentId: {
                message: '门店招聘id验证失败',
                validators: {
                    notEmpty: {
                        message: '门店招聘id不能为空'
                    },
                },
            }, uploadHistoryId: {
                message: '上传历史清单id验证失败',
                validators: {
                    notEmpty: {
                        message: '上传历史清单id不能为空'
                    },
                },
            }, name: {
                message: '姓名验证失败',
                validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    },
                },
            }, sex: {
                message: '性别:1男；2女验证失败',
                validators: {
                    notEmpty: {
                        message: '性别:1男；2女不能为空'
                    },
                },
            }, age: {
                message: '年龄验证失败',
                validators: {
                    notEmpty: {
                        message: '年龄不能为空'
                    },
                },
            }, province: {
                message: '省验证失败',
                validators: {
                    notEmpty: {
                        message: '省不能为空'
                    },
                },
            }, city: {
                message: '市验证失败',
                validators: {
                    notEmpty: {
                        message: '市不能为空'
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
            vm.providePersonnel = {};
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
            var url = vm.providePersonnel.id == null ? "provide/providePersonnel/save" : "provide/providePersonnel/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.providePersonnel),
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
                    url: baseURL + "provide/providePersonnel/delete",
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
            $.get(baseURL + "provide/providePersonnel/info/" + id, function (r) {
                vm.providePersonnel = r.providePersonnel;
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