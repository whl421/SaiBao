var $parentNode = window.parent.document;

function $childNode(name) {
    return window.frames[name]
}

// tooltips
$('.tooltip-demo').tooltip({
    selector: "[data-toggle=tooltip]",
    container: "body"
});

// 使用animation.css修改Bootstrap Modal
$('.modal').appendTo("body");

$("[data-toggle=popover]").popover();

//折叠ibox
$('.collapse-link').click(function () {
    var ibox = $(this).closest('div.ibox');
    var button = $(this).find('i');
    var content = ibox.find('div.ibox-content');
    content.slideToggle(200);
    button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
    ibox.toggleClass('').toggleClass('border-bottom');
    setTimeout(function () {
        ibox.resize();
        ibox.find('[id^=map-]').resize();
    }, 50);
});

//关闭ibox
$('.close-link').click(function () {
    var content = $(this).closest('div.ibox');
    content.remove();
});

//判断当前页面是否在iframe中
// if (top == this) {
//     var gohome = '<div class="gohome"><a class="animated bounceInUp" href="index.html?v=4.0" title="返回首页"><i class="fa fa-home"></i></a></div>';
//     $('body').append(gohome);
// }

//animation.css
function animationHover(element, animation) {
    element = $(element);
    element.hover(
        function () {
            element.addClass('animated ' + animation);
        },
        function () {
            //动画完成之前移除class
            window.setTimeout(function () {
                element.removeClass('animated ' + animation);
            }, 2000);
        });
}

//拖动面板
function WinMove() {
    var element = "[class*=col]";
    var handle = ".ibox-title";
    var connect = "[class*=col]";
    $(element).sortable({
            handle: handle,
            connectWith: connect,
            tolerance: 'pointer',
            forcePlaceholderSize: true,
            opacity: 0.8,
        })
        .disableSelection();
};


var $table;
(function() {
		 $("#exampleTablePagination").bootstrapTable('destroy');
		 $table= $('#exampleTablePagination').bootstrapTable({
		    url: "http://127.0.0.1:8088/RecoveryEPA/user/testtable.action",
		    height: "450",			 //行高，如果没有设置height属性，
			uniqueId: "id",          //每一行的唯一标识，一般为主键列
			cache: false,           //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			striped: true,          //是否显示行间隔色
			sortable: true,                     //是否启用排序
            sortOrder: "asc",
			sidePagination: "server",    //分页方式：client客户端分页，server服务端分页（*）
			pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
		    iconSize: 'outline',
		    showColumns: true,
			showRefresh: true,
			//refresh:{pageNumber:1},
			paginationDetailHAlign: 'left',//指定 分页详细信息 在水平方向的位置。'left' 或 'right'。
			showToggle: true,
			showColumns: true,
			pageList: [5, 10, 25],        //可供选择的每页的行数（*）
			toolbar: '#exampleToolbar', //使用工具条
		    icons: {
		      refresh: 'glyphicon-repeat',
		      toggle: 'glyphicon-list-alt',
		      columns: 'glyphicon-list'
		    },
		  //得到查询的参数
          queryParams : function (params) {
//                     //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                     var temp = {   
                         rows: params.limit,                         //页面大小
                         page: (params.offset / params.limit) + 1,   //页码
                         sort: params.sort,      //排序列名  
                         sortOrder: params.order, //排位命令（desc，asc）
						 actjson:JSON.stringify(gridReloadjson())	//查询参数			 
                     };
					 
					// alert(params.limit);
					// alert(JSON.stringify(gridReloadjson()));
                     return temp;
          },
			columns:[{
				checkbox: true,  
				visible: true                  //是否显示复选框  
			}, {
				field: 'id',
				title: 'id',
				sortable: true,
				visible:false
			}, {
				field: 'name',
				title: '姓名',
				sortable: true,
				width: 320,
				align: 'center'
			
			}, {
				field: 'workcode',
				title: '工号',
				sortable: true,
				width: 120,
				align: 'center'
				
			}, {
				field: 'accountcode',
				title: '账号',
				sortable: false,
				width: 120,
				align: 'center',
				valign: 'middle'
				//formatter: emailFormatter
			}, {
				field: 'sex',
				title: '性别',
				width: 120,
				align: 'center',
				valign: 'middle'
				//formatter: linkFormatter
			}, {
				field: 'birthday',
				title: '生日',
				width: 120,
				align: 'center',
				valign: 'middle'
			}, {
				field: 'phonenum',
				title: '手机号码' ,
				width: 120,
				align: 'center',
				valign: 'middle'
			}, {
				field: 'dateentry',
				title: '入职日期',
				width: 120,
				align: 'center',
				valign: 'middle'
			}, {
				field: 'companycode',
				title: '企业账号',
				width: 120,
				align: 'center',
				valign: 'middle' 
			}, {
				field: 'departmentname',
				title: '部门',
				width: 120,
				align: 'center',
				valign: 'middle'
			}, {
				field: 'ranking',
				title: '排名',
				width: 120,
				align: 'center',
				valign: 'middle'
			}, {
				field: 'basescore',
				title: '基础分',
				width: 120,
				align: 'center',
				valign: 'middle'
			}, {
				field: 'workagescore',
				title: '工龄分',
				width: 120,
				align: 'center',
				valign: 'middle'
			}],
			// ,{
			// 	field:'ID',
			// 	title: '操作',
			// 	width: 120,
			// 	align: 'center',
			// 	valign: 'middle',
			// 	formatter: actionFormatter
			// },
			//]
		  });
		  $table.bootstrapTable('refresh',{pageNumber:1});
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

var json = '[' +
        '{' +
        '"text": "父节点 1",' +
		'"id": "1",'+
        '"nodes": [' +
        '{' +
        '"text": "子节点 1",' +
		'"id": "2",'+
        '"nodes": [' +
        '{' +
        '"text": "孙子节点 1",' +
		'"id": "3"'+
        '},' +
        '{' +
        '"text": "孙子节点 2",' +
		'"id": "4"'+
        '}' +
        ']' +
        '},' +
        '{' +
        '"text": "子节点 2",' +
		'"id": "5"'+
        '}' +
        ']' +
        '},' +
        '{' +
        '"text": "父节点 2",' +
		'"id": "1"'+
        '},' +
        '{' +
        '"text": "父节点 3",' +
		'"id": "1"'+
        '},' +
        '{' +
        '"text": "父节点 4",' +
		'"id": "1"'+
        '},' +
        '{' +
        '"text": "父节点 5",' +
		'"id": "1"'+
        '}' +
        ']';
$('#treeview').treeview({
		 color: "#5F6368",
		 expandIcon: 'glyphicon glyphicon-chevron-right',
		 collapseIcon: 'glyphicon glyphicon-chevron-down',
		 nodeIcon: 'fa fa-users',
		// data: defaultData
	    data: json,
		onNodeSelected: function (event, node) {
		   alert('您单击了 text=' + node.text +',id=' +node.id);
		}
});

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
	  //  url: "http://127.0.0.1:8088/mes/tablegetJsonstable.action",
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

})();

