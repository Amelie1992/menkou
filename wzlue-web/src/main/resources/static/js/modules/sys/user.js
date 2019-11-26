$(function () {
    $("#table").BT({
        url: baseURL + 'sys/user/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '用户ID', field: 'userId'},
            {title: '用户名', field: 'username'},
            {title: '邮箱', field: 'email'},
            {title: '手机号', field: 'mobile'},
            {
                title: '状态', field: 'status',
                formatter: function (value, row, index) {
                    return value === 0 ?
                        '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">正常</span>';
                }
            },
            {title: '创建时间', field: 'createTime'},
            {title: '所属门店', field: 'appName'},
            {
                title: '操作', formatter: function (value, row, index) {
                    let temp = null;
                    // $.isEmptyObject(row.appName) ? temp : temp = row.appName;
                    if (!hasPermission('sys:user:bindApp')) return temp;

                    let store = row.store; // 0不是门店，1是门店
                    let appId = row.appId; // 公众号appid
                    let createId = row.createUserId;

                    if (store == '0' && $.isEmptyObject(appId) && createId == '1') { // 不是门店，appid为空，说明没有分配过门店
                        // 分配门店
                        temp = '<button id="swtichApp" class="btn btn-primary btn-sm" type="button">分配门店</button>';

                    } else if (store == '0' && !$.isEmptyObject(appId)) { // 不是门店，appid不为空,说明分配过门店

                    } else if (store == '1' && !$.isEmptyObject(appId)) { // 是门店，appid不为空，显示分配的门店

                    }

                    return temp;
                },
                events: operateEvent
            }
        ]
    });
});


window.operateEvent = {
    'click #swtichApp': function (e, value, row, index) {
        vm.q.userId = row.userId;
        $('#appModal').modal('show');
        vm.getWxApp();

    }
};

var vm = new Vue({
    el: '#rrapp',
    data: {
        wxApp: [],
        selectApp: {},
        q: {
            username: null,
            appId: '',
            userId: ''
        },
        showList: true,
        title: null,
        roleList: {},
        user: {
            status: 1,
            roleIdList: []
        }
    },
    methods: {
        getWxApp() {
            $.get(baseURL + 'wechat/wxapp/selectlistNotApp', function (r) {
                vm.wxApp = [];
                let mes = {id: '', name: "请选择门店"};
                let nmes = {id: '', name: "无"};
                let appList = r.wxAppList;
                if (r.code == 0) {
                    if (appList.length == 0) {
                        vm.wxApp = [mes,nmes];
                    } else {
                        appList.splice(0, 0, mes);
                        vm.wxApp = appList;
                    }
                } else {
                    alert('门店查询错误！');
                }

            })


        },

        // 绑定门店
        bindApp() {

            let temp = {
                userId: vm.q.userId,
                appId: vm.q.appId
            };

            if ($.isEmptyObject(temp.appId)) {
                alert('请选择门店');
                return;
            }

            $.post(baseURL + 'sys/user/bindApp', temp, function (r) {
                if (r.code == 0) {
                    $('#appModal').modal('hide');
                    alert('操作成功', function () {
                        vm.reload();
                    });
                } else {
                    alert(r.msg);
                }
            })
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.roleList = {};
            vm.user = {status: 1, roleIdList: []};

            //获取角色信息
            this.getRoleList();
        },
        update: function () {
            var userId = getSelectedRowId("userId");
            if (userId == null) {
                return;
            }

            vm.showList = false;
            vm.title = "修改";

            vm.getUser(userId);
            //获取角色信息
            this.getRoleList();
        },
        del: function () {
            var userIds = getSelectedRowsId("userId");
            if (userIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/user/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function () {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function () {
            if (vm.validator()) {
                return;
            }

            var url = vm.user.userId == null ? "sys/user/save" : "sys/user/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.user),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        getUser: function (userId) {
            $.get(baseURL + "sys/user/info/" + userId, function (r) {
                vm.user = r.user;
                /*vm.user.password = null;*/
            });
        },
        getRoleList: function () {
            $.get(baseURL + "sys/role/select", function (r) {
                vm.roleList = r.list;
            });
        },
        reload: function () {
            vm.showList = true;
            vm.title = "";
            $("table").BTF5({
                username: vm.q.username
            })
        },
        validator: function () {
            if (isBlank(vm.user.username)) {
                alert("用户名不能为空");
                return true;
            }

            if (vm.user.userId == null && isBlank(vm.user.password)) {
                alert("密码不能为空");
                return true;
            }

            if (isBlank(vm.user.email)) {
                alert("邮箱不能为空");
                return true;
            }

            if (!validator.isEmail(vm.user.email)) {
                alert("邮箱格式不正确");
                return true;
            }

            if (!isBlank(vm.user.mobile)) {
                var reg = /^1[345678]\d{9}$/;
                if(!reg.test(vm.user.mobile)) {
                    alert("手机格式不正确");
                    return true;
                }
            }

            if (vm.user.roleIdList == null || vm.user.roleIdList.length == 0) {
                alert("角色不能为空");
                return true;
            }
        }
    }
});