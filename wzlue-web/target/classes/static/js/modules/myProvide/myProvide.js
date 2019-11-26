$(function () {
    $("#table2").BT({
        url: baseURL + 'provide/pUploadHistory/list',//历史清单列表--当前登录人的appId
        columns: [
            {checkbox: true, width: '60px'},
            {title: '清单编号', field: 'id'},
            {title: '招聘单位', field: 'shopRecruitment.recruitmentFirm'},
            {title: '招聘标题', field: 'shopRecruitment.title'},
            {title: '成功条数', field: 'num'},
            {title: '备注', field: 'remark',formatter:function (value, row, index) {
                    return '<textarea rows="5" cols="20" disabled="disabled">'+value+'</textarea>'
                }},
            {title: '状态', field: 'confirm',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-warning">待门店确认</span>';
                    } else if (value == 2) {
                        return '<span class="label label-primary">已确认接受</span>';
                    } else if (value == 3) {
                        return '<span class="label label-danger">拒绝</span>';
                    } else if (value == 5) {
                        return '<span class="label label-warning">自己取消</span>';
                    }
                }},
            {title: '拒绝原因', field: 'errorInfo'},
            {title: '创建时间', field: 'createDate'},
            {title: '操作', events: vm.events, formatter: function (value, row, index) {
                var ss ="<a href='javascript:void(0)' id='provideBtn'>" + "供人列表" + "</a>";
                if (row.status == 1 && row.confirm!=5) { //待平台审核 & 自己没有取消过
                    ss+="&nbsp;&nbsp;<a href='javascript:void(0)' id='cancel'>" + "取消" + "</a>"
                }
                return ss;
                }, align: 'center', width: "30%"}
        ],
        detailView: true,
        detailFormatter: function (index, row, element) {
            return vm.detailFormatter(index, row, element);
        },
        onExpandRow: function (index, row, $detail) {
            return vm.onExpandRow(index, row, $detail);
        },
    });

    $("#table3").BT({
        url: baseURL + 'provide/providePersonnel/list',//appId=当前登录人的
        columns: [
            {checkbox: true, width: '60px'},
            {title: '招聘单位', field: 'shopTitle'},
            {title: '姓名', field: 'name'},
            {title: '性别', field: 'sex',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-warning">男</span>';
                    } else if (value == 2) {
                        return '<span class="label label-primary">女</span>';
                    }
                }},
            {title: '年龄', field: 'age'},
            {title: '省', field: 'provinceStr'},
            {title: '市', field: 'cityStr'},
            {title: '创建时间', field: 'createDate'},
            {title: '操作', events: vm.events, formatter: function (value, row, index) {
                    if (row.status===1){
                        return "<a href='javascript:void(0)' id='provideInfo'>" + "编辑" + "</a>";
                    }
                }, align: 'center', width: "30%"}
        ],
        //条件查询
        queryParams: {}
    });

    //供人表单提交
    $("#provideForm").FM({
        fields: vm.fields,
        success: vm.saveOrUpdateProvide
    })

});

