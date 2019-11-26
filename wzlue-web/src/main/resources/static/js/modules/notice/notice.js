$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'notice/notice/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '内容', field: 'content'},
            {title: '创建时间', field: 'createDate'},
            {
                title: '操作', field: 'id', formatter: function (value, row, index) {
                    return "<a id='showInfo'>" + "详情" + "</a>";
                }, events: vm.events
            }
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
        showList: true,
        detailList: false,
        zpList: false,
        grList: false,
        title: null,
        notice: {},
        //供人信息
        provideStaff: {},
        //招聘信息
        shopRecruitment: {
            logo: '',
            labelList: []
        },
        image11: [],//轮播图
        feeReturnList: [],    //返费
        labels: null,   //标签
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
            'click #showInfo': function (event, value, row, index) {
                vm.showList = false;
                vm.detailList = true;
                vm.zpList = false;
                vm.grList = false;
                vm.title = '详情';
                vm.getInfo(value);
            }
        },
        //验证字段
        fields: {
            belongTo: {
                message: '归属：1门店；2平台验证失败',
                validators: {
                    notEmpty: {
                        message: '归属：1门店；2平台不能为空'
                    },
                },
            }, type: {
                message: '分类：1供人成交通知验证失败',
                validators: {
                    notEmpty: {
                        message: '分类：1供人成交通知不能为空'
                    },
                },
            }, demandAppId: {
                message: '用人门店验证失败',
                validators: {
                    notEmpty: {
                        message: '用人门店不能为空'
                    },
                },
            }, supplyAppId: {
                message: '供人门店验证失败',
                validators: {
                    notEmpty: {
                        message: '供人门店不能为空'
                    },
                },
            }, shopRecruitmentId: {
                message: '招聘id验证失败',
                validators: {
                    notEmpty: {
                        message: '招聘id不能为空'
                    },
                },
            }, provideStaffId: {
                message: '供人id验证失败',
                validators: {
                    notEmpty: {
                        message: '供人id不能为空'
                    },
                },
            }, content: {
                message: '内容验证失败',
                validators: {
                    notEmpty: {
                        message: '内容不能为空'
                    },
                },
            }, createDate: {
                message: '创建时间验证失败',
                validators: {
                    notEmpty: {
                        message: '创建时间不能为空'
                    },
                },
            }
        }
    },
    methods: {
        //招聘详情
        showZPInfo: function (id) {
            vm.showList = false;
            vm.detailList = false;
            vm.zpList = true;
            vm.grList = false;
            vm.title = '招聘信息';
            vm.getZPInfo(id);
        },
        //供人详情
        showGRInfo: function (id) {
            vm.showList = false;
            vm.detailList = false;
            vm.zpList = false;
            vm.grList = true;
            vm.title = '供人信息';
            vm.getGRInfo(id);
        },
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.notice = {};
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
            var bl = $('form').VF();//启用验证
            if (!bl) {
                return;
            }
        },
        saveOrUpdate: function (event) {
            var url = vm.notice.id == null ? "notice/notice/save" : "notice/notice/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.notice),
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
                    url: baseURL + "notice/notice/delete",
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
            $.get(baseURL + "notice/notice/info/" + id, function (r) {
                vm.notice = r.notice;
            });
        },
        getZPInfo: function (id) {
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
                let $checkbox2 = $("input[name='configId2']");  //详情页
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
        getGRInfo: function (id) {
            $.get(baseURL + "provideStaff/providestaff/info/" + id, function (r) {
                vm.provideStaff = r.provideStaff;
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.detailList = false;
            vm.zpList = false;
            vm.grList = false;
            vm.title = "";
            //刷新 如需条件查询common.js
            $("#table").BTF5();
        }
    },
    created: function () {
        var map = window.getConfigs();
        this.labels = map.labels;
    },
});