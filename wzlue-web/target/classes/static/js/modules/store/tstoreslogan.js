$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'store/tstoreslogan/list',
        columns: [
            {checkbox: true, width: '60px'},
            /*{
                title: '标语位置', field: 'position',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-success">首页</span>';
                    }
                }
            },*/
            {title: '主标语', field: 'mainTitle'},
            {title: '副标语', field: 'subTitle'},
            /*  {title: '公众号编号', field: 'appId'},*/
            {title: '公众号名称', field: 'wname'}
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
        user_Store : userStore,
        showList: true,
        title: null,
        tStoreSlogan: {},
        positionList: [
            {'value': 1, 'label': '首页'},
        ],
        categoryList: [],
        appIdList: [],

        queryParams: {
            appId: ''
        },
        //验证字段
        fields: {
            /*position: {
                message: '标语位置验证失败',
                validators: {
                    notEmpty: {
                        message: '标语位置不能为空'
                    },
                    stringLength: {
                        max: 10,
                        message: '长度不能超过10位'
                    }
                },
            }, */mainTitle: {
                message: '主标语验证失败',
                validators: {
                    notEmpty: {
                        message: '主标语不能为空'
                    },
                    stringLength: {
                        max: 10,
                        message: '最多可填10个字'
                    }
                },
            }, subTitle: {
                message: '副标语验证失败',
                validators: {
                    /* notEmpty: {
                         message: '副标语不能为空'
                     },*/
                    stringLength: {
                        max: 15,
                        message: '最多可填15个字'
                    }
                },
            }, appId: {
                message: '应用编号验证失败',
                validators: {
                    notEmpty: {
                        message: '公众号不能为空'
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
            vm.tStoreSlogan = {};
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
            var url = vm.tStoreSlogan.id == null ? "store/tstoreslogan/save" : "store/tstoreslogan/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.tStoreSlogan),
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
                    url: baseURL + "store/tstoreslogan/delete",
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
            $.get(baseURL + "store/tstoreslogan/info/" + id, function (r) {
                vm.tStoreSlogan = r.tStoreSlogan;
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
    }
});







