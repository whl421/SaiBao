<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>材料出库</title>
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
				<div class="form-inline form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<input type="hidden" id="applyid" name="applyid" />
					<label  class="date-label col-xs-2 col-sm-2 col-md-2 col-lg-2"><span style="color:red">*</span>申请单号</label>
					<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
						<input type="text" class="form-control" 
						  id="applycode"  name="applycode" />
					</div>	
				</div>
				<div class="form-inline form-group col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<input type="hidden" id="taskid" name="taskid" />
					<label  class="date-label col-xs-2 col-sm-2 col-md-2 col-lg-2">任务编号</label>
					<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
						<input type="text" class="form-control" disabled="disabled"
						  id="taskcode"  name="taskcode" />
					</div>
					<input type="hidden" id="taskid" name="taskid" />
					<label  class="date-label col-xs-2 col-sm-2 col-md-2 col-lg-2">任务标题</label>
					<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
						<input type="text" class="form-control" disabled="disabled"
						  id="tasktitle"  name="tasktitle" />
					</div>
				</div>
				<div id="tlrkDiv" class="col-xs-12 col-sm-12 col-md-12 col-lg-12 " >
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
			var _id = 1;
			$(document).ready(function(){
				init();
			});
			
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
			var $table;
			function init() {
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
					// {
					// 	checkbox: false,  
					// 	visible: false,//是否显示复选框 
					// 	width:30
					// }, 
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
						field: 'unitname',
						title: '计量单位',
						sortable: false,
						width: 60,
						align: 'center'
					},
					{
						field: 'sqnum',
						title: '申请数量',
						sortable: false,
						width: 100,
						align: 'center'
					},
					{
						field: 'ycknum',
						title: '已出库数量',
						sortable: false,
						width: 100,
						align: 'center'
					},
					{
						field: 'applynum',
						title: '出库数量',
						sortable: false,
						width: 100,
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
			}
			/**
			 * 新增
			 */
			function setadd(aobj){
				$(this).attr("disabled",true); 
				var billtype ="2";
				var applyid = $("#applyid").val();
				var applycode = $("#applycode").val();
				var taskid = $("#taskid").val();
				
				if(applycode == null || applycode == ""){
					alert("申请单号不能为空");
					return;
				}
				var data = {
					"billtype" : billtype,
					"applyid" : applyid,
					"taskid" : taskid,
				};
				var str = JSON.stringify(data);
				//获取明细表
				var subData = {};
				subData = $table.bootstrapTable('getData');	//入库明细
				if(subData.length == 0) {
					alert("材料明细不能为空");
					return;
				}
				var j = 0;
				if(subData.length > 0) {
					var list = new Array();
					for(row in subData){
						var applynum = subData[row].applynum;
						var ycknum = subData[row].ycknum;
						var sqnum = subData[row].sqnum;
						if(applynum != null && applynum != "" && applynum != "0" && applynum != "null") {
							j++;
						}
						if(parseFloat(applynum)+parseFloat(ycknum) > parseFloat(sqnum)) {
							alert("已出库数+出库数大于申请数量");
							return;
						}
					}
				}
				if(j == 0) {
					alert("出库数量不能全为空或者全为0");
					return;
				}
				var sublist = JSON.stringify(subData);
				$.ajax({
					url : '${pageContext.request.contextPath}/matStor/outBound.action?rundd='+new Date().getTime(),
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
			$("#applycode").change(function(){
				var mc_val=$(this).val();
				if(mc_val&&mc_val!=""){
					$.ajax({
						url:'${pageContext.request.contextPath}/matStor/getApplyList.action?rundd='+new Date().getTime(),
						type:'post',
						data:{tablejson:mc_val,applytype:"1"},
						dataType:"json",
						async:false,
						success:function(date){
							if(date==null||date=='null'||date=='NULL'||date==' '||date["info"]=='0'){
								if(date["textinfo"]){
									alert(date["textinfo"]);
									// $("#applycode").val("");
								}else
									alert("\u670d\u52a1\u5668\u7e41\u5fd9\uff0c\u63d0\u4ea4\u5931\u8d25");  //服务器繁忙，提交失败
							}else{
								var map = date["map"];
								$("#taskid").val(map["taskid"]);
								$("#applyid").val(map["clapplyid"]);
								$("#taskcode").val(map["taskcode"]);
								$("#tasktitle").val(map["tasktitle"]);
								var subData = date["subList"];
								var _id = 1;
								for(index in subData) {
									var row = {
										"id": _id,
										"storclid": subData[index]["storclid"],
										"sqnum": subData[index]["applynum"],
										"clcode": subData[index]["clcode"],
										"clname": subData[index]["clname"],
										"unitname": subData[index]["unitname"],
										"ycknum" : subData[index]["ycknum"],
										"applynum" : (subData[index]["applynum"]-subData[index]["ycknum"])+""
									};
									_id++;
									$table.bootstrapTable('insertRow', {index: 0, row: row});
								}
							}	
						},
						 error:function(){
								alert("\u000d\u000a\u000d\u000a\u7f51\u7edc\u7e41\u5fd9\uff0c\u63d0\u4ea4\u5931\u8d25"); //网络繁忙，提交失败
						}
					});
				}
			});
		</script>
	</body>
</html>
 
		 
