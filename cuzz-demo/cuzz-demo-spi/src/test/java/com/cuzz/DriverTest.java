package com.cuzz;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DriverTest {

    @Test
    public void testDriver() throws SQLException {

        String url = "jdbc:mysql://localhost:3306/demo?useSSL=true&useUnicode=true&characterEncoding=UTF-8";
        String username = "root";
        String pwd = "12345";
        Connection conn = DriverManager.getConnection(url, username, pwd);
    }
}
