$(function () {
    // 表单提交
    $("form").FM({
        fields: vm.fields,
        success: vm.saveOrUpdate

    })

});

var vm = new Vue(
    {
        el: '#rrapp',

        data: {
            showList: false,
            title: null,
            config: {
                signInRule: {},
                newbieTask: {}
            },
            newbieTask: {},
            signInRule: {},
            awardRulesList: [],
            step1: 0,
            // 验证字段
            fields: {

                /*newbieTaskScore: {
                    message: '验证失败',
                    validators: {
                         notEmpty: {
                             message: '新手任务积分不能为空'
                         },regexp: {
                            regexp: /^[1-9]\d{0,9}$/,
                            message: '新手任务积分为正整数'
                        }
                    },
                },

                signInRuleScore: {
                    message: '验证失败',
                    validators: {
                        notEmpty: {
                            message: '每日签到积分不能为空'
                        },regexp: {
                            regexp: /^[1-9]\d{0,9}$/,
                            message: '每日签到积分为正整数'
                        }
                    },
                },*/

                ruleType: {
                    message: '规则类型验证失败',
                    validators: {
                        notEmpty: {
                            message: '规则类型不能为空'
                        },
                    },
                },
                lose: {
                    message: '失去验证失败',
                    validators: {
                        notEmpty: {
                            message: '失去不能为空'
                        },
                    },
                },
                get: {
                    message: '获得验证失败',
                    validators: {
                        notEmpty: {
                            message: '获得不能为空'
                        },
                    },
                },
                upperLimit: {
                    message: '上限验证失败',
                    validators: {
                        notEmpty: {
                            message: '上限不能为空'
                        },
                    },
                },
                updateTime: {
                    message: '更新时间验证失败',
                    validators: {
                        notEmpty: {
                            message: '更新时间不能为空'
                        },
                    },
                }
            }
        },
        methods: {

            addwardRules: function () {
                if (vm.step1 == 5) {
                    alert("最多添加5个连续签到奖励规则");
                    return;
                }
                vm.awardRulesList.push({
                    days: '',
                    addscore: ''
                });
                vm.step1 = vm.step1 + 1;
            },

            remove: function (index) {
                vm.awardRulesList.splice(index, 1);
                vm.step1 = vm.step1 - 1;
            },

            update: function (event) {
                var id = getSelectedRowId("id");
                if (id == null) {
                    return;
                }
                vm.showList = false;
                vm.title = "修改";
            },
            // 表单验证
            validate: function () {
                var bl = $('form').VF();// 启用验证
                if (!bl) {
                    return;
                }
            },
            saveOrUpdate: function (event) {
                var url = "sys/config/updateByKey";


                if (vm.newbieTask.score != null && vm.newbieTask.score != '') {
                    if (!/(^[1-9](\d{0,8})$)/.test(vm.newbieTask.score)) {
                        alert("新手任务积分请输入正整数");
                        return;
                    }
                }
                if (vm.signInRule.score != null && vm.signInRule.score != '') {
                    if (!/(^[1-9](\d{0,8})$)/.test(vm.signInRule.score)) {
                        alert("每日签到积分请输入正整数");
                        return;
                    }
                }


                vm.config.newbieTask = vm.newbieTask;
                vm.config.signInRule = vm.signInRule;



                //返费
                if (vm.awardRulesList != null && vm.awardRulesList.length > 0) {
                    for (let obj of vm.awardRulesList) {
                        if (!/(^[1-9](\d{0,8})$)/.test(obj.days)) {
                            alert("天数请输入正整数");
                            return;
                        }
                        if (!/(^[1-9](\d{0,8})$)/.test(obj.addscore)) {
                            alert("积分请输入正整数");
                            return;
                        }

                    }
                    vm.config.signInRule.awardRules = vm.awardRulesList;
                }


                //vm.config.signInRule.awardRules = vm.awardRulesList;


                var params = {key: "INTEGRAL_SETTING", value: JSON.stringify(vm.config)};
                $.ajax({
                    type: "POST",
                    url: baseURL + url,
                    contentType: "application/json",
                    data: JSON.stringify(params),
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function (index) {

                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            },
            getInfo: function () {
                $.get(baseURL + "sys/config/getByKey", {key: "INTEGRAL_SETTING"},
                    function (r) {
                        vm.config = JSON.parse(r.value);
                        vm.newbieTask = vm.config.newbieTask;
                        vm.signInRule = vm.config.signInRule;
                        vm.awardRulesList = vm.config.signInRule.awardRules;
                    });
            }


        },
        created: function () {
            this.getInfo();

        }
    });