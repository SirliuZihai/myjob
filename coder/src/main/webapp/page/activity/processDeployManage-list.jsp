<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程部署管理</title>
<jsp:include page="${contextPath}/page/common/common.html"></jsp:include>
</head>
<body>

	<!-- 搜索模块 -->
	<div id="userlist2" class="easyui-panel" style="width: 100%;height:auto; padding: 8px 0 8px 0;overflow:hidden;">
			<form id="serachFrom" method="post">
				<table style="table-layout:fixed;overflow:hidden;border:0;width: 100%;">
					<tr>
						<td style="width: 8%;text-align:right;"><label>部署ID:</label></td>
						<td style="width: 13%;"><input id="id" name="id"
							class="easyui-textbox" data-options="prompt: '部署ID'"
							style="width: 100%;"></td>
						<td style="width: 8%;text-align:right;"><label>部署名称:</label></td>
						<td style="width: 13%;"><input id="name" name="name"
							class="easyui-textbox" data-options="prompt: '部署名称'"
							style="width: 100%;"></td>
						<td style="width: 50%;" colspan="4"></td>
					</tr>
				</table>
				
			</form>
	</div>
	<div class="easyui-panel" style="border:0;width:100%;">
		<table id="dg" class="easyui-datagrid"
		 	title=""
			style="width: 100%; height: auto;"
			url=${pageContext.request.contextPath}/activitiController/viewProcessDeploy.do
			toolbar="#toolbar" rownumbers="true" fitColumns="true" nowrap="false"
			singleSelect="false" pagination="true">
			<thead>
				<tr>
					<th field="ck" checkbox=true></th>
					<th field="id" width="16%" align="center" halign="center">部署ID</th>
					<th field="name" width="40%" align="left" halign="center">部署名称</th>
					<th field="time" width="40%" align="center" halign="center">部署时间</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="newDeploy()">新建部署</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" onclick="destroyDeploy()">删除部署</a>
			<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"
				onclick="viewDeploy()">查看部署</a> -->
		</div>
		<br>
		<table id="dg1" class="easyui-datagrid"
			title="流程定义查看"
			style="width: 100%; 
			height: auto;"
			url=${pageContext.request.contextPath}/activitiController/viewProdef.do
			rownumbers="true" 
			fitColumns="true"
			nowrap="false"
			pagination="true">
			<thead>
				<tr>
					<th field="deploymentId" width="11%" align="center" halign="center">部署ID</th>
					<th field="defineId" width="12%" align="center" halign="center">流程定义ID</th>
					<th field="name" width="12%" align="left" halign="center">流程定义名称</th>
					<th field="key" width="12%" align="center" halign="center">流程定义key</th>
					<th field="resourceName" width="15%" align="left" halign="center">流程定义资源名称</th>
					<th field="diagramResourceName" width="15%" align="left" halign="center" data-options="formatter:formatHref">流程定义资源图片名称</th>
					<th field="version" width="10%" align="center" halign="center">流程定义版本</th>
					<th field="_operate" width="12%" align="center" halign="center" data-options="formatter:formatOper">操作</th>
				</tr>
			</thead>
		</table>
		<!--  新增弹框界面-->
		<div id="dlg" class="easyui-dialog"
			style="width: 400px; padding: 10px 20px" align="center"
			data-options="modal:true,closed:true,resizable:true,collapsible:true,minimizable:true,maximizable:true"
			buttons="#dlg-buttons">
			<form id="fm" method="post" enctype="multipart/form-data">
				<table cellpadding="5">
					<tr>
						<td style="width: 20%;"><label>部署名称:</label></td>
						<td style="width: 30%;"><input class="easyui-textbox"
							data-options="prompt: '部署名称'" id="filename" name="filename"
							style="width: 80%;"></td>
					</tr>
					<tr>
						<td tyle="width:20%;">文件地址:</td>
						<td style="width: 30%;"><input class="easyui-filebox"
							name="file"
							data-options="prompt: '文件路径',required:true,
					        missingMessage:'请添加文件路径'"
							style="width: 80%;"></td>
					</tr>
				</table>
			</form>

			<div id="dlg-buttons">
				<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
					onclick="processDeploy()">流程部署</a> <a href="#"
					class="easyui-linkbutton" iconCls="icon-cancel"
					onclick="javascript:$('#dlg').dialog('close')">取消</a>
			</div>
		</div>
		<div id="imagediv" class="easyui-dialog"
			style="width: 80%; height: 450px; padding: 1px" closed="true"
			align="center"
			data-options="modal:true,closed:true,resizable:true,collapsible:true,minimizable:true,maximizable:true">
			<img id=image style="position: absolute; top: 50px; left: 50px;"
				src="" />
		</div>
	</div>
</body>
<script type="text/javascript">
//重置
function reset(){
	$('#serachFrom').form("clear");
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
	$('#dg').datagrid("load", params);
}

//新建
function newDeploy(){
	$('#dlg').dialog('open').dialog('setTitle','新建流程部署');
	$('#fm').form('clear');
}
    
    //部署流程
function processDeploy(){
	$('#fm').form('submit',{
		url: '${pageContext.request.contextPath}/activitiController/processDeploy.do',
		onSubmit: function(){
			return $(this).form('validate');
		},
		success: function(result){
			var result = eval('('+result+')');
			if (result.success){
				$('#dlg').dialog('close');		// close the dialog
 				$('#dg').datagrid('reload');	// reload the user data
 				$('#dg1').datagrid('reload');	// reload the user data
			}
			$.messager.alert("提示",result.message,"info");
		}
	});
}
        

     function formatOper(val,row,index){  
 		return '<a href="#" onclick="convertModel('+index+')">转为model</a>';  
 	} 
    
	function formatHref(value, row, index) {
		return '<a href=# onclick=viewBpmnImage('+index+',"image")>'+value+'</a>';
	}
	
	
	function viewBpmnImage(index,type) {
	$('#dg1').datagrid('selectRow', index);// 关键在这里  
	var row = $('#dg1').datagrid('getSelected');
		if (row) {
			var url = "${pageContext.request.contextPath}/activitiController/resource/process-def.do?processDefId="+row.defineId+"&type="+type;
			var	title = "查看流程图片";
			$('#imagediv').dialog('open').dialog('setTitle',title);
			$('#image')[0].src=url;
		}
	}
	   function destroyDeploy(){
	    	var row = $('#dg').datagrid('getSelected');
	    	if (row){
	    		$.messager.confirm('删除','确认删除该流程部署吗?',function(r){
	    			if (r){
	    				$.post('${pageContext.request.contextPath}/activitiController/delDeploy.do',{deploymentId:row.id},function(result){
	    					if (result.success){
	    						$('#dg').datagrid('reload');	// reload the user data
	    						$('#dg1').datagrid('reload');	// reload the user data
	    					} else {
	    						$.messager.show({	// show error message
	    							title: 'Error',
	    							msg: result.message
	    						});
	    					}
	    				},'json');
	    			}
	    		});
	    	}
	     }
</script>
</html>