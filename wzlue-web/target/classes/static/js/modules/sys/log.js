$(function () {
    $("#table").BT({
        url: baseURL + 'sys/log/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: 'id', field: 'id'},
            {title: '用户名', field: 'username'},
            {title: '用户操作', field: 'operation'},
            {title: '请求方法', field: 'method'},
            {title: '请求参数', field: 'params'},
            {title: '执行时长', field: 'time'},
            {title: 'IP地址', field: 'ip'},
            {title: '创建时间', field: 'createDate'},
        ]
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		q:{
			key: null
		},
	},
	methods: {
		query: function () {
			vm.reload();
		},
		reload: function (event) {
		    $("#table").BTF5({
                'key': vm.q.key,
            })
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 

                page:page
            }).trigger("reloadGrid");
		}
	}
});