var vm = new Vue({
    el: '#rrapp',
    data: {
        user_Store : userStore,
        image: '',
        historyList:true,
        provideList:false,
        provideDetail:false,
        title: null,
        pUploadHistory: {},//记录
        providePersonnel:{},//清单
        shopId:'',
        historyId:'',
        labels: null,
        scales: null,
        treatments: null,
        years: null,
        provinces: null,
        cities: null,
        areas: null,
        events: {
            //供人列表
            'click #provideBtn': function (event, value, row, index) {
                vm.params3.uploadHistoryId = row.id;
                vm.shopId = row.shopRecruitmentId;
                vm.historyId = row.id;
                vm.getProvideList(row.id);
            },
            //供人编辑
            'click #provideInfo': function (event, value, row, index) {
                vm.update(row);
            },
            //供人取消
            'click #cancel': function (event, value, row, index) {
                vm.cancel(row.id);
            }
        },
        params2:{
            appId:'',
            beginTime: '',
            endTime: '',
            title:'',
            confirm:'',
            remark:''
        },
        appList: [],
        params3:{
            name:'',
            uploadHistoryId:'',
        },
        statusList: [
            {'value': 1, 'label': '待门店确认'},
            {'value': 2, 'label': '已确认接受'},
            {'value': 3, 'label': '拒绝'},
            {'value': 5, 'label': '自己取消'},
        ],
        feeReturnList: [],    //返费
        step1: 0,    //返费 子项个数
        //验证字段
        fields: {
            name: {
                message: '姓名验证失败',
                validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    },stringLength: {
                        max: 50,
                        message: '长度最大50'
                    }, regexp: {
                        regexp: /^[\u2E80-\u9FFF]+$/,
                        message: '输入合法姓名'
                    }
                },
            }, age: {
                message: '年龄验证失败',
                validators: {
                    notEmpty: {
                        message: '年龄不能为空'
                    }, regexp: {
                        regexp: /^(\d\d{0,1}|100)$/,
                        message: '请输入正确年龄'
                    }
                },
            }, province: {
                message: '省验证失败',
                validators: {
                    notEmpty: {
                        message: '省不能为空'
                    },
                },
            }, city: {
                message: '市验证失败',
                validators: {
                    notEmpty: {
                        message: '市不能为空'
                    },
                },
            }, status: {
                message: '状态：1待审核 2通过 3拒绝验证失败',
                validators: {
                    notEmpty: {
                        message: '状态：1待审核 2通过 3拒绝不能为空'
                    },
                },
            }
        }
    },
    methods: {
        //供人取消
        cancel(id) {
            vm.pUploadHistory={
                id: id,
                confirm: 5 //门店确认供人状态：5供人方自己取消
            };
            layer.prompt(
                {
                    formType: 2,
                    value: '信息有误！',
                    title: '请输入取消原因！',
                    maxlength: 100,
                    area: ['350px', '150px'] //自定义文本域宽高
                },
                function (value, index, elem) {
                    vm.pUploadHistory.remark = value;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "provide/pUploadHistory/update",
                        contentType: "application/json",
                        data: JSON.stringify(vm.pUploadHistory),
                        success: function (r) {
                            layer.close(index);
                            if (r.code === 0) {
                                alert('操作成功', function (index) {
                                    vm.getHistoryList();
                                });
                            } else {
                                alert(r.msg);
                            }
                        }
                    });
                });
        },
        onExpandRow: function (index, row, $detail) {
            var uploadHistoryId = row.id;
            var $tb = $detail.find('table');
            $tb.bootstrapTable({
                ajax: function (params) {
                    $.get('/provide/providePersonnel/blist', params.data, function (e) {
                        params.success(e ? e.rows : []);
                    }, 'json')
                },
                'idField': 'id',//指定主键列
                "uniqueId": "id",//对每一行指定唯一标识符
                "pagination": true,
                "striped": true,
                "pageNumber": 1,
                "pageSize" : 10, //每页的记录行数（*）
                "sidePagination": "client",
                queryParams: function (params) {
                    var params = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                        limit: params.limit,   //页面大小
                        page: Math.ceil((params.offset + 1)/params.limit),  //页码
                    };
                    params.uploadHistoryId = uploadHistoryId;
                    return params;
                }
            });
        },
        detailFormatter: function (index, row, element) {
            var html = '<table class="table table-hover" id="ddv-' + index + '">' +
                '          <thead>' +
                '            <tr>' +
                '                <th data-field="name">姓名</th>' +
                '                <th data-field="genderName">性别</th>' +
                '                <th data-field="age">年龄(岁)</th>' +
                '                <th data-field="provinceStr">省</th>' +
                '                <th data-field="cityStr">市</th>' +
                '            </tr>' +
                '          </thead>' +
                '          </tbody>' +
                '        </table>';
            return html;
        },

        provincesChange: function (index, child, value) {
            var str = JSON.stringify(window.getCities(value));
            str = str.replace(/id/g, "value").replace(/shortname/g, "label");
            this.cities = JSON.parse(str);
            vm.providePersonnel.city = null;   //跟换省份时清除城市的选中状态
            this.areas = null;      //清除区列表数据
        },
        add: function () {
            vm.historyList = false;
            vm.provideList = false;
            vm.provideDetail = true;
            vm.title = "新增供人";
            //招聘id，上传历史id
            vm.providePersonnel = {
                sex:'1',
                status:'1',
                shopRecruitmentId:vm.shopId,
                uploadHistoryId:vm.historyId,
            };
        },
        update: function (row) {
            vm.historyList = false;
            vm.provideList = false;
            vm.provideDetail = true;
            vm.title = "修改供人";
            vm.getInfoProvide(row.id)
        },
        //重置按钮
        reset: function () {
            vm.params2 = {
                beginTime: '',
                endTime: '',
                title:'',
                status:'',
                remark:''
            }
        },
        query2: function () {
            vm.params2.beginTime = $('#beginTime').val();
            vm.params2.endTime = $('#endTime').val();
            vm.getHistoryList();
        },
        query3: function () {
            vm.getProvideList();
        },
        //表单验证
        validate: function () {
            var bl = $('#provideForm').VF();//启用验证
            if (!bl) {
                return;
            }
        },
        //供人详情
        getInfoProvide: function (id) {
            $.get(baseURL + "provide/providePersonnel/info/" + id, function (r) {
                vm.providePersonnel = r.providePersonnel;
            });
        },
        saveOrUpdateProvide: function (event) {
            var url = vm.providePersonnel.id == null ? "provide/providePersonnel/save" : "provide/providePersonnel/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.providePersonnel),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.getProvideList();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        //加载二级列表
        getHistoryList:function (id) {
            vm.provideList = false;
            vm.provideDetail = false;
            vm.historyList = true;
            vm.title = "上传历史清单";
            //刷新 如需条件查询common.js
            $("#table2").BTF5(vm.params2);
        },
        //加载二级列表
        getHistoryListNotReload:function (id) {
            vm.provideList = false;
            vm.provideDetail = false;
            vm.historyList = true;
            vm.title = "上传历史清单";
            //刷新 如需条件查询common.js
            // $("#table2").BTF5(vm.params2);
        },
        //加载三级列表
        getProvideList:function (id) {
            vm.historyList = false;
            vm.provideList = true;
            vm.provideDetail = false;
            vm.title = "供人列表";
            vm.providePersonnel ={}
            //刷新 如需条件查询common.js
            $("#table3").BTF5(vm.params3);
            $("#provideForm").RF();
        },       //加载三级列表
        getProvideListNotReload:function (id) {
            vm.historyList = false;
            vm.provideList = true;
            vm.provideDetail = false;
            vm.title = "供人列表";
            vm.providePersonnel ={}
            //刷新 如需条件查询common.js
            // $("#table3").BTF5(vm.params3);
            $("#provideForm").RF();
        },
    },
    created: function () {
        var str = JSON.stringify(window.getProvinces());
        str = str.replace(/id/g, "value").replace(/shortname/g, "label");
        this.provinces = JSON.parse(str);
    },
    watch: {
        "providePersonnel.province": function (value, old) {
            if (value != old) {
                var str = JSON.stringify(window.getCities(value));
                str = str.replace(/id/g, "value").replace(/shortname/g, "label");
                this.cities = JSON.parse(str);
                setTimeout(function () {
                    vm.providePersonnel.city = parseInt(vm.providePersonnel.city);
                }, 0)
            }
        }
    }
});