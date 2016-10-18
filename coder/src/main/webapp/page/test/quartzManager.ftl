<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache"> 
<META HTTP-EQUIV="Expires" CONTENT="0"> 
<title>调度控制台</title>
<script type="text/javascript" src="../js/lib/jquery.min.js"></script>

</head>
	<body>
		<form action="../quartz/add.do" method="post">
			任务名字:<input name="jobname"></input>
			任务组名:<input name="group"></input>
			任务类:<input name="className"></input>
			任务触发器:<input name="triggerName"></input>
			<input type="submit" value='增加'></input>
		</form>
		<form action="../quartz/delete.do" method="post">
			任务名字:<input name="jobname"></input>
			任务组名:<input name="group"></input>
			任务触发器:<input name="triggerName"></input>
			<input type="submit" value='删除'></input>
		</form>
	</body>
</html>