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
<!-- 搜索模块 -->
<div id="userlist" >
	<div style="border:1px solid #D8D8D8;padding: 10px 0px 10px 0px;">
		<form id="serachFrom" method="post">
			<table cellpadding="5" border='0' style="width: 100%;">
	    		<tr>
	    			<td style="width:90px;text-align: right;padding-right: 20px;" >
	    				<label>用户名:</label>
	    			</td>
	    			<td >
	    				<input  name="username" class="easyui-textbox" data-options="prompt: '用户名'">
	    			</td>
	    			<td style="width:90px;text-align: right;padding-right: 20px;" >
	    				<label>手机号:</label>
	    			</td>
	    			<td >
	    				<input  name="phone" class="easyui-textbox" data-options="prompt: '手机号'">
	    			</td>
	    			<td style="width:90px;text-align: right;padding-right: 20px;" >
	    				<label>邮箱:</label>
	    			</td>
	    			<td >
	    				<input  name="email" class="easyui-textbox" data-options="prompt: '邮箱'">
	    			</td>
	    			<td style="width:90px;text-align: right;padding-right: 20px;" >
	    				<label>名字:</label>
	    			</td>
	    			<td >
	    				<input name="name"  class="asyui-textbox" data-options="prompt: '名字'">
	    			</td>
	    		</tr>
	    		<tr>
	    			<td colspan="6"></td>
	    			<td colspan="2" style="text-align: right; padding-right: 20px" >
	    				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="serach()"  data-options="iconCls:'icon-search'" style="width:80px;">搜索</a>
	    				<a href="javascript:void(0)" class="easyui-linkbutton" onclick="reset()" data-options="iconCls:'icon-reload'" style="width:80px;">重置</a>
	    			</td>
	    		</tr>
	    	</table>
	</form>	
</div>
<div class="easyui-panel" style="border: 0;width: 100%;">
	    <table id="dg" class="easyui-datagrid"
	    data-options="loadMsg:'加载数据中...',url:'list.do',pagination:true">
	     
<!--     启动多列排序		multiSort="true" -->
    	<thead>
    		<tr>
    			<th halign="center" align="center" field="ck" checkbox=true></th>
    			<th halign="center" align="center" field="uername" width=50"  sortable="true">用户名</th>
    			<th halign="center" align="center" field="userinfo.name" width="40"  sortable="true">姓名</th>
    			<th halign="center" align="center" field="userinfo.address" width="40"  sortable="true">地址</th>
    			<th halign="center" align="center" field="phone" width="40"  sortable="true">手机号</th>
    			<th halign="center" align="center" field="email" width="40" >邮箱</th>
    			<th halign="center" align="center" field="account.money" width="40" >余额</th>
    			<!-- <th halign="center" align="center" field="used" width="40" formatter="formatState">任务状态</th> -->
    			<th halign="center" align="center" field="makedatetime" width="40" >注册时间</th>
    		</tr> 
    	</thead>
    </table>
    </div>
    <div id="toolbar">
    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">增加</a>
    	<a href="#" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editWork()">编辑</a>
    	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="deleteWork()">删除</a>
    </div>
	<!--  新增弹框界面-->
