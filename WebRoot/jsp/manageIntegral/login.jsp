<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
	<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
	<meta name="force-rendering" content="webkit">
	<meta name="renderer" content="webkit">
	<meta name="screen-orientation" content="portrait">
	<meta name="x5-orientation" content="portrait">
  	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title>电子信息产品质量与可靠性保障科技服务平台</title>
	<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
	<link rel="icon shortcut" href="${pageContext.request.contextPath}/jsp/manageIntegral/images/loginicon.ico" type="image/x-icon">	
	<link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/manageIntegral/css/reset.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/manageIntegral/css/login.css">
	
	<style type="text/css">
		/*filter:alpha(opacity=30);-moz-opacity:0.3;opacity:0.30;background: #2196F3;*/
		body{
			background-image: url("${pageContext.request.contextPath}/jsp/manageIntegral/img/bkg.jpg");
			text-align: center;
			background-size: 100% 100%;
			height: 100%;
			overflow: hidden;
			background-repeat: no-repeat;
			background-position: center;
			background-attachment: fixed;
		} 
		 
		.wrapForm{
			background:#FFFFFF;
		}
		/* .input-right {
			margin: 0 0 0 0px !important;
		} */
		.icon{
			display:block;
			float:left;
			position: initial !important;
			margin-top: 3px;
		}
		 
		.top {
			background: #ffffff2e;
			width: 100%;
			position: absolute;
			top: 0;
			line-height: 80px;
			left: 0px;
			right: 0px;
			color: #fff;
			text-align: left;
			padding-left: 80px;
			font-size: 26px;
			font-weight: 600;
		}
		input{
			border:1px solid #E7EAED !important; 
			border-radius: 5px;
			font-size: 16px !important;
			line-height: 31px;
			height: 2em !important;
			color:#19406D;
		} 
		.tipbox{
			background:#FFFF;
			color:#1A1C1D;
		}
		.login-top {
			font-size: 19px;
			margin-top: 10px;
			padding-left: 40px;
			box-sizing: border-box;
			color: #333333;
			margin-bottom: 25px;
			text-align: left;
		}
		.check{
			margin-left: -60%;
		}
		.checkbox{
			top: 13px;
		}
		.wrapForm{
			width:403px;
		} 
		.main{
			width:403px;
			left:auto !important;
			background:#FFFFFF;
			border-radius: 5px;
			margin:0 auto;
			position:inherit;
			margin-top:200px;
		}
		.wrap-psw {
			margin-top: 10px;
		}
		.input{
			height: 31px;
		}
		.err-tip {
			margin-bottom: 0px;
		}
				.btn-primary.btn-outline:hover, .btn-success.btn-outline:hover, .btn-info.btn-outline:hover, .btn-warning.btn-outline:hover, .btn-danger.btn-outline:hover {
    color: #fff;
}
.btn-primary:hover, .btn-primary:focus, .btn-primary:active, .btn-primary.active, .open .dropdown-toggle.btn-primary {
    background-color: #18a689;
    border-color: #18a689;
    color: #FFFFFF;
}
	</style>
