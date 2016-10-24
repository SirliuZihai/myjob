<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
	<META HTTP-EQUIV="Expires" CONTENT="0"> 
	<title>用户信息</title>
	<script type="text/javascript" src="../js/lib/jquery.min.js"></script>
	<script type="text/javascript" src="../js/lib/jquery.easyui.min.js"></script>
</head>
<body>
	<div style="border:1px solid #D8D8D8;padding: 10px 0px 10px 0px;">
	  <table id="dg" style="width:100%;height:383px;">
		    <thead>
	    		<tr>
	    			<th halign="center" align="center" field="ck" checkbox=true></th>
	    			<th halign="center" align="center" field="uername"  sortable="true">用户名</th>
	    			<!-- <th halign="center" align="center" field="userinfo.name" width="40"  sortable="true">姓名</th> -->
	    			<!-- <th halign="center" align="center" field="userinfo.address" width="40"  sortable="true">地址</th> -->
	    			<th halign="center" align="center" field="phone"  sortable="true">手机号</th>
	    			<th halign="center" align="center" field="email" >邮箱</th>
	    			<!-- <th halign="center" align="center" field="account.money" width="40" >余额</th> -->
	    			<!-- <th halign="center" align="center" field="used" width="40" formatter="formatState">任务状态</th> -->
	    			<th halign="center" align="center" field="makedatetime"  >注册时间</th>
	    		</tr> 
	    	</thead>
	    </table>
    </div>
   

     <div id="toolbar">
    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">增加</a>
    	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editWork()">编辑</a>
    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="deleteWork()">删除</a>
    </div>
    <script type="text/javascript">
    $(function(){
    	//以后封装
    	$('#dg').datagrid({    
    		loadMsg:'加载数据中...',
    	    pagination:true,
    	    singleSelect:true,
    	    rownumbers:true,
    	    fitColumns:true,
    	    striped:true,
    	  
    	   
    	});
    	
    	$('#dg').datagrid({
    		url:'list.do',
    	});
    	
    });
    </script>
    </body>
</html>