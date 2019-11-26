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
        vm.theRequest.recruitmentId = theRequest.recruitmentId;
    }

    //列表
    $("#table").BT({
        url: baseURL + 'recruitment/shoprecruitment/listByPlatform', //list2
        columns: [
            {checkbox: true, width: '60px'},
            {title: '门店名称', field: 'wxName'},
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
                title: '缴纳保证金', field: 'hasEarnest', formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-success">是</span>';
                    } else {
                        return '<span class="label label-default">否</span>';
                    }
                }
            },
            {
                title: '保证金', field: 'isEarnest', formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-success">已交</span>';
                    } else {
                        return '<span class="label label-default">未交</span>';
                    }
                }
            },
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
            {
                title: '审核信息', field: 'reviewMsg', formatter: function (value, row, index) {
                    if (row.reviewState == '3') {
                        return value;
                    }
                    return null;
                }
            },
            {title: '发布时间', field: 'createDate'},
            {
                title: '操作', field: 'id', width: "15%", formatter: function (value, row, index) {

                    var mes = "<button type='button' class='btn-primary btn btn-sm showInfo'>" + "详情" + "</button>&nbsp;&nbsp;";
                    if (hasPermission('recruitment:shoprecruitment:earnest')) {
                        if (row.hasEarnest == '1') {
                            mes += "<button type='button' class='btn-success btn btn-sm earnestOk'>" + "已缴保证金" + "</button>&nbsp;&nbsp;" +
                                "<button type='button' class='btn-warning btn btn-sm earnestNo'>" + "取消保证金" + "</button>"
                        } else {
                            mes += "<button type='button' class='btn-success btn btn-sm disabled'>" + "已缴保证金" + "</button>&nbsp;&nbsp;" +
                                "<button type='button' class='btn-warning btn btn-sm disabled'>" + "取消保证金" + "</button>"
                        }
                    }

                    return mes;
                }, events: vm.events
            }
        ],
        //条件查询
        queryParams: vm.params
    });
    //表单提交
    $("#formInsert").FM({
        fields: vm.fields,
        success: vm.saveOrUpdate
    });


    $("#msgModalForm").FM({
        //fields: vm.msgModelFields,
        success: vm.reviewMsgEdit
    });
    //模态框隐藏时，清空验证和数据，以及重置表单
    $('#msgModal').on('hide.bs.modal', function () {
        $('#msgModalForm').bootstrapValidator('resetForm', true);//清空验证
        // $('#msgModalForm')[0].reset();
    });

});

