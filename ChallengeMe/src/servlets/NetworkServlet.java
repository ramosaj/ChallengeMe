package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/views")
public class NetworkServlet
{
	// Maps a challengeId with the number of views
	private static Map<String, Integer> views = new HashMap<>();
	
	private static Map<Session, String> sessions = new HashMap<>();
	
	@OnOpen
	public void open (Session session) {
		System.out.println(session + " - Connection made!");
	}
	
	@OnMessage
	public void onMessage (String message, Session session) {
		System.out.format("%s from %s" + System.lineSeparator(), message, session.getId());
		
		String[] messageComponents = message.split(" ");
		String action = messageComponents[0];
		String challengeId = messageComponents[1];
		
		if (action.equals("VIEW")) {
			onView(challengeId, session);
		}
		
		else if (action.equals("QUERY")) {
			onQuery(challengeId, session);
		}
		
	}
	
	public void onView (String challengeId, Session session)
	{
		if (sessions.get(session) == null) {
			sessions.put(session, challengeId);
		}
		else {
			sessions.replace(session, challengeId);
		}
		
		if (views.get(challengeId) == null) {
			views.put(challengeId, 1);
		}
		else {
			views.replace(challengeId, views.get(challengeId) + 1);
		}
		
		onQuery(challengeId, session);
	}
	
	public void onQuery (String challengeId, Session session)
	{
		Integer challengeViews = views.get(challengeId);
		if (challengeViews == null) {
			challengeViews = 0;
		}
		
		try {
			session.getBasicRemote().sendText(challengeViews.toString());
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	@OnClose
	public void close (Session session)
	{
		views.replace(sessions.get(session), views.get(sessions.get(session))-1);
		System.out.println("Disconnecting!");
	}
	
	@OnError
	public void error (Throwable error)
	{
		System.out.println(error.getMessage());
	}
}