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
		<span class="titlethis" >委托单推送</span>
	</nav> 
    <div class="wrapper wrapper-content animated fadeInRight">
		<div class="ibox-content">
			<div class="row row-lg">
				<div class="col-sm-12">
					<div class="example-wrap">
						<div class="form-inline">
							<div class="form-group">
								<label  class="date-label"></label>
								<input type="text" class="form-control" id="ordercode" name="tablesearch" placeholder="委托编号">
							</div>
							<div class="form-group">
								<label  class="date-label"></label>
								<input type="text" class="form-control" id="ordername" name="tablesearch" placeholder="委托名称">
							</div>
							<div class="form-group">
								<label  class="date-label"></label>
								<input type="text" class="form-control" id="khname" name="tablesearch" placeholder="客户名称">
							</div>
							<div class="form-group" style="width:220px" >
								<input type="text" id="orderdate" class="form-control" style="width:210px" name="tablesearch" placeholder="委托时间选择">
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
		* 推送
		*/
		function updatetable(index){
			var _url="${pageContext.request.contextPath}/delegatePush/tsForm.action?tablejson="+index;
			PopupModalView(null,'refresh','委托单推送>>推送','60%','600px',_url);
		}
		/**
		* 任务
		*/
		function infotable(index){
			var _url="${pageContext.request.contextPath}/order/infoForm.action?tablejson="+index+"&type=1";
			PopupModalView(null,'refresh','委托单推送>>任务','60%','500px',_url);
		}
		/**
		表格加载
		*/
			
		var $table;
		$(function() {
			$("#exampleTablePagination").bootstrapTable('destroy');
			$table= $('#exampleTablePagination').bootstrapTable({
				url: "${pageContext.request.contextPath}/delegatePush/list.action",
				//height: "650",			 //行高，如果没有设置height属性，
				uniqueId: "orderid",          //每一行的唯一标识，一般为主键列
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
					$detail.html("拒绝理由："+row["confirmmemo"]);
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
					field: 'orderid',
					title: 'orderid',
					hidden: true
				}, 
				{
					field: 'columnbutton', //后台未传输当前对象的值
					title: '操作',
					width: 30,
					visible:true,
					align: 'center',
					formatter:rowbutton
					//hidden: true
				},
				{
					field: 'ordercode',
					title: '委托编号',
					sortable: true,
					width: 100,
					align: 'center',
					formatter:rowOrderCode
				},
				{
					field: 'ordername',
					title: '委托名称',
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
					field: 'ordertime',
					title: '委托时间',
					sortable: true,
					width: 80,
					align: 'center'
				},
				{
					field: 'khname',
					title: '客户名称',
					sortable: true,
					width: 60,
					align: 'center'
				},
				{
					field: 'departname',
					title: '子中心名称',
					sortable: true,
					width: 80,
					align: 'center'
				},
				{
					field: 'tsdate',
					title: '推送日期',
					sortable: true,
					width: 80,
					align: 'center'
				},
				{
					field: 'liststate',
					title: '子中心状态',
					sortable: true,
					width: 100,
					align: 'center',
					formatter : liststate
				},
				{
					field: 'confirmmemo',
					title: '子中心意见',
					sortable: true,
					width: 100,
					align: 'center'
				},
				]
				 
			});
			// 设置隐藏列
			$table.bootstrapTable('hideColumn', 'orderid');
			function rowOrderCode(value,row,index) {
				var str = "";
				if(row.liststate == "3") {
					str = "<span style='background-color:#F3565D;color:white;padding:3px 6px 3px 6px;'>"+value+"</span>";
				} else {
					str = value;
				}
				return str;
			}
			function liststate(value,row,index) {
				if(row.liststate == "1") {
					return "待确认";
				}
				if(row.liststate == "2") {
					return "接收";
				}
				if(row.liststate == "3") {
					return "拒绝";
				}
				if(row.liststate == "4") {
					return "任务发起";
				}
				if(row.liststate == "5") {
					return "任务接收";
				}
				if(row.liststate == "6") {
					return "任务拒绝";
				}
				if(row.liststate == "") {
					return "";
				}
			}
			function rowbutton(value,row,index){
	// 		  	alert(row.id);  value为空值
	//			alert(index);
				var bt1 = "";
				var liststate = row.liststate;
				if(liststate == "3" || liststate == "" || liststate == null) {
					if("${button.update}"&&"${button.update}"!=null&&"${button.update}"!=""){
						bt1 += "<button type='button' onclick='updatetable("+row.orderid+")' class='btn btn-info btn-sm'><i class='glyphicon glyphicon-wrench'></i>&nbsp;推送</button>&nbsp;";
					}
				} else {
					if("${button.taskList}"&&"${button.taskList}"!=null&&"${button.taskList}"!=""){
						bt1 += "<button type='button' onclick='infotable("+row.orderid+")' class='btn btn-success btn-sm'><i class='glyphicon glyphicon-search'></i>&nbsp;任务</button>";
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

