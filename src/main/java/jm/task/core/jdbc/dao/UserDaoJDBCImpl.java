package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.service.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {

    private void runStatement(String sql){
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement())
        {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS usersTable"+
                     "(id INT NOT NULL AUTO_INCREMENT,"+
                     "name VARCHAR(255) NOT NULL,"+
                     "lastName VARCHAR(255) NOT NULL," +
                     "age INT(3) NOT NULL," +
                     "PRIMARY KEY (id))";
        runStatement(sql);
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS usersTable;";
        runStatement(sql);
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "insert into usersTable ( name, lastName, age) values ( '%s', '%s', '%s');".
                formatted(name, lastName, age);
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement())
        {
            statement.executeUpdate(sql);
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM usersTable WHERE Id = %s;".formatted(id);
        runStatement(sql);
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM usersTable"))
        {
            while (resultSet.next()) {
                Long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String lastName = resultSet.getString(3);
                byte age = resultSet.getByte(4);
                User user = new User(name, lastName, age);
                user.setId(id);
                usersList.add(user);
                System.out.println(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String sql = "TRUNCATE TABLE  usersTable";
        runStatement(sql);
    }
}