var vm = new Vue({
    el: '#rrapp',
    data: {
        modelReturnList: {},
        image: '',
        showList: true,
        title: null,
        shopRecruitment: {
            logo: '',
            labelList: []
        },
        image11: [],//轮播图
        theRequest: {
            recruitmentId: null
        },
        labels: null,
        scales: null,
        treatments: null,
        years: null,
        provinces: null,
        cities: null,
        areas: null,
        addList: false,
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
                vm.getInfo(value);
            },
            'click .earnestOk': function (event, value, row, index) {
                vm.earnest(row.id, 1);
            },
            'click .earnestNo': function (event, value, row, index) {
                vm.earnest(row.id, 2);
            }
        },
        params: {
            belongTo: 2,    //平台
            delFlag: 2,
            recruitmentFirm: '',
            title: '',
            salary: '',
            platformSuspendFlag: '',
            reviewState: ''
        },
        feeReturnList: [],    //返费
        step1: 0,    //返费 子项个数
        isEarnest: false,   //保证金 不显示
        earnestOne: 50, //平台单人保证金
        platform: true,
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
        pois: null,   //地图搜索项

        msgModelFields: {
            reviewMsg: {
                validators: {
                    notEmpty: {
                        message: '审核信息不能为空'
                    },
                    stringLength: {
                        max: 100,
                        message: '最多不能超过100个字'
                    }
                },
            }
        }
    },
    methods: {
        // 保证金
        earnest(id, type) {
            // 已缴纳保证金
            let tt = {
                id: id,
                type: type
            };
            confirm('确定要修改保证金？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "recruitment/shoprecruitment/earnest",
                    contentType: "application/json",
                    data: JSON.stringify(tt),
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
        validateTJ: function () {
            var bl = $('#msgModalForm').VF();//启用验证
            if (!bl) {
                return;
            }
        },

        reviewMsgEdit: function () {
            $.ajax({
                type: "POST",
                url: baseURL + "recruitment/shoprecruitment/toExamineFailedPl",
                contentType: "application/json",
                data: JSON.stringify(vm.modelReturnList),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            $('#msgModal').modal('hide');
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },

        addFeeReturn: function () {
            if (vm.step1 == 5) {
                alert("最多添加5个返费等级");
                return;
            }
            vm.feeReturnList.push({
                days: '',
                reward: ''
            });
            vm.step1 = vm.step1 + 1;
        },
        remove: function (index) {
            vm.feeReturnList.splice(index, 1);
            vm.step1 = vm.step1 - 1;
        },
        start: function (event) {
            var ids = getSelectedRowsId("id");
            if (ids == null) {
                return;
            }

            confirm('确定要开启选中的招聘信息？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "recruitment/shoprecruitment/start",
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

            confirm('确定要暂停选中的招聘信息？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "recruitment/shoprecruitment/end",
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

        //审核成功
        toExamineSuccessPl: function (event) {
            var ids = getSelectedRowsId("id");
            if (ids == null) {
                return;
            }

            confirm('确定要审核通过选中的招聘信息？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "recruitment/shoprecruitment/toExamineSuccessPl",
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
        //

        //审核失败
        toExamineFailedPl: function (event) {
            var ids = getSelectedRowsId("id");
            if (ids == null) {
                return;
            }
            /*vm.modelReturnList = {
                ids:[] ,
                review_state:''
            };*/
            vm.modelReturnList.ids = ids;
            $('#msgModal').modal('show');

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
            vm.shopRecruitment = {
                shopSuspendFlag: 1,
                salary: vm.treatments[0].configId,
                enterpriseSize: vm.scales[0].configId,
                enterpriseAge: vm.years[0].configId,
                logo: ''
            };
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
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        //表单验证
        validate: function () {
            var bl = $('formInsert').VF();//启用验证
            if (!bl) {
                return;
            }
        },
        saveOrUpdate: function (event) {
            //单选按钮状态
            var recommendFlag = document.getElementById("recommendFlag");
            if (recommendFlag.checked) {
                vm.shopRecruitment.recommendFlag = 1;
            } else {
                vm.shopRecruitment.recommendFlag = null;
            }
            var famousFlag = document.getElementById("famousFlag");
            if (famousFlag.checked) {
                vm.shopRecruitment.famousFlag = 1;
            } else {
                vm.shopRecruitment.famousFlag = null;
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
            //返费
            if (vm.feeReturnList != null && vm.feeReturnList.length > 0) {
                for (let obj of vm.feeReturnList) {
                    if (!/(^[1-9](\d{0,8})$)/.test(obj.days)) {
                        alert("天数请输入正整数");
                        return;
                    }
                    if (!/(^[1-9](\d{1,8})?(\.\d{1,2})?$)|(^\d\.\d{1,2}$)/.test(obj.reward)) {
                        alert("返费奖励为整数最多9位，小数最多2位的正数");
                        return;
                    }
                }
                vm.shopRecruitment.feeReturnList = vm.feeReturnList;
            }
            this.shopRecruitment.jobDescription = UE.utils.unhtml(this.ue1.getContent());
            this.shopRecruitment.jobRequirement = UE.utils.unhtml(this.ue2.getContent());
            this.shopRecruitment.jobResponsibilities = UE.utils.unhtml(this.ue3.getContent());
            var imgReg = /img.*?(?:>|\/)/gi;
            var arr1 = this.shopRecruitment.jobDescription.match(imgReg);//筛选出图片个数
            var arr2 = this.shopRecruitment.jobRequirement.match(imgReg);//筛选出图片个数
            var arr3 = this.shopRecruitment.jobResponsibilities.match(imgReg);//筛选出图片个数
            if ((arr1 != null && arr1.length > 5) || (arr2 != null && arr2.length > 5) || (arr3 != null && arr3.length > 5)) {
                alert("富文本仅允许插入5张以内的图片");
                return
            }

            if (isBlank(vm.shopRecruitment.longitude) || isBlank(vm.shopRecruitment.latitude)) {
                alert("请选择地理位置");
                return
            }
            var url = vm.shopRecruitment.id == null ? "recruitment/shoprecruitment/save" : "recruitment/shoprecruitment/update";
            if (vm.shopRecruitment.id == null) {
                vm.shopRecruitment.platformSuspendFlag = 1;//开启状态
                vm.shopRecruitment.belongTo = 2;//归属平台
            }

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
            vm.platform = true;
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
            vm.title = "";
            //刷新 如需条件查询common.js
            $("#table").BTF5(vm.params);
            $("form").RF();
        },
        showTableList: function (event) {
            vm.showList = true;
            vm.title = "";
            //刷新 如需条件查询common.js
            // $("#table").BTF5(vm.params);
            $("form").RF();
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