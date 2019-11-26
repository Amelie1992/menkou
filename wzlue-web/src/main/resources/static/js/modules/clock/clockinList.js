$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'recruitment/shoprecruitment/colockList',//查询招聘信息(门店的招聘信息)
        columns: [
            {checkbox: true, width: '60px'},
            {title: '招聘单位', field: 'recruitmentFirm'},
            {title: '招聘标题', field: 'title'},
            {title: '在职人数', field: 'entryNum'},
            {title: '操作', events: operateEvent, formatter: operFormatter, align: 'center',width:"30%"}
        ],
        //条件查询
        queryParams: {}
    });
    //表单提交
    $("#form").FM({
        fields: vm.fields,
        success: vm.saveOrUpdate
    })
});

//详情渲染
function operFormatter(value, row, index) {
    if (row.entryNum>0){
        return '<a id="btn_detail">打卡详情</a>&nbsp;&nbsp;<a id="btn_info">设置打卡</a>';
    }
};

//设置打卡地址
window.operateEvent = {
    'click #btn_detail': function (e, value, row, index) {
        vm.title = "打卡详情";
        if (row.clockInFlag==1){
            alert("尚未设置打卡信息！")
        } else {
            vm.showList = false;
            vm.sureBtn = false;
            vm.getInfo(row.id);
        }
    },
    'click #btn_info': function (e, value, row, index) {
        layer.confirm('此操作会重置该条招聘信息下\n' +
            '所有已入职员工的考勤位置信息\n' +
            '是否继续操作?', function(index){
            //do something
            vm.title = "设置打卡地址";
            vm.showList = false;
            vm.sureBtn = true;
            if (row.clockInFlag==1){
                vm.add(row.id);
            } else {
                vm.getInfo(row.id);
            }
            layer.close(index);
        });
    }
};

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        sureBtn: true,
        title: null,
        clockIn: {
            type:2
        },
        appList: [],
        params: {
            appId:''
        },
        //状态
        statusList: [
            {'value': 4, 'label': '已入职'},
            {'value': 5, 'label': '已离职'},
            {'value': 6, 'label': '其他'},
        ],
        //验证字段
        fields: {
            coordinateX: {
                message: 'x坐标验证失败',
                validators: {
                    notEmpty: {
                        message: 'x坐标不能为空'
                    },
                },
            }, coordinateY: {
                message: 'y坐标验证失败',
                validators: {
                    notEmpty: {
                        message: 'y坐标不能为空'
                    },
                },
            }, effectiveDistance: {
                message: '有效范围验证失败',
                validators: {
                    notEmpty: {
                        message: '有效范围不能为空'
                    },stringLength: {
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
        query: function () {
            vm.params.beginTime = $('#beginTime').val();
            vm.params.endTime = $('#endTime').val();
            vm.reload();
        },
        add: function (id) {
            vm.showList = false;
            vm.title = "新增";
            vm.clockIn = {
                recruitId:id,
                type:1
            };
        },
        //表单验证
        validate: function () {
            var bl = $('#form').VF();//启用验证
            if (!bl) {
                return;
            }
        },
        saveOrUpdate: function (event) {
            if (isBlank(vm.clockIn.coordinateX) || isBlank(vm.clockIn.coordinateY)) {
                alert("请在地图上设置打卡位置");
                return
            }
            var url = "clock/clockIn/saveList";
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

        //重置按钮
        reset:function(){
            vm.params = {
                appId:'',
                stateFeedback:'',
                beginTime: '',
                endTime: ''
            }
        },
        getInfo: function (recruitId) {
            $.get(baseURL + "clock/clockIn/infoClockIn/" + recruitId, function (r) {
                vm.clockIn = r.clockIn;
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.title = "招聘打卡设置列表";
            //刷新 如需条件查询common.js
            $("#table").BTF5(vm.params);
            $("#form").RF();
        },
        showTableList: function (event) {
            vm.showList = true;
            vm.title = "招聘打卡设置列表";
            //刷新 如需条件查询common.js
            // $("#table").BTF5(vm.params);
            $("#form").RF();
        }
    }
});