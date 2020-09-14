<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>人员管理新增111</title>
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
		<table style="width:100%;height: 90%">
		<tr>
		<td>
		<div class="wrapper wrapper-content animated fadeInRight">
			<div class="ibox-content col-sm-12">
				
			  <div class="row col-sm-12 form-inline">
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4"><span style="color:red">*</span>&nbsp;部门名称</label>
					<div  class="col-sm-8">
						<input type="text" class="form-control col-sm-12"  style="width:100%"  null="no" id="departname" name="manageIntegral" >
					<span class="errorspan">部门名称不能为空</span>
					</div>
				</div>
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4"><span style="color:red">*</span>&nbsp;上级部门</label>
					<div  class="col-sm-8">
						<select class="form-control col-sm-12" style="width:100%" data-done-button="true" title="上级部门名称" 
					 		null="no" id="parentid" name="manageIntegral" >
						 	<option value="" >请选择上级部门</option>
						  	<c:forEach items="${dept }" var="dept">
				  	  			<option value="${dept.departid }">${dept.departname }</option>
				  	  		</c:forEach>
						</select>
					</div>
				</div>
			</div>
			<div class="row col-sm-12 form-inline">
				
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">基础分</label>
					<div  class="col-sm-8">
						<input type="number" class="form-control col-sm-8"  null="yes" id="bm_jcf" name="manageIntegral" style="width:100%">
					 </div>
				</div>
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">备注</label>
					<div  class="col-sm-8">
						<input type="text" class="form-control col-sm-8"  null="yes" id="memo" name="manageIntegral" style="width:100%">
					</div>
				</div>
				<div class="form-group col-sm-6">
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
		if($tablebytreeid!=""&&$tablebytreeid!=-1)$("#parentid").val($tablebytreeid);

		//下拉设置搜索
		$("#parentid").select2(); 		
		/**
		 * 生日
		 */
		setgetdate("day");
			
		/**
		 * 新增
		 */
		function setadd(){
			/**
			 * 获取参数值
			 */
			var departname = $("#departname").val();
			var parentid = $("#parentid").val();
			
			if(departname == null || departname == ""){
			  	alert("部门名称不能为空");
			  	return;
			}
			if(parentid == null || parentid == ""){
			  	alert("上级不能为空");
			  	return;
			}
			
			var obj=SaveMesDate("manageIntegral");
			if(!obj["ajax"])return;
			// alert(JSON.stringify(obj));
			/**
			 * 提交参数
			 * obj：提交参数
			 * true:继续执行
			 */
			var ajax=doajax("${pageContext.request.contextPath}/dept/deptsave.action",obj);
			
			if(ajax){
				ColseModalView(pusetype);
					// var input = document.getElementsByTagName('input');
					// if(input)
				 //    for(i = 0; i < input.length; i++) {   
				 //      if(input[i].getAttribute("name") == "manageIntegral" && input[i].getAttribute("type")!="checkbox" 
				 //    	  													&& input[i].getAttribute("type")!="radio") {   
				 //         var inputid=input[i].getAttribute("id")||input[i]["id"];   
				 //         $("#"+inputid).val("");
				 //      }   
				 //    }
				 //    var select = document.getElementsByTagName('select');   
				 //    if(select)
				 //    for(i = 0; i < select.length; i++) {   
				 //      if(select[i].getAttribute("name") == "manageIntegral" ) {   
				 //         var selectid=select[i].getAttribute("id")||select[i]["id"];   
				 //        //$("#"+selectid).val("");
				 //      }   
				 //    }
				 //    var textarea = document.getElementsByTagName('textarea');  
				 //    if(textarea)
				 //    for(i = 0; i < textarea.length; i++) {   
				 //      if(textarea[i].getAttribute("name") == "manageIntegral" ) { 
				 //         var textareaid=textarea[i].getAttribute("id")||textarea[i]["id"];  
				 //        $("#"+textareaid).text("");
				 //      }   
				 //    }
			}
			
			//window.parent.document.getElementById(window.frameElement.id).contentWindow.location.reload(true);
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
 
		 
