<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>密码修改- 模板界面</title>
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
	<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/form.css?v=4.1.0" rel="stylesheet">
	
	
	
	
</head>

<body class="gray-bg">
	 
	
    <div class="wrapper wrapper-content animated fadeInRight">
		 <!-- Panel Other -->
			<div class="ibox-content col-sm-12">
				<input type="text" class="form-control" style="position: fixed;top:-200px"  />
				<input type="password" class="form-control" style="position: fixed;top:-200px"  />
				<input type="password" class="form-control" style="position: fixed;top:-200px"  />
			  <div class="row col-sm-12 ">
				  <div class="col-sm-1"></div>
				<div class="form-group col-sm-10">
					<label  class="date-label col-sm-2"><span style="color:red">*</span>原密码</label>
					<div class="col-sm-10">
					<input type="text" class="form-control" autocomplete="off"
					 null="no" id="pwd" name="manageIntegral" >
					 <span class="errorspan">原密码不能为空</span>
					</div> 
				</div>
					<div class="col-sm-1"></div>
			  </div>
			  
			  
			  <div class="row col-sm-12 ">
				  <div class="col-sm-1"></div>
				<div class="form-group col-sm-10">
					<label  class="date-label col-sm-2"><span style="color:red">*</span>新密码</label>
					<div class="col-sm-10">
					<input type="password" class="form-control" autocomplete="off" 
					 null="no" id="newpwd" name="manageIntegral" >
					 <span id="newpwdspan" class="errorspan">新密码不能为空</span>
					</div> 
				</div>
					<div class="col-sm-1"></div>
			  </div>
			  
			   
			   <div class="row col-sm-12 ">
					  <div class="col-sm-1"></div>
					<div class="form-group col-sm-10">
						<label  class="date-label col-sm-2"><span style="color:red">*</span>新密码确认</label>
						<div class="col-sm-10">
						<input type="password" class="form-control" autocomplete="off" 
						 null="no" id="newpwds" name="manageIntegral" >
						 <span class="errorspan">新密码确认不能为空</span>
						</div> 
					</div>
						<div class="col-sm-1"></div>
			  </div>
				  
			<div class="row col-sm-12 form-inline buttonrow">
				<div class="form-group col-sm-12 form-inline" style="text-align: center;">
					<div class="btn-group">
						<button id="setadd"  class="btn btn-primary">
							<i class="glyphicon glyphicon-ok" aria-hidden="true"></i>保存
						</button>
					</div>
					
					<div class="btn-group">
						<button id="ColseModalView"  class="btn btn-warning">
							<i class="glyphicon glyphicon-remove" aria-hidden="true"></i>取消
						</button>
					</div>
				</div>
			 </div>	  
			
			</div>
					
		<!-- End Panel Other -->
		
    </div>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js"></script>
	
	<script type="text/javascript">
		 $(function(){
			 // setTimeout(function(){
				//  $("#pwd").val("");
				//  $("#newpwd").val("");
				//  $("#newpwds").val("");
			 // },1000);
		 })
		
		 $("#setadd").click(function(){
				/**
				 * 获取参数值
				 */
				var obj=SaveMesDate("manageIntegral");
				if(!obj["ajax"])return;
				if(obj["newpwd"]!=obj["newpwds"]){
					// alert("两次密码不同，请重新输入");
					Ewin.alert({ message: "两次密码不同，请重新输入" });
					return;
				}
				
				if(obj["newpwd"]=="123456"){
					// alert("123456为初始默认密码，请使用其他密码");
					Ewin.alert({ message: "123456为初始默认密码，请使用其他密码!" });
					return;
				}
				
			// 	var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
			// 　　if(reg.test(obj["newpwd"])){     
			// 	   var strObj = document.getElementById("newpwd");  
			// 	   strObj.value = "";  
			// 	   strObj.focus();     
			// 	}			
				 
				// alert(JSON.stringify(obj));
				/**
				 * 提交参数
				 * obj：提交参数
				 * true:继续执行
				 */
				var ajax=doajax("${pageContext.request.contextPath}/login/changepwd.action",obj);
				
				if(ajax){
					var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
					parent.layer.close(index);	 
				}
			 
		 });
		 
		 $("#ColseModalView").click(function(){
			 var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
			 parent.layer.close(index);
		 });
	
		
	</script>

