package chat;

import com.google.gson.Gson;
import db.entity.MessageEntity;
import db.service.DbService;
import dto.MessageDTO;
import org.eclipse.jetty.websocket.api.WebSocketException;

import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatService {

    private Set<ChatWebSocket> webSockets;

    private final DbService dbService = new DbService();

    public ChatService(){
        this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    public void add(ChatWebSocket webSocket){
        webSockets.add(webSocket);
    }

    public void remove(ChatWebSocket webSocket) {
        webSockets.remove(webSocket);
    }

    public void saveMessageInDb(String data) {
        MessageDTO messageDTO = formMessageDTO(data);
        dbService.saveMessage(messageDTO);
    }

    public void sendMessage(String data){
        for(ChatWebSocket client: webSockets){
            try{
                MessageDTO messageDTO = formMessageDTO(data);
                client.sendString(formResponseJson(messageDTO));
            } catch (WebSocketException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public void sendMessageHistory(){
        for (MessageEntity messageDataSet:dbService.getLastMessages()){
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setUser(messageDataSet.getUser());
            messageDTO.setMessage(messageDataSet.getMessage());
            messageDTO.setTimestamp(messageDataSet.getTimestamp());
            String json = formResponseJson(messageDTO);
            sendMessage(json);
        }
    }

    private MessageDTO formMessageDTO(final String message){
        Gson gson = new Gson();
        MessageDTO messageDTO = gson.fromJson(message, MessageDTO.class);
        messageDTO.setTimestamp(new Date());
        return messageDTO;
    }

    private String formResponseJson(final MessageDTO messageDTO){
        Gson gson = new Gson();
        return gson.toJson(messageDTO);
    }

}
