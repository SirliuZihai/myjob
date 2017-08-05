

//easyUI formatter函数
fmtld = function(value,row){return fmtBaseFld(value,row,this.field);}
fmtMonth = function(value, row) {return dateFormat(fmtBaseFld(value, row,this.field), 'yyyy-MM');}
fmtDate = function(value, row) {return dateFormat(fmtBaseFld(value, row,this.field), 'yyyy-MM-dd');}
fmtDateTime = function(value, row) {return dateFormat(fmtBaseFld(value, row,this.field), 'yyyy-MM-dd HH:mm:ss');}
//easyUI formatter函数
function fmtBaseFld(value,row,fieldName) {
	var eachObj = row; // 返回str
	var tableFields = fieldName.split("."); // 解析成数组
	for(var i = 0; i < tableFields.length; i++) {
		eachObj = eval("eachObj."+tableFields[i]); // 预处理该对象
		if(eachObj == null || "undefined" == typeof eachObj) { // 对象不存在直接返回
			return "";
		}
	}
	return eachObj;
}
//格式日期函数
dateFormat = function (dateStr, format) {
	var MONTH_NAMES=new Array('January','February','March','April','May','June','July','August','September','October','November','December','Jan','Feb','Mar','Apr','May','Jun','Jul','Aug','Sep','Oct','Nov','Dec');
	var DAY_NAMES=new Array('Sunday','Monday','Tuesday','Wednesday','Thursday','Friday','Saturday','Sun','Mon','Tue','Wed','Thu','Fri','Sat');
	function LZ(x) {return(x<0||x>9?"":"0")+x}

	//校验年月日格式2013-11-28
	var reg = new RegExp(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
	//校验年月日时分秒格式2013-11-28 12:12:00
	 var regr = new RegExp(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/);
	 
	if (null == dateStr || 0 == dateStr.length) {
		return "";
	} 
	
	var date = new Date(dateStr);
	var y=date.getYear()+"";
	if(y=="NaN"){
		var dr = dateStr.match(reg);
		var dtr = dateStr.match(regr);
		if(dtr || dr){
			dateStr = dateStr.replace(/-/g,"/");
			 date = new Date(dateStr);
			 y=date.getYear()+"";
		}
	}
	format=format+"";
	var result="";
	var i_format=0;
	var c="";
	var token="";
	
	var M=date.getMonth()+1;
	var d=date.getDate();
	var E=date.getDay();
	var H=date.getHours();
	var m=date.getMinutes();
	var s=date.getSeconds();
	var yyyy,yy,MMM,MM,dd,hh,h,mm,ss,ampm,HH,H,KK,K,kk,k;
	var value=new Object();
	if (y.length < 4) {y=""+(y-0+1900);}
	value["y"]=""+y;
	value["yyyy"]=y;
	value["yy"]=y.substring(2,4);
	value["M"]=M;
	value["MM"]=LZ(M);
	value["MMM"]=MONTH_NAMES[M-1];
	value["NNN"]=MONTH_NAMES[M+11];
	value["d"]=d;
	value["dd"]=LZ(d);
	value["E"]=DAY_NAMES[E+7];
	value["EE"]=DAY_NAMES[E];
	value["H"]=H;
	value["HH"]=LZ(H);
	if (H==0){value["h"]=12;}
	else if (H>12){value["h"]=H-12;}
	else {value["h"]=H;}
	value["hh"]=LZ(value["h"]);
	if (H>11){value["K"]=H-12;} else {value["K"]=H;}
	value["k"]=H+1;
	value["KK"]=LZ(value["K"]);
	value["kk"]=LZ(value["k"]);
	if (H > 11) { value["a"]="PM"; }
	else { value["a"]="AM"; }
	value["m"]=m;
	value["mm"]=LZ(m);
	value["s"]=s;
	value["ss"]=LZ(s);
	while (i_format < format.length) {
		c=format.charAt(i_format);
		token="";
		while ((format.charAt(i_format)==c) && (i_format < format.length)) {
			token += format.charAt(i_format++);
			}
		if (value[token] != null) { result=result + value[token]; }
		else { result=result + token; }
		}
	return result;
}
$.fn.datebox.defaults.formatter = function(date){
	if(date==null)return "";
 	var y = date.getFullYear();
 	var m = date.getMonth()+1;
 	var d = date.getDate();
 	return y+'/'+m+'/'+d;
 }
 $.fn.datebox.defaults.parser=function(s){
	 if(s=="1970/1/1")return null;
 	  var t = Date.parse(s);  
 	  if (!isNaN(t)){  
 	   return new Date(t);  
 	  } else {  
 	   return new Date();  
 	  }  
 }
 function easyui_disabled(obj ,bool,type){
		if(type=="datebox"){
			var oldvalue = obj.datebox('getValue');
	 		obj.datebox({disabled:bool});
	 		if(bool==false){obj.datebox('setValue',oldvalue)}else{obj.datebox('clear')};
		}
		if(type=="combobox"){
			var oldvalue = obj.combobox('getValues');
	 		obj.combobox({disabled:bool});
	 		if(bool==false){obj.combobox('setValues',oldvalue)}else{obj.combobox('clear')}; //默认值丢失
		}
		if(type=="textbox"){
			obj.textbox({disabled:bool}); //默认值不丢失
			if(bool==true)obj.textbox('clear');
		}
	}
 
 function easyui_required(obj ,bool,type){
		if(type=="datebox"){
			var oldvalue = obj.datebox('getValue');
	 		obj.datebox({required:bool});
	 		obj.datebox('setValue',oldvalue);
		}
		if(type=="combobox"){
			var oldvalue = obj.combobox('getValues');
	 		obj.combobox({required:bool});
	 		obj.combobox('setValues',oldvalue); //默认值丢失
		}
		if(type=="textbox"){
			obj.textbox({required:bool}); //默认值不丢失
		}
	}
 function easyui_load(id,params){
		var temp;
		$('#'+id).find('input').each(function(){
				var obj = $(this);
		   		var name =obj.attr('textboxname');
		   		if(name){
		   			try{
		    		temp = eval('params.'+name);
		    		}catch(e){
						return ;
					}
		    		if(obj.attr('class').indexOf('easyui-datebox')!=-1){temp = formate_date_2(temp); obj.datebox('setValue',temp);return;}
					if(obj.attr('class').indexOf('easyui-combobox')!=-1){obj.combobox('setValue',temp);return ;}
		    		obj.textbox('setValue',temp);	    
		   		}
			
	   	
	   	});
	}
 function formate_date_2(value){
	 if(value == null)return null;
	 	var date = new Date(value);
	 	var y = date.getFullYear();
	 	var m = date.getMonth()+1;
	 	var d = date.getDate();
	 	return y+'/'+m+'/'+d;	
	 }
//Easyui Form增加myLoad方法,使其支持二级数据对象(验证失败)
$.extend($.fn.form.methods, {
	myLoad : function (jq, param) {
	return jq.each(function () {
	load(this, param);
	});

	function load(target, param) {
	if (!$.data(target, "form")) {
	$.data(target, "form", {
	options : $.extend({}, $.fn.form.defaults)
	});
	}
	var options = $.data(target, "form").options;
	if (typeof param == "string") {
	var params = {};
	if (options.onBeforeLoad.call(target, params) == false) {
	return;
	}
	$.ajax({
	url : param,
	data : params,
	dataType : "json",
	success : function (rsp) {
	loadData(rsp);
	},
	error : function () {
	options.onLoadError.apply(target, arguments);
	}
	});
	} else {
	loadData(param);
	}
	function loadData(dd) {
	var form = $(target);
	var formFields = form.find("input[name],select[name],textarea[name]");
	formFields.each(function(){
	var name = this.name;
	var value = jQuery.proxy(function(){try{return eval('this.'+name);}catch(e){return "";}},dd)();
	var rr = setNormalVal(name,value);
	if (!rr.length) {
	var f = form.find("input[numberboxName=\"" + name + "\"]");
	if (f.length) {
	f.numberbox("setValue", value);
	} else {
	$("input[name=\"" + name + "\"]", form).val(value);
	$("textarea[name=\"" + name + "\"]", form).val(value);
	$("select[name=\"" + name + "\"]", form).val(value);
	}
	}
	setPlugsVal(name,value);
	});
	options.onLoadSuccess.call(target, dd);
	$(target).form("validate");
	};
	function setNormalVal(key, val) {
	var rr = $(target).find("input[name=\"" + key + "\"][type=radio], input[name=\"" + key + "\"][type=checkbox]");
	rr._propAttr("checked", false);
	rr.each(function () {
	var f = $(this);
	if (f.val() == String(val) || $.inArray(f.val(), val) >= 0) {
	f._propAttr("checked", true);
	}
	});
	return rr;
	};
	function setPlugsVal(key, val) {
	var form = $(target);
	var cc = ["combobox", "combotree", "combogrid", "datetimebox", "datebox", "combo"];
	var c = form.find("[comboName=\"" + key + "\"]");
	if (c.length) {
	for (var i = 0; i < cc.length; i++) {
	var combo = cc[i];
	if (c.hasClass(combo + "-f")) {
	if (c[combo]("options").multiple) {
	c[combo]("setValues", val);
	} else {
	c[combo]("setValue", val);
	}
	return;
	}
	}
	}
	};
	};
	}
	});

String.prototype.startWith=function(str){     
	var reg=new RegExp("^"+str);     
	return reg.test(this);       
}  
/**
 * 需要重写dosuccess方法.
 * */
function submit(id,url){
	var elemarr = new Array();
 	var elemarr_name = new Array();
	$('#'+id).form('submit',{
		queryParams:{},
		onSubmit: function(){
			var b = $('#'+id).form('validate');
 			if(b==true){
 				$(this).find("input").each(function(){
 	 				var elem = $(this); 
 	 				var elem_name = elem.attr("name");
 	 				if(elem_name!=undefined &&(elem_name.length!=0&&elem_name!="file")){//重写的逻辑 
 	 					if(elem.val()==""||elem_name.startWith('dis_')){ 
 	 						elemarr_name.push(elem.attr("name"));
 	 						elem.removeAttr("name");
 	 						elemarr.push(elem);
 	 						
 	 					}
 	 				}
 	 			});
 			}
 			return b;
		},
		url:url,
		success:  function(result){
 			//console.log(elemarr_name);
 			//恢复过滤 recover name
 			 for(var i=0;i<elemarr.length;i++){
 				elemarr[i].attr("name",elemarr_name[i]);
 			}; 
 			var result = eval('('+result+')');
 		   		 if(result.success){
 		   			dosuccess();	
 				 }
 		   		$.messager.show({
						title:'提示',
						msg:result.message,
						showType:'show'
					});
 		}
	})
}
function dosuccess(){
		$(document).scrollTop(0);	
}
/**
 * 本地分页datagrid
 * data:数据array
 * dg_id 目标ID
 * */
function datagrid_local(data,dg_id){
	var pager = $('#'+dg_id).datagrid("getPager"); 
	var start_1 = (pager.pagination('options').pageNumber - 1) * pager.pagination('options').pageSize; 
    var end_1 = start + pager.pagination('options').pageSize; 
	$('#'+dg_id).datagrid('loadData',data.slice(start_1,end_1));
	pager.pagination({ 
	        total:send_data.length, 
	        onSelectPage:function (pageNo, pageSize) {
	          var start = (pageNo - 1) * pageSize; 
	          var end = start + pageSize; 
	          $('#'+dg_id).datagrid("loadData",data.slice(start, end)); 
	            pager.pagination('refresh', { 
	            total:data.length, 
	            pageNumber:pageNo 
	          }); 
	        } 
	      });
}