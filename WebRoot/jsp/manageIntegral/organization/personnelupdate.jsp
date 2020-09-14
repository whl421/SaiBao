<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>人员管理修改</title>
		<meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
		<meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">
		
		<link rel="shortcut icon" href="H5.ico"> 
		<link href="../css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
		<link href="../css/font-awesome.css?v=4.4.0" rel="stylesheet">
		<script src="../js/jquery.min.js?v=2.1.4"></script>
		<script src="../js/bootstrap.min.js?v=3.3.6"></script>
		<link href="../css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css"></link> 
		<script src="../js/bootstrap-datetimepicker.min.js"></script>
		<script src="../js/bootstrap-datetimepicker.zh-CN.js"></script>
		<link href="../css/form.css?v=4.1.0" rel="stylesheet">
	</head>
	<body class="gray-bg" >
		
		<div class="wrapper wrapper-content animated fadeInRight">
			<div class="ibox-content col-sm-12">
				
			  <div class="row col-sm-12 form-inline">
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">姓名：</label>
					<input type="text" class="form-control col-sm-8" 
					 null="no" id="name" name="manageIntegral" >
					<span class="errorspan">姓名不能为空</span>
				</div>
				
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">性别：</label>
					<select class="form-control col-sm-8" data-done-button="true"
					 title="性别选择" 
					 null="no" id="sex" name="manageIntegral" >
					 <option value="" ></option>
					  <option value="1" >男</option>
					  <option value="0" >女</option>
					</select>
					<span class="errorselectspan">不能为空</span>
				</div>
			  </div>
			
			 <div class="row col-sm-12 form-inline">
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">生日：</label>
					<input type="text" class="form-control date col-sm-8" readonly="readonly"
					alt="生日" null="no" id="day" name="manageIntegral" >
				</div>
				
			 </div>	  
				  
				  
			<div class="row col-sm-12 form-inline buttonrow">
				<div class="form-group col-sm-4">
				</div>
				<div class="form-group col-sm-8 form-inline">
					<div class="btn-group">
						<button onclick="setupate();"  class="btn btn-primary">
							<i class="glyphicon glyphicon-ok" aria-hidden="true"></i>提交
						</button>
					</div>
					
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
		<script type="text/javascript" src="../js/index.js"></script>
		<script type="text/javascript">
		 
		
window.onload=function(){
	    
	     $("#day").val(parentiframedate["day"]);
// 		 $("#id").val(parentiframedate["id"]);
		 $("#name").val(parentiframedate["name"]);
		 $("#sex").val(parentiframedate["sex"]);
}		 
		
			/**
			 * 生日
			 */
			setgetdate("birthday");
			/**
			 * 入职日期
			 */
			setgetdate("dateentry");
			/**
			 * 待赋值参数
			 */
			var	departmentcode=null;
			/**
			 * 修改
			 */
			function setupate(){
				/**
				 * 获取参数值
				 */
				var obj=SaveMesDate("manageIntegral");
				if(!obj["ajax"]){
				alert(12);
				return;
				}
				/**
				*注意此处为 id赋值
				*/
				obj["id"]=parentiframedate["id"];
				// alert(JSON.stringify(obj));
				/**
				 * 提交参数
				 * obj：提交参数
				 * true:继续执行
				 */
				var ajax=doajax("${pageContext.request.contextPath}/user/testupdatetable.action",obj,true);
				/**
				*pusetype ：回传参数 在index.js中，organization.html 中的dofunction 函数响应
				*/
				if(ajax)ColseModalView(pusetype);
					
			}
			/**
			 * 放弃
			 */
			function giveup(){
				ColseModalView(null);
			}
			/**
			 * 回调参数
			 * @param {Object} usetype
			 * @param {Object} data
			 */
// 			function dofunction(usetype,data){
// 				if(usetype=="department"){
// 					$("#departmentname")=data["departmentname"];
// 					departmentcode=data["departmentcode"];
// 				}
// 			}
// 			/**
// 			 * 部门选择弹出
// 			 */
// 			$("#departmentname").click(function(){
// 				PopupModalView(null,"department","部门选择","500px","90%",_url)
// 			})
		</script>
	</body>
</html>
 
		 

		 
