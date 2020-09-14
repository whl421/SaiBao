<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>菜单配置 - 模板界面</title>
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
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/treeview/bootstrap-treeview.min.css" rel="stylesheet">
	
	<style type="text/css">
		.test{
			overflow-x: scroll;
		}
		.list-group li
		{  
		    white-space: nowrap;  /*强制span不换行*/
		}
	</style>
	
</head>

<body class="gray-bg">
	<nav class="navigation" >
		<span class="title" >菜单配置</span>
		<span class="title" >-</span>
		<span class="titlethis" >系统菜单配置</span>
	</nav>
	
	
	<div class="modal inmodal fade" id="myModa26" tabindex="-1" role="dialog"  aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	                <h4 class="modal-title" id="usemenuname">新增</h4>
					<div class="form-group">
						<label  class="date-label col-sm-2">编号&nbsp;<span style="color:red">*</span></label>
						<div class="col-sm-10">
						<input type="text" class="form-control" 
						 null="no" id="htmlcode" name="manageIntegral" >
						</div> 
					</div>
					<div class="form-group">
						<label  class="date-label col-sm-2">名称&nbsp;<span style="color:red">*</span></label>
						<div class="col-sm-10">
						<input type="text" class="form-control" 
						 null="no" id="htmlname" name="manageIntegral" >
						</div> 
					</div>
					<div class="form-group">
						<label  class="date-label col-sm-2">路径&nbsp;</label>
						<div class="col-sm-10">
						<input type="text" class="form-control" 
						 null="no" id="urlaction" name="manageIntegral" >
						</div> 
					</div>
					<div class="form-group">
						<label  class="date-label col-sm-2">类型&nbsp;<span style="color:red">*</span></label>
						<div class="col-sm-10">
						<select type="text" class="form-control" 
						 null="no" id="type" name="manageIntegral" ></select>
						</div> 
					</div>
					<div class="form-group">
						<label  class="date-label col-sm-2">图标icon&nbsp;<span style="color:red">*</span></label>
						<div class="col-sm-10">
						<input type="text" class="form-control" 
						 null="no" id="icon" name="manageIntegral" >
						</div> 
					</div>
					
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
	                <button id="queren" type="button" onclick="domnenu()" class="btn btn-primary" >保存</button>
	            </div>
	        </div>
	    </div>
	</div>
	
	<button id="addbutton" data-toggle="modal" data-target="#myModa26" style="display:none" ></button> 
	
	
    <div class="wrapper wrapper-content animated fadeInRight">
		 <!-- Panel Other -->
			
		<div class="ibox-content">
			<div class="row row-lg">
				<div class="col-sm-3 ">
					<h5>菜单树形选择</h5>
					    <div class="ibox float-e-margins">
					        <div class="ibox-content" style="overflow-x: auto;">
								<div id="treeview" class="test"></div>
					        </div>
					    </div>

<script src="${pageContext.request.contextPath}/jsp/manageIntegral/treeview/bootstrap-treeview.min.js"></script>	

<script type="text/javascript">
	/**
	*树形加载
	*/
	$('#treeview').treeview({
			 color: "#5F6368",
			 expandIcon: 'glyphicon glyphicon-chevron-right',
			 collapseIcon: 'glyphicon glyphicon-chevron-down',
			 nodeIcon: 'fa fa-users',
			// data: json,
		    data: ${menutree},
			onNodeSelected: function (event, node) {
			   var _url="${pageContext.request.contextPath}/menu/menutable.action?parentid="+node.id;
			   window.location.href=_url;
			}
	});
	//展开所有节点
	$('#treeview').treeview('expandAll', { levels: 1, silent: true });
	//得到所有的展开节点
	var nodes=$('#treeview').treeview('getEnabled', 0);
	//alert(nodes.length);
	for(var i=0;i<nodes.length;i++){
	//	alert(nodes[i]["id"]+nodes[i]+JSON.stringify(nodes[i]));
		if("${parentid}"==nodes[i]["id"])
		$('#treeview').treeview('selectNode', [ nodes[i], { silent: true } ]);
	}i
	
