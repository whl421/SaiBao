<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>人员管理</title>
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
				<input type="hidden" id="userid" name="manageIntegral" value="${map.userid }" />
				<div class="row col-sm-12 form-inline">
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">&nbsp;姓名</label>
						<div  class="col-sm-8">
						<input type="text" class="form-control col-sm-8" readonly="readonly" value="${map.username}" style="width:100%">
						</div>
					</div>
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">&nbsp;部门</label>
						<div  class="col-sm-8">
						  	<c:forEach items="${deptList }" var="deptList">
				  	  		 <c:if test="${deptList.departid==map.departid}"> 
							   <input type="text" class="form-control col-sm-8" readonly="readonly" value="${deptList.departname }" style="width:100%">
							</c:if>    
				  	  		</c:forEach>
						</div>
					</div>
			  	</div>
			  	<div class="row col-sm-12 form-inline">
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">&nbsp;手机号码</label>
						<div  class="col-sm-8">
						<input type="text" class="form-control col-sm-8" readonly="readonly" value="${map.phone}" id="phone" onchange="yzphone(this.value)" name="manageIntegral" style="width:100%">
						</div>
					</div>
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">邮箱地址</label>
						<div  class="col-sm-8">
						<input type="text" class="form-control col-sm-8" readonly="readonly" value="${map.emall}" id="emall" onchange="yzemail(this.value)" name="manageIntegral" style="width:100%">
						</div>
					</div>
					<span style="color:red">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						用户可通过手机号码或邮箱地址登录系统
					</span>
			  	</div>
			  	<div class="row col-sm-12 form-inline">
			  		<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">&nbsp;入职日期</label>
						<div  class="col-sm-8">
						<input type="text" class="form-control col-sm-8" readonly="readonly" value="${map.joindate}" id="joindate" name="manageIntegral" style="width:100%">
						</div>
					</div>
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">奖扣权限</label>
						<div  class="col-sm-8">
						  	<c:forEach items="${jkconfig }" var="jkconfig">
				  	  			<c:if test="${jkconfig.configid==map.configid}">
						<input type="text" class="form-control col-sm-8" readonly="readonly" value="${jkconfig.pzmc }" id="joindate" name="manageIntegral" style="width:100%">
								</c:if>
				  	  		</c:forEach>
						</div>
					</div>
			 	</div>	  
			 	<div class="row col-sm-12 form-inline">
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">职位</label>
						<div  class="col-sm-8">
						  	<c:forEach items="${position }" var="position">
				  	  			<c:if test="${position.pid==map.pid}"> 
						<input type="text" class="form-control col-sm-8" readonly="readonly" value="${position.pname }" id="joindate" name="manageIntegral" style="width:100%">		  
								 </c:if> 
				  	  		</c:forEach>
						</div>
					</div>
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">职称</label>
						<div  class="col-sm-8">
						  	<c:forEach items="${title }" var="title">
				  	  		<c:if test="${title.tid==map.tid}">
						<input type="text" class="form-control col-sm-8" readonly="readonly" value="${title.tname }" id="joindate" name="manageIntegral" style="width:100%">		  
							</c:if>
				  	  		</c:forEach>
						</div>
					</div>
			 	</div>	
			 	<div class="row col-sm-12 form-inline">
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">学历</label>
						<div  class="col-sm-8">
						  	<c:forEach items="${education }" var="education">
				  	  			<c:if test="${education.eid==map.eid}"> 
						<input type="text" class="form-control col-sm-8" readonly="readonly" value="${education.ename }" id="joindate" name="manageIntegral" style="width:100%">		  
								</c:if>
				  	  		</c:forEach>
						</div>
					</div>
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">技术证书</label>
						<div  class="col-sm-8">
						  	<c:forEach items="${skill }" var="skill">
				  	  			<c:if test="${skill.sid==map.sid}"> 
						<input type="text" class="form-control col-sm-8" readonly="readonly" value="${skill.sname }" id="joindate" name="manageIntegral" style="width:100%">		  
								</c:if>
				  	  		</c:forEach>
						</div>
					</div>
			 	</div>	
			 	<div class="row col-sm-12 form-inline">
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">人员编号</label>
						<div  class="col-sm-8">
						<input type="text" class="form-control col-sm-8" readonly="readonly" value="${map.usercode}" id="usercode" name="manageIntegral" style="width:100%">
						</div>
					</div>
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">&nbsp;性别</label>
						<div  class="col-sm-8">
						<select  class="form-control col-sm-8 " id="sex" name="manageIntegral" style="width:100%">
						 <c:if test="${map.sex==0}"> 	<option value="0"  >女</option> </c:if>
						 <c:if test="${map.sex==1}">  	<option value="1"  >男</option> </c:if>
						</select>
						</div>
					</div>
			  	</div>
			 	<div class="row col-sm-12 form-inline">
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">毕业院校</label>
						<div  class="col-sm-8">
						<input type="text" class="form-control col-sm-8" readonly="readonly" value="${map.byschool}" id="byschool" name="manageIntegral" style="width:100%">
						</div>
					</div>
					
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">专业</label>
						<div  class="col-sm-8">
						<input type="text" class="form-control col-sm-8" readonly="readonly" value="${map.major}" id="major" name="manageIntegral" style="width:100%">
						</div>
					</div>
			  	</div>
			  	<div class="row col-sm-12 form-inline">
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">办公室电话</label>
						<div  class="col-sm-8">
						<input type="text" class="form-control col-sm-8" readonly="readonly" value="${map.officephone}" id="officephone" name="manageIntegral" style="width:100%">
						</div>
					</div>
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">身份证号</label>
						<div  class="col-sm-8">
						<input type="text" class="form-control col-sm-8" readonly="readonly" value="${map.card}" id="card" name="manageIntegral" style="width:100%">
						</div>
					</div>
			  	</div>
			  	<div class="row col-sm-12 form-inline">
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">生日</label>
						<div  class="col-sm-8">
						<input type="text" class="form-control col-sm-8" readonly="readonly" value="${map.birthday}" id="birthday" name="manageIntegral" style="width:100%">
						</div>
					</div>
					<div class="form-group col-sm-6">
						<label  class="date-label col-sm-4">备注</label>
						<div  class="col-sm-8">
						<input type="text" class="form-control col-sm-8" readonly="readonly" value="${map.memo}" id="memo" name="manageIntegral" style="width:100%">
						</div>
					</div>
			  	</div>
				
				<!-- <div class="row col-sm-12 form-inline buttonrow">
					<div class="btn-groups" style="text-align: center;">
						<div class="btn-group">
							<button onclick="giveup();"  class="btn btn-warning">
								<i class="glyphicon glyphicon-remove" aria-hidden="true"></i>取消
							</button>
						</div>
					</div>
				</div>	  -->
			
			</div>
		</div>
		
		<!-- ${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js  每个弹出表单都需要导入 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js"></script>
		<script type="text/javascript">
		 
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
 
		 
