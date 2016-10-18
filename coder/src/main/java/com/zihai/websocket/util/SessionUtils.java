package com.zihai.websocket.util;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ����˵���������洢ҵ�����sessionId�����ӵĶ�Ӧ��ϵ
 * ����ҵ���߼�����װ��sessionId��ȡ��Ч���Ӻ���к�������
 * ���ߣ�liuxing(2014-12-26 02:32)
*/
public class SessionUtils {

 public static Map<String, Session> clients = new ConcurrentHashMap<>();

 public static void put(String relationId, int userCode, Session session){
 clients.put(getKey(relationId, userCode), session);
}

 public static Session get(String relationId, int userCode){
 return clients.get(getKey(relationId, userCode));
}

 public static void remove(String relationId, int userCode){
 clients.remove(getKey(relationId, userCode));
}

/**
 * �ж��Ƿ�������
 * @param relationId
 * @param userCode
 * @return
*/
 public static boolean hasConnection(String relationId, int userCode) {
 return clients.containsKey(getKey(relationId, userCode));
}

/**
 * ��װΨһʶ���key
 * @param relationId
 * @param userCode
 * @return
*/
 public static String getKey(String relationId, int userCode) {
 return relationId +"_"+ userCode;
}

}