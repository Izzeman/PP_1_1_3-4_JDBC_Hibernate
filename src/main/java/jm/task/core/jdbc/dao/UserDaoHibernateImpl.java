package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {

    public UserDaoHibernateImpl() {}

    @Override
    public void createUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "CREATE TABLE IF NOT EXISTS usersTable"+
                "(id INT NOT NULL AUTO_INCREMENT,"+
                "name VARCHAR(255) NOT NULL,"+
                "lastName VARCHAR(255) NOT NULL," +
                "age INT(3) NOT NULL," +
                "PRIMARY KEY (id))";

        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        String sql = "DROP TABLE IF EXISTS usersTable;";

        Query query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();

        transaction.commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        User user = new User(name, lastName, age);
        session.save(user);

        transaction.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        User user = session.get(User.class, id);
        session.delete(user);

        transaction.commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {

        List<User> usersList = new ArrayList<>();

        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        usersList = session.createQuery("FROM User").list();

        transaction.commit();
        session.close();

        return usersList;
    }

    @Override
    public void cleanUsersTable() {

        Session session = getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("delete User");
        query.executeUpdate();

        transaction.commit();
        session.close();
    }
}
