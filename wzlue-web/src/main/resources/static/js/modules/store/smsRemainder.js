$(function () {
    //列表
    $("#table").BT({
        url: baseURL + 'wechat/wxapp/smsRemainder',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '公众号名称', field: 'appname'},
            {title: '短信余量', field: 'remainder'}
        ],
        //条件查询
        queryParams: {}
    });

})



var vm = new Vue({
    el: '#rrapp',
    data: {
        user_Store: userStore,
        showList: true,
        title: null,
        categoryList: [],   //公众号
        appIdList: [],
        queryParams: {
            appId: ''
        },
        //验证字段
        fields: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },

        reload: function (event) {
            vm.showList = true;
            vm.title = "";
            //刷新 如需条件查询common.js
            $("#table").BTF5(vm.queryParams);
        },
        getCategory: function () {
            // 加载菜单树
            $.get(baseURL + "wechat/wxapp/selectlist", function (r) {
                vm.categoryList = r.wxAppList;
            })
        },
    },
    created: function () {
        this.getCategory();
    },
    watch: {

    }
});







