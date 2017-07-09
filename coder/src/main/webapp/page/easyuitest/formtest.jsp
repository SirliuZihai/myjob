<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<jsp:include page="${contextPath}/page/common/common.html"></jsp:include>
</head>
<body>
<form id="fm" method="post" enctype="multipart/form-data"  style="text-align:left">
 	<input  name="users[1].auth" class="easyui-combobox" data-options="valueField: 'VALUE',textField: 'LABEL',data:statu_arr4" />
 	<input class="easyui-textbox" name="users[0].username">
	<input class="easyui-datebox" name="users[0].makedatetime">
 	<input class="easyui-textbox" name="users[1].username"> 
 	<input class="easyui-datebox" name="users[1].makedatetime">
	<input   name="files" class="easyui-filebox" data-options="buttonText:'上传文件',prompt:'支持上传小于10M的rar或pdf文件'"/>
	<input  name="files" class="easyui-filebox" data-options="buttonText:'上传文件',prompt:'支持上传小于10M的rar或pdf文件'"/>
 	<input class="easyui-textbox"  name="users[1].account.credit">
 	<input class="easyui-textbox" name="oneleveltest" >
 	<a href="#" onclick="submit('fm','getForm.do')">提交</a>
 <!-- 	<a href="#" onclick="inform()">加载数据</a> -->
</form>
data==${users.users[0].username}
<c:set var="colect" value="fws" />
datasize==${fn:length(users.users) }
<table>

<c:forEach var="item" items="${users.users}" varStatus="status">   
      <tr  <c:if test="${status.count%2==0}">bgcolor="#CCCCFE"</c:if> align="left">  
         <td>${status.count}</td> <td> ${item.username}</td>
       </tr>   
</c:forEach>

<c:forEach var="index" begin="1" end="9" step="3">
	<tr><td>${index }</td></tr>
</c:forEach>
</table>
<script type="text/javascript">
var statu_arr4 = [{'LABEL': '是','VALUE': '1'},{'LABEL': '否','VALUE': '0'}];
var data = ${userjson};
$(function(){
	//init
})

$.parser.onComplete = function(){
	console.log("data==="+data)
	easyui_load('fm',data);
};


function formate_date_2(value){
 	var date = new Date(value);
 	var y = date.getFullYear();
 	var m = date.getMonth()+1;
 	var d = date.getDate();
 	return y+'/'+m+'/'+d;	
 }
 /**********util调用的******/
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
 	 				if(elem_name!=undefined &&(elem_name.length!=0&&elem_name!="files"||elem_name.startWith('dis_'))){//重写的逻辑 
 	 					if(elem.val()==""){ 
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
 /******************/
</script>
</body>
</html>