</head>
<body>
	<div class="top" >&nbsp;</div>
	<!-- 登录框 -->
	<div class="main" style="top:105px;right:30px;">
	 
		<div class="wrapForm login animated fadeInRight" style="padding-bottom: 15px;" >
			<div class="login-top">
				登录
			</div>
			<form id="login" method="post" onsubmit="return onclicks();" action="${pageContext.request.contextPath}/login/user.action">
				<input type="text" class="form-control" style="position: fixed;top:-200px"  />
				<input type="text" class="form-control" style="position: fixed;top:-200px"  />
				<input type="text" class="form-control" style="position: fixed;top:-200px"  />
				<input type="password" class="form-control" style="position: fixed;top:-200px"  />
				<input type="password" class="form-control" style="position: fixed;top:-200px"  />
				<input type="password" class="form-control" style="position: fixed;top:-200px"  />
				<div class="wrapInput">
					<div class="input loginname">
						<i class="icon ic-loginname" ></i>
						<div class="input-right">
							<input type="text" class="form-control" autocomplete="off" id="usecode" name="usecode" style="background-color: rgb(255, 255, 255) !important;" placeholder="手机号或邮箱">
							
							<i class="del"></i>
						</div>
					</div>
					<div class="input">
						<i class="icon ic-password" ></i>
						<div class="input-right">
							<input type="password" id="password" class="form-control" autocomplete="off" name="password" style="background-color: rgb(255, 255, 255) !important;" placeholder="密码">
							<i class="visualPsw"></i>
						</div>
					</div>
					<!-- <div class="enterprise input">-->
					
					<div class="err-tip">${textinfo}</div>
					<div class="wrap-psw">
						<div id="checkdiv" class="check">
							<input type="checkbox" id="checkbox_sel" class="selectBetting"><span class="checkbox"></span>
							<span class="rmbPsw">记住密码</span>
						</div>
						
						<span class="forgetPsw" onclick="ModalView(null,null,null,'注册','900px','350px','${pageContext.request.contextPath}/cus/cuszc.action')">
						用户注册
						</span>
						
					</div>
					<div style="margin: 0 0 0 2px;">
						<input type="hidden" name="username">
						<button type="submit" class="loginbtn active-btn">登录</button>
					</div>
 
				</div>
			</form>
		</div>
		
		 
		
	</div>
	<!-- 页脚 -->
		<div class="login-footer">
			<p></p>
		</div>
	<!-- 遮罩层 -->
	<div class="mask"></div> 
	
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/jquery_2.1.4.min.js"></script>
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap.min.js?v=3.3.6"></script>
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/login.js"></script> 
	<script src="${pageContext.request.contextPath }/jsp/manageIntegral/js/plugins/layer/layer.min.js"></script>
	
	       <form id="logincusid" method="get" style="margin-top:-1000px" action="${pageContext.request.contextPath}/login/usercusid.action">
				<input type="text" class="form-control" style="position: fixed;top:-200px"  />
				<input type="password" class="form-control" style="position: fixed;top:-200px"  />
						<input type="text"   autocomplete="off" id="usecodecusid" name="usecodecusid"  style="display:none" >
						<input type="text" id="passwordcusid" autocomplete="off" name="passwordcusid" style="display:none">
						<input type="text" id="cusidcusid" autocomplete="off" name="cusidcusid" style="display:none">
						<button type="submit" id="submitcusid" >登录</button>
			</form>
<c:if test="${info==1||info=='1'}">
	<script type="text/javascript">
	$(function(){
			$("#usecode").focus();
			$("input[ name='password' ]").val("${password}");
			$("input[ name='usecode' ]").val("${usecode}");
		});
	</script>
</c:if>
<c:if test="${info==2||info=='2'}">
	<script type="text/javascript">
	$(function(){
			$("#usecode").focus();
			$("input[ name='password' ]").val("${password}");
			$("input[ name='usecode' ]").val("${usecode}");
			
			$("input[ name='passwordcusid' ]").val("${password}");
			$("input[ name='usecodecusid' ]").val("${usecode}");
			
			var uselist=${uselist};
				
			var cusidbtn='';
			for(var index in uselist){
				cusidbtn+='<div  onClick="submitlogin('+uselist[index]["cusid"]+
					')" class="btn btn-block btn-outline btn-primary" data-dismiss="modal" style="margin-top:15px">'+
					uselist[index]["cusname"]+'</div>';
			}
			
			var htmlmodel=  '<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'+
								'<div class="modal-dialog">'+
									'<div class="modal-content">'+
										'<div class="modal-header">'+
											'<h4 class="modal-title" id="myModalLabel">'+
													'请选择登录系统'+
											'</h4>'+
							            '</div>'+
							           '<div class="modal-body" style="padding-bottom:20px" >'+
											cusidbtn+ 
										'</div>'+
							            '<div class="modal-footer">'+
							            '</div>'+
							        '</div> '+
							   '</div> '+
							'</div>';
							
			//if(document.getElementById("myModal"))$('body').find('#myModal').remove();
			$('body').append(htmlmodel);
			$('#myModal').modal({
			   backdrop: 'static',
			   show:true});
			var $modal_dialog = $('.modal-content');
			var m_top = ( $(window).height() - $modal_dialog.height() )/3;
			$modal_dialog.css({'margin': m_top + 'px auto'});
// 			$('#myModal').on('hide.bs.modal', function () {
// 				alert('嘿，我听说您喜欢模态框...');
// 	   		});				
		});
		
		
		function submitlogin(cusid){
			$("input[ name='cusidcusid' ]").val(cusid);
			document.getElementById("submitcusid").click();
		} 
		
	</script>
