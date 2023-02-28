package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class UserDaoJDBCImpl implements UserDao {

    Connection connection = Util.getCon();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS `users` \n" +
                "  (`id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastname` VARCHAR(45) NOT NULL,\n" +
                "  `age` INT(3) NOT NULL,\n" +
                "  PRIMARY KEY (`id`))\n" +
                "ENGINE = InnoDB;\n";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(CREATE_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void dropUsersTable() {
        final String DROP_TABLE = "DROP TABLE IF EXISTS users;";

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(DROP_TABLE);
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        final String SAVE_USERS = "INSERT INTO `users` (name, lastName, age) VALUES(?,?,?);";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USERS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        final String REMOVE_USERS = "DELETE FROM `users` WHERE `id`=?;";
        try (PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_USERS)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        String GET_A_U = "SELECT * FROM users;";

        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(GET_A_U);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                list.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public void cleanUsersTable() {
        String CLEAN_U = "TRUNCATE users";
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(CLEAN_U);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
