<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>

<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>organization- 组织机构</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">
	<meta http-equiv="Access-Control-Allow-Origin" content="*" />
    <link rel="shortcut icon" href="H5.ico"> 
	<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/font-awesome.css?v=4.4.0" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/jquery.min.js?v=2.1.4"></script>
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap.min.js?v=3.3.6"></script>
	
<!-- 	列表控件 -->
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
	<!-- <script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script> -->
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/demo/bootstrap-table-demo.js"></script>
<!-- 下拉控件	 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/manageIntegral/css/bootstrap-select.css">
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap-select.js"></script>

    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/organization.css?v=4.1.0" rel="stylesheet">
    
<!--     时间控件 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/layui/css/layui.css"  media="all">
	<script src="${pageContext.request.contextPath}/layui/layui.js" charset="utf-8"></script> 

	<style>
	   .fixed-table-container thead th .sortable {
        background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABMAAAATCAQAAADYWf5HAAAAkElEQVQoz7X QMQ5AQBCF4dWQSJxC5wwax1Cq1e7BAdxD5SL+Tq/QCM1oNiJidwox0355mXnG/DrEtIQ6azioNZQxI0ykPhTQIwhCR+BmBYtlK7kLJYwWCcJA9M4qdrZrd8pPjZWPtOqdRQy320YSV17OatFC4euts6z39GYMKRPCTKY9UnPQ6P+GtMRfGtPnBCiqhAeJPmkqAAAAAElFTkSuQmCC");
        cursor: pointer;
        background-position: right;
        background-repeat: no-repeat;
        padding-right: 30px;
       }

      .fixed-table-container thead th .asc {
        background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABMAAAATCAYAAAByUDbMAAAAZ0lEQVQ4y2NgGLKgquEuFxBPAGI2ahhWCsS/gDibUoO0gPgxEP8H4ttArEyuQYxAPBdqEAxPBImTY5gjEL9DM+wTENuQahAvEO9DMwiGdwAxOymGJQLxTyD+jgWDxCMZRsEoGAVoAADeemwtPcZI2wAAAABJRU5ErkJggg==");
        cursor: pointer;
        background-position: right;
        background-repeat: no-repeat;
        padding-right: 30px;
     }

    .fixed-table-container thead th .desc {
        background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABMAAAATCAYAAAByUDbMAAAAZUlEQVQ4y2NgGAWjYBSggaqGu5FA/BOIv2PBIPFEUgxjB+IdQPwfC94HxLykus4GiD+hGfQOiB3J8SojEE9EM2wuSJzcsFMG4ttQgx4DsRalkZENxL+AuJQaMcsGxBOAmGvopk8AVz1sLZgg0bsAAAAASUVORK5CYII= ");
        cursor: pointer;
        background-position: right;
        background-repeat: no-repeat;
        padding-right: 30px;
    }
	.pull-right.pagination-detail{
display:none;
}
.scrollbar table{
		min-width:800px;
	}
	</style>
	
	
</head>

