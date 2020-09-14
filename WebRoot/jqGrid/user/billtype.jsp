<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>单据类型定义</title>
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="this is my page">
	<meta http-equiv="content-type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
				
				<link rel="stylesheet" href="${pageContext.request.contextPath}/jqGrid/jqgrid/css/ui.jqgrid.css" />

				<link rel="stylesheet"
					href="${pageContext.request.contextPath}/jqGrid/jqgrid/css/css/redmond/jquery-ui-1.8.16.custom.css" />

				<script src="${pageContext.request.contextPath}/js/laydate.js"></script>
				<!-- 改成你的路径 -->

				<script type="text/javascript" src="${pageContext.request.contextPath}/jqGrid/js/jquery-1.7.1.js"></script>

				<script type="text/javascript"
					src="${pageContext.request.contextPath}/jqGrid/jqgrid/js/jquery.jqGrid.src.js"></script>

				<script type="text/javascript"
					src="${pageContext.request.contextPath}/jqGrid/jqgrid/js/i18n/grid.locale-cn.js"></script>

<style>
html,body {
	border: 0px;
	margin: 0px 0px;
	pading: 0px 0px
}

.titlemenu {
	position: absolute;
	margin-top: 3px;
	font-family: '仿宋';
	font-size: 12px;
}

#tree {
	font-size: 12px;
}

#tree,#tree ul {
	list-style: none;
	margin: 0;
	padding: 0;
	padding: 2px;
}

#tree li {
	padding: 0px 0px 5px 15px;
	cursor: pointer;
}

