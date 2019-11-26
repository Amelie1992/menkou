$(function () {
    //接收页面跳转
    var url = location.search; //获取url中"?"符后的字串 ('?modFlag=business&role=1')
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1); //substr()方法返回从参数值开始到结束的字符串；
        var strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = (strs[i].split("=")[1]);
        }
    }
    if (theRequest.recruitmentId != null) {
        vm.showList = false;
        vm.addList = false;
        vm.detailList = true;
        vm.theRequest.recruitmentId = theRequest.recruitmentId;
    }

    //列表
    $("#table").BT({
        url: baseURL + 'recruitment/shoprecruitment/list',
        columns: [
            {checkbox: true, width: '60px'},
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
            {
                title: '薪资待遇（元/月）', field: 'salaryStr'/*, formatter: function (value, row, index) {
                    /!* if (value == 1) {
                         return "4000以下";
                     } else if (value == 2) {
                         return "4000～6000";
                     } else if (value == 3) {
                         return "6000～8000";
                     } else if (value == 4) {
                         return "8000以上";
                     }*!/
                    var str = "";
                    vm.treatments.forEach(function (item, index) {
                        if (item.configId == value) {
                            str = item.configValue;
                        }
                    });
                    return str;
                }*/
            },
            {title: '招聘人数 （人）', field: 'numberOfRecruitment'},
            /* {title: '省', field: 'province'},
             {title: '市', field: 'city'},
             {title: '区', field: 'area'},*/
            {title: '公司地址', field: 'position'},
            /* {title: '经度', field: 'longitude'},
             {title: '纬度', field: 'latitude'},
             {title: '企业规模 （人）', field: 'enterpriseSize'},
             {title: '企业年限 （年）', field: 'enterpriseAge'},
             {title: '职位描述', field: 'jobDescription'},
             {title: '招聘要求', field: 'jobRequirement'},
             {title: '工作职责', field: 'jobResponsibilities'},
             {title: '工作时间', field: 'workTime'},
             {title: '公司福利', field: 'companyBenefits'},*/
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
                title: '状态', field: 'shopSuspendFlag', formatter: function (value, row, index) {
                    if (value == 1) {
                        return "开启";
                    } else if (value == 2) {
                        return "暂停";
                    }
                }
            },
            {title: '发布时间', field: 'createDate'},
            {
                title: '操作', field: 'id', formatter: function (value, row, index) {
                    return "<a href='javascript:void(0)' class='showInfo'>" + "详情" + "</a>";
                }, events: vm.events
            }
            /* {title: ' 应用ID', field: 'appId'},
             {title: '', field: 'createId'},
             {title: '', field: 'updateId'},
             {title: '', field: 'updateDate'}*/
        ],
        //条件查询
        queryParams: vm.params
    });
    //表单提交
    $("form").FM({
        fields: vm.fields,
        success: vm.saveOrUpdate

    });


});

