package db.service;

import db.dao.MessageDao;
import db.entity.MessageEntity;
import dto.MessageDTO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Collections;
import java.util.List;

public class DbService {

    private static final String HIBERNATE_HBM2DDL_SQL = "update";
    private static final String HIBERNATE_SHOW_SQL = "true";

    private final SessionFactory sessionFactory;

    public DbService() {
        Configuration configuration = getMySqlConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    private Configuration getMySqlConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(MessageEntity.class)
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/trychat")
                .setProperty("hibernate.connection.autoReconnect", "true")
                .setProperty("hibernate.connection.username", "test")
                .setProperty("hibernate.connection.password", "test")
                .setProperty("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_SQL)
                .setProperty("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        return configuration;
    }

    public List<MessageEntity> getLastMessages() throws HibernateException {
        try {
            Session session = sessionFactory.openSession();
            MessageDao dao = new MessageDao(session);
            List<MessageEntity> dataSetList = dao.getLastMessages();
            session.close();
            Collections.reverse(dataSetList);
            return dataSetList;
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }

    public void saveMessage(final MessageDTO messageDTO) throws HibernateException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            MessageDao dao = new MessageDao(session);
            dao.insertMessage(messageDTO);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new HibernateException(e);
        }
    }
}
