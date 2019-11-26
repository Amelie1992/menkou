$(function() {
	// 表单提交
	$("form").FM({
		fields : vm.fields,
		success : vm.saveOrUpdate

	})

});

var vm = new Vue(
		{
			el : '#rrapp',
			data : {
				showList : false,
				title : null,
				config: {
				},
				// 验证字段
				fields : {
					ruleType : {
						message : '规则类型验证失败',
						validators : {
							notEmpty : {
								message : '规则类型不能为空'
							},
						},
					},
					lose : {
						message : '失去验证失败',
						validators : {
							notEmpty : {
								message : '失去不能为空'
							},
						},
					},
					get : {
						message : '获得验证失败',
						validators : {
							notEmpty : {
								message : '获得不能为空'
							},
						},
					},
					upperLimit : {
						message : '上限验证失败',
						validators : {
							notEmpty : {
								message : '上限不能为空'
							},
						},
					},
					updateTime : {
						message : '更新时间验证失败',
						validators : {
							notEmpty : {
								message : '更新时间不能为空'
							},
						},
					}
				}
			},
			methods : {
				update : function(event) {
					var id = getSelectedRowId("id");
					if (id == null) {
						return;
					}
					vm.showList = false;
					vm.title = "修改";
				},
				// 表单验证
				validate : function() {
					var bl = $('form').VF();// 启用验证
					if (!bl) {
						return;
					}
				},
				saveOrUpdate : function(event) {
					var url = "sys/config/updateByKey";
					var params = {key: "WECHAT_SETTING", value: JSON.stringify(vm.config)};
					$.ajax({
						type : "POST",
						url : baseURL + url,
						contentType : "application/json",
						data : JSON.stringify(params),
						success : function(r) {
							if (r.code === 0) {
								alert('操作成功', function(index) {
									
								});
							} else {
								alert(r.msg);
							}
						}
					});
				},
				getInfo : function() {
					$.get(baseURL + "sys/config/getByKey", {key: "WECHAT_SETTING"},
					function(r) {
						vm.config = JSON.parse(r.value);
					});
				}
			},
			created: function(){
				this.getInfo();
			}
		});