var vm = new Vue({
    el: '#rrapp',
    data: {
        recommend: false,
        famous: false,
        minority: false,
        student: false,
        older: false,
        firstHand: false,
        //门店（belongTo=1）自定义岗位种类
        job_Types: null,
        image: '',
        showList: true,
        addList: false,
        detailList: false,
        platform: false,    //上平台
        title: null,
        returnType: '',
        updateF: true,  //返费等级可修改
        shopRecruitment: {
            logo: '',
            labelList: []
        },
        theRequest: {
            recruitmentId: null
        },
        labels: null,
        treatments: null,
        /* scales: null,
         years: null,*/
        provinces: null,
        cities: null,
        areas: null,
        areas2: null,
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
            'click .showInfo': function (event, value, row, index) {
                vm.showList = false;
                vm.addList = false;
                vm.detailList = true;
                vm.platform = false;
                vm.title = '详情';
                vm.getInfo(value);
            }
        },
        params: {
            belongTo: 1,    //门店
            delFlag: 2,
            recruitmentFirm: '',
            title: '',
            salary: '',
            shopSuspendFlag: ''
        },
        feeReturnList: [],    //返费
        step1: 0,    //返费 子项个数
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
        image11: [],//轮播图
        pois: null,   //地图搜索项
        trace: null,    //门店招聘转发至平台，记录原门店招聘id
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
                        message: '月薪不能为空'
                    }, /* regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '月薪为小于6位的正整数'
                    }*/
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
            }, workHours: {
                message: '每日工时验证失败',
                validators: {
                    regexp: {
                        regexp: /^[1-9]\d{0,2}$/,
                        message: '每日工时为小于4位的正整数',
                    }
                },
            }, city: {
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
            }, longitude: {
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
            }, workTime: {
                validators: {
                    stringLength: {
                        max: 500,
                        message: '工作时间长度在500字符以内'
                    }
                },
            }, /*jobDescription: {
                validators: {
                    notEmpty: {
                        message: '职位描述不能为空'
                    },
                },
            }, jobRequirement: {
                validators: {
                    notEmpty: {
                        message: '招聘要求不能为空'
                    },
                },
            }, jobResponsibilities: {
                validators: {
                    notEmpty: {
                        message: '工作职责不能为空'
                    },
                },
            },companyBenefits: {
                validators: {
                   stringLength: {
                        max: 100,
                        message: '公司福利长度在100字符以内'
                    }
                },
            }, recommendFlag: {
                validators: {
                    notEmpty: {
                        message: '推荐标记不能为空'
                    },
                },
            }, famousFlag: {
                validators: {
                    notEmpty: {
                        message: '名企标志不能为空'
                    },
                },
            }, area: {
                validators: {
                    notEmpty: {
                        message: '办公场地面积不能为空'
                    }, regexp: {
                        regexp: /(^[1-9](\d{1,8})?(\.\d{1,2})?$)|(^\d\.\d{1,2}$)/,
                        message: '办公场地面积为整数最多9位，小数最多2位的正数'
                    }
                }
            },*/ people: {
                validators: {
                    notEmpty: {
                        message: '参保人数不能为空'
                    }, regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '参保人数为小于6位的正整数'
                    }
                }
            }, entryDays: {
                validators: {
                    /* notEmpty: {
                         message: '推荐入职天数不能为空'
                     },*/ regexp: {
                        regexp: /^[1-9]\d{0,3}$/,
                        message: '推荐入职天数为小于5位的正整数'
                    }
                },
            }, bonus: {
                validators: {
                    /* notEmpty: {
                         message: '推荐奖励金不能为空'
                     },*/ regexp: {
                        regexp: /^[1-9]\d{0,4}$/,
                        message: '推荐奖励金为小于6位的正整数'
                    }
                },
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
        //上平台
        relay: function (event) {
            var grid = $('#table').bootstrapTable('getSelections'); //获取勾选的记录
            if (!grid.length) {  //发布到平台 <--- 未勾选记录
                vm.shopRecruitment = {
                    logo: '',
                    labelList: []
                };
                vm.image11 = [];
                let $checkbox = $("input[name='configId']");
                if ($checkbox.length > 0) {
                    $.each($checkbox, function (idx, item) {
                        $(this).prop('checked', false);
                    });
                }
                vm.feeReturnList = [];
                vm.ue1.setContent('');
                vm.ue2.setContent('');
                vm.ue3.setContent('');
                vm.ue4.setContent('');
                vm.ue5.setContent('');

            } else if (grid.length > 1) {   //勾选多条记录
                alert("只能选择一条记录转发至平台");
                return;
            } else {
                var id = grid[0].id;  //勾选记录的id
                //验证是否转发过
                var exist = false;
                $.ajaxSettings.async = false;
                $.get(baseURL + "recruitment/shoprecruitment/exist/" + id, function (r) {
                    if (r.result != null) {
                        exist = true;
                    }
                });
                $.ajaxSettings.async = true;
                if (exist) {
                    alert("同一条记录请勿多次转发至平台招聘");
                    return;
                }
                // $.ajaxSettings.async = false;
                vm.getInfo(id);
                // $.ajaxSettings.async = true;
                vm.trace = id;
                // vm.shopRecruitment.trace = id;  //追溯（原门店招聘id）
                /*  vm.shopRecruitment.id = null;
                  vm.shopRecruitment.platformSuspendFlag = 1;//开启状态
                  vm.shopRecruitment.belongTo = 2;//归属平台
                  vm.feeReturnList = [];
                  vm.shopRecruitment.feeReturnList = [];*/
            }
            vm.applicationMaterials = {};
            vm.image1 = {
                picUrl: '',
                sort: 1
            };
            vm.image2 = {
                picUrl: '',
                sort: 2
            };
            vm.image3 = {
                picUrl: '',
                sort: 3
            };
            vm.image4 = [];
            vm.image5 = [];
            vm.image6 = [];
            vm.image7 = [];
            vm.image8 = [];
            vm.image9 = [];
            vm.image10 = [];
            vm.showList = false;
            vm.addList = true;
            vm.detailList = false;
            vm.platform = true;
            vm.title = "上平台招聘";
        },
        start: function (event) {
            var ids = getSelectedRowsId("id");
            if (ids == null) {
                return;
            }
            var belongto = 1;//门店
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
            var belongto = 1;//门店
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
            vm.cities = JSON.parse(str);
            vm.shopRecruitment.city = null;   //跟换省份时清除城市的选中状态
            vm.areas = null;      //清除区列表数据
            vm.streets = null;
        },
        cityChange: function (index, child, value) {
            var str = JSON.stringify(window.getAreas(value));
            str = str.replace(/id/g, "value").replace(/shortname/g, "label");
            vm.areas = JSON.parse(str);
            vm.shopRecruitment.area = null;
            vm.streets = null;
        },
        areaChange: function (index, child, value) {
            var str = JSON.stringify(window.getStreets(value));
            str = str.replace(/id/g, "value").replace(/shortname/g, "label");
            vm.streets = JSON.parse(str);
            vm.shopRecruitment.street = null;
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.addList = true;
            vm.detailList = false;
            vm.platform = false;
            vm.title = "新增";
            vm.shopRecruitment = {
                shopSuspendFlag: 1,
                salary: vm.treatments[0].configId,
                /*
                 enterpriseSize: vm.scales[0].configId,
                 enterpriseAge: vm.years[0].configId,
                 */
                logo: ''
            };
            vm.image11 = [];
            let $checkbox = $("input[name='configId']");
            if ($checkbox.length > 0) {
                $.each($checkbox, function (idx, item) {
                    $(this).prop('checked', false);
                });
            }
            vm.feeReturnList = [];
            vm.ue1.setContent('');
            vm.ue2.setContent('');
            vm.ue3.setContent('');
            vm.ue4.setContent('');
            vm.ue5.setContent('');

        },
        update: function (event) {

            var id = getSelectedRowId("id");
            if (id == null) {
                return;
            }
            /*vm.getInfo(id);*/
            //该条招聘下有入职员工 返费不可修改
            $.ajaxSettings.async = false;
            /* $.get(baseURL + "jobApplication/jobapplication/entry",{shopRecruitmentId: id}, function (r) {
                 vm.updateF = r.entry;
             });*/
            vm.getInfo(id);
            $.ajaxSettings.async = true;
            if (vm.shopRecruitment.entryFlag == 1) {
                vm.updateF = false;
            }
            vm.title = "修改";
            vm.platform = false;
            vm.showList = false;
            vm.addList = true;
            vm.detailList = false;

        },
        //表单验证
        validate: function () {
            var bl = $('form').VF();//启用验证
            if (!bl) {
                alert("请完善所有信息哦");
                return;
            }
        },
        saveOrUpdate: function (event) {
            vm.shopRecruitment.feeReturnType = 1;//默认 按天返费
            if (vm.shopRecruitment.feeReturnType == 2) {
                if (isBlank(vm.shopRecruitment.workHours)) {
                    alert("每日工时不能为空");
                    return;
                }
            }

            if (vm.image11 == null || vm.image11.length == 0) {
                alert("请上传轮播图");
                return;
            } else if (vm.image11.length > 10) {
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
            var famousFlag = document.getElementById("famousFlag"); //灵活用工
            if (famousFlag.checked) {
                vm.shopRecruitment.famousFlag = 1;
                vm.feeReturnList = [];  //返费
            } else {
                vm.shopRecruitment.famousFlag = 2;
                vm.shopRecruitment.hourlyWage = "null";   //时薪
            }

            var recommendFlag2 = document.getElementById("recommendFlag2");
            if (recommendFlag2.checked) {
                vm.shopRecruitment.recommendFlag = 1;
            } else {
                vm.shopRecruitment.recommendFlag = 2;
            }
            var famousFlag2 = document.getElementById("famousFlag2");   //灵活用工
            if (famousFlag2.checked) {
                vm.shopRecruitment.famousFlag = 1;
                vm.feeReturnList = [];  //返费
            } else {
                vm.shopRecruitment.famousFlag = 2;
                vm.shopRecruitment.hourlyWage = "null";   //时薪
            }

            var minorityFlag = document.getElementById("minorityFlag");
            if (minorityFlag.checked) {
                vm.shopRecruitment.minorityFlag = 1;
            } else {
                vm.shopRecruitment.minorityFlag = 2;
            }
            var studentFlag = document.getElementById("studentFlag");
            if (studentFlag.checked) {
                vm.shopRecruitment.studentFlag = 1;
            } else {
                vm.shopRecruitment.studentFlag = 2;
            }

            var olderFlag = document.getElementById("olderFlag");
            if (olderFlag.checked) {
                vm.shopRecruitment.olderFlag = 1;
            } else {
                vm.shopRecruitment.olderFlag = 2;
            }
            var firstHandFlag = document.getElementById("firstHandFlag");
            if (firstHandFlag.checked) {
                vm.shopRecruitment.firstHandFlag = 1;
            } else {
                vm.shopRecruitment.firstHandFlag = 2;
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

            //推荐奖励金
            if ((isBlank(vm.shopRecruitment.bonus) && !isBlank(vm.shopRecruitment.entryDays))
                || (!isBlank(vm.shopRecruitment.bonus) && isBlank(vm.shopRecruitment.entryDays))) {
                alert("推荐入职天数和奖励金额，请同时配置或者不配置");
                return;
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
                || (arr3 != null && arr3.length > 5) || (arr4 != null && arr4.length > 5) || (arr5 != null && arr5.length > 5)) {
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
            if (length5 <= 0) {
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

            if (vm.platform == false && vm.shopRecruitment.id == null) {
                vm.shopRecruitment.shopSuspendFlag = 1;//开启状态
                vm.shopRecruitment.belongTo = 1;//归属门店
            }

            if (vm.platform && isBlank(vm.shopRecruitment.hasEarnest)) {
                alert("请选择是否缴纳保证金");
                return;
            }
            if (vm.platform && vm.shopRecruitment.hasEarnest == 1 && isBlank(vm.shopRecruitment.numberOfRecruitment)) {
                alert("请填写招聘人数");
                return;
            }
            if (vm.platform && vm.shopRecruitment.hasEarnest == 1 && isBlank(vm.shopRecruitment.earnestOne)) {
                alert("请填写单个保证金");
                return;
            }


            if (vm.platform && isBlank(vm.image1.picUrl)) {
                alert("请上传营业执照");
                return;
            }
            if (vm.platform && (vm.image7 == null || vm.image7.length == 0)) {
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
            if (vm.platform) {  //发布平台招聘
                vm.shopRecruitment.id = null;
                //vm.shopRecruitment.feeReturnList = [];
                vm.shopRecruitment.shopSuspendFlag = null;
                vm.shopRecruitment.platformSuspendFlag = 1;//开启状态
                vm.shopRecruitment.belongTo = 2;//归属平台
                vm.shopRecruitment.reviewState = 1; //待审核
                vm.shopRecruitment.applicationMaterials = vm.applicationMaterials;
                vm.shopRecruitment.trace = vm.trace;//追溯（原门店招聘id）
                vm.shopRecruitment.bonus = null;//平台招聘不设置推荐奖励金
                vm.shopRecruitment.entryDays = null;
            }

            if (vm.shopRecruitment.id == null && vm.shopRecruitment.hourlyWage === "null") {    //新增
                vm.shopRecruitment.hourlyWage = null;
            }
            if (vm.shopRecruitment.id != null && isBlank(vm.shopRecruitment.bonus) && isBlank(vm.shopRecruitment.entryDays)) {//修改
                vm.shopRecruitment.bonus = 0;
                vm.shopRecruitment.entryDays = 0;
            }

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
            /*vm.platform = false;
            vm.image1 = {};
            vm.image2 = {};
            vm.image3 = {};
            vm.image4 = [];
            vm.image5 = [];
            vm.image6 = [];
            vm.image7 = [];
            vm.image8 = [];
            vm.image9 = [];
            vm.image10 = [];*/
            vm.image11 = [];
            vm.ue1.setContent('');
            vm.ue2.setContent('');
            vm.ue3.setContent('');
            vm.ue4.setContent('');
            vm.ue5.setContent('');
            $.get(baseURL + "recruitment/shoprecruitment/info/" + id, function (r) {
                vm.shopRecruitment = r.shopRecruitment;
                //轮播图
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
                if (vm.detailList) {
                    vm.shopRecruitment.jobDescription = vm.ue1.body.textContent;
                    vm.shopRecruitment.jobRequirement = vm.ue2.body.textContent;
                    vm.shopRecruitment.jobResponsibilities = vm.ue3.body.textContent;
                    vm.shopRecruitment.companyBenefits = vm.ue4.body.textContent;
                    vm.shopRecruitment.enterpriseInfo = vm.ue5.body.textContent;

                    if (vm.shopRecruitment.famousFlag == 1) {
                        $(".hourlyWage").show();
                        $(".ffxx").hide();
                    } else {
                        $(".hourlyWage").hide();
                        $(".ffxx").show();
                    }

                    //岗位种类
                    var ss = "";
                    if (!isBlank(vm.shopRecruitment.recommendFlag) && (vm.shopRecruitment.recommendFlag == 1)) {
                        ss += '\xa0\xa0\xa0\xa0\xa0\xa0\xa0' + "推荐岗位";
                    }
                    if (!isBlank(vm.shopRecruitment.famousFlag) && (vm.shopRecruitment.famousFlag == 1)) {
                        ss += '\xa0\xa0\xa0\xa0\xa0\xa0\xa0' + "灵活用工";
                    }
                    if (!isBlank(vm.shopRecruitment.minorityFlag) && (vm.shopRecruitment.minorityFlag == 1)) {
                        ss += '\xa0\xa0\xa0\xa0\xa0\xa0\xa0' + "少数民族";
                    }
                    if (!isBlank(vm.shopRecruitment.studentFlag) && (vm.shopRecruitment.studentFlag == 1)) {
                        ss += '\xa0\xa0\xa0\xa0\xa0\xa0\xa0' + "学生工";
                    }
                    if (!isBlank(vm.shopRecruitment.olderFlag) && (vm.shopRecruitment.olderFlag == 1)) {
                        ss += '\xa0\xa0\xa0\xa0\xa0\xa0\xa0' + "大龄工";
                    }
                    if (!isBlank(vm.shopRecruitment.firstHandFlag) && (vm.shopRecruitment.firstHandFlag == 1)) {
                        ss += '\xa0\xa0\xa0\xa0\xa0\xa0\xa0' + "一手单";
                    }
                    vm.shopRecruitment.jobTypeStr = ss;
                }

                //申请材料
                /*  if (r.shopRecruitment.applicationMaterials != null) {
                      vm.platform = true;
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
                  }*/
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.addList = false;
            vm.detailList = false;
            vm.platform = false;
            vm.title = "";
            //刷新 如需条件查询common.js
            $("#table").BTF5(vm.params);
            $("form").RF();

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
            $("form").RF();
            vm.showList = true;
            vm.addList = false;
            vm.detailList = false;
            vm.platform = false;
            vm.title = "";

            vm.feeReturnList = [];   //返费
            vm.step1 = 0;    //返费 子项个数

            //刷新 如需条件查询common.js
            // $("#table").BTF5(vm.params);

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

        exportExcel: function () {
            var gids = getSelectedRowsIds("id");
            if (gids == null) {
                $.table.exportExcel('所有', '/recruitment/shoprecruitment/export/0', '招聘', vm.params);
            } else {
                vm.params.ids = gids;
                $.table.exportExcel('选中', '/recruitment/shoprecruitment/export/1', '招聘', vm.params);
            }

        }

    },
    created: function () {
        var str = JSON.stringify(window.getProvinces());
        str = str.replace(/id/g, "value").replace(/shortname/g, "label");
        this.provinces = JSON.parse(str);
        var map = window.getConfigs();
        this.labels = map.labels;
        this.treatments = map.treatments;
        /*   this.scales = map.scales;
           this.years = map.years;*/
        this.returnType = 1;//默认按天返费

        // this.job_Types = window.jobTypes;
        this.job_Types = JSON.parse(window.jobTypes);   //对象
        //门店自定义岗位种类
        if (null != this.job_Types && this.job_Types.length > 0) {
            // this.job_Types.forEach(function (item) {
            // })
            for (var i in this.job_Types) {
                if (this.job_Types[i].configValue === "推荐岗位") {
                    this.recommend = true;
                } else if (this.job_Types[i].configValue === "灵活用工") {
                    this.famous = true;
                } else if (this.job_Types[i].configValue === "少数民族") {
                    this.minority = true;
                } else if (this.job_Types[i].configValue === "学生工") {
                    this.student = true;
                } else if (this.job_Types[i].configValue === "大龄工") {
                    this.older = true;
                } else if (this.job_Types[i].configValue === "一手单") {
                    this.firstHand = true;
                }
            }

        }
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
                vm.cities = JSON.parse(str);
                setTimeout(function () {
                    vm.shopRecruitment.city = parseInt(vm.shopRecruitment.city);
                }, 0);
            }
        },
        "shopRecruitment.city": function (value, old) {
            if (value != old) {
                var str = JSON.stringify(window.getAreas(value));
                str = str.replace(/id/g, "value").replace(/shortname/g, "label");
                vm.areas = JSON.parse(str);
                setTimeout(function () {
                    vm.shopRecruitment.area = parseInt(vm.shopRecruitment.area);
                }, 1);

                //等于 NaN
                if (value !== value) {
                    vm.areas = null;
                }

            }
        },
        "shopRecruitment.area": function (value, old) {
            if (value != old) {
                var str = JSON.stringify(window.getStreets(value));
                str = str.replace(/id/g, "value").replace(/shortname/g, "label");
                vm.streets = JSON.parse(str);
                setTimeout(function () {
                    vm.shopRecruitment.street = parseInt(vm.shopRecruitment.street);
                }, 1);

                //等于 NaN
                if (value !== value) {
                    vm.streets = null;
                }
            }
        },
    }
});