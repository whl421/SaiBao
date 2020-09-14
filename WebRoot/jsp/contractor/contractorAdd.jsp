<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>分包商管理新增</title>
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
			<div class="ibox-content col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<label  class="date-label col-xs-2 col-sm-2 col-md-2 col-lg-2"><span style="color:red">*</span>分包商编码</label>
						<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
							<input type="text" class="form-control" value="${map.fbscode }"
								null="no" id="fbscode" name="fbscode" >
						</div> 
						<label  class="date-label col-xs-2 col-sm-2 col-md-2 col-lg-2"><span style="color:red">*</span>分包商名称</label>
						<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
							<input type="text" class="form-control" value="${map.fbsname }"
								null="no" id="fbsname" name="fbsname" >
						</div> 
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<div class="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<label  class="date-label col-xs-2 col-sm-2 col-md-2 col-lg-2">联系人</label>
						<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
							<input type="text" class="form-control" value="${map.contacts }"
								null="no" id="contacts" name="contacts" >
						</div> 
						<label  class="date-label col-xs-2 col-sm-2 col-md-2 col-lg-2">联系方式</label>
						<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
							<input type="text" class="form-control" value="${map.contactphone }"
								null="no" id="contactphone" name="contactphone" >
						</div> 
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" >
					<div class=" form-group col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
						<label  class="date-label col-xs-2 col-sm-2 col-md-2 col-lg-2"><span style="color:red">*</span>状态</label>
						<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 ">
							<div class="form-control col-xs-12 col-sm-12 col-md-12 col-lg-12"  > 
							 <label class="radio-inline">
								<input type="radio"  value="1" name="fbsstate" id="fbsstate" checked >启用
							 </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 <label class="radio-inline">
								<input type="radio"  value="0" name="fbsstate" id="fbsstate" >禁用
							 </label>	
							</div>
						</div>	
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
					<div class="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<label  class="date-label col-xs-2 col-sm-2 col-md-2 col-lg-2">说明</label>
						<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
							<textarea  rows="2" class="form-control" 
							  id="memo"  name="memo" ></textarea >
						</div> 
					</div>
				</div>
				<div class="row col-xs-12 col-sm-12 col-md-12 col-lg-12 form-inline buttonrow">
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
		
		/**
		 * 新增
		 */
		function setadd(aobj){
		    $(this).attr("disabled",true); 
		    var fbscode = $("#fbscode").val();
		    var fbsname = $("#fbsname").val();
			var contacts = $("#contacts").val();
			var contactphone = $("#contactphone").val();
			var fbsstate = $('input[name="fbsstate"]:checked').val();
			var memo = $("#memo").val();
			
			if(fbscode == null || fbscode == ""){
			  	alert("分包商编码不能为空");
			  	return;
			}
			if(fbsname == null || fbsname == ""){
			  	alert("分包商名称不能为空");
			  	return;
			}
			var data = {
		        "fbscode" : fbscode,
		        "fbsname" : fbsname,
				"contacts" : contacts,
				"contactphone" : contactphone,
				"fbsstate" : fbsstate,
				"memo" : memo
			};
			var str = JSON.stringify(data);
			var flag = "";
			flag = codeIsExist(fbscode,'');
			if(flag == "1") {
				$.ajax({
					url : '${pageContext.request.contextPath}/contractor/save.action?rundd='+new Date().getTime(),
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
			} else if(flag == "0"){
				alert("分包商编码已存在，请重新输入");
				return;
			} else {
				alert("操作失败，请联系管理员");
				return;
			}
		}
		function codeIsExist(fbscode,fbsid) {
			var flag = "";
			var data = {
				"fbscode" : fbscode,
				"fbsid" : fbsid,
			};
			var str = JSON.stringify(data);
			$.ajax({
				url : '${pageContext.request.contextPath}/contractor/codeIsExist.action?rundd='+new Date().getTime(),
				type : 'post',
			 	data : {tablejson:str},
			    dataType : "json",
			    async : false,
			    success : function(date){
					flag = date["info"]
			    },
			    error:function(){
					alert("网络繁忙，提交失败");
					flag = "2";
				},
			});
			return flag;
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
 
		 