<div id="dlg" class="easyui-dialog" style="width: 600px; padding: 10px 20px" align="center"  data-options="modal:true,closed:true,resizable:true,collapsible:true,minimizable:true,maximizable:true"  buttons="#dlg-buttons">
    	<form id="fm" method="post">
    		<table  cellpadding="5">
	    		<tr>
	    			<td style="text-align: right;"><label>用户名<font color="red">*</font>:</label></td>
	    			<td>
						<input class="easyui-textbox easyui-validatebox" id="uername2" name="uername" data-options="prompt:'任务唯一标志',required:true,missingMessage:'请填写用户名'">
					</td>
					<td style="text-align: right;"><label><font color="red">*</font>:</label></td>
	    			<td>
						 <input class="easyui-textbox" name="phone" class="easyui-validatebox"data-options="prompt:'任务命名空间',required:true,missingMessage:'请输入cron表达式开如:0 2 0/1 0 0 ?'">
					</td>
	    		</tr>
	    		<tr>
	    			<td style="text-align: right;"><label>任务类名<font color="red">*</font>:</label></td>
	    			<td>
						 <input class="easyui-textbox"  name="className" data-options="prompt:'任务类名，不加包！',required:true,missingMessage:'请添加邮箱'">
					</td>
					<td style="text-align: right;"><label>任务时间<font color="red">*</font>:</label></td>
	    			<td>
						 <input class="easyui-textbox"  name="cronExpression" data-options="prompt:'cron表达试',required:true,missingMessage:'请添加邮箱'">
					</td>
	    		</tr>
	    		<tr>
	    			<td style="text-align: right;"><label>邮箱<font color="red">*</font>:</label></td>
	    			<td>
						 <input class="easyui-textbox" id="email" name="email" data-options="prompt:'邮箱',required:true,missingMessage:'请添加邮箱'">
					</td>
					<td style="text-align: right;"><label>状态<font color="red">*</font>:</label></td>
	    			<td>
						 <input class="easyui-combobox"  id="used2" name="used" data-options="prompt:'状态',required:true,missingMessage:'请添加状态'">
					</td>
	    		</tr>
	    		 </table>
    		<input type='Hidden' name="springmvc.token" value="">
    	</form>
    
    <div id="dlg-buttons">
    	<a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveWork()">保存</a>
    	<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="cancel()">取消</a>
    </div>
   </div>
</div>
</body>
<script type="text/javascript">
$(function(){
	//$('dg').
	/* $("#used2").combobox({
    	editable:false,
        valueField:'value',
		textField:'label',
		data: [{
			label: '启用',
			value: '1'
		},{
			label: '停止',
			value: '0'
		}]
    }); */
	
});
//重置
function reset(){
	$('#serachFrom').form("clear");
}

function cancel(){
	$('#dlg').dialog('close');
}

function formatState(value,row,index){
	if(value=="1")
		return "启用";
	return "停止";
}

//搜索
function serach(){
	var params = {};
	$('#serachFrom').find('input').each(function(){
        var obj = $(this);
        var name = obj.attr('name');
        if(name){
            params[name] = obj.val();
        }
    });
	console.log(params);
	$('#dg').datagrid("load", params);
}
   
var url;
//新建
function newUser(){
	$('#dlg').dialog('open').dialog('setTitle','新增任务');
	//var token=$('#fm').find('input[name="springmvc.token"]').val();
	$('#fm').form('clear');
	  $('#uername2').textbox({readonly:false});
	//$('#fm').find('input[name="springmvc.token"]').val(token);
		url='./userinfo/add.do';
}
    
	
//编译
function editWork(){
    var rows = $('#dg').datagrid('getSelections');
    if(rows.length==0){$.messager.alert('消息提示','请选择要编辑的数据信息!','error');return ;}
    var row = $('#dg').datagrid('getSelected');
    console.log(row);
    if (row){
    	$('#dlg').dialog('open').dialog('setTitle','修改任务');
    	$('#fm').form('load',row);
    	url = './userlist/update.do';
    }
    $('#uername2').textbox({readonly:true});
}
    
    
    
//保存
function saveWork(){
   	$('#fm').form('submit',{
   		url: url,
   		onSubmit: function(){
   			var isValid = $(this).form('validate');
   			return isValid;
   		},
   		success: function(result){
   			if(result.success){
   				$('#dlg').dialog('close');		// close the dialog
   				$('#dg').datagrid('reload');	// reload the user data
   				jsutil.msg.alert("保存成功");
   			}else{
   				jsutil.msg.alert(result.errorMsg);
   			}
   		},
   		
   	});
}
//删除任务
function deleteWork(){
    var rows = $('#dg').datagrid('getSelections');
    if(rows.length==0){$.messager.alert('消息提示','请选择要编辑的数据信息!','error');return ;}
    var row = $('#dg').datagrid('getSelected');
    console.log(row);
    if (row){
    	$('#fm').form('load',row);
    	url = './userinfo/delete.do';
    }
	$('#fm').form('submit',{url:url,
		success: function(result){
			eval("result="+result);
			if(result.success){
				$('#dg').datagrid('reload');
				jsutil.msg.alert("删除成功");
			}else{
   				jsutil.msg.alert(result.errorMsg);
			}
		},
	});
}

     </script>
</html>