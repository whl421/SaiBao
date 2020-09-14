

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>委托单管理新增</title>
		<meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
		<meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">
		
		<link rel="shortcut icon" href="H5.ico"> 
		<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/font-awesome.css?v=4.4.0" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/jquery.min.js?v=2.1.4"></script>
		<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap.min.js?v=3.3.6"></script>
		<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css"></link> 
		<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap-datetimepicker.min.js"></script>
		<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/form.css?v=4.1.0" rel="stylesheet">
		
		<link href="${pageContext.request.contextPath}/jsp/manageIntegral/js/select/select/multiple-select.css" rel="stylesheet">
		<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.7/css/select2.min.css" rel="stylesheet" />
		<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.7/js/select2.min.js"></script>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css"  media="all">
		<script src="${pageContext.request.contextPath}/layui/layui.js" charset="utf-8"></script> 
	</head>
	<body class="gray-bg" >
		
		<div class="wrapper wrapper-content animated fadeInRight">
			<form id="myform"  enctype="multipart/form-data">
				<div class="ibox-content col-sm-12">
					<div class="col-sm-12 form-group">
						<label  class="date-label col-sm-2"><span style="color:red">*</span>委托编号</label>
						<div  class="col-sm-4">
							<input type="text" class="form-control"  null="no" id="ordercode" name="ordercode" 
								style="width:100%" value="${map.ordercode}" >
							<span class="errorspan">委托编号不能为空</span>
						</div>
						<label  class="date-label col-sm-2"><span style="color:red">*</span>委托名称</label>
						<div  class="col-sm-4">
							<input type="text" class="form-control"  null="no" id="ordername" name="ordername" 
								style="width:100%" value="${map.ordername}" >
						</div>
					</div>
					<div class="col-sm-12 form-group">
						<label  class="date-label col-sm-2"><span style="color:red">*</span>委托时间</label>
						<div  class="col-sm-4">
							<input type="text" class="form-control"  null="no" id="ordertime" name="ordertime" 
								style="width:100%" value="${map.ordertime}" >
						</div>
						<label  class="date-label col-sm-2"><span style="color:red">*</span>委托人</label>
						<div  class="col-sm-4">
							<input type="text" class="form-control"  null="no" id="orderuser" name="orderuser" 
								style="width:100%" value="${map.orderuser}" >
						</div>
					</div>
					<div class="col-sm-12 form-group">
						<label  class="date-label col-sm-2">客户名称</label>
						<div  class="col-sm-10">
							<textarea rows="2" class="form-control col-sm-10"  null="yes" id="khname" name="khname" 
								style="width:100%" value="${map.khname}"></textarea>
						</div>
					</div>
					<div class="col-sm-12 form-group">
						<label  class="date-label col-sm-2">上传文件&nbsp;</label>
						<div class="col-sm-10">
							<input type="file" class="" null="no" 
								multiple="multiple" name="file" id="file" onclick="addinput(); >
							<input type="button" name="button" value="添加附件" onclick="addinput();" />
							<!-- <input type="button" name="button" value="删除所有" onclick="deleteinput();" /> -->
							<span id="upload">
								<div style="font-size: 14px;padding: 10px;">
									<a href="../daytask/downLoad.action?id=${fj.id}">${fj.sourcefile }</a><br/>
								</div>
							</span>
						</div> 
					</div>
					<div class="col-sm-12 form-group buttonrow">
						<div class="btn-groups" style="text-align: center;">
							<div class="btn-group">
								<button id="submitbtn"  class="btn btn-primary">
									<i class="glyphicon glyphicon-ok" aria-hidden="true"></i>保存
								</button>
							</div>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<div class="btn-group">
								<button id="ColseModalView"  class="btn btn-warning">
									<i class="glyphicon glyphicon-remove" aria-hidden="true"></i>取消
								</button>
							</div>
						</div>
					</div>	
				</div>
			</form>
		</div>
		
		<!-- ${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js  每个弹出表单都需要导入 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js"></script>
		<script type="text/javascript">
			layui.use('laydate', function(){
				var laydate = layui.laydate;
				//日期时间范围
				laydate.render({
					elem: '#ordertime'
					,type: 'date'
					,format: 'yyyy-MM-dd'
					// ,range: "--"
					// ,showBottom: false
					,trigger: 'click'
				});
			});
		$("#submitbtn").click(function(){
			
			var ordercode = $("#ordercode").val();
			var ordername = $("#ordername").val();
			var ordertime = $("#ordertime").val();
			if(ordercode == null || ordercode == ""){
			  	alert("委托单编号不能为空");
			  	return;
			}
			if(ordername == null || ordername == ""){
			  	alert("委托单名称不能为空");
			  	return;
			}
			if(ordertime == null || ordertime == ""){
			  	alert("委托时间不能为空");
			  	return;
			}
			var form = document.getElementById("myform");
			//创建表单对象，并加入文件对象
			var formFile = new FormData(form);
			//设置ajax参数为表单对象
			var datas = formFile;
			parent.layer.load(2); 
			$.ajax({
				type: "post",
				url: "${pageContext.request.contextPath}/order/save.action",
				dataType: "json",
				data:datas,
				cache: false,
				processData: false,
				contentType: false,
				success: function (data) {
					parent.layer.closeAll('loading');
					if(data == null || data == 'null' || data == 'NULL' || data == ' ' || data["info"] == '0'){
						if(data["text"]) {
							alert(data["text"]);
						} else {
							alert("服务器繁忙，提交失败");
						}
						$(this).attr("disabled",false);
					}else{
						ColseModalView(pusetype);
					}
				},
				error: function (a,b,c) {
					parent.layer.closeAll('loading');
					alert('网络繁忙，提交失败!');
				}
			});
			return false;
		});
		//取消按钮
		$("#ColseModalView").click(function(){
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index);
		});
		/**
		 * 新增
		 */
		// function setadd(aobj){
		//     $(this).attr("disabled",true); 
		// 	
		// 	var filename = $("#filename").val();
		// 	
		// 	if(filename == null || filename == ""){
		// 	  	alert("文件名称不能为空");
		// 	  	return;
		// 	}
		// 	
		// 	var data = {
		//         "filename" : filename,
		//         "filepath" : $("#filepath").val(),
		//         "sclassid" : $("#sclassid").val(),
		//         "memo" : $("#memo").val(),
		// 	};
		// 	var str = JSON.stringify(data);
		// 	var form = document.getElementById("myform");
		// 	//创建表单对象，并加入文件对象
		// 	var formFile = new FormData(form);
		// 	//设置ajax参数为表单对象
		// 	var datas = formFile;
		// 	$.ajax({
		// 		url : '${pageContext.request.contextPath}/stainfo/stainfosave.action?rundd='+new Date().getTime(),
		// 		type : 'post',
		// 	 	data : {tablejson:str,file:datas},
		//         dataType : "json",
		// 	    async : false,
		// 	    success : function(date){
		// 		 	if(date == null || date == 'null' || date == 'NULL' || date == ' ' || date["info"] == '0'){
		// 		 		alert("服务器繁忙，提交失败");
		// 		 		$(this).attr("disabled",false);
		// 		 	}else{
		// 		 		ColseModalView(pusetype);
		// 		 	}		
		// 	    },
		// 	    error:function(){
		// 			alert("网络繁忙，提交失败");
		// 			$(this).attr("disabled",false);
		// 		}
		// 	});
		// }	
		/**
		 * 放弃
		 */
		function giveup(){
			//关闭窗口   			
			ColseModalView(null);
		}
 
		</script>
	</body>
</html>
 
		 
