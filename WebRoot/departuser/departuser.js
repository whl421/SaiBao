function getdepartuser(divid,iframeid,departuser_url){
	return new _getdepartuser(divid,iframeid,departuser_url);
}

var _$index,_userid,_ModalHtml,_userdate,_departdate,_$searchbtn,_$departid;
var _getdepartuser=function(divid,iframeid,departuser_url) {
//        var _date=new Object();
   var _btn=document.createElement("button");
   _btn.setAttribute("class", "form-control-button");  //departuser
   _btn.setAttribute("id", divid+"departuser");
  // var _node=document.getElementById(divid);
   var _node=document.getElementById(iframeid).contentWindow.document.getElementById(divid);
// 	   alert(_node.style.lineHeight);
   _node.style.display="none";
   if(!document.getElementById(iframeid).contentWindow.document.getElementById(divid+"departuser"))_node.parentNode.insertBefore(_btn,_node);
   _btn.style.height=_node.style.height+"px";
   var _user,_depart;
   var ik=0;
  //${pageContext.request.contextPath}/departuserutil/user 
   var _url="${pageContext.request.contextPath}/login/depttree.action?rundd="+new Date().getTime();
   
   if(departuser_url)_url=departuser_url;
   
   $.getJSON(_url,function(departuserdate){ 
		_user=departuserdate["user"];
		_depart=departuserdate["depart"];
		_userdate=_user;
   		_departdate=_depart;
	//	alert(JSON.stringify(_userdate));
   });
//   function reload(_reload){
//	    _depart=null;_user=null;
//	    $.getJSON(_reload,function(departuserdate){ 
//	   _user=departuserdate["user"];
//	   _depart=departuserdate["depart"];
//	   _userdate=_user;
//	      _departdate=_depart;
//	    });
//	}
   document.getElementById(iframeid).contentWindow.document.getElementById(divid+"departuser").onclick=function(){
   		  _userdate=_user;
   		  _departdate=_depart;
   		  _$departid=-1;
   		   _$index=-1;
	   	  _$searchbtn=0; //所有查询
	   	  init(divid,_user,iframeid);
	   	 
   }	
};	
   function init(divid,_user,iframeid){
	   _userid=new Date().getTime();
	   _ModalHtml='<div class="modal fade" id="myModal'+_userid+'" tabindex="-1" style="z-index:999919891014 !important;" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'+
						'<div class="modal-dialog modal-lg">'+
							'<div id="modal-content'+_userid+'" class="modal-content">'+
								'<div class="modal-header" >'+
									'<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>'+
									'<h4 class="modal-title" id="myModalLabel'+_userid+'">'+
									   ' 请选择人员>'+
									'</h4>'+
								'</div>'+
								'<div class="modal-body">'+
									'<div class="row"  >'+
										'<div class="col-sm-2"></div>'+
										'<div class="col-sm-6 form-group form-inline">'+
											'<label  class="date-label col-sm-2" style="padding: 6px 12px;font-size: 14px;line-height: 1.42857143;text-align:right;vertical-align:middle;">查询:</label>'+
											'<div class="col-sm-6" style="text-align:left;vertical-align:middle;">'+
											 '<input class="form-control" id="searchdiv'+_userid+'"   />'+
											'</div>'+
											'<div class="btn-group col-sm-4">'+
												'<button id="_searchbtn'+_userid+'0" class="btn btn-info btn-sm active" type="button" onClick="_searchbtn$(this,1,'+_userid+')" > 所有人员查询 </button>'+
												'<button id="_searchbtn'+_userid+'1" class="btn btn-info btn-sm active" type="button" style="display:none;" onClick="_searchbtn$(this,0,'+_userid+')" > 选中部门下查询 </button>'+
											 '</div>'+		
										'</div>'+
										'<div class="col-sm-3"></div>'+
									'</div>'+
									'<table class="departtable " cellspacing="0" cellpadding="0" >'+
										'<tr width="100%">'+
										'<td width="33.3%"  >'+
											'<div id="treediv'+_userid+'" style="width:100%;height:440px;overflow-y:hidden;" >'+
												'<div id="departtree'+_userid+'" style="float:left;width:100%;margin:-3px;font-size:12px;"></div>'+
												'<span id="departtreespan'+_userid+'" class="zdc"></span>'+
											'</div>'+	
										'</td>'+
										'<td width="33.3%" style="border-left: 1px double #B6B4B6;" >'+
											'<div id="userdiv'+_userid+'" style="width:100%;height:440px;">'+
												'<div id="usertitle'+_userid+'" style="float:left;width:100%;height:30px;padding-left:15px;border-bottom:1px dashed #B6B4B6;">'+
												'</div>'+
												'<div style="clear: both;"></div>'+
												'<div id="usercheck'+_userid+'" class="usercheck" style="float:left;width:100%;overflow-y:hidden;"  >'+
												'<span id="usercheckspan'+_userid+'" class="zdc" style="display:none" ></span>'+
												'</div>'+
											'</div>'+
										'</td>'+
										'<td width="33.3%" style="border-left: 1px double #B6B4B6;" >'+
											'<div id="getuserdiv'+_userid+'" style="width:100%;height:440px;">'+
												'<div id="getusertitle'+_userid+'" style="float:left;width:100%;height:30px;padding-left:15px;border-bottom:1px dashed  #B6B4B6;">'+
													'<span id="departname" style="float:left;width:80%;font-size:15px;font-weight:500;margin-top:5px;">已人员选择</span>'+
													'<button id="removeuser'+_userid+'" onClick="departuserremoveall();" class="btn btn-info btn-sm" style="float:left;padding: 1px 5px;margin-top:5px;margin-left:1%;">清空</button>'+
												'</div>'+
												'<div style="clear: both;"></div>'+
												'<div id="getusercheck'+_userid+'" class="usercheck" style="float:left;width:100%;overflow-y:hidden;"  >'+
												'</div>'+
											'</div>'+
										'</td>'+
										 '</tr>'+
									'</table>'+
								'</div>'+
								'<div class="modal-footer">'+
									'<button type="button" class="btn btn-default" data-dismiss="modal">'+
										'关闭'+
									'</button>'+
									'<button type="button" id="hasdepartuer'+_userid+'" class="btn btn-primary" data-dismiss="modal" >'+
										'完成'+
									'</button>'+	
								'</div>'+
							'</div>'+ 
						'</div>'+ 
					'</div> ';
			 $("body").append(_ModalHtml);		
			 $('#myModal'+_userid).modal({
					   		   backdrop: 'static',
					   		   show:true});
			var $modal_dialog = $('.modal-content'+_userid);
			var m_top = ( $(window).height() - 500 )/2;  
			 $modal_dialog.css({'margin-top': m_top + 'px',
				 				'z-index':'999919891014'});
			wiatdepart();
			
			_inituser();
			
			 $('#treediv'+_userid).slimScroll({
			   height: '440px',
			   railOpacity: 0.4,
			   wheelStep: 10
			});
			$('#getuserdiv'+_userid).slimScroll({
			   height: '440px',
			   railOpacity: 0.4,
			   wheelStep: 10
			});
			$('#usercheck'+_userid).slimScroll({
			   height: '410px',
					  width:'100%',
			   railOpacity: 0.4,
			   wheelStep: 10,
					  top:"30px",
					  start: 'top'
			});
			$("#getusercheck"+_userid).slimScroll({
			   height: '410px',
					  width:'100%',
			   railOpacity: 0.4,
			   wheelStep: 10,
					  top:"30px",
					  start: 'top'
			});
			$("#searchdiv"+_userid).bind("input propertychange", function () {
		        var _val = $("#searchdiv"+_userid).val(); 
		         _initsearch(_$departid);
		    });
			
		document.getElementById("hasdepartuer"+_userid).onclick=function(){

			var usernames="",ids="";
			for(var _i=0;_i<_userdate.length;_i++){
			 var userlist=_userdate[_i]["userlist"];
			 var groudname="";
			 if(usernames=="")
			 		groudname="["+_userdate[_i]["groupname"]+":";
			 else
			   		groudname=",["+_userdate[_i]["groupname"]+":";
			 	ik=0;
			 	for(var _u=0;_u<userlist.length;_u++){
			 	    
			 		if(userlist[_u]["check"]=="true"){
			 			var userid=userlist[_u]["id"];
			 			var username=userlist[_u]["name"];
						if(ik==0){
							groudname+=username;
							ids+=userid;
						}else{	
							groudname+=","+username;
							ids+=","+userid;
						}	
						ik++;
			 		}
			 	}
			 	if(ik>0){
			 		usernames+=(groudname+="]");
			 	}
			 	ik=0;
			}
			_user=_userdate;
			_depart=_departdate;
			document.getElementById(iframeid).contentWindow.document.getElementById(divid).value=ids;
			document.getElementById(iframeid).contentWindow.document.getElementById(divid+"departuser").innerHTML=usernames;
			document.getElementById(iframeid).contentWindow.document.getElementById(divid).setAttribute("name-date", usernames);
//			$("#"+divid).html(ids);
//			$("#"+divid+"departuser").html(usernames);
//			$("#"+divid).attr("name-date",usernames);
			var _opt=document.getElementById("myModal"+_userid);
			_opt.parentNode.removeChild(_opt);
		}
   }
 //_$searchbtn  0 所有查询   1 对应部门查询  
 function _searchbtn$(obj,id,userid){
		$("#_searchbtn"+userid+"0").css("display","none");
		$("#_searchbtn"+userid+"1").css("display","none");
		_$searchbtn=id;
		$("#_searchbtn"+userid+id).css("display","inline-block");
// 			$(obj).css("display","inline-block");
		_initsearch(_$departid);
} 
  
