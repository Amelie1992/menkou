$(function () {
    $('#thisMyBtn').on('click',function () {
        //var index=layer.load(0,{shade:[0.3]});
        var fileInput = document.getElementById("excelFile").value;
        if (fileInput==null||fileInput==""){
            alert("您未上传文件，或者您上传文件类型有误！")
            return false;
        } else {
            $("#excelFile").fileinput("upload");
        }
    });
    //初始化upload
    initUpload("excelFile", baseURL + "provide/providePersonnel/upload", {
        'xlsx': '<i class="fa fa-file-excel-o text-success"></i>',
        'xls': '<i class="fa fa-file-excel-o text-success"></i>',
    }, ["xls", "xlsx"]);
    //列表
    $("#table").BT({
        url: baseURL + 'recruitment/shoprecruitment/platFormlist',//平台不包含当前登录门店的所有审核通过的招聘信息
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
            {title: '保证金', field: 'isEarnest',formatter: function (value, row, index) {//1有0无
                    if (value == 1) {
                        return "√";
                    } else {
                        return "";
                    }
                }},
            {title: '发布时间', field: 'createDate'},
            {
                title: '操作', field: 'id', formatter: function (value, row, index) {
                    return "<a href='javascript:void(0)' class='showInfo'>" + "详情" + "</a>"+
                        /*"&nbsp;&nbsp;&nbsp;&nbsp;<a id='upload'>"+"上传excel"+"</a>"*/
                        /*"&nbsp;&nbsp;&nbsp;&nbsp;<a id='historyList'>"+"上传历史清单"+"</a>"*/
                        "&nbsp;&nbsp;&nbsp;&nbsp;<a id='provideStaff'>"+"供人"+"</a>";
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
        url: baseURL + 'provide/pUploadHistory/list',//历史清单列表--当前登录人的appId
        columns: [
            {checkbox: true, width: '60px'},
            {title: '招聘单位', field: 'shopRecruitment.recruitmentFirm'},
            {title: '招聘标题', field: 'shopRecruitment.title'},
            {title: '成功条数', field: 'num'},
            {title: '备注', field: 'remark'},
            {title: '状态', field: 'status',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-warning">待审核</span>';
                    } else if (value == 2) {
                        return '<span class="label label-primary">通过</span>';
                    } else {
                        return '<span class="label label-danger">拒绝</span>';
                    }
                }},
            {title: '拒绝原因', field: 'reason'},
            {title: '创建时间', field: 'createDate'},
            {title: '操作', events: vm.events, formatter: function (value, row, index) {
                    return "<a href='javascript:void(0)' id='provideBtn'>" + "供人列表" + "</a>";
                }, align: 'center', width: "30%"}
        ],
    });

    $("#table3").BT({
        url: baseURL + 'provide/providePersonnel/list',//appId=当前登录人的
        columns: [
            {checkbox: true, width: '60px'},
            {title: '招聘单位', field: 'shopTitle'},
            {title: '姓名', field: 'name'},
            {title: '性别', field: 'sex',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-warning">男</span>';
                    } else if (value == 2) {
                        return '<span class="label label-primary">女</span>';
                    }
                }},
            {title: '年龄', field: 'age'},
            {title: '省', field: 'provinceStr'},
            {title: '市', field: 'cityStr'},
            {title: '状态', field: 'status',
                formatter: function (value, row, index) {
                    if (value == 1) {
                        return '<span class="label label-warning">待审核</span>';
                    } else if (value == 2) {
                        return '<span class="label label-primary">通过</span>';
                    } else {
                        return '<span class="label label-danger">拒绝</span>';
                    }
                }},
            {title: '拒绝原因', field: 'reason'},
            {title: '创建时间', field: 'createDate'},
            {title: '操作', events: vm.events, formatter: function (value, row, index) {
                if (row.status===1){
                    return "<a href='javascript:void(0)' id='provideInfo'>" + "编辑" + "</a>";
                }
                }, align: 'center', width: "30%"}
        ],
        //条件查询
        queryParams: {}
    });

    //供人表单提交
    $("#provideForm").FM({
        fields: vm.fields,
        success: vm.saveOrUpdateProvide
    })
    $("#provideStaffForm").FM({
        fields: vm.provideFields,
        success: vm.saveProvide
    })

});

