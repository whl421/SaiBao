<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>编辑新增- 模板界面</title>
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
		 <!-- Panel Other -->
			<div class="ibox-content col-sm-12">
				
			  <div class="row col-sm-12 ">
				  <div class="col-sm-1"></div>
				<div class="form-group col-sm-10">
					<label  class="date-label col-sm-2">编号&nbsp;<span style="color:red">*</span></label>
					<div class="col-sm-10">
					<input type="text" class="form-control" 
					 null="no" id="rolecode" name="manageIntegral" >
					 <span class="errorspan">编号不能为空</span>
					</div> 
				</div>
					<div class="col-sm-1"></div>
			  </div>
			  
			  
			  <div class="row col-sm-12 ">
				  <div class="col-sm-1"></div>
				<div class="form-group col-sm-10">
					<label  class="date-label col-sm-2">名称&nbsp;<span style="color:red">*</span></label>
					<div class="col-sm-10">
					<input type="text" class="form-control" 
					 null="no" id="rolename" name="manageIntegral" >
					 <input type="text" class="form-control" 
					  null="no" id="id" style="display:none" name="manageIntegral" >
					 <span class="errorspan">名称不能为空</span>
					</div> 
				</div>
					<div class="col-sm-1"></div>
			  </div>
			  
			   <div class="row col-sm-12 ">
					  <div class="col-sm-1"></div>
					<div class="form-group col-sm-10">
						<label  class="date-label col-sm-2">备注&nbsp;</label>
						<div class="col-sm-10">
						<textarea  rows="5" class="form-control" 
						 null="yes" id="desc" name="manageIntegral" ></textarea >
						</div> 
					</div>
						<div class="col-sm-1"></div>
			  </div>
			  
				  
			<div class="row col-sm-12 form-inline buttonrow" style="text-align: center;">
				<div class="form-group col-sm-12 form-inline">
					<div class="btn-group">
						<button id="setadd"  class="btn btn-primary">
							<i class="glyphicon glyphicon-ok" aria-hidden="true"></i>保存
						</button>
					</div>
					
					<div class="btn-group">
						<button onclick="JavaScript:ColseModalView(null);"  class="btn btn-warning">
							<i class="glyphicon glyphicon-remove" aria-hidden="true"></i>取消
						</button>
					</div>
				</div>
			 </div>	  
			</div>
					
		<!-- End Panel Other -->
		
    </div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js"></script>
	
	<script type="text/javascript">
window.onload=function(){
	     $("#rolename").val(parentiframedate["rolename"]);
		 $("#id").val(parentiframedate["id"]);
		 $("#rolecode").val(parentiframedate["rolecode"]);
		 $("#desc").val(parentiframedate["desc"]);
}		
		
		
		 $("#setadd").click(function(){
				/**
				 * 获取参数值
				 */
				var obj=SaveMesDate("manageIntegral");
				if(!obj["ajax"])return;
				// alert(JSON.stringify(obj));
				/**
				 * 提交参数
				 * obj：提交参数
				 * true:继续执行
				 */
				var ajax=doajax("${pageContext.request.contextPath}/menu/updaterole.action",obj);
				
				if(ajax){
					ColseModalView(null);	 
				}
			 
		 });
		 
	
		
	</script>

</body>

</html>
