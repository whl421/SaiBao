<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>showtest- 模板界面</title>
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
	
	
	
	<!--              树形结构引入css样式        	 -->
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/plugins/treeview/bootstrap-treeview.css" rel="stylesheet">
	
	
	
</head>

<body class="gray-bg">
	<nav class="navigation" >
		<span class="title" >首页</span>
		<span class="title" >-</span>
		<span class="title" >导航模板</span>
		<span class="title" >-</span>
		<span class="titlethis" >模板</span>
	</nav>
	 
	
	
    <div class="wrapper wrapper-content animated fadeInRight">
		 <!-- Panel Other -->
			
		<div class="ibox-content">
			<h5>树形选择--级联table表单重加载</h5>
			<div class="row row-lg">
				<div class="col-sm-3 ">
<!-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>树形>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>					 -->
					    <div class="ibox float-e-margins">
<!-- 							<h5>部门操作</h5> -->
					        <div class="ibox-title">
<!-- 					           <div class="form-group treeviewtitle"> -->
<!-- 									<a href="javascript:;"  class="btn btn-primary queryButton btn-sm form-control-btn"> -->
<!-- 											<i class="glyphicon glyphicon-plus"></i>新 增</a> -->
<!-- 									<a href="javascript:;" class="btn btn-info queryButton btn-sm form-control-btn"> -->
<!-- 											<i class="fa fa-wrench"></i>编 辑</a> -->
<!-- 									<a href="javascript:;" class="btn btn-warning queryButton btn-sm form-control-btn"> -->
<!-- 											<i class="glyphicon glyphicon-trash"></i>删 除</a>				 -->
<!-- 					           </div> -->
					        </div>
					        <div class="ibox-content">
								<div id="treeview" class="test"></div>
					        </div>
					    </div>
<!--              树形结构引入js脚本        	 -->
<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/treeview/bootstrap-treeview.js"></script>	

<script type="text/javascript">
// 	var json = '[' +
// 	        '{' +
// 	        '"text": "父节点 1",' +
// 			'"id": "1",'+
// 	        '"nodes": [' +
// 	        '{' +
// 	        '"text": "子节点 1",' +
// 			'"id": "2",'+
// 	        '"nodes": [' +
// 	        '{' +
// 	        '"text": "孙子节点 1",' +
// 			'"id": "3"'+
// 	        '},' +
// 	        '{' +
// 	        '"text": "孙子节点 2",' +
// 			'"id": "4"'+
// 	        '}' +
// 	        ']' +
// 	        '},' +
// 	        '{' +
// 	        '"text": "子节点 2",' +
// 			'"id": "5"'+
// 	        '}' +
// 	        ']' +
// 	        '},' +
// 	        '{' +
// 	        '"text": "父节点 2",' +
// 			'"id": "1"'+
// 	        '},' +
// 	        '{' +
// 	        '"text": "父节点 3",' +
// 			'"id": "1"'+
// 	        '},' +
// 	        '{' +
// 	        '"text": "父节点 4",' +
// 			'"id": "1"'+
// 	        '},' +
// 	        '{' +
// 	        '"text": "父节点 5",' +
// 			'"id": "1"'+
// 	        '}' +
// 	        ']';
	/**
	*树形加载
	*/
	$('#treeview').treeview({
			 color: "#5F6368",
			 expandIcon: 'glyphicon glyphicon-chevron-right',
			 collapseIcon: 'glyphicon glyphicon-chevron-down',
			// nodeIcon: 'fa fa-users',
			// data: json,
		    data: ${treemap},
			onNodeSelected: function (event, node) {
			   alert('您单击了 text=' + node.text +',id=' +node.id);
			}
	});
	
// 	alert(${treemap});
</script>
<!--              树形结构引入js脚本        	 -->	
<!-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>树形结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>					 -->			
				</div>	
				
	
				<div class="col-sm-9 leftbottom">
