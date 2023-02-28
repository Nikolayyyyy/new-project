package jm.task.core.jdbc.util;


import com.mysql.cj.jdbc.Driver;

import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/users";
    private static final String name = "root";
    private static final String password = "rootroot123";
    // реализуйте настройку соеденения с БД

    public static Connection getCon() {
        Connection con;

        try {
            //Class.forName("com.mysql.cj.jdbc");
            con = DriverManager.getConnection(url,
                    name, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return con;
    }
}

