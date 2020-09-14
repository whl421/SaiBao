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
	
	<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.7/css/select2.min.css" rel="stylesheet" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.7/js/select2.min.js"></script>
	
	<link href="${pageContext.request.contextPath}/jsp/manageIntegral/treeview/bootstrap-treeview.min.css" rel="stylesheet">
	
	 <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/plugins/toastr/toastr.min.css" rel="stylesheet">
	
	<style type="text/css">
		.rightbottom{
			border-right: 2px solid #D0D0D0;
			
		}
		.treeview {
			margin-left: 80px !important;
		}
		.treeview span.indent {
			margin-left: 80px !important;
			margin-right: 80px !important;
		}
	</style>
	
</head>

<body class="gray-bg">
	<nav class="navigation" >
		<span class="title" >菜单配置</span>
		<span class="title" >-</span>
		<span class="titlethis" >角色权限设定</span>
	</nav>
	
	
    <div class="wrapper wrapper-content animated fadeInRight">
		 <!-- Panel Other -->
			
		<div class="ibox-content">
			
			<div class="row row-lg" style="height:500px">
				<div class="col-sm-4 rightbottom">
					    <div class="ibox float-e-margins">
							<h5>角色选择</h5>
					        <div class="ibox-content" >
								<div class="col-sm-12" style="height: 30px;">&nbsp;</div>
								<select class="col-sm-12" style="width:100%;top:50px;margin-bottom: 50px;" 
								  title="角色选择" null="no" id="role" >
								  <c:forEach items="${userMap }" var="user" >
								  		<option value="${user.id }">${user.rolename }</option>
								  </c:forEach>
								</select>
								<div id="showtree" class="col-sm-12" style="height: 100px;">&nbsp;</div>
					        </div>
					    </div>

<script type="text/javascript">
   var $rolecodeid;
   
	$("#role").select2(); 
	/**
	 * 角色单选事件
	 */
	$("#role").change(function(){
		var rolecodeid=$(this).val();
		// alert($(this).val());
		if(rolecodeid&&rolecodeid!=""){
			$rolecodeid=rolecodeid;
			getrolemenu(rolecodeid);
		}
	});
	
	// $("#role").select2('data', null);;
	$("#role").val('').select2();
