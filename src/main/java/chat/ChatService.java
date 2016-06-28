package chat;

import com.google.gson.Gson;
import db.entity.MessageEntity;
import db.service.DbService;
import org.eclipse.jetty.websocket.api.WebSocketException;

import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatService {

    private final DbService dbService = new DbService();
    private Set<ChatWebSocket> webSockets;

    public ChatService() {
        this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public void add(ChatWebSocket webSocket) {
        webSockets.add(webSocket);
    }

    public void remove(ChatWebSocket webSocket) {
        webSockets.remove(webSocket);
    }

    public void saveMessageInDb(String data) {
        MessageEntity messageEntity = formMessageEntity(data);
        dbService.saveMessage(messageEntity);
    }

    public void sendMessage(String data) {
        for (ChatWebSocket client : webSockets) {
            try {
                MessageEntity messageEntity = formMessageEntity(data);
                client.sendString(formResponseJson(messageEntity));
            } catch (WebSocketException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void sendMessageHistory() {
        for (MessageEntity messageEntity : dbService.getLastMessages()) {
            String json = formResponseJson(messageEntity);
            sendMessage(json);
        }
    }

    private MessageEntity formMessageEntity(final String message) {
        Gson gson = new Gson();
        MessageEntity messageEntity = gson.fromJson(message, MessageEntity.class);
        messageEntity.setTimestamp(new Date());
        return messageEntity;
    }

    private String formResponseJson(final MessageEntity messageEntity) {
        Gson gson = new Gson();
        return gson.toJson(messageEntity);
    }

}
