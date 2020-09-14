	
	var loginStatus = 1;
	
	$('.forgetPsw').click(function(event) {
		loginStatus=1;
		if (loginStatus === 1) { 
			showMask();
			return;
		}
		 
	}); 
	
	// 打开遮罩层
	function showMask(){
		$(".mask").css("height",$(document).height());
		$(".mask").css("width",$(document).width());
		$(".mask").show();
		$(".tipbox").show();
	}
	// 隐藏遮罩层  
	function hideMask(){
		$(".mask").hide();
		$(".tipbox").hide();
	}
	//设置居中
	function mainbox(){
	  var t = 0;
	  if($(window).width() > 767){
	    var t = ($(document).height() - $('.main').height()+30)/2;//pc
	  }else{
	    var t = ($(document).height() - $('.main').height())/2-60;//手机
	  }
	  if(t<0){
	    t = ($(document).height() - $('.main').height())/2 + 'px';//居中显示
	  }else{
	    t = t+'px';
	  }
	  $('.main').css('top', t);
	}



	// 去空格
	function trim(e){
		return $.trim(e).val();
	}
	// 重置界面
	// 要对input进行清空，图标和登录按钮重置，图标有6个，用户、密码、企业代码、验证码、记住密码、显示密码
	function resetIcon(){
		$('input').val('');
		$('.ic-loginname').css('background-position', '-20px -40px');
		$('.ic-password').css('background-position', '-20px -60px');
		$('.ic-verifyCode').css('background-position', '-20px -100px');
		$('.ic-enterprise').css('background-position', '-20px 0');
		$('.visualPsw').css('background-position', '0 -20px');
		$("input[name='password']").prop("type", "password");
		$('.loginbtn').removeClass('active-btn').addClass('disable-btn').prop('disabled','true');
		$('.err-tip>p').hide();
		rememberPsw(1);
	}
	// 记住密码
	function rememberPsw(flag){
		if(!flag){
		  // 选中就显示打钩,并把勾选框状态设置为true，标志改为1
			$('.checkbox').css('background-position', '0 -140px');
			$('.rmbPsw').css('color','#228FFF');
			$("input[type='checkbox']").prop("checked", true); 
			flag = 1;
		}else{
			$('.checkbox').css('background-position', '-20px -140px');
			$('.rmbPsw').css('color','#A1A1A1');
			$("input[type='checkbox']").prop("checked", false); 
			flag = 0;
		}
	}
	//错误提示信息
	function errMsg(err){
	  $(".err-tip").html("<p>"+err+"<i class='ic-err'></i></p>");
	  $(".err-tip>p").css('display', 'block');
	}
	// 切换图标(亮)
	function changeIconBright(e){
		var targetHTML = e.target.parentNode.previousElementSibling;
		if(targetHTML.className.indexOf("ic-loginname") != -1){
			$('.ic-loginname').css('background-position', '0 -40px');
		}else if(targetHTML.className.indexOf("ic-password") != -1){
			$('.ic-password').css('background-position', '0 -60px');
		}else if(targetHTML.className.indexOf("ic-verifyCode") != -1){
			$('.ic-verifyCode').css('background-position', '0 -100px');
		}else if(targetHTML.className.indexOf("ic-enterprise") != -1){
			$('.ic-enterprise').css('background-position', '0 0');
		}
	}
	// 切换图标(暗)
	function changeIconDark(e){
		var targetHTML = e.target.parentNode.previousElementSibling;
		if(targetHTML.className.indexOf("ic-loginname") != -1){
			$('.ic-loginname').css('background-position', '-20px -40px');
		}else if(targetHTML.className.indexOf("ic-password") != -1){
			$('.ic-password').css('background-position', '-20px -60px');
		}else if(targetHTML.className.indexOf("ic-verifyCode") != -1){
			$('.ic-verifyCode').css('background-position', '-20px -100px');
		}else if(targetHTML.className.indexOf("ic-enterprise") != -1){
			$('.ic-enterprise').css('background-position', '-20px 0');
		}
	}
 
	// 手机号码验证
	function checkPhone(){ 
	    var loginname = $("input[name='loginname']").val();
	    if(!/^[0-9]/.test(loginname)){
	      return true;
	    }else if(!(/^1[34578]\d{9}$/.test(loginname))){ 
	      errMsg("手机号输入有误");  
	      return false;
	    }else {
	      return true;
	    }
	}
	// 密码验证
	function checkPass(str){
	  if(str.length == 0){
	    errMsg('请输入密码');
	    return false;
	  }else if(str.length < 6){
	      errMsg('密码长度不低于6位');
	      return false;
	  }else {
	    return true;
	  }
	}
    // 请联系管理员的弹框隐藏
	function hideMask(){     
    $(".mask").hide();     
    $(".tipbox").hide();
    }
     // 请联系管理员的弹框显示
    function showMask(){     
    $(".mask").css("height",$(document).height());     
    $(".mask").css("width",$(document).width());     
    $(".mask").show();     
    $(".tipbox").show(); 
    } 
