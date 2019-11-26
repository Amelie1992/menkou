$(function () {
    vm.initTable();
    vm.initTable3();
    //列表
    $("#table").BT({
        url: baseURL + 'clock/clockIn/list',//查询已入职的员工
        columns: [
            {checkbox: true, width: '60px'},
            {title: '招聘标题', field: 'shopRecruitmentEntity.title'},
            {title: '招聘单位', field: 'shopRecruitmentEntity.recruitmentFirm'},
            {title: '姓名', field: 'realName'},
            {title: '昵称', field: 'nickName'},
            {
                title: '性别', field: 'sex',
                formatter: function (value, row, index) {
                    /* return value === 1 ?
                         '<span>男</span>' :
                         '<span>女</span>';*/
                    if (value == 1) {
                        return '<span>男</span>';
                    } else if (value == 2) {
                        return '<span>女</span>';
                    }

                }
            },
            {title: '手机号', field: 'phone'},
            {title: '入职日期', field: 'hiredate'},
            {
                title: '在职状态', field: 'stateFeedback',
                formatter: function (value, row, index) {
                    if (value == 4) {
                        return '<span class="label label-warning">在职</span>';
                    } else if (value == 5) {
                        return '<span class="label label-default">离职</span>';
                    } else if (value == 6) {
                        return '<span class="label label-primary">其他</span>';
                    }
                }
            },
            {
                title: '打卡状态', field: 'clockInEntity.type',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-warning">存在</span>';
                    } else if (value == 2) {
                        return '<span class="label label-default">禁用</span>';
                    } else {
                        return '<span class="label label-danger">未设置</span>';
                    }
                }
            },
            {title: '打卡返费奖励(元)', field: 'reward'},
            {title: '操作', events: operateEvent, formatter: operFormatter, align: 'center', width: "30%"}
        ],
        //条件查询
        params: {}
    });
    //表单提交
    $("#form").FM({
        fields: vm.fields,
        success: vm.saveOrUpdate
    })
});

//操作按钮渲染
function operFormatter(value, row, index) {
    return '<a id="btn_detail">打卡详情</a>&nbsp;&nbsp;<a id="btn_info">设置打卡</a>&nbsp;&nbsp;<a id="btn_clockList">打卡记录</a>' +
        '&nbsp;&nbsp;<a id="btn_returnFee">返费记录</a>&nbsp;&nbsp;<a id="btn_withdraw" onclick="withdraw(' + row.id + ',' + row.reward + ')">提现</a>';
};

