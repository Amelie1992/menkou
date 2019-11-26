$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'jobApplication/jobapplication/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '微信号', field: 'wechatNumber'},
            {title: '微信昵称', field: 'nickName'},
            {title: '姓名', field: 'realName'},
            {title: '手机号', field: 'phone'},
            {
                title: '性别', field: 'sex', formatter: function (value, row, index) {
                    if (value == 1) {
                        return "男";
                    } else if (value == 2) {
                        return "女";
                    }
                }
            },
            {title: '年龄/岁', field: 'age'},
            {
                title: '报名类型', field: 'type', formatter: function (value, row, index) {
                    if (value == 1) {
                        return "主动报名";
                    } else if (value == 2) {
                        return "招聘报名";
                    }
                    // else if (value == 3) {
                    //     return "推荐报名";
                    // }
                }
            },
            {
                title: '状态',
                field: 'stateFeedback',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return "已报名";
                    } else if (value == 2) {
                        return "已面试";
                    } else if (value == 3) {
                        return "未录用";
                    } else if (value == 4) {
                        return "已入职";
                    } else if (value == 5) {
                        return "已离职";
                    } else if (value == 6) {
                        return "其他";
                    }
                }
            },
            {title: '报名时间', field: 'createDate'},
            {
                title: '操作', field: 'id', formatter: function (value, row, index) {
                    var str = "<a href='javascript:void(0)' class='showInfo'>" + "详情" + "</a>";
                    str += '&nbsp;<a href="javascript:void(0)" class="feedback">' + "反馈" + "</a>";
                    if (!isBlank(row.type) && row.type == 2) {  //招聘报名
                        str += '&nbsp;<a href="javascript:void(0)" class="zpDetail">' + "招聘详情" + "</a>";

                    }
                    return str;
                }, events: vm.events
            }
        ],
        //条件查询
        queryParams: vm.params
    });
    //表单提交
    $("form").FM({
        fields: vm.fields,
        success: vm.saveOrUpdate
    });

    $("#feedbackForm").FM({
        fields: vm.feedbackFields,
        success: vm.feedback
    })
    //模态框隐藏时，清空验证和数据，以及重置表单
    $('#feedbackModal').on('hide.bs.modal', function () {
        $('#feedbackForm').bootstrapValidator('resetForm', true);//清空验证
        // $('#feedbackForm')[0].reset();
    })

});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        detailList: false,
        zpInfoList: false,
        title: null,
        jobApplication: {},
        shopRecruitment: {},
        image11: [],//轮播图
        ue1: UE.getEditor('myEditor1', {
            initialFrameHeight: 220,
        }),
        ue2: UE.getEditor('myEditor2', {
            initialFrameHeight: 220,
        }),
        ue3: UE.getEditor('myEditor3', {
            initialFrameHeight: 220,
        }),
        ue4: UE.getEditor('myEditor4', {
            initialFrameHeight: 220,
        }),
        feeReturnList: [],
        labels: [],
        scales: [],
        treatments: [],
        years: [],
        jobs: [],
        provinces: null,
        cities: null,
        areas: null,
        streets: null,
        params: {
            belongTo: 1,    //门店
            delFlag: 2,
            distributionStatus: 0,  //不是平台分配的
            type: '',
            stateFeedback: '',
            nickName: '',
            realName: '',
            recruitmentFirm: '',
            recruitmentTitle: ''
        },
        events: {
            'click .showInfo': function (event, value, row, index) {
                vm.showList = false;
                vm.detailList = true;
                vm.zpInfoList = false;
                vm.getInfo(value);
            },
            'click .feedback': function (event, value, row, index) {
                /* vm.jobApplication.id = value;
                 vm.jobApplication.stateFeedback = row.stateFeedback;
                 vm.jobApplication.feedback = '';*/
                vm.jobApplication = {
                    id: value,
                    stateFeedback: row.stateFeedback,
                    feedback: ''
                };
                $('#feedbackModal').modal('show');
            },
            'click .zpDetail': function (event, value, row, index) {
                vm.showList = false;
                vm.detailList = false;
                vm.zpInfoList = true;
                vm.getZPInfo(row.shopRecruitmentId);
            }
        },
        //验证字段
        fields: {
            openid: {
                message: '微信用户的唯一标识验证失败',
                validators: {
                    notEmpty: {
                        message: '微信用户的唯一标识不能为空'
                    },
                },
            }, nickName: {
                message: '微信昵称验证失败',
                validators: {
                    notEmpty: {
                        message: '微信昵称不能为空'
                    },
                },
            }, realName: {
                message: '姓名验证失败',
                validators: {
                    notEmpty: {
                        message: '姓名不能为空'
                    },
                },
            }, phone: {
                message: '手机号验证失败',
                validators: {
                    notEmpty: {
                        message: '手机号不能为空'
                    },
                },
            }, sex: {
                message: '性别：1男；2女验证失败',
                validators: {
                    notEmpty: {
                        message: '性别：1男；2女不能为空'
                    },
                },
            }, wechatNumber: {
                message: '微信号验证失败',
                validators: {
                    notEmpty: {
                        message: '微信号不能为空'
                    },
                },
            }, age: {
                message: '年龄验证失败',
                validators: {
                    notEmpty: {
                        message: '年龄不能为空'
                    },
                },
            }, workExperience: {
                message: '工作经验验证失败',
                validators: {
                    notEmpty: {
                        message: '工作经验不能为空'
                    },
                },
            }, jodId: {
                message: '应聘岗位类型id验证失败',
                validators: {
                    notEmpty: {
                        message: '应聘岗位类型id不能为空'
                    },
                },
            }, expectedSalary: {
                message: '期望薪资：1：4000元以下；2：4000～6000元；3：6000元～8000元；4：8000元以上验证失败',
                validators: {
                    notEmpty: {
                        message: '期望薪资：1：4000元以下；2：4000～6000元；3：6000元～8000元；4：8000元以上不能为空'
                    },
                },
            }, expectedProvince: {
                message: '期望工作地-省验证失败',
                validators: {
                    notEmpty: {
                        message: '期望工作地-省不能为空'
                    },
                },
            }, expectedCity: {
                message: '期望工作地-市验证失败',
                validators: {
                    notEmpty: {
                        message: '期望工作地-市不能为空'
                    },
                },
            }, expectedArea: {
                message: '期望工作地-区验证失败',
                validators: {
                    notEmpty: {
                        message: '期望工作地-区不能为空'
                    },
                },
            }, shopRecruitmentId: {
                message: '门店招聘id验证失败',
                validators: {
                    notEmpty: {
                        message: '门店招聘id不能为空'
                    },
                },
            }, platformRecruitmentId: {
                message: '平台招聘id验证失败',
                validators: {
                    notEmpty: {
                        message: '平台招聘id不能为空'
                    },
                },
            }, type: {
                message: '报名类型：1主动报名（求职）；2招聘报名（应聘）验证失败',
                validators: {
                    notEmpty: {
                        message: '报名类型：1主动报名（求职）；2招聘报名（应聘）不能为空'
                    },
                },
            }, stateFeedback: {
                message: '状态反馈：1已报名；2已面试；3已入职；4未录用；5已离职；6其他验证失败',
                validators: {
                    notEmpty: {
                        message: '状态反馈：1已报名；2已面试；3已入职；4未录用；5已离职；6其他不能为空'
                    },
                },
            }, appId: {
                message: ' 应用ID验证失败',
                validators: {
                    notEmpty: {
                        message: ' 应用ID不能为空'
                    },
                },
            }, createId: {
                message: '验证失败',
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                },
            }, createDate: {
                message: '验证失败',
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                },
            }, updateId: {
                message: '验证失败',
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                },
            }, updateDate: {
                message: '验证失败',
                validators: {
                    notEmpty: {
                        message: '不能为空'
                    },
                },
            }
        },
        feedbackFields: {
            stateFeedback: {
                validators: {
                    notEmpty: {
                        message: '反馈状态不能为空'
                    },
                },
            }, feedback: {
                validators: {
                    stringLength: {
                        max: '200',
                        message: '反馈备注须在200字符以内'
                    }
                },
            },
        }
    },
    methods: {
        //招聘详情
        getZPInfo: function (shopRecruitmentId) {
            vm.image11 = [];
            vm.ue1.setContent('');
            vm.ue2.setContent('');
            vm.ue3.setContent('');
            vm.ue4.setContent('');
            $.get(baseURL + "recruitment/shoprecruitment/info/" + shopRecruitmentId, function (r) {
                vm.shopRecruitment = r.shopRecruitment;
                //轮播图
                vm.image11 = vm.shopRecruitment.banner;
                //标签
                let $checkbox2 = $("input[name='configId2']");
                if (vm.shopRecruitment.labelList != null && vm.shopRecruitment.labelList.length > 0) {
                    vm.shopRecruitment.labelList.forEach(function (obj, index) {
                        if ($checkbox2.length > 0) {
                            $.each($checkbox2, function (idx, item) {
                                let cValue2 = item.value;
                                if (obj.configId == cValue2) {
                                    $(this).prop('checked', true);
                                }
                            });
                        }
                    });
                }
                //返费
                if (vm.shopRecruitment.feeReturnList != null && vm.shopRecruitment.feeReturnList.length > 0) {
                    vm.feeReturnList = vm.shopRecruitment.feeReturnList;
                } else {
                    vm.feeReturnList = [];
                }
                vm.ue1.setContent(UE.utils.html(vm.shopRecruitment.jobDescription));
                vm.ue2.setContent(UE.utils.html(vm.shopRecruitment.jobRequirement));
                vm.ue3.setContent(UE.utils.html(vm.shopRecruitment.jobResponsibilities));
                vm.ue4.setContent(UE.utils.html(vm.shopRecruitment.companyBenefits));
                vm.shopRecruitment.jobDescription = vm.ue1.body.textContent;
                vm.shopRecruitment.jobRequirement = vm.ue2.body.textContent;
                vm.shopRecruitment.jobResponsibilities = vm.ue3.body.textContent;
                vm.shopRecruitment.companyBenefits = vm.ue4.body.textContent;

                if (vm.shopRecruitment.famousFlag == 1) {//灵活用工
                    $(".hourlyWage").show();//时薪
                    $(".ffxx").hide();//返费
                }else {
                    $(".hourlyWage").hide();
                    $(".ffxx").show();
                }

                //岗位种类
                var ss = "";
                if (!isBlank(vm.shopRecruitment.recommendFlag) && (vm.shopRecruitment.recommendFlag == 1)) {
                    ss += '\xa0\xa0\xa0\xa0\xa0\xa0\xa0'+"推荐岗位";
                }
                if (!isBlank(vm.shopRecruitment.famousFlag) && (vm.shopRecruitment.famousFlag == 1)) {
                    ss +=  '\xa0\xa0\xa0\xa0\xa0\xa0\xa0'+"灵活用工";
                }
                if (!isBlank(vm.shopRecruitment.minorityFlag) && (vm.shopRecruitment.minorityFlag == 1)) {
                    ss +=  '\xa0\xa0\xa0\xa0\xa0\xa0\xa0'+"少数民族";
                }
                if (!isBlank(vm.shopRecruitment.studentFlag) && (vm.shopRecruitment.studentFlag == 1)) {
                    ss +=  '\xa0\xa0\xa0\xa0\xa0\xa0\xa0'+"学生工";
                }
                if (!isBlank(vm.shopRecruitment.olderFlag) && (vm.shopRecruitment.olderFlag == 1)) {
                    ss +=  '\xa0\xa0\xa0\xa0\xa0\xa0\xa0'+"大龄工";
                }
                if (!isBlank(vm.shopRecruitment.firstHandFlag) && (vm.shopRecruitment.firstHandFlag == 1)) {
                    ss +=  '\xa0\xa0\xa0\xa0\xa0\xa0\xa0'+"一手单";
                }
                vm.shopRecruitment.jobTypeStr = ss;
            });
        },
        //查看招聘详情
        ZPInfo: function (shopRecruitmentId) {
            window.location.href='/modules/recruitment/shoprecruitment.html?recruitmentId='+shopRecruitmentId;
        },
        //反馈
        feedback: function () {
            if(!isBlank(vm.jobApplication.feedback) && vm.jobApplication.feedback.length>200) {
                alert("反馈备注须在200字符以内");
                return;
            }
            //同一用户同一门店只能入职一个岗位
            var flag = true;
            if(vm.jobApplication.stateFeedback==4) {
                $.ajaxSettings.async = false;
                $.get(baseURL + "jobApplication/jobapplication/check/" + vm.jobApplication.id, function (r) {
                    flag = r.check;
                });
                $.ajaxSettings.async = true;
            }
            if (!flag) {
                alert("该用户已经入职门店的其他招聘岗位");
                return;
            }
            confirm('确定提交反馈？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "jobApplication/jobapplication/feedback",
                    contentType: "application/json",
                    data: JSON.stringify(vm.jobApplication),
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function (index) {
                                $('#feedbackModal').modal('hide');
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        provincesChange: function (index, child, value) {
            var str = JSON.stringify(window.getCities(value));
            str = str.replace(/id/g, "value").replace(/shortname/g, "label");
            this.cities = JSON.parse(str);
            vm.jobApplication.expectedCity = null;   //跟换省份时清除城市的选中状态
            this.areas = null;      //清除区列表数据
        },
        cityChange: function (index, child, value) {
            var str = JSON.stringify(window.getAreas(value));
            str = str.replace(/id/g, "value").replace(/shortname/g, "label");
            this.areas = JSON.parse(str);
        },
        areaChange: function (index, child, value) {
            var str = JSON.stringify(window.getStreets(value));
            str = str.replace(/id/g, "value").replace(/shortname/g, "label");
            this.streets = JSON.parse(str);
        },
        query: function () {
            vm.reload();
        },
        add: function () {

        },
        update: function (event) {
            var id = getSelectedRowId("id");
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.detailList = true;
            vm.zpInfoList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        //表单验证
        validate: function () {
            /*var bl = $('form').VF();//启用验证
            if (!bl) {
                return;
            }*/
        },
        validateF: function () {
            var bl = $('#feedbackForm').VF();//启用验证
            if (!bl) {
                return;
            }
        },
        saveOrUpdate: function (event) {
            var url = vm.jobApplication.id == null ? "jobApplication/jobapplication/save" : "jobApplication/jobapplication/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.jobApplication),
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
                    url: baseURL + "jobApplication/jobapplication/delete",
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
            $.get(baseURL + "jobApplication/jobapplication/info/" + id, function (r) {
                vm.jobApplication = r.jobApplication;
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.detailList = false;
            vm.zpInfoList = false;
            vm.title = "";
            //刷新 如需条件查询common.js
            $("#table").BTF5(vm.params);
            $("form").RF();
            //清除选中状态
            let $checkbox2 = $("input[name='configId2']");
            if ($checkbox2.length > 0) {
                $.each($checkbox2, function (idx, item) {
                    $(this).prop('checked', false);
                });
            }
        },
        showTableList: function (event) {
            vm.showList = true;
            vm.detailList = false;
            vm.zpInfoList = false;
            vm.title = "";
            //刷新 如需条件查询common.js
            // $("#table").BTF5(vm.params);
            $("form").RF();
            //清除选中状态
            let $checkbox2 = $("input[name='configId2']");
            if ($checkbox2.length > 0) {
                $.each($checkbox2, function (idx, item) {
                    $(this).prop('checked', false);
                });
            }
        },

        exportExcel: function () {
            var gids = getSelectedRowsIds("id");
            if (gids == null) {
                $.table.exportExcel('所有', '/jobApplication/jobapplication/export/0', '应聘', vm.params);
            } else {
                vm.params.ids = gids;
                $.table.exportExcel('选中', '/jobApplication/jobapplication/export/1', '应聘', vm.params);
            }

        }
    },
    created: function () {
        var str = JSON.stringify(window.getProvinces());
        str = str.replace(/id/g, "value").replace(/shortname/g, "label");
        this.provinces = JSON.parse(str);
        var map = window.getConfigs();
        this.labels = map.labels;
        this.scales = map.scales;
        this.treatments = map.treatments;
        this.years = map.years;
        this.jobs = map.jobs;
    },
    watch: {
        "jobApplication.expectedProvince": function (value, old) {
            if(isNaN(value) || value== null|| value+"".trim() == ''){
                return;
            }
            if (value != old) {
                var str = JSON.stringify(window.getCities(value));
                str = str.replace(/id/g, "value").replace(/shortname/g, "label");
                this.cities = JSON.parse(str);
                setTimeout(function () {
                    vm.jobApplication.expectedCity = parseInt(vm.jobApplication.expectedCity);
                }, 0)
            }
        },
        "jobApplication.expectedCity": function (value, old) {
            console.log("**************************分割线***************************jobapplication.js:555"+value)
            if(isNaN(value)  ||value== null|| (value+"".trim()) == ''){
                return;
            }
            if (value != old) {
                var str = JSON.stringify(window.getAreas(value));
                str = str.replace(/id/g, "value").replace(/shortname/g, "label");
                this.areas = JSON.parse(str);
                setTimeout(function () {
                    vm.jobApplication.expectedArea = parseInt(vm.jobApplication.expectedArea);
                }, 1)
            }
        },
        "jobApplication.expectedArea": function (value, old) {
            if(isNaN(value) || value== null||value+"".trim() == ''){
                return;
            }
            if (value != old) {
                var str = JSON.stringify(window.getStreets(value));
                str = str.replace(/id/g, "value").replace(/shortname/g, "label");
                this.streets = JSON.parse(str);
                setTimeout(function () {
                    vm.jobApplication.expectedStreet = parseInt(vm.jobApplication.expectedStreet);
                }, 1)
            }
        },
    }
});