$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'store/tstoreglory/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '标题', field: 'gloryTitle'},
            {title: '排序', field: 'glorySort'},
            /*{title: '公众号编号', field: 'appId'},*/
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
        categoryList: [],
        title: null,
        tStoreGlory: {},
        queryParams: {
            appId: ''
        },
        //验证字段
        fields: {
            gloryTitle: {
                message: '标题验证失败',
                validators: {
                    notEmpty: {
                        message: '标题不能为空'
                    },
                    stringLength: {
                        max: 50,
                        message: '最多可填50个字'
                    }
                },
            }, gloryContent: {
                message: '内容验证失败',
                validators: {
                    notEmpty: {
                        message: '内容不能为空'
                    },
                },
            }, glorySort: {
                message: '排序验证失败',
                validators: {
                    /*notEmpty: {
                        message: '排序不能为空'
                    },*/
                    digits: {
                        message: '只能包含正整数。'
                    },
                    stringLength: {
                        max: 6,
                        message: '长度不能超过6位'
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
            vm.tStoreGlory = {};


            // 初始化富文本
            ue.setContent('');
        },
        update: function (event) {
            var gloryId = getSelectedRowId("gloryId");
            if (gloryId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(gloryId)
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

            var url = vm.tStoreGlory.gloryId == null ? "store/tstoreglory/save" : "store/tstoreglory/update";


            var gloryContent = $('#container').val();

            ue.addListener("ready", function () {
                // editor准备好之后才可以使用
                ue.setContent(gloryContent);

            });
            vm.tStoreGlory.gloryContent = ue.getContent();

            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.tStoreGlory),
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
            var gloryIds = getSelectedRowsId("gloryId");
            if (gloryIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "store/tstoreglory/delete",
                    contentType: "application/json",
                    data: JSON.stringify(gloryIds),
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
        getInfo: function (gloryId) {
            $.get(baseURL + "store/tstoreglory/info/" + gloryId, function (r) {
                vm.tStoreGlory = r.tStoreGlory;
                // 初始化富文本
                ue.setContent(r.tStoreGlory.gloryContent);
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

            let contentLength = UE.getEditor('container').getContentLength(true);
            if (contentLength > 500) {
                alert("内容最多输入500个字符");
                return true;
            }


        },
        exportExcel: function () {
            var gloryIds = getSelectedRowsIds("gloryId");
            if (gloryIds == null) {
                $.table.exportExcel('所有', '/store/tstoreglory/export/0', '光荣榜', vm.queryParams);
            } else {
                vm.queryParams.ids = gloryIds;
                $.table.exportExcel('选中', '/store/tstoreglory/export/1', '光荣榜', vm.queryParams);
            }

        }

    },
    created: function () {
        this.getCategory();

    }
});