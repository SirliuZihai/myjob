package com.zihai.websocket.test;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zihai.websocket.util.SessionUtils;

/**
 * 功能说明：websocket处理类, 使用J2EE7的标准
 * 切忌直接在该连接处理类中加入业务处理代码
 * 作者：liuxing(2014-11-14 04:20)
*/
//relationId和userCode是我的业务标识参数,websocket.ws是连接的路径，可以自行定义
@ServerEndpoint("/websocket/{relationId}/{userCode}")
public class WebsocketEndPoint {

 private static Log log = LogFactory.getLog(WebsocketEndPoint.class);

/**
 * 打开连接时触发
 * @param relationId
 * @param userCode
 * @param session
*/
@OnOpen
 public void onOpen(@PathParam("relationId") String relationId,
 @PathParam("userCode") int userCode, Session session){
	SessionUtils.put(relationId, userCode, session);
	System.out.println(relationId+userCode);
}

/**
 * 收到客户端消息时触发
 * @param relationId
 * @param userCode
 * @param message
 * @return
*/
@OnMessage
 public String onMessage(@PathParam("relationId") String relationId,
 @PathParam("userCode") int userCode,String message) {
 return"Got your message ("+ message +").Thanks !";
}

/**
 * 异常时触发
 * @param relationId
 * @param userCode
 * @param session
*/
@OnError
 public void onError(@PathParam("relationId") String relationId,
 @PathParam("userCode") int userCode,Throwable throwable,Session session) {
  System.out.println("error");
}

/**
 * 关闭连接时触发
 * @param relationId
 * @param userCode
 * @param session
*/
@OnClose
 public void onClose(@PathParam("relationId") String relationId,
 @PathParam("userCode") int userCode,
 Session session) {
 System.out.println("close");
}

}