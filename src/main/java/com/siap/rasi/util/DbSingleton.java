package com.siap.rasi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rafael.esquivel
 */
public class DbSingleton {

    private static final String connectionUrl = "jdbc:sqlserver://10.11.2.32\\v1_2;"
            + "databaseName=rasi;user=sa;password=Winter1234";
    private static Connection connection;

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(connectionUrl);
            return connection;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DbSingleton.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void main(String[] args) throws SQLException {
        Connection c = getConnection();
        boolean valid = c.isValid(0);
        System.out.println(valid);
    }

}
