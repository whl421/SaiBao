<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>人员管理新增</title>
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
	</head>
	<body class="gray-bg" >
		
		<div class="wrapper wrapper-content animated fadeInRight">
			<div class="ibox-content col-sm-12">
				
			  <div class="row col-sm-12 form-inline">
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">姓名：</label>
					<input type="text" class="form-control col-sm-8" 
					 null="no" id="name" name="manageIntegral" readonly="readonly" value="${map.name}" >
					<span class="errorspan">姓名不能为空</span>
				</div>
				
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">性别：</label>
					<input type="text" class="form-control date col-sm-8" readonly="readonly"
					alt="生日" null="no" id="sex" name="manageIntegral" value="${map.sex}" >
					<span class="errorspan">不能为空</span>
				</div>
			  </div>
			
			 <div class="row col-sm-12 form-inline">
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">生日：</label>
					<input type="text" class="form-control date col-sm-8" readonly="readonly"
					alt="生日" null="no" id="day" name="manageIntegral" value="${map.day}" >
				</div>
				
			 </div>	  
				  
				  
			<div class="row col-sm-12 form-inline buttonrow">
				<div class="form-group col-sm-7">
				</div>
				<div class="form-group col-sm-5 form-inline">
					
					<div class="btn-group">
						<button onclick="giveup();"  class="btn btn-warning">
							<i class="glyphicon glyphicon-remove" aria-hidden="true"></i>取消
						</button>
					</div>
				</div>
			</div>	  
			</div>
		</div>
		
		<!-- placeholder="请输入单据号" -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js"></script>
		<script type="text/javascript">
		 
// 		 $("#day").val(parentiframedate["day"]);
// 		 $("#id").val(parentiframedate["id"]);
// 		 $("#name").val(parentiframedate["name"]);
// 		 $("#sex").val(parentiframedate["name"]);
			 
			/**
			 * 放弃
			 */
			function giveup(){
				ColseModalView(null);
			}
			 
		</script>
	</body>
</html>
 
		 