<body class="gray-bg">
	<nav class="navigation" >
		<span class="title" >委托单管理</span>
		<span class="title" >-</span>
		<span class="titlethis" >任务接收处理</span>
	</nav> 
    <div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content">
			<div class="row row-lg">
				<div class="col-sm-12">
					<div class="example-wrap">
						<div class="form-inline">
							<div class="form-group">
								<label  class="date-label"></label>
								<input type="text" class="form-control" id="taskcode" name="tablesearch" placeholder="任务编号">
							</div>
							<div class="form-group">
								<label  class="date-label"></label>
								<input type="text" class="form-control" id="tasktitle" name="tablesearch" placeholder="任务标题">
							</div>
							<div class="form-group ">
								<a id="search_button" href="javascript:;" class="btn btn-primary queryButton btn-sm form-control-btn"><i class="fa fa-search"></i>查 询</a>
							</div>
						</div>	
						<div class="example">
							<div class="scrollbar" >
								<table id="exampleTablePagination" >
								</table>
							</div>	
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js"></script>
	<script type="text/javascript">
		layui.use('laydate', function(){
			var laydate = layui.laydate;
			//日期时间范围
			laydate.render({
				elem: '#orderdate'
				,type: 'date'
				,format: 'yyyy-MM-dd'
				,range: "--"
				// ,showBottom: false
				,trigger: 'click'
			});
		});
		/**
		 * 回调函数
		 * @param {Object} usetype
		 * @param {Object} data
		 */
		function dofunction(usetype,data){
			if(usetype=="refresh"){
				$table.bootstrapTable('selectPage', 1);
				$table.bootstrapTable('refresh');
			}
		}
		
		/**
		* 接收
		*/
		function accept(index){
			var _url="${pageContext.request.contextPath}/taskCl/acceptForm.action?tablejson="+index;
			PopupModalView(null,'refresh','任务接收处理>>接收','60%','80%',_url);
		}
		/**
		* 处理
		*/
		function handle(index){
			var _url="${pageContext.request.contextPath}/taskCl/handleForm.action?tablejson="+index;
			PopupModalView(null,'refresh','任务接收处理>>处理','60%','85%',_url);
		}
		/**
		* 添加协作
		*/
		function addxz(index){
			var _url="${pageContext.request.contextPath}/taskCl/addxzForm.action?tablejson="+index;
			PopupModalView(null,'refresh','任务接收处理>>添加协作','60%','75%',_url);
		}
		/**
		* 协作处理
		*/
		function xzhandle(index){
			var _url="${pageContext.request.contextPath}/taskCl/xzhandleForm.action?tablejson="+index;
			PopupModalView(null,'refresh','任务接收处理>>协作处理','60%','75%',_url);
		}
		/**
		表格加载
		*/
			
		var $table;
		$(function() {
			$("#exampleTablePagination").bootstrapTable('destroy');
			$table= $('#exampleTablePagination').bootstrapTable({
				url: "${pageContext.request.contextPath}/taskCl/list.action",
				//height: "650",			 //行高，如果没有设置height属性，
				uniqueId: "tasklistid",          //每一行的唯一标识，一般为主键列
				cache: false,           //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
				striped: true,          //是否显示行间隔色
				sortable: true, //是否启用排序
				sort:"ordertime",
				sortOrder: "desc",
				sidePagination: "server",    //分页方式：client客户端分页，server服务端分页（*）
				pagination:true,
				mobileresponsive:true,
				queryParamsType: 'limit',
				pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
				iconSize: 'outline',
				showColumns: true,
				showRefresh: true,
				//refresh:{pageNumber:1},
				paginationDetailHAlign: 'left',//指定 分页详细信息 在水平方向的位置。'left' 或 'right'。
				showToggle: true,
				showColumns: true,
				pageSize: 10, 
				pageList: [5, 10, 20],        //可供选择的每页的行数（*）
				toolbar: '#exampleToolbar', //使用工具条
				detailView:true,
				icons: {
					detailOpen: 'glyphicon-plus icon-plus',
					detailClose: 'glyphicon-minus icon-minus',
					refresh: 'glyphicon-repeat',
					toggle: 'glyphicon-list-alt',
					columns: 'glyphicon-list'
				},
				onExpandRow: function (index, row, $detail) {
				  /* eslint no-use-before-define: ["error", { "functions": false }]*/
					$detail.html("上一阶段备注信息："+row["upmemo"]);
				},
				//得到查询的参数
				queryParams : function (params) {
	//              //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
					var temp = {   
						rows: params.limit,                         //页面大小
						page: (params.offset / params.limit) + 1,   //页码
						sort: params.sort,      //排序列名  
						sortOrder: params.order, //排位命令（desc，asc）
						searchjson:JSON.stringify(gridReloadjson())	//查询参数			 
					};
					return temp;
				},
				columns:[
				// {
				// 	checkbox: true,  
				// 	visible: true                  //是否显示复选框  
				// }, 
				{
					field: 'tasklistid',
					title: 'tasklistid',
					hidden: true
				}, 
				{
					field: 'columnbutton', //后台未传输当前对象的值
					title: '操作',
					width: 100,
					visible:true,
					align: 'center',
					formatter:rowbutton
					//hidden: true
				},
				{
					field: 'taskcode',
					title: '任务编号',
					sortable: true,
					width: 100,
					align: 'center',
					formatter:rowOrderCode
				},
				{
					field: 'tasktitle',
					title: '任务标题',
					sortable: true,
					width: 100,
					align: 'center'
				
				},
				{
					field: 'ordercode',
					title: '委托单号',
					sortable: true,
					width: 100,
					align: 'center'
				
				},
				{
					field: 'ordername',
					title: '委托单名称',
					sortable: true,
					width: 100,
					align: 'center'
				
				},
				{
					field: 'orderuser',
					title: '委托人',
					sortable: true,
					width: 100,
					align: 'center'
				
				},
				{
					field: 'upexecusername',
					title: '上一阶段完成人',
					sortable: true,
					width: 80,
					align: 'center'
				},
				{
					field: 'bjdstate',
					title: '状态',
					sortable: true,
					width: 80,
					align: 'center',
					formatter : bjdstate
				},
				]
				 
			});
			// 设置隐藏列
			$table.bootstrapTable('hideColumn', 'tasklistid');
			function bjdstate(value,row,index) {
				if(row.bjdstate == "1") return "待接收";
				if(row.bjdstate == "2") return "已接收";
				if(row.bjdstate == "3") return "已拒绝";
				if(row.bjdstate == "4") return "已完成";
				if(row.bjdstate == "5") return "移交退回";
			}
			function rowOrderCode(value,row,index) {
				var str = "";
				if(row.liststate == "3") {
					str = "<span style='background-color:#F3565D;color:white;padding:3px 6px 3px 6px;'>"+value+"</span>";
				} else {
					str = value;
				}
				return str;
			}
			function rowbutton(value,row,index){
				
				var bt1 = "";
				if(row.bjdstate == "1") {	//接收
					if("${button.accept}"&&"${button.accept}"!=null&&"${button.accept}"!=""){
						bt1 += "<button type='button' onclick='accept("+row.tasklistid+")' class='btn btn-success btn-sm'style='width:80px;'><i class='glyphicon glyphicon-check'></i>&nbsp;接收</button>&nbsp;";
					}
				} else if(row.bjdstate == "2" || row.bjdstate == "5") {
					if(row.exec != "0") {	//当前登录人为执行人,显示处理和添加协助按钮
						if("${button.handle}"&&"${button.handle}"!=null&&"${button.handle}"!=""){
							bt1 += "<button type='button' onclick='handle("+row.tasklistid+")' class='btn btn-success btn-sm'style='width:80px;'><i class='glyphicon glyphicon-edit'></i>&nbsp;处理</button>&nbsp;";
						}
						if("${button.addxz}"&&"${button.addxz}"!=null&&"${button.addxz}"!=""){
							bt1 += "<button type='button' onclick='addxz("+row.tasklistid+")' class='btn btn-info btn-sm'style='width:80px;'><i class='glyphicon glyphicon-plus'></i>&nbsp;添加协作</button>&nbsp;";
						}
					}
					if(row.xz != "0") {		//当前登录人为协作人,显示协助处理按钮
						if("${button.xzhandle}"&&"${button.xzhandle}"!=null&&"${button.xzhandle}"!=""){
							bt1 += "<button type='button' onclick='xzhandle("+row.tasklistid+")' class='btn btn-success btn-sm'style='width:80px;'><i class='glyphicon glyphicon-wrench'></i>&nbsp;协作处理</button>&nbsp;";
						}
					}
				}
				if(bt1 == "") {
					bt1 = "----";
				}
				return bt1;
			}
			/**
			 * 表格搜索 表格查询  $table
			 */
			$("#search_button").click(function(){
				gridReload();
			})
			/**
			 * 表格搜索 表格查询 回车事件 $table
			 */
			var $tablesearch=document.getElementsByName("tablesearch");
			$.each($tablesearch,function(index,optionval){      
					// alert($(this).val());
				$(this).keydown(function(event){
					if(event.which=="13")gridReload();
				})			 
			}); 
			/**
			 * 表格搜索 表格查询  $table
			 */ 
			function gridReload(){
				var search= {
				  //  url: "http://127.0.0.1:8088/mes/tablegetJsonstable.action",
					silent: true,
					query:{
						type:1,
						level:2
					},
				//	actjson:obj
				}
				$table.bootstrapTable('selectPage', 1);
				$table.bootstrapTable('refresh', search);
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

		});
   </script>
</body>
</html>

