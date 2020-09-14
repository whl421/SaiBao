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
		<table style="width:100%;height: 90%;min-width:700px">
		<tr>
		<td>
		<div class="wrapper wrapper-content animated fadeInRight">
			<div class="ibox-content col-sm-12">
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<input type="hidden" id="taskid" name="taskid" />
						<label  class="date-label col-sm-2">申请单号</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" readonly="readonly"
							  id="applycode"  name="applycode" value="${map.applycode}" />
						</div> 
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<input type="hidden" id="taskid" name="taskid" />
						<label  class="date-label col-sm-2">任务编码</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" readonly="readonly"
							  id="taskcode"  name="taskcode" value="${map.taskcode}" />
						</div> 
						<label  class="date-label col-sm-2">任务标题</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" readonly="readonly"
							  id="tasktitle"  name="tasktitle" value="${map.tasktitle}" />
						</div>
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2">申请原因</label>
						<div class="col-sm-10">
							<textarea rows="2" class="form-control" readonly="readonly"
							  id="applyreason"  name="applyreason" >${map.applyreason}</textarea>
						</div> 
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="scrollbar" >
						<input type="hidden" class="form-control" id="clapplyid" name="tablesearch" placeholder="材料申请ID" value="${map.clapplyid}">
						<!-- <span style="font-size: 14px;font-weight: bold;text-align: center;">材料明细</span> -->
						<table id="matList" >
						</table>
					</div>	
				</div>
				<div class="col-sm-12 " style="margin-top: 10px;">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2">审核人</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" readonly="readonly"
							  id="checkusername"  name="checkusername" value="${map.checkusername}" />
						</div> 
						<label  class="date-label col-sm-2">审核时间</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" readonly="readonly"
							  id="checktime"  name="checktime" value="${map.checktime}" />
						</div>
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2">审核结果</label>
						<div class="col-sm-10">
							<c:if test="${map.applystate == 1 }">
								<input type="text"  class="form-control" id="applystate" name="applystate" readonly="readonly" value="" />
							</c:if>
							<c:if test="${map.applystate == 2 }">
								<input type="text"  class="form-control" id="applystate" name="applystate" readonly="readonly" value="同意" />
							</c:if>
							<c:if test="${map.applystate == 3 }">
								<input type="text"  class="form-control" id="applystate" name="applystate" readonly="readonly" value="拒绝" />
							</c:if>	
						</div> 
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2">拒绝理由</label>
						<div class="col-sm-10">
							<textarea rows="2" class="form-control" readonly="readonly"
							  id="jjreason"  name="jjreason" >${map.jjreason}</textarea>
						</div> 
					</div>
				</div>
				<div class="row col-sm-12 form-inline buttonrow">
					<div class="btn-groups" style="text-align: center;">
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
					var clname = data.clname;
					var row = {
						"id": _id,
						"storclid": storclid,
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
				PopupModalView(null,'refresh','任务信息>>选择任务','60%','70%',_url);
			});
			/*****选择任务信息开始*****/
			
			/*****选择库存开始*****/
			function addRows() {
				var _url="${pageContext.request.contextPath}/common/storclForm.action";
				PopupModalView(null,'refresh','任务信息>>选择任务','60%','70%',_url);
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
				url: "${pageContext.request.contextPath}/matApply/clApplyList.action",//
				//height: "250",			 //行高，如果没有设置height属性，
				uniqueId: "storclid",          //每一行的唯一标识，一般为主键列
				striped: true,          //是否显示行间隔色
				sidePagination: "server",    //分页方式：client客户端分页，server服务端分页（*）
				clickEdit: true,
				// clickToSelect: true,
				//得到查询的参数
				queryParams : function (params) {
	//              //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					var temp = {   
						searchjson:JSON.stringify(gridReloadjson())	//查询参数			 
					};
					return temp;
				},
				columns:[
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
				// onDblClickCell : function(field,value,row,$element){
				// 	// var upIndex = $element[0].parentElement.rowIndex -1;
				// 	var upIndex = $element.parent().data('index');
				// 	if(field == options.editFiled){
				// 		$element[0].innerHTML="<input id='inputCell' type='text' name='inputCell' style ='width: 80%' value='"+value+"'>";
				// 		$("#inputCell").focus();
				// 		$("#inputCell").blur(function(){
				// 			var newValue = $("#inputCell").val();
				// 			newValue = newValue.replace(/[^\d]/g, "");
				// 			row[field] = newValue;
				// 			$(this).remove();
				// 			$table.bootstrapTable('updateCell', {
				// 				index: upIndex,
				// 				field:field,
				// 				value: newValue
				// 			});
				// 		});
				// 	}
				// },
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
		function gridReloadjson(){
			var obj=new Object();
			var $tablesearch=document.getElementsByName("tablesearch");
			for(var i=0;i<$tablesearch.length;i++){
			  var st=$tablesearch[i];
			  obj[st.id]=st.value;
			}
			return obj;
		}
 
		</script>
	</body>
</html>
 
		 
