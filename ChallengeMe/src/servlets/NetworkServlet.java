package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/ws")
public class NetworkServlet {

	private static Map<String, Integer> sessionVector = new HashMap<String, Integer>();
	private static Map<Session, String> sessions = new HashMap<Session, String>();
	
	@OnOpen
	public void open(Session session) {
		System.out.println("Connection made!");
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		if(sessions.get(session)==null)
		sessions.put(session, message);
		else
			sessions.replace(session, message);
		if(sessionVector.get(message)==null)
		{
			sessionVector.put(message, 1 );
		}
		else
		{
			sessionVector.replace(message, sessionVector.get(message)+1);
		}
		try {
			session.getBasicRemote().sendText(sessionVector.get(message).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@OnClose
	public void close(Session session) {
		sessionVector.replace(sessions.get(session), sessionVector.get(sessions.get(session))-1);
		System.out.println("Disconnecting!");
	}
	
	@OnError
	public void error(Throwable error) {
		System.out.println("Error!");
	}
}