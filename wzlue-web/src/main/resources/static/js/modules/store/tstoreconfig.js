$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'store/tstoreconfig/list',
        columns: [
            {checkbox: true, width: '60px'},
            /*{title: '配置类型', field: 'configType'},*/

            {
                title: '配置类型', field: 'configType',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-default">标签</span>';
                    } else if (value == 2) {
                        return '<span class="label label-primary">待遇</span>';
                    } else if (value == 3) {
                        return '<span class="label label-success">规模</span>';
                    } else if (value == 4) {
                        return '<span class="label label-info">企业年限</span>';
                    } else if (value == 5) {
                        return '<span class="label label-warning">返费金额</span>';
                    } else if (value == 6) {
                        return '<span class="label label-danger">岗位类型</span>';
                    } else if (value == 7) {
                        return '<span class="label label-default">自定义岗位种类</span>';
                    }
                }
            },


            {title: '配置内容', field: 'configValue'},
            {title: '最小范围', field: 'rangeMin'},
            {title: '最大范围', field: 'rangeMax'},
            {title: '排序', field: 'configSort'}
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
        tStoreConfig: {},
        queryParams: {
            configType: ''
        },

        typeList: [
            {'value': 1, 'label': '标签'},
            {'value': 2, 'label': '待遇'},
            {'value': 3, 'label': '规模'},
            {'value': 4, 'label': '企业年限'},
            {'value': 5, 'label': '返费金额'},
            {'value': 6, 'label': '岗位类型'},
            {'value': 7, 'label': '自定义岗位种类'}
        ],


        //验证字段
        fields: {
            configType: {
                message: '配置类型验证失败',
                validators: {
                    notEmpty: {
                        message: '配置类型不能为空'
                    },
                },
            },
            /*configValue: {
                message: '配置内容验证失败',
                validators: {
                    notEmpty: {
                        message: '配置内容不能为空'
                    },
                    stringLength: {
                        max: 15,
                        message: '长度不能超过15位'
                    }
                },
            },*/
            /*configValue: {
                message: '配置内容验证失败',
                validators: {
                    notEmpty: {
                        message: '配置内容不能为空'
                    },
                    stringLength: {
                        max: 15,
                        message: '长度不能超过15位'
                    },
                    callback: {
                        message : '333',
                        callback:function(value, validator){

                            var configType = vm.tStoreConfig.configType;
                            var configValue = vm.tStoreConfig.configValue;
                            if (isBlank(configType)) {
                                return true;
                            }
                            /!*
                            *
                            * {'value': 1, 'label': '标签'},
            {'value': 2, 'label': '待遇'},
            {'value': 3, 'label': '规模'},
            {'value': 4, 'label': '企业年限'},
            {'value': 5, 'label': '返费金额'},
            {'value': 6, 'label': '岗位类型'}
            *!/
                            var validatorLen = 15;
                            if (configType == 1) {
                                validatorLen = 5;
                            }
                            if (isBlank(configValue)) {
                                return true;
                            }
                            if (configValue.length >= validatorLen) {

                                return false;
                            }


                        }
                    }
                },
            },*/ rangeMin: {
                message: '最小范围验证失败',
                validators: {
                    /*notEmpty: {
                        message: '最小范围不能为空'
                    },*/
                    regexp: {
                        regexp: /^-?\d+$/,
                        message: '只能包含正负整数。'
                    },
                    stringLength: {
                        max: 5,
                        message: '长度不能超过5位'

                    },
                    callback: {
                        message: '最小范围必须小于最大范围',
                        callback:function(value, validator,$field,options){

                            var rangeMin = vm.tStoreConfig.rangeMin;
                            var rangeMax = vm.tStoreConfig.rangeMax;

                            if (isBlank(rangeMin) || isBlank(rangeMax)) {
                                return true;
                            }
                            return parseInt(rangeMin)<parseInt(rangeMax);

                        }
                    }
                },
            }, rangeMax: {
                message: '最大范围验证失败',
                validators: {
                    /*notEmpty: {
                        message: '最大范围不能为空'
                    },*/
                    regexp: {
                        regexp: /^-?\d+$/,
                        message: '只能包含正负整数。'
                    },
                    stringLength: {
                        min: 1,
                        max: 15,
                        message: '长度不能超过15位'
                    },
                    callback: {
                        message: '最小范围必须小于最大范围',
                        callback:function(value, validator,$field,options){
                            var rangeMin = vm.tStoreConfig.rangeMin;
                            var rangeMax = vm.tStoreConfig.rangeMax;
                            if (isBlank(rangeMin) || isBlank(rangeMax)) {
                                return true;
                            }


                            return parseInt(rangeMin)<parseInt(rangeMax);
                        }
                    }
                },
            }, configSort: {
                message: '排序验证失败',
                validators: {
                    /*notEmpty: {
                        message: '排序不能为空'
                    },*/
                    digits: {
                        message: '只能包含数字。'
                    },
                    stringLength: {
                        max: 6,
                        message: '长度不能超过6位'
                    }
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
            vm.tStoreConfig = {};
        },
        update: function (event) {
            var configId = getSelectedRowId("configId");
            if (configId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(configId)
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
            var url = vm.tStoreConfig.configId == null ? "store/tstoreconfig/save" : "store/tstoreconfig/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.tStoreConfig),
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
            var configIds = getSelectedRowsId("configId");
            if (configIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "store/tstoreconfig/delete",
                    contentType: "application/json",
                    data: JSON.stringify(configIds),
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
        getInfo: function (configId) {
            $.get(baseURL + "store/tstoreconfig/info/" + configId, function (r) {
                vm.tStoreConfig = r.tStoreConfig;
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
        validator: function () {
            var configType = vm.tStoreConfig.configType;
            var configValue = vm.tStoreConfig.configValue;
            if (isBlank(configType)) {
                alert("配置类型不能为空");
                return true;
            }
            if (isBlank(configValue)) {
                alert("配置内容不能为空");
                return true;
            }
            var validatorLen = 15;
            if (configType == 1) {
                validatorLen = 5;
            }
            if (configValue.length >= validatorLen){
                alert("配置内容最多可填"+validatorLen+"个字");
                return true;
            }

        }
    },
    created: function () {
        /*this.tStoreConfig.configType = this.typeList[0];*/

    }
});
