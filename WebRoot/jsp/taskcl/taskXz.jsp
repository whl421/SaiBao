<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>
	<head>
		 <meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>任务添加协作</title>
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
			<!-- <form id="myform"   enctype="multipart/form-data"> -->
			<div class="ibox-content col-sm-12 col-xs-12">
				<input type="hidden" id = "tasklistid" name="tasklistid" value="${map.tasklistid }" >
				<div class="col-sm-12 col-xs-12">
					<div class="form-group col-sm-12 col-xs-12">
						<label  class="date-label col-sm-2 col-xs-2">任务编号</label>
						<div class="col-sm-4 col-xs-4">
							<input type="text" class="form-control" value="${map.taskcode}"
								null="no" id="taskcode" name="taskcode" readonly="readonly">
						</div> 
						<label  class="date-label col-sm-2 col-xs-2">任务标题</label>
						<div class="col-sm-4 col-xs-4">
							<input type="text" class="form-control" value="${map.tasktitle}"
								null="no" id="tasktitle" name="tasktitle" readonly="readonly">
						</div> 
					</div>
				</div>
				<div class="col-sm-12 col-xs-12">
					<div class="form-group col-sm-12 col-xs-12">
						<label  class="date-label col-sm-2 col-xs-2">任务说明</label>
						<div class="col-sm-10 col-xs-10">
							<input type="text" class="form-control" value="${map.taskmemo}"
								null="no" id="taskmemo" name="taskmemo" readonly="readonly">
						</div> 
					</div>
				</div>
				<div class="col-sm-12 col-xs-10">
					<div class="form-group col-sm-12 col-xs-12">
						<label  class="date-label col-sm-2 col-xs-2">委托编号</label>
						<div class="col-sm-4 col-xs-4">
							<input type="text" class="form-control" value="${map.ordercode }"
								null="no" id="ordercode" name="ordercode" readonly="readonly">
						</div> 
						<label  class="date-label col-sm-2 col-xs-2">委托名称</label>
						<div class="col-sm-4 col-xs-4">
							<input type="text" class="form-control" value="${map.ordername }"
								null="no" id="ordername" name="ordername" readonly="readonly">
						</div> 
					</div>
				</div>
				<div class="col-sm-12 ">
					<div class="form-group col-sm-12">
						<label  class="date-label col-sm-2">附件</label>
						<div class="col-sm-10">
							<span id="upload" class="form-control" style="font-size: 14px;padding: 10px;height: 50px;background-color:#eee;">
								<c:forEach items="${fjmap }" var="fj">
									<a style="display: inline-block;" href="../order/downLoad.action?id=${fj.id}">${fj.sourcefile }</a>&nbsp;&nbsp;
								</c:forEach>
							</span>
						</div> 
					</div>
				</div>
				<div class="col-sm-12 col-xs-12">
					<div class="form-group col-sm-12 col-xs-12">
						<label  class="date-label col-sm-2 col-xs-2">上一阶段完成人</label>
						<div class="col-sm-4 col-xs-4">
							<input type="text" class="form-control" value="${map.upexecusername }"
								null="no" id="upexecusername" name="upexecusername" readonly="readonly">
						</div> 
						<label  class="date-label col-sm-2 col-xs-2">委托人</label>
						<div class="col-sm-4 col-xs-4">
							<input type="text" class="form-control" value="${map.orderuser }"
								null="no" id="orderuser" name="orderuser" readonly="readonly">
						</div> 
					</div>
				</div>
				<div class="col-sm-12 col-xs-12">
					<div class="form-group col-sm-12 col-xs-12">
						<label  class="date-label col-sm-2 col-xs-2">上一阶段备注</label>
						<div class="col-sm-10 col-xs-10">
							<input type="text" class="form-control" value="${map.upmemo }"
								null="no" id="upmemo" name="upmemo" readonly="readonly">
						</div> 
					</div>
				</div>
				<div class="col-sm-12 col-xs-12">
					<div class="form-group col-sm-12 col-xs-12">
						<label  class="date-label col-sm-2 col-xs-2">协作人</label>
						<div class="col-sm-10 col-xs-10">
							<select style="width:100%" title="初审人" multiple="multiple" 
							 null="no" id="xzuserid" name="manageIntegral" >
								  <c:forEach items="${userMap }" var="user" >
									<optgroup label="${user.key }" >
									<c:forEach items="${user.value }" var="userlist" >
										<option value="${userlist.userid }">${userlist.username }</option>&nbsp;&nbsp;
									</c:forEach>
								  </c:forEach>
							</select>
						</div> 
					</div>
				</div>
				<div class="col-sm-12 col-xs-12">
					<div class="form-group col-sm-12 col-xs-12">
						<label  class="date-label col-sm-2 col-xs-2">协作说明</label>
						<div class="col-sm-10 col-xs-10">
							<textarea rows="2" class="form-control" 
								null="no" id="xzplain" name="xzplain" >${map.xzplain }</textarea>
						</div> 
					</div>
				</div>
				<div class="col-sm-12 col-xs-12 form-inline buttonrow">
					<div class="form-group col-sm-12 col-xs-12 form-inline" style="text-align: center;">
						<div class="btn-group" >
							<button onclick="setadd(this);" class="btn btn-primary">
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
			<!-- </form> -->
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
			});
			$("#xzuserid").multipleSelect({
			  multiple: true,
			 // width: 460,
			  multipleWidth: 85,
			  selectAll: false,
			  //选择达到20条记录才会隐藏 默认3条
			  minimumCountSelected:20,
			  //openOnHover:true,
			  filter: true,
			  //分组不可选
			  // hideOptgroupCheckboxes:true,
			  //只可单选
			  // single:true
			});
			//获取多选人员名称
			function getCkName(checkuserid, ele) {
				var objids = new Object();
				for(var i=0; i<checkuserid.length; i++){
					if(checkuserid[i]) {
						objids[checkuserid[i]] = checkuserid[i];
					}
				}
				var opttext = "";
				var ckuserid = document.getElementById(ele);
				if(ckuserid){
					var cks = ckuserid.childNodes;
					if(cks){
						for(var i=0; i<cks.length; i++){
							var optg = cks[i];
							if(optg){
								var opts = optg.childNodes;
								if(opts){
									for(var j=0; j<opts.length; j++){
										var opt = opts[j];
										if(opt&&opt.value&&objids[opt.value]){
											if(opttext == "") {
												opttext += opt.innerText;
											} else {
												opttext += ","+opt.innerText;
											}
										}
									}
								}
							}
						}
					}
				}
				return opttext;
			}
			//新增
			function setadd(aobj){
				$(this).attr("disabled",true); 
				var tasklistid = "${map.tasklistid }";
				var xzuserid = $("#xzuserid").val();
				var xzplain = $("#xzplain").val();
				var xzusername = "";
				if(xzuserid != "" && xzuserid != null) {
					xzusername = getCkName(xzuserid, "xzuserid");
				} else {
					alert("协作人不能为空");
					return;
				}
				var data = {
					"tasklistid" : tasklistid,
					"xzuserid" : xzuserid,
					"xzusername" : xzusername,
					"xzplain" : xzplain,
				};
				var str = JSON.stringify(data);
				parent.layer.load(2); 
				$.ajax({
					url : '${pageContext.request.contextPath}/taskCl/taskXz.action?rundd='+new Date().getTime(),
					type : 'post',
					data : {tablejson:str},
					dataType : "json",
					async : false,
					success : function(date){
						parent.layer.closeAll('loading');
						if(date == null || date == 'null' || date == 'NULL' || date == ' ' || date["info"] == '0'){
							alert("服务器繁忙，提交失败");
							$(this).attr("disabled",false);
						}else{
							ColseModalView(pusetype);
						}		
					},
					error:function(){
						parent.layer.closeAll('loading');
						alert("网络繁忙，提交失败");
						$(this).attr("disabled",false);
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
