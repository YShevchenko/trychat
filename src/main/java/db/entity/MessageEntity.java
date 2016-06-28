package db.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="messages")
public class MessageEntity implements Serializable{

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user")
    private String user;

    @Column(name = "message")
    private String message;

    @Column(name = "timestamp")
    private Date timestamp;

    public MessageEntity(final MessageEntity messageEntity) {
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