//导入初始化
function initUpload(ctrlName, uploadUrl, previewFileIconSettings, allowedFileExtensions, initialPreview, initialPreviewConfig) {
    var control = $('#' + ctrlName);
    control.fileinput({
        language: 'zh', //设置语言
        uploadUrl: uploadUrl, //上传的地址
        uploadAsync: true, //默认异步上传
        showCaption: true,//是否显示标题
        textEncoding: 'UTF-8',
        showUpload: false, //是否显示上传按钮
        browseClass: "btn btn-primary", //按钮样式
        allowedFileExtensions: allowedFileExtensions, //接收的文件后缀
        overwriteInitial: false,
        ifshowPreview: true,
        initialPreviewAsData: true,
        initialPreview: initialPreview,
        initialPreviewConfig: initialPreviewConfig,
        maxFileCount: 5,//最大上传文件数限制
        previewFileIcon: '<i class="glyphicon glyphicon-file"></i>',
        showPreview: true, //是否显示预览
        previewFileIconSettings: previewFileIconSettings,
        msgFileNotFound: '文件 "{name}" 未找到!',
        enctype: 'multipart/form-data',
        showCancel: false,
        showClose: false,
        uploadExtraData: function () {
            var extraValue = "test";
            return {"excelType": extraValue,"shopRecruitmentId":$("#shopRecruitmentId").val(),"remark":$("#remark").val()};
        }
    }).on('filebatchselected', function (event, files) {//选中文件事件
        if(files == null || files.length <= 0){
            return;
        }
    }).on("fileuploaded", function (event, data, previewId, index) {//文件上传成功回调事件
        layer.closeAll();
        var r = data.response;
        if(r.errorCount>0)
        {
            vm.listError=r.listError;
            vm.errorCount=r.errorCount;
            $('#myModal').modal('hide');
            var str=""
            for (var i = 0; i <r.listError.length; i++)
            {
                str+='<tr><td>'+
                    r.listError[i].name
                    +'</td><td style="color:#ff1c2b;">'+
                    r.listError[i].errorReason
                    +'</td></tr>'
            }
            var html='<div><table class="table table-striped table-bordered"><thead>' +
                '<tr><th>姓名</th><th>错误原因</th></tr>' +
                '</thead><tbody>'+ str +'</tbody></table></div>'
            layer.open({
                type: 1,
                title:'供人表上传错误集合',
                skin: 'layui-layer-rim',
                area: ['420px', '240px'],
                content: html
            });
        }
        else
        {
           alert("上传成功");
           $('#myModal').modal('hide');
           vm.reload();
        }
    }).on("upload",function () {
        console.log('uoload');
    }).on("fileuploaded",function (data,previewId,index) {
        var form = data.form, files = data.files, extra = data.extra,response = data.response, reader = data.reader;
            console.log(form)
        console.log('fileuploaded');
    }).on("filepreupload",function (data,previewId,index) {
        console.log("filepreupload")
    });
}

