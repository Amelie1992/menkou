$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'jobApplication/jobapplication/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '公司', field: 'recruitmentFirm'},
            {title: '招聘标题', field: 'recruitmentTitle'},
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
            /*{
                title: '报名类型', field: 'type', formatter: function (value, row, index) {
                if (value == 1) {
                    return "主动报名";
                } else if (value == 2) {
                    return "招聘报名";
                }
            }
            },*/
            /*{
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
            },*/
            {title: '报名时间', field: 'createDate'},
            /*{title: '分配门店名称', field: 'wname'},
            {
                title: '分配状态',
                field: 'distributionStatus',
                formatter: function (value, row, index) {
                    if (value == 0) {
                        return '<span class="label label-success">未分配</span>';
                    } else if (value == 1) {
                        return '<span class="label label-danger">已分配</span>';

                    }

                }


            },*/
            {
                title: '门店反馈', field: 'shopFeedback', formatter: function (value, row, index) {
                    if (value == 1) {
                        return "接收";
                    } else if (value == 2) {
                        return "拒收";
                    } else  if (value == 3) {
                        return "待反馈";
                    }
                }
            },
            {title: '反馈备注', field: 'shopRemark',formatter: function (value, row, index) {

                if(!isBlank(value)) {
                    value = value.substr(0,10);
                }


                    return value;
                },},
            {
                title: '操作', field: 'id', formatter: function (value, row, index) {
                    if (row.shopFeedback==3) {
                        var str = '<a href="javascript:void(0)" class="showInfo">详情</a>' +
                            '&nbsp;&nbsp;<a id="btn_feedback" onclick="shopFeedback(' + row.id + ')">反馈</a>';
                    } else {
                        var str = '<a href="javascript:void(0)" class="showInfo">详情</a>';
                    }
                    return str;
                },
                events: vm.events
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
//门店反馈
function shopFeedback(id){
    layer.confirm('门店反馈', {
        btn: ['接收','拒收','取消'] //按钮
    }, function(){
        /*var url ="jobApplication/jobapplication/shopFeedback";
        $.ajax({
            type: "GET",
            url: baseURL + url,
            data: {
                id:id,
                shopFeedback:1
            },
            success: function(r){
                if(r.code === 0){
                    alert('操作成功', function(){
                        layer.closeAll();
                        vm.reload();
                    });
                }else{
                    alert(r.msg);
                }
            }
        });*/
        layer.open({
            title: '反馈备注',
            type: 1,
            content: '<div><textarea id="rebutReason" rows="6" cols="20" type="text" class="form-control" placeholder="请输入反馈" maxlength="200"></textarea></div>',
            area: ['420px', '240px'], //宽高
            btn: ['确定', '取消'],
            yes: function(index, layero){
                var rebutReason = $("#rebutReason").val();
                var url ="jobApplication/jobapplication/shopFeedback";
                $.ajax({
                    type: "GET",
                    url: baseURL + url,
                    data: {
                        id:id,
                        shopFeedback:1,
                        shopRemark:rebutReason,
                    },
                    success: function(r){
                        if(r.code === 0){
                            alert('操作成功', function(){
                                layer.closeAll();
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            }
        });
    }, function(){
        layer.open({
            title: '反馈备注',
            type: 1,
            content: '<div><textarea id="rebutReason" rows="6" cols="20" type="text" class="form-control" placeholder="请输入反馈" maxlength="200"></textarea></div>',
            area: ['420px', '240px'], //宽高
            btn: ['确定', '取消'],
            yes: function(index, layero){
                var rebutReason = $("#rebutReason").val();
                var url ="jobApplication/jobapplication/shopFeedback";
                $.ajax({
                    type: "GET",
                    url: baseURL + url,
                    data: {
                        id:id,
                        shopFeedback:2,
                        shopRemark:rebutReason,
                    },
                    success: function(r){
                        if(r.code === 0){
                            alert('操作成功', function(){
                                layer.closeAll();
                                vm.reload();
                            });
                        }else{
                            alert(r.msg);
                        }
                    }
                });
            }
        });
    }, function(){
        layer.closeAll();
    });

};
var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        detailList: false,
        zpInfoList: false,
        title: null,
        jobApplication: {},
        labels: [],
        scales: [],
        treatments: [],
        years: [],
        jobs: [],
        provinces: null,
        cities: null,
        areas: null,
        params: {
            belongTo: 1,    //门店
            delFlag: 2,
            distributionStatus: 1,  //平台分配
            realName: '',
            phone: '',
            recruitmentFirm:'',
            recruitmentTitle:''
        },
        events: {
            'click .showInfo': function (event, value, row, index) {
                $.ajaxSettings.async = false;
                vm.getInfo(value);
                $.ajaxSettings.async = true;
                vm.showList = false;
                vm.detailList = true;
                vm.zpInfoList = false;
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
        categoryList: [],
        ownFlag: false, //本门店发布的平台招聘
        shopRecruitment: {},
        image11: [],
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
        isEarnest: false,   //保证金 不显示
        earnestOne: 50, //平台单人保证金
        applicationMaterials: {
            images: []
        },   //申请材料
        image1: {   //营业执照
            picUrl: '',
            sort: 1
        },
        image2: {   //劳动派遣证
            picUrl: '',
            sort: 2
        },
        image3: {   //人力资源许可证
            picUrl: '',
            sort: 3
        },
        image4: [], //纳税证明
        image5: [], //银行流水
        image6: [], //三家大企业合作合同
        image7: [], //与派遣公司签署的协议
        image8: [], //办公场地租赁合同
        image9: [], //房产证
        image10: [], //参保人数社保局网站截图


        feeReturnList: [],    //返费
        step1: 0,    //返费 子项个数
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
            /*stateFeedback: {
                appId: {
                    notEmpty: {
                        message: '门店不能为空'
                    },
                },
            }*/
            appId: {
                validators: {
                    notEmpty: {
                        message: '反馈状态不能为空'
                    },
                },
            }
        }
    },
    methods: {
        //招聘详情
        getZPInfo: function (shopRecruitmentId) {
            vm.image1 = {};
            vm.image2 = {};
            vm.image3 = {};
            vm.image4 = [];
            vm.image5 = [];
            vm.image6 = [];
            vm.image7 = [];
            vm.image8 = [];
            vm.image9 = [];
            vm.image10 = [];
            vm.image11 = [];//轮播图
            vm.ue1.setContent('');
            vm.ue2.setContent('');
            vm.ue3.setContent('');
            vm.ue4.setContent('');
            $.get(baseURL + "recruitment/shoprecruitment/info/" + shopRecruitmentId, function (r) {
                vm.shopRecruitment = r.shopRecruitment;
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
                vm.returnType = vm.shopRecruitment.feeReturnType;
                if (vm.shopRecruitment.feeReturnList != null && vm.shopRecruitment.feeReturnList.length > 0) {
                    vm.feeReturnList = vm.shopRecruitment.feeReturnList;
                    vm.step1 = vm.shopRecruitment.feeReturnList.length;
                } else {
                    vm.feeReturnList = [];
                    vm.step1 = 0;
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

                //申请材料
                if (r.shopRecruitment.applicationMaterials != null) {
                    vm.applicationMaterials = r.shopRecruitment.applicationMaterials;
                    if (vm.applicationMaterials.images != null && vm.applicationMaterials.images.length > 0) {
                        vm.applicationMaterials.images.forEach(function (item) {
                            if (item.sort == 1) {
                                vm.image1 = item;
                            } else if (item.sort == 2) {
                                vm.image2 = item;
                            } else if (item.sort == 3) {
                                vm.image3 = item;
                            } else if (item.sort == 4) {
                                vm.image4.push(item);
                            } else if (item.sort == 5) {
                                vm.image5.push(item);
                            } else if (item.sort == 6) {
                                vm.image6.push(item);
                            } else if (item.sort == 7) {
                                vm.image7.push(item);
                            } else if (item.sort == 8) {
                                vm.image8.push(item);
                            } else if (item.sort == 9) {
                                vm.image9.push(item);
                            } else if (item.sort == 10) {
                                vm.image10.push(item);
                            }
                        })
                    }
                }
            });
        },
        //查看招聘详情
        ZPInfo: function (shopRecruitmentId) {
            // window.location.href = '/modules/recruitment/platformrecruitment.html?recruitmentId=' + shopRecruitmentId;

            $.ajaxSettings.async = false;
            vm.getZPInfo(shopRecruitmentId);
            $.ajaxSettings.async = true;
            vm.showList = false;
            vm.detailList = false;
            vm.zpInfoList = true;
        },
        //分配
        feedback: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "jobApplication/jobapplication/distribution",
                contentType: "application/json",
                data: JSON.stringify(vm.jobApplication),
                success: function (r) {
                    if (r.code === 0) {
                        /*  alert('操作成功', function (index) {*/
                        $('#feedbackModal').modal('hide');
                        vm.reload();
                        /*});*/
                    } else {
                        alert(r.msg);
                    }
                }
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
        query: function () {
            vm.reload();
        },
        add: function () {

        },
        update: function (event) {

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
            $.get(baseURL + "jobApplication/jobapplication/info2/" + id, function (r) {


                vm.jobApplication = r.jobApplication;
                vm.ownFlag = r.flag;




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
        getCategory: function () {
            // 加载菜单树
            $.get(baseURL + "wechat/wxapp/selectlist", function (r) {
                vm.categoryList = r.wxAppList;
            })
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


        this.getCategory();


    },
    watch: {
        //是否缴纳保证金
        'shopRecruitment.hasEarnest': function (value) {
            if (value == 1) {   //是
                vm.isEarnest = true;
            } else if (value == 2) {    //否
                vm.isEarnest = false;
            }
        },
        "jobApplication.expectedProvince": function (value, old) {
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
            if (value != old) {
                var str = JSON.stringify(window.getAreas(value));
                str = str.replace(/id/g, "value").replace(/shortname/g, "label");
                this.areas = JSON.parse(str);
                setTimeout(function () {
                    vm.jobApplication.expectedArea = parseInt(vm.jobApplication.expectedArea);
                }, 1)
            }
        }
    }
});