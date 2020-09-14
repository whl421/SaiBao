<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>角色配置 - 角色人员配置</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">
	<meta http-equiv="Access-Control-Allow-Origin" content="*" />
    <link rel="shortcut icon" href="H5.ico"> 
	<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/font-awesome.css?v=4.4.0" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/jquery.min.js?v=2.1.4"></script>
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap.min.js?v=3.3.6"></script>
	
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/js/dist/bootstrap-table.min.css" rel="stylesheet">
	<!-- <script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/bootstrap-table/bootstrap-table.js"></script> -->
	
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/dist/bootstrap-table.min.js"></script>
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/dist/bootstrap-table-locale-all.js"></script>
	
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/manageIntegral/css/bootstrap-select.css">
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap-select.js"></script>
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/organization.css?v=4.1.0" rel="stylesheet">
	
	 
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js"></script>
	<style type="text/css">
		.rightbottom{
			border-right: 2px solid #D0D0D0;
			
		}
	</style>
	
</head>

<body class="gray-bg">
	<nav class="navigation" >
		<span class="title" >菜单配置</span>
		<span class="title" >-</span>
		<span class="titlethis" >角色人员配置</span>
	</nav>
	
	
    <div class="wrapper wrapper-content animated fadeInRight">
		 <!-- Panel Other -->
			
		<div class="ibox-content">
			
			<div class="row row-lg">
				<div class="col-sm-4 rightbottom">
					    <div class="ibox float-e-margins">
							<h5>角色选择</h5>
					        <div class="ibox-content">
									<div class="btn-groups hidden-xs" id="roleToolbar" role="group">
								<c:if test="${button.add!=null}" >		
									<div id="add" class="btn-group">
										<a  href="javascript:PopupModalView(null,'refreshrole','角色配置>>角色新增','800px','90%','${pageContext.request.contextPath}/menu/addrolejsp.action');"  class="btn btn-primary btn-sm">
											<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>新增
										</a>
									</div>
								</c:if>	
								<c:if test="${button.update!=null}" >
									<div id="update" class="btn-group">	
										<a  href="javascript:updaterole();"  class="btn btn-info btn-sm">
											<i class="fa fa-wrench" aria-hidden="true"></i>编辑
										</a>
									</div>
									<script type="text/javascript">
										function updaterole(){
											var rows = $roletable.bootstrapTable('getSelections');
											if (rows.length > 0) {
												if(rows.length>1){
													alert("编辑只能选择一行数据");
												}else{
													PopupModalView(rows[0],'refreshrole','角色配置>>角色编辑','800px','90%','${pageContext.request.contextPath}/menu/updaterolejsp.action');
												} 
											}else{
												alert("未选中数据");
											}
										}
									</script>
								</c:if>	
								<c:if test="${button.delete!=null}" >	
									<div id="delete" class="btn-group">		
										<a href="javascript:reovmerole();"  class="btn btn-warning btn-sm">
											<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>删除
										</a>
									</div>
									<script type="text/javascript">
										function reovmerole(){
											var rows = $roletable.bootstrapTable('getSelections');
											if (rows.length > 0) {
												
												 var list=new Array();
												 for(row in rows){
												 	list[row]=rows[row].id;
												 }
												 var durl="${pageContext.request.contextPath}/menu/removerole.action";
												 var ajax=doajax(durl,list);
												 if(ajax){
												 	$roletable.bootstrapTable('selectPage', 1);
													$roletable.bootstrapTable('refresh');
												 }
											}else{
												alert("未选中数据");
											}
										}
									</script>
								</c:if>	
									</div>
								<table id="roletable" class="test"></table>
								
					        </div>
					    </div>


