<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"  prefix="fmt"%>
<!DOCTYPE html>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>标准管理</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">
	<meta http-equiv="Access-Control-Allow-Origin" content="*" />
    <link rel="shortcut icon" href="H5.ico"> 
	<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/font-awesome.css?v=4.4.0" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/jquery.min.js?v=2.1.4"></script>
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap.min.js?v=3.3.6"></script>
<!-- 	列表控件 -->
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
	<!-- <script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script> -->
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/demo/bootstrap-table-demo.js"></script>
<!-- 下拉控件	 -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/jsp/manageIntegral/css/bootstrap-select.css">
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap-select.js"></script>

    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/animate.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/organization.css?v=4.1.0" rel="stylesheet">
    
<!--     时间控件 -->
	<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/bootstrap-datetimepicker.css" rel="stylesheet" type="text/css"></link> 
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap-datetimepicker.min.js"></script>
	<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/bootstrap-datetimepicker.zh-CN.js"></script>

	<!--              树形结构引入css样式        	 -->
	<link href="${pageContext.request.contextPath}/jsp/manageIntegral/css/plugins/treeview/bootstrap-treeview.min.css" rel="stylesheet">
	
	
	<style>
	   .fixed-table-container thead th .sortable {
        background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABMAAAATCAQAAADYWf5HAAAAkElEQVQoz7X QMQ5AQBCF4dWQSJxC5wwax1Cq1e7BAdxD5SL+Tq/QCM1oNiJidwox0355mXnG/DrEtIQ6azioNZQxI0ykPhTQIwhCR+BmBYtlK7kLJYwWCcJA9M4qdrZrd8pPjZWPtOqdRQy320YSV17OatFC4euts6z39GYMKRPCTKY9UnPQ6P+GtMRfGtPnBCiqhAeJPmkqAAAAAElFTkSuQmCC");
        cursor: pointer;
        background-position: right;
        background-repeat: no-repeat;
        padding-right: 30px;
       }

      .fixed-table-container thead th .asc {
        background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABMAAAATCAYAAAByUDbMAAAAZ0lEQVQ4y2NgGLKgquEuFxBPAGI2ahhWCsS/gDibUoO0gPgxEP8H4ttArEyuQYxAPBdqEAxPBImTY5gjEL9DM+wTENuQahAvEO9DMwiGdwAxOymGJQLxTyD+jgWDxCMZRsEoGAVoAADeemwtPcZI2wAAAABJRU5ErkJggg==");
        cursor: pointer;
        background-position: right;
        background-repeat: no-repeat;
        padding-right: 30px;
     }

    .fixed-table-container thead th .desc {
        background-image: url("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABMAAAATCAYAAAByUDbMAAAAZUlEQVQ4y2NgGAWjYBSggaqGu5FA/BOIv2PBIPFEUgxjB+IdQPwfC94HxLykus4GiD+hGfQOiB3J8SojEE9EM2wuSJzcsFMG4ttQgx4DsRalkZENxL+AuJQaMcsGxBOAmGvopk8AVz1sLZgg0bsAAAAASUVORK5CYII= ");
        cursor: pointer;
        background-position: right;
        background-repeat: no-repeat;
        padding-right: 30px;
    }
	.pull-right.pagination-detail{
display:none;
}
.scrollbar table{
		min-width:500px;
	}
	</style>
	
	
</head>

<body class="gray-bg">
	<nav class="navigation" >
		<span class="title" >标准管理</span>
		<span class="title" >-</span>
		<span class="titlethis" >标准信息管理</span>
	</nav>

    <div class="wrapper wrapper-content animated fadeInRight">
		 <!-- Panel Other -->
			
		<div class="ibox-content">
			<div class="row row-lg">
			
				<div class="col-sm-3 ">
<!-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>树形>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>					 -->
				    <div class="ibox float-e-margins">
				        <div class="ibox-content">
				        	<span onclick="gettablebytree(-1)" class="list-group-item node-treeview look-all" style="margin-left:-25px" >
				        		<span class="icon glyphicon"></span>
				        		<i class="icon node-icon fa fa-users"></i>查看所有
				        	</span>
							<div id="treeview" class="test"></div>
				        </div>
				    </div>
<!--              树形结构引入js脚本        	 -->
<script src="${pageContext.request.contextPath}/jsp/manageIntegral/js/plugins/treeview/bootstrap-treeview.min.js"></script>	

