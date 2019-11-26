$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'store/tstorecontactus/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '标题', field: 'title'},
            {title: '客服电话', field: 'phone'},
            {title: '企业邮箱', field: 'email'},
            {title: '企业地址', field: 'address'},
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
        categoryList: [],
        queryParams: {
            appId:''
        },
        tStoreContactUs: {},
        // ue: UE.getEditor('myEditor', {
        //     initialFrameHeight: 220
        // }),
        //验证字段
        fields: {
            title: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    stringLength: {
                        max: 50,
                        message: '长度不能超过50位'
                    }
                }
            },
            phone: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    stringLength: {
                        max: 30,
                        message: '长度不能超过30位'
                    },
                    regexp: {
                        regexp: /((\d{11})|^((\d{7,8})|(\d{4}|\d{3})-(\d{7,8})|(\d{4}|\d{3})-(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1})|(\d{7,8})-(\d{4}|\d{3}|\d{2}|\d{1}))$)/ ,
                        message: '请输入正确的联系方式'
                    }

                }
            }, email: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    stringLength: {
                        max: 50,
                        message: '长度不能超过50位'
                    },
                    emailAddress: {
                        message: '邮箱地址格式有误'
                    }
                }
            },
            address: {
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                    stringLength: {
                        max: 100,
                        message: '长度不能超过100位'
                    }
                }
            },
            appId: {
                message: '应用ID验证失败',
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
            vm.tStoreContactUs = {};
            // vm.ue.setContent('');

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
            /*  vm.tStoreContactUs.content = UE.utils.unhtml(this.ue.getContent());
          var imgReg = /img.*?(?:>|\/)/gi;
          var arr = vm.tStoreContactUs.content.match(imgReg);//筛选出图片个数
          if (arr != null && arr.length > 0) {
              alert("富文本不允许插入图片");
              return
          }
          //富文本长度限制
          var length = this.ue.getContentLength(true);
         /* if (length <{
              alert("富文本不能为空");
              return;
          }
          if (length > 500 ) {
              alert("富文本最多输入500个字符");
              return;
          }
      */

            var url = vm.tStoreContactUs.id == null ? "store/tstorecontactus/save" : "store/tstorecontactus/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.tStoreContactUs),
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
                    url: baseURL + "store/tstorecontactus/delete",
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
            // vm.ue.setContent('');
            $.get(baseURL + "store/tstorecontactus/info/" + id, function (r) {
                vm.tStoreContactUs = r.tStoreContactUs;
                // vm.ue.setContent(UE.utils.html(vm.tStoreContactUs.content));
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