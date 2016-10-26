

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
