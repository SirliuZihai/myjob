<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
	<META HTTP-EQUIV="Expires" CONTENT="0"> 
	<title>交易测试</title>
	<#include "../common/common.ftl">
	
</head>
<body>
	<div>
		<form id="transform" method="post">
			<input class="easyui-textbox" name="userFrom" data-options="prompt:'付方'">
			<input class="easyui-textbox" name="userTo" data-options="prompt:'收方'">
			<input class="easyui-textbox" name="amt" data-options="prompt:'金额'">
			<input class="easyui-textbox" name="timesleep" data-options="prompt:'线程延迟时间'">
		</form>
		<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="submit()">提交</a>
    	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	</div>
	
	
<script type="text/javascript">
$(function(){
	
});
function submit(){
	$('#transform').form('submit',{
		url:"trans.do",
		success: function(result){
			alert(result);
		}
	});
}
function loadDate(){
	//$.get("demo_test.asp",function(data,status){
    //alert("Data: " + data + "\nStatus: " + status);
 // });
}

</script>
    </body>
</html>