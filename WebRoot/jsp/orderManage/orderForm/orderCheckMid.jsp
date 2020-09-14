<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>中验收</title>
		<meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
		<meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">
		
		<link rel="shortcut icon" href="H5.ico"> 
		<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
		<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/font-awesome.css?v=4.4.0" rel="stylesheet">
		<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/jquery.min.js?v=2.1.4"></script>
		<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap.min.js?v=3.3.6"></script>
		<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css"></link> 
		<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap-datetimepicker.min.js"></script>
		<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/form.css?v=4.1.0" rel="stylesheet">
		
		<link href="${pageContext.request.contextPath}/jsp/manageIntegral/js/select/select/multiple-select.css" rel="stylesheet">
		
		<link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.7/css/select2.min.css" rel="stylesheet" />
		<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.7/js/select2.min.js"></script>
		<!-- <link href="../js/select/select2/css/select2.min.css" rel="stylesheet" />
		<script src="../js/select/select2/js/select2.min.js"></script> -->
			
		<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/select/select/multiple-select.js" ></script>
		
		
	</head>
	<body class="gray-bg" >
		<div class="wrapper wrapper-content animated fadeInRight">
			<form id="myform" enctype="multipart/form-data" method="post">
			<div class="ibox-content col-sm-12">
				<input type="hidden" id = "orderid" name="orderid" value="${map.orderid }" >
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2 ">委托编号</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" value="${map.ordercode }"
								null="no" id="ordercode" name="ordercode" readonly="readonly">
						</div> 
						<label  class="date-label col-sm-2">委托名称</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" value="${map.ordername }"
								null="no" id="ordername" name="ordername" readonly="readonly">
						</div> 
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2 ">联系人</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" value="${map.contacts }"
								null="no" id="contacts" name="contacts" readonly="readonly">
						</div> 
						<label  class="date-label col-sm-2">联系方式</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" value="${map.phone }"
								null="no" id="phone" name="phone" readonly="readonly">
						</div> 
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2">委托时间</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" value="${map.ordertime }"
								null="no" id="ordertime" name="ordertime" readonly="readonly">
						</div> 
						<label  class="date-label col-sm-2">委托人</label>
						<div class="col-sm-4">
							<input type="text" class="form-control" value="${map.orderuser }"
								null="no" id="orderuser" name="orderuser" readonly="readonly">
						</div> 
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2">客户名称</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" value="${map.khname }"
								null="no" id="khname" name="khname" readonly="readonly">
						</div> 
					</div>
				</div> 
				<div class="col-sm-12" >
					<div class=" form-group col-sm-12 ">
						<label  class="date-label col-sm-2"><span style="color:red">*</span>状态</label>
						<div class="col-sm-4 ">
							<div class="form-control col-sm-8"  > 
							 <label class="radio-inline">
								<input type="radio"  value="01" name="ostate" id="ostate" checked >中验收
							 </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
							</div>
						</div>	 
					</div>
				</div>
				<div class="col-sm-12 form-group">
					<label  class="date-label col-sm-2">上传文件&nbsp;</label>
					<div class="col-sm-10"> 
							<div class="form-group col-sm-12">
								<div class="col-sm-4">
									<input type="file" class="" null="no" multiple name="file" id="file" />
									<input type="hidden" name="system" id="system" value="ts"/>
								</div>  
							</div> 
							<span id="upload">
								<div style="font-size: 14px;padding: 10px;">
									<a href="../daytask/downLoad.action?id=${fj.id}">${fj.sourcefile }</a><br/>
								</div>
							</span>
					</div> 
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2">中验收说明</label>
						<div class="col-sm-10">
							<textarea  rows="5" class="form-control" maxlenth="500"
							  id="qrmemo"  name="qrmemo">${map.midinspectedcase}</textarea >
						</div> 
					</div>
				</div>
				<div class="col-sm-12 form-inline buttonrow">
					<div class="form-group col-sm-12 form-inline" style="text-align: center;">
						<div class="btn-group" >
							<button onclick="setMiddleCheckAdd(this);" class="btn btn-primary">
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
		   </form>
		</div>
		<!-- ../js/index.js  每个弹出表单都需要导入 -->
		<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js"></script>
		<script type="text/javascript">
			$(function(){
				//数字验证
				$.each($("#orderprice"),function(index,obj){
					$(obj).bind("input propertychange", function() {
						// alert($(this).val());
						var ob=$(this);
						//先把非数字的都替换掉，除了数字和.
						ob.val(ob.val().replace(/[^\d]/g, ""));
					});
				});
			})
			//中验收
			function setMiddleCheckAdd(aobj){
				$(this).attr("disabled",true); 
				var orderid = "${map.orderid }";
				var ordercode = "${map.ordercode }";
				var ostate = $('input[name="ostate"]:checked').val();
				var orderprice = $("#orderprice").val();
				var qrmemo = $("#qrmemo").val();
				if( qrmemo.length>500 ) {
				    alert("中验收内容不能超过500字");
					return;
				} 
				if( qrmemo.length==0 ) {
				    alert("中验收内容不能为空");
					return;
				} 
				var data = {
					"orderId" : orderid,
					"ordercode" : ordercode,
					"orderPrice" : orderprice,
					"inspectedCase" : qrmemo,
					"status" : ostate
				};
				var form = document.getElementById("myform");
				//创建表单对象，并加入文件对象
				var formFile = new FormData(form);
				var datas = formFile;
				parent.layer.load(2); 
				$.ajax({
					type: "post",
					url : '${pageContext.request.contextPath}/orderCheck/insertCheck.action?rundd='+new Date().getTime(),
					dataType: "json",
					data:datas,
					async : false,
					cache: false,
					processData: false,
					contentType: false,
					success: function (data) {
						parent.layer.closeAll('loading');
						if(data == null || data == 'null' || data == 'NULL' || data == ' ' || data["info"] == '0'){
							if(data["text"]) {
								alert(data["text"]);
							} else {
								alert("服务器繁忙，提交失败");
							}
							$(this).attr("disabled",false);
						}else{
							ColseModalView(pusetype);
						}
					},
					error: function (a,b,c) {
						parent.layer.closeAll('loading');
						alert('网络繁忙，提交失败!');
					}
				});
			}
			
			//取消按钮
			$("#ColseModalView").click(function(){
				var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				parent.layer.close(index);
			});
		
			/**
			 * 放弃
			 */
			function giveup(){
				//关闭窗口   			
				ColseModalView(null);
			}
			
 			/**
 			 * 回调参数
 			 * @param {Object} usetype
			 * @param {Object} data
 			 */
 			function dofunction(usetype,data){
 				if(usetype=="department"){
 					$("#departmentname")=data["departmentname"];
 					departmentcode=data["departmentcode"];
 				}
 			}
 
		</script>
		<script type="text/javascript" >
			var attachname = "attach";
			var _indexobj=new Object();
			var iindex=1;
			function addinput(){
				if(iindex<10){
					var attach = attachname + iindex ;
					if(createInput(attach)){
						iindex=iindex+1;
						document.getElementById("FileUpload"+(iindex-1)).click();
					}
				}
			} 
			function deleteinput(){
			 
				for(iindex;iindex>0;iindex--){
					var cdiv=document.getElementById("div"+iindex);
					if(cdiv)cdiv.parentNode.removeChild(cdiv);
				}
				_indexobj=new Object();
				iindex=1;
			} 
			function createInput(nm){ 
				var aElement=document.createElement("div"); 
				var html = "<input type='file' style='display:none' id='FileUpload"
						 + iindex + "' name='file"
						 + iindex + "' title='选择图片' size='45' onchange='javascript:showphoto("+iindex+");'>"
						 +"<span id='spantext"+iindex+"' style='color:blue;font-size:14px;display:none'></span>&nbsp;&nbsp;&nbsp;"
						 + "<a name='"+iindex+"' href='javascript:removethisinput("+iindex+")' style='font-size:14px;' >删除</a>";
				aElement.innerHTML=html;
				aElement.setAttribute("id","div"+iindex);
				aElement.style.display='none'; 
				 
				if(document.getElementById("upload").appendChild(aElement) == null)
				return false;
				return true;
			} 
			function removethisinput(index){
				//var idi=obj.name;
				var aElement = document.getElementById("div"+index);
				 //alert(idi);
				 var e=document.getElementById("FileUpload"+index).files[0];
				 var filename=e.name;
				if(aElement.parentNode.removeChild(aElement)){
					 _indexobj[filename]=null;
				}else{
					alert("删除失败");
				}
			}
			function removeInput(nm){
				var aElement = document.getElementById("upload");
				if(aElement.removeChild(aElement.lastChild) == null)
				return false;
				return true; 
			}
			function showphoto(index){
				/**
				*上传格式限制
				*/
				  //var re=/.(xls|sxls|)$/;  //jpg|jpeg|JPG|JPEG|txt|png   
				var x=document.getElementById("FileUpload"+index);
				if(x){
					var filename=x.value;
					if(filename==""){
						var cdiv=document.getElementById("div"+index);
						if(cdiv)cdiv.parentNode.removeChild(cdiv);
						alert(cdiv);
					} else {
						var e=document.getElementById("FileUpload"+index).files[0];
						var filename=e.name;
						if(_indexobj[filename]||_indexobj[filename]=="1"){
							alert("文件上传重复");
						}else{
							_indexobj[filename]="1";
							var size=e.size;
							document.getElementById("FileUpload"+index).style.display='none'; 
							document.getElementById("spantext"+index).style.display='inline'; 
							document.getElementById("div"+index).style.display='block';	 
							document.getElementById("spantext"+index).innerHTML="文件名称："+filename+",大小:"+size; 
						}	
					}
				}else{
					alert("浏览器异常");
				}
			}
		</script>
	</body>
</html>