$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'store/tstorerotarypic/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '标题', field: 'title'},
            /*{title: '轮播图', field: 'picUrl'},*/
            {
                title: '轮播图', field: 'picUrl', formatter: function (value, row, index) {
                return '<img width="100px" height="100px" src=' + value + '>';
            }
            },
            {title: '图片跳转链接至', field: 'link'},
           /* {title: '图片大小说明', field: 'explain'},*/
            {title: '排序', field: 'sort'},
           /* {title: '公众号编号', field: 'appId'},*/
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
        tStoreRotarypic: {},

        categoryList: [],
        queryParams: {
            appId:''
        },

        //验证字段
        fields: {
            title: {
                message: '标题验证失败',
                validators: {
                    notEmpty: {
                        message: '标题不能为空'
                    },
                    stringLength: {
                        max: 10,
                        message: '最多10个字符'
                    }
                }
            }, picUrl: {
                message: '轮播图验证失败',
                validators: {
                    notEmpty: {
                        message: '轮播图不能为空'
                    }
                }
            }, link: {
                message: '图片跳转链接验证失败',
                validators: {
                    /*notEmpty: {
                        message: '图片跳转链接不能为空'
                    },*/
                    stringLength: {
                        max: 180,
                        message: '长度不能超过180位'
                    }
                }
            },
           /* explain: {
                message: '图片大小说明验证失败',
                validators: {
                   /!* notEmpty: {
                        message: '图片大小说明不能为空'
                    },*!/
                    stringLength: {
                        max: 10,
                        message: '最多10个字符'
                    }

                }
            },*/

            sort: {
                message: '排序验证失败',
                validators: {
                    /*notEmpty: {
                        message: '排序不能为空'
                    },*/
                    digits: {
                        message: '只能包含数字。'
                    },
                    stringLength: {
                        max: 6,
                        message: '长度不能超过6位'
                    }
                }
            }, appId: {
                message: '应用编号验证失败',
                validators: {
                    notEmpty: {
                        message: '公众号不能为空'
                    },
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
            vm.tStoreRotarypic = {
                picUrl: ''
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
            if (vm.validator()) {
                return;
            }
            var url = vm.tStoreRotarypic.id == null ? "store/tstorerotarypic/save" : "store/tstorerotarypic/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.tStoreRotarypic),
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
                    url: baseURL + "store/tstorerotarypic/delete",
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
            $.get(baseURL + "store/tstorerotarypic/info/" + id, function (r) {
                vm.tStoreRotarypic = r.tStoreRotarypic;
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
        validator: function () {
            var picUrl = vm.tStoreRotarypic.picUrl;

            if (isBlank(picUrl)) {
                alert("轮播图不能为空");
                return true;
            }
        }
    },
    created: function () {
        this.getCategory();

    }
});