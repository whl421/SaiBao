<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>奖扣积分分类修改</title>
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
			<div class="ibox-content col-sm-12">
				<input type="hidden" id="sclassid" name="manageIntegral" value="${map.sclassid }" />
				<div class="row col-sm-12 form-inline">
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4"><span style="color:red">*</span>&nbsp;分类编号</label>
						<input type="text" class="form-control col-sm-8" 
						 null="no" id="sclasscode" name="manageIntegral" value="${map.sclasscode}">
						 <span class="errorspan">分类编号不能为空</span>
					</div>
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4"><span style="color:red">*</span>&nbsp;分类名称</label>
						<input type="text" class="form-control col-sm-8" 
						 null="no" id="sclassname" name="manageIntegral" value="${map.sclassname}">
						 <span class="errorspan">分类名称不能为空</span>
					</div>
				 </div>
				<div class="row col-sm-12 form-inline">
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">父类名称</label>
						<select class="form-control col-sm-8" data-done-button="true" title="父类名称" 
						 	null="yes" id="parentid" name="manageIntegral">
						 	<option value="" ></option>
						  	<c:forEach items="${jfclass }" var="jfclass2">
				  	  			<option value="${jfclass2.sclassid }"  <c:if test="${jfclass2.sclassid==map.parentid}"> selected="selected"  </c:if>>${jfclass2.sclassname }</option>
				  	  		</c:forEach>
						</select>
					</div>
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">备注</label>
						<input type="text" class="form-control col-sm-8" 
						 null="yes" id="memo" name="manageIntegral" value="${map.memo}">
						</div>
					</div>	 
					<div class="row col-sm-12 form-inline buttonrow">
						<div class="btn-groups" style="text-align: center;">
						<div class="btn-group">
							<button onclick="setupate(this);"  class="btn btn-primary">
								<i class="glyphicon glyphicon-ok" aria-hidden="true"></i>修改
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
		
		<!-- placeholder="请输入单据号" -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js"></script>
		<script type="text/javascript">
		//下拉设置搜索
		$("#parentid").select2(); 
		
		/**
		 * 修改
		 */
		function setupate(){
			/**
			 * 获取参数值
			 */
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
			var ajax=doajax("${pageContext.request.contextPath}/standard/staclassupdate.action",obj,true);
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
			 
		</script>
	</body>
</html>
 
		 
