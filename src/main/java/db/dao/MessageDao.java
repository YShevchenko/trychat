package db.dao;

import db.entity.MessageEntity;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

import java.util.List;


public class MessageDao {

    private static final int INITIAL_MAX_MESSAGES = 20;
    private static final String DESCEND_CRITERIA = "timestamp";

    private Session session;

    public MessageDao(final Session session){
        this.session = session;
    }

    public List<MessageEntity> getLastMessages() throws HibernateException {
        Criteria criteria = session.createCriteria(MessageEntity.class);
        criteria.addOrder(Order.desc(DESCEND_CRITERIA));
        criteria.setMaxResults(INITIAL_MAX_MESSAGES);
        final List<MessageEntity> result =  criteria.list();
        return result;
    }

    public long insertMessage(final MessageEntity messageEntity) throws HibernateException {
        return (Long) session.save(messageEntity);
    }
}
