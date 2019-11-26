$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'jobApplication/jobapplication/listByPlatform',  //list2
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
            /* {
                 title: '报名类型', field: 'type', formatter: function (value, row, index) {
                 if (value == 1) {
                     return "主动报名";
                 } else if (value == 2) {
                     return "招聘报名";
                 }
             }
             },*/
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
            {title: '分配门店名称', field: 'wname'},
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


            },
            {
                title: '操作',
                field: 'id',
                /*  formatter: function (value, row, index) {
                      var str = "<a href='javascript:void(0)' class='showInfo'>" + "详情" + "</a>";
                      if (!isBlank(row.stateFeedback) && row.stateFeedback == 1) {  //招聘报名
                          str += '&nbsp;<a href="javascript:void(0)" class="feedback">' + "分配" + "</a>";
                      }
                      return str;
                  },*/

                formatter: function (value, row, index) {

                    let mes = '';

                    mes += '<button type="button" class="btn btn-warning btn-sm showInfoJob" >报名信息</button>&nbsp;';
                    mes += '<button type="button" class="btn btn-info btn-sm showInfoRecruit" >招聘信息</button>&nbsp;';

                    if (!isBlank(row.stateFeedback) && row.stateFeedback == 1 && row.distributionStatus == 0) {
                        mes += '<button type="button" class="btn btn-primary btn-sm feedback" id="btn_integralList">分配</button>&nbsp;';
                    }
                    return mes;
                },
                events: vm.events
            }
        ],
        //条件查询
        queryParams: vm.params
    });
    //表单提交
    /*$("form").FM({
        fields: vm.fields,
        success: vm.saveOrUpdate
    });*/

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

        showJobInfo: false,
        showRecruitInfo: false,
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
            belongTo: 2,    //门店
            distributionStatus: '',  //分配状态
            type: '',
            stateFeedback: ''
        },


        /*招聘信息*/
        earnestOne: 50, //平台单人保证金
        isEarnest: false,   //保证金 不显示
        feeReturnList: [],    //返费
        platform: true,
        shopRecruitment: {
            logo: '',
            labelList: []
        },
        image11: [],//轮播图
        applicationMaterials: {
            images: []
        },
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
        pois: null,   //地图搜索项

        addList: false,

        events: {
            'click .showInfoJob': function (event, value, row, index) {
                vm.showList = false;
                vm.showRecruitInfo = false;
                vm.showJobInfo = true;
                vm.getInfoJob(value);
            },
            'click .showInfoRecruit': function (event, value, row, index) {
                vm.showList = false;
                vm.showJobInfo = false;
                vm.showRecruitInfo = true;
                var id = row.shopRecruitmentId;
                vm.getInfoRecruit(id);
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
            }
        },

        ue1: UE.getEditor('myEditor1', {
            initialFrameHeight: 220,
            onready: function () {//创建一个编辑器实例
                vm.ue1.setDisabled();
            }
        }),
        ue2: UE.getEditor('myEditor2', {
            initialFrameHeight: 220,
            onready: function () {//创建一个编辑器实例
                vm.ue2.setDisabled();
            }
        }),
        ue3: UE.getEditor('myEditor3', {
            initialFrameHeight: 220,
            onready: function () {//创建一个编辑器实例
                vm.ue3.setDisabled();
            }
        }),
        ue4: UE.getEditor('myEditor4', {
            initialFrameHeight: 220,
            onready: function () {//创建一个编辑器实例
                vm.ue4.setDisabled();
            }
        }),
        ue5: UE.getEditor('myEditor5', {
            initialFrameHeight: 220,
            onready: function () {//创建一个编辑器实例
                vm.ue5.setDisabled();
            }
        }),
        categoryList: [],

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
                        message: '公众号不能为空'
                    },
                },
            }
        }
    },
    methods: {
        //查看招聘详情
        ZPInfo: function (shopRecruitmentId) {

            vm.showList = false;
            window.location.href = '/modules/auditDistribution/recruitmentAudit.html?recruitmentId=' + shopRecruitmentId;
            //    modules/recruitment/platformrecruitment.html
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
            vm.showList = false;
            vm.title = "新增";
            vm.jobApplication = {};
        },
        update: function (event) {
            var id = getSelectedRowId("id");
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfoRecruit(id)
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
        getInfoJob: function (id) {
            $.get(baseURL + "jobApplication/jobapplication/info/" + id, function (r) {
                vm.jobApplication = r.jobApplication;
            });
        },

        getInfoRecruit: function (id) {
            vm.platform = true;
            vm.addList = false;
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
            vm.ue5.setContent('');
            $.get(baseURL + "recruitment/shoprecruitment/info/" + id, function (r) {
                vm.shopRecruitment = r.shopRecruitment;
                vm.image11 = vm.shopRecruitment.banner;

                /*经纬度*/
                /*geolocation_latlng(vm.shopRecruitment.latitude,vm.shopRecruitment.longitude);*/


                //标签
                let $checkbox = $("input[name='configId']");    //编辑页
                let $checkbox2 = $("input[name='configId2']");  //详情页
                if (vm.shopRecruitment.labelList != null && vm.shopRecruitment.labelList.length > 0) {
                    vm.shopRecruitment.labelList.forEach(function (obj, index) {
                        if ($checkbox.length > 0) {
                            $.each($checkbox, function (idx, item) {
                                let cValue = item.value;
                                if (obj.configId == cValue) {
                                    $(this).prop('checked', true);
                                }
                            });
                        }
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
                    vm.step1 = vm.shopRecruitment.feeReturnList.length;
                } else {
                    vm.feeReturnList = [];
                    vm.step1 = 0;
                }
                vm.ue1.setContent(UE.utils.html(vm.shopRecruitment.jobDescription));
                vm.ue2.setContent(UE.utils.html(vm.shopRecruitment.jobRequirement));
                vm.ue3.setContent(UE.utils.html(vm.shopRecruitment.jobResponsibilities));
                vm.ue4.setContent(UE.utils.html(vm.shopRecruitment.companyBenefits));
                vm.ue5.setContent(UE.utils.html(vm.shopRecruitment.enterpriseInfo));


                //详情展示

                vm.shopRecruitment.jobDescription = vm.ue1.body.textContent;
                vm.shopRecruitment.jobRequirement = vm.ue2.body.textContent;
                vm.shopRecruitment.jobResponsibilities = vm.ue3.body.textContent;
                vm.shopRecruitment.companyBenefits = vm.ue4.body.textContent;
                vm.shopRecruitment.enterpriseInfo = vm.ue5.body.textContent;

                if (vm.shopRecruitment.famousFlag == 1) {//灵活用工
                    $(".hourlyWage").show();//时薪
                    $(".ffxx").hide();//返费
                }else {
                    $(".hourlyWage").hide();
                    $(".ffxx").show();
                }

                //申请材料
                if (r.shopRecruitment.applicationMaterials != null) {
                    vm.applicationMaterials = r.shopRecruitment.applicationMaterials
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


        reload: function (event) {
            vm.showList = true;
            vm.showRecruitInfo = false;
            vm.showJobInfo = false;
            vm.title = "";
            //刷新 如需条件查询common.js
            $("#table").BTF5(vm.params);
            $("form").RF();
        },
        showTableList: function (event) {
            vm.showList = true;
            vm.showRecruitInfo = false;
            vm.showJobInfo = false;
            vm.title = "";
            //刷新 如需条件查询common.js
            // $("#table").BTF5(vm.params);
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
    /*watch: {
        //是否缴纳保证金
        'shopRecruitment.hasEarnest': function (value) {
            if (value == 1) {   //是
                vm.isEarnest = true;
            } else if (value == 2) {    //否
                vm.isEarnest = false;
            }
        },
        "theRequest.recruitmentId": function (value, old) {
            this.title = "详情";
            setTimeout(function () {
                vm.getInfo(value);
            }, 1000)
        },
        "shopRecruitment.province": function (value, old) {
            if (value != old) {
                var str = JSON.stringify(window.getCities(value));
                str = str.replace(/id/g, "value").replace(/shortname/g, "label");
                this.cities = JSON.parse(str);
                setTimeout(function () {
                    vm.shopRecruitment.city = parseInt(vm.shopRecruitment.city);
                }, 0)
            }
        },
        "shopRecruitment.city": function (value, old) {
            if (value != old) {
                var str = JSON.stringify(window.getAreas(value));
                str = str.replace(/id/g, "value").replace(/shortname/g, "label");
                this.areas = JSON.parse(str);
                setTimeout(function () {
                    vm.shopRecruitment.area = parseInt(vm.shopRecruitment.area);
                }, 1)
            }
        }
    }*/
});