<script type="text/javascript">
	
	$.getJSON("${pageContext.request.contextPath}/stainfo/stainfotree.action"+"?rundd="+new Date().getTime(),function(datas){
		 $('#treeview').treeview({
			 color: "#5F6368",
			 expandIcon: 'glyphicon glyphicon-chevron-right',
			 collapseIcon: 'glyphicon glyphicon-chevron-down',
			 nodeIcon: 'fa fa-users',
		     data:datas,        //"${pageContext.request.contextPath}/show/tree.action",
			 onNodeSelected: function (event, node) {
// 			   alert('您单击了 text=' + node.text +',id=' +node.id);

			   gettablebytree(node.sclassid);
			}
		});
		//折叠整个树
		$('#treeview').treeview('expandAll',{ levels: 2, silent: true});
	});
	
	//自动刷新树
	function reftree(){
		$('#treeview').treeview('remove');
		$.getJSON("${pageContext.request.contextPath}/stainfo/stainfotree.action"+"?rundd="+new Date().getTime(),function(datas){
			 $('#treeview').treeview({
				 color: "#5F6368",
				 expandIcon: 'glyphicon glyphicon-chevron-right',
				 collapseIcon: 'glyphicon glyphicon-chevron-down',
				 nodeIcon: 'fa fa-users',
			     data:datas,        //"${pageContext.request.contextPath}/show/tree.action",
				 onNodeSelected: function (event, node) {
	// 			   alert('您单击了 text=' + node.text +',id=' +node.id);
	
				   gettablebytree(node.sclassid);
				}
			});
			//折叠整个树
			$('#treeview').treeview('expandAll',{ levels: 2, silent: true});
		});
	}
	
	var lit=$("<li class='list-group-item node-treeview' data-nodeid='-1' onclick='gettablebytree(-1)' style='color:undefined;background-color:undefined;'><span class='icon expand-icon glyphicon glyphicon-chevron-right'></span><span class='icon node-icon fa fa-users'></span>查看所有</li>");
	$(".list-group").append(lit);
	var $tablebytreeid=-1;
	function gettablebytree(id){
		$tablebytreeid=id;
		var search= {
			    silent: true,
			    query:{
			        type:1,
			        level:2
			    },
			}
		//gridReload();	
		$table.bootstrapTable('refresh', search);
	}
	
	
