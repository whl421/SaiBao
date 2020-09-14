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
		
		<link href="${pageContext.request.contextPath}/jsp/manageIntegral/js/select/select/multiple-select.css" rel="stylesheet">
		<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.7/css/select2.min.css" rel="stylesheet" />
		<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.7/js/select2.min.js"></script>
	</head>
	<body class="gray-bg" >
		<table style="width:100%;height: 90%;min-width:700px">
		<tr>
		<td>
		<div class="wrapper wrapper-content animated fadeInRight">
			<div class="ibox-content col-sm-12">
				<div class="row" style="margin: 0 auto;">
					<fieldset>
						<legend style="font-size: 16px;">基础信息</legend>
					</fieldset>
					<div class="row col-sm-12 form-inline" style="margin-top: -10px;">
						<div class="form-group col-sm-6">
							<label  class="date-label col-sm-4"><span style="color:red">*</span>&nbsp;姓名</label>
							<div  class="col-sm-8">
							<input type="text" class="form-control col-sm-8" null="no" id="username" name="manageIntegral" style="width:100%">
							<span class="errorspan">姓名不能为空</span>
							</div>
						</div>
						<div class="form-group col-sm-6">
							<label  class="date-label col-sm-4"><span style="color:red">*</span>&nbsp;部门</label>
							<div  class="col-sm-8">
							<select class="form-control col-sm-8" data-done-button="true" null="no" id="departid" name="manageIntegral" style="width:100%">
							 	<option value="" >请选择部门</option>
							  	<c:forEach items="${deptList }" var="deptList">
						  			<option value="${deptList.departid }">${deptList.departname }</option>
						  		</c:forEach>
							</select>
							</div>
						</div>
					</div>
					<div class="row col-sm-12 form-inline">
						<div class="form-group col-sm-6">
							<label  class="date-label col-sm-4"><span style="color:red">*</span>&nbsp;登录号</label>
							<div  class="col-sm-8">
							<input type="text" class="form-control col-sm-8" null="no" id="phone" onchange="yzphone(this.value)" name="manageIntegral" style="width:100%">
							
							</div>
						</div>
						<div class="form-group col-sm-6">
							<label  class="date-label col-sm-4">性别</label>
							<div  class="col-sm-8">
							<select  class="form-control col-sm-8 "  null="no" id="sex" name="manageIntegral" style="width:100%">
							  	<option value=""></option>
							  	<option value="0">女</option>
							  	<option value="1">男</option>
							</select>
							</div>
						</div>
					</div>
					
					<div class="row col-sm-12 form-inline">
						
						<div class="form-group col-sm-6">
							<label  class="date-label col-sm-4">备注</label>
							<div  class="col-sm-8">
							<input type="text" class="form-control col-sm-8" null="yes" id="memo" name="manageIntegral" style="width:100%">
							</div>
						</div>
					</div>
				</div>
				<div class="row col-sm-12 form-inline buttonrow">
					<div class="btn-groups" style="text-align: center;">
						<div class="btn-group">
							<button onclick="setadd();"  class="btn btn-primary">
								<i class="glyphicon glyphicon-ok" aria-hidden="true"></i>保存
							</button>
						</div>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<div class="btn-group">
							<button onclick="giveup();"  class="btn btn-warning">
								<i class="glyphicon glyphicon-remove" aria-hidden="true"></i>取消
							</button>
						</div>
					</div>
				</div>	  
			</div>
		</div>
		</td>
		</tr>
		</table>
		<!-- ${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js  每个弹出表单都需要导入 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js"></script>
		<script type="text/javascript">
		//接收树节点ID
		var $tablebytreeid = parent.getParentIframeDate();
		//alert("传入值:"+$tablebytreeid);
		if($tablebytreeid!=""&&$tablebytreeid!=-1)$("#departid").val($tablebytreeid);
		
		//验证手机号
		function yzphone(phone){
			var flag = false;
			var message = "";
		        $.ajax({
					url : '${pageContext.request.contextPath}/userinfo/phoneYZ.action?rundd='+new Date().getTime(),
					type : 'post',
				 	data : {tablejson:phone},
			        dataType : "json",
				    async : false,
				    success : function(date){
					    if(date){
					    	var isok = date["isok"];
					    	if(isok == "no"){
					    		message = "手机号已存在";
					    		$("#phone").val("");
					    	}else{
					    		flag = true;
					    	}
						}	 
				    },
				    error:function(){
						alert("网络繁忙，提交失败");
					}
				});
		    if (message != "") {
		        alert(message);
		        return;
		    }
		    return flag;
		 }
		 
		
		
		
		//下拉设置搜索
		$("#departid").select2(); 
			
		/**
		 * 新增
		 */
		function setadd(aobj){
		    $(this).attr("disabled",true); 
		    var username = $("#username").val();
		    var sex = $("#sex").val();
			var departid = $("#departid").val();
			var phone = $("#phone").val();
			
			if(username == null || username == ""){
			  	alert("姓名不能为空");
			  	return;
			}
			if(phone == null || phone == ""){
			  	alert("登录号不能为空");
			  	return;
			}
			if(departid == null || departid == ""){
			  	alert("部门不能为空");
			  	return;
			}
			if(!yzphone(phone)){
				return;
			}
			var data = {
		        "username" : username,
		        "departid" : departid,
		        "departname" : $("#departid option:checked").text(),
				"sex" : sex,
				"phone" : $("#phone").val(),
				"memo" : $("#memo").val(),
				"userstatus" : "1"
			};
			var str = JSON.stringify(data);
			$.ajax({
				url : '${pageContext.request.contextPath}/userinfo/usersave.action?rundd='+new Date().getTime(),
				type : 'post',
			 	data : {tablejson:str},
		        dataType : "json",
			    async : false,
			    success : function(date){
				 	if(date == null || date == 'null' || date == 'NULL' || date == ' ' || date["info"] == '0'){
				 		alert("服务器繁忙，提交失败");
				 		$(this).attr("disabled",false);
				 	}else{
				 		ColseModalView(null);
				 	}		
			    },
			    error:function(){
					alert("网络繁忙，提交失败");
					$(this).attr("disabled",false);
				}
			});
			
		}
		/**
		 * 放弃
		 */
		function giveup(){
			//关闭窗口   			
			ColseModalView(null);
		}
			
// 			/**
// 			 * 回调参数
// 			 * @param {Object} usetype
// 			 * @param {Object} data
// 			 */
// 			function dofunction(usetype,data){
// 				if(usetype=="department"){
// 					$("#departmentname")=data["departmentname"];
// 					departmentcode=data["departmentcode"];
// 				}
// 			}
 
		</script>
	</body>
</html>
 
		 
