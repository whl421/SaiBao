<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>设备管理新增</title>
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
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2 "><span style="color:red">*</span>设备编码</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" value="${map.equipcode }"
								null="no" id="equipcode" name="equipcode" >
						</div> 
						<label  class="date-label col-sm-2"><span style="color:red">*</span>设备名称</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" value="${map.equipname }"
								null="no" id="equipname" name="equipname" >
						</div> 
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2 ">所属部门</label>
						<div class="col-sm-4">
							<select  class="form-control col-sm-12" style="width:100%"
							 title="所属部门" 
							 null="no" id="departid" name="manageIntegral" >
								<option value=""></option>
								<c:forEach items = "${deptList }" var = "dept">
								  <option value="${dept.departid }">${dept.departname }</option>
								</c:forEach>
							</select>
						</div> 
						<label  class="date-label col-sm-2">状态</label>
						<div class="col-sm-4 ">
							<select  class="form-control" style="width:100%"
							 title="状态" value="${map.estate}"
							 null="no" id="estate" name="manageIntegral" >
								  <option value="1"  <c:if test="${map.estate == 1 }">selected="selected"</c:if> >启用</option>
								  <option value="2" <c:if test="${map.estate == 2 }">selected="selected"</c:if> >维护中</option>
								  <option value="3" <c:if test="${map.estate == 3 }">selected="selected"</c:if> >维修中</option>
								  <option value="0" <c:if test="${map.estate == 0 }">selected="selected"</c:if> >禁用</option>
								</select>
						</div>	
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2">设备描述</label>
						<div class="col-sm-10">
							<textarea  rows="2" class="form-control" 
							  id="equipdesc"  name="equipdesc" >${map.equipdesc}</textarea >
						</div> 
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2">备注</label>
						<div class="col-sm-10">
							<textarea  rows="2" class="form-control" 
							  id="memo"  name="memo" >${map.memo}</textarea >
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
		window.onload=function(){
			var departid = "${map.departid}";
			$("#departid").val(departid.split(",")).select2();
		}
		/**
		 * 新增
		 */
		function setadd(aobj){
		    $(this).attr("disabled",true); 
		    var equipid = "${map.equipid }";
		    var equipcode = $("#equipcode").val();
		    var equipname = $("#equipname").val();
			var departid = $("#departid").val();
			var obj = document.getElementById("departid");
			var departname = obj.options[obj.selectedIndex].text;
			var equipdesc = $("#equipdesc").val();
			// var estate = $('input[name="estate"]:checked').val();
			var estate = $("#estate").val();
			var memo = $("#memo").val();
			
			if(equipcode == null || equipcode == ""){
			  	alert("设备编码不能为空");
			  	return;
			}
			if(equipname == null || equipname == ""){
			  	alert("设备名称不能为空");
			  	return;
			}
			var data = {
		        "equipid" : equipid,
		        "equipcode" : equipcode,
		        "equipname" : equipname,
				"departid" : departid,
				"departname" : departname,
				"estate" : estate,
				"equipdesc" : equipdesc,
				"memo" : memo
			};
			var str = JSON.stringify(data);
			var flag = "";
			flag = codeIsExist(equipcode,equipid);
			if(flag == "1") {
				$.ajax({
					url : '${pageContext.request.contextPath}/equip/update.action?rundd='+new Date().getTime(),
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
				alert("设备编码已存在，请重新输入");
				return;
			} else {
				alert("操作失败，请联系管理员");
				return;
			}
		}
		function codeIsExist(equipcode,id) {
			var flag = "";
			var data = {
				"equipcode" : equipcode,
				"equipid" : id,
			};
			var str = JSON.stringify(data);
			$.ajax({
				url : '${pageContext.request.contextPath}/equip/codeIsExist.action?rundd='+new Date().getTime(),
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
 
		 
