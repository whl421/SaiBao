var $table;
(function() {
		 $("#Integralaward").bootstrapTable('destroy');
		 $table= $('#Integralaward').bootstrapTable({
		    url: "../../js/demo/bootstrap_table_test.json",
		    height: "450",			 //行高，如果没有设置height属性，
			uniqueId: "id",          //每一行的唯一标识，一般为主键列
			cache: false,           //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			striped: true,          //是否显示行间隔色
		//	sidePagination: "server",    //分页方式：client客户端分页，server服务端分页（*）
			pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
		    iconSize: 'outline',
		    showColumns: true,
			showRefresh: true,
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
//          queryParams : function (params) {
//                     //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
//                     var temp = {   
//                         rows: params.limit,                         //页面大小
//                         page: (params.offset / params.limit) + 1,   //页码
//                         sort: params.sort,      //排序列名  
//                         sortOrder: params.order //排位命令（desc，asc） 
//                     };
//                     return temp;
//          },
			columns:[{
				checkbox: true,  
				visible: true                  //是否显示复选框  
			}, {
				field: 'id',
				title: 'id',
				sortable: false,
				visible:false
			}, {
				field: 'name',
				title: '奖扣时间',
				sortable: false,
				width: 320,
				align: 'center',
				valign: 'middle'
			}, {
				field: 'workcode',
				title: '记录时间',
				sortable: false,
				width: 120,
				align: 'center',
				valign: 'middle'
			}, {
				field: 'accountcode',
				title: '主题',
				sortable: false,
				width: 120,
				align: 'center',
				valign: 'middle'
				//formatter: emailFormatter
			}, {
				field: 'sex',
				title: 'A分',
				width: 120,
				align: 'center',
				valign: 'middle'
				//formatter: linkFormatter
			}, {
				field: 'birthday',
				title: 'B分',
				width: 120,
				align: 'center',
				valign: 'middle'
			}, {
				field: 'phonenum',
				title: '产值' ,
				width: 120,
				align: 'center',
				valign: 'middle'
			}, {
				field: 'dateentry',
				title: '人次',
				width: 120,
				align: 'center',
				valign: 'middle'
			}, {
				field: 'companycode',
				title: '记录人',
				width: 120,
				align: 'center',
				valign: 'middle' 
			}, {
				field: 'departmentname',
				title: '初审人',
				width: 120,
				align: 'center',
				valign: 'middle'
			}, {
				field: 'ranking',
				title: '终审人',
				width: 120,
				align: 'center',
				valign: 'middle'
			}, {
				field: 'basescore',
				title: '审核状态',
				width: 120,
				align: 'center',
				valign: 'middle'
			}, {
				field: 'workagescore',
				title: '操作',
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


})();