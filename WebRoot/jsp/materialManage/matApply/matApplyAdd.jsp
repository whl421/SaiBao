<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>材料领用申请</title>
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
		<!-- 	列表控件 -->
		<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
		<!-- <script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/editable/bootstrap-table-editable.js"></script>
		<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/editable/bootstrap-table-editable.min.js"></script>
		<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap-editable.js"></script> -->
		<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
		<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/demo/bootstrap-table-demo.js"></script>
	</head>
	<body class="gray-bg" >
		<table style="width:100%;height: 90%;">
		<tr>
		<td>
		<div class="wrapper wrapper-content animated fadeInRight">
			<div class="ibox-content col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
					<div class="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<input type="hidden" id="taskid" name="taskid" />
						<label  class="date-label col-xs-2 col-sm-2 col-md-2 col-lg-2"><span style="color:red">*</span>任务编码</label>
						<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
							<input type="text" class="form-control" 
							  id="taskcode"  name="taskcode" />
						</div> 
						<label  class="date-label col-xs-2 col-sm-2 col-md-2 col-lg-2">任务标题</label>
						<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
							<input type="text" class="form-control" readonly="readonly"
							  id="tasktitle"  name="tasktitle" />
						</div>
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
					<div class="form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<label  class="date-label col-xs-2 col-sm-2 col-md-2 col-lg-2">申请原因</label>
						<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
							<textarea rows="2" class="form-control" 
							  id="applyreason"  name="applyreason" ></textarea>
						</div> 
					</div>
				</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 ">
					<div class="btn-groups" id="exampleToolbar" role="group">
						<div class="btn-group">
							<button onclick="addRows();"  class="btn btn-primary">
								<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>添加行
							</button>
						</div>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<div class="btn-group">
							<button onclick="delRows();"  class="btn btn-warning">
								<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>删除行
							</button>
						</div>
					</div>
					<div class="scrollbar" >
						<table id="matList" >
						</table>
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
			var _id=1;
			/**
			 * 回调函数
			 * @param {Object} usetype
			 * @param {Object} data
			 */
			function dofunction(usetype,data){
				if(data.type == "selectTask") {
					var taskid = data.taskid;
					var taskcode = data.taskcode;
					var tasktitle = data.tasktitle;
					$("#taskid").val(taskid);
					$("#taskcode").val(taskcode);
					$("#tasktitle").val(tasktitle);
				} else if(data.type == "selectStorCl") {
					var storclid = data.storclid;
					var clcode = data.clcode;
					var clname = data.clname;
					var row = {
						"id": _id,
						"storclid": storclid,
						"clcode": clcode,
						"clname": clname,
						"applynum" : ""
					};
					_id++;
					$table.bootstrapTable('insertRow', {index: 0, row: row});
				}
			}
			/*****选择任务信息开始*****/
			$("#taskcode").on("focus",function(){
				var _url="${pageContext.request.contextPath}/common/taskInfoForm.action";
				PopupModalView(null,'refresh','任务信息>>选择任务','80%','80%',_url);
			});
			/*****选择任务信息开始*****/
			
			/*****选择库存开始*****/
			function addRows() {
				var _url="${pageContext.request.contextPath}/common/storclForm.action";
				PopupModalView(null,'refresh','任务信息>>选择任务','80%','80%',_url);
			}
			/*****选择库存信息开始*****/
			//删除选中的数据,根据选择行的id来删除该行数据，每行id唯一
			function delRows(){
				var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
				  return row.id //price
				})
				if(ids.length==0){
					alert("请选择删除行！！");
					return;
				}
				$table.bootstrapTable('remove', {
				  field: 'id',  //id   每行id唯一
				  values: ids
				})
				// $table.bootstrapTable('removeAll'); //清空列表
			}
		$(function(){
			//数字验证
			$.each($("#billnum"),function(index,obj){
				$(obj).bind("input propertychange", function() {
					// alert($(this).val());
					var ob=$(this);
					//先把非数字的都替换掉，除了数字和.
					ob.val(ob.val().replace(/[^\d]/g, ""));
				});
			});
		});
		/**
		表格加载
		*/
			
		var $table;
		$(function() {
			var options = {editFiled:"applynum"}
			$("#matList").bootstrapTable('destroy');
			$table= $('#matList').bootstrapTable({
				url: "",//${pageContext.request.contextPath}/matApply/list.action
				height: "250",			 //行高，如果没有设置height属性，
				uniqueId: "storclid",          //每一行的唯一标识，一般为主键列
				striped: true,          //是否显示行间隔色
				sidePagination: "server",    //分页方式：client客户端分页，server服务端分页（*）
				clickEdit: true,
				// clickToSelect: true,
				columns:[
				{
					checkbox: true,  
					visible: true,//是否显示复选框 
					width:30
				}, 
				// {
				// 	field: 'storclid',
				// 	title: 'storclid',
				// 	hidden: true,
				// 	width: 0,
				// }, 
				{
					field: 'clcode',
					title: '材料编码',
					sortable: false,
					width: 100,
					align: 'center'
				},
				{
					field: 'clname',
					title: '材料名称',
					sortable: false,
					width: 100,
					align: 'center'
				},
				{
					field: 'applynum',
					title: '申请数量',
					sortable: false,
					width: 60,
					align: 'center'
				},
				],
				onDblClickCell : function(field,value,row,$element){
					// var upIndex = $element[0].parentElement.rowIndex -1;
					var upIndex = $element.parent().data('index');
					if(field == options.editFiled){
						$element[0].innerHTML="<input id='inputCell' type='text' name='inputCell' style ='width: 80%' value='"+value+"'>";
						$("#inputCell").focus();
						$("#inputCell").blur(function(){
							var newValue = $("#inputCell").val();
							newValue = newValue.replace(/[^\d]/g, "");
							row[field] = newValue;
							$(this).remove();
							$table.bootstrapTable('updateCell', {
								index: upIndex,
								field:field,
								value: newValue
							});
						});
					}
				},
				formatNoMatches:function(){
					return "";
				}
			});
			// 设置隐藏列
			// $table.bootstrapTable('hideColumn', 'storclid');
		});
		/**
		 * 新增
		 */
		function setadd(aobj){
		    $(this).attr("disabled",true); 
		    var taskid = $("#taskid").val();
		    var taskcode = $("#taskcode").val();
		    var applyreason = $("#applyreason").val();
			
			if(taskcode == null || taskcode == ""){
			  	alert("任务编码不能为空");
			  	return;
			}
			var data = {
		        "taskid" : taskid,
		        "applyreason" : applyreason,
			};
			var date = $table.bootstrapTable('getData');
			var str = JSON.stringify(data);
			var sublist = JSON.stringify(date)
			// alert(sublist);
			$.ajax({
				url : '${pageContext.request.contextPath}/matApply/save.action?rundd='+new Date().getTime(),
				type : 'post',
				data : {tablejson:str,sublist:sublist},
				dataType : "json",
				async : false,
				success : function(date){
					if(date == null || date == 'null' || date == 'NULL' || date == ' ' || date["info"] == '0'){
						alert(date["text"]);
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
 
		</script>
	</body>
</html>
 
		 
