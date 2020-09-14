<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>人员导入</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">
	<meta http-equiv="Access-Control-Allow-Origin" content="*" />
    <link rel="shortcut icon" href="H5.ico"> 
	<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/font-awesome.css?v=4.4.0" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/jquery.min.js?v=2.1.4"></script>
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap.min.js?v=3.3.6"></script>
	
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/bootstrap-table/bootstrap-table.js"></script>
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/manageIntegral/css/bootstrap-select.css">
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap-select.js"></script>
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/organization.css?v=4.1.0" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/form.css?v=4.1.0" rel="stylesheet">
	
	
	
	
</head>

<body class="gray-bg">
	 
	
    <div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content col-sm-12">
			<form id="myform"   enctype="multipart/form-data">	
				<div class="row col-sm-12 ">
					<div class="col-sm-1"></div>
					<div class="form-group col-sm-10">
						<label  class="date-label col-sm-2"><span style="color:red">*</span>文件</label>
						<div class="col-sm-10">
							<input type="file" class="form-control" 
							 null="no" id="pwd" name="file"  >
						</div> 
					</div>
					<div class="col-sm-1"></div>
				</div>
				<div class="row col-sm-12 form-inline buttonrow">
					<div class="form-group col-sm-12 form-inline" style="text-align:center;">
						<div class="btn-group">
							<button id="submitbtn"  class="btn btn-primary">
								<i class="glyphicon glyphicon-ok" aria-hidden="true"></i>保存
							</button>
						</div>
						<div class="btn-group">
							<button id="ColseModalView"  class="btn btn-warning">
								<i class="glyphicon glyphicon-remove" aria-hidden="true"></i>取消
							</button>
						</div>
					</div>
				</div>	  
			</form>
		</div>
    </div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js"></script>
	
	<script type="text/javascript">
		$("#submitbtn").click(function(){
			var form = document.getElementById("myform");
			// var fd = new FormData(form);
			// var file = $('#file')[0].files[0];
			 
			//创建表单对象，并加入文件对象
			var formFile = new FormData(form);
			// formFile.append("file", file); //加入文件对象
			// formFile.append("manageIntegral",$("#manageIntegral").val()); 
			//设置ajax参数为表单对象
			var datas = formFile;
			parent.layer.load(2);
			$.ajax({
				type: "post",
				url: "${pageContext.request.contextPath}/userimport/fileUpload.action",
				dataType: "json",
				data:datas,
				cache: false,
				processData: false,
				contentType: false,
				success: function (data) {
					parent.layer.closeAll('loading');
					// alert(JSON.stringify(data));
					if(data == null || data == 'null' || data == 'NULL' || data == ' ' || data["info"] == '0'){
						alert(data["textinfo"]);
						$(this).attr("disabled",false);
					}else{
						alert(data["textinfo"]);
						ColseModalView(pusetype);
					}
				},
				error: function (a,b,c) {
					parent.layer.closeAll('loading');
					alert('导入失败!');
				}
			});
			return false;
		});
		
		  //  $("#submitbtn").click(function(){
			 //  $("#myform").ajaxForm({
				//   //定义返回JSON数据，还包括xml和script格式
				//   url:"${pageContext.request.contextPath}/login/fileUpload.action",
				//   dataType:'json',
				//   beforeSend: function() {
				// 	  //表单提交前做表单验证
				// 	  return true;
				//   },
				//   success: function(data) {
				// 	  //提交成功后调用
				// 	  alert(JSON.stringify(data));
				// 	  //alert(data.data.url);
				// 	  //return false;
				//   }
			 //  });
			 //  return false;
		  // });
		 
		$("#ColseModalView").click(function(){
			var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			parent.layer.close(index);
		});
	</script>
</body>
</html>
