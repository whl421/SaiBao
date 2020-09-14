/**
 * 弹出表单加载的js文件
 * thisiframeid 当前表单的iframe的id
 * parentiframedate 传入的当前iframe值
 * parentiframeid 打开当前iframe的操作iframe的id
 * pusetype 如果要在关闭时对打开当前界面的iframe界面操作的 指令  对应的响应函数名称 dofunction
 */
var thisiframeid=null;
var parentiframedate=null;
var parentiframeid=null;
var pusetype=null;
$(function(){
	
	
	thisiframeid=window.frameElement.id; // parent.getThisiframeid();
	//如果为弹出窗口使用
	parentiframeid=parent.getParentIframeid();
	parentiframedate=parent.getParentIframeDate();
	
	
	pusetype=parent.getpusetype();
	parent.parendate=null;
	parent.parentiframeid=null;
	parent.pusetype=null;
	
});
/**
 * @param {Object} usetype 回调使用标识
 * @param {Object} date 传递参数
 * @param {Object} titletext 弹出标题
 * @param {Object} width 弹出宽度
 * @param {Object} height 弹出高度
 * @param {Object} _url 弹出路径
 */
function PopupModalView(date,usetype,titletext,width,height,_url){
	parent.PopupModalView(thisiframeid,date,usetype,titletext,width,height,_url);
}
/**
 * @param {Object} data  传递参数
 */
function ColseModalView(data){
	parent.ColseModalView(parentiframeid,data,pusetype);
	var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	parent.layer.close(index);
}
/**
 * @param {Object} data  传递参数  刷新
 */
function RefreshModalView(data){
	parent.ColseModalView(parentiframeid,data,pusetype);
}

/**
 * 获得要提交的字段,不包含复选框 和 单选框
 * 	 并验证是否为空
 * @param name 要提交的参数
 */
function SaveMesDate(name){
	var obj=new Object();
	obj["ajax"]=true;
	var input = document.getElementsByTagName('input');
	if(input)
    for(i = 0; i < input.length; i++) {   
      if(input[i].getAttribute("name") == name && input[i].getAttribute("type")!="checkbox" 
    	  													&& input[i].getAttribute("type")!="radio") {   
         var inputid=input[i].getAttribute("id")||input[i]["id"];   
         obj[inputid]=input[i].value;
         IsNotNull(obj,input[i],obj[inputid]);
      }   
    }
    var select = document.getElementsByTagName('select');   
    if(select)
    for(i = 0; i < select.length; i++) {   
      if(select[i].getAttribute("name") == name ) {   
         var selectid=select[i].getAttribute("id")||select[i]["id"];   
         obj[selectid]=select[i].value||$("#"+selectid).val();
         IsNotNull(obj,select[i],obj[selectid]);
      }   
    }
    var textarea = document.getElementsByTagName('textarea');  
    if(textarea)
    for(i = 0; i < textarea.length; i++) {   
      if(textarea[i].getAttribute("name") == name ) { 
         var textareaid=textarea[i].getAttribute("id")||textarea[i]["id"];  
         obj[textareaid]=textarea[i].value||$("#"+textareaid).val();
         IsNotNull(obj,textarea[i],obj[textareaid]);
      }   
    }
    return obj;
}
/**
 * 根据需求判定是否可以为空
 * obj 回床参数
 * object html标签元素
 * val 标签值
 * @param obj
 */
function IsNotNull(obj,object,val){
	var isnull=object.getAttribute("null");
	if(isnull&&isnull=="no"&&(!val||val=="")){
	//	var alt=object.getAttribute("alt");
	//	alert(alt+"\u000d\u000a\u4e0d\u80fd\u4e3a\u7a7a"); //不能为空
		$(object).addClass("errinput");
		if($(object).parent().find(".errorspan"))$(object).parent().find(".errorspan").css("display","block");
		if($(object).parent().find(".errorselectspan"))$(object).parent().find(".errorselectspan").css("display","block");
		obj["ajax"]=false;
	}
}
/**
 * 不为空输入加载事件响应
 */
