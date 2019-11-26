$(function () {

    //列表
    $("#table").BT({
        url: baseURL + 'recruitment/shoprecruitment/platFormlist',
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
            {title: '薪资待遇（元/月）', field: 'salary'},
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
            {title: '发布时间', field: 'createDate'},
            {
                title: '操作', field: 'id', formatter: function (value, row, index) {
                    return "<button class='btn btn-primary btn-sm' type='button' id='showInfo'>" + "详情" + "</button>" +
                        "&nbsp;&nbsp;&nbsp;&nbsp;<button class='btn btn-success btn-sm' type='button' id='historyList'>" + "历史清单" + "</button>";
                }, events: vm.events
            }
        ],
        //条件查询
        queryParams: vm.params
    });

    //表单提交
    $("#form").FM({
        fields: vm.fields,
        success: vm.saveOrUpdate
    })

    $("#table2").BT({
        url: baseURL + 'provide/pUploadHistory/list',//历史清单列表
        columns: [
            {checkbox: true, width: '60px'},
            {title: '供人门店', field: 'appName'},
            {title: '招聘单位', field: 'shopRecruitment.recruitmentFirm'},
            {title: '招聘标题', field: 'shopRecruitment.title'},
            {title: '成功条数', field: 'num'},
            {
                title: '用人状态', field: 'confirm', formatter: function (value, row, index) {
                    if (row.status != 3) {
                        if (value == 1) {
                            return '<span class="label label-warning">待确认</span>';
                        } else if (value == 2) {
                            return '<span class="label label-primary">已确认</span>';
                        } else {
                            return '<span class="label label-danger">拒绝</span> </<br>' + '原因：' + row.errorInfo;
                        }
                    }
                }
            },
            {title: '备注', field: 'remark'},
            {
                title: '状态', field: 'status',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-warning">待审核</span>';
                    } else if (value == 2) {
                        return '<span class="label label-primary">通过</span>';
                    } else {
                        return '<span class="label label-danger">拒绝</span>';
                    }
                }
            },
            {title: '拒绝原因', field: 'reason'},
            {title: '创建时间', field: 'createDate'},
            {
                title: '操作', events: vm.events, formatter: function (value, row, index) {
                    let mes = "<button class='btn btn-primary btn-sm' type='button' id='provideBtn'>供人列表</button>";

                    if (hasPermission('provide:pUploadHistory:audit') && row.status == '1') {

                        mes += '&nbsp;&nbsp;<button class="btn btn-success btn-sm" type="button" id="auditOk">通过</button>';
                        mes += '&nbsp;&nbsp;<button class="btn btn-danger btn-sm" type="button" id="auditNo">失败</button>';
                    }

                    return mes;
                }, align: 'center', width: "20%"
            }
        ],
    });

    $("#table3").BT({
        url: baseURL + 'provide/providePersonnel/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '招聘单位', field: 'shopTitle'},
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
                    } else {
                        return '<span class="label label-danger">拒绝</span>';
                    }
                }
            },
            {title: '拒绝原因', field: 'reason'},*/
            {title: '创建时间', field: 'createDate'},
            {
                title: '操作', events: vm.events, formatter: function (value, row, index) {
                    return "<a href='javascript:void(0)' id='provideInfo'>" + "查看" + "</a>";
                }, align: 'center', width: "30%"
            }
        ],
        //条件查询
        queryParams: {}
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        image: '',
        showList: true,
        recruitmentDetail: false,
        historyList: false,
        provideList: false,
        provideDetail: false,
        title: null,
        shopRecruitment: {
            logo: '',
            labelList: []
        },
        image11: [],
        providePersonnel: {},
        shopId: '',
        historyId: '',
        labels: null,
        scales: null,
        treatments: null,
        years: null,
        provinces: null,
        cities: null,
        areas: null,
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
        events: {
            //招聘信息
            'click #showInfo': function (event, value, row, index) {
                vm.showList = false;
                vm.recruitmentDetail = true;
                vm.getInfo(value);
            },
            //历史清单
            'click #historyList': function (event, value, row, index) {
                vm.params2.shopRecruitmentId = row.id;
                vm.getHistoryList(row.id);
            },
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
            // 审核通过
            'click #auditOk': function (event, value, row, index) {
                vm.auditOk([row.id]);
            },
            //  审核失败
            'click #auditNo': function (event, value, row, index) {
                vm.auditNo([row.id]);
            }
        },
        params: {
            hasEarnest: '',//保证金标识
            numberOfRecruitmentS: '',
            numberOfRecruitmentE: '',
        },
        params2: {
            shopRecruitmentId: '',
            beginTime: '',
            endTime: '',
            title: '',
            status: '',
            remark: '',
            cancel: 5//供人方自己取消
        },
        params3: {
            name: '',
            uploadHistoryId: '',
        },
        isFlagList: [
            {'value': 1, 'label': '有'},
            {'value': 2, 'label': '无'},
        ],
        statusList: [
            {'value': 1, 'label': '待审核'},
            {'value': 2, 'label': '通过'},
            {'value': 3, 'label': '驳回'},
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
                    },
                },
            }, sex: {
                message: '性别:1男；2女验证失败',
                validators: {
                    notEmpty: {
                        message: '性别:1男；2女不能为空'
                    },
                },
            }, age: {
                message: '年龄验证失败',
                validators: {
                    notEmpty: {
                        message: '年龄不能为空'
                    },
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
        // 审核通过
        auditOk(ids) {
            confirm('确定审批通过?', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "provide/pUploadHistory/auditOk",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        layer.close();
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#table2").BTF5(vm.params2);
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });

            });
        },
        // 审核失败
        auditNo(ids) {
            let temp = {};
            temp.ids = ids;
            layer.prompt(
                {
                    formType: 2,
                    value: '信息有误！',
                    title: '请输入审批失败内容！',
                    maxlength: 100,
                    area: ['350px', '150px'] //自定义文本域宽高
                },
                function (value, index, elem) {
                    temp.content = value;
                    $.ajax({
                        type: "POST",
                        url: baseURL + "provide/pUploadHistory/auditNo",
                        contentType: "application/json",
                        data: JSON.stringify(temp),
                        success: function (r) {
                            layer.close(index);
                            if (r.code == 0) {
                                alert('操作成功', function (index) {
                                    $("#table2").BTF5(vm.params2);
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
            vm.providePersonnel.city = null;   //跟换省份时清除城市的选中状态
        },
        cityChange: function (index, child, value) {
            var str = JSON.stringify(window.getAreas(value));
            str = str.replace(/id/g, "value").replace(/shortname/g, "label");
            this.areas = JSON.parse(str);
        },
        reset1: function () {
            vm.params = {
                isFlag: '',//保证金标识
                numberOfRecruitmentS: '',
                numberOfRecruitmentE: '',
            }
        },
        query: function () {
            vm.reload();
        },
        //重置按钮
        reset: function () {
            vm.params2 = {
                shopRecruitmentId: vm.params2.shopRecruitmentId,
                beginTime: '',
                endTime: '',
                title: '',
                status: '',
                remark: '',
                cancel: 5//供人方自己取消
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
        //招聘详情
        getInfo: function (id) {
            vm.image11 = [];//轮播图
            //清除选中状态
            let $checkbox2 = $("input[name='configId2']");
            if ($checkbox2.length > 0) {
                $.each($checkbox2, function (idx, item) {
                    $(this).prop('checked', false);
                });
            }
            vm.ue1.setContent('');
            vm.ue2.setContent('');
            vm.ue3.setContent('');
            vm.ue4.setContent('');
            $.get(baseURL + "recruitment/shoprecruitment/info/" + id, function (r) {
                vm.shopRecruitment = r.shopRecruitment;
                vm.image11 = vm.shopRecruitment.banner;
                //标签
                if (vm.shopRecruitment.labelList != null && vm.shopRecruitment.labelList.length > 0) {
                    vm.shopRecruitment.labelList.forEach(function (obj, index) {
                        if ($checkbox2.length > 0) {
                            $.each($checkbox2, function (idx, item) {
                                let cValue = item.value;
                                if (obj.configId == cValue) {
                                    $(this).prop('checked', true);
                                }
                            });
                        }
                    });
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
                }else {
                    $(".hourlyWage").hide();
                }
            });
        },
        update: function (row) {
            vm.showList = false;
            vm.historyList = false;
            vm.provideList = false;
            vm.provideDetail = true;
            vm.title = "修改供人";
            vm.getInfoProvide(row.id)
        },
        //供人详情
        getInfoProvide: function (id) {
            $.get(baseURL + "provide/providePersonnel/info/" + id, function (r) {
                vm.providePersonnel = r.providePersonnel;
            });
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
                vm.shopRecruitment.shopSuspendFlag = 1;//开启状态
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
        reload: function (event) {
            vm.showList = true;
            vm.recruitmentDetail = false;
            vm.provideDetail = false;
            vm.historyList = false;
            vm.provideList = false;
            vm.title = "平台招聘需求";
            //刷新 如需条件查询common.js
            $("#table").BTF5(vm.params);
            $("#form").RF();

        },
        showTableList: function (event) {
            vm.showList = true;
            vm.recruitmentDetail = false;
            vm.provideDetail = false;
            vm.historyList = false;
            vm.provideList = false;
            vm.title = "平台招聘需求";
            //刷新 如需条件查询common.js
            // $("#table").BTF5(vm.params);
            $("#form").RF();

        },
        //加载二级列表
        getHistoryList: function (id) {
            vm.showList = false;
            vm.provideList = false;
            vm.provideDetail = false;
            vm.historyList = true;
            vm.title = "上传历史清单";
            //刷新 如需条件查询common.js
            $("#table2").BTF5(vm.params2);
        },
        //加载三级列表
        getProvideList: function (id) {
            vm.showList = false;
            vm.historyList = false;
            vm.provideList = true;
            vm.provideDetail = false;
            vm.title = "供人列表";
            vm.providePersonnel = {};
            //刷新 如需条件查询common.js
            $("#table3").BTF5(vm.params3);
            $("#provideForm").RF();
        },
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
    watch: {
        /*"shopRecruitment.province": function (value, old) {
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
        }*/
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