</script>
				</div>	
				
				<div class="col-sm-8 leftbottom" style="margin-left: -2px;">
					<!-- Example Pagination -->
					<div class="ibox float-e-margins">
							<h5>权限设定</h5>
							<!-- <button id="unchecked">unchecked</button>
							<button id="checked">checked</button> -->
							<div class="ibox-content scrollbar" >
								<div class="col-sm-2"></div>
								<div class="col-sm-10" style="width:100%;overflow-y: auto;overflow-x: hidden;height:450px" >
									<div id="treeview" style="width:100%" ></div>
								</div>
							</div>	
						</div>
						
					</div>
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/treeview/bootstrap-treeview.js"></script>					
 
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/toastr/toastr.min.js"></script>
 
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
  <script type="text/javascript">
	  toastr.options = {
	    "closeButton": false,
	    "debug": false,
	    "progressBar": false,
	    "positionClass": "toast-top-right",
	    "showDuration": "400",
	    "hideDuration": "500",
	    "timeOut": "800",
	    "extendedTimeOut": "600",
	    "showEasing": "swing",
	    "hideEasing": "linear",
	    "showMethod": "fadeIn",
	    "hideMethod": "fadeOut"
	  }
	  function showsussces(){
		  toastr["success"]('', '操作成功!'); 
	  }
	  // $("#unchecked").click(function()
	  // {
		 // $checkableTree.treeview('uncheckNode', [ $node, { silent: true } ]); 
	  // });
	  // $("#checked").click(function()
	  // {
	  // 	 $checkableTree.treeview('checkNode', [ $node, { silent: true } ]); 
	  // });
	  /**
	  *树形加载
	  */
	  // var $node;
	  var $checkableTree ;
	  var $displaynode;
	  function getrolemenu(roleid){
			 $.getJSON("${pageContext.request.contextPath}/menu/rolemenu.action?roleid="+roleid+"&rundd="+new Date().getTime(),function(defaultData){
				 $checkableTree = $('#treeview').treeview({
					data: defaultData["tree"],
					showIcon: false,
					showCheckbox: true,
					state: {
						checked: true,
						disabled: true,
						expanded: true,
						selected: true
					},
					onNodeChecked: function(event, node) {
						// var pnode=$checkableTree.treeview('getParent', node);
						// 	 $("#showtree").html(JSON.stringify(pnode));
						// var cl=$checkableTree.treeview('getSelected', node.nodeId); 
						// 	 alert(JSON.stringify(cl));
						// 	 return ;
						
							  // alert("选中了"+node.id);
						var obj={
							"roleid":$rolecodeid,
							"menuid":node.id,
							"type":"1"
							};
						var boo=doajax(obj);
						if(!boo){
							// alert("授权失败");
							// setTimeout(function(){
							// 	alert(JSON.stringify(node));
							$checkableTree.treeview('uncheckNode', [ node.nodeId, { silent: true } ]);
							toastr["error"]('', '操作失败!');
							// },1000)
						}else{
							showsussces();
						}	
						//  $displaynode.css("display","none");
					  // $('#checkable-output').prepend('<p>' + node.text + ' was checked</p>');
					},
					onNodeUnchecked: function (event, node) {
						// var pnode=$checkableTree.treeview('getParent', node);
						// 	 $("#showtree").html(JSON.stringify(pnode));
						// var cl=$checkableTree.treeview('getSelected', node.nodeId); 
						// 	 alert(JSON.stringify(cl));
						// 	 return ;
						var obj={
							"roleid":$rolecodeid,
							"menuid":node.id,
							"type":"0"
							};
						var boo=doajax(obj);
						if(!boo){
							$checkableTree.treeview('checkNode', [ node.nodeId, { silent: true } ]);
							toastr["error"]('', '操作失败!');	
							// alert("取消失败");
							
						}else{
							showsussces();
						}	
							// $displaynode.css("display","none");
					  // $('#checkable-output').prepend('<p>' + node.text + ' was unchecked</p>');
					}
				  });
				//默认打开所有节点
				$checkableTree.treeview('expandAll', { levels: 1, silent: true });
				
				
				// getParent(node | nodeId)：返回给定节点的父节点，如果没有则返回undefined。
				// $('#tree').treeview('getParent', node);
				// getSelected()：返回所有被选择节点的数组，例如：state.selected = true。
				// $('#tree').treeview('getSelected', nodeId);
				
				
				/**
				 * 加载已赋值权限
				 */
// 				var ltcheck=defaultData["ltcheck"];
				
				//alert(JSON.stringify(ltcheck));
				//$checkableTree.treeview('disableNode', [ '1', { silent: true } ]);
				
// 				var nodeobj=new Object();
// 				var nodes=$checkableTree.treeview('getEnabled', 0);
// 				for(var i=0;i<nodes.length;i++){
// 					nodeobj[nodes[i]["id"]]=nodes[i].nodeId;
// 				}
// 				for(var i=0;i<ltcheck.length;i++){
// 					if(ltcheck[i]){
// 						$checkableTree.treeview('checkNode', [ nodeobj[ltcheck[i]], { silent: true } ]);
// 					}
// 				}
				
				
				//data-nodeid=0
				
				// var lis=document.getElementsByTagName("li");
				// for(var i=0;i<lis.length;i++){
				// 	var nodeid=lis[i].getAttribute("data-nodeid");
				// 	if(nodeid==0){
				// 		$displaynode=$(lis[i]);
				// 		//$displaynode.css("display","none");
				// 		//$displaynode.remove();
				// 	}
				// }
				
				//选择一个给定的树节点，接收节点或节点ID。
				// $('#tree').treeview('selectNode', [ nodeId, { silent: true } ]);
				// 选择指定的节点，接收节点或节点ID。   uncheckNode 
				// $('#tree').treeview('checkNode', [ nodeId, { silent: true } ]);
				// 不选择指定的节点，接收节点或节点ID。
				// $('#tree').treeview('unselectNode', [ nodeId, { silent: true } ]);
				//$checkableTree.treeview('getNode', nodeId);
				// alert(JSON.stringify(defaultData));
				// revealNode(node | nodeId, options)：显示一个树节点，展开从这个节点开始到根节点的所有节点。
				// $('#tree').treeview('revealNode', [ nodeId, { silent: true } ]);
			}); 
	  }	  
	  
	  function treedoresfure(id1,id2,id3){
		  	var nodeobj=new Object();
			var nodes=$checkableTree.treeview('getEnabled', 0);
			for(var i=0;i<nodes.length;i++){
				nodeobj[nodes[i]["id"]]=nodes[i].nodeId;
			}
			//alert(id1+"=="+id2+"==="+id3); 
			
			// alert(nodeobj[id1]+"=="+nodeobj[id2]+"==="+nodeobj[id3]); 
			if((id3==0||id3)&&(nodeobj[id3]||nodeobj[id3]==0)){
				nodeobjss(nodeobj[id3]);
			} 
			if((id1==0||id1)&&(nodeobj[id1]||nodeobj[id1]==0)){
				nodeobjss(nodeobj[id1]);
			}
			if((id2==0||id2)&&(nodeobj[id2]||nodeobj[id2]==0)){
				nodeobjss(nodeobj[id2]);
			}
			
			// alert((id2==0||id2)&&nodeobj[id2]);
			 
	  }
	  
	  function nodeobjss(id){
		  $checkableTree.treeview('checkNode', [ id, { silent: true } ]);
	  }
	  
	  $(function () {
	    //   $('.full-height-scroll').slimScroll({
	    //       height: '450px',
		// 		 width:'100%' 
	    //   });
	  });
	  
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
	 
	
	
		 
   </script>				
				
				</div>
				
		 
		  
		  
		  
		  
			</div>
		</div>
	
		
		
    </div>
	
	
  
   
	
<script type="text/javascript">
function doajax(obj){
	 var str=JSON.stringify(obj);
	// str=encodeURI(encodeURI(str));
	var _url="${pageContext.request.contextPath}/menu/setrolemenu.action";
	 var ajax=true;
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
					if(date["info"]&&date["info"]=="5"){
						treedoresfure(date["lid1"],date["lid2"],date["lid3"]);
					}
			 		ajax=true;
			 	}	
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