onload=function(){
	var name="manageIntegral";
	var input = document.getElementsByTagName('input');
	if(input)
    for(i = 0; i < input.length; i++) {   
      if(input[i].getAttribute("name") == name && input[i].getAttribute("type")!="checkbox" 
    	  													&& input[i].getAttribute("type")!="radio") {   
         IsNotNulls(input[i],"input");
      }   
    }
    var select = document.getElementsByTagName('select');   
    if(select)
    for(i = 0; i < select.length; i++) {   
      if(select[i].getAttribute("name") == name ) {   
         IsNotNulls(select[i],"select");
      }   
    }
    var textarea = document.getElementsByTagName('textarea');  
    if(textarea)
    for(i = 0; i < textarea.length; i++) {   
      if(textarea[i].getAttribute("name") == name ) { 
         IsNotNulls(textarea[i],"textarea");
      }   
    }
} 
function IsNotNulls(object,type){
	var isnull=object.getAttribute("null");
	if(type=="input"&&isnull&&isnull=="no"){
		$(object).change(function(){
			$(object).removeClass("errinput");
			$(object).parent().find(".errorspan").css("display","none");
		});
		$(object).blur(function(){
			var val=$(object).val();
			if(!val||val==""){
				$(object).addClass("errinput");
				$(object).parent().find(".errorspan").css("display","block");
			}
		});
	}
	if(type=="select"&&isnull&&isnull=="no"){
		$(object).focus(function(){
			$(object).removeClass("errinput");
			$(object).parent().find(".errorselectspan").css("display","none");
			
		});
		$(object).blur(function(){
			var val=$(object).val();
			if(!val||val==""){
				$(object).addClass("errinput");
				$(object).parent().find(".errorselectspan").css("display","block");
				
			}
		});
	}
}
/**
 * 时间选择器
 */
function setgetdate(indexid){
	$("#"+indexid).datetimepicker({
		language: "zh-CN",
		format:"yyyy-mm-dd",
		todayBtn: true,
		autoclose: true,
		startView:2,
		minView: "month", //选择日期后，不会再跳转去选择时分秒 
		pickerPosition: "bottom-left"
	});
}
function setgettime(indexid){
	$("#"+indexid).datetimepicker({
		format:"hh:ii:ss",
		language: "zh-CN",
		todayBtn: true,
		autoclose: true,
		minView:0,
		startView:1,
		pickerPosition: "bottom-left"
		// maxViewMode: 0,
	});
}
function setgetdatetime(indexid){
	$("#"+indexid).datetimepicker({
		format:"yyyy-mm-dd hh:ii:ss",
		language: "zh-CN",
		todayBtn: true,
		autoclose: true,
		pickerPosition: "bottom-left",
	});
}
/**
 * button :按钮对象
 * _url :提交参数访问的链接
 * obj :提交的参数
 * @param button
 * @param _url
 * @param obj
 */
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
						// Ewin.alert({ message: date["textinfo"] });
			 		}else if(date["text"]){
						alert(date["text"]);
						// Ewin.alert({ message: date["text"] });
					}else{
						alert("\u670d\u52a1\u5668\u7e41\u5fd9\uff0c\u63d0\u4ea4\u5931\u8d25");  //服务器繁忙，提交失败
						// Ewin.alert({ message: "\u670d\u52a1\u5668\u7e41\u5fd9\uff0c\u63d0\u4ea4\u5931\u8d25" });
					}
			 		ajax=false;
			 	}else{
			 		ajax=true;
			 	}	
			 	button=false;
		    },
		     error:function(){
					alert("\u000d\u000a\u000d\u000a\u7f51\u7edc\u7e41\u5fd9\uff0c\u63d0\u4ea4\u5931\u8d25"); //网络繁忙，提交失败
					// Ewin.alert({ message: "\u000d\u000a\u000d\u000a\u7f51\u7edc\u7e41\u5fd9\uff0c\u63d0\u4ea4\u5931\u8d25" });
					button=false;
					ajax=false;
			}
		});
		return ajax;
}


/*判断终端是手机还是电脑--用于判断文件是否导出(电脑需要导出)*/
function phoneOrPc(){
	var sUserAgent = navigator.userAgent.toLowerCase();  
	var bIsIpad = sUserAgent.match(/ipad/i) == "ipad";  
	var bIsIphoneOs = sUserAgent.match(/iphone os/i) == "iphone os";  
	var bIsMidp = sUserAgent.match(/midp/i) == "midp";  
	var bIsUc7 = sUserAgent.match(/rv:1.2.3.4/i) == "rv:1.2.3.4";  
	var bIsUc = sUserAgent.match(/ucweb/i) == "ucweb";  
	var bIsAndroid = sUserAgent.match(/android/i) == "android";  
	var bIsCE = sUserAgent.match(/windows ce/i) == "windows ce";  
	var bIsWM = sUserAgent.match(/windows mobile/i) == "windows mobile";  
	if (bIsIpad || bIsIphoneOs || bIsMidp || bIsUc7 || bIsUc || bIsAndroid || bIsCE || bIsWM) {  
		return false;  
	} else {  
	    return true; 
	}  
}


