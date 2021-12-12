package io.github._7isenko.databasefiller;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.sql.*;

/**
 * @author 7isenko
 */
public class DBRepository {
    static private Connection connection;
    static private String DB_URL = "jdbc:postgresql://localhost:5432/postgres"; // jdbc:postgresql://pg:5432/studs
    static private String USER = "postgres";
    static private String PASS = "ZXcv";
    // данных для входа на хелиос тут не будет >:D

    public DBRepository() {

        Configurations configs = new Configurations();
        try {
            Configuration config = configs.properties(new File("config.properties"));
            DB_URL = config.getString("host");
            USER = config.getString("user");
            PASS = config.getString("password");
        } catch (ConfigurationException cex) {
            System.out.println("Config not found! Using default!");
        }

        start();
    }

    public void addSCP(int scpId, String scpClass, String name, String description) {
        try {
            String sql = "insert into scp_object (id, name, description, object_class) VALUES (?,?,?,?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, scpId);
            st.setString(2, name);
            st.setString(3, description);
            st.setObject(4, scpClass, Types.OTHER);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println("SQLException! " + e.getMessage());
            e.printStackTrace();
        }
    }

    //  public boolean login(String email, String password) {
    //      try {
    //          String sql = "select email, password from userlist where email like '" + email + "'";
    //          Statement st = connection.createStatement();
    //          ResultSet rs = st.executeQuery(sql);
    //          if (!rs.next()) return register(email);
    //          else return rs.getObject("password").equals(SHA1Encoder.encryptPassword(password));
    //      } catch (SQLException e) {
    //          System.out.println(e.getMessage());
    //          return false;
    //      }
    //  }

    // private void addUser(String email, String encodedPassword) throws SQLException {
    //     String sql = "insert into userlist (email, password) VALUES (?,?)";
    //     PreparedStatement st = connection.prepareStatement(sql);
    //     st.setString(1, email);
    //     st.setString(2, encodedPassword);
    //     st.executeUpdate();
    // }

    private void start() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found.");
            e.printStackTrace();
            return;
        }

        System.out.println("PostgreSQL JDBC Driver successfully connected");

        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You successfully connected to the database");
        } else {
            System.out.println("Failed to make connection to database");
        }
    }

    //TODO: не забыть
    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
