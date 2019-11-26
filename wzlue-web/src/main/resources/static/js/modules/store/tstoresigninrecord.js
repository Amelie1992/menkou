$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'store/tstoresigninrecord/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '用户姓名', field: 'wname'},
            {title: '签到时间', field: 'createTime'},
            {title: '备注', field: 'remarks'},
            {title: '连续签到天数', field: 'countDate'}
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
        tStoreSignInRecord: {},
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
            }, createTime: {
                message: '创建时间验证失败',
                validators: {
                    notEmpty: {
                        message: '创建时间不能为空'
                    },
                },
            }, remarks: {
                message: '备注验证失败',
                validators: {
                    notEmpty: {
                        message: '备注不能为空'
                    },
                },
            }, countDate: {
                message: '连续签到天数验证失败',
                validators: {
                    notEmpty: {
                        message: '连续签到天数不能为空'
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
            vm.tStoreSignInRecord = {};
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
            var url = vm.tStoreSignInRecord.id == null ? "store/tstoresigninrecord/save" : "store/tstoresigninrecord/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.tStoreSignInRecord),
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
                    url: baseURL + "store/tstoresigninrecord/delete",
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
            $.get(baseURL + "store/tstoresigninrecord/info/" + id, function (r) {
                vm.tStoreSignInRecord = r.tStoreSignInRecord;
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