var vm = new Vue({
    el: '#rrapp',
    data: {
        image: '',
        showList: true,
        recruitmentDetail:false,
        historyList:false,
        provideList:false,
        provideDetail:false,
        provideInfo: false,//供人信息提交页面
        provideStaff: {},//供人信息
        title: null,
        shopRecruitment: {
            logo: '',
            labelList: []
        },
        image11: [],//招聘轮播图
        providePersonnel:{
        },
        shopId:'',
        historyId:'',
        labels: null,
        scales: null,
        treatments: null,
        years: null,
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
            //供人
            'click #provideStaff':function (event, value, row, index) {
              vm.title="供人";
              vm.showList = false;
              vm.recruitmentDetail = false;
              vm.historyList = false;
              vm.provideList = false;
              vm.provideDetail = false;
              vm.historyList = false;
              vm.provideInfo = true;
              vm.provideStaff = {
                  shopRecruitmentId: row.id,
                  type: ''
              };
            },
            //招聘信息
            'click .showInfo': function (event, value, row, index) {
                vm.title = "详情";
                vm.showList = false;
                vm.recruitmentDetail = true;
                vm.getInfo(value);
            },
            //上传excel
            'click #upload': function (event, value, row, index) {
                vm.showList = true;
                vm.recruitmentDetail = false;
                vm.provideDetail = false;
                vm.historyList = false;
                vm.provideList = false;
                $("#shopRecruitmentId").val(row.id);
                //清空 fileinput
                $(".fileinput-remove-button").click();
                $("#remark").val("");
                $("#myModal").modal("show");
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
        },
        params: {
            isEarnest:'',//保证金标识
            numberOfRecruitmentS:'',
            numberOfRecruitmentE:'',
        },
        params2:{
            shopRecruitmentId:'',
            beginTime: '',
            endTime: '',
            title:'',
            status:'',
            remark:''
        },
        params3:{
            name:'',
            uploadHistoryId:'',
        },
        isFlagList:[
            {'value': 1, 'label': '有'},
            {'value': 0, 'label': '无'},
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
                    },stringLength: {
                     max: 50,
                     message: '长度最大50'
                 }
                },
            }, age: {
                message: '年龄验证失败',
                validators: {
                    notEmpty: {
                        message: '年龄不能为空'
                    }, regexp: {
                        regexp: /^((1[0-5])|[1-9])?\d$/,
                        message: '请输入正确年龄'
                    }
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
        },
        provideFields: {
            number: {
                validators: {
                    notEmpty: {
                        message: '供人人数不能为空'
                    }, regexp: {
                        regexp: /^[1-9]\d{0,2}$/,
                        message: '人数为小于4位的正整数'
                    }
                }
            }, minAge: {
                validators: {
                    notEmpty: {
                        message: '年龄下限不能为空'
                    }, regexp: {
                        regexp: /^((1[0-5])|[1-9])?\d$/,
                        message: '请输入正确年龄'
                    }
                }
            }, maxAge: {
                validators: {
                    notEmpty: {
                        message: '年龄上限不能为空'
                    }, regexp: {
                        regexp: /^((1[0-5])|[1-9])?\d$/,
                        message: '请输入正确年龄'
                    }
                }
            }, province: {
                validators: {
                    notEmpty: {
                        message: '省不能为空'
                    }
                }
            }, city: {
                validators: {
                    notEmpty: {
                        message: '市不能为空'
                    }
                }
            }, arrivalTime: {
                validators: {
                    notEmpty: {
                        message: '到岗时间不能为空'
                    },stringLength: {
                        max: 20,
                        message: '到岗时间长度在20字符以内'
                    }
                }
            }, contacts: {
                validators: {
                    notEmpty: {
                        message: '联系人不能为空'
                    },stringLength: {
                        max: 10,
                        message: '联系人长度在10字符以内'
                    }
                }
            },phone: {
                validators: {
                    notEmpty: {
                        message: '联系方式不能为空'
                    }, regexp: {
                        regexp: /^1[345678]\d{9}$/,
                        message: '请输入正确的手机号'
                    }
                }
            },remarks: {
                validators: {
                   /* notEmpty: {
                        message: '备注不能为空'
                    }, */stringLength: {
                        max: 200,
                        message: '备注长度在200字符以内'
                    }
                }
            }
        }
    },
    methods: {
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
            // vm.providePersonnel.city = null;   //跟换省份时清除城市的选中状态
            vm.provideStaff.city = null;
            vm.areas = null;      //清除区列表数据
            vm.streets = null;
        },
        cityChange: function (index, child, value) {
            var str = JSON.stringify(window.getAreas(value));
            str = str.replace(/id/g, "value").replace(/shortname/g, "label");
            this.areas = JSON.parse(str);
            vm.provideStaff.area = null;
            vm.streets = null;
        },
        areaChange: function (index, child, value) {
            var str = JSON.stringify(window.getStreets(value));
            str = str.replace(/id/g, "value").replace(/shortname/g, "label");
            vm.streets = JSON.parse(str);
            vm.provideStaff.street = null;
        },
        reset1:function () {
            vm.params = {
                isEarnest:'',//保证金标识
                numberOfRecruitmentS:'',
                numberOfRecruitmentE:'',
            }
        },
        query: function () {
            vm.reload();
        },
        //重置按钮
        reset: function () {
            vm.params2 = {
                shopRecruitmentId:'',
                beginTime: '',
                endTime: '',
                title:'',
                status:'',
                remark:''
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
        //表单验证
        validate: function () {
            var bl = $('#provideForm').VF();//启用验证
            if (!bl) {
                return;
            }
        },
        validateProvide: function () {
            var bl = $('#provideStaffForm').VF();//启用验证
            if (!bl) {
                return;
            }
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
            vm.ue5.setContent('');
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
            });
        },
        add: function () {
            vm.showList = false;
            vm.historyList = false;
            vm.provideList = false;
            vm.provideDetail = true;
            vm.title = "新增供人";
            //招聘id，上传历史id
            vm.providePersonnel = {
                sex:'1',
                status:'1',
                shopRecruitmentId:vm.shopId,
                uploadHistoryId:vm.historyId,
            };
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
        saveOrUpdateProvide: function (event) {
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
        saveProvide: function (event) {
            if (isBlank(vm.provideStaff.type)) {
                alert("请选择人员类型");
                return;
            }
            $.ajax({
                type: "POST",
                url: baseURL +  "provideStaff/providestaff/save",
                contentType: "application/json",
                data: JSON.stringify(vm.provideStaff),
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
            // $("#form").RF();
            $("#provideStaffForm").RF();
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
            // $("#form").RF();
        },
        //加载二级列表
        getHistoryList:function (id) {
            vm.showList = false;
            vm.provideList = false;
            vm.provideDetail = false;
            vm.historyList = true;
            vm.title = "上传历史清单";
            //刷新 如需条件查询common.js
            $("#table2").BTF5(vm.params2);
        },
        //加载三级列表
        getProvideList:function (id) {
            vm.showList = false;
            vm.historyList = false;
            vm.provideList = true;
            vm.provideDetail = false;
            vm.title = "供人列表";
            vm.providePersonnel ={}
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
       /* "shopRecruitment.province": function (value, old) {
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
        },*/
        "provideStaff.province": function (value, old) {
            if (value != old) {
                var str = JSON.stringify(window.getCities(value));
                str = str.replace(/id/g, "value").replace(/shortname/g, "label");
                vm.cities = JSON.parse(str);
                setTimeout(function () {
                    vm.provideStaff.city = parseInt(vm.provideStaff.city);
                }, 0);
            }
        },
        "provideStaff.city": function (value, old) {
            if (value != old) {
                var str = JSON.stringify(window.getAreas(value));
                str = str.replace(/id/g, "value").replace(/shortname/g, "label");
                vm.areas = JSON.parse(str);
                setTimeout(function () {
                    vm.provideStaff.area = parseInt(vm.provideStaff.area);
                }, 1);

                //等于 NaN
                if (value !== value) {
                    vm.areas = null;
                }

            }
        },
        "provideStaff.area": function (value, old) {
            if (value != old) {
                var str = JSON.stringify(window.getStreets(value));
                str = str.replace(/id/g, "value").replace(/shortname/g, "label");
                vm.streets = JSON.parse(str);
                setTimeout(function () {
                    vm.provideStaff.street = parseInt(vm.provideStaff.street);
                }, 1);

                //等于 NaN
                if (value !== value) {
                    vm.streets = null;
                }
            }
        },

    }
});