// (function ($) {
//     window.Ewin = function () {
//         var html = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
//                               '<div class="modal-dialog modal-sm">' +
//                                   '<div class="modal-content">' +
//                                       '<div class="modal-header">' +
//                                           '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
//                                           '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
//                                       '</div>' +
//                                       '<div class="modal-body">' +
//                                       '<p>[Message]</p>' +
//                                       '</div>' +
//                                        '<div class="modal-footer">' +
//         '<button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>' +
//         '<button type="button" class="btn btn-primary ok" data-dismiss="modal">[BtnOk]</button>' +
//     '</div>' +
//                                   '</div>' +
//                               '</div>' +
//                           '</div>';
// 
// 
//         var dialogdHtml = '<div id="[Id]" class="modal fade" role="dialog" aria-labelledby="modalLabel">' +
//                               '<div class="modal-dialog">' +
//                                   '<div class="modal-content">' +
//                                       '<div class="modal-header">' +
//                                           '<button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>' +
//                                           '<h4 class="modal-title" id="modalLabel">[Title]</h4>' +
//                                       '</div>' +
//                                       '<div class="modal-body">' +
//                                       '</div>' +
//                                   '</div>' +
//                               '</div>' +
//                           '</div>';
//         var reg = new RegExp("\\[([^\\[\\]]*?)\\]", 'igm');
//         var generateId = function () {
//             var date = new Date();
//             return 'mdl' + date.valueOf();
//         }
//         var init = function (options) {
//             options = $.extend({}, {
//                 title: "操作提示",
//                 message: "提示内容",
//                 btnok: "确定",
//                 btncl: "取消",
//                 width: 200,
//                 auto: false
//             }, options || {});
//             var modalId = generateId();
//             var content = html.replace(reg, function (node, key) {
//                 return {
//                     Id: modalId,
//                     Title: options.title,
//                     Message: options.message,
//                     BtnOk: options.btnok,
//                     BtnCancel: options.btncl
//                 }[key];
//             });
//             $('body').append(content);
//             $('#' + modalId).modal({
//                 width: options.width,
//                 backdrop: 'static'
//             });
// 			
// 			var $modal_dialog = $('.modal-content');
// 			var m_top = ( $(window).height() - $modal_dialog.height() )/3;
// 			$modal_dialog.css({'margin': m_top + 'px auto'});
// 			
//             $('#' + modalId).on('hide.bs.modal', function (e) {
//                 $('body').find('#' + modalId).remove();
//             });
//             return modalId;
//         }
// 
//         return {
//             alert: function (options) {
//                 if (typeof options == 'string') {
//                     options = {
//                         message: options
//                     };
//                 }
//                 var id = init(options);
//                 var modal = $('#' + id);
//                 modal.find('.ok').removeClass('btn-success').addClass('btn-primary');
//                 modal.find('.cancel').hide();
// 					
//                 return {
//                     id: id,
//                     on: function (callback) {
//                         if (callback && callback instanceof Function) {
//                             modal.find('.ok').click(function () { callback(true); });
//                         }
//                     },
//                     hide: function (callback) {
//                         if (callback && callback instanceof Function) {
//                             modal.on('hide.bs.modal', function (e) {
//                                 callback(e);
//                             });
//                         }
//                     }
//                 };
//             },
//             confirm: function (options) {
//                 var id = init(options);
//                 var modal = $('#' + id);
//                 modal.find('.ok').removeClass('btn-primary').addClass('btn-success');
//                 modal.find('.cancel').show();
//                 return {
//                     id: id,
//                     on: function (callback) {
//                         if (callback && callback instanceof Function) {
//                             modal.find('.ok').click(function () { callback(true); });
//                             modal.find('.cancel').click(function () { callback(false); });
//                         }
//                     },
//                     hide: function (callback) {
//                         if (callback && callback instanceof Function) {
//                             modal.on('hide.bs.modal', function (e) {
//                                 callback(e);
//                             });
//                         }
//                     }
//                 };
//             },
//             dialog: function (options) {
//                 options = $.extend({}, {
//                     title: 'title',
//                     url: '',
//                     width: 800,
//                     height: 550,
//                     onReady: function () { },
//                     onShown: function (e) { }
//                 }, options || {});
//                 var modalId = generateId();
// 
//                 var content = dialogdHtml.replace(reg, function (node, key) {
//                     return {
//                         Id: modalId,
//                         Title: options.title
//                     }[key];
//                 });
//                 $('body').append(content);
//                 var target = $('#' + modalId);
//                 target.find('.modal-body').load(options.url);
//                 if (options.onReady())
//                     options.onReady.call(target);
//                 target.modal();
//                 target.on('shown.bs.modal', function (e) {
//                     if (options.onReady(e))
//                         options.onReady.call(target, e);
//                 });
//                 target.on('hide.bs.modal', function (e) {
//                     $('body').find(target).remove();
//                 });
//             }
//         }
//     }();
// })(jQuery);

