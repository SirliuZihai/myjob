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
 * ����˵����websocket������, ʹ��J2EE7�ı�׼
 * �м�ֱ���ڸ����Ӵ������м���ҵ�������
 * ���ߣ�liuxing(2014-11-14 04:20)
*/
//relationId��userCode���ҵ�ҵ���ʶ����,websocket.ws�����ӵ�·�����������ж���
@ServerEndpoint("/websocket/{relationId}/{userCode}")
public class WebsocketEndPoint {

 private static Log log = LogFactory.getLog(WebsocketEndPoint.class);

/**
 * ������ʱ����
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
 * �յ��ͻ�����Ϣʱ����
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
 * �쳣ʱ����
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
 * �ر�����ʱ����
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