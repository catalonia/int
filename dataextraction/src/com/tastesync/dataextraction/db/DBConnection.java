package com.tastesync.dataextraction.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
    /** DB_DRIVER */
    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";

    /** DB_CONNECTION */
    //private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/tastesync";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/factual";

    /** DB_USER */
    //private static final String DB_USER = "tastesync";
    private static final String DB_USER = "root";

    /** DB_PASSWORD */
    private static final String DB_PASSWORD = "";

    public DBConnection() {
    }

    /**
     * Open DB connection
     *
     * @return connection
     */
    public Connection openDBConnection() {
        System.out.println("Open DB connection");

        Connection connection = null;

        try {
            Class.forName(DB_DRIVER);
        } // end try
        catch (ClassNotFoundException e) {
            System.out.println("Where is your MYSQL JDBC Driver?");
            e.printStackTrace();

            System.exit(0);
        } // end catch

        System.out.println("MYSQL Driver Registered!");

        try {
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
                    DB_PASSWORD);
        } // end try
        catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();

            System.exit(0);
        } // end catch

        if (connection != null) {
            System.out.println(
                "DB connection Success... Finally, DO close the Connection!");
        } // end if
        else {
            System.out.println("Failed to make connection!");
        } // end else

        return connection;
    } // end openDBConnection()

    /**
     * Close DB connection
     *
     * @param connection connection to be closed.
     */
    public void closeDBConnection(Connection connection) {
        System.out.println("Closed DB connection");

        if (connection != null) {
            try {
                connection.close();
            } // end try
            catch (SQLException e) {
                e.printStackTrace();
            } // end catch
        } // end if
    } // end closeDBConnection()

    /**
     * test jdbc connection
     */
    public void testJdbcConnection() {
        System.out.println("-------- MYSQL JDBC Connection Testing ------");

        try {
            Class.forName(DB_DRIVER);
        } // end try
        catch (ClassNotFoundException e) {
            System.out.println("Where is your MYSQL JDBC Driver?");
            e.printStackTrace();

            return;
        } // end catch

        System.out.println("MYSQL JDBC Driver Registered!");

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
                    DB_PASSWORD);
        } // end try
        catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();

            return;
        } // end catch

        if (connection != null) {
            System.out.println("DB connection Success");

            try {
                connection.close();
            } // end try
            catch (SQLException e) {
                e.printStackTrace();
            } // end catch
        } // end if
        else {
            System.out.println("Failed to make connection!");
        } // end else
    } // end testJdbcConnection()
}
