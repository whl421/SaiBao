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
	<title>就好用.激分宝</title>
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
		.icon{
			margin-left:-50%;
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
				<div class="wrapInput">
					<div class="input loginname">
						<i class="icon ic-loginname"></i>
						<div class="input-right">
							<input type="text" class="form-control" autocomplete="off" id="usecode" name="usecode" style="background-color: rgb(255, 255, 255) !important;" placeholder="手机号或邮箱">
							<input type="text" style="display:none" name="num" value="0" autocomplete="off" >
							<i class="del"></i>
						</div>
					</div>
					<div class="input">
						<i class="icon ic-password"></i>
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
						<span class="forgetPsw">忘记密码</span>
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
			<p>诺恩斯积分服务中心 v1.0.0</p>
		</div>
	<!-- 遮罩层 -->
	<div class="mask"></div> 
	<!-- 遮罩层里的提示框 -->
	<div class="tipbox">
		<p class="msg">请联系管理员</p>
		<span class="back" onclick="hideMask()">知道了</span>
	</div>
	
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/jquery_2.1.4.min.js"></script>
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/login.js"></script> 
	
	<script type="text/javascript">
		$(function(){
			$("#usecode").focus();
			if($("#usecode").val!=null&&$("#usecode").val!=""&&$("#usecode").val!=" "){
				$(".check").click();
			}
		})
		 
		
		var num='${num}';	
		if(!num||num=="")
			num=0;
		else
			$("input[ name='num' ]").val(num);
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
		
		
		
		window.onload=function(){
			setTimeout(function(){
				if('${info}'==1){
					$("#password").val("");
					$("#usecode").val("");
					if(document.getElementById("checkbox_sel").checked){
						document.getElementById("checkdiv").click();
					}
				}
			},1000);
		}
			
		function onclicks(){
			var password = $("input[ name='password' ]").val();
			var cusid = $("input[ name='cusid' ]").val();
			var usecode=$("input[ name='usecode' ]").val();
			
			if(password==null||password==""){
				$(".err-tip").html("密码不能为空");
				return false;
			}
			if(usecode==null||usecode==""){
				$(".err-tip").html("账号不能为空");
				return false;
			}
			
			if ($("input[type='checkbox']").is(':checked')) {
			  window.localStorage.username = usecode;
			  window.localStorage.password = password;
			}else{
			  window.localStorage.removeItem('username');
			  window.localStorage.removeItem('password');
			}
			return true;
		}
			 
			 
	//判断当前页面是否在iframe中
	if (top != this) {
		alert("登录验证已经过期");
		setTimeout(function(){
			parent.window.location.href="${pageContext.request.contextPath}/login/user.action?time="+new Date().getTime(); //parent.window.location.href;
		},1000);
	} 
			 
	</script>
</body></html>