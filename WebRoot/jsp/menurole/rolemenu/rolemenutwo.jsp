<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
	
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/organization.css?v=4.1.0" rel="stylesheet">
	 
	
	<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.7/css/select2.min.css" rel="stylesheet" />
	<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.7/js/select2.min.js"></script>
	
	<!-- <link href="${pageContext.request.contextPath}/layui/css/layui.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/layui/layui.js"></script> -->
	
	<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/plugins/toastr/toastr.min.css" rel="stylesheet">
	 
	<link href="${pageContext.request.contextPath}/rolemenu2/dtree.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/rolemenu2/dtree.js"></script>
	
		
	<style type="text/css">
		/* line-height: 1.42857143; */
		
		textarea {
			display: block;
			width: 100%;
			height: 34px;
			padding: 6px 12px;
			font-size: 14px;
			color: #555;
			background-color: #fff;
			background-image: none;
			border: 1px solid #ccc;
			border-radius: 4px !important;
			-webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075) !important;
			box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
			-webkit-transition: border-color ease-in-out .15s,-webkit-box-shadow ease-in-out .15s;
			-o-transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
			transition: border-color ease-in-out .15s,box-shadow ease-in-out .15s;
		} 
		.select2-container--default .select2-selection--single {
			background-color: #fff;
			border: 1px solid #ccc;
			border-radius: 4px;
		} 
		.dtree .clip {
			overflow: visible;
		} 
		.ibox-content{
			overflow-y:auto;overflow-x:hidden;
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
					<div class="ibox float-e-margins" style="margin-bottom: 0px;">
						<h5>选择角色</h5>
						<div class="ibox-content" style="padding: 15px 20px 5px 20px;">
							<div class="row"> 
								<div class="col-sm-12" >
									<label  class="date-label col-sm-1" style="text-align:right;font-weight:300;margin-top:20px;">选择:&nbsp;</label>
									<div class="col-sm-3" style="margin-top:15px;padding-left:0px;" > 
										<select class="col-sm-12"  style="width:100%"
										  title="角色选择" null="no" id="role" >
										  <c:forEach items="${userMap }" var="user" >
												<option value="${user.id }">${user.rolename }</option>
										  </c:forEach>
										</select>
									</div>
									<label  class="date-label col-sm-1" style="text-align:right;font-weight:300;margin-top:15px;">描述:&nbsp;</label>
									<div class="col-sm-4" > <textarea  rows="2" class="form-control" style="padding-left:0px;" id="desc"  ></textarea></div>
									<div class="col-sm-3" style="text-align: center;margin-top: 10px;" > 
										<div class="btn-group">
											<button id="setrolemenubtn1" onclick="setrolemenu();" disabled="disabled" class="btn btn-primary">
												<i class="glyphicon glyphicon-ok" aria-hidden="true"></i>保存权限设置
											</button>
										</div>
									</div>
																
								</div>
							</div>
						 </div>	
					</div>

					<div class="ibox float-e-margins" >
							<h5>权限设定</h5>
							<div class="ibox-content scrollbar"  >
								<div id="treetitle" class="col-sm-12" style="font-size: 16px;display:none;">
									<div style="float:left;">菜单分组</div>
									<div style="float:left;margin-left:60px">菜单</div>
									<div style="float:left;margin-left:100px">按钮/页签组</div>
								</div>
								<div id="dtree" class="col-sm-12" style="width:100%;min-height: 160px;" >
									 
								</div>
								<div class="col-sm-12" style="text-align: center;margin-top:25px">
									<div class="btn-group">
										<button id="setrolemenubtn2" onclick="setrolemenu();" disabled="disabled" class="btn btn-primary">
											<i class="glyphicon glyphicon-ok" aria-hidden="true"></i>保存权限设置
										</button>
									</div>
								</div>
							</div>	
						</div>
						
					</div>
					</div>
		</div>
	</div>
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/toastr/toastr.min.js"></script>
<script type="text/javascript">
   var $rolecodeid;
	//$("#role").select2(); 
	$("#role").val('').select2({placeholder: '请选择角色'});
	//存入角色描述
	var rolelist=${role };
	var roleobj=new Object();
	for(var i=0;i<rolelist.length;i++){
		var role=rolelist[i];
		roleobj[role["id"]]=role["desc"];
	}
	/**
	 * 角色单选事件
	 */
	$("#role").change(function(){
		var rolecodeid=$(this).val();
		// alert($(this).val());
		if(rolecodeid&&rolecodeid!=""){
			$rolecodeid=rolecodeid;
			getrolemenu(rolecodeid);
			$("#desc").text(roleobj[$rolecodeid]);
		}else{
			$("#desc").text("");
		}
	});
	
</script> 
 
 
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
	  
	/**
	 * 提交保存
	 */	
	function setrolemenu(){
		var obj = document.all.authority;
		var str="";
		for(i=0;i<obj.length;i++){
			if(obj[i].checked){					
				if(str=="")
					str=obj[i].value;
				else 
					str+=","+obj[i].value;
			}
		}
		var ajax=new Object();
		ajax["menu"]=str;
		ajax["role"]=$rolecodeid;
		var boo=doajax(ajax);
		if(boo){
			//$("#setrolemenubtn").attr("disabled","disabled");
		} 
	}					 
	  
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
	 
	
	/**
	 * 刷新树
	 * @param {Object} roleid
	 */
	function getrolemenu(roleid){
		$.getJSON("${pageContext.request.contextPath}/rolemenu/rolemenu.action?"+
			"roleid="+roleid+"&rundd="+new Date().getTime(),function(tree){
			if(tree){
				var _tree=document.getElementById("dd0");
				if(_tree)_tree.parentNode.removeChild(_tree);
				d = new dTree('d');
				for(var i=0;i<tree.length;i++){
					var obj=tree[i];
					if(i==0)d.add(1,-1,'');
					var boo=(obj["ischeck"]=="null"||obj["ischeck"]=="")?false:true;
					d.add(obj["id"],obj["parentid"],'authority',obj["id"]+'',obj["text"]+'',boo);
					
					//$("#dtree").append(obj["id"]+"||"+obj["parentid"]+"||"+'authority'+"||"+obj["id"]+''+"||"+obj["text"]+''+"||"+boo+"<br/>");
				}
				//document.write(d);
				$("#dtree").html("");
				$("#dtree").append(d+"");
				d.closeAll();
				$("#treetitle").css("display","block");
				$("#setrolemenubtn1").removeAttr("disabled");
				$("#setrolemenubtn2").removeAttr("disabled");
				var _as=document.getElementsByTagName("a");
				
				for(var i=0;i<_as.length;i++){
					var _a=_as[i];
					if(_a){
						_a.setAttribute("href","javascript:return false;");
					}
				}
				
				var _tree=document.getElementById("dd0");
				if(_tree){
					_tree.style="margin-left:0px;margin-top:-20px;";
					if(isIE()){
						_tree.style.marginLeft="0px";
						_tree.style.marginTop="-20px";
					}
					var _chlids=_tree.childNodes;
					for(var i=0;i<_chlids.length;i++){
						var _chlid=_chlids[i];
						var classname=_chlid.getAttribute("class");
						if(classname=="clip"){
							_chlid.style="margin-top:-31px;"; //margin-left:0px
							if(isIE()){
								_chlid.style.marginTop="-31px";
							}
							var _nodes=_chlid.childNodes;
							if(_nodes){
								for(var j=0;j<_nodes.length;j++){
									var _node=_nodes[j];
									var classname=_node.getAttribute("class");
									if(classname&&classname=="clip"){
										if(Allnode(_node)){//都是子节点
											nodeclass(_node);
										} else{ //含有页签
											var _btns=_node.childNodes;
											if(_btns){
												var boo=true;
												_node.style="float:left;margin-top:-20px;"; //margin-left:130px;
												if(isIE()){
													_node.float="left";
													_node.style.marginTop="-20px";
													//_btn.style.marginLeft="130px";
												}
												for(var k=0;k<_btns.length;k++){
													var classname=_btns[k].getAttribute("class");
													if(classname=="clip"){
														nodeclass2(_btns[k]);
													}
													if(classname=="dTreeNode"&&boo){
														_btns[k].style="clear:both;margin-left:50px;margin-top:-20px;";
														if(isIE()){
															_btns[k].style.clear="both";
															_btns[k].style.marginLeft="50px";
															_btns[k].style.marginTop="-20px";
														}
														boo=false;
													}
													if(classname=="dTreeNode"&&!boo){
														_btns[k].style="clear:both;margin-left:50px;margin-top:20px;";
														if(isIE()){
															_btns[k].style.clear="both";
															_btns[k].style.marginLeft="50px";
															_btns[k].style.marginTop="20px";
														}
													}
												} 
											}
										}
									}else{
										_node.style="clear:both;margin-left:5px"; //width:150px;
										if(isIE()){
											_node.style.clear="both";
											_node.style.marginLeft="5px";
										}
										var _as=_node.childNodes;
										if(_as&&_as[2]&&_as[2].childNodes){
											var _input=_as[2].childNodes[0];
											if(_input){
													_input.style="margin-top:20px;";
												if(isIE()){
													_input.style.marginTop="20px";
												}
											} 
										}
										
									}
									
								}
							}
						}else{
							_chlid.style="margin-top:21.7px;border-top:1px dashed #ccc;padding-top:10px;margin-left:0px;";
							if(isIE()){
								_chlid.style.marginTop="21.7px";
								_chlid.style.marginLeft="0px";
								_chlid.style.borderTop="1px dashed #ccc";
								_chlid.style.paddingTop="10px";
							}
						}
					}
					_tree.parentNode.style="padding-bottom:10px;border-bottom:1px dashed #ccc;";
					if(isIE()){
						_tree.parentNode.paddingBottom="10px";
						_tree.parentNode.borderBottom="1px dashed #ccc";
					}
					
				}
				
			} 
		}); 
    }	
 
	
	function nodeclass(_node){
		var _btns=_node.childNodes;
		if(_btns){
			_node.style="float:left;margin-top:-20px;margin-left:130px;";
			if(isIE()){
				_node.float="left";
				_node.style.marginTop="-20px";
				_node.style.marginLeft="130px";
			}
			for(var k=0;k<_btns.length;k++){
				var _btn=_btns[k];
				_btn.style="float:left;margin-left:10px;width:100px;";
				if(isIE()){
					_btn.style.float="left"; 
					_btn.style.width="100px";
					_btn.style.marginLeft="10px";
				}
			}
		}
	}
	function nodeclass2(_node){
		var _btns=_node.childNodes;
		if(_btns){
			_node.style="float:left;margin-top:-20px;margin-left:130px;";
			if(isIE()){
				_node.float="left";
				_node.style.marginTop="-20px";
				_node.style.marginLeft="130px";
			}
			for(var k=0;k<_btns.length;k++){
				var _btn=_btns[k];
				_btn.style="float:left;margin-left:10px;width:100px;";
				if(k==0)_btn.style="float:left;margin-left:50px;width:100px;";
				if(isIE()){
					_btn.style.float="left"; 
					_btn.style.width="100px";
					_btn.style.marginLeft="10px";
					if(k==0)_btn.style.marginLeft="50px";
				}
			}
		}
	}
	function Allnode(_nodes){
		var _chlids=_nodes.childNodes;
		if(_chlids){
			var boo=true;
			for(var i=0;i<_chlids.length;i++){
				var _chlid=_chlids[i];
				var classname=_chlid.getAttribute("class");
				if(classname&&classname!="dTreeNode")boo=false;
			}
			return boo;
		}else{
			return false;
		}
		
	}
	
	
	function isIE(){
	    var userAgent = navigator.userAgent, 
	    rMsie = /(msie\s|trident.*rv:)([\w.]+)/, 
	    rFirefox = /(firefox)\/([\w.]+)/, 
	    rOpera = /(opera).+version\/([\w.]+)/, 
	    rChrome = /(chrome)\/([\w.]+)/, 
	    rSafari = /version\/([\w.]+).*(safari)/; 
	    var browser; 
	    var version; 
	    var ua = userAgent.toLowerCase(); 
	
	    var match = rMsie.exec(ua);    
	    if (match != null) {  
	        ieVersion = { browser : "IE", version : match[2] || "0" };
	        return true; 
	    }  
	    var match = rFirefox.exec(ua);  
	    if (match != null) {  
	        var ffVersion = { browser : match[1] || "", version : match[2] || "0" };
	        return false; 
	    }  
	    var match = rOpera.exec(ua);  
	    if (match != null) { 
	       var opVersion =  { browser : match[1] || "", version : match[2] || "0" };
	       return false;
	    }  
	    var match = rChrome.exec(ua);  
	    if (match != null) {  
	
	        var chVersion = { browser : match[1] || "", version : match[2] || "0" };
	        return false;
	    }  
	    var match = rSafari.exec(ua);  
	    if (match != null) {  
	
	       var sfVersion = { browser : match[2] || "", version : match[1] || "0" }; 
	       return false;
	    }  
	    if (match != null) {  
	        var ohterVersion = { browser : "", version : "0" }; 
	        return false;
	    }
	} 
</script>	
	
  
   
	
<script type="text/javascript">
function doajax(obj){
	 var str=JSON.stringify(obj);
	// str=encodeURI(encodeURI(str));
	var _url="${pageContext.request.contextPath}/rolemenu/setrolemenu.action";
	parent.layer.load(2);
	 var ajax=true;
		$.ajax({
			url:_url+'?rundd='+new Date().getTime(),
			type:'post',
		 	data:{tablejson:str},
	        dataType:"json",
		    async:true,
		    success:function(date){
				parent.layer.closeAll('loading');
			 	if(date==null||date=='null'||date=='NULL'||date==' '||date["info"]=='0'){
			 		if(date["textinfo"]){
						toastr["error"]('', date["textinfo"]); 
			 		}else
					toastr["error"]('', "服务器繁忙，提交失败!");
			 		ajax=false;
			 	}else{
					toastr["success"]('', '操作成功!');
			 		ajax=true;
			 	}	
		    },
		     error:function(){
				parent.layer.closeAll('loading'); 
					toastr["error"]('', '网络繁忙，提交失败!'); 
					button=false;
					ajax=false;
			}
		});
		return ajax;
}
</script>



</body>

</html>
