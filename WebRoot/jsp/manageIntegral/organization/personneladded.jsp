<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>人员管理新增11</title>
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
					alt="生日" null="no" id="day" name="manageIntegral" />
					<span class="errorspan">生日不能为空</span>
				</div>
				
			 </div>	  
				  
				  
			<div class="row col-sm-12 form-inline buttonrow">
				<div class="form-group col-sm-4">
				</div>
				<div class="form-group col-sm-8 form-inline">
					<div class="btn-group">
						<button onclick="setadd();"  class="btn btn-primary">
							<i class="glyphicon glyphicon-ok" aria-hidden="true"></i>保存并新增下一个
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
		
		<!-- ../js/index.js  每个弹出表单都需要导入 -->
		<script type="text/javascript" src="../js/index.js"></script>
		<script type="text/javascript">
			
	/**
	 * 生日
	 */
	setgetdate("day");
// 	$("#day").datetimepicker({
// 		language: "zh-CN",
// 		format:"yyyy-mm-dd",
// 		todayBtn: true,
// 		autoclose: true,
// 		startView:2,
// 		minView: "month", //选择日期后，不会再跳转去选择时分秒 
// 		pickerPosition: "bottom-left"
// 	});
			
			/**
			 * 新增
			 */
			function setadd(){
				/**
				 * 获取参数值
				 */
				var obj=SaveMesDate("manageIntegral");
				if(!obj["ajax"])return;
				// alert(JSON.stringify(obj));
				/**
				 * 提交参数
				 * obj：提交参数
				 * true:继续执行
				 */
				 alert(12);
				var ajax=doajax("${pageContext.request.contextPath}/user/testaddtable.action",obj);
				
// 				alert("ajax"+ajax+",thisiframeid"+thisiframeid);
				
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
 
		 

 
		 
