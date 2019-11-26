$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'store/tstorenotice/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '标题', field: 'noticeTitle'},
            {
                title: '公告图', field: 'headLogo', formatter: function (value, row, index) {
                return '<img width="100px" height="100px" src=' + value + '>';
                }
            },
            {title: '排序', field: 'noticeSort'},
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
        tStoreNotice: {},
        categoryList: [],
        queryParams: {
            appId:''
        },
        //验证字段
        fields: {
            noticeTitle: {
                message: '标题验证失败',
                validators: {
                    notEmpty: {
                        message: '标题不能为空'
                    },
                    stringLength: {
                        max: 50,
                        message: '最多可填50个字'
                    }
                }
            }, noticeContent: {
                message: '内容验证失败',
                validators: {
                    notEmpty: {
                        message: '内容不能为空'
                    },
                }
            }, headLogo: {
                message: '公告图验证失败',
                validators: {
                    notEmpty: {
                        message: '公告图不能为空'
                    },
                }
            }, noticeSort: {
                message: '排序验证失败',
                validators: {
                   /* notEmpty: {
                        message: '排序不能为空'
                    },*/
                    digits: {
                        message: '只能包含正整数。'
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
            vm.tStoreNotice = {
                headLogo: ''
            };

            // 初始化富文本
            ue.setContent('');


        },
        update: function (event) {
            var noticeId = getSelectedRowId("noticeId");
            if (noticeId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(noticeId)
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


            var url = vm.tStoreNotice.noticeId == null ? "store/tstorenotice/save" : "store/tstorenotice/update";

            var noticeContent = $('#container').val();

            ue.addListener("ready", function () {
                // editor准备好之后才可以使用
                ue.setContent(noticeContent);

            });
            vm.tStoreNotice.noticeContent = ue.getContent();

            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.tStoreNotice),
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
            var noticeIds = getSelectedRowsId("noticeId");
            if (noticeIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "store/tstorenotice/delete",
                    contentType: "application/json",
                    data: JSON.stringify(noticeIds),
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
        getInfo: function (noticeId) {

            $.get(baseURL + "store/tstorenotice/info/" + noticeId, function (r) {
                vm.tStoreNotice = r.tStoreNotice;
                // 初始化富文本
                ue.setContent(r.tStoreNotice.noticeContent);
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
            var headLogo = vm.tStoreNotice.headLogo;

            if (isBlank(headLogo)) {
                alert("公告图不能为空");
                return true;
            }



            let contentLength = UE.getEditor('container').getContentLength(true);
            if (contentLength > 500) {
                alert("详情描述最多输入500个字符");
                return true;
            }


        }
    },
    created: function () {
        this.getCategory();

    }
});