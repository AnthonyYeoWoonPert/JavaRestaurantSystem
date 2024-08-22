/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DerbyDB {

    private final static String DATABASE_URL = "jdbc:derby://localhost:1527/FBOS";
    private final static String DATABASE_USERNAME = "root";
    private final static String DATABASE_PASSWORD = "food";
    private static Connection dbConnection;

    public static void connectDB() {
        try {
            dbConnection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            dbConnection.setAutoCommit(false);
            System.out.println("Database is running...");
            
            //initialise all tables
            initialiseItemTable(createStatement());
            initialiseOrderTable(createStatement());
            initialiseCartTable(createStatement());
            initialiseUserTable(createStatement());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static Statement createStatement() throws SQLException {
        return dbConnection.createStatement();
    }

    public static PreparedStatement preparedStatement(String query) throws SQLException {
        return dbConnection.prepareStatement(query);
    }

    public static void commit() throws SQLException {
        dbConnection.commit();
    }

    public static void close() throws SQLException {
        dbConnection.close();
    }
    

    public static void initialiseItemTable(Statement stmt) throws SQLException {
        String createItemTableQuery;
        ResultSet rs;
        try {
            rs = dbConnection.getMetaData().getTables(null, null, "ITEM", null);
            if (!rs.next()) {
                createItemTableQuery
                        = "CREATE TABLE Item ("
                        + "item_id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "item_name VARCHAR(255) NOT NULL,"
                        + "unit_price DECIMAL(10,2) NOT NULL,"
                        + "stock_amount INT NOT NULL)";
                stmt.executeUpdate(createItemTableQuery);
                System.out.println("Table 'Item' created successfully.");
                commit();

                // Insert static values
                String insertValuesQuery = "INSERT INTO Item (item_name, unit_price, stock_amount) "
                        + "VALUES "
                        + "('burger', 15.99, 50), "
                        + "('fried chicken', 89.99, 25), "
                        + "('cake', 29.99, 75), "      
                        + "('pepsi', 35.99, 30), "
                        + "('seven up', 49.99, 20)";
                stmt.executeUpdate(insertValuesQuery);
                System.out.println("Static values added to 'Item' table.");
                commit();
            } else {
                System.out.println("Table 'Item' already exists. No new table created.");
            }
        } catch (SQLException ex) {
            System.out.println("Error creating/checking table: " + ex.getMessage());
        }
    }

    public static void initialiseUserTable(Statement stmt) throws SQLException {
        String createCustomerTableQuery;
        ResultSet rs;
        try {
            rs = dbConnection.getMetaData().getTables(null, null, "ODSUSER", null);
            if (!rs.next()) {
                createCustomerTableQuery
                        = "CREATE TABLE OdsUser ("
                        + "user_id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "username VARCHAR(15) NOT NULL UNIQUE,"
                        + "password VARCHAR(255) NOT NULL,"
                        + "first_name VARCHAR(45) NOT NULL,"
                        + "last_name VARCHAR(45) NOT NULL,"
                        + "passport VARCHAR(20) NOT NULL UNIQUE,"
                        + "role VARCHAR(20) NOT NULL)";
                stmt.executeUpdate(createCustomerTableQuery);
                System.out.println("Table 'OdsUser' created successfully.");
                commit();
                
                String customerPassword = Hasher.sha256("jason0114");
                String adminPassword = Hasher.sha256("administrator");
                
                // Insert static values
                String insertValuesQuery = "INSERT INTO OdsUser (username, password, first_name, last_name, passport, role) "
                        + "VALUES "
                        + "('administrator', '" + adminPassword + "', 'Jayson', 'Tatum', 'J12345678', 'ADMIN'), "
                        + "('jason', '" + customerPassword + "', 'JieXiang', 'Yu', 'X12345678', 'CUSTOMER')";
                stmt.executeUpdate(insertValuesQuery);
                System.out.println("Static values added to 'Item' table.");
                commit();
            } else {
                System.out.println("Table 'OdsUser' already exists. No new table created.");
            }
        } catch (SQLException ex) {
            System.out.println("Error creating/checking table: " + ex.getMessage());
        }
    }

    public static void initialiseOrderTable(Statement stmt) throws SQLException {
        String createCustomerOrderTableQuery;
        ResultSet rs;
        try {
            rs = dbConnection.getMetaData().getTables(null, null, "CUSTOMERORDER", null);
            if (!rs.next()) {
                createCustomerOrderTableQuery
                        = "CREATE TABLE CustomerOrder ("
                        + "receipt_no INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "customer_id VARCHAR(255) NOT NULL,"
                        + "customer_name VARCHAR(255) NOT NULL,"
                        + "item_id VARCHAR(255) NOT NULL,"
                        + "item_name VARCHAR(255) NOT NULL,"
                        + "unit_price VARCHAR(255) NOT NULL,"
                        + "order_quantity VARCHAR(255) NOT NULL,"
                        + "total_quantity INT NOT NULL,"
                        + "item_totalprice VARCHAR(255) NOT NULL,"
                        + "order_totalprice DECIMAL(10,2) NOT NULL,"
                        + "paid_amount DECIMAL(10,2) NOT NULL,"
                        + "payment_type VARCHAR(40) NOT NULL,"
                        + "payment_time VARCHAR(40) NOT NULL,"
                        + "payment_status VARCHAR(10) NOT NULL,"
                        + "order_status VARCHAR (10) NOT NULL)";
                
                stmt.executeUpdate(createCustomerOrderTableQuery);
                System.out.println("Table 'Customer Order Table' created successfully.");
                commit();
            } else {
                System.out.println("Table 'Order' already exists. No new table created.");
            }
        } catch (SQLException ex) {
            System.out.println("Error creating/checking table: " + ex.getMessage());
        }
    }
    
    public static void initialiseCartTable(Statement stmt) throws SQLException {
        String createCartTableQuery;
        ResultSet rs;
        try {
            rs = dbConnection.getMetaData().getTables(null, null, "CUSTOMERCART", null);
            if (!rs.next()) {
                createCartTableQuery
                        = "CREATE TABLE Cart ("
                        + "customer_id VARCHAR(255) NOT NULL,"
                        + "customer_name VARCHAR(255) NOT NULL,"
                        + "item_id INT NOT NULL,"
                        + "item_name VARCHAR(255) NOT NULL,"
                        + "unit_price DECIMAL(10,2) NOT NULL,"
                        + "order_quantity INT NOT NULL,"
                        + "item_totalprice DECIMAL(10,2) NOT NULL,"
                        + "payment_status VARCHAR(10) NOT NULL)";
                
                stmt.executeUpdate(createCartTableQuery);
                System.out.println("Table 'Cart Table' created successfully.");
                commit();
            } else {
                System.out.println("Table 'Cart' already exists. No new table created.");
            }
        } catch (SQLException ex) {
            System.out.println("Error creating/checking table: " + ex.getMessage());
        }
    }

//    public static void getConnection() throws SQLException {
//        Connection conn = null;
//        Statement stmt = null;
//        conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);; // Replace with your method to get a connection
//        stmt = conn.createStatement();
//        if (stmt == null) {
//            throw new SQLException("Database connection is not established.");
//        }
//    }

    public static void setAutoCommit(boolean b) throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);; // Replace with your method to get a connection
        stmt = conn.createStatement();
        if (stmt == null) {
            throw new SQLException("Database connection is not established.");
        }
    }

    public static void rollback() throws SQLException {
        Connection conn = null;
        Statement stmt = null;
        conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);; // Replace with your method to get a connection
        stmt = conn.createStatement();
        if (stmt == null) {
            throw new SQLException("Database connection is not established.");
        }
    }
    
    
    // Tang Her Vin backupDatabase
    public static void backupDatabaseCart(String outputPath) throws SQLException, IOException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        BufferedWriter writer = null;


        try {
            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);; // Replace with your method to get a connection
            stmt = conn.createStatement();

            String query = "SELECT * FROM CART";
            rs = stmt.executeQuery(query);

            writer = new BufferedWriter(new FileWriter(outputPath));

            while (rs.next()) {

                int numColumns = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= numColumns; i++) {
                    String columnValue = rs.getString(i);
                    writer.write(columnValue);

                    if (i < numColumns) {
                        writer.write(", ");
                    }
                }
                writer.newLine();
            }

            System.out.println("Database backup successful to " + outputPath);

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            // Close all resources to prevent resource leaks
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }
    
    // Benjamin Yee backupDatase
    public static void backupDatabaseCustomerOrder(String outputPath) throws SQLException, IOException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        BufferedWriter writer = null;


        try {
            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);; // Replace with your method to get a connection
            stmt = conn.createStatement();

            String query = "SELECT * FROM CUSTOMERORDER";
            rs = stmt.executeQuery(query);

            writer = new BufferedWriter(new FileWriter(outputPath));

            while (rs.next()) {

                int numColumns = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= numColumns; i++) {
                    String columnValue = rs.getString(i);
                    writer.write(columnValue);

                    if (i < numColumns) {
                        writer.write(", ");
                    }
                }
                writer.newLine();
            }

            System.out.println("Database backup successful to " + outputPath);

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            // Close all resources to prevent resource leaks
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }
    
    // Yu Jie Xiang backupDatabase
    public static void backupDatabaseItem(String outputPath) throws SQLException, IOException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        BufferedWriter writer = null;


        try {
            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);; // Replace with your method to get a connection
            stmt = conn.createStatement();

            String query = "SELECT * FROM ITEM";
            rs = stmt.executeQuery(query);

            writer = new BufferedWriter(new FileWriter(outputPath));

            while (rs.next()) {

                int numColumns = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= numColumns; i++) {
                    String columnValue = rs.getString(i);
                    writer.write(columnValue);

                    if (i < numColumns) {
                        writer.write(", ");
                    }
                }
                writer.newLine();
            }

            System.out.println("Database backup successful to " + outputPath);

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            // Close all resources to prevent resource leaks
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }
    
    // Anthony Yeo backupDatabse
    public static void backupDatabaseODSUSER(String outputPath) throws SQLException, IOException {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        BufferedWriter writer = null;


        try {
            conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);; // Replace with your method to get a connection
            stmt = conn.createStatement();

            String query = "SELECT * FROM ODSUSER";
            rs = stmt.executeQuery(query);

            writer = new BufferedWriter(new FileWriter(outputPath));

            while (rs.next()) {

                int numColumns = rs.getMetaData().getColumnCount();
                for (int i = 1; i <= numColumns; i++) {
                    String columnValue = rs.getString(i);
                    writer.write(columnValue);

                    if (i < numColumns) {
                        writer.write(", ");
                    }
                }
                writer.newLine();
            }

            System.out.println("Database backup successful to " + outputPath);

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            // Close all resources to prevent resource leaks
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }
}
