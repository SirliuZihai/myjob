<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>流程测试</title>
<jsp:include page="${contextPath}/page/common/common.html"></jsp:include>
</head>
<body>
	<div class="easyui-panel" style="border:0;width:100%;">
	<table id="dg" class="easyui-datagrid"
		 	title=""
			style="width: 100%; height: auto;"
			url='list.do'
			toolbar="#toolbar" rownumbers="true" fitColumns="true" nowrap="false"
			singleSelect="true" pagination="true">
			<thead>
				<tr>
					<th field="ck" checkbox=true></th>
					<th field="eventid" width="10%" align="center" halign="center">事件ID</th>
					<th field="instanceid"  width="10%"align="center" halign="center">实例ID</th>
					<th field="id" width="10%" align="left" halign="center">当前id</th>
					<th field="name" width="10%" align="left" halign="center">名称</th>
					<th field="statue" width="10%" align="left" halign="center">状态</th>
					<th field="prepeople" width="10%" align="left" halign="center">待审批人</th>
					<th field="people" width="10%" align="left" halign="center">最近审批人</th>
					<th field="reason" width="10%" align="left" halign="center">批注</th>
					<th field="time" width="10%" align="left" halign="center">审批时间</th>
					<th field="show" width="10%" align="left" halign="center" formatter="showimage">查看流程图</th>
				</tr>
			</thead>
		</table>
		<div id="toolbar">
			<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="start()">开始流程</a> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="submit_1(1)">审批</a>
			<a href="#"  class="easyui-linkbutton" iconCls="icon-remove"  plain="true" onclick="stop()" >停止</a> 		
		</div>
	</div>
	<!--流程展示  -->
	<div id="imagediv" class="easyui-dialog"
		 style="width: 900px; height: 450px; padding: 1px;top:25px" closed="true" align="center" 
		data-options="modal:true,closed:true,resizable:false,collapsible:true,minimizable:true,maximizable:true" >
		<img id="image" />
    </div>
     <!--  新增弹框界面-->
	<div id="dlgview" class="easyui-dialog"
		style="width: 300px; height:100px; padding: 10px 20px" closed="true" align="center" 
		data-options="modal:true,closed:true">
		<div id="msg"></div>
    </div> 
     <!-- 审批 -->
     <div id="back_dialog" class="easyui-dialog"  top=10px style="width:500px;padding: 8px 10px;text-align:right" data-options="resizable:true,iconCls:'icon-more',modal:true,closed:true" title="退回理由">
	 	<form id="fx1" style=" text-align:center; width: 100%;" >
	 		<input name="agree" class="easyui-combobox" data-options="required:true,valueField: 'VALUE',textField: 'LABEL',data:statu_arr4"/>
	 		<input name="userid" class="easyui-textbox" data-options="prompt:'审批用户:liu-deptleader,zhi-hr,yi:own'"/>
	 		<input name="eventid" id="eventid"  hidden="true"/>
	 		<input name="arg" id="arg"  hidden="true" />
		  	<br/>
		   	<input id='reason' name='reason' style="width:80%;height:160px;padding:0px 0px 0px 0px" class="easyui-textbox" data-options=" required:true,validType:'length[0,500]',multiline:true,prompt:'提示：写明具体的备注。'"  >
		</form>
		<div style="margin-bottom: 0px">
			<a href="#"  class="easyui-linkbutton" onclick="submit('fx1','submit.do')" data-options="iconCls:'icon-save'" style="width: 70px;">保存</a> 
		<a href="#"  class="easyui-linkbutton" onclick="javascript:$('#back_dialog').dialog('close')" data-options="iconCls:'icon-cancel'" style="width: 70px;">取消</a> 
		</div>
	</div>
	<form id="none"></form>
	
	
<script type="text/javascript">
var statu_arr4 = [{'LABEL': '通过 ','VALUE': '1'},{'LABEL': '拒绝','VALUE': '0'}];
function dosuccess(){
	$(document).scrollTop(0);
	$('#back_dialog').dialog('close');
	$('#dg').datagrid('reload');
}
function submit_1(arg){
	var row = $('#dg').datagrid('getSelected');
	if(row){
		$('#fx1').form('clear');
		$('#eventid').val(row.eventid);
		$('#arg').val(arg);
		$('#back_dialog').dialog('open');
	}else{
		$.messager.alert('提示','请先选择要操作的元素','info');
	}

}
function start(){
	var row = $('#dg').datagrid('getSelected');
	if(row){
		$.post('start.do',{"eventid":row.eventid},function(result){
			result = eval('('+result+')');
			$.messager.show({
				title:'提示',
				msg:result.message,
				timeout:3000,
				showType:'slide'
			});
			$('#dg').datagrid('reload');

		});
	}else{
		$.messager.alert('提示','请先选择要操作的元素','info');
	}
	
}

function stop(){
	var row = $('#dg').datagrid('getSelected');
	if(row){
		$.post('stop.do',{"eventid":row.eventid},function(result){
			result = eval('('+result+')');
			$.messager.show({
				title:'提示',
				msg:result.message,
				timeout:3000,
				showType:'slide'
			});
			$('#dg').datagrid('reload');

		});
	}else{
		$.messager.alert('提示','请先选择要操作的元素','info');
	}
}
/**
 * "查看流程
 */
function viewprocess(id,prepeople){
	$('#image')[0].src="showproccess.do?eventid="+id+"&prepeople="+"liu"+"&t="+Math.random();
	//$('#image')[0].src="../runtime/process-instances/"+id+"/diagram.do?t="+Math.random();
	//$('#image')[0].src="../repository/process-definitions/test:1:127504/image.do?t="+Math.random();
	$('#imagediv').dialog('open').dialog('refresh').dialog('setTitle',"查看流程"); 	

}
function showimage(value,row){
	return "<a onclick='viewprocess(\""+row.eventid+"\",\""+row.prepeople+"\")'>查看流程图</a>";
}

</script>
</body>
</html>