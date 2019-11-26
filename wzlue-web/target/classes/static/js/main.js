var vm = new Vue({
	el:'#rrapp',
	data:{
		statistics: {}
	},
	methods: {
		getStatistics: function () {
			$.getJSON(baseURL + "order/order/statistics", function(r){
				vm.statistics = r.statistics;
				
				$("#main").css("width", $(".content").width() - 50);
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'));

                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '订单统计'
                    },
                    tooltip: {},
                    legend: {
                        data:['销量']
                    },
                    xAxis: {
                        data: r.chart.map(function(v){return v.createTime})
                    },
                    yAxis: {
                        minInterval: 1,
                    },
                    series: [{
                        name: '订单数量',
                        type:'line',
                        data: r.chart.map(function(v){return v.count})
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
			});
		}
	},
	created: function(){
		// this.getStatistics();
	}
});
