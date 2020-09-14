<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>设备预约审核</title>
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
		<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css"  media="all">
		<script src="${pageContext.request.contextPath}/layui/layui.js" charset="utf-8"></script> 
	</head>
	<body class="gray-bg" >
		<table style="width:100%;height: 90%;min-width:700px">
		<tr>
		<td>
		<div class="wrapper wrapper-content animated fadeInRight">
			<div class="ibox-content col-sm-12">
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<input type="hidden" id="applyuser" name="applyuser" value="${map.applyuser}" />
						<label  class="date-label col-sm-2 ">申请人</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" value="${map.applyusername }"
								null="no" id="applyusername" name="applyusername" readonly="readonly">
						</div> 
						<label  class="date-label col-sm-2">申请时间</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" value="${map.applytime }"
								null="no" id="applytime" name="applytime" readonly="readonly" >
						</div> 
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<input type="hidden" id="taskid" name="taskid" />
						<label  class="date-label col-sm-2">任务编号</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" readonly="readonly"
							  id="taskcode"  name="taskcode" value="${map.taskcode }" />
						</div> 
						<label  class="date-label col-sm-2">任务标题</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" readonly="readonly"
							  id="tasktitle"  name="tasktitle" value="${map.tasktitle }" />
						</div>
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<input type="hidden" id="equipid" name="equipid" />
						<label  class="date-label col-sm-2">设备编号</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" readonly="readonly"
							  id="equipcode"  name="equipcode" value="${map.equipcode }" />
						</div> 
						<label  class="date-label col-sm-2">设备名称</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" readonly="readonly"
							  id="equipname"  name="equipname" value="${map.equipname }" />
						</div> 
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2">开始时间</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" readonly="readonly"
							  id="starttime"  name="starttime" value="${map.starttime }" />
						</div> 
						<label  class="date-label col-sm-2">结束时间</label>
						<div class="col-sm-4">
							<input  rows="2" class="form-control" readonly="readonly"
							  id="endtime"  name="endtime" value="${map.endtime }" />
						</div> 
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2">备注</label>
						<div class="col-sm-10">
							<textarea rows="2" class="form-control" readonly="readonly"
							  id="memo"  name="memo" >${map.memo }</textarea>
						</div> 
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2">审核人</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" readonly="readonly"
							  id="checkname"  name="checkname" value="${map.checkname }" />
						</div> 
						<label  class="date-label col-sm-2">审核时间</label>
						<div class="col-sm-4">
							<input  rows="2" class="form-control" readonly="readonly"
							  id="checktime"  name="checktime" value="${map.checktime }" />
						</div> 
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2">审核结果</label>
						<div class="col-sm-4">
							<div class="form-control col-sm-12"  > 
							 <label class="radio-inline">
								<input type="radio"  value="2" name="applystate" id="applystate" checked>通过
							 </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 <label class="radio-inline">
								<input type="radio"  value="3" name="applystate" id="applystate"  >驳回
							 </label>	
							</div>
						</div> 
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2">审核意见</label>
						<div class="col-sm-10">
							<textarea rows="2" class="form-control" 
							  id="checkmemo"  name="checkmemo" >${map.checkmemo }</textarea>
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
			/**
			 * 回调函数
			 * @param {Object} usetype
			 * @param {Object} data
			 */
			function dofunction(usetype,data){
				if(data.type == "selectEquip") {
					var equipid = data.equipid;
					var equipcode = data.equipcode;
					var equipname = data.equipname;
					$("#equipid").val(equipid);
					$("#equipcode").val(equipcode);
					$("#equipname").val(equipname);
				} else if(data.type == "selectTask") {
					var taskid = data.taskid;
					var taskcode = data.taskcode;
					var tasktitle = data.tasktitle;
					$("#taskid").val(taskid);
					$("#taskcode").val(taskcode);
					$("#tasktitle").val(tasktitle);
				}
			}
			/**
			 * 新增
			 */
			function setadd(aobj){
			    $(this).attr("disabled",true); 
			    var equipapplyid = "${map.equipapplyid }";
			    var applystate = $('input[name="applystate"]:checked').val();
			    var checkmemo = $("#checkmemo").val();
				
				var data = {
			        "equipapplyid" : equipapplyid,
			        "applystate" : applystate,
					"checkmemo" : checkmemo,
				};
				var str = JSON.stringify(data);
				var flag = "";
				$.ajax({
					url : '${pageContext.request.contextPath}/equipApply/check.action?rundd='+new Date().getTime(),
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
 
		 
