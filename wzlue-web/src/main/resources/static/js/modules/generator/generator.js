$(function () {
    $("#table").BT({
        url: baseURL + 'sys/generator/list',
        columns: [
            {checkbox: true, width: '60px'},
            {title: '表名', field: 'tableName'},
            {title: 'Engine', field: 'engine'},
            {title: '表备注', field: 'tableComment'},
            {title: '创建时间', field: 'createTime'},
        ]
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            tableName: null
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        generator: function () {
            var tableNames = getSelectedRowsId("tableName");
            if (tableNames == null) {
                return;
            }
            location.href = baseURL + "sys/generator/code?token=" + token + "&tables=" + JSON.stringify(tableNames);
        },
        reload: function (event) {
            $("#table").BTF5(
                {
                    'tableName':vm.q.tableName,
                }
            );
        }
    }
});

