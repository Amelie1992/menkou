var imageExt = new Array(".png", ".jpg", ".jpeg", ".bmp", ".gif");//图片文件的后缀名
/*var htmlExt = new Array(".html");//word文件的后缀名
var textExt = new Array(".txt");//excel文件的后缀名*/
var videoExt = new Array(".mp4", ".webm", "ogg");//js文件的后缀名
/*var pdfExt = new Array(".pdf");//js文件的后缀名*/
var extList = [imageExt, videoExt, extList]


//获取文件名后缀名
var extension = function (str) {
    var ext = null;
    if (str == null) {
        return null;
    }
    var name = str.toLowerCase();
    var i = name.lastIndexOf(".");
    if (i > -1) {
        var ext = name.substring(i);
    }
    return ext;
}


function typeMatch(type, filename) {
    var ext = extension(filename);
    if (contain(type, ext)) {
        return true;
    }
    return false;
}

var fileinput = {
    inheritAttrs: false,
    data: function () {
        return {
            option: {
                //文件上传Url
                this_uploadUrl: this.options.upload_url || this.upload_url || window.baseURL + "app/file/upload",
                //上传异步或者同步
                this_uploadAsync: this.options.upload_async == false ? false : (this.upload_async ? false : true),
                //上传图片最大个数
                this_maxFileCount: this.options.max_file_count || this.max_file_count || 5,
                //是否多选
                this_multiple: this.options.multiple == false ? false : (this.multiple ? false : true),
                //设置上传允许文件类型列表.
                this_allowedFileTypes: this.options.allowed_file_types || this.allowed_file_types || [],
                //上载的允许文件扩展名列表,如果您同时设置allowedFileTypes和 ，则需要小心allowedFileExtensions。在这种情况下，allowedFileTypes首先验证属性，并且通常在allowedFileExtensions设置之前（并且可以跳过后一个验证）
                this_allowedFileExtensions: this.options.allowed_file_extensions || this.allowed_file_extensions || ['jpg','jpeg','gif','png','bpm','doc','docx','xls','xlsx','pdf'],
                //是否显示拖放区域,
                this_dropZoneEnabled: this.options.drop_zone_enabled == false ? false : (this.drop_zone_enabled == false ? false : true),
                //是否显示预览
                showPreview: this.options.showPreview == false ? false : (this.show_preview == false ? false : true),
                this_deleteurl: this.deleteurl || this.options.deleteurl || '/sys/image/delete'
            },
            urllist: [],
            saveUrlList: [],
            showList: [],
        }
    },
    props: {
        //参数列表
        options: {
            default: function () {
                return {};
            }
        },
        //上传文件以后的集合
        files: null,
        deleteurl: null,
        //文件上传Url
        upload_url: null,
        //上传异步或者同步
        upload_async: null,
        image_list: null,
        //上传图片最大个数
        max_file_count: null,
        //是否多选
        multiple: null,
        //预览文件类型
        allowed_preview_types: null,
        //设置上传文件类型
        allowed_file_types: null,
        //上传文件类型
        allowed_file_extensions: null,
        //是否显示拖放去也
        drop_zone_enabled: null,
        //是否显示预览
        show_preview: null,
    },
    template: '<input ref="fileinput" type="file" class="file-loading" :multiple="option.this_multiple" name="file" v-bind:fileselect="fileselect"/>',
    methods: {
        initFileInput: function initFileInput() {
            this.saveUrlList = this.files == null ? [] : this.files;
            var _this = this;
            var fileinput = $(this.$refs.fileinput);
            fileinput.fileinput({
                language: _this.$parent.$i18n.locale, //设置语言
                uploadAsync: _this.option['this_uploadAsync'], //默认是异步上传
                uploadUrl: _this.option['this_uploadUrl'],  //上传地址
                showUpload: true, //是否显示上传按钮
                showRemove: true,//是否显示移除按钮
                dropZoneEnabled: _this.option['this_dropZoneEnabled'],//是否显示拖放区域
                showClose: false,
                showCaption: true,//是否显示标题
                allowedFileTypes: _this.option['this_allowedFileTypes'],
                allowedFileExtensions: _this.option['this_allowedFileExtensions'],
                maxFileSize: 10240,
                maxFileCount: _this.option['this_maxFileCount'],
                overwriteInitial: false, //不覆盖已存在的图片
                //下面几个就是初始化预览图片的配置
                msgFileNotFound: '文件 "{name}" 未找到!',
                showPreview: _this.option['showPreview'],
                initialPreviewAsData: true,
                deleteUrl: _this.option['this_deleteurl'],
                initialPreviewShowDelete: true,
                initialPreviewFileType: _this.option['allowed_preview_types'],
                //预览图片的设置
                initialPreview: _this.urllist,
                //initialPreview: ["http://img.zcool.cn/community/0117e2571b8b246ac72538120dd8a4.jpg@1280w_1l_2o_100sh.jpg"],// _this.option['this_imageList'],
                /*initialPreviewConfig: [
                    {caption: "nature-1.jpg", size: 329892, width: "120px", url: "{$url}", key: 1}
                    /!*                  {caption: "nature-2.jpg", size: 872378, width: "120px", url: "{$url}", key: 2},
                                      {caption: "nature-3.jpg", size: 632762, width: "120px", url: "{$url}", key: 3}*!/
                ]*/
                initialPreviewConfig: _this.showList,
                layoutTemplates:{
                    actionUpload:''
                }

            }).on('filebatchselected', _this.fileselect)
                .on('fileerror', _this.fileerror)
                .on('fileuploaded', _this.fileuploaded)
                .on('fileremoved', _this.fileremoved)
                .on('filesuccessremove', _this.filesuccessremove)
                .on('fileclear', _this.fileclear)
                .on("filedeleted", function (event, key, jqXHR, data) {
                if (_this.image_list instanceof Array) {
                    _this.image_list.forEach(function (x) {
                        if (x.id == key) {
                            _this.image_list.splice(_this.image_list.indexOf(x), 1);
                        }
                        return null;
                    })
                } else {
                    for (var key in _this.image_list) {
                        delete _this.image_list[key];
                    }
                    //_this.image_list;
                    return null;
                }
            });
        },
        //当文件选中事件
        fileselect: function (even, numFiles, label) {
            this.$emit('fileselect', even, numFiles, label);
        },
        //异步文件上传失败
        fileerror: function (event, data, msg) {
            console.log("异步文件上传失败")
            this.$emit('fileerror', event, data, msg);
        },
        filesuccessremove: function (event, id) {
            console.log("移除上传文件的事件");
            var _this = this;
            this.saveUrlList.forEach(function (item, index, arr) {
                if (item.previewId == id) {
                    _this.saveUrlList.splice(index, 1)
                }
            })
        },
        //点击浏览框右上角X 清空文件前响应事件
        fileclear: function (event) {
            console.log("点击浏览框右上角X 清空文件前响应事件")
            this.saveUrlList.length = 0;
            this.$emit('fileclear', event);
        },

        //异步成功
        fileuploaded: function (event, data, previewId, index) {
        	debugger
            var _this = this;
            var resp = data.response;
            data.files[index].picUrl = resp.url;
            data.files[index].picName = data.files[index].name;
            data.files[index].previewId = previewId;
            var flag = false;
            if (this.saveUrlList instanceof Array) {
                this.saveUrlList.forEach(function (t) {
                    if (t.previewId == previewId) {
                        flag = true;
                    }
                });
                if (flag) {
                    return;
                }
                this.saveUrlList.push(data.files[index]);
            } else {
                var item = data.files[index];
                Object.keys(item).forEach(function (key) {
                    _this.saveUrlList[key] = item[key];
                });
            }
            console.log("文件上传成功")
            this.$emit('fileuploaded', event, data, previewId, index);
        },
        //移除文件事件
        fileremoved: function (event, id, index) {
            console.log("移除文件事件")
            this.$emit('fileremoved', event, id, index);
        },
        destroy: function () {
            this.urllist = [];
            this.saveUrlList = [];
            this.showList = [];
            $(this.$refs.fileinput).fileinput('clear');
            $(this.$refs.fileinput).fileinput('destroy')
        },
        //方法
        //清空方法
        clean: function () {
            $(this.$refs.fileinput).fileinput('clear');
            vm.saveUrlList = [];
        }

    },
    /*    mounted: function () {
            this.initFileInput();
        },*/
    watch: {
        'image_list': function (value) {
            this.destroy();
            var _this = this;
            debugger
            if (value != null) {
                value = JSON.parse(JSON.stringify(value))
                if (value instanceof Array) {
                    if (value.length > 0) {
                        var files = [];
                        value.forEach(function (item) {
                            var s = {};
                            s.key = item.id;
                            s.caption = item.picName;
                            var flag = true;
                            window.extList.forEach(function (ext) {
                                if (window.typeMatch(ext, s.caption)) {
                                    flag = false;
                                    return;
                                }
                            })
                            if (flag) {
                                s.type = 'object'
                            }
                            s.previewAsData = true;
                            _this.urllist.push(item.picUrl)
                            files.push(s)
                        })
                        _this.showList = files;
                        //$(_this.$refs.fileinput).fileinput("readFiles", value);
                    }
                } else {
                    var s = {};
                    value.url = value.picUrl;
                    s.key = value.id;
                    s.caption = value.picName;
                    var flag = true;
                    window.extList.forEach(function (ext) {
                        if (window.typeMatch(ext, s.caption)) {
                            flag = false;
                            return;
                        }
                    })
                    if (flag) {
                        s.type = 'object'
                    }
                    s.previewAsData = true;
                    _this.urllist.push(value.picUrl)
                    _this.showList.push(s);
                }
            }
            _this.initFileInput();
        },
        '$parent.$i18n.locale': function (value) {
            var fileinput = $(this.$refs.fileinput);
            fileinput.fileinput('destroy')
            this.initFileInput();
        }
    }
}