</body>

</html>
<script type="text/javascript">
(function ($) {
    window.Ewin = function () {
        var html = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
                              '<div class="modal-dialog modal-sm">' +
                                  '<div class="modal-content">' +
                                      '<div class="modal-header">' +
                                          '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
                                          '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
                                      '</div>' +
                                      '<div class="modal-body">' +
                                      '<p>[Message]</p>' +
                                      '</div>' +
                                       '<div class="modal-footer">' +
        '<button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>' +
        '<button type="button" class="btn btn-primary ok" data-dismiss="modal">[BtnOk]</button>' +
    '</div>' +
                                  '</div>' +
                              '</div>' +
                          '</div>';


        var dialogdHtml = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
                              '<div class="modal-dialog">' +
                                  '<div class="modal-content">' +
                                      '<div class="modal-header">' +
                                          '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
                                          '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
                                      '</div>' +
                                      '<div class="modal-body">' +
                                      '</div>' +
                                  '</div>' +
                              '</div>' +
                          '</div>';
        var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
        var generateId = function () {
            var date = new Date();
            return 'mdl' + date.valueOf();
        }
        var init = function (options) {
            options = $.extend({}, {
                title: "操作提示",
                message: "提示内容",
                btnok: "确定",
                btncl: "取消",
                width: 200,
                auto: false
            }, options || {});
            var modalId = generateId();
            var content = html.replace(reg, function (node, key) {
                return {
                    Id: modalId,
                    Title: options.title,
                    Message: options.message,
                    BtnOk: options.btnok,
                    BtnCancel: options.btncl
                }[key];
            });
            $('body').append(content);
            $('#' + modalId).modal({
                width: options.width,
                backdrop: 'static'
            });
			
			var $modal_dialog = $('.modal-content');
			var m_top = ( $(window).height() - $modal_dialog.height() )/3;
			$modal_dialog.css({'margin': m_top + 'px auto'});
			
            $('#' + modalId).on('hide.bs.modal', function (e) {
                $('body').find('#' + modalId).remove();
            });
            return modalId;
        }

        return {
            alert: function (options) {
                if (typeof options == 'string') {
                    options = {
                        message: options
                    };
                }
                var id = init(options);
                var modal = $('#' + id);
                modal.find('.ok').removeClass('btn-success').addClass('btn-primary');
                modal.find('.cancel').hide();
					
                return {
                    id: id,
                    on: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.find('.ok').click(function () { callback(true); });
                        }
                    },
                    hide: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.on('hide.bs.modal', function (e) {
                                callback(e);
                            });
                        }
                    }
                };
            },
            confirm: function (options) {
                var id = init(options);
                var modal = $('#' + id);
                modal.find('.ok').removeClass('btn-primary').addClass('btn-success');
                modal.find('.cancel').show();
                return {
                    id: id,
                    on: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.find('.ok').click(function () { callback(true); });
                            modal.find('.cancel').click(function () { callback(false); });
                        }
                    },
                    hide: function (callback) {
                        if (callback && callback instanceof Function) {
                            modal.on('hide.bs.modal', function (e) {
                                callback(e);
                            });
                        }
                    }
                };
            },
            dialog: function (options) {
                options = $.extend({}, {
                    title: 'title',
                    url: '',
                    width: 800,
                    height: 550,
                    onReady: function () { },
                    onShown: function (e) { }
                }, options || {});
                var modalId = generateId();

                var content = dialogdHtml.replace(reg, function (node, key) {
                    return {
                        Id: modalId,
                        Title: options.title
                    }[key];
                });
                $('body').append(content);
                var target = $('#' + modalId);
                target.find('.modal-body').load(options.url);
                if (options.onReady())
                    options.onReady.call(target);
                target.modal();
                target.on('shown.bs.modal', function (e) {
                    if (options.onReady(e))
                        options.onReady.call(target, e);
                });
                target.on('hide.bs.modal', function (e) {
                    $('body').find(target).remove();
                });
            }
        }
    }();
})(jQuery);

</script>
