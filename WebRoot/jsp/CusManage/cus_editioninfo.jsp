<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>版本信息</title>
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
			<input type="hidden" id="cusid" name="manageIntegral" value="${map.cusid }" />
			  <div class="row col-sm-12 form-inline">
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">公司编号</label>
					<input type="text" class="form-control col-sm-8" value="${map.cuscode}"  readonly="readonly" 
					 null="no" id="cuscode" name="manageIntegral" >
				</div>
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">会员等级</label>
					 <select  class="form-control col-sm-8 " null="no" id="hytype" name="manageIntegral" disabled="disabled">
					  	<option value=""></option>
					  	<option value="1" <c:if test="${map.hytype==1}"> selected="selected" </c:if>>创始会员</option>
					  	<option value="2" <c:if test="${map.hytype==2}"> selected="selected" </c:if>>高级会员</option>
					  	<option value="3" <c:if test="${map.hytype==3}"> selected="selected" </c:if>>普通会员</option>
					</select>
				</div>
			  </div>
			
			 <div class="row col-sm-12 form-inline">
			 	<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">公司简称</label>
					<input type="text" class="form-control col-sm-8" value="${map.cusname}"
					 null="no" id="cusname" name="manageIntegral" > 
					 <span class="errorspan">公司简称不能为空</span>
				</div>
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">公司全称</label>
					<input type="text" class="form-control col-sm-8" value="${map.cusfullname}" 
					 null="no" id="cusfullname" name="manageIntegral" >
				</div>
			 </div>	  
			 <div class="row col-sm-12 form-inline">
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">管理员账号</label>
					<input type="text" class="form-control col-sm-8" value="${map.sysusername}" 
					 null="no" id="sysusername" name="manageIntegral" >
				</div>
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">购买日期</label>
					<input type="text" class="form-control col-sm-8" value="${map.startdate}" disabled="disabled"
					 null="no" id="startdate" name="manageIntegral">
				</div>
			</div>	 
			<div class="row col-sm-12 form-inline">
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">续费日期</label>
					<input type="text" class="form-control col-sm-8" value="${map.xfdate}" disabled="disabled"
					 null="yes" id="xfdate" name="manageIntegral">
				</div>
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">到期日期</label>
					<input type="text" class="form-control col-sm-8" value="${map.dqdate}" disabled="disabled"
					 null="no" id="dqdate" name="manageIntegral">
				</div>
			</div>	
			<div class="row col-sm-12 form-inline">
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">购买版本</label>
					<select  class="form-control col-sm-8 " null="no" id="version" name="manageIntegral" disabled="disabled">
					  	<option value=""></option>
					  	<option value="1" <c:if test="${map.version==1}"> selected="selected" </c:if>>初级版</option>
					  	<option value="2" <c:if test="${map.version==2}"> selected="selected" </c:if>>中级版</option>
					  	<option value="3" <c:if test="${map.version==3}"> selected="selected" </c:if>>高级版</option>
					  	<option value="4" <c:if test="${map.version==4}"> selected="selected" </c:if>>定制版</option>
					</select>
				</div>
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">使用人数上限</label>
					<input type="text" class="form-control col-sm-8" value="${map.usernum}" readonly="readonly" 
					 null="no" id="usernum" name="manageIntegral">
				</div>
			</div> 
			<div class="row col-sm-12 form-inline">
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">单位地址</label>
					<input type="text" class="form-control col-sm-8" value="${map.dwdz}"
					 null="yes" id="dwdz" name="manageIntegral">
				</div>
				<!-- <div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">邮编</label>
					<input type="text" class="form-control col-sm-8" value="${map.yb}"
					 null="yes" id="yb" name="manageIntegral">
				</div> -->
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">组织机构代码</label>
					<input type="text" class="form-control col-sm-8" value="${map.sbh}"
					 null="yes" id="sbh" name="manageIntegral">
				</div>
			</div>	
			<div class="row col-sm-12 form-inline">
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">联系人</label>
					<input type="text" class="form-control col-sm-8" value="${map.lxr}"
					 null="yes" id="lxr" name="manageIntegral">
				</div>
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">联系电话</label>
					<input type="text" class="form-control col-sm-8" value="${map.tel}"
					 null="yes" id="tel" name="manageIntegral">
				</div>
			</div>		  
			<!-- <div class="row col-sm-12 form-inline"> -->
				<!-- <div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">开户银行</label>
					<input type="text" class="form-control col-sm-8" value="${map.khh}"
					 null="yes" id="khh" name="manageIntegral">
				</div> -->
				<!-- <div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">纳税人识别号</label>
					<input type="text" class="form-control col-sm-8" value="${map.sbh}"
					 null="yes" id="sbh" name="manageIntegral">
				</div> -->
			<!-- </div>	 -->
			<div class="row col-sm-12 form-inline">
				<!-- <div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">银行账号</label>
					<input type="text" class="form-control col-sm-8" value="${map.yhzh}"
					 null="yes" id="yhzh" name="manageIntegral">
				</div> -->
				<div class="form-group col-sm-6">
					<label  class="date-label col-sm-4">备注</label>
					<input type="text" class="form-control col-sm-8" value="${map.memo}"
					 null="yes" id="memo" name="manageIntegral">
				</div>
			</div>	
			
			<div class="row col-sm-12 form-inline buttonrow">
				 <div class="btn-groups" style="text-align: center;">
					<div class="btn-group">
						<button onclick="setupate();"  class="btn btn-primary">
							<i class="glyphicon glyphicon-ok" aria-hidden="true"></i>修改
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
			var cusid = $("#cusid").val();
			$.ajax({
				url : '${pageContext.request.contextPath}/cus/cuscodeYZ.action?rundd='+new Date().getTime(),
				type : 'post',
			 	data : {tablejson:cuscode,cusid:cusid},
		        dataType : "json",
			    async : false,
			    success : function(date){
				    if(date){
				    	var isok = date["isok"];
				    	if(isok == "no"){
				    		alert("客户编号已存在");
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
	
	//超级管理员账号验证
	$("#sysusername").change(function(){
		var sysusername = $(this).val();
		if(sysusername!=""){
			$.ajax({
				url : '${pageContext.request.contextPath}/cus/sysusernameYZ.action?rundd='+new Date().getTime(),
				type : 'post',
			 	data : {tablejson:sysusername},
		        dataType : "json",
			    async : false,
			    success : function(date){
				    if(date){
				    	var isok = date["isok"];
				    	if(isok == "no"){
				    		alert("管理员账号已存在");
				    		$("#sysusername").val("");
				    	}
					}	 
			    },
			    error:function(){
					alert("网络繁忙，提交失败");
				}
			});
		}
	});	
		
	//日期设置
	setgetdate("startdate");
	setgetdate("dqdate");
	setgetdate("xfdate");
	
	/**
	 * 修改
	 */
	function setupate(){
		var cuscode = $("#cuscode").val();
		var cusname = $("#cusname").val();
		var cusfullname = $("#cusfullname").val();
		var sysusername = $("#sysusername").val();
		var hytype = $("#hytype").val();
		var startdate = $("#startdate").val();
		var dqdate = $("#dqdate").val();
		var version = $("#version").val();
		var money = $("#money").val();
		
		if(cusname == null || cusname == ""){
		  	alert("客户简称不能为空");
		  	return;
		}
		if(cusfullname == null || cusfullname == ""){
		  	alert("客户全称不能为空");
		  	return;
		}
		if(sysusername == null || sysusername == ""){
		  	alert("管理员账号不能为空");
		  	return;
		}
		
		var obj=SaveMesDate("manageIntegral");
		if(!obj["ajax"])return;
		/**
		*注意此处为 id赋值
		*/
		//obj["tid"]=parentiframedate["tid"];
		//alert(JSON.stringify(obj));
		/**
		 * 提交参数
		 * obj：提交参数
		 * true:继续执行
		 */
		var ajax=doajax("${pageContext.request.contextPath}/cus/cusupdate.action",obj,true);
		/**
		*pusetype ：回传参数 在index.js中，organization.html 中的dofunction 函数响应
		*/
		alert("信息修改成功");	
		if(ajax)ColseModalView(pusetype);
		
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
 
		 