<script type="text/javascript">
var	$roletable;
var	$roleid;
$(function(){
	$roletable=$("#roletable");
		 $roletable.bootstrapTable('destroy');
		 $roletable.bootstrapTable({
		   url: "${pageContext.request.contextPath}/menu/role.action",
		  //  height: "450",			 //行高，如果没有设置height属性，
		 // uniqueId: "id",          //每一行的唯一标识，一般为主键列
		 //	cache: false,           //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			striped: true,          //是否显示行间隔色
			pagination:true,
			sidePagination: "client",    //分页方式：client客户端分页，server服务端分页（*）
			pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
			iconSize: 'outline',
			search: true,
		//	showColumns: true,
			showRefresh: true,
			showToggle: true,
			//showColumns: true,
			pageSize: 25, 
			pageList: [10, 25, 50],        //可供选择的每页的行数（*）
		//	toolbar: '#roleToolbar', //使用工具条
			detailView:true,
			onExpandRow: function (index, row, $detail) {
				$detail.html(row["desc"]);
			},
			onClickRow:function (row,$element) {
				$('.info').removeClass('info');
				$($element).addClass('info');
				$roleid=row.id;
				/**
				 * 这里刷新人员表格
				 */
				gridReload_byrole($roleid);
			 },
			icons: {
			  refresh: 'glyphicon-repeat',
			  toggleOff: 'glyphicon-list-alt icon-list-alt',
			  toggleOn: 'glyphicon-list-alt icon-list-alt',
			  columns: 'glyphicon-list',
			  detailOpen: 'glyphicon-plus icon-plus',
			  detailClose: 'glyphicon-minus icon-minus',
			  fullscreen: 'glyphicon-fullscreen'
			},
			columns:[{
				checkbox: true,  
				visible: true                  //是否显示复选框  
			}
			// ,  {
			// 	field: 'id',
			// 	title: 'id',
			// 	hidden:true,
			// }
			, {
				field: 'rolecode',
				title: '角色编号',
				sortable: true,
				width: 120,
				align: 'center'
			}, {
				field: 'rolename',
				title: '角色名称',
				width: 120,
				align: 'center'
			}, {
				field: 'desc',
				title: '备注',
				sortable: true,
				width: 120,
				align: 'center',
				hidden:true
			}]
		  });
		// 设置隐藏列
	//	$roletable.bootstrapTable('hideColumn', 'id');
		$roletable.bootstrapTable('hideColumn', 'desc');
		
		function columnbuttonFormatter(value,row,index){
				return [
			"<button type='button' onclick='removebutton("+index+")' class='btn btn-default btn-sm'><i class='glyphicon glyphicon-trash'></i>移除</button>&nbsp;&nbsp;",
			].join("");
		}
	
})	

 /**
  * 刷新角色
  */
 function gridReload_roletable(){
 	var search= {
 	    silent: true,
 	}
 	$roletable.bootstrapTable('selectPage', 1);
 	$roletable.bootstrapTable('refresh', search);
 }
 
