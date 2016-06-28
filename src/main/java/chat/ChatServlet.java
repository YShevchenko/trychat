package chat;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

import javax.servlet.annotation.WebServlet;

@WebServlet(name = "ChatServlet", urlPatterns = {"/chat"})
public class ChatServlet extends WebSocketServlet{
    private final ChatService chatService;
    public ChatServlet(){
        this.chatService = new ChatService();
    }

    @Override
    public void configure(WebSocketServletFactory factory){
        factory.setCreator((request, response) ->  new ChatWebSocket(chatService));
    }
}