#tree ul {
	display: none;
	font-size: 12px;
}  
/* li span:link {color: #222222 !important;} 设置全局未点击链接的颜色*/ 
/* li span:visited{text-color:#79AED5;color: #79AED5 !important;}   设置全局被点击后的链接颜色*/
li span:hover{color: #79AED5 !important;}  /*设置全局鼠标悬停在链接上未点击时的颜色*/ 
li span:active {color: #FDFEFE !important;} /*设置全局鼠标在点击链接瞬间发生的动作*/
.parenttree {
	background: url(/mes/img/tree/icon-folder.gif) 5px 50% no-repeat;
}

.parenttreeopen {
	background: url(/mes/img/tree/icon-folder-open.gif) 5px 50% no-repeat;
}

.sontree {
	background: url(/mes/img/tree/icon-file.gif) 5px 50% no-repeat;
}
label input{height:16px;margin-top:2px}
label{float:left;margin-left: 5px;}
</style>
</head>
<body
	style="min-height: 500px;height:auto;width:100%;overflow：scroll;min-width:1000px">
	<div style="height:20px;width:100%;background: #E9F4F9;margin-top:0px;">
		<span class="titlemenu">&nbsp; >> 基础数据管理>> 公共基础数据>> 用户信息</span>
	</div>
 

	<div style="clear:both"></div>
	<div style="position: absolute;left:1%;width:98%;">
		<div style="clear: both;width:95%;margin:2px auto;">

			 <fieldset style="width:100%;margin:1px auto;padding:5px 5px;font-size:12px;border:1px solid #cccccc" >
			<legend >查询选项</legend>
			<table  width="99%" cellspacing="0" cellpadding="0" border="0" align="center">
			<tbody><tr> 
			  <td > 
				<label style="white-space:nowrap;text-align:right;" >用户名:&nbsp;<input type="text" id="username" onkeydown="doSearch(arguments[0]||event)" />  </label>
			  </td>
			  <td width="60px">
				<input onclick="gridReload()" type="button" id="submitButton" style="margin-left: 5px;font-size:16px" value="查询"/>
				</td>
			</tr>
		  </tbody></table>
		</fieldset>
		</div>

		<div style="width:95%;margin:5px auto;">
			<span style="font-size: 12px">操作功能： &nbsp;</span> 
     			&nbsp;<input type="button" id="addrows" style="display:none" onclick="" value="新增" /> 
   				&nbsp; <input type="button" id="deleterows" style="display:none" value="删除" />
<script >
	var button = "<%=request.getParameter("button")%>";
	
	var but = button.split(",");
	for(var i=0; i< but.length; i++){
	  	var un = but[i];
	  	if(un == "add"){
	   		$("#addrows").css("display","inline");
	  	}
	   	if(un == "delete"){
	    	$("#deleterows").css("display","inline");
	  	}
	}
</script>
		</div>
<script>

	//新增按钮
    function addthistable_addss(_url){
    	var iframeid = "<%=request.getParameter("iframeid")%>";
    	var obj = new Object();
   	    obj.user = parent.username();
   	    obj.code = parent.usercode();
   	    obj.time = parent.nowtime();
		parent.tciframeopen(_url,800,450,iframeid,obj," >> 单据类型定义 >> 新增");
    }
    
    //功能按钮 修改
    function updatetable(_url){
		var id = jQuery("#list2").jqGrid("getGridParam","selrow"); //获取选中行的id 单行选择
		if(id) {
			var rowval = jQuery("#list2").jqGrid("getRowData",id); //获取选中行的对象;  encodeURI JSON.stringify
		  	var iframeid = "<%=request.getParameter("iframeid")%>";
			parent.tciframeopen(_url,800,450,iframeid,rowval," >> 单据类型定义 >> 修改");
		} else {
			alert("请选择编辑行");
		} 
    }
    
    //功能按钮   删除按钮
	jQuery("#deleterows").click(function(){  //多行选择
		var ids = jQuery("#list2").jqGrid("getGridParam","selarrrow");
		if(ids != ''){
			if(confirm("您真的要删除选中的记录吗？") == true) {
				var str = JSON.stringify(ids);
				$.ajax({
					url:'/mes/billdelete.action?rundd='+new Date().getTime(),
					type:'post',
				 	data:{jsonStr:str},
			        dataType:"json",
				    async:false,
				    success:function(date){
					 	if(date == null || date == 'null' || date == 'NULL' || date == ' ' || date["info"] == '0'){
					 		if(date["0"]=="0"){
					 			alert(date["textinfo"]);
					 		}else{
					 			alert("服务器繁忙，提交失败");
					 		}
					 	} else {
					 		var length=ids.length;
						  	for(var i = 0;i <  length;i ++) { 
								$("#list2").jqGrid("delRowData", ids[0]);  
							}
					 	}		
				    },
				    error:function() {
						alert("网络繁忙，提交失败");
					}
				});
			}
		} else {
			alert("请选择删除行");
		} 
	});
	
	//列表按钮   修改按钮
	function editParam(id,e){
 			e = e || window.event;  //阻止父元素冒泡
			if(e.stopPropagation) {
			    e.stopPropagation();
			} else {
			    e.cancelBubble = true;
			}
		var _url = "/mes/mes/basic/billtype/do/updateBilltype.jsp?do=update";
	  	var rowval = jQuery("#list2").jqGrid("getRowData",id); //获取选中行的对象;  encodeURI JSON.stringify
  	  	var iframeid = "<%=request.getParameter("iframeid")%>";
	  	parent.tciframeopen(_url,800,450,iframeid,rowval," >> 单据类型定义 >> 修改");
	}
	
	//列表按钮    查看按钮
	function cancelParam(id,e){
 			e = e || window.event;  //阻止父元素冒泡
			if(e.stopPropagation) {
			    e.stopPropagation();
			} else {
			    e.cancelBubble = true;
			}
	  	var _url = "/mes/mes/basic/billtype/do/readBilltype.jsp?do=info";
	  	var rowval = jQuery("#list2").jqGrid("getRowData",id); //获取选中行的对象;  encodeURI JSON.stringify
  	  	var iframeid = "<%=request.getParameter("iframeid")%>";
	  	parent.tciframeopen(_url,800,450,iframeid,rowval," >> 单据类型定义 >> 查看");
	}
</script>
    
		<div style="width:98%;margin:0px auto;">
			<table id="list2" style="width:100%"></table>
			<div id="pager2"></div>
			<br>
		</div>

	</div>

	<script type="text/javascript" > 
		
		var rowbutton = "<%=request.getParameter("rowbutton")%>";
		var rowbt = rowbutton.split(",");
		alert(rowbutton);
		var cobo = false;
		if(rowbutton == "" || rowbutton == " "){
			cobo = true;
		}
		var colnameorder = "?colnameorder=userid,userid,username,sex,birthday,address";  //第一个必须为id
		var _url = "/user/query.action"+colnameorder+"&rundd="+new Date().getTime();
		alert(_url);
		function pageInit(){
			alert(_url);
			jQuery("#list2").jqGrid( {
				//url : '/mes/jqGrid/data/JSONData.json',//json格式地址
				url :_url,
// 				editurl : "/mes/testgetJson.action", //修改和删除 调用的url
				datatype : "json",//
				colNames : [ '主键id','操作','用户名称', '性别', '生日', '地址' ], 
				colModel : [  
					 {name : 'userid',index : 'userid',width : 35,align : "center",hidden:true},
					 {name : 'control',index : 'control',width : 85,align : "center",hidden:cobo},
		             {name : 'username',index : 'username',width : 90,align : "center"}, 
		             {name : 'sex',index : 'sex',width : 40,align : "center"}, 
		             {name : 'birthday',index : 'birthday',width : 40,align : "center"}, 
		             {name : 'address',index : 'address',width : 40,align : "center"}
		       	],
				autowidth:true,
					height :"130%",
					width : "100%",
// 					pgbuttons : false,
// 					pginput : false,
  					rowNum : 10, 
					rowList : [ 10, 20, 30 ], 
					pager : '#pager2', 
					sortname : 'username', 
					sortorder : "asc", 
					mtype : "post", 
					viewrecords : true,
					repeatitems : false,
					multiselect : true,    //出现选择框
					caption : "用户信息" ,
				   	gridComplete : function() { // 第二步：数据加载完成后 添加操作按钮  
						var ids = jQuery("#list2").jqGrid('getDataIDs'); //获取表格的所有列
						           
						for (var i = 0; i < ids.length; i++) {
							var id = ids[i];
							var editBtn = "<div class='btnBox'> ";
							for(var j=0; j<rowbt.length; j++){
								var rbt = rowbt[j];
								if(rbt == "update"){
									editBtn+="<button type='button'  onclick='editParam("+id+ ",event)'>修改</button>";
								}
							}
							for(var j=0; j<rowbt.length; j++){
								var rbt = rowbt[j];
								if(rbt == "info")
									editBtn += "<button type='button'  onclick='cancelParam("+id+ ",event)'>查看</button>";
							}
							editBtn += "</div>";
							
							if((i%2)!=0){//如果天数等于0，则背景色置灰显示  253, 253, 253
								document.getElementById(id).style.background='rgb(246, 250, 253)';
			                 }else{
			                 	document.getElementById(id).style.background='rgb(253, 253, 253)';
			                 }
							
						    jQuery("#list2").jqGrid('setRowData',ids[i], {control : editBtn}); //给每一列添加操作按钮
						}//end for (var i = 0; i < ids.length; i++)
					},
				});
				jQuery("#list2").jqGrid('navGrid', '#pager2', {edit : false,add : false,del : false,search:false});
			}
			$(function(){
			alert(0);
				pageInit();
			});
			
			//查询按钮的重新加载  
			function gridReload() {
			alert(0);
			  	var username = jQuery("#username").val()||"";
				alert(username);			  	
			  	 var json=new Object();
			  	json["username"]=username;
			  	 var actjson=JSON.stringify(json); 
			 	 actjson=encodeURI(encodeURI(actjson));	
			  	var u_url = _url + "&actjson=" + actjson; 
			  	alert(u_url);
			  	jQuery("#list2").jqGrid('setGridParam', {
			    	url : u_url,
			  		page : 1
			  	}).trigger("reloadGrid");
			}
			
		    //按回车调用查询按钮
		    function doSearch(ev){
		    	// var elem=ev.target|| ev.srcElement;
		    	if(ev.keyCode == "13"){
			   		gridReload();
			   	}
		    }
		    
		    /**
			*odwork 调用的函数 
			*data   传递的json参数
			*/
			function dofunction(dowork,data) {
			 	if(dowork == null || dowork == 'null' || dowork == '' || dowork == 'NULL') return;
			 	//添加行数据
			 	if(dowork == 'add'){
				 	jQuery("#list2").jqGrid('setGridParam').trigger("reloadGrid");
//			     	$("#list2").jqGrid("addRowData", 1, data, "first");
			 	}
				//编辑行数据
			 	if(dowork == 'update'){
			 	 	jQuery("#list2").jqGrid('setGridParam').trigger("reloadGrid");
			 	}
			}
	</script>
</body>
</html>
