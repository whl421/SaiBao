<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>标准信息管理查看</title>
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
	</head>
	<body class="gray-bg" >
		
		<div class="wrapper wrapper-content animated fadeInRight">
			<form id="myform"  enctype="multipart/form-data">
			<div class="ibox-content col-sm-12">
				<div class="col-sm-12 form-group">
					<label  class="date-label col-sm-2">文件名称</label>
					<div  class="col-sm-10">
						<input type="text" class="form-control col-sm-10"  null="no" id="filename" name="filename" 
							style="width:100%" value="${map.filename}" readonly="readonly">
						<span class="errorspan">文件名称不能为空</span>
					</div>
				</div>
				<div class="col-sm-12 form-group">
					<label  class="date-label col-sm-2">文件路径</label>
					<div  class="col-sm-10">
						<input type="text" class="form-control col-sm-10"  null="no" id="filepath" name="filepath" 
							style="width:100%" value="${map.filepath}" readonly="readonly">
					</div>
				</div>
				<div class="col-sm-12 form-group">
					<label  class="date-label col-sm-2">标准分类</label>
					<div  class="col-sm-10">
						<input type="text" class="form-control col-sm-10"  null="no" id="parentname" name="parentname" 
							style="width:100%" value="${map.sclassname}" readonly="readonly">
					</div>
				</div>
				<div class="col-sm-12 form-group">
					<label  class="date-label col-sm-2">备注</label>
					<div  class="col-sm-10">
						<input type="text" class="form-control col-sm-10"  null="yes" id="memo" name="memo" 
							style="width:100%" value="${map.memo}" readonly="readonly">
					</div>
				</div>
				<div class="col-sm-12 form-group">
					<label  class="date-label col-sm-2">附件&nbsp;</label>
					<div class="col-sm-10">
						<span id="upload" class="form-control" style="font-size: 14px;padding: 10px;height: 50px;background-color:#eee;width: 100%;">
							<c:forEach items="${fjmap }" var="fj">
								<a style="display: inline-block;" href="../stainfo/downLoad.action?id=${fj.id}">${fj.sourcefile }</a>&nbsp;&nbsp;
							</c:forEach>
						</span>
					</div> 
				</div>
				<div class="col-sm-12 form-inline buttonrow">
					<div class="btn-groups" style="text-align: center;">
						<!-- <div class="btn-group">
							<button id="submitbtn"  class="btn btn-primary">
								<i class="glyphicon glyphicon-ok" aria-hidden="true"></i>保存
							</button>
						</div>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
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
		//接收树节点ID
		var $tablebytreeid = parent.getParentIframeDate();
		//alert("传入值:"+$tablebytreeid);
		if($tablebytreeid!=""&&$tablebytreeid!=-1)$("#sclassid").val($tablebytreeid);
		
		//下拉设置搜索
		$("#sclassid").select2(); 	
		$("#submitbtn").click(function(){
			
			var filename = $("#filename").val();
			if(filename == null || filename == ""){
			  	alert("文件名称不能为空");
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
				url: "${pageContext.request.contextPath}/stainfo/stainfosave.action",
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
 
		 
