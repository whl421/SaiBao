<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="renderer" content="webkit">

<title>电子信息产品质量与可靠性保障科技服务平台</title>

<meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
<meta name="description"
	content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

<!--[if lt IE 9]>
    <meta http-equiv="refresh" content="0;ie.html" />
    <![endif]-->

<link rel="shortcut icon" href="H5.ico">
<link
	href="${pageContext.request.contextPath}/jsp/manageIntegral/css/bootstrap.min.css?v=3.3.6"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/jsp/manageIntegral/css/font-awesome.min.css?v=4.4.0"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/jsp/manageIntegral/css/animate.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/jsp/manageIntegral/css/style.css?v=4.1.0"
	rel="stylesheet">

<script
	src="${pageContext.request.contextPath}/jsp/manageIntegral/js/jquery.min.js?v=2.1.4"></script>
<!-- 部门人员选择树 -->
<script
	src="http://www.jq22.com/demo/bootstrap-treeview20161102/js/bootstrap-treeview.js"></script>
<script
	src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script
	src="${pageContext.request.contextPath}/departuser/departuser.js"></script>
<link
	href="${pageContext.request.contextPath}/departuser/departuser.css"
	rel="stylesheet">

<script type="text/javascript">
	function imgerrorfun() {
		var img = event.srcElement;
		img.src = "${pageContext.request.contextPath}/jsp/manageIntegral/images/profile_small.jpg";
		img.onerror = null; // 控制不要一直跳动 
	}

	/**
	 * 头像重新加载
	 */
	function Refreshtitleimg() {
		$("#titleimg")
				.attr(
						"src",
						'${pageContext.request.contextPath}/resources/cusid${user.cusid}/${user.userid}/titleimg.png?rt='
								+ new Date().getTime());
	}
</script>
<style type="text/css">
/* .sidebar-collapse {
        height: 100%;
        overflow-x: hidden;
        overflow-y: scroll;
		overflow:-moz-scrollbars-none;
    }
    .sidebar-collapse::-webkit-scrollbar {
        display: none !important;
    }
	.sidebar-collapse::-ms-overflow-style {
	    display: none !important;
	}
	.sidebar-collapse::-o-scrollbar{
		-moz-appearance: none !important;   
	}
	 transform: translateX(-50.9262px) translateY(-12.6376px);
	 */
.titleimg {
	display: block;
	width: 94px;
	height: 94px;
	min-width: 0px !important;
	min-height: 0px !important;
	max-width: none !important;
	max-height: none !important;
}

.phone .downphone {
	display: none;
}

.phone:hover .downphone {
	display: inline;
}
</style>
</head>

