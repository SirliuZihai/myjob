<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"><html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
	<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
	<META HTTP-EQUIV="Expires" CONTENT="0"> 
	<title>用户信息</title>
	<#include "../common/common.ftl">
	
</head>
<body>
<!-- 搜索模块 -->
	<div style="border:1px solid #D8D8D8;padding: 10px 0px 10px 0px;">
		<form id="serachFrom" method="post">
			<table cellpadding="5" border='0' style="width: 100%;">
	    		<tr>
	    			<td style="width:90px;text-align: right;padding-right: 20px;" >
	    				<label>姓名:</label>
	    			</td>
	    			<td >
	    				<input name="userinfo.name" class="easyui-textbox" data-options="prompt: '姓名'">
	    			</td>
	    			<td style="width:90px;text-align: right;padding-right: 20px;" >
	    				<label>性别:</label>
	    			</td>
	    			<td >
	    				<input name="userinfo.sex" id="sex" class="easyui-textbox" data-options="prompt: '性别'">
	    			</td>
	    			<td style="width:90px;text-align: right;padding-right: 20px;" >
	    				<label>地区:</label>
	    			</td>
	    			<td >
	    				<input  name="userinfo.area" id="area" class="easyui-textbox" data-options="prompt: '地区'">
	    			</td>
	    			<td style="width:90px;text-align: right;padding-right: 20px;" >
	    				<label>积分:</label>
	    			</td>
	    			<td >
	    				<input name="account.credit"  class="easyui-textbox" data-options="prompt: '积分'">
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
	<div style="border:1px solid #D8D8D8;padding: 10px 0px 10px 0px;">
	  <table id="dg" style="width:100%;height:383px;">
		    <thead>
	    		<tr>
	    			<th halign="center" align="center" field="ck" checkbox=true></th>
	    			<th halign="center" align="center" field="username" >用户名</th>
	    			 <th halign="center" align="center"  field="userinfo.name" formatter="fmtld" >姓名</th>
	    			 <th halign="center" align="center"  field="userinfo.sex" formatter="fmtld" >性别</th>
	    			 <th halign="center" align="center" field="userinfo.areainfo.areaname" formatter="fmtld" >地区</th>
	    			 <th halign="center" align="center" field="userinfo.address" formatter="fmtld">地址</th>
	    			<th halign="center" align="center" field="phone" >手机号</th>
	    			<th halign="center" align="center" field="email" >邮箱</th>
	    			<th halign="center" align="center" field="account.money" formatter="fmtld" >余额</th>
	    			<th halign="center" align="center" field="account.credit" formatter="fmtld" >积分</th> 
	    			 <!-- formatter="fmtDateTime"<th halign="center" align="center" field="used" width="40" formatter="formatState">任务状态</th> -->
	    			<th halign="center" align="center" field="account.modifydate" formatter="fmtDateTime" >最后交易时间</th>
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
var TreeNode;
$(function(){
	//以后封装
	$('#dg').datagrid({    
		loadMsg:'加载数据中...',
	    pagination:true,
	    singleSelect:true,
	    rownumbers:true,
	    fitColumns:true,
	    striped:true
	});
	$('#dg').datagrid({
		url:'list.do'
	});
	
	//$.get("areaAll2.do",function(data,status){
	 // TreeNode=data;
	//  });
	
	$('#area').combotree({
		url:"areaAll2.do" 
	  // data: eval("TreeNode="+TreeNode)

	}); 
	$("#sex").combobox({
    	editable:false,
        valueField:'value',
		textField:'label',
		data: [{
			label: '男',
			value: '男'
		},{
			label: '女',
			value: '女'
		}]
    });
	
	
});

function getName(row){
	return row!=null?row[userinfo][name]:"";
}
function getAddress(value){
	return value!=null?value.address:"";
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

</script>
    </body>
</html>