Vue.component('commodity-fileinput', fileinput);

/**
 * select 插件
 * @param   options     select 中的值
 * @param   value       设置所选值
 * @param   method      更改选择的值后将触发此事件。它通过事件，clickedIndex，newValue，oldValue。
 * @param   load        调用show实例方法时会立即触发此事件。
 * @param   index       下标
 * @param   search      是否启用搜索功能
 * @param   childidx
 * @param   title       占位符
 */
Vue.component('vm-select', {
    data: function () {
        return {}
    },
    props: ['options', 'value', 'multiple', 'method', 'load', 'index', 'search', 'childidx', 'title'],
    template:
       /* :data-live-search-placeholder="$parent.$t(`query`)"*/
        '<select :multiple="multiple" class="selectpicker" :data-live-search="search" :title="title">' +
        '   <option :value="option.value" v-for="option in options" :id="option.labelvalue">{{ option.label }}</option>' +
        '</select>',
    mounted: function () {
        var vm = this;

        $(this.$el).selectpicker('val', this.value != null ? this.value : null);
        $(this.$el).on('changed.bs.select', function () {
            vm.$emit('input', $(this).val(), vm.childidx, $(this));
            if (typeof(vm.method) != 'undefined') {
                vm.method(vm.index, this.options[this.selectedIndex].text, this.value);
            }
        });
        $(this.$el).on('show.bs.select', function () {
            if (typeof(vm.load) != 'undefined') {
                vm.load(vm.index, vm.childidx);
            }
        });
    },
    updated: function () {
        $(this.$el).selectpicker('refresh');
        /*var div = $(".bootstrap-select").children().find(".filter-option");
        div.html(this.title);*/
    },
    destroyed: function () {
        $(this.$el).selectpicker('destroy');
    },
    watch: {
        '$parent.$i18n.locale': function (value) {
            $(this.$el).selectpicker('destroy');
            var zh = {
                noneSelectedText: '没有选中任何项',
                noneResultsText: '没有找到匹配项',
                countSelectedText: '选中{1}中的{0}项',
                maxOptionsText: ['超出限制 (最多选择{n}项)', '组选择超出限制(最多选择{n}组)'],
                multipleSeparator: ', ',
                selectAllText: '全选',
                deselectAllText: '取消全选',
            }
            if (value == 'en') {
                $(this.$el).selectpicker.defaults = undefined;
            } else {
                $(this.$el).selectpicker.defaults = zh;
            }
        },
        "value": function (value) {
            $(this.$el).selectpicker('val', this.value != null ? this.value : null);
        }
    }
});

$(".datepicker").attr("readonly", true);