/* js执行代码 */
   $(document).ready(function() {
	//解决浏览器input密码框变黄色
	setTimeout(function(){
	  $('input[name="password"]').prop('type', 'password');
	},0);

	//设置居中
	mainbox();
	window.onresize = function(){
		mainbox();
	};
	//按回车键点击登录
	$("body").keydown(function(event) {
		if($(".loginbtn").prop('disabled') != true){
			if (event.keyCode == "13") {//keyCode=13是回车键
				$(".loginbtn").click();
			}
		}
	});
	//登录状态为1时
	if (loginStatus === 1) { 
		$(".toggle-text").addClass('hide')
		$(".login-account-title").addClass('hide')
		$('.entrepreneur').removeClass('hide')
		$(".loginname-input").addClass('hide')
		$('.enterprise-input').removeClass('hide')
	}
 

/* 1. input聚焦失焦切换icon  2.input失焦验证*/
	// 输入框聚焦切换icon
	$("input").focus(function(e){
		$('.err-tip>p').hide();
		changeIconBright(e)
	});
	// 输入框失焦切换icon，失焦不显示删除按钮，和失焦验证
	$("input").blur(function(e){
		// 如果没有输入框没有输入就使图标变灰色
		if(!$(this).get(0).value.length){
			changeIconDark(e);
		}
	});
	$("input[name='loginname']").blur(function(event) {
		checkPhone();
	});

// 清空输入框功能
	$("input").on("focus input propertychange change click", function(e) {
		e.stopPropagation();
		$(".del").hide();
		if ($(this).val()) {
			$(this).siblings(".del").css('display','inline-block')
		}
	});
	$(".del").click(function(e) {
	  e.stopPropagation();
	  $(this).siblings("input").val("");
	  $(this).css('display','none')
	  $('.loginbtn').removeClass('active-btn').addClass('disable-btn').prop('disabled','true');
	  changeIconDark(e);
	});
	// 点击其他地方隐藏X
	$(document).click(function(){
	  $(".del").hide();
	});

// 切换密码小眼睛
	$('.visualPsw,.enterprise-visualPsw').click(function() {
		if($("input[name='password']").prop('type') == 'password'){
		  $("input[name='password']").prop("type", "text");
		    $('.visualPsw').css('background-position', '-20px -20px');//pc
		}else{
		  $("input[name='password']").prop("type", "password");
		    $('.visualPsw').css('background-position', '0 -20px');//pc
		}
	});

// 记住密码
	$('.check').click(function() {
		var flag = 0;
		if($("input[type='checkbox']").prop("checked")){
			flag = 1
		}
		rememberPsw(flag);
	});


// 忘记密码界面确定按钮
	$(".form-psw input").on("input", function(e) {
	  	e.stopPropagation();
	  	if($("input[name='phonenum']").get(0).value.length > 0 && $("input[name='msgCode']").get(0).value.length > 0 &&$("input[name='newPsw']").get(0).value.length > 0){
	  		$('.comfirebtn').removeClass('disable-btn').addClass('active-btn').removeAttr('disabled');
	  	}else {
	  		$('.comfirebtn').removeClass('active-btn').addClass('disable-btn').prop('disabled','true');
	  	}
	});


 
 
	// 点击登录按钮
	if (window.localStorage) {
	    if (window.localStorage.username && window.localStorage.password &&
				+window.localStorage.overTime > new Date().getTime()) {
		  $("input[ name='usecode' ]").val(window.localStorage.username);
		  $("input[ name='password' ]").val(window.localStorage.password);
		  $('.checkbox').css('background-position', '0 -140px'); 
		  $("input[type='checkbox']").prop("checked", true); 
		}else{
		  $("input[ name='usecode' ]").val('');
		  $("input[ name='password' ]").val('');
		  window.localStorage.removeItem('overTime');
		  window.localStorage.removeItem('username');
		  window.localStorage.removeItem('password');
		  $('.checkbox').css('background-position', '-20px -140px'); //pc
		  $("input[type='checkbox']").prop("checked", false); 
		}
	}else if(document.cookie.length>0){
		/**
		 * 添加使用cookie填写密码
		 */
		var username=getCookie("username");
		var pwd=getCookie("password");
	  $("input[ name='usecode' ]").val(username);
	  $("input[ name='password' ]").val(pwd); 
	  $('.checkbox').css('background-position', '0 -140px');
	  if(username&&username.length>0&&pwd&&pwd.length>0){
		  $("input[type='checkbox']").attr("checked", true); 
		  $('.checkbox').css('background-position', '0 -140px');
		  $('.rmbPsw').css('color','#228FFF');
		  $("input[type='checkbox']").prop("checked", true); 
		  flag = 1;
	  }else{
		  $('.checkbox').css('background-position', '-20px -140px');
		  $('.rmbPsw').css('color','#A1A1A1');
		  $("input[type='checkbox']").prop("checked", false); 
		  flag = 0;
	  }
//	  document.getElementById("checkdiv").click();
	}
	function getCookie(name) {
	  var cookies = document.cookie;
	  var list = cookies.split("; ");          // 解析出名/值对列表
	  for(var i = 0; i < list.length; i++) {
	    var arr = list[i].split("=");          // 解析出名和值
	    if(arr.length>0&&arr[0] == name){
			return decodeURIComponent(arr[1]);   // 对cookie值解码
		}
	  }
	  return "";
	}
		 
//	alert(window.localStorage.username && window.localStorage.password && +window.localStorage.overTime > new Date().getTime());
});  
  
  
  
   
