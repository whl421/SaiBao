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
	<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/plugins/toastr/toastr.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/manageIntegral/cropper/cropper.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/manageIntegral/cropper/changimg.css">
	
	<style type="text/css">
		.gray-bg {
			background-color: #f3f3f4;
		}
		.tailoring-container {
			position: fixed;
			width: 100%;
			height: 100%;
			z-index: 1000;
			top: 10%;
			left: 21%;
		}
	</style>
	
</head>

<body class="gray-bg">
	 

<div style="display: block;" class="tailoring-container animated fadeInRight">
    
    <div class="tailoring-content" >
            <div class="tailoring-content-one">
                <label title="上传图片" for="chooseImg" class="l-btn choose-btn">
                    <input type="file" accept="image/jpg,image/jpeg,image/png" name="file" id="chooseImg" class="hidden" onchange="selectImg(this)">
                    选择图片
                </label>
				<button class="l-btn cropper-reset-btn" style="margin-left:100px">复位</button>
                <button class="l-btn cropper-rotate-btn">旋转</button>
                <button class="l-btn cropper-scaleX-btn">换向</button>
				<button class="l-btn sureCut" id="sureCut">确定</button>
               <!-- <div class="close-tailoring" onclick="closeTailor(this)">×</div> -->
            </div>
            <div class="tailoring-content-two">
                <div class="tailoring-box-parcel">
                    <img id="tailoringImg"  class="cropper-hidden">
					 
                </div>
                <div class="preview-box-parcel">
                    <p>图片预览：</p>
					<div class="circular previewImg" style="width: 128px; height: 128px;">
					<img   style="display: block; width: 450.378px; height: 239.498px; min-width: 0px !important; min-height: 0px !important; max-width: none !important; max-height: none !important; transform: translateX(-120.103px) translateY(-5.21673px);"></div>
                    <div class="square previewImg" style="width: 124px; height: 124px;"><img 
					  style="display: block; width: 450.378px; height: 239.498px; min-width: 0px !important; min-height: 0px !important; max-width: none !important; max-height: none !important; transform: translateX(-120.103px) translateY(-5.21673px);"></div>
                    
                </div>
            </div>
            <div class="tailoring-content-three">
               
                
            </div>
        </div>
</div>
<!--图片裁剪框 end-->


<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/cropper/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap.min.js?v=3.3.6"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/cropper/cropper.min.js"></script>
<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/toastr/toastr.min.js"></script>
<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/layer/layer.min.js"></script>
		
