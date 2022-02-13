package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl extends Util implements UserDao {

    Connection connection;
    {
        try {
            connection = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {

        String sql = "CREATE TABLE IF NOT EXISTS usersTable"+
                     "(id INT NOT NULL AUTO_INCREMENT,"+
                     "name VARCHAR(255) NOT NULL,"+
                     "lastName VARCHAR(255) NOT NULL," +
                     "age INT(3) NOT NULL," +
                     "PRIMARY KEY (id))";

        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {

        String sql = "DROP TABLE IF EXISTS usersTable;";

        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "insert into usersTable ( name, lastName, age) values ( ?, ?, ?);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {

        String sql = "DELETE FROM usersTable WHERE Id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();

        try (Statement statement = connection.createStatement()){
             ResultSet resultSet = statement.executeQuery("SELECT * FROM usersTable");

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                usersList.add(user);
                System.out.println(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {

        try(Statement statement = connection.createStatement()){
            statement.executeUpdate("TRUNCATE TABLE  usersTable");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
