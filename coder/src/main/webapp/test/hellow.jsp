<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="../js/lib/jquery.min.js" ></script>
<script type="text/javascript">
var webSocket = null;
var tryTime = 0;
$(function () {
initSocket();

 window.onbeforeunload = function () {
//离开页面时的其他操作
};
});

/**
 * 初始化websocket，建立连接
*/
function initSocket() {
 if (!window.WebSocket) {
alert("您的浏览器不支持websocket！");
 return false;
}

 webSocket = new WebSocket("ws://localhost:8080/coder/websocket/zihai/007");

 // 收到服务端消息
 webSocket.onmessage = function (msg) {
	 alert(msg);
	 alert(msg.data);
console.log(msg);
};

 // 异常
 webSocket.onerror = function (event) {
console.log(event);
};

 // 建立连接
 webSocket.onopen = function (event) {
console.log(event);
};

 // 断线重连
 webSocket.onclose = function () {
	 // 重试10次，每次之间间隔10秒
	 if (tryTime < 2) {
	 setTimeout(function () {
	 webSocket = null;
	tryTime++;
	initSocket();
	 }, 500);
	 } else {
	 tryTime = 0;
	}
};

}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	welcome
</body>

</html>