<body class="fixed-sidebar full-height-layout gray-bg"
	style="overflow: hidden">
	<div id="wrapper">
		<!--左侧导航开始-->
		<nav class="navbar-default navbar-static-side" role="navigation">
			<div class="nav-close">
				<i class="fa fa-times-circle"></i>
			</div>
			<div class="sidebar-collapse">
				<ul class="nav" id="side-menu">
					<li class="nav-header">
						<div class="dropdown profile-element">
							<span><img alt="image" id="titleimg"
								class="img-circle titleimg"
								src="${pageContext.request.contextPath}/resources/cusid${user.cusid}/${user.userid}/titleimg.png"
								onerror="imgerrorfun();" /></span> <a data-toggle="dropdown"
								class="dropdown-toggle" href="#"> <span class="clear">
									<span class="block m-t-xs"> <strong class="font-bold">${user.departname }</strong>&nbsp;&nbsp;
										${user.username }<b class="caret"></b>
								</span>
							</span>
							</a>
							<ul class="dropdown-menu animated fadeInRight m-t-xs">
								<li><a class="J_menuItem"
									href="${pageContext.request.contextPath}/login/changimgjsp.action">修改头像</a>
								</li>
								<li><a
									href="JavaScript:PopupModalView(null,null,null,'修改信息>>密码修改','900px','80%','${pageContext.request.contextPath}/login/changepwdjsp.action');">修改密码</a>
								</li>
								<li class="divider"></li>

								<li><a
									href="${pageContext.request.contextPath}/login/loginout.action">安全退出</a>
								</li>
							</ul>
						</div>
						<div class="logo-element">赛宝</div>
					</li>
					<li>
						<!-- 						<a class="J_menuItem" href="orkbench/orkbench.html"> -->
						<!-- 							<i class="fa fa-home"></i>  --> <!-- 							<span class="nav-label">工作台</span> -->
						<!-- 						</a> -->
					</li>
					<c:if test="${menuMap!=null}">
						<c:forEach items="${menuMap }" var="menu">
							<li><a class="${menu.Class}" href="${menu.urlaction}"> <i
									class="${menu.icon}"></i> <span class="nav-label">${menu.htmlname}</span>
									<c:if test="${menu.list!=null}">
										<span class="fa arrow"></span>
									</c:if>
							</a> <c:if test="${menu.list!=null}">
									<ul class="nav nav-second-level">
										<c:forEach items="${menu.list }" var="menulist">
											<li><a class="J_menuItem" href="${menulist.urlaction}"><i
													class="${menulist.icon}"></i> ${menulist.htmlname}</a></li>
										</c:forEach>
									</ul>
								</c:if></li>
						</c:forEach>
					</c:if>

					<!-- <li>
                        <a href="#">
                            <i class="fa fa-cube"></i>
                            <span class="nav-label">系统管理</span>
                            <span class="fa arrow"></span>
                        </a>
                        <ul class="nav nav-second-level">
                            <li>
                                <a class="J_menuItem" href="${pageContext.request.contextPath}/menu/menutable.action">系统菜单配置</a>
                            </li>
                        </ul>
                    </li> -->

				</ul>
			</div>
		</nav>
		<!--左侧导航结束-->
		<!--右侧部分开始-->
		<div id="page-wrapper" class="gray-bg dashbard-1">
			<div class="row border-bottom">
				<nav class="navbar navbar-static-top" role="navigation"
					style="margin-bottom: 0;background:no-repeat url(${pageContext.request.contextPath}/jsp/manageIntegral/images/top.jpg)">
					<div class="navbar-header"
						style="background:no-repeat url(${pageContext.request.contextPath}/jsp/manageIntegral/images/logo.png)">
						<a id="sbchange"
							class="navbar-minimalize minimalize-styl-2 btn btn-primary "
							href="#"><i class="fa fa-bars"></i> </a>
						<!-- <form role="search" class="navbar-form-custom" method="post" action="search_results.html">
                            <div class="form-group">
                                <input type="text" placeholder="请输入您需要查找的内容 …" class="form-control" name="top-search" id="top-search">
                            </div>
                        </form> -->
						<div class="marquee">
							<marquee direction="left" scrollamount="2" scrolldelay="0">
							</marquee>
						</div>

					</div>
					<!-- <ul class="nav navbar-top-links navbar-right" >
                    	<li style="color:white">
                           	会员到期日期：${user.dqdate }
                        </li>
                        
                        <li class="phone" style="color:#1AB394;font-weight:800">
                           	&nbsp;移动下载&nbsp;<i class="fa fa-download fa-lg" aria-hidden="true"></i>&nbsp;
							<div class="downphone" style="border:2px dashed #1ABB9C;float:left;width:450px;height:270px;background:#FFFFFF;position:fixed;right:20px;top:70px;z-index:1000;color: #F3F3F3;">
								<table style="text-align:center;width:450px;font-size:16px;color:#3498DB" cellpadding=0 cellspacing=0 >
									<tr><td>&nbsp;</td> <td>&nbsp;</td></tr>
									<tr><td><img style="width:200px;height:200px" src="${pageContext.request.contextPath}/jsp/manageIntegral/images/app/android.png" /></td> <td> <img style="width:200px;height:200px" src="${pageContext.request.contextPath}/jsp/manageIntegral/images/app/apple.png" /> </td></tr>
									<tr><td>Android</td> <td>IOS</td></tr>
								</table>
							</div>
                        </li>
                        
						<li class="dropdown">
                            <a class="dropdown-toggle count-info" data-toggle="dropdown" title="系统公告" href="#">
                                <i class="fa fa-bell"></i> <span id="waitredall" class="label label-primary">${waitread.total}</span>
                            </a>
							
                            <ul id="xtgg" class="dropdown-menu dropdown-alerts">
							 	
                                <li id="waitggrdli" style="display:none;" >
                                    <a id="waitggrda" style="display:none;" class="J_menuItem" href="${pageContext.request.contextPath}/ggfb/needggfblistjsp">公告阅读</a>
									<div onclick="javascript:document.getElementById('waitggrda').click()">
										<i class="fa fa-envelope fa-fw"></i> 您有<span id="waitggrdlook">${waitread.ggrd}</span>条未读消息
										<span id="waitggrd" attr-time="" class="pull-right text-muted small">0分钟前</span>
									</div>
                                    
                                </li>
							 	
                               <!-- <li class="divider"></li>
                                <li>
                                    <a href="profile.html">
                                        <div>
                                            <i class="fa fa-qq fa-fw"></i> 3条新回复
                                            <span class="pull-right text-muted small">12分钟钱</span>
                                        </div>
                                    </a>
                                </li> -->


					</ul>
					</li>
				</nav>
			</div>
			<div class="row content-tabs">
				<button class="roll-nav roll-left J_tabLeft">
					<i class="fa fa-backward"></i>
				</button>
				<nav class="page-tabs J_menuTabs">
					<div class="page-tabs-content">
						<a href="javascript:;" class="active J_menuTab"
							data-id="organization.html">首页</a>
					</div>
				</nav>
				<button class="roll-nav roll-right J_tabRight">
					<i class="fa fa-forward"></i>
				</button>
				<div class="btn-group roll-nav roll-right">
					<button class="dropdown J_tabClose" data-toggle="dropdown">
						关闭操作<span class="caret"></span>

					</button>
					<ul role="menu" class="dropdown-menu dropdown-menu-right">
						<!--                         <li class="J_tabShowActive"><a>定位当前选项卡</a> -->
						<!--                         </li> -->

						<li class="J_tabCloseAll"><a>关闭全部选项卡</a></li>
						<li class="divider">c</li>
						<li class="J_tabCloseOther"><a>关闭其他选项卡</a></li>
					</ul>
				</div>
				<!--                 <a href="login.html" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a> -->
			</div>
			<div class="row J_mainContent" id="content-main">
				<iframe class="J_iframe" id="index0" name="iframe0" width="100%"
					height="100%"
					src="${pageContext.request.contextPath}/report/indexreport.action"
					frameborder="0" data-id="organization.html" seamless></iframe>
			</div>
			<div class="footer">
				<div class="pull-right">
					&copy; 2019-2020
					<!-- <a href="http://www.zi-han.net/" target="_blank">zihan's blog</a> -->
				</div>
			</div>
		</div>
		<!--右侧部分结束-->
		<!--右侧边栏开始-->
		<div id="right-sidebar">
			<div class="sidebar-container">

				<!-- <ul class="nav nav-tabs navs-3">

                    <li class="active">
                        <a data-toggle="tab" href="#tab-1">
                            <i class="fa fa-gear"></i> 主题
                        </a>
                    </li>
                    <li class=""><a data-toggle="tab" href="#tab-2">
                        通知
                    </a>
                    </li>
                    <li><a data-toggle="tab" href="#tab-3">
                        项目进度
                    </a>
                    </li>
                </ul> -->

				<div class="tab-content">
					<div id="tab-1" class="tab-pane active">
						<!--                         <div class="sidebar-title"> -->
						<!--                             <h3> <i class="fa fa-comments-o"></i> 主题设置</h3> -->
						<!--                             <small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small> -->
						<!--                         </div> -->
						<div class="skin-setttings">
							<!--                             <div class="title">主题设置</div> -->
							<div class="setings-item">
								<span>收起左侧菜单</span>
								<div class="switch">
									<div class="onoffswitch">
										<input type="checkbox" name="collapsemenu"
											class="onoffswitch-checkbox" id="collapsemenu"> <label
											class="onoffswitch-label" for="collapsemenu"> <span
											class="onoffswitch-inner"></span> <span
											class="onoffswitch-switch"></span>
										</label>
									</div>
								</div>
							</div>
							<!--                             <div class="setings-item"> -->
							<!--                                 <span>固定顶部</span> -->

							<!--                                 <div class="switch"> -->
							<!--                                     <div class="onoffswitch"> -->
							<!--                                         <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar"> -->
							<!--                                         <label class="onoffswitch-label" for="fixednavbar"> -->
							<!--                                             <span class="onoffswitch-inner"></span> -->
							<!--                                             <span class="onoffswitch-switch"></span> -->
							<!--                                         </label> -->
							<!--                                     </div> -->
							<!--                                 </div> -->
							<!--                             </div> -->
							<!--                             <div class="setings-item"> -->
							<!--                                 <span> -->
							<!--                         		固定宽度 -->
							<!--                     			</span> -->

							<!--                                 <div class="switch"> -->
							<!--                                     <div class="onoffswitch"> -->
							<!--                                         <input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox" id="boxedlayout"> -->
							<!--                                         <label class="onoffswitch-label" for="boxedlayout"> -->
							<!--                                             <span class="onoffswitch-inner"></span> -->
							<!--                                             <span class="onoffswitch-switch"></span> -->
							<!--                                         </label> -->
							<!--                                     </div> -->
							<!--                                 </div> -->
							<!--                             </div> -->
							<div class="title">皮肤选择</div>
							<div class="setings-item default-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-0">
										默认皮肤 </a>
								</span>
							</div>
							<div class="setings-item blue-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-1">
										蓝色主题 </a>
								</span>
							</div>
							<div class="setings-item yellow-skin nb">
								<span class="skin-name "> <a href="#" class="s-skin-3">
										黄色/紫色主题 </a>
								</span>
							</div>
						</div>
					</div>

				</div>

			</div>
		</div>
		<!--右侧边栏结束-->

	</div>

	<!-- 全局js -->

	<script
		src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap.min.js?v=3.3.6"></script>
	<script
		src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script
		src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/layer/layer.min.js"></script>

	<!-- 自定义js -->
	<script
		src="${pageContext.request.contextPath}/jsp/manageIntegral/js/hplus.js?v=4.1.0"></script>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/jsp/manageIntegral/js/contabs.js"></script>

	<!-- 	<script type="text/javascript" > -->
	<!-- 		var user='${user}'; -->
	<!-- 		function getuser(){ -->
	<!-- 			return user; -->
	<!-- 		} -->
	<!-- 	</script> -->
	<script type="text/javascript">
		window.onload = function() {
			setTimeout(function() {
				if ("${cstate}" == "1") {
					Ewin.alert({
						message : "您的密码不安全，请修改！"
					});
				}
			}, 1000);
			if ("${waitread.ggrd}" != "0") {
				var nowtime = GetDateTime(2);
				$("#waitggrd").attr("attr-time", nowtime);
				$("#waitggrdli").css("display", "inline");
			}
			$caleWaitRead = setInterval("CalcTime()", 1000 * 60);

		}
		/**
		 * format=1表示获取年月日
		 * format=0表示获取年月日时分秒
		 * **/
		function GetDateTime(format) {
			var now = new Date();
			var year = now.getFullYear();
			var month = now.getMonth() + 1;
			var date = now.getDate();
			var day = now.getDay();//得到周几
			var hour = now.getHours();//得到小时
			var minu = now.getMinutes();//得到分钟
			var sec = now.getSeconds();//得到秒
			if (format == 1) {
				_time = year + "-" + month + "-" + date
			} else if (format == 2) {
				_time = year + "-" + month + "-" + date + " " + hour + ":"
						+ minu + ":" + sec
			}
			return _time
		}
		/**
		 * 计算时间差
		 * @param {Object} startTime
		 * @param {Object} endTime
		 * @param {Object} diffType
		 */
		function GetDateDiff(startTime, endTime, diffType) {
			startTime = startTime.replace(/\-/g, "/");
			endTime = endTime.replace(/\-/g, "/");
			diffType = diffType.toLowerCase();
			var sTime = new Date(startTime); //开始时间
			var eTime = new Date(endTime); //结束时间</font>
			//作为除数的数字
			var divNum = 1;
			switch (diffType) {
			case "second":
				divNum = 1000;
				break;
			case "minute":
				divNum = 1000 * 60;
				break;
			case "hour":
				divNum = 1000 * 3600;
				break;
			case "day":
				divNum = 1000 * 3600 * 24;
				break;
			default:
				break;
			}
			return parseInt((eTime.getTime() - sTime.getTime())
					/ parseInt(divNum));
		}

		var $caleWaitRead;
		function DoCalcTime() {
			$
					.ajax({
						url : '${pageContext.request.contextPath}/login/caleWaitRead.action?rundd='
								+ new Date().getTime(),
						type : 'post',
						dataType : "json",
						async : true,
						success : function(date) {
							if (date == null || date == 'null'
									|| date == 'NULL' || date == ' '
									|| date["info"] == '0') {
								if (date["textinfo"]) {
									alert(date["textinfo"]);
								} else {
									alert("网络异常，后台刷新数据失败");
								}
								clearInterval($caleWaitRead);
							} else {
								var datetiem = date["waitread"];
								clearInterval($caleWaitRead);
								document.getElementById("waitredall").innerHTML = datetiem["total"];
								if (datetiem["ggrd"] != 0) {
									var nowtime = GetDateTime(2);
									$("#waitggrd").attr("attr-time", nowtime);
								} else {
									// var waitggrdli=document.getElementById("waitggrdli");
									// if(waitggrdli)waitggrdli.parentNode.removeChild(waitggrdli);
									document.getElementById("waitggrdli").style.display = "none";
								}
								document.getElementById("waitggrdlook").innerHTML = datetiem["ggrd"];

								$caleWaitRead = setInterval("CalcTime()",
										1000 * 60);
							}
						},
						error : function() {

						}
					});
		}
		/**
		 * 计算时间差
		 */
		function CalcTime() {
			var ntime = GetDateTime(2);
			if (document.getElementById("waitggrd")) {
				var btime = document.getElementById("waitggrd").getAttribute(
						"attr-time");
				var cale = GetDateDiff(btime, ntime, "minute");
				document.getElementById("waitggrd").innerHTML = cale + "分钟前";
			}
		}
	</script>