</script>
				</div>	
				
	
				<div class="col-sm-9 leftbottom">
					<!-- Example Pagination -->
					<h5>系统菜单管理</h5>
					<div class="ibox float-e-margins">
							<div class="ibox-content"  >	
								<div class="btn-groups hidden-xs" id="exampleToolbar" role="group">	
							<c:if test="${button.add!=null}" >				 
								<div class="btn-group" style="margin-top:2px" >
									<a href="javascript:addbutton();"  class="btn btn-primary btn-sm">
										<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>新增
									</a>
								</div>
							</c:if>		
								</div>
							 <div class="scrollbar" >
								<table id="TreeTablePagination"  ></table>
							 </div>	
						</div>
						
					</div>
					<!-- End Example Pagination -->
  <script type="text/javascript">
		var $row=new Array();
		var $parentid="${parentid}";
		var $dialog="";
		var $order="";
		var $ptype="${ptype}";
		var $table;
		if("${textinfo}"!=null&&"${textinfo}"!=""&&"${textinfo}"!="null")alert("${textinfo}");
			function addbutton(){
				if($parentid==null||$parentid==""){alert("请选择父节点");}
				
				if($ptype=='0'){
					$("#usemenuname").html("系统新增"); //"系统";
					$("#type").html("<option value='1' selected='selected'>系统</option>");
				}
				if($ptype=='1'){
					$("#usemenuname").html("菜单新增"); //"菜单";
					$("#type").html("<option value='2' selected='selected'>菜单</option>");
				}
				if($ptype=='2'){
					$("#usemenuname").html("按钮新增"); //"菜单";
					$("#type").html("<option value='3' >功能按钮</option>");
				}
				if($ptype=='3'){
					$("#usemenuname").html("按钮新增"); //"菜单";
					$("#type").html("<option value='4' >行按钮</option>");
				}
				$dialog="add";
				$order=$row.length+1;
				resetfrom();
				$("#addbutton").click();
			}
			
			function updatebutton(index){
				if($parentid==null||$parentid==""){alert("请选择父节点");}
				var row=$row[index];
// 				alert($row.length);
// 				alert(JSON.stringify(row));
				if(row.type=='1'){
					$("#usemenuname").html("系统修改"); //"系统";
					$("#type").html("<option value='1' selected='selected'>系统</option>");
				}
				if(row.type=='2'){
					$("#usemenuname").html("菜单修改"); //"菜单";
					$("#type").html("<option value='2' selected='selected'>菜单</option>");
				}
				
				if(row.type=='3'){
					$("#usemenuname").html("按钮修改"); //"菜单";
					$("#type").html("<option value='3' selected='selected' >功能按钮</option><option value='4' >行按钮</option>");
				}
				if(row.type=='4'){
					$("#usemenuname").html("按钮修改"); //"菜单";
					$("#type").html("<option value='3'  >功能按钮</option><option value='4' selected='selected' >行按钮</option>");
				}
				$dialog="update";
				resetfrom();
				
				$("#htmlcode").val(row.htmlcode);
				$("#htmlname").val(row.htmlname);
				$("#urlaction").val(row.urlaction);
				$("#icon").val(row.icon);
				$id=row.id;
				$parentid=row.parentid;
				$order=row.order;
				$("#addbutton").click();
			}
			
			
			function domnenu(){
				if($dialog=="add"){
					var obj=getfrom();
					if(obj!=null){
						obj["order"]=$order;
						var _addurl="${pageContext.request.contextPath}/menu/addmenu.action?tablejson="+JSON.stringify(obj);
						window.location.href=_addurl;
						
						//doajax(_addurl,obj);
					}
				}
				if($dialog=="update"){
					var obj=getfrom();
					if(obj!=null){
						obj["id"]=$id;
						obj["order"]=$order;
						obj["parentid"]=$parentid;
						window.location.href="${pageContext.request.contextPath}/menu/updatemenu.action?actjson="+JSON.stringify(obj);
// 						doajax(_addurl,obj);
					}
				}
				$dialog="";
			}
			
			function resetfrom(){
				$("#htmlcode").val("");
				$("#htmlname").val("");
				$("#urlaction").val("");
				$("#icon").val("");
			}
			function getfrom(){
				var htmlcode=$("#htmlcode").val();
				var htmlname=$("#htmlname").val();
				var urlaction=$("#urlaction").val();
				var icon=$("#icon").val();
				var parentid=$parentid;
				var type=$("#type").val();
				var alerts="";
				if(htmlcode==null||htmlcode=="")alerts="编号不能为空";
				if(htmlname==null||htmlname=="")alerts="名称不能为空";
				//if(urlaction==null||urlaction=="")alerts="路径不能为空";
				if(type==null||type=="")alerts="类型不能为空";
				if(alerts!=""){
					alert(alerts);
					return null;
				}
				return {"htmlcode":htmlcode,
						"htmlname":htmlname,
						"icon":icon,
						"type":type,
						"parentid":parentid,
						"urlaction":urlaction};
				
			}
			
			function removebutton(index){
				  var msg = "确定删除！";
					if (confirm(msg)==true){
					}else{
						return ;
					}
				var obj=new Object();
				obj["id"]=$row[index]["id"];
				obj["parentid"]=$parentid;
				window.location.href="${pageContext.request.contextPath}/menu/removemenu.action?tablejson="+JSON.stringify(obj);
// 				if(doajax(_url,obj)){
// 					$table.bootstrapTable('destroy');
// 					$table.bootstrapTable('refresh');
// 				} 	
			}
			
			function upbutton(index){
				if(index==0)return false;
				var obj=new Object();
				obj["upid"]=$row[index]["id"];
				obj["uporder"]=$row[index-1]["order"];
				obj["downid"]=$row[index-1]["id"];
				obj["downorder"]=$row[index]["order"];
				obj["parentid"]=$parentid;
				window.location.href="${pageContext.request.contextPath}/menu/movemenu.action?tablejson="+JSON.stringify(obj);
// 				if(doajax(_url,obj)){
// 					var uprow=$row[index];
// 					var downrow=$row[index-1];
// 					var order=uprow["order"];
// 					uprow["order"]=downrow["order"]; 
// 					downrow["order"]=order;
// 					$row[index]=downrow;
// 					$row[index-1]=uprow;
// 					reload();
// 				}
			}
			
			function downbutton(index){
				if(index==($row.length-1))return false;
				var obj=new Object();
				obj["upid"]=$row[index+1]["id"];
				obj["uporder"]=$row[index]["order"];
				obj["downid"]=$row[index]["id"];
				obj["downorder"]=$row[index+1]["order"];
				obj["parentid"]=$parentid;
				window.location.href="${pageContext.request.contextPath}/menu/movemenu.action?tablejson="+JSON.stringify(obj);
// 				if(doajax(_url,obj)){
// 					var uprow=$row[index+1];
// 					var downrow=$row[index];
// 					var order=uprow["order"];
// 					uprow["order"]=downrow["order"]; 
// 					downrow["order"]=order;
// 					$row[index+1]=downrow;
// 					$row[index]=uprow;
// 					reload();
// 				}
			}	  
			
			function reload(){
				$table.bootstrapTable('removeAll');
				$table.bootstrapTable('destroy');
				var date=$row;
				$row=new Array();
				$table.bootstrapTable('load',date);
			}
		
		
		(function() {
			$table=$("#TreeTablePagination");
				/**
				 * 表格搜索 表格查询  $table
				 */
				// $("#search_button").click(function(){
				// 	gridReload();
				// })
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
				  // url: "${pageContext.request.contextPath}/menu/menutable.action",
				  //  height: "450",			 //行高，如果没有设置height属性，
				//	uniqueId: "id",          //每一行的唯一标识，一般为主键列
					cache: false,           //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
					striped: true,          //是否显示行间隔色
					pagination:true,
					sidePagination: "client",    //分页方式：client客户端分页，server服务端分页（*）
					pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
				    iconSize: 'outline',
					search: true,
				    showColumns: true,
					showRefresh: true,
					showToggle: true,
					//showColumns: true,
					pageSize: 200, 
					pageList: [10, 200, 400],        //可供选择的每页的行数（*）
					toolbar: '#exampleToolbar', //使用工具条
				    icons: {
				      refresh: 'glyphicon-repeat',
				      toggleOff: 'glyphicon-list-alt icon-list-alt',
          	  		  toggleOn: 'glyphicon-list-alt icon-list-alt',
				      columns: 'glyphicon-list'
				    },
// 				  得到查询的参数
// 		         queryParams : function (params) {
// 		                    var temp = {   
// 		                        rows: params.limit,                         //页面大小
// 		                        page: (params.offset / params.limit) + 1,   //页码
// 		                        sort: params.sort,      //排序列名  
// 		                        sortOrder: params.order ,//排位命令（desc，asc） 
// 				 			    actjson:JSON.stringify(gridReloadjson())	//查询参数	
// 		                    };
// 		                    return temp;
// 		         },
					columns:[ {
						field: 'htmlcode',
						title: '菜单编号',
						sortable: true,
						width: 320,
						align: 'center'
					}, {
						field: 'htmlname',
						title: '菜单名称',
						sortable: true,
						width: 120,
						align: 'center'
					}, {
						field: 'type',
						title: '类型',
						sortable: true,
						width: 120,
						align: 'center',
						formatter: buttonFormatter
					}, {
						field: 'icon',
						title: '图标',
						sortable: true,
						width: 120,
						align: 'center',
						formatter: iconFormatter
					}, {
						field: 'urlaction',
						title: '路径配置',
						width: 120 
					}, {
						field: 'order',
						title: '排序',
						width: 120,
						hidden:true
					}, {
						field: 'dotable',
						title: '操作',
						width: 320,
						formatter: orderFormatter
					}]
				  });
			
			$table.bootstrapTable('load',${menutable});
				  
			function buttonFormatter(value,row,index){
				 if(row.type=='1')return "系统";
				 if(row.type=='2')return "菜单";
				 if(row.type=='3')return "功能按钮";
				 if(row.type=='4')return "行操作";
			}
			function orderFormatter(value,row,index){
				$row[index]=row;
					return [
				"<button type='button' onclick='upbutton("+index+")' class='btn btn-default btn-sm'><i class='fa fa-search'></i>上移</button>&nbsp;&nbsp;",
				"<button type='button' onclick='downbutton("+index+")' class='btn btn-default btn-sm'><i class='fa fa-search'></i>下移</button>&nbsp;&nbsp;",
				"<button type='button' onclick='updatebutton("+index+")' class='btn btn-default btn-sm'><i class='fa fa-search'></i>编辑</button>&nbsp;&nbsp;",
				"<button type='button' onclick='removebutton("+index+")' class='btn btn-default btn-sm'><i class='fa fa-search'></i>删除</button>&nbsp;&nbsp;",
				].join("");
			 }
			
			
			
			function iconFormatter(value,row,index){
				return "<button type='button' class='btn btn-default btn-sm'><i class='"+value+"'></i>&nbsp;&nbsp;"+row["htmlname"]+"</button>";
			} 
		
		})();
		
		
		 
   </script>				
				
				</div>
				
		 
		  
		  
		  
		  
			</div>
		</div>
	
		
		
    </div>
	
	
  
   
	
<!-- 	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js"></script> -->
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
