<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>新增客户</title>
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
					<label  class="date-label col-sm-4"><span style="color:red">*</span>&nbsp;用户号</label>
					<input type="text" class="form-control col-sm-8" null="no" id="cuscode" name="manageIntegral" >
				</div>
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4"><span style="color:red">*</span>&nbsp;密码</label>
					<input type="text" class="form-control col-sm-8" null="no" value="123456" id="pwd" readonly="readonly" name="manageIntegral" >
				</div>
			  </div>
			 <div class="row col-sm-12 form-inline">
			 	<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4"><span style="color:red">*</span>&nbsp;服务商名称</label>
					<input type="text" class="form-control col-sm-8" null="no" id="cusname" name="manageIntegral" >
				</div>
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">联系人</label>
					<input type="text" class="form-control col-sm-8" null="yes" id="lxr" name="manageIntegral">
				</div>
			 </div>	  
			 <div class="row col-sm-12 form-inline">
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">联系电话</label>
					<input type="text" class="form-control col-sm-8" null="no" id="tel" name="manageIntegral" >
				</div>
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">邮箱地址</label>
					<input type="text" class="form-control col-sm-8" null="no" id="email" name="manageIntegral">
				</div>
			 </div>	 
			<div class="row col-sm-12 form-inline buttonrow">
				 <div class="btn-groups" style="text-align: center;">
					<div class="btn-group">
						<button onclick="setadd();"  class="btn btn-primary">
							<i class="glyphicon glyphicon-ok" aria-hidden="true"></i>提交
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
		
	<!-- ${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js  每个弹出表单都需要导入 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js"></script>
	<script type="text/javascript">
	//客户编号验证
	$("#cuscode").change(function(){
		var cuscode = $(this).val();
		if(cuscode!=""){
			$.ajax({
				url : '${pageContext.request.contextPath}/cus/cuscodeYZ.action?rundd='+new Date().getTime(),
				type : 'post',
			 	data : {tablejson:cuscode},
		        dataType : "json",
			    async : false,
			    success : function(date){
				    if(date){
				    	var isok = date["isok"];
				    	if(isok == "no"){
				    		alert("用户号已存在");
				    		$("#cuscode").val("");
				    	}
					}	 
			    },
			    error:function(){
					alert("网络繁忙，提交失败");
				}
			});
		}
	});
	
	
	/**
	 * 新增
	 */
	function setadd(){
		var cuscode = $("#cuscode").val();
		var cusname = $("#cusname").val();
		var pwd = $("#pwd").val();
		var lxr = $("#lxr").val();
		var tel = $("#tel").val();
		var email = $("#email").val();
		
		if(cuscode == null || cuscode == ""){
		  	alert("用户号不能为空");
		  	return;
		}
		if(pwd == null || pwd == ""){
		  	alert("用户号不能为空");
		  	return;
		}
		if(cusname == null || cusname == ""){
		  	alert("服务商名称不能为空");
		  	return;
		}
		
		var data = {
		        "cuscode" : cuscode,
		        "cusname" : cusname,
		        "pwd" : pwd,
		        "lxr" : lxr,
		        "tel" : tel,
		        "email" : email
			};
			var str = JSON.stringify(data);
			$.ajax({
				url : '${pageContext.request.contextPath}/cus/cussave.action?rundd='+new Date().getTime(),
				type : 'post',
			 	data : {tablejson:str},
		        dataType : "json",
			    async : false,
			    success : function(date){
				 	if(date == null || date == 'null' || date == 'NULL' || date == ' ' || date["info"] == '0'){
				 		alert("服务器繁忙，提交失败");
				 		$(this).attr("disabled",false);
				 	}else{
				 		alert("注册成功");
				 		ColseModalView(pusetype);
				 	}		
			    },
			    error:function(){
					alert("网络繁忙，提交失败");
					$(this).attr("disabled",false);
				}
			});
		
		//var obj=SaveMesDate("manageIntegral");
		//if(!obj["ajax"])return;
		/**
		 * 提交参数
		 * obj：提交参数
		 * true:继续执行
		 */
		/*
		var ajax=doajax("${pageContext.request.contextPath}/cus/cussave.action",obj);
		
		
		if(ajax){
			var input = document.getElementsByTagName('input');
			if(input)
		    for(i = 0; i < input.length; i++) {   
		      if(input[i].getAttribute("name") == "manageIntegral" && input[i].getAttribute("type")!="checkbox" 
		    	  													&& input[i].getAttribute("type")!="radio") {   
		         var inputid=input[i].getAttribute("id")||input[i]["id"];   
		         $("#"+inputid).val("");
		      }   
		    }
		    var select = document.getElementsByTagName('select');   
		    if(select)
		    for(i = 0; i < select.length; i++) {   
		      if(select[i].getAttribute("name") == "manageIntegral" ) {   
		         var selectid=select[i].getAttribute("id")||select[i]["id"];   
		        $("#"+selectid).val("");
		      }   
		    }
		    var textarea = document.getElementsByTagName('textarea');  
		    if(textarea)
		    for(i = 0; i < textarea.length; i++) {   
		      if(textarea[i].getAttribute("name") == "manageIntegral" ) { 
		         var textareaid=textarea[i].getAttribute("id")||textarea[i]["id"];  
		        $("#"+textareaid).text("");
		      }   
		    }
		}
		*/
		//window.parent.document.getElementById(window.frameElement.id).contentWindow.location.reload(true);
	}
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
 
		 
