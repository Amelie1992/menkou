$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'doorway/tdoorwayaboutus/list',
        columns: [
            {checkbox: true, width: '60px'},
            {
                title: '公司简介', field: 'profile', formatter: function (value, row, index) {
                    if (!isBlank(value) && value.length>100) {
                        return value.substring(0,100);
                    }else {
                        return value;
                    }
                }
            },
            /* {title: '公众号名称', field: 'wname'}*/
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
        categoryList: [],
        tDoorwayAboutUs: {},
        queryParams: {},
        //验证字段
        fields: {},
        ue: UE.getEditor('myEditor', {
            initialFrameHeight: 220
        }),
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.tDoorwayAboutUs = {};
            vm.ue.setContent('');
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
            vm.tDoorwayAboutUs.profile = UE.utils.unhtml(this.ue.getContent());
            var imgReg = /img.*?(?:>|\/)/gi;
            var arr = vm.tDoorwayAboutUs.profile.match(imgReg);//筛选出图片个数
            if (arr != null && arr.length > 0) {
                alert("富文本不允许插入图片");
                return
            }
            //富文本长度限制
            var length = this.ue.getContentLength(true);
            if (length <= 0) {
                alert("富文本不能为空");
                return;
            }
            if (length > 500) {
                alert("富文本最多输入500个字符");
                return;
            }
            var url = vm.tDoorwayAboutUs.id == null ? "doorway/tdoorwayaboutus/save" : "doorway/tdoorwayaboutus/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.tDoorwayAboutUs),
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
                    url: baseURL + "doorway/tdoorwayaboutus/delete",
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
            vm.ue.setContent('');
            $.get(baseURL + "doorway/tdoorwayaboutus/info/" + id, function (r) {
                vm.tDoorwayAboutUs = r.tDoorwayAboutUs;
                vm.ue.setContent(UE.utils.html(vm.tDoorwayAboutUs.profile));
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.title = "";
            //刷新 如需条件查询common.js
            $("#table").BTF5(vm.queryParams);
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