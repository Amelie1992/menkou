$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'recruitment/shoprecruitment/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '归属门店', field: 'wxName'},
            {title: '招聘单位', field: 'recruitmentFirm'},
            {
                title: '企业LOGO', field: 'logo', formatter: function (value, row, index) {
                    var str = "";
                    if (!isBlank(value)) {
                        str = '<div class="row" style="white-space:nowrap;overflow:hidden;text-overflow:ellipsis;">' +
                            '<div class="col-sm-4">' +
                            '<img class="thumbnail" style="background-color:#f9f9f9" src="" load-src="' + value +
                            '" width="100px" height="80px" alt="企业logo" onerror="vm.imgExists(this)">' +
                            '</div>' +
                            '</div>';
                    }
                    return str;

                }
            },
            {title: '招聘标题', field: 'title'},
            {title: '薪资待遇（元/月）', field: 'salaryStr'},
            {title: '招聘人数 （人）', field: 'numberOfRecruitment'},
            {title: '公司地址', field: 'position'},
           /* {
                title: '推荐岗位', field: 'recommendFlag', formatter: function (value, row, index) {
                    if (value == 1) {
                        return "√";
                    } else {
                        return "";
                    }
                }
            },
            {
                title: '灵活用工', field: 'famousFlag', formatter: function (value, row, index) {
                    if (value == 1) {
                        return "√";
                    } else {
                        return "";
                    }
                }
            },*/
            {
                title: '开启状态', field: 'platformSuspendFlag', formatter: function (value, row, index) {
                    if (value == 1) {
                        return "开启";
                    } else if (value == 2) {
                        return "暂停";
                    }
                }
            },
            {
                title: '审核状态', field: 'reviewState', formatter: function (value, row, index) {
                    if (value == 1) {
                        return "待审核";
                    } else if (value == 2) {
                        return "通过";
                    } else if (value == 3) {
                        return "不通过";
                    }
                }
            },
            {title: '审核信息', field: 'reviewMsg'},
            {title: '发布时间', field: 'createDate'},
            {
                title: '操作', field: 'id', formatter: function (value, row, index) {
                    var str = "<a href='javascript:void(0)' class='showInfo'>" + "详情" + "</a>";
                    if (!isBlank(row.reviewState) && row.reviewState == 2) {  //平台招聘审核通过
                        /* str += '&nbsp;&nbsp;&nbsp;&nbsp;<a id="applyList">' + "报名列表" + "</a>";*/
                        str += '&nbsp;&nbsp;&nbsp;&nbsp;<a id="historyList">' + "供人列表" + "</a>";
                    }
                    return str;
                }, events: vm.events
            }
        ],
        //条件查询
        queryParams: vm.params
    });

    $("#table2").BT({
        url: baseURL + 'provide/pUploadHistory/list2',//供人列表
        columns: [
            {checkbox: true, width: '60px'},
            {title: '供人单位', field: 'appName'},
            {title: '成功条数', field: 'num'},
            {title: '备注', field: 'remark'},
            {title: '供人时间', field: 'createDate'},
            {
                title: '确认状态', field: 'confirm',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-warning">待确认</span>';
                    } else if (value == 2) {
                        return '<span class="label label-primary">已确认</span>';
                    } else if (value == 3) {
                        return '<span class="label label-danger">已拒绝</span>';
                    }
                }
            },
            {title: '拒绝原因', field: 'errorInfo'},
            {
                title: '操作', events: vm.events, formatter: function (value, row, index) {
                    return "<a href='javascript:void(0)' id='provideBtn'>" + "供人清单" + "</a>";
                }, align: 'center', width: "30%"
            }
        ],
    });

    $("#table3").BT({
        url: baseURL + 'provide/providePersonnel/list2',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '姓名', field: 'name'},
            {
                title: '性别', field: 'sex',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-warning">男</span>';
                    } else if (value == 2) {
                        return '<span class="label label-primary">女</span>';
                    }
                }
            },
            {title: '年龄', field: 'age'},
            {title: '省', field: 'provinceStr'},
            {title: '市', field: 'cityStr'},
            /*{
                title: '状态', field: 'status',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-warning">待审核</span>';
                    } else if (value == 2) {
                        return '<span class="label label-primary">通过</span>';
                    } else if (value == 3) {
                        return '<span class="label label-danger">拒绝</span>';
                    }
                }
            },
            {title: '拒绝原因', field: 'reason'},*/
            {title: '创建时间', field: 'createDate'},
            {
                title: '操作', events: vm.events, formatter: function (value, row, index) {
                    return "<a href='javascript:void(0)' id='provideInfo'>" + "编辑" + "</a>";
                }, align: 'center', width: "30%"
            }
        ],
    });

    $("#table4").BT({
        url: baseURL + 'jobApplication/jobapplication/list2',
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
            /*{
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
            /* {title: '分配门店名称', field: 'wname'},
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
                title: '操作', field: 'id', formatter: function (value, row, index) {
                    var str = "<a href='javascript:void(0)' id='applyInfo'>" + "详情" + "</a>";
                    /*if (!isBlank(row.type) && row.type == 2) {  //招聘报名*/
                    str += '&nbsp;&nbsp;&nbsp;&nbsp;<a id="feedback">' + "反馈" + "</a>";
                    /*}*/
                    return str;
                }, events: vm.events
            }
        ],
    });


    //平台招聘信息修改
    $("#recruitmentForm").FM({
        fields: vm.fields,
        success: vm.saveOrUpdate
    })
    //供人详情提交
    $("#provideForm").FM({
        fields: vm.provideFields,
        success: vm.saveOrUpdateProvide
    })
    //报名反馈
    $("#feedbackForm").FM({
        fields: vm.feedbackFields,
        success: vm.feedback
    })
    //
    $("#refuseForm").FM({
        fields: vm.refuseFields,
        success: vm.tjRefuse
    })
    //模态框隐藏时，清空验证和数据，以及重置表单
    $('#feedbackModal').on('hide.bs.modal', function () {
        $('#feedbackForm').bootstrapValidator('resetForm', true);//清空验证
        // $('#feedbackForm')[0].reset();
    })
    $('#refuseModal').on('hide.bs.modal', function () {
        $('#refuseForm').bootstrapValidator('resetForm', true);//清空验证
        // $('#feedbackForm')[0].reset();
    })
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        image: '',
        showList: true,
        updateList: false,
        zpDetail: false,
        applyList: false,
        applyDetail: false,
        historyList: false,
        provideList: false,
        provideDetail: false,
        title: null,
        updateF: true,  //返费等级可修改
        shopRecruitment: {
            logo: '',
            labelList: []
        },
        labels: null,
        treatments: null,
        /*scales: null,
        years: null,*/
        provinces: null,
        cities: null,
        areas: null,
        streets: null,
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
        ue5: UE.getEditor('myEditor5', {
            initialFrameHeight: 220,
        }),
        events: {
            //查看详情
            'click .showInfo': function (event, value, row, index) {
                vm.title = "详情";
                vm.showList = false;
                vm.updateList = false;
                vm.zpDetail = true;
                vm.applyList = false;
                vm.applyDetail = false;
                vm.historyList = false;
                vm.provideList = false;
                vm.provideDetail = false;
                vm.getInfo(value);
            },
            //二级    报名列表
            'click #applyList': function (event, value, row, index) {
                vm.params4.shopRecruitmentId = row.id;
                vm.getApplyList(row.id);
            },
            //报名详情
            'click #applyInfo': function (event, value, row, index) {
                vm.showList = false;
                vm.updateList = false;
                vm.zpDetail = false;
                vm.applyList = false;
                vm.applyDetail = true;
                vm.historyList = false;
                vm.provideList = false;
                vm.provideDetail = false;
                vm.title = "报名详情";
                vm.getApplyInfo(value);
            },
            //报名反馈
            'click #feedback': function (event, value, row, index) {
                vm.jobApplication = {
                    id: value,
                    stateFeedback: row.stateFeedback,
                    feedback: ''
                };
                $('#feedbackModal').modal('show');
            },
            //二级    供人列表
            'click #historyList': function (event, value, row, index) {
                vm.params2.shopRecruitmentId = row.id;
                vm.getHistoryList(row.id);
            },
            //三级    供人清单
            'click #provideBtn': function (event, value, row, index) {
                vm.params3.uploadHistoryId = row.id;
                vm.getProvideList(row.id);
            },
            //供人编辑
            'click #provideInfo': function (event, value, row, index) {
                vm.edit(row);
            },
        },
        params: {
            belongTo: 2,    //平台招聘
            delFlag: 2,
            recruitmentFirm: '',
            title: '',
            salary: '',
            platformSuspendFlag: '',
            reviewState: ''
        },
        params2: {
            shopRecruitmentId: '',
            status: 2,   //平台审核通过的供人列表
            beginTime: '',
            endTime: ''
        },
        params3: {
            uploadHistoryId: '',
            name: ''
        },
        params4: {
            shopRecruitmentId: '',
            stateFeedback: '',  //状态
            type: 2,    //招聘报名
            delFlag: 2,
            belongTo: 1,    //平台分配给门店的
            distributionStatus: 1   //已分配
        },
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
        image11: [],    //轮播图
        pois: null,   //地图搜索项
        //报名详情
        jobApplication: {},
        //供人详情
        providePersonnel: {},
        //供人列表 拒绝参数
        refuseMap: {},
        feeReturnList: [],    //返费
        step1: 0,    //返费 子项个数
        //验证字段
        fields: {
            recruitmentFirm: {
                validators: {
                    notEmpty: {
                        message: '招聘单位不能为空'
                    }, stringLength: {
                        max: 20,
                        message: '招聘单位名称长度在20字符以内'
                    }
                }
            }, title: {
                validators: {
                    notEmpty: {
                        message: '招聘标题不能为空'
                    }, stringLength: {
                        max: 15,
                        message: '招聘标题长度在15字符以内'
                    }
                },
            }, salary: {
                validators: {
                    notEmpty: {
                        message: '薪资待遇不能为空'
                    }, regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '薪资待遇为小于6位的正整数'
                    }
                },
            }, hourlyWage: {
                validators: {
                    notEmpty: {
                        message: '时薪不能为空'
                    }, regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '时薪为小于6位的正整数'
                    }
                },
            }, province: {
                message: '省验证失败',
                validators: {
                    notEmpty: {
                        message: '省不能为空'
                    },
                },
            },


            workHours: {
                message: '每日工时验证失败',
                validators: {
                    regexp: {
                        regexp: /^[1-9]\d{0,2}$/,
                        message: '每日工时为小于4位的正整数',
                    }
                },
            },
            city: {
                message: '市验证失败',
                validators: {
                    notEmpty: {
                        message: '市不能为空'
                    },
                },
            }, area: {
                message: '区验证失败',
                validators: {
                    notEmpty: {
                        message: '区不能为空'
                    },
                },
            }, street: {
                message: '街道验证失败',
                validators: {
                   /* notEmpty: {
                        message: '街道不能为空'
                    },*/
                },
            },longitude: {
                validators: {
                    notEmpty: {
                        message: '经度不能为空'
                    },
                },
            }, latitude: {
                validators: {
                    notEmpty: {
                        message: '纬度不能为空'
                    },
                },
            }, position: {
                validators: {
                    notEmpty: {
                        message: '详细地址不能为空'
                    }, stringLength: {
                        max: 50,
                        message: '详细地址长度在50字符以内'
                    }
                },
            }, enterpriseSize: {
                validators: {
                    notEmpty: {
                        message: '企业规模不能为空'
                    }, regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '企业规模为小于6位的正整数'
                    }
                },
            }, enterpriseAge: {
                validators: {
                    notEmpty: {
                        message: '企业年限不能为空'
                    }, regexp: {
                        regexp: /^[1-9]\d{0,2}$/,
                        message: '企业年限为小于4位的正整数'
                    }
                },
            }, numberOfRecruitment: {
                validators: {
                    /* notEmpty: {
                         message: '招聘人数不能为空'
                     },*/regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '招聘人数为小于6位的正整数'
                    }
                },
            }, people: {
                validators: {
                    notEmpty: {
                        message: '参保人数不能为空'
                    }, regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '参保人数为小于6位的正整数'
                    }
                }
            }, phone: {
                validators: {
                    notEmpty: {
                        message: '联系电话不能为空'
                    }, regexp: {
                        regexp: /^1[345678]\d{9}$/,
                        message: '请输入正确的手机号码'
                    }
                }
            }
        },

        provideFields: {},
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
        },
        refuseFields: {
            errorInfo: {
                validators: {
                    notEmpty: {
                        message: '拒绝原因不能为空'
                    },stringLength: {
                        max: '100',
                        message: '拒绝原因长度在100字符以内'
                    }
                },
            }
        },

    },
    methods: {
        //获取地图定位信息
        getLocation: function () {

        },
        //地图定位
        locate: function () {
            $("#mapModel").modal("show");
        },

        addFeeReturn: function () {

            if (vm.returnType == null || vm.returnType == '') {
                alert("请选择返费方式");
                return;
            }

            if (vm.returnType == 1 && vm.step1 == 5) {
                alert("最多添加5个返费等级");
                return;
            }
            if (vm.returnType == 2 && vm.step1 == 1) {
                alert("最多添加1个返费等级");
                return;
            }
            if (vm.returnType == 1) {
                vm.feeReturnList.push({
                    days: '',
                    reward: ''
                });
            }
            if (vm.returnType == 2) {
                vm.feeReturnList.push({
                    days: '1',
                    reward: ''
                });
            }

            vm.step1 = vm.step1 + 1;
        },
        remove: function (index) {
            vm.feeReturnList.splice(index, 1);
            vm.step1 = vm.step1 - 1;
        },
        removeAll: function () {
            vm.step1 = 0;
            vm.feeReturnList = [];

        },


        start: function (event) {
            var ids = getSelectedRowsId("id");
            if (ids == null) {
                return;
            }
            var belongto = 2;//平台
            confirm('确定要开启选中的招聘信息？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "recruitment/shoprecruitment/start/" + belongto,
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
        end: function (event) {
            var ids = getSelectedRowsId("id");
            if (ids == null) {
                return;
            }
            var belongto = 2;//平台
            confirm('确定要暂停选中的招聘信息？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "recruitment/shoprecruitment/end/" + belongto,
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
        imgExists: function (e) {
            //默认图片
            var $e = $(e);
            var imgUrl = $e.attr('load-src');
            var flag = window.validateImage(imgUrl);
            if (flag) {
                e.src = imgUrl;
                return;
            }
            var errrorUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1537520261126&di=46f08158404f99cabbc5f31dee5f30aa&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F016fde5687ebb76ac7251bb6439d05.png%402o.jpg";
            flag = window.validateImage(errrorUrl);
            if (flag) {
                e.src = errrorUrl;
                return;
            }
            e.src = '';
        },
        provincesChange: function (index, child, value) {
            var str = JSON.stringify(window.getCities(value));
            str = str.replace(/id/g, "value").replace(/shortname/g, "label");
            this.cities = JSON.parse(str);
            vm.shopRecruitment.city = null;   //跟换省份时清除城市的选中状态
            this.areas = null;      //清除区列表数据
            this.streets = null;
        },
        cityChange: function (index, child, value) {
            var str = JSON.stringify(window.getAreas(value));
            str = str.replace(/id/g, "value").replace(/shortname/g, "label");
            this.areas = JSON.parse(str);
            vm.shopRecruitment.area = null;
            this.streets = null;
        },
        areaChange: function (index, child, value) {
            var str = JSON.stringify(window.getStreets(value));
            str = str.replace(/id/g, "value").replace(/shortname/g, "label");
            this.street = JSON.parse(str);
            vm.shopRecruitment.street = null;
        },
        query: function () {
            vm.reload();
        },
        query2: function () {
            vm.params2.beginTime = $('#beginTime').val();
            vm.params2.endTime = $('#endTime').val();
            vm.getHistoryList();
        },
        query3: function () {
            vm.getProvideList();
        },
        query4: function () {
            vm.getApplyList();
        },
        //重置按钮
        reset: function () {
            /*vm.params2 = {
                shopRecruitmentId:'',
                beginTime: '',
                endTime: '',
                title:'',
                status:'',
                remark:''
            }*/
            vm.params2.beginTime = '';
            vm.params2.endTime = '';
        },
        //确认供人列表
        confirm: function () {
            /*var ids = getSelectedRowsId("id");*/
            var grid = $('#table2').bootstrapTable('getSelections');
            if (!grid.length) {
                alert("请选择一条记录");
                return;
            }
            var ids = [];
            var repeat = [];
            $.each(grid, function (idx, item) {
                ids[idx] = item.id;
                if (item.confirm == 1) { //待确认
                    // repeat[idx] = item.id;
                    repeat.push(item.id);
                }
            });
            if (grid.length != repeat.length) {
                alert("请勾选待确认状态下的供人列表");
                return;
            }
            vm.refuseMap = {
                confirm: 2,//确认
                ids: ids
            };
            confirm('确定要确认选中的供人列表？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "provide/pUploadHistory/confirm",
                    contentType: "application/json",
                    data: JSON.stringify(vm.refuseMap),
                    success: function (r) {
                        if (r.code == 0) {
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
        //拒绝供认列表
        refuse: function () {
            var grid = $('#table2').bootstrapTable('getSelections');
            if (!grid.length) {
                alert("请选择一条记录");
                return;
            }
            var ids = [];
            var repeat = [];
            $.each(grid, function (idx, item) {
                ids[idx] = item.id;
                if (item.confirm == 1) { //待确认
                    // repeat[idx] = item.id;
                    repeat.push(item.id);
                }
            });
            if (grid.length != repeat.length) {
                alert("请勾选待确认状态下的供人列表");
                return;
            }
            vm.refuseMap = {
                confirm: 3,//拒绝
                ids: ids
            };
            $('#refuseModal').modal('show');
        },
        tjRefuse: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "provide/pUploadHistory/confirm",
                contentType: "application/json",
                data: JSON.stringify(vm.refuseMap),
                success: function (r) {
                    if (r.code == 0) {
                        alert('操作成功', function (index) {
                            $('#refuseModal').modal('hide');
                            vm.getHistoryList();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        add: function () {

        },
        update: function (event) {
            var id = getSelectedRowId("id");
            if (id == null) {
                return;
            }
            $.ajaxSettings.async = false;
            vm.getInfo(id);
            $.ajaxSettings.async = true;
            if (vm.shopRecruitment.reviewState == 2) {
                alert("已通过审核的平台招聘信息不得修改");
                return;
            }

            debugger
            if (vm.shopRecruitment.entryFlag == 1) {
                vm.updateF = false;
            }
            vm.showList = false;
            vm.updateList = true;
            vm.zpDetail = false;
            vm.applyList = false;
            vm.applyDetail = false;
            vm.historyList = false;
            vm.provideList = false;
            vm.provideDetail = false;
            vm.title = "修改";
        },
        //表单验证
        validate: function () {
            var bl = $('#recruitmentForm').VF();//启用验证
            if (!bl) {
                return;
            }
        },
        validate2: function () {
            var bl = $('#provideForm').VF();//启用验证
            if (!bl) {
                return;
            }
        },
        validateF: function () {
            var bl = $('#feedbackForm').VF();//启用验证
            if (!bl) {
                return;
            }
        },
        validateRefuse: function () {
            var bl = $('#refuseForm').VF();//启用验证
            if (!bl) {
                return;
            }
        },
        saveOrUpdate: function (event) {
            vm.shopRecruitment.feeReturnType = 1;//默认按天返费
            if (vm.shopRecruitment.feeReturnType == 2 ) {
                if (isBlank(vm.shopRecruitment.workHours)){
                    alert("每日工时不能为空");
                    return;
                }
            }

            if (vm.image11 == null || vm.image11.length == 0) {
                alert("请上传轮播图");
                return;
            }else if (vm.image11.length > 10) {
                alert("轮播图不得超过10张");
                return;
            }
            vm.shopRecruitment.banner = vm.image11;
            //单选按钮状态
            var recommendFlag = document.getElementById("recommendFlag");
            if (recommendFlag.checked) {
                vm.shopRecruitment.recommendFlag = 1;
            } else {
                vm.shopRecruitment.recommendFlag = 2;
            }
            var famousFlag = document.getElementById("famousFlag");
            if (famousFlag.checked) {
                vm.shopRecruitment.famousFlag = 1;
            } else {
                vm.shopRecruitment.famousFlag = 2;
            }
            //标签
            let $box = $("input[name='configId']:checked");
            if ($box.length > 0) {
                vm.shopRecruitment.labelList = [];
                $.each($box, function (index, item) {
                    let $this = $(this);
                    let obj = {};
                    obj.configId = item.value;
                    obj.configValue = $this.attr('text');
                    vm.shopRecruitment.labelList.push(obj);
                });
            }
            /*if (vm.shopRecruitment.labelList != null && vm.shopRecruitment.labelList.length > 5) {
                alert("标签至多5个");
                return;
            }*/


            //返费
            if (vm.feeReturnList != null && vm.feeReturnList.length > 0) {
                var arr = [];

                for (let obj of vm.feeReturnList) {
                    if (!/(^[1-9](\d{0,2})$)/.test(obj.days)) {
                        alert("天数请输入小于4位的正整数");
                        return;
                    }
                    /*if (!/(^[1-9](\d{0,4})$)/.test(obj.reward)) {
                        alert("返费奖励请输入小于6位的正整数");
                        return;
                    }*/

                    if (!/(^[0-9]{1,6}$)|(^[0-9]{1,6}[\.]{1}[0-9]{1,2}$)/.test(obj.reward)) {
                        alert("返费奖励请输入小于6位小数点最多两位的数字");
                        return;
                    }

                    arr.push(obj.days);
                }

                var sort = arr.sort();
                for (let i = 0; i < sort.length; i++) {
                    if (sort[i] == sort[i + 1]) {
                        alert("返费等级重复，请重新设置");
                        return;
                    }
                }

                vm.shopRecruitment.feeReturnList = vm.feeReturnList;
            }



            this.shopRecruitment.jobDescription = UE.utils.unhtml(this.ue1.getContent());
            this.shopRecruitment.jobRequirement = UE.utils.unhtml(this.ue2.getContent());
            this.shopRecruitment.jobResponsibilities = UE.utils.unhtml(this.ue3.getContent());
            this.shopRecruitment.companyBenefits = UE.utils.unhtml(this.ue4.getContent());
            this.shopRecruitment.enterpriseInfo = UE.utils.unhtml(this.ue5.getContent());
            var imgReg = /img.*?(?:>|\/)/gi;
            var arr1 = this.shopRecruitment.jobDescription.match(imgReg);//筛选出图片个数
            var arr2 = this.shopRecruitment.jobRequirement.match(imgReg);
            var arr3 = this.shopRecruitment.jobResponsibilities.match(imgReg);
            var arr4 = this.shopRecruitment.companyBenefits.match(imgReg);
            var arr5 = this.shopRecruitment.enterpriseInfo.match(imgReg);
            if ((arr1 != null && arr1.length > 5) || (arr2 != null && arr2.length > 5)
                || (arr3 != null && arr3.length > 5)|| (arr4 != null && arr4.length > 5) || (arr5 != null && arr5.length > 5)) {
                alert("富文本仅允许插入5张以内的图片");
                return
            }
            //富文本长度限制
            var length1 = this.ue1.getContentLength(true);
            var length2 = this.ue2.getContentLength(true);
            var length3 = this.ue3.getContentLength(true);
            var length4 = this.ue4.getContentLength(true);
            var length5 = this.ue5.getContentLength(true);
            if (length1 > 500 || length2 > 500 || length3 > 500 || length4 > 500 || length5 > 500) {
                alert("富文本最多输入500个字符");
                return;
            }

            if (length5 <= 0 ) {
                alert("企业简介不能为空");
                return;
            }

            if (isBlank(vm.shopRecruitment.logo)) {
                alert("请上传企业LOGO");
                return
            }

            if (isBlank(vm.shopRecruitment.longitude) || isBlank(vm.shopRecruitment.latitude)) {
                alert("请选择地理位置");
                return
            }

            if (isBlank(vm.shopRecruitment.hasEarnest)) {
                alert("请选择是否缴纳保证金");
                return;
            }
            if (isBlank(vm.image1.picUrl)) {
                alert("请上传营业执照");
                return;
            }
            if (vm.image7 == null || vm.image7.length == 0) {
                alert("请上传与派遣公司签署的协议");
                return;
            }
            if (vm.shopRecruitment.hasEarnest == 2 && isBlank(vm.image2.picUrl)) {
                alert("请上传劳动派遣证");
                return;
            }
            if (vm.shopRecruitment.hasEarnest == 2 && isBlank(vm.image3.picUrl)) {
                alert("请上传人力资源许可证");
                return;
            }
            if (vm.shopRecruitment.hasEarnest == 2 && (vm.image4 == null || vm.image4.length == 0)) {
                alert("请上传纳税证明");
                return;
            }
            if (vm.shopRecruitment.hasEarnest == 2 && (vm.image5 == null || vm.image5.length == 0)) {
                alert("请上传银行流水");
                return;
            }
            if (vm.shopRecruitment.hasEarnest == 2 && (vm.image6 == null || vm.image6.length == 0)) {
                alert("请上传三家大企业合作合同");
                return;
            }
            if (vm.shopRecruitment.hasEarnest == 2 && (vm.image8 == null || vm.image8.length == 0)) {
                alert("请上传办公场地租赁合同");
                return;
            }
            if (vm.shopRecruitment.hasEarnest == 2 && (vm.image9 == null || vm.image9.length == 0)) {
                alert("请上传房产证");
                return;
            }
            if (vm.shopRecruitment.hasEarnest == 2 && (vm.image10 == null || vm.image10.length == 0)) {
                alert("请上传参保人数社保局网站截图");
                return;
            }
            if ((vm.image4 != null && vm.image4.length > 10) || (vm.image5 != null && vm.image5.length > 10) || (vm.image6 != null && vm.image6.length > 10)
                || (vm.image7 != null && vm.image7.length > 10) || (vm.image8 != null && vm.image8.length > 10) || (vm.image9 != null && vm.image9.length > 10)
                || (vm.image10 != null && vm.image10.length > 10)) {
                alert("每项不得超过10张图片");
                return;
            }
            vm.applicationMaterials.images = [];
            if (!isBlank(vm.image1.picUrl)) {
                vm.applicationMaterials.images.push(vm.image1);
            }
            if (!isBlank(vm.image2.picUrl)) {
                vm.applicationMaterials.images.push(vm.image2);
            }
            if (!isBlank(vm.image3.picUrl)) {
                vm.applicationMaterials.images.push(vm.image3);
            }
            if (vm.image4 != null && vm.image4.length > 0) {
                vm.image4.forEach(function (item) {
                    item.sort = 4;
                    vm.applicationMaterials.images.push(item);
                });
            }
            if (vm.image5 != null && vm.image5.length > 0) {
                vm.image5.forEach(function (item) {
                    item.sort = 5;
                    vm.applicationMaterials.images.push(item);
                });
            }
            if (vm.image6 != null && vm.image6.length > 0) {
                vm.image6.forEach(function (item) {
                    item.sort = 6;
                    vm.applicationMaterials.images.push(item);
                });
            }
            if (vm.image7 != null && vm.image7.length > 0) {
                vm.image7.forEach(function (item) {
                    item.sort = 7;
                    vm.applicationMaterials.images.push(item);
                });
            }
            if (vm.image8 != null && vm.image8.length > 0) {
                vm.image8.forEach(function (item) {
                    item.sort = 8;
                    vm.applicationMaterials.images.push(item);
                });
            }
            if (vm.image9 != null && vm.image9.length > 0) {
                vm.image9.forEach(function (item) {
                    item.sort = 9;
                    vm.applicationMaterials.images.push(item);
                });
            }
            if (vm.image10 != null && vm.image10.length > 0) {
                vm.image10.forEach(function (item) {
                    item.sort = 10;
                    vm.applicationMaterials.images.push(item);
                });
            }

            vm.shopRecruitment.reviewState = 1; //待审核
            vm.shopRecruitment.reviewMsg = '';//审核信息
            vm.shopRecruitment.applicationMaterials = vm.applicationMaterials;

            var url = vm.shopRecruitment.id == null ? "recruitment/shoprecruitment/save" : "recruitment/shoprecruitment/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.shopRecruitment),
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
                    url: baseURL + "recruitment/shoprecruitment/delete",
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
                vm.ue5.setContent(UE.utils.html(vm.shopRecruitment.enterpriseInfo));
                //详情展示
                if (vm.zpDetail) {
                    vm.shopRecruitment.jobDescription = vm.ue1.body.textContent;
                    vm.shopRecruitment.jobRequirement = vm.ue2.body.textContent;
                    vm.shopRecruitment.jobResponsibilities = vm.ue3.body.textContent;
                    vm.shopRecruitment.companyBenefits = vm.ue4.body.textContent;
                    vm.shopRecruitment.enterpriseInfo = vm.ue5.body.textContent;

                    if (vm.shopRecruitment.famousFlag == 1) {
                        $(".hourlyWage").show();
                        $(".ffxx").hide();
                    }else {
                        $(".hourlyWage").hide();
                        $(".ffxx").show();
                    }
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
        reload: function (event) {
            vm.showList = true;
            vm.updateList = false;
            vm.zpDetail = false;
            vm.applyList = false;
            vm.applyDetail = false;
            vm.historyList = false;
            vm.provideList = false;
            vm.provideDetail = false;
            vm.title = "";
            //刷新 如需条件查询common.js
            $("#table").BTF5(vm.params);
            $("#recruitmentForm").RF();

            position.innerHTML = "";       //清空 当前点击坐标
            $("#keyword").val("南京");    //地图检索框显示的关键字
            searchService.clear();       //地图 清空当前结果在map和panel中的展现。
            initMap();

            //清除选中状态
            let $checkbox = $("input[name='configId']");
            if ($checkbox.length > 0) {
                $.each($checkbox, function (idx, item) {
                    $(this).prop('checked', false);
                });
            }
            let $checkbox2 = $("input[name='configId2']");
            if ($checkbox2.length > 0) {
                $.each($checkbox2, function (idx, item) {
                    $(this).prop('checked', false);
                });
            }
        },
        showTableList: function (event) {
            vm.showList = true;
            vm.updateList = false;
            vm.zpDetail = false;
            vm.applyList = false;
            vm.applyDetail = false;
            vm.historyList = false;
            vm.provideList = false;
            vm.provideDetail = false;
            vm.title = "";
            //刷新 如需条件查询common.js
            // $("#table").BTF5(vm.params);
            $("#recruitmentForm").RF();

            position.innerHTML = "";       //清空 当前点击坐标
            $("#keyword").val("南京");    //地图检索框显示的关键字
            searchService.clear();       //地图 清空当前结果在map和panel中的展现。
            initMap();

            //清除选中状态
            let $checkbox = $("input[name='configId']");
            if ($checkbox.length > 0) {
                $.each($checkbox, function (idx, item) {
                    $(this).prop('checked', false);
                });
            }
            let $checkbox2 = $("input[name='configId2']");
            if ($checkbox2.length > 0) {
                $.each($checkbox2, function (idx, item) {
                    $(this).prop('checked', false);
                });
            }
        },
        //加载二级列表（报名列表）
        getApplyList: function () {
            vm.showList = false;
            vm.updateList = false;
            vm.zpDetail = false;
            vm.applyList = true;
            vm.applyDetail = false;
            vm.historyList = false;
            vm.provideList = false;
            vm.provideDetail = false;
            vm.title = "报名列表";
            $("#table4").BTF5(vm.params4);
        },
        //加载二级列表（供人列表）
        getHistoryList: function (id) {
            vm.showList = false;
            vm.updateList = false;
            vm.zpDetail = false;
            vm.applyList = false;
            vm.applyDetail = false;
            vm.historyList = true;
            vm.provideList = false;
            vm.provideDetail = false;
            vm.title = "供人列表";
            //刷新 如需条件查询common.js
            $("#table2").BTF5(vm.params2);
        },
        //加载三级列表（供人清单）
        getProvideList: function (id) {
            vm.showList = false;
            vm.updateList = false;
            vm.zpDetail = false;
            vm.applyList = false;
            vm.applyDetail = false;
            vm.historyList = false;
            vm.provideList = true;
            vm.provideDetail = false;
            vm.title = "供人清单";
            // vm.providePersonnel ={}
            //刷新 如需条件查询common.js
            $("#table3").BTF5(vm.params3);
            $("#provideForm").RF();
        },
        //编辑供人详情
        edit: function (row) {
            vm.showList = false;
            vm.updateList = false;
            vm.zpDetail = false;
            vm.applyList = false;
            vm.applyDetail = false;
            vm.historyList = false;
            vm.provideList = false;
            vm.provideDetail = true;
            vm.title = "供人详情";
            vm.getInfoProvide(row.id)
        },
        //报名详情
        getApplyInfo: function (id) {
            $.get(baseURL + "jobApplication/jobapplication/info/" + id, function (r) {
                vm.jobApplication = r.jobApplication;
            });
        },
        //供人详情
        getInfoProvide: function (id) {
            $.get(baseURL + "provide/providePersonnel/info/" + id, function (r) {
                vm.providePersonnel = r.providePersonnel;
            });
        },
        //供人详情提交
        saveOrUpdateProvide: function () {
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
        //报名反馈提交
        feedback: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "jobApplication/jobapplication/feedback",
                contentType: "application/json",
                data: JSON.stringify(vm.jobApplication),
                success: function (r) {
                    if (r.code === 0) {
                        /*  alert('操作成功', function (index) {*/
                        $('#feedbackModal').modal('hide');
                        /*  vm.reload();*/
                        vm.getApplyList();
                        /*});*/
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },

    },
    created: function () {
        var str = JSON.stringify(window.getProvinces());
        str = str.replace(/id/g, "value").replace(/shortname/g, "label");
        this.provinces = JSON.parse(str);
        var map = window.getConfigs();
        this.labels = map.labels;
        this.treatments = map.treatments;
        /*  this.scales = map.scales;
          this.years = map.years;*/

        this.returnType = 1;//默认按返费
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
        //计算保证金
        'shopRecruitment.numberOfRecruitment': function (value) {
            /*if (!isBlank(value)) {
                vm.shopRecruitment.earnest = (parseFloat(value) * parseFloat(vm.earnestOne)).toFixed(2);
            }*/
            if (!isBlank(value) && !isBlank(vm.shopRecruitment.earnestOne)) {
                vm.shopRecruitment.earnest = (parseFloat(value) * parseFloat(vm.shopRecruitment.earnestOne)).toFixed(2);
            }
        },
        //计算保证金
        'shopRecruitment.earnestOne': function (value) {
            if (!isBlank(value)) {
                vm.shopRecruitment.earnest = (parseFloat(vm.shopRecruitment.numberOfRecruitment) * parseFloat(value)).toFixed(2);
            }
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
                //等于 NaN
                if (value !== value) {
                    vm.areas=null;
                }
            }
        },
        "shopRecruitment.area": function (value, old) {
            if (value != old) {
                var str = JSON.stringify(window.getStreets(value));
                str = str.replace(/id/g, "value").replace(/shortname/g, "label");
                this.streets = JSON.parse(str);
                setTimeout(function () {
                    vm.shopRecruitment.street = parseInt(vm.shopRecruitment.street);
                }, 1)
                //等于 NaN
                if (value !== value) {
                    vm.streets=null;
                }
            }
        },
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