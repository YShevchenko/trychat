package db.entity;

import dto.MessageDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="messages")
public class MessageEntity implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user")
    private String user;

    @Column(name = "message")
    private String message;

    @Column(name = "timestamp")
    private Date timestamp;

    public MessageEntity(final MessageDTO messageDTO) {
        this.setMessage(messageDTO.getMessage());
        this.setUser(messageDTO.getUser());
        this.setTimestamp(messageDTO.getTimestamp());
    }

    public MessageEntity(){

    }

    public long getId() {
        return id;
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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Date timestamp) {
        this.timestamp = timestamp;
    }
}
