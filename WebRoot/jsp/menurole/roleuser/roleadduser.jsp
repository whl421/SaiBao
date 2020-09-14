<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>角色人员新增- 模板界面</title>
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
	
	
	<link href="${pageContext.request.contextPath}/jsp/manageIntegral/js/select/select/multiple-select.css" rel="stylesheet">
	
	<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.7/css/select2.min.css" rel="stylesheet" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.7/js/select2.min.js"></script>
	<!-- <link href="../js/select/select2/css/select2.min.css" rel="stylesheet" />
	<script src="../js/select/select2/js/select2.min.js"></script> -->
		
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/select/select/multiple-select.js" ></script>
	
	
</head>

<body class="gray-bg">
	 
	
    <div class="wrapper wrapper-content animated fadeInRight">
		 <!-- Panel Other -->
			<div class="ibox-content col-sm-12">
			<div id="rolenametitle" class="row col-sm-12" style="font-size:16px;color:red;text-align: center;padding-bottom: 20px;"></div>	
			<div class="row col-sm-12 ">
				  <div class="col-sm-1"></div>
				<div class="form-group col-sm-10">
					<label  class="date-label col-sm-2">人员选择&nbsp;<span style="color:red">*</span></label>
					<div class="col-sm-10">
					<div id="ssid2"  class="form-group col-sm-12">
						<select  style="width:100%;" multiple="multiple" style="text-"
						 title="人员选择" 
						 null="no" id="userid" name="manageIntegral" >
						  <c:forEach items="${sluser }" var="user" >
						   <optgroup label="${user.key }" >
						   <c:forEach items="${user.value }" var="userlist" >
							<option value="${userlist.userid }">${userlist.username }</option>&nbsp;&nbsp;
						   </c:forEach>
						  </c:forEach>
						</select>
						<input type="text" value="${roleid}" style="display:none" id="roleid" null="no" title="角色"  name="manageIntegral" />
					  </div>
					</div> 
				</div>
					
			</div>
 
				  
			<div class="row col-sm-12 form-inline buttonrow">
				<div class="form-group col-sm-4">
				</div>
				<div class="form-group col-sm-8 form-inline">
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
		
		$(function(){
			$('#userid').multipleSelect({
				  multiple: true,
				 // width: 460,
				  multipleWidth: 55,
				  selectAll: false,
				  //选择达到20条记录才会隐藏 默认3条
				  minimumCountSelected:30,
				  //openOnHover:true,
				  filter: true,
				  //分组不可选
				  //hideOptgroupCheckboxes:true,
				  //只可单选
				  // single:true
				})
				
				setTimeout(function() {
					$("#rolenametitle").html(parentiframedate+"(角色)新增人员");
				}, 100)
		});
		
		 $("#setadd").click(function(){
				/**
				 * 获取参数值
				 */
				var obj=SaveMesDate("manageIntegral");
				if(!obj["ajax"])return;
				
				obj["userid"]=$("#userid").val();
				/**
				 * 提交参数
				 * obj：提交参数
				 * true:继续执行
				 */
				var ajax=doajax("${pageContext.request.contextPath}/menu/roleuseradd.action",obj);
				
				if(ajax){
					ColseModalView(null);	
				}
			 
		 });
		 
	
		
	</script>

</body>

</html>
