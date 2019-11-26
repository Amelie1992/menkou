$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'store/wxappthemecolor/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '公众号名称', field: 'appname'},
            {
                title: '色块', field: 'backcolor', formatter: function (value, row, index) {
                    if (!isBlank(value)) {
                        var a = '<img src="/images/themecolor/backcolor/' + value + '.png" alt="背景色' + value + '" height="80px" width="100px">';
                        return a;
                    }
                }
            },
            {
                title: '图标', field: 'buttonColor', formatter: function (value, row, index) {
                    if (!isBlank(value)) {
                        var a = '<img src="/images/themecolor/buttonColor/' + value + '.png" alt="按钮颜色' + value + '" height="80px" width="80px">';
                        return a;
                    }
                }
            },
            {
                title: '形式', field: 'form', formatter: function (value, row, index) {
                    if (!isBlank(value)) {
                        var a = '<img src="/images/themecolor/form/' + value + '.png" alt="形式' + value + '" height="80px" width="160px">';
                        return a;
                    }
                }
            }
        ],
        //条件查询
        queryParams: {}
    });
    //表单提交
    $("form").FM({
        fields: vm.fields,
        success: vm.saveOrUpdate
    });


    //背景色
    $(".backcolor").click(function () {
        $(".backcolor ul").toggleClass("hide");
    });
    $(".backcolor li").click(function () {
        var a = $(this).html();
        console.log("aaaaaaaaaaaa", a);
        $("#text1").empty(); //清空
        $("#text1").append(a);   //賦值
    });

    //按钮颜色
    $(".buttonColor").click(function () {
        $(".buttonColor ul").toggleClass("hide");
    });
    $(".buttonColor li").click(function () {
        var a = $(this).html();
        console.log("bbbbbbbbbbb", a);
        $("#text2").empty(); //清空
        $("#text2").append(a);   //賦值
    });

    //形式
    $(".form").click(function () {
        $(".form ul").toggleClass("hide");
    });
    $(".form li").click(function () {
        var a = $(this).html();
        console.log("ccccccccc", a);
        $("#text3").empty(); //清空
        $("#text3").append(a);   //賦值
    })

});


var vm = new Vue({
    el: '#rrapp',
    data: {
        user_Store: userStore,
        showList: true,
        title: null,
        themecolor: {},
        categoryList: [],   //公众号
        queryParams: {
            appId: ''
        },
        //验证字段
        fields: {
            backcolor: {
                validators: {
                    notEmpty: {
                        message: '色块不能为空'
                    }
                }
            }, buttonColor: {
                validators: {
                    notEmpty: {
                        message: '图标不能为空'
                    }
                }
            }, form: {
                validators: {
                    notEmpty: {
                        message: '形式不能为空'
                    }
                }
            }, appId: {
                validators: {
                    notEmpty: {
                        message: '公众号不能为空'
                    }
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
            vm.themecolor = {};
            $("#text1").empty();
            $("#text2").empty();
            $("#text3").empty();

        },
        update: function (event) {
            var appId = getSelectedRowId("appId");
            if (appId == null) {
                return;
            }
            $("#text1").empty();
            $("#text2").empty();
            $("#text3").empty();

            $.ajaxSettings.async = false;
            vm.getInfo(appId);
            $.ajaxSettings.async = true;

            if (!isBlank(vm.themecolor.backcolor)) {
                var a = '<img src="/images/themecolor/backcolor/' + vm.themecolor.backcolor + '.png" alt="背景色' + vm.themecolor.backcolor + '" height="100px" width="100px">';
                $("#text1").append(a);
            }
            if (!isBlank(vm.themecolor.buttonColor)) {
                var a = '<img src="/images/themecolor/buttonColor/' + vm.themecolor.buttonColor + '.png" alt="按钮颜色' + vm.themecolor.buttonColor + '" height="100px" width="100px">';
                $("#text2").append(a);
            }
            if (!isBlank(vm.themecolor.form)) {
                var a = '<img src="/images/themecolor/form/' + vm.themecolor.form + '.png" alt="形式' + vm.themecolor.form + '" height="100px" width="200px">';
                $("#text3").append(a);
            }

            vm.showList = false;
            vm.title = "修改";
        },
        //表单验证
        validate: function () {
            var bl = $('form').VF();//启用验证
            if (!bl) {
                return;
            }
        },
        saveOrUpdate: function (event) {

            var text1 = $("#text1").html();
            var text2 = $("#text2").html();
            var text3 = $("#text3").html();
            if (!isBlank(text1)) {
                var lastIndexOf = text1.lastIndexOf("/");
                var indexOf = text1.indexOf(".");
                if (lastIndexOf != -1 && indexOf != -1) {
                    vm.themecolor.backcolor = text1.substring(lastIndexOf + 1, indexOf);
                }

            }
            if (!isBlank(text2)) {
                var lastIndexOf = text2.lastIndexOf("/");
                var indexOf = text2.indexOf(".");
                if (lastIndexOf != -1 && indexOf != -1) {
                    vm.themecolor.buttonColor = text2.substring(lastIndexOf + 1, indexOf);
                }

            }
            if (!isBlank(text3)) {
                var lastIndexOf = text3.lastIndexOf("/");
                var indexOf = text3.indexOf(".");
                if (lastIndexOf != -1 && indexOf != -1) {
                    vm.themecolor.form = text3.substring(lastIndexOf + 1, indexOf);
                }

            }

            var url = vm.themecolor.createTime == null ? "store/wxappthemecolor/save" : "store/wxappthemecolor/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.themecolor),
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
            var appIds = getSelectedRowsId("appId");
            if (appIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "store/wxappthemecolor/delete",
                    contentType: "application/json",
                    data: JSON.stringify(appIds),
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
        getInfo: function (appId) {
            $.get(baseURL + "store/wxappthemecolor/info/" + appId, function (r) {
                vm.themecolor = r.wxAppThemeColor;
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
    },
    watch: {}
});