</script>
<!--              上为树形结构引入js脚本        	 -->	
<!-- >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>树形结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>					 -->			
				</div>	
				<div class="col-sm-9">
					<!-- Example Pagination -->
					<div class="example-wrap">
							<div class="form-inline">
								 <div class="form-group">
									 <label  class="date-label"></label>
									 <input type="text" class="form-control" id="filename" name="tablesearch" placeholder="文件名称">
								 </div>
								 <div class="form-group">
									 <label  class="date-label"></label>
									 <input type="text" class="form-control" id="createuser" name="tablesearch" placeholder="上传人">
								 </div>
								 <div class="form-group ">
									 <a id="search_button" href="javascript:;" class="btn btn-primary queryButton btn-sm form-control-btn"><i class="fa fa-search"></i>查 询</a>
								 </div>
							</div>	
						<div class="example">
							 
							 <div class="btn-groups hidden-xs" id="exampleToolbar" role="group">
								<c:if test="${button.add!=null}">
									<div id="add" class="btn-group">
										<button onclick="addthis()" class="btn btn-primary btn-sm">
											<i class="glyphicon glyphicon-plus" aria-hidden="true"></i>&nbsp;新增
										</button>
									</div>
									<script type="text/javascript">
										function addthis(){
											var id=$tablebytreeid;
											if(id==""||id==-1){
												//alert("请选择父级部门");return;
												id = "";
											}
											PopupModalView(id,'refresh','标准管理>>标准信息管理','800px','500px','${pageContext.request.contextPath}/stainfo/stainfoadd.action');
										}
									</script>
								</c:if>
								<c:if test="${button.delete!=null}">
									<div id="delete" class="btn-group">
										<button type="button" onclick="deletetable()" class="btn btn-warning btn-sm">
											<i class="glyphicon glyphicon-trash" aria-hidden="true"></i>&nbsp;删除
										</button>
									</div>
								</c:if>
							</div>
							<div class="scrollbar" >
								<table id="exampleTablePagination" >
								</table>
							</div>	
						</div>
					
					</div>
					<!-- End Example Pagination -->
				</div>
				
		  
		  
		  
			</div>
		</div>
	
		
		<!-- End Panel Other -->
		
    </div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jsp/manageIntegral/js/index.js"></script>
    <script type="text/javascript">
    	/**
    	*时间控件
    	*/
    	setgetdate("dayb");
	    setgetdate("daye");
	    
		/**
		 * 回调函数
		 * @param {Object} usetype
		 * @param {Object} data
		 */
		function dofunction(usetype,data){
			if(usetype=="refresh"){
				reftree();
				$table.bootstrapTable('selectPage', 1);
				$table.bootstrapTable('refresh');
			}
		}
		/**
		*编辑表格数据
		*/
		function updateGl(index){
			var _url="${pageContext.request.contextPath}/stainfo/stainfoload.action?tablejson="+index;
			PopupModalView(null,'refresh','标准管理>>标准信息管理','800px','500px',_url);
		}
		/**
		*删除数据
		*/
		function deletetable(){
			var rows = $table.bootstrapTable('getSelections');
			if (rows.length > 0) {
				// var IDs = rows[0].id;
				//alert(JSON.stringify(rows));
				 var list=new Array();
				 for(row in rows){
				 	list[row]=rows[row].standardid;
				 }
				 var durl="${pageContext.request.contextPath}/stainfo/stainfodelete.action";
				 var ajax=doajax(durl,list);
				 if(ajax){
				 	reftree();
				 	$table.bootstrapTable('selectPage', 1);
					$table.bootstrapTable('refresh');
				 }
			}else{
				alert("未选中数据");
			}
		}
		/**
		*查看
		*/
		function infotable(index){
// 			var row1=$table.bootstrapTable('getRowByUniqueId', index);
// 			alert(JSON.stringify(row1));
			var _url="${pageContext.request.contextPath}/stainfo/stainfoload.action?tablejson="+index;
			PopupModalView(null,'refresh','标准管理>>标准信息管理','800px','300px',_url);
		}
	/**
	表格加载
	*/
			
	var $table;
	$(function() {
		 $("#exampleTablePagination").bootstrapTable('destroy');
		 $table= $('#exampleTablePagination').bootstrapTable({
		    url: "${pageContext.request.contextPath}/stainfo/stainfolist.action",
			uniqueId: "standardid",          //每一行的唯一标识，一般为主键列
			cache: false,           //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			striped: true,          //是否显示行间隔色
			sortable: true, //是否启用排序
			sort:"standardid",
            sortOrder: "asc",
			sidePagination: "server",    //分页方式：client客户端分页，server服务端分页（*）
			pagination:true,
			mobileresponsive:true,
			queryParamsType: 'limit',
			pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
		    iconSize: 'outline',
		    showColumns: true,
			showRefresh: true,
			//refresh:{pageNumber:1},
			paginationDetailHAlign: 'left',//指定 分页详细信息 在水平方向的位置。'left' 或 'right'。
			showToggle: true,
			showColumns: true,
			pageSize: 30, 
			pageList: [10, 20, 30, 50, 100],        //可供选择的每页的行数（*）
			toolbar: '#exampleToolbar', //使用工具条
		    icons: {
		      refresh: 'glyphicon-repeat',
		      toggle: 'glyphicon-list-alt',
		      columns: 'glyphicon-list'
		    },
		    //行点击  响应事件
// 		    onClickRow:function (row,$element) {
//                 $('.info').removeClass('info');
//                 $($element).addClass('info');
//                 alert("当前点击行"+JSON.stringify(row));
//             },
		  //得到查询的参数
          queryParams : function (params) {
//                     //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                     var temp = {   
                         rows: params.limit,                         //页面大小
                         page: (params.offset / params.limit) + 1,   //页码
                         sort: params.sort,      //排序列名  
                         sortOrder: params.order, //排位命令（desc，asc）
                         actjson:$tablebytreeid,	//查询参数	
                         searchjson:JSON.stringify(gridReloadjson())	//查询参数		
                          
                     };
					// alert(params.limit);
					// alert(JSON.stringify(temp)+"$tablebytreeid"+$tablebytreeid);
                     return temp;
          },
			columns:[{
				checkbox: true,  
				visible: true                  //是否显示复选框  
			}, {
				field: 'standardid',
				title: 'standardid',
				hidden: true
			}, {
				field: 'rowid', //后台未传输当前对象的值
				title: '操作',
				width: 100,
				visible:true,
				align: 'center',
				formatter:rowbutton
				//hidden: true
			},{
				field: 'files',
				title: '文件',
				sortable: true,
				width: 200,
				align: 'center',
				formatter : files
			
			}, {
				field: 'filename',
				title: '文件名称',
				sortable: true,
				width: 200,
				align: 'center'
			
			}, {
				field: 'sclassname',
				title: '分类名称',
				sortable: true,
				width: 100,
				align: 'center'
				
			}, {
				field: 'filepath',
				title: '文件路径',
				sortable: true,
				width: 500,
				align: 'center'
				
			}, {
				field: 'memo',
				title: '备注',
				sortable: true,
				width: 200,
				align: 'center'
				
			}, {
				field: 'createuser',
				title: '上传人',
				sortable: true,
				width: 100,
				align: 'center'				
			}, {
				field: 'createtime',
				title: '上传时间',
				sortable: true,
				width: 200,
				align: 'center'
				
			}]
			 
		  });
		  // 设置隐藏列
		  $table.bootstrapTable('hideColumn', 'standardid');
		  function files(value,row,index) {
			  var ids = row.ids;
			  var files = row.files;
			  var str = "";
			  var aids = ids.split(",");
			  var filenames = files.split(",");
			  for(var i=0; i<aids.length; i++) {
				  if(str != "") {
					  str += "&nbsp;&nbsp;";
				  }
				  str += "<span><a style='display: inline-block;' href='../events/downLoad.action?id="+aids[i]+"'>"+filenames[i]+"</a></span>";
			  }
			  return str;
		  }
		  //下拉级联
		  /**
		  *可以设置按钮
		  *formatter:peronsex
		  */
		  function rowbutton(value,row,index){
		  	var bt1 = "";
			if("${button.load}"&&"${button.load}"!=null&&"${button.load}"!=""){
				bt1 += "<button type='button' onclick='updateGl("+row.standardid+")' class='btn btn-info btn-sm'><i class='fa fa-search'></i>&nbsp;查看</button>";
			}
			if(bt1 == "") {
				bt1 = "----";
			}
			return bt1;
		  }
		  
//获取选中行数据
	$("#getrowdate").click(function(){
		var rows = $table.bootstrapTable('getSelections');
			if (rows.length > 0) {
				// var IDs = rows[0].id;
				alert(JSON.stringify(rows));
			}else{
				alert("未选中数据");
			}
	});
//插入行数据
	$("#insertrowdate").click(function(){
		var row={
        "id": 0,
        "name": "插入数据1",
        "price": "&yen;0",
        "column1": "c10",
        "column2": "c20",
        "column3": "c30",
        "column4": "c40"
    };
		$table.bootstrapTable('insertRow', {index: 0, row: row});
	});
//获取所有数据
	$("#getallrowdate").click(function(){
		var date=$table.bootstrapTable('getData');
		alert(JSON.stringify(date));
	});
//删除选中的数据
	$("#deleterowdate").click(function(){
		var ids = $.map($table.bootstrapTable('getSelections'), function (row) {
		  return row.price //price
		})
		if(ids.length==0){
			alert("请选择删除行！！");
			return;
		}
		$table.bootstrapTable('remove', {
		  field: 'price',  //price
		  values: ids
		})
		// $table.bootstrapTable('removeAll'); //清空列表
	});
//得到第一行数据
	$("#getrow1date").click(function(){
		var row1=$table.bootstrapTable('getRowByUniqueId', 1);
		alert(JSON.stringify(row1));
	});
//加载数据
	$("#loaddate").click(function(){
		var data=[{
		    "id": 0,
		    "name": "插入数据1",
		    "price": "&yen;0",
		    "column1": "c10",
		    "column2": "c20",
		    "column3": "c30",
		    "column4": "c40"
		},{
		    "id": 1,
		    "name": "插入数据2",
		    "price": "&yen;0",
		    "column1": "c10",
		    "column2": "c20",
		    "column3": "c30",
		    "column4": "c40"
		}];
		$table.bootstrapTable('load', data);
	});
//数据追加  $table.bootstrapTable('append', data).
//数据前加  $table.bootstrapTable('prepend', data)
//修改某行数据  $table.bootstrapTable('updateRow', {index: 1, row: row})
//列表刷新 $table.bootstrapTable('refresh')
	
//展示某列 $table.bootstrapTable('showColumn', 'name')
//隐藏某列 $table.bootstrapTable('hideColumn', 'name')	


// $table.bootstrapTable('scrollTo', 0)  滚动到第一行
// $table.bootstrapTable('scrollTo', number) 滚动到第number 行
// $table.bootstrapTable('scrollTo', 'bottom')	滚动到底部展示

// $table.bootstrapTable('selectPage', 1)  翻页到第一页
// $table.bootstrapTable('prevPage')	翻页到第一页
// $table.bootstrapTable('nextPage')	翻页到最后一页


 // $table.bootstrapTable('toggleView') 竖视图


/**
 * 表格搜索 表格查询  $table
 */
$("#search_button").click(function(){
	gridReload();
})
/**
 * 表格搜索 表格查询 回车事件 $table
 */
var $tablesearch=document.getElementsByName("tablesearch");
$.each($tablesearch,function(index,optionval){      
		// alert($(this).val());
	$(this).keydown(function(event){
		if(event.which=="13")gridReload();
	})			 
}); 
/**
 * 表格搜索 表格查询  $table
 */ 
function gridReload(){

	var search= {
	  //  url: "http://127.0.0.1:8080/mes/tablegetJsonstable.action",
	    silent: true,
	    query:{
	        type:1,
	        level:2
	    },
	//	actjson:obj
	}
	$table.bootstrapTable('selectPage', 1);
	$table.bootstrapTable('refresh', search);
}
function gridReloadjson(){
	var obj=new Object();
	var $tablesearch=document.getElementsByName("tablesearch");
	for(var i=0;i<$tablesearch.length;i++){
	  var st=$tablesearch[i];
	  obj[st.id]=st.value;
	}
	return obj;
}

});
   </script>
   

 
</body>
</html>