<!-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>树形级联表格table>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>					 -->				
					<!-- Example Pagination -->
					<div class="example-wrap">
							<h5>人员管理</h5>
							<div class="form-inline">
								 <div class="form-group">
									 <label  class="date-label"></label>
									 <input type="text" class="form-control" id="name" name="tablesearch" placeholder="请名字">
								 </div>
								 <div class="form-group">
									 <label class="date-label" ></label>
									  <select class="selectpicker form-control" multiple data-done-button="true"
									   title="下拉查询" id="sex" name="tablesearch">
									  <option>男</option>
									  <option>女</option>
									</select>
								 </div>
								 <div class="form-group">
									 <label  class="date-label"></label>
									 <input type="text" id="startData" class="form-control" id="workcode" name="tablesearch" placeholder="请工号">
								 </div>
								  <div class="form-group">
									 <label  class="date-label"></label>
									 <input type="text" id="startData" class="form-control" id="birthday" name="tablesearch" placeholder="请选择生日">
								 </div>
								 <div class="form-group ">
									 <a id="search_button" href="javascript:;" class="btn btn-primary queryButton btn-sm form-control-btn"><i class="fa fa-search"></i>查 询</a>
								 </div>
								 
							</div>	
						<div class="example">
							 
<!-- 							 <div class="btn-groups hidden-xs" id="exampleToolbar" role="group"> -->
<!-- 								<div class="btn-group"> -->
<!-- 									<a href="javascript:PopupModalView(null,null,'组织机构>>人员新增','900px','80%','organization/personneladded.html');"  class="btn btn-primary btn-sm"> -->
<!-- 										<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>新增 -->
<!-- 									</a> -->
<!-- 								</div> -->
<!-- 								<div class="btn-group"> -->
<!-- 									<button type="button" class="btn btn-info btn-sm"> -->
<!-- 										<i class="fa fa-wrench" aria-hidden="true"></i>编辑 -->
<!-- 									</button> -->
<!-- 								</div> -->
<!-- 								<div class="btn-group"> -->
<!-- 									<button type="button" class="btn btn-warning btn-sm"> -->
<!-- 										<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>删除 -->
<!-- 									</button> -->
<!-- 								</div> -->
<!-- 							</div> -->
							<div class="scrollbar" >
								<table id="TreeTablePagination"  ></table>
							</div>	
						</div>
					
						<div class="example form-inline">
							<button type="button" id="getrowdate" class="btn btn-primary queryButton"><i class="fa fa-search"></i>获取选择行数据</button>
							<button type="button" id="insertrowdate" class="btn btn-primary queryButton"><i class="fa fa-search"></i>插入行数据</button>
							<button type="button" id="getallrowdate" class="btn btn-primary queryButton"><i class="fa fa-search"></i>获取所有数据</button>
							<button type="button" id="getrow1date" class="btn btn-primary queryButton"><i class="fa fa-search"></i>得到(data-unique-id="id")对应的数据</button>
							<button type="button" id="deleterowdate" class="btn btn-primary queryButton"><i class="fa fa-search"></i>删除选中行数据</button>	
							<button type="button" id="loaddate" class="btn btn-primary queryButton"><i class="fa fa-search"></i>加载数据</button>	
						</div>
						
					</div>
					<!-- End Example Pagination -->
  <script type="text/javascript">
		
		var $table;
		(function() {
			$table=$("#TreeTablePagination");
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
			
			
				 $table.bootstrapTable('destroy');
				 $table.bootstrapTable({
				    url: "http://127.0.0.1:8088/RecoveryEPA/user/testtable.action",
				    height: "450",			 //行高，如果没有设置height属性，
					uniqueId: "id",          //每一行的唯一标识，一般为主键列
					cache: false,           //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					striped: true,          //是否显示行间隔色
					pagination:true,
					sidePagination: "server",    //分页方式：client客户端分页，server服务端分页（*）
					pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
				    iconSize: 'outline',
				    showColumns: true,
					showRefresh: true,
					showToggle: true,
					showColumns: true,
					pageSize: 10, 
					pageList: [5, 10, 25],        //可供选择的每页的行数（*）
					toolbar: '#exampleToolbar', //使用工具条
				    icons: {
				      refresh: 'glyphicon-repeat',
				      toggle: 'glyphicon-list-alt',
				      columns: 'glyphicon-list'
				    },
				  //得到查询的参数
		         queryParams : function (params) {
		                    //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
		                    var temp = {   
		                        rows: params.limit,                         //页面大小
		                        page: (params.offset / params.limit) + 1,   //页码
		                        sort: params.sort,      //排序列名  
		                        sortOrder: params.order ,//排位命令（desc，asc） 
				 			    actjson:JSON.stringify(gridReloadjson())	//查询参数	
		                    };
						//	alert(JSON.stringify(params));
		                    return temp;
		         },
					columns:[{
						checkbox: true,  
						visible: true                  //是否显示复选框  
					}, {
						field: 'id',
						title: 'id',
						hidden:true,
		//				visible:false
					}, {
						field: 'name',
						title: '姓名',
						sortable: true,
						width: 320,
						align: 'center'
					
					}, {
						field: 'sex',
						title: '性别',
						sortable: true,
						width: 120,
						align: 'center'
						
					}, {
						field: 'birthday',
						title: '生日',
						sortable: true,
						width: 120,
						align: 'center'
						
					}, {
						field: 'remarks',
						title: '备注',
						sortable: true,
						width: 120,
						align: 'center'
					}, {
						field: 'parentid',
						title: '父级id',
						width: 120,
						hidden:true,
					}]
					 
				  });
		//获取选中行数据
			$("#getrowdate").click(function(){
				var rows = $table.bootstrapTable('getSelections');
					if (rows.length > 0) {
						// var IDs = rows[0].id;
						alert(JSON.stringify(rows));
					}else{
						alert("未选中数据");
					}
			});
		//插入行数据
			$("#insertrowdate").click(function(){
				var row={
		        "id": 0,
		        "name": "插入数据1",
		        "price": "&yen;0",
		        "column1": "c10",
		        "column2": "c20",
		        "column3": "c30",
		        "column4": "c40"
		    };
				$table.bootstrapTable('insertRow', {index: 0, row: row});
			});
		//获取所有数据
			$("#getallrowdate").click(function(){
				var date=$table.bootstrapTable('getData');
				alert(JSON.stringify(date));
			});
		//删除选中的数据
			$("#deleterowdate").click(function(){
				var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
				  return row.price //price
				})
				if(ids.length==0){
					alert("请选择删除行！！");
					return;
				}
				$table.bootstrapTable('remove', {
				  field: 'price',  //price
				  values: ids
				})
				// $table.bootstrapTable('removeAll'); //清空列表
			});
		//得到第一行数据
			$("#getrow1date").click(function(){
				var row1=$table.bootstrapTable('getRowByUniqueId', 1);
				alert(JSON.stringify(row1));
			});
		//加载数据
			$("#loaddate").click(function(){
				var data=[{
				    "id": 0,
				    "name": "插入数据1",
				    "price": "&yen;0",
				    "column1": "c10",
				    "column2": "c20",
				    "column3": "c30",
				    "column4": "c40"
				},{
				    "id": 1,
				    "name": "插入数据2",
				    "price": "&yen;0",
				    "column1": "c10",
				    "column2": "c20",
				    "column3": "c30",
				    "column4": "c40"
				}];
				$table.bootstrapTable('load', data);
			});
		//数据追加  $table.bootstrapTable('append', data).
		//数据前加  $table.bootstrapTable('prepend', data)
		//修改某行数据  $table.bootstrapTable('updateRow', {index: 1, row: row})
		//列表刷新 $table.bootstrapTable('refresh')
			
		//展示某列 $table.bootstrapTable('showColumn', 'name')
		//隐藏某列 $table.bootstrapTable('hideColumn', 'name')	
		
		
		// $table.bootstrapTable('scrollTo', 0)  滚动到第一行
		// $table.bootstrapTable('scrollTo', number) 滚动到第number 行
		// $table.bootstrapTable('scrollTo', 'bottom')	滚动到底部展示
		// $table.bootstrapTable('selectPage', 1)  翻页到第一页
		// $table.bootstrapTable('prevPage')	翻页到前一页
		// $table.bootstrapTable('nextPage')	翻页到最后一页
		 // $table.bootstrapTable('toggleView') 竖视图
		
		})();
		 
   </script>				
				
<!-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>树形级联表格table结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>					 -->					
				</div>
				
		 
		  
		  
		  
		  
			</div>
		</div>
	
		
		<!-- End Panel Other -->
		
    </div>
	
	
  
   
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js"></script>
</body>

</html>