</body>

</html>
<script type="text/javascript">
	(function($) {
		window.Ewin = function() {
			var html = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">'
					+ '<div class="modal-dialog modal-sm">'
					+ '<div class="modal-content">'
					+ '<div class="modal-header">'
					+ '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>'
					+ '<h4 class="modal-title" id="modalLabel">[Title]</h4>'
					+ '</div>'
					+ '<div class="modal-body">'
					+ '<p>[Message]</p>'
					+ '</div>'
					+ '<div class="modal-footer">'
					+ '<button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>'
					+ '<button type="button" class="btn btn-primary ok" data-dismiss="modal">[BtnOk]</button>'
					+ '</div>' + '</div>' + '</div>' + '</div>';

			var dialogdHtml = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">'
					+ '<div class="modal-dialog">'
					+ '<div class="modal-content">'
					+ '<div class="modal-header">'
					+ '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>'
					+ '<h4 class="modal-title" id="modalLabel">[Title]</h4>'
					+ '</div>'
					+ '<div class="modal-body">'
					+ '</div>'
					+ '</div>' + '</div>' + '</div>';
			var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
			var generateId = function() {
				var date = new Date();
				return 'mdl' + date.valueOf();
			}
			var init = function(options) {
				options = $.extend({}, {
					title : "操作提示",
					message : "提示内容",
					btnok : "确定",
					btncl : "取消",
					width : 200,
					auto : false
				}, options || {});
				var modalId = generateId();
				var content = html.replace(reg, function(node, key) {
					return {
						Id : modalId,
						Title : options.title,
						Message : options.message,
						BtnOk : options.btnok,
						BtnCancel : options.btncl
					}[key];
				});
				$('body').append(content);
				$('#' + modalId).modal({
					width : options.width,
					backdrop : 'static'
				});

				var $modal_dialog = $('.modal-content');
				var m_top = ($(window).height() - $modal_dialog.height()) / 3;
				$modal_dialog.css({
					'margin' : m_top + 'px auto'
				});

				$('#' + modalId).on('hide.bs.modal', function(e) {
					$('body').find('#' + modalId).remove();
				});
				return modalId;
			}

			return {
				alert : function(options) {
					if (typeof options == 'string') {
						options = {
							message : options
						};
					}
					var id = init(options);
					var modal = $('#' + id);
					modal.find('.ok').removeClass('btn-success').addClass(
							'btn-primary');
					modal.find('.cancel').hide();

					return {
						id : id,
						on : function(callback) {
							if (callback && callback instanceof Function) {
								modal.find('.ok').click(function() {
									callback(true);
								});
							}
						},
						hide : function(callback) {
							if (callback && callback instanceof Function) {
								modal.on('hide.bs.modal', function(e) {
									callback(e);
								});
							}
						}
					};
				},
				confirm : function(options) {
					var id = init(options);
					var modal = $('#' + id);
					modal.find('.ok').removeClass('btn-primary').addClass(
							'btn-success');
					modal.find('.cancel').show();
					return {
						id : id,
						on : function(callback) {
							if (callback && callback instanceof Function) {
								modal.find('.ok').click(function() {
									callback(true);
								});
								modal.find('.cancel').click(function() {
									callback(false);
								});
							}
						},
						hide : function(callback) {
							if (callback && callback instanceof Function) {
								modal.on('hide.bs.modal', function(e) {
									callback(e);
								});
							}
						}
					};
				},
				dialog : function(options) {
					options = $.extend({}, {
						title : 'title',
						url : '',
						width : 800,
						height : 550,
						onReady : function() {
						},
						onShown : function(e) {
						}
					}, options || {});
					var modalId = generateId();

					var content = dialogdHtml.replace(reg, function(node, key) {
						return {
							Id : modalId,
							Title : options.title
						}[key];
					});
					$('body').append(content);
					var target = $('#' + modalId);
					target.find('.modal-body').load(options.url);
					if (options.onReady())
						options.onReady.call(target);
					target.modal();
					target.on('shown.bs.modal', function(e) {
						if (options.onReady(e))
							options.onReady.call(target, e);
					});
					target.on('hide.bs.modal', function(e) {
						$('body').find(target).remove();
					});
				}
			}
		}();
	})(jQuery);
</script>