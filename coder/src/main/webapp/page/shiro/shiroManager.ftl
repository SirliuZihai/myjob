<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
<META HTTP-EQUIV="Expires" CONTENT="0"> 
<title>权限控制</title>
<#include "../common/common.ftl">
</head>
	<body>
		<a id="box" href="#" class="easyui-linkbutton" iconCls="icon-cancel">提交</a>
	</body>
	
<script type="text/javascript">
$(function(){
	$('#box').click(function(){
		$.get('test2.do',function(data){
			alert(data);
		});
	});
});
</script>
</html>