//提现框
function withdraw(id, reward) {
    layer.open({
        title: '提现',
        type: 1,
        area: ['420px', '160px'], //宽高
        content: '<div><input id="amount" type="text" oninput="inputnum(this)" class="form-control" placeholder="请输入提现金额,小数点后两位"/></div>',
        btn: ['确定', '取消'],
        yes: function (index, layero) {
            var amount = $("#amount").val();
            if (amount == null || amount == '') {
                alert("不能为空!")
                return;
            } else if (amount > reward) {
                alert("不能大于返费奖励额度!")
                return;
            } else if (amount <= 0) {
                alert("需大于0!")
                return;
            }
            var url = "jobApplication/jobapplication/withdraw";//返费提现
            $.ajax({
                type: "GET",
                url: baseURL + url,
                data: {
                    id: id,
                    amount: amount
                },
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            layer.closeAll();
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        }
    });
};

//input输入框只能输入数字和 小数点后两位
function inputnum(obj, val) {
    obj.value = obj.value.replace(/[^\d.]/g, ""); //清除"数字"和"."以外的字符
    obj.value = obj.value.replace(/^\./g, ""); //验证第一个字符是数字
    obj.value = obj.value.replace(/\.{2,}/g, ""); //只保留第一个, 清除多余的
    obj.value = obj.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
    obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/, '$1$2.$3'); //只能输入两个小数
}

function getSelectedRowIdData(dataId) {
    var grid = $('#table2').bootstrapTable('getSelections');
    if (!grid.length) {
        alert("请选择一条记录");
        return;
    }
    var ids = [];
    $.each(grid, function (idx, item) {
        ids[idx] = item[dataId.toString()];
    });
    return ids;
}

function getSelectedRowIdData3(dataId) {
    var grid = $('#table3').bootstrapTable('getSelections');
    if (!grid.length) {
        alert("请选择一条记录");
        return;
    }
    var ids = [];
    $.each(grid, function (idx, item) {
        ids[idx] = item[dataId.toString()];
    });
    return ids;
}

//操作方法
window.operateEvent = {
    'click #btn_detail': function (e, value, row, index) {
        vm.title = "打卡详情";
        if (row.clockInEntity.id == null) {
            alert("尚未设置打卡信息！")
        } else {
            vm.showList = false;
            vm.clockAdd = true;
            vm.sureBtn = false;
            vm.getInfo(row.clockInEntity.id);
        }
    },
    'click #btn_info': function (e, value, row, index) {
        vm.title = "单个设置打卡地址";
        vm.showList = false;
        vm.clockAdd = true;
        vm.sureBtn = true;
        if (row.clockInEntity.id == null) {
            vm.add(row.id);
        } else {
            vm.getInfo(row.clockInEntity.id);
        }
    },
    'click #btn_clockList': function (e, value, row, index) {
        vm.title = "打卡记录";
        if (row.clockInEntity.id == null) {
            alert("尚未设置打卡信息,暂无打卡记录！")
        } else {
            vm.showList = false;
            vm.clockAdd = false;
            vm.params2.userNo = row.id;
            vm.reloadTwo();
        }
    },
    'click #btn_returnFee': function (e, value, row, index) {
        vm.title = "返费记录";
        if (row.clockInEntity.id == null) {
            alert("尚未设置打卡信息,暂无返费记录！")
        } else {
            vm.showList = false;
            vm.feeList = true;
            vm.params3.jobId = row.id;
            vm.reloadThree();
        }
    }

};

var vm = new Vue({
    el: '#rrapp',
    data: {
        user_Store: userStore,
        showList: true,//一级列表页
        clockAdd: false,//打卡设置页
        clockList: false,//打卡记录
        clockSet: false,//打卡设置
        feeList: false,//返费记录
        sureBtn: true,//确定按钮
        title: null,
        clockIn: {
            type: 1
        },
        params: {
            appId: '',
            stateFeedback: '',
            beginTime: '',
            endTime: ''
        },
        cMemberClock: {},//打卡信息
        params2: {
            userNo: '',
            time: '',
        },
        feeReturnRecord: {},//返费信息
        params3: {
            jobId: '',
            type: '',
        },
        appList: [],
        //状态
        statusList: [
            {'value': 4, 'label': '在职'},
            {'value': 5, 'label': '已离职'},
            {'value': 6, 'label': '其他'},
        ],
        //返费类型
        typeList: [
            {'value': 1, 'label': '返费'},
            {'value': 2, 'label': '提现'},
        ],
        //验证字段
        fields: {
            effectiveDistance: {
                message: '有效范围验证失败',
                validators: {
                    notEmpty: {
                        message: '有效范围不能为空'
                    }, stringLength: {
                        max: 8,
                        message: '有效范围长度最大8'
                    }, regexp: {
                        regexp: /^(([1-9]{1}\d*)|(0{1}))(\.\d{1})?$/,
                        message: '保留小数点后1位'
                    }
                },
            }, displayAddress: {
                message: '打卡显示名称验证失败',
                validators: {
                    notEmpty: {
                        message: '打卡显示名称不能为空'
                    }, stringLength: {
                        max: 20,
                        message: '打卡显示名称长度在20字符以内'
                    }
                },
            }, address: {
                message: '详细地址验证失败',
                validators: {
                    notEmpty: {
                        message: '详细地址不能为空'
                    }, stringLength: {
                        max: 50,
                        message: '详细地址长度在50字符以内'
                    }
                },
            }
        }
    },
    methods: {
        initTable: function () {
            $("#table2").BT({
                url: baseURL + 'clock/cMemberClock/list',//报名用户打卡记录
                columns: [
                    {checkbox: true, width: '60px'},
                    /* {title: '微信用户唯一标识', field: 'openId'},*/
                    {title: '用户姓名', field: 'jobApplication.realName'},
                    {title: '入职单位', field: 'shopRecruitment.recruitmentFirm'},
                    {title: '应打卡日期', field: 'createDate'},
                    {title: '实际打卡时间', field: 'clockTime'},
                    {title: '连续打卡天数', field: 'numFlag'},
                    {
                        title: '打卡类型', field: 'clockType',
                        formatter: function (value, row, index) {
                            if (value == 1) {
                                return '<span class="label label-warning">正常</span>';
                            } else {
                                return '<span class="label label-primary">缺卡</span>';
                            }
                        }
                    },
                    {title: '打卡地址', field: 'clockAddress'},
                    {title: '备注说明', field: 'remark'},
                    {
                        title: '操作', width: '10%', align: 'center', formatter: function (value, row, index) {
                            if (row.clockType == 2) {
                                if (row.toMend == 1) {
                                    return '<a id="btn_dataUpdate">补卡</a>';
                                } else {
                                    return '<span>暂不支持补卡</span>';
                                }
                            }
                        }, events: {
                            //打卡详情
                            'click #btn_dataUpdate': function (e, value, row, index) {
                                vm.showList = false;
                                vm.clockAdd = false;
                                vm.clockList = false;
                                vm.clockSet = true;
                                vm.title = "当天补卡";
                                var time = $("#time").val();
                                var id = row.id;
                                $.get(baseURL + "clock/cMemberClock/info/?time=" + time + "&id=" + id, function (r) {
                                    if (r.code === 0) {
                                        vm.cMemberClock = r.cMemberClock;
                                    } else {
                                        alert(r.msg);
                                        vm.clockList = true;
                                        vm.clockSet = false;
                                    }
                                });
                            },
                        }
                    }
                ],
            });
            //table2表单提交
            $("#formTwo").FM({
                fields: {},
                success: vm.saveOrUpdateTwo
            });
        },

        initTable3: function () {
            $("#table3").BT({
                url: baseURL + 'fee/feeReturnRecord/list',//返费记录
                columns: [
                    {checkbox: true, width: '60px'},
                    {title: '入职员工', field: 'jobApplication.realName'},
                    {title: '金额(元)', field: 'amount'},
                    {
                        title: '类型', field: 'type',
                        formatter: function (value, row, index) {
                            if (value == 1) {
                                return '<span class="label label-warning">返费</span>';
                            } else {
                                return '<span class="label label-primary">提现</span>';
                            }
                        }
                    },
                    {title: '备注', field: 'remark'},
                    {title: '创建时间', field: 'createDate'}
                ],
                //条件查询
                params3: {}
            });
        },
        //当前年月
        time: function (year, month) {
            var date = new Date();
            var year = date.getFullYear() + '';
            var month = date.getMonth() + 1;
            if (month == 11 || month == 12 || month == 10) {
                var timeww = year + month;
            } else {
                var timeww = year + '0' + month;
            }
            return timeww
        },
        query: function () {
            vm.params.beginTime = $('#beginTime').val();
            vm.params.endTime = $('#endTime').val();
            vm.reload();
        },
        query2: function () {
            $("#table2").bootstrapTable('removeAll');
            vm.params2.time = $("#time").val();
            vm.reloadTwo();
        },
        query3: function () {
            vm.reloadThree();
        },
        add: function (id) {
            vm.showList = false;
            vm.title = "新增";
            vm.clockIn = {
                memberId: id,
                type: 1
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
            var bl = $('#form').VF();//启用验证
            if (!bl) {
                return;
            }
        },
        formTwoValidate: function () {
            var bl = $('#formTwo').VF();//启用验证
            if (!bl) {
                return;
            }
        },
        saveOrUpdate: function (event) {
            if (isBlank(vm.clockIn.coordinateX) || isBlank(vm.clockIn.coordinateY)) {
                alert("请在地图上设置打卡位置");
                return
            }
            var url = vm.clockIn.id == null ? "clock/clockIn/save" : "clock/clockIn/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.clockIn),
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
        //补卡操作
        saveOrUpdateTwo: function (event) {
            if (vm.cMemberClock.clockType == 2) {
                alert("请选择正常打卡!")
                return false;
            }
            vm.cMemberClock.tableName = $("#time").val();
            var url = vm.cMemberClock.id == null ? "clock/cMemberClock/save" : "clock/cMemberClock/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.cMemberClock),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reloadTwo();
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
                    url: baseURL + "clock/clockIn/delete",
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
        //打卡记录删除（软删）
        delTwo: function (event) {
            var ids = getSelectedRowIdData("id");
            if (ids == null) {
                return;
            }
            var tableName = vm.time();
            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "clock/cMemberClock/delete?tableName=" + tableName,
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                vm.reloadTwo();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        //返费删除（软删）
        delThree: function (event) {
            var ids = getSelectedRowIdData3("id");
            if (ids == null) {
                return;
            }
            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "fee/feeReturnRecord/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                vm.reloadThree();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        //重置按钮
        reset: function () {
            vm.params = {
                appId: '',
                stateFeedback: '',
                beginTime: '',
                endTime: ''
            }
        },
        getInfo: function (id) {
            $.get(baseURL + "clock/clockIn/info/" + id, function (r) {
                vm.clockIn = r.clockIn;
            });
        },
        //一级列表刷新
        reload: function (event) {
            vm.showList = true;
            vm.clockList = false;
            vm.clockAdd = false;
            vm.feeList = false;
            vm.title = "入职用户列表";
            //刷新 如需条件查询common.js
            $("#table").BTF5(vm.params);
            $("#form").RF();
        },
        //一级列表刷新
        showTableList: function (event) {
            vm.showList = true;
            vm.clockList = false;
            vm.clockAdd = false;
            vm.feeList = false;
            vm.title = "入职用户列表";
            //刷新 如需条件查询common.js
            // $("#table").BTF5(vm.params);
            $("#form").RF();
        },
        //二级表单返回
        dataBack: function () {
            vm.reloadTwo();
            vm.title = "打卡记录";
            vm.showList = false;
            vm.clockList = true;
        },
        //打卡记录列表刷新
        reloadTwo: function (event) {
            if (vm.params2.time == '') {
                vm.params2.time = vm.time();
            }
            $("#table2").BTF5(vm.params2);
            $("#formTwo").RF();
            vm.title = "打卡记录"
            vm.clockList = true;
            vm.showList = false;
            vm.clockSet = false;
            vm.feeList = false;
        },
        //返费记录列表刷新
        reloadThree: function (event) {
            vm.title = "返费记录"
            vm.feeList = true;
            vm.clockList = false;
            vm.showList = false;
            $("#table3").BTF5(vm.params3);
            $("#formThree").RF();
        }

    }
});