package dto;

import java.util.Date;

public class MessageDTO {

    private String user;
    private String message;
    private Date timestamp;

    public MessageDTO(){

    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