</c:if>
<c:if test="${info!='2'&&info!='1'}">
	<script type="text/javascript">
	$(function(){
			$("#usecode").focus();
		});
	</script>
</c:if>	
	
	<script type="text/javascript">
		
		var num='${num}';	
		if(!num||num=="")
			num=0;
			
		num=parseInt(num);
		if(num>=5){
			$(".err-tip").html("${textinfo},登录频繁:"+num+"s后登录");
			$(".loginbtn").removeClass('active-btn').addClass('disable-btn').prop('disabled','true');
			var numtime=setInterval(function(){
				num--;
				$(".err-tip").html("${textinfo},登录频繁:"+num+"s后登录");
				if(num<=0){
					$(".err-tip").html("");
					clearInterval(numtime);
					$("input[ name='num' ]").val("0");
					$(".loginbtn").removeClass('disable-btn').addClass('active-btn').removeAttr('disabled');
				}
			},1000);	
		}
		
		 
			
		function onclicks(){
		
			var password = $("input[ name='password' ]").val();
		//	var cusid = $("input[ name='cusid' ]").val();
			var usecode=$("input[ name='usecode' ]").val();
			  
			 var _idsb=document.getElementById("idsb123");
		    if(!_idsb){ 
		    	 var _numIput='<input type="text" style="display:none" id="idsb123" name="num" value="'+num+'" autocomplete="off" >';
		   		 $("input[ name='usecode' ]").append(_numIput);		
		    }
			
			if(password==null||password==""){
				$(".err-tip").html("密码不能为空");
				return false;
			}
			if(usecode==null||usecode==""){
				$(".err-tip").html("账号不能为空");
				return false;
			}
			
			if ($("input[type='checkbox']").is(':checked')||document.getElementById("checkbox_sel").checked) {
			   
				  if(window.localStorage){
				     if(usecode) window.localStorage.username = usecode;
				  	 if(password)window.localStorage.password = password;
					 if(usecode) window.localStorage.setItem('username',usecode);
					 if(password)window.localStorage.setItem('password',password);
					 var overTimes = new Date().getTime()+3600*1000*24*5;
		  			  window.localStorage.overTime = overTimes;
				 }else{
				 	 var date = new Date();
           			date.setTime(date.getTime() + 10*3600*1000);
				 	if(usecode) document.cookie ="username=" + escape(usecode);
					if(password)document.cookie ="password=" + escape(password);
					document.cookie ="expires="+date.toGMTString();
				 }
			}else{
				
				if(window.localStorage){	
				  window.localStorage.removeItem('username');
				  window.localStorage.removeItem('password');
				  window.localStorage.removeItem('overTime');
				}else{
				  document.cookie ="username=";
				  document.cookie ="password=";
				  document.cookie ="";
				}  
			}
			return true;
			// return true;
		}
			 
			 
	//判断当前页面是否在iframe中
	if (top != this) {
		alert("登录验证已经过期");
		setTimeout(function(){
			parent.window.location.href="${pageContext.request.contextPath}/login/user.action?time="+new Date().getTime(); //parent.window.location.href;
		},1000);
	} 
	
	//弹出注册页面
	function ModalView(iframeid,date,usetype,titletext,width,height,_url2){
		parentiframeid = iframeid;
		parendate = date;
		pusetype = usetype;
		layer.open({
			type : 2,
			title : titletext,
			shadeClose : false,
			shade : 0.7,
			area : [width,height],
			content : _url2
		});
	}
	
	//刷新页面
	function ref(){
		window.location.reload();
	}		 
	</script>
</body></html>