$(function () {
    $("#table").BT({
        url: baseURL + 'sys/schedule/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '任务ID', field: 'jobId'},
            {title: 'bean名称', field: 'beanName'},
            {title: '方法名称', field: 'methodName'},
            {title: '参数', field: 'params'},
            {title: 'cron表达式', field: 'cronExpression'},
            {title: '备注', field: 'remark'},
            {
                title: '状态', field: 'status', formatter: function (value, row, index) {
                return value === 0 ?
                    '<span class="label label-success">正常</span>' :
                    '<span class="label label-danger">暂停</span>';
            }
            },
        ],
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            beanName: null
        },
        showList: true,
        title: null,
        schedule: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.schedule = {};
        },
        update: function () {
            var jobId = getSelectedRow();
            if (jobId == null) {
                return;
            }

            $.get(baseURL + "sys/schedule/info/" + jobId, function (r) {
                vm.showList = false;
                vm.title = "修改";
                vm.schedule = r.schedule;
            });
        },
        saveOrUpdate: function () {
            if (vm.validator()) {
                return;
            }

            var url = vm.schedule.jobId == null ? "sys/schedule/save" : "sys/schedule/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.schedule),
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
        del: function () {
            var jobIds = getSelectedRowsId("jobId");
            if (jobIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/schedule/delete",
                    contentType: "application/json",
                    data: JSON.stringify(jobIds),
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
        pause: function () {
            var jobIds = getSelectedRowsId("jobId");
            if (jobIds == null) {
                return;
            }

            confirm('确定要暂停选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/schedule/pause",
                    contentType: "application/json",
                    data: JSON.stringify(jobIds),
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
        resume: function () {
            var jobIds = getSelectedRowsId("jobId");
            if (jobIds == null) {
                return;
            }

            confirm('确定要恢复选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/schedule/resume",
                    contentType: "application/json",
                    data: JSON.stringify(jobIds),
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
        runOnce: function () {
            var jobIds = getSelectedRowsId("jobId");
            if (jobIds == null) {
                return;
            }

            confirm('确定要立即执行选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/schedule/run",
                    contentType: "application/json",
                    data: JSON.stringify(jobIds),
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
        reload: function (event) {
            vm.showList = true;
            $("#table").BTF5({
                'beanName': vm.q.beanName
            });
        },
        validator: function () {
            if (isBlank(vm.schedule.beanName)) {
                alert("bean名称不能为空");
                return true;
            }

            if (isBlank(vm.schedule.methodName)) {
                alert("方法名称不能为空");
                return true;
            }

            if (isBlank(vm.schedule.cronExpression)) {
                alert("cron表达式不能为空");
                return true;
            }
        }
    }
});