</script>
				</div>	
				
				<div class="col-sm-8 leftbottom" style="margin-left: -1px;">
					<!-- Example Pagination -->
					<div class="ibox float-e-margins">
							<h5>角色人员管理</h5>
							<div class="ibox-content">							 
								<div class="btn-groups hidden-xs" id="exampleToolbar" role="group">
								
								<c:if test="${button.adduser!=null}" >	
								<div id="adduser" class="btn-group">
									<a href="javascript:addroleuser();"  class="btn btn-primary btn-sm">
										<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>人员新增
									</a>
								</div>
								<script type="text/javascript">
									function addroleuser(){
										if($roleid==null||$roleid==""){
											alert("请先选择角色");
											return;
										}
										PopupModalView(null,'refreshuser','角色人员>>人员新增','800px','90%','${pageContext.request.contextPath}/menu/roleadduser.action?roleid='+$roleid);
									}
								</script>
								</c:if>	
							</div>
							<div class="scrollbar" >
								<table id="roleusertable"  ></table>
							</div>	
						</div>
						
					</div>
					<!-- End Example Pagination -->
  <script type="text/javascript">
	  
	  
	/**
	 * 回调函数
	 * @param {Object} usetype
	 * @param {Object} data
	 */
	function dofunction(usetype,data){
		if(usetype=="refreshuser"){
			gridReload_byrole($roleid);
		}
		if(usetype=="refreshrole"){
			gridReload_roletable();
		}
	}  
	var $roleusertable;
		(function() {
			$roleusertable=$("#roleusertable");
				 
				 $roleusertable.bootstrapTable('destroy');
				 $roleusertable.bootstrapTable({
					cache: false,           //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					striped: true,          //是否显示行间隔色
					pagination:true,
					sidePagination: "client",    //分页方式：client客户端分页，server服务端分页（*）
					pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
				    iconSize: 'outline',
					search: true,
				    showColumns: true,
					//showRefresh: true,
					showToggle: true,
					pageSize: 15, 
					pageList: [10, 15, 25],        //可供选择的每页的行数（*）
					toolbar: '#exampleToolbar', //使用工具条
				    icons: {
				      refresh: 'glyphicon-repeat',
				      toggleOff: 'glyphicon-list-alt icon-list-alt',
          	  		  toggleOn: 'glyphicon-list-alt icon-list-alt',
				      columns: 'glyphicon-list'
				    },
					columns: setcolumns()
				  });
			
			function setcolumns(){
				var columns=[
					// 	{
					// 	field: 'id',
					// 	title: 'id',
					// 	hidden:true
					// },{
					// 	field: 'userid',
					// 	title: 'userid',
					// 	hidden:true
					// }, 
					{
						field: 'columnbutton',
						title: '操作',
						align: 'center',
						width: 120,
						formatter: columnbuttonFormatter
					}, {
						field: 'departname',
						title: '部门',
						sortable: true,
						width: 120,
						align: 'center'
					}, {
						field: 'usercode',
						title: '人员编号',
						sortable: true,
						width: 120,
						align: 'center'
					}, {
						field: 'username',
						title: '名称',
						sortable: true,
						width: 120,
						align: 'center'
					}, {
						field: 'sex',
						title: '性别',
						sortable: true,
						width: 60,
						align: 'center',
						formatter: sexFormatter
					}, {
						field: 'dutyname',
						title: '职务',
						sortable: true,
						width: 120,
						align: 'center'
					}];
				if("${button.deleteuser}"&&"${button.deleteuser}"!=null&&"${button.deleteuser}"!=""){
				}else{
					columns.splice(0,1);
				}	
					
					
				return 	columns;
			}	  
				  
				  
				// $("#roleusertable").bootstrapTable('hideColumn', 'id');
				// $("#roleusertable").bootstrapTable('hideColumn', 'userid');
				  
			function sexFormatter(value,row,index){
				 if(value=='1')return "男";
				 if(value=='0')return "女";
				  
			}
			function columnbuttonFormatter(value,row,index){
				var bt1="";
				if("${button.deleteuser}"&&"${button.deleteuser}"!=null&&"${button.deleteuser}"!=""){
					bt1="<button type='button' onclick='gridReload_byremoveuser("+row.id+")' class='btn btn-warning btn-sm'><i class='glyphicon glyphicon-trash'></i>移除</button>&nbsp;&nbsp;";
				}
				
				return bt1;
				// 	return [
				// "<button type='button' onclick='gridReload_byremoveuser("+row.id+")' class='btn btn-warning btn-sm'><i class='glyphicon glyphicon-trash'></i>移除</button>&nbsp;&nbsp;",
				// ].join("");
			 }
		
		})();
		
	function gridReload_byrole(indexid){
		var search= {
		    url: "${pageContext.request.contextPath}/menu/roleuser.action",
		    silent: true,
		    query:{
		        roleid:indexid,
		        type:"refresh"
		    },
		}
		$roleusertable.bootstrapTable('selectPage', 1);
		$roleusertable.bootstrapTable('refresh', search);
	}
	function gridReload_byremoveuser(indexid){
		var search= {
		    url: "${pageContext.request.contextPath}/menu/roleuser.action",
		    silent: true,
		    query:{
		        roleuserid:indexid,
				roleid:$roleid,
		        type:"remove"
		    },
		}
		$roleusertable.bootstrapTable('selectPage', 1);
		$roleusertable.bootstrapTable('refresh', search);
	}	
	
	
		 
   </script>				
				
				</div>
				
		 
		  
		  
		  
		  
			</div>
		</div>
	
		
		
    </div>
	
	
  
   
	
<script type="text/javascript">
var button=false;
function doajax(_url,obj){
	 var str=JSON.stringify(obj);
	// str=encodeURI(encodeURI(str));
	 var ajax=true;
	 if(button)return;
	 button=true;
		$.ajax({
			url:_url+'?rundd='+new Date().getTime(),
			type:'post',
		 	data:{tablejson:str},
	        dataType:"json",
		    async:false,
		    success:function(date){
			 	if(date==null||date=='null'||date=='NULL'||date==' '||date["info"]=='0'){
			 		if(date["textinfo"]){
			 			alert(date["textinfo"]);
			 		}else
			 		alert("\u670d\u52a1\u5668\u7e41\u5fd9\uff0c\u63d0\u4ea4\u5931\u8d25");  //服务器繁忙，提交失败
			 		ajax=false;
			 	}else{
			 		ajax=true;
			 	}	
			 	button=false;
		    },
		     error:function(){
					alert("\u000d\u000a\u000d\u000a\u7f51\u7edc\u7e41\u5fd9\uff0c\u63d0\u4ea4\u5931\u8d25"); //网络繁忙，提交失败
					button=false;
					ajax=false;
			}
		});
		return ajax;
}

</script>



</body>

</html>
