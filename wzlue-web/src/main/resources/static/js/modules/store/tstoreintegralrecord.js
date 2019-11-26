$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'store/tstoreintegralrecord/list',
        columns: [
            {checkbox: true, width: '60px'},

            {title: '用户姓名', field: 'wname'},
            {title: '备注', field: 'remarks'},
            {title: '积分奖励', field: 'integral'},
            {title: '创建时间', field: 'createTime'},
            {title: '积分类型', field: 'integralType'}
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
        tStoreIntegralRecord: {},
        //验证字段
        fields: {
            openId: {
                message: '用户id验证失败',
                validators: {
                    notEmpty: {
                        message: '用户id不能为空'
                    },
                },
            }, appId: {
                message: '公众号id验证失败',
                validators: {
                    notEmpty: {
                        message: '公众号id不能为空'
                    },
                },
            }, remarks: {
                message: '备注验证失败',
                validators: {
                    notEmpty: {
                        message: '备注不能为空'
                    },
                },
            }, integral: {
                message: '积分奖励验证失败',
                validators: {
                    notEmpty: {
                        message: '积分奖励不能为空'
                    },
                },
            }, createTime: {
                message: '创建时间验证失败',
                validators: {
                    notEmpty: {
                        message: '创建时间不能为空'
                    },
                },
            }, integralType: {
                message: '积分类型验证失败',
                validators: {
                    notEmpty: {
                        message: '积分类型不能为空'
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
            vm.tStoreIntegralRecord = {};
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
            var url = vm.tStoreIntegralRecord.id == null ? "store/tstoreintegralrecord/save" : "store/tstoreintegralrecord/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.tStoreIntegralRecord),
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
                    url: baseURL + "store/tstoreintegralrecord/delete",
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
            $.get(baseURL + "store/tstoreintegralrecord/info/" + id, function (r) {
                vm.tStoreIntegralRecord = r.tStoreIntegralRecord;
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