function _initsearch(departid){
	var search_text=document.getElementById("searchdiv"+_userid).value;
	if(search_text&&search_text!=""){
		if(_$searchbtn==0){
				onloadAllsearchcheck(search_text);
		}else if(_$searchbtn==1&&departid!=-1){
				onloadsearchcheck(departid,search_text); 
		}
	}else if(departid!=-1){
		onloadcheck(departid);
	}
}
function _inituser(){
		if(_userdate){
			$("#getusercheck"+_userid).html("");
			var _userhtml="";
			for(var _i=0;_i<_userdate.length;_i++){
			 var groupid=_userdate[_i]["groupid"];
			 var groupname=_userdate[_i]["groupname"];
			 var userlist=_userdate[_i]["userlist"];
			 	for(var _u=0;_u<userlist.length;_u++){
			 		if(userlist[_u]["check"]=="true"){
			 			var userid=userlist[_u]["id"];
			 			var username=userlist[_u]["name"];
			 		    _userhtml+='<p id="p'+groupid+"_"+userid+'"><span id="departname" style="float:left;width:80%;font-size:15px;font-weight:500;margin-top:5px;">'+username+'-'+groupname+'</span>'
							+'<span onClick="departuserremove(this)" id="'+groupid+"_"+userid+'" data-in="'+_u+'" data-groupname="'+groupname+'" data-index="'+_i+'" data-userid="'+userid+'" data-groupid="'+groupid+'" data-username="'+username+'" style="float:left;width:15px;height:15px;margin-top:5px;margin-left:0;" ><i class="glyphicon glyphicon-trash"></i> </span>';

			 		}
			 	}
			}
			 $("#getusercheck"+_userid).html(_userhtml);
		}
 }  
 function wiatdepart(){
	  if(_departdate){
	  	$('#departtree'+_userid).treeview({
			data: _departdate,
			onNodeSelected: function(event, node) {
				$("#usercheckspan"+_userid).css("display","inline"); 
				_$searchbtn=1;
				$("#_searchbtn"+_userid+"0").css("display","none");
				$("#_searchbtn"+_userid+"1").css("display","inline-block");
// 					onloadcheck(node["departid"]);
				_$departid=node["departid"];
				
				$("#usercheckspan"+_userid).css("display","block");
				_initsearch(node["departid"]);
			}
	  	 });
	  	var _span=document.getElementById("departtreespan"+_userid);
	  	_span.parentNode.removeChild(_span);
	  }else{
		  setTimeout(function(){
				wiatdepart();
		  },1000);
	  }
  } 
   function onloadAllsearchcheck(search_text){
   		var _$searcharray=new Array();
			for(var index=0;index<_userdate.length;index++){
				  var data=_userdate[index];
				  var _userdates=data["userlist"];
				  var groupname=data["groupname"];
				  var groupid=data["groupid"];
				  for(var check=0;check<_userdates.length;check++){
				  	   var _name=_userdates[check]["name"];
					   var _id=_userdates[check]["id"];
					   var _check=_userdates[check]["check"];
					   var _obj=new Object();
					   _obj["id"]=_id;
					   _obj["name"]=_name;
					   _obj["check"]=_check;
					   _obj["groupname"]=groupname;
					   _obj["groupid"]=groupid;
					   _obj["search"]=_name+groupname;
					   _obj["gindex"]=index;
					   _obj["in"]=check;
					   _$searcharray.push(_obj);
				  }		
			}
// 				var userdate=_$searcharray.filter(array => array.search.match(search_text)); 
			
			var userdate=FitterArray(_$searcharray,search_text,"search");
			
			if(userdate.length==0){
			  	$("#usercheck"+_userid).html("未找到匹配结果");
			  	$("#usertitle"+_userid).html("");
			  	$("#usercheckspan"+_userid).css("display","none");
			  		return; 
			}
			
			$("#usercheck"+_userid).html("");
			ik=0; 
		    for(var check=0;check<userdate.length;check++){
			   var _name=userdate[check]["name"];
			   var _id=userdate[check]["id"];
			   var _check=userdate[check]["check"];
			   var _groupname=userdate[check]["groupname"];
			   var groupid=userdate[check]["groupid"];
			   var _index=userdate[check]["gindex"];
			   var _in=userdate[check]["in"];
			    if(_check&&(_check=="true"||_check==true)){
						var $htmlcheck='<p><span id="departnamespan'+_id+'"  onClick="$check(\''+groupid+'\','+_id+');"  style="float:left;width:25%;font-size:15px;font-weight:500;margin-top:5px;">'+_name+'</span>'+
						'<span id="departnamespan'+_id+'"  onClick="$check(\''+groupid+'\',\'check_'+_id+'\');"  style="float:left;width:55%;font-size:15px;font-weight:500;margin-top:5px;">--'+_groupname+'</span>'+
						'<input index="'+_index+'" type="checkbox" onClick="$check('+groupid+','+_id+');" style="float:left;width:15px;height:15px;margin-top:5px;margin-left:0;" id="'+_id+'" name="'+_in+'" checked="checked" /> </p>';
				   		$("#usercheck"+_userid).append($htmlcheck);
				   }else{
					   ik++;
						var $htmlcheck='<p><span id="departnamespan'+_id+'"  onClick="$check(\''+groupid+'\','+_id+');"  style="float:left;width:25%;font-size:15px;font-weight:500;margin-top:5px;">'+_name+'</span>'+
						'<span id="departnamespan'+_id+'"  onClick="$check(\''+groupid+'\','+_id+');"  style="float:left;width:55%;font-size:15px;font-weight:500;margin-top:5px;">--'+_groupname+'</span>'+
						'<input index="'+_index+'" type="checkbox" onClick="$check('+groupid+','+_id+');" style="float:left;width:15px;height:15px;margin-top:5px;margin-left:0;" id="'+_id+'" name="'+_in+'" /> </p>';
				   		$("#usercheck"+_userid).append($htmlcheck);
				   }
		    }
			   if(ik==0){
				   var _title='<span id="departname" style="float:left;width:89%;font-size:15px;font-weight:500;margin-top:5px;">全部选择</span>'+
				   '<input onClick="$checkall(this)" type="checkbox" id="inputcheckall" check="checked" style="float:left;width:15px;height:15px;margin-top:5px;margin-left:1%;" checked="checked"  />';
				   $("#usertitle"+_userid).html(_title);
			   }else{
				   var _title='<span id="departname" style="float:left;width:89%;font-size:15px;font-weight:500;margin-top:5px;">全部选择</span>'+
				   '<input onClick="$checkall(this)" type="checkbox" id="inputcheckall" style="float:left;width:15px;height:15px;margin-top:5px;margin-left:1%;" />';
				   $("#usertitle"+_userid).html(_title);
			   }
			   ik=0;
				$('#usercheck'+_userid).slimScroll({
				  top:"30px",
				  start: 'top'
			   });
			 
			 $("#usercheckspan"+_userid).css("display","none"); 
   }
   function onloadsearchcheck(departid,search_text){
   		var userdates,groupname,groupid;
		  for(var index=0;index<_userdate.length;index++){
			  var data=_userdate[index];
			  if(data["groupid"]==departid){
			  		_$index=index;
					userdates=data["userlist"];
					groupname=data["groupname"];
					groupid=data["groupid"];
					break;
			  }
		  }
		  if(!userdates){
			  	$("#usercheck"+_userid).html("未找到匹配结果");
			  	$("#usertitle"+_userid).html("");
			  	$("#usercheckspan"+_userid).css("display","none");
			  		return; 
		  }
		  var _$searcharray=new Array();
		  for(var check=0;check<userdates.length;check++){
		  		   var _name=userdates[check]["name"];
				   var _id=userdates[check]["id"];
				   var _check=userdates[check]["check"];
				   var _obj=new Object();
				   _obj["id"]=_id;
				   _obj["name"]=_name;
				   _obj["check"]=_check;
				   _obj["groupname"]=groupname;
				   _obj["groupid"]=groupid;
				   _obj["search"]=_name+groupname;
				   _obj["gindex"]=_$index;
				   _obj["in"]=check;
				   _$searcharray.push(_obj);
		  }	   
// 			  var userdate=_$searcharray.filter(array => array.name.match(search_text)); 
		  
		  var userdate=FitterArray(_$searcharray,search_text,"name");
		  
		  if(userdate.length==0){
		  	$("#usercheck"+_userid).html("未找到匹配结果");
		  	$("#usertitle"+_userid).html("");
		  	$("#usercheckspan"+_userid).css("display","none");
		  	return; 
		  }
		  
		  
		 $("#usercheck"+_userid).html("");
		   ik=0; 
		   for(var check=0;check<userdate.length;check++){
			   var _name=userdate[check]["name"];
			   var _id=userdate[check]["id"];
			   var _check=userdate[check]["check"];
			   var _in=userdate[check]["in"];
			    if(_check&&(_check=="true"||_check==true)){
						var $htmlcheck='<p><span id="departnamespan'+_id+'"  onClick="$check(\''+groupid+'\','+_id+');"  style="float:left;width:80%;font-size:15px;font-weight:500;margin-top:5px;">'+_name+'</span>'+
						'<input index="'+_$index+'" type="checkbox" onClick="$check('+groupid+','+_id+');" style="float:left;width:15px;height:15px;margin-top:5px;margin-left:0;" id="'+_id+'" name="'+_in+'" checked="checked" /> </p>';
				   		$("#usercheck"+_userid).append($htmlcheck);
				   }else{
					   ik++;
						var $htmlcheck='<p><span id="departnamespan'+_id+'"  onClick="$check(\''+groupid+'\','+_id+');"  style="float:left;width:80%;font-size:15px;font-weight:500;margin-top:5px;">'+_name+'</span>'+
						'<input index="'+_$index+'" type="checkbox" onClick="$check('+groupid+','+_id+');" style="float:left;width:15px;height:15px;margin-top:5px;margin-left:0;" id="'+_id+'" name="'+_in+'" /> </p>';
				   		$("#usercheck"+_userid).append($htmlcheck);
				   }
		   }
		   if(ik==0){
			   var _title='<span id="departname" style="float:left;width:89%;font-size:15px;font-weight:500;margin-top:5px;">'+groupname+'</span>'+
			   '<input type="checkbox" id="inputcheckall" check="checked" onClick="$checkall(this)" style="float:left;width:15px;height:15px;margin-top:5px;margin-left:1%;" name="'+groupid+'" checked="checked"  />';
			   $("#usertitle"+_userid).html(_title);
		   }else{
			   var _title='<span id="departname" style="float:left;width:89%;font-size:15px;font-weight:500;margin-top:5px;">'+groupname+'</span>'+
			   '<input type="checkbox" id="inputcheckall" onClick="$checkall(this)" style="float:left;width:15px;height:15px;margin-top:5px;margin-left:1%;" name="'+groupid+'" />';
			   $("#usertitle"+_userid).html(_title);
		   }
		   ik=0;
			$('#usercheck'+_userid).slimScroll({
			  top:"30px",
			  start: 'top'
		   });
		 
		 $("#usercheckspan"+_userid).css("display","none"); 
   }	
   function onloadcheck(departid){
	  var userdate,groupname,groupid;
	  for(var index=0;index<_userdate.length;index++){
		  var data=_userdate[index];
		  if(data["groupid"]==departid){
		  		_$index=index;
				userdate=data["userlist"];
				groupname=data["groupname"];
				groupid=data["groupid"];
				break;
		  }
	  }
	  if(!userdate){
		  	$("#usercheck"+_userid).html("未找到匹配结果");
		  	$("#usertitle"+_userid).html("");
		  	$("#usercheckspan"+_userid).css("display","none");
		  		return; 
	  }
	 $("#usercheck"+_userid).html("");
// 		 var $htmlcheck='';
	   ik=0; 
	   for(var check=0;check<userdate.length;check++){
		   var _name=userdate[check]["name"];
		   var _id=userdate[check]["id"];
		   var _check=userdate[check]["check"];
		   if(_check&&(_check=="true"||_check==true)){
				var $htmlcheck='<p><span id="departnamespan'+_id+'"  onClick="$check(\''+groupid+'\','+_id+');"  style="float:left;width:80%;font-size:15px;font-weight:500;margin-top:5px;">'+_name+'</span>'+
				'<input index="'+_$index+'" type="checkbox" onClick="$check('+groupid+','+_id+');" style="float:left;width:15px;height:15px;margin-top:5px;margin-left:0;" id="'+_id+'" name="'+check+'" checked="checked" /> </p>';
		   		$("#usercheck"+_userid).append($htmlcheck);
		   }else{
			   ik++;
				var $htmlcheck='<p><span id="departnamespan'+_id+'"  onClick="$check(\''+groupid+'\','+_id+');"  style="float:left;width:80%;font-size:15px;font-weight:500;margin-top:5px;">'+_name+'</span>'+
				'<input index="'+_$index+'" type="checkbox" onClick="$check('+groupid+','+_id+');" style="float:left;width:15px;height:15px;margin-top:5px;margin-left:0;" id="'+_id+'" name="'+check+'" /> </p>';
		   		$("#usercheck"+_userid).append($htmlcheck);
		   }
	   }
	   if(ik==0){
		   var _title='<span id="departname" style="float:left;width:89%;font-size:15px;font-weight:500;margin-top:5px;">'+groupname+'</span>'+
		   '<input id="inputcheckall" onClick="$checkall(this)" type="checkbox" check="checked" style="float:left;width:15px;height:15px;margin-top:5px;margin-left:1%;" name="'+groupid+'" checked="checked"  />';
		   $("#usertitle"+_userid).html(_title);
	   }else{
		   var _title='<span id="departname" style="float:left;width:89%;font-size:15px;font-weight:500;margin-top:5px;">'+groupname+'</span>'+
		   '<input id="inputcheckall" onClick="$checkall(this)" type="checkbox" style="float:left;width:15px;height:15px;margin-top:5px;margin-left:1%;" name="'+groupid+'" />';
		   $("#usertitle"+_userid).html(_title);
	   }
	   ik=0;
		$('#usercheck'+_userid).slimScroll({
		  top:"30px",
		  start: 'top'
	   });
// 		   $("#usercheck"+_userid).html($htmlcheck);
	 
	  $("#usercheckspan"+_userid).css("display","none"); 
  }
   
 
   
   function $checkall(obj){
	  var _checked=obj["checked"];
	   if(_checked){
		    var listcheck=document.getElementById("usercheck"+_userid).getElementsByTagName("input");
		   for(var index=0;index<listcheck.length;index++){
				listcheck[index].checked="checked";
				if(listcheck[index]&&listcheck[index].checked&&listcheck[index].tagName=="INPUT"){
					var _in=listcheck[index]["name"];
					var _index=listcheck[index].getAttribute("index");
					_userdate[_index]["userlist"][_in]["check"]="true";
				}	
		   } 
	   }else{
		    var listcheck=document.getElementById("usercheck"+_userid).getElementsByTagName("input");
		   for(var index=0;index<listcheck.length;index++){
				listcheck[index].checked=false;
				if(listcheck[index]&&listcheck[index].tagName=="INPUT"&&listcheck[index].checked==false){
					var _in=listcheck[index]["name"];
					var _index=listcheck[index].getAttribute("index");
					_userdate[_index]["userlist"][_in]["check"]="false";
				}
		   }
	   }
	   _inituser();
   }
   function $check(groupid,userid){
		var obj;
		var _listcheck=document.getElementById("usercheck"+_userid).getElementsByTagName("input");
// 			alert(_listcheck.length);
		
		for(var i=0;i<_listcheck.length;i++){
			if(_listcheck[i]&&_listcheck[i].tagName=="INPUT"){ 
				if(_listcheck[i]["id"]==userid||_listcheck[i].getAttribute("id")==userid){   
					obj=_listcheck[i];
					break;
				}
			}
		}
		
		var _index=obj.getAttribute("index");

		if(obj["checked"]){
			for(var gid=0;gid<_userdate.length;gid++){
				if(_userdate[gid]["groupid"]==groupid){
					var _userlist=_userdate[gid]["userlist"];
					for(var uindex=0;uindex<_userlist.length;uindex++){
						if(_userlist[uindex]["id"]==userid){
							_userdate[gid]["userlist"][uindex]["check"]="true";
							break;
						}
					}
					break;
				}				
			}
			var listcheck=document.getElementById("usercheck"+_userid).getElementsByTagName("input");
			ik=0;
			for(var index=0;index<listcheck.length;index++){
				if(listcheck[index]&&listcheck[index].checked==false){  //&&listcheck[index]!=obj
					ik++;
				} 
			}
			if(ik==0){
				document.getElementById("inputcheckall").checked=true;
			}
			ik=0;
		} else{
// 				_userdate[_$index]["userlist"][_in]["check"]="false";
			for(var gid=0;gid<_userdate.length;gid++){
				if(_userdate[gid]["groupid"]==groupid){
					var _userlist=_userdate[gid]["userlist"];
					for(var uindex=0;uindex<_userlist.length;uindex++){
						if(_userlist[uindex]["id"]==userid){
							_userdate[gid]["userlist"][uindex]["check"]="false";
							break;
						}
					}
					break;
				}				
			}
			document.getElementById("inputcheckall").checked=false;
		}
		_inituser();
  }
  function departuserremoveall(){
	  var listcheck=document.getElementById("usercheck"+_userid).getElementsByTagName("input");
	  if(document.getElementById("inputcheckall"))document.getElementById("inputcheckall").checked=false;
	  for(var index=0;index<listcheck.length;index++){
			if(listcheck[index]&&listcheck[index].checked)listcheck[index].checked=false;	
	  }
	  if(_userdate){
			for(var _i=0;_i<_userdate.length;_i++){
			 var userlist=_userdate[_i]["userlist"];
			 	for(var _u in userlist){
			 		 userlist[_u]["check"]="false";
			 	}
			}
	  }
	  _inituser();
  }
  function departuserremove(obj){
	  var id=obj.getAttribute("id");
	  var _in=obj.getAttribute("data-in");
	  var index=obj.getAttribute("data-index");
	  var userid=obj.getAttribute("data-userid");
	  var groupid=obj.getAttribute("data-groupid");
// 		  var _p=document.getElementById("p"+id);
// 		 _p.parentNode.removeChild(_p);
	  _userdate[index]["userlist"][_in]["check"]="false";
	  var listcheck=document.getElementById("usercheck"+_userid).getElementsByTagName("input");
	  for(var index=0;index<listcheck.length;index++){
	  	var _id=listcheck[index]["id"];
		if(_id==userid){
			listcheck[index].checked=false;
			if(document.getElementById("inputcheckall")&&document.getElementById("inputcheckall").checked)
				document.getElementById("inputcheckall").checked=false;
		}	
	  }
	  _inituser();
  }
  
  function FitterArray(array,search_text,match_name){
  		var ary=new Array();
  		for(var i=0;i<array.length;i++){
  			var index=array[i];
  			if(index[match_name].match(search_text)){
  				ary.push(index);
  			}
  		}
  		return ary;
  }
	   