<script type="text/javascript">
	  toastr.options = {
	    "closeButton": false,
	    "debug": false,
	    "progressBar": false,
	    "positionClass": "toast-top-center",
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
</script>
 	
<script type="text/javascript">

    //弹出框水平垂直居中
    (window.onresize = function () {
        var win_height = $(window).height();
        var win_width = $(window).width();
        if (win_width <= 768){
            $(".tailoring-content").css({
                "top": (win_height - $(".tailoring-content").outerHeight())/2,
                "left": 0
            });
        }else{
            $(".tailoring-content").css({
                "top": (win_height - $(".tailoring-content").outerHeight())/2,
                "left": (win_width - $(".tailoring-content").outerWidth())/2
            });
        }
    })();

    //弹出图片裁剪框
    $("#replaceImg").on("click",function () {
        $(".tailoring-container").toggle();
    });
    //图像上传
    function selectImg(file) {
        if (!file.files || !file.files[0]){
            return;
        }
        var reader = new FileReader();
        reader.onload = function (evt) {
            var replaceSrc = evt.target.result;
            //更换cropper的图片
            $('#tailoringImg').cropper('replace',replaceSrc,false);//默认false，适应高度，不失真
        }
        reader.readAsDataURL(file.files[0]);
    }
    //cropper图片裁剪
    $('#tailoringImg').cropper({
		dragMode:'move',
        aspectRatio: 1/1,//默认比例
        preview: '.previewImg',//预览视图
        guides: true,  //裁剪框的虚线(九宫格)
        autoCropArea: 0.9,  //0-1之间的数值，定义自动剪裁区域的大小，默认0.8
        movable: false, //是否允许移动图片
        dragCrop: false,  //是否允许移除当前的剪裁框，并通过拖动来新建一个剪裁框区域
        movable: false,  //是否允许移动剪裁框
        resizable: false,  //是否允许改变裁剪框的大小
        zoomable: false,  //是否允许缩放图片大小
		//cropBoxMovable: false,	 //允许通过拖动移动裁剪框。
        cropBoxResizable: false,  //允许通过拖动调整裁剪框的大小。
		toggleDragModeOnDblclick: false, //在裁剪器上单击两次时，可以在“裁剪”和“移动”之间切换拖动模式。
		mouseWheelZoom: false,  //是否允许通过鼠标滚轮来缩放图片
        touchDragZoom: false,  //是否允许通过触摸移动来缩放图片
        rotatable: true,  //是否允许旋转图片
        crop: function(e) {
            // 输出结果数据裁剪图像。
        }
    });
    //旋转
    $(".cropper-rotate-btn").on("click",function () {
        $('#tailoringImg').cropper("rotate", 45);
    });
    //复位
    $(".cropper-reset-btn").on("click",function () {
        $('#tailoringImg').cropper("reset");
    });
    //换向
    var flagX = true;
    $(".cropper-scaleX-btn").on("click",function () {
        if(flagX){
            $('#tailoringImg').cropper("scaleX", -1);
            flagX = false;
        }else{
            $('#tailoringImg').cropper("scaleX", 1);
            flagX = true;
        }
        flagX != flagX;
    });
    //裁剪后的处理
    $("#sureCut").on("click",function () {
//     		parent.layer.load(2);
// 			//此处演示关闭
// 			setTimeout(function(){
// 			  parent.layer.closeAll('loading');
// 			}, 2000);
    	//	parent.layer.load(2,{ anim: 2});
        if ($("#tailoringImg").attr("src") == null ){
            return false;
        }else{
            var cas = $('#tailoringImg').cropper('getCroppedCanvas');//获取被裁剪后的canvas
            //var base64url = cas.toDataURL('image/png'); //转换为base64地址形式
			var base64url = cas.toDataURL("image/*", 0.5);
            //$("#finalImg").prop("src",base64url);//显示为图片的形式
			//alert(getImgSize(base64url));  
			//base64url=encodeURIComponent(base64url);
			base64url=base64url.toString();

			var imgsize=getImgSize(base64url);
			//parent.layer.closeAll('loading'); 
			if(imgsize>(5000)){
				Ewin.confirm({ message: "上传图片较大,压缩时间较长,确认要执行？" }).on(function (e) {
					if(e){
						fileimgupdate(base64url);
					}
				});		
			}else{
				fileimgupdate(base64url);
			}
            //关闭裁剪框
            //closeTailor();
        }
    });
	
	function fileimgupdate(base64url){
		var formData = new FormData();
		
		// formData.append("fileimg", base64url);
		formData.append("fileimg",convertBase64UrlToBlob(base64url));  
		parent.layer.load(2);
		
		$.ajax({
			url:'${pageContext.request.contextPath}/login/cropper.action?rundd='+new Date().getTime(),
			type:'post',
			//data:{tablejson:base64url},
			//dataType:"json",
			data: formData,
			processData : false, // 使数据不做处理
			contentType : false, // 不要设置Content-Type请求头
			async:true,
			success:function(date){
				if(date==null||date=='null'||date=='NULL'||date==' '||date["info"]=='0'){
					if(date["textinfo"]){
						//执行失败的提示
// 				 			alert(date["textinfo"]);
						toastr["error"](date["textinfo"], '操作失败!');
					}else{
						 //服务器繁忙，提交失败
						 toastr["error"]('', '操作失败!');
// 							alert("\u670d\u52a1\u5668\u7e41\u5fd9\uff0c\u63d0\u4ea4\u5931\u8d25");
					}
				}else{
					//执行成功
					showsussces();
					parent.Refreshtitleimg();
				}	
				parent.layer.closeAll('loading'); 	
			},
			 error:function(){
// 					alert("数据提交失败");
				parent.layer.closeAll('loading'); 
				toastr["error"]('', '操作失败!');
			}
		});
	}
	
	
	/**  
	 * 将以base64的图片url数据转换为Blob  
	 * @param urlData  
	 *            用url方式表示的base64图片数据  
	 */  
	function convertBase64UrlToBlob(urlData){  

		var bytes=window.atob(urlData.split(',')[1]);        //去掉url的头，并转换为byte  

		//处理异常,将ascii码小于0的转换为大于0  
		var ab = new ArrayBuffer(bytes.length);  
		var ia = new Uint8Array(ab);  
		for (var i = 0; i < bytes.length; i++) {  
			ia[i] = bytes.charCodeAt(i);  
		}  

		return new Blob( [ab] , {type : 'image/png'});  
	}
	
	function getImgSize(str) {
    //获取base64图片大小，返回KB数字
    var str = base64url.replace('data:image/jpeg;base64,', '');//这里根据自己上传图片的格式进行相应修改
    
    var strLength = str.length;
    var fileLength = parseInt(strLength - (strLength / 8) * 2);

    // 由字节转换为KB
    var size = "";
    size = (fileLength / 1024).toFixed(2);

    return parseInt(size);

  }
	
	
    //关闭裁剪框
    function closeTailor() {
        $(".tailoring-container").toggle();
    }
	//获取图片大小
	function getImgSize(base64url) {
    //获取base64图片大小，返回KB数字
    var str = base64url.replace('data:image/jpeg;base64,', '');//这里根据自己上传图片的格式进行相应修改
    
    var strLength = str.length;
    var fileLength = parseInt(strLength - (strLength / 8) * 2);

    // 由字节转换为KB
    var size = "";
    size = (fileLength / 1024).toFixed(2);
    
    
    
    return parseInt(size);

	}
	
	
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