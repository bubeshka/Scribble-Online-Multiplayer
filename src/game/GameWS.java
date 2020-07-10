package game;

import login.User;
import main.java.Pair;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/WS", configurator = GameSocketConfig.class)
public class GameWS {
    private static ConcurrentHashMap<Session, Pair<Game, Integer>> map = new ConcurrentHashMap<>();
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        HttpSession sess = (HttpSession) config.getUserProperties().get("httpSession");
        Game game = (Game) sess.getAttribute("GAME");
        int id = game.registerSession(session, (User) sess.getAttribute("USER"));
        map.put(session, new Pair<>(game, id));
    }

    @OnClose
    public void onClose(Session session) {
        Pair<Game, Integer> p = map.get(session);
        p.getFirst().unregister(p.getSecond());
        map.remove(session);
    }

    //CLIENT TO SERVER COMMUNICATION
    @OnMessage
    public String onMessage(String message, Session session) throws IOException {
        Pair<Game, Integer> p = map.get(session);
        if(message.startsWith("L") || message.startsWith("B"))
            p.getFirst().stroke(message, p.getSecond());
        else p.getFirst().CheckGuessFromGame(p.getSecond(), message.substring(2));
        //TODO: return chosen word from artist
        //TODO: maybe echo something useful back to client?
        //TODO: handle clear canvas action
        //TODO: handle stroke color and size.
        return "";
    }

    //TODO: unregister player and session on error
    @OnError
    public void onError(Throwable e) {
        e.printStackTrace();
    }

}
