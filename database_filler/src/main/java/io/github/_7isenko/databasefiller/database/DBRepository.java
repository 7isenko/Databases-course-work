package io.github._7isenko.databasefiller.database;

import io.github._7isenko.databasefiller.database.entities.Location;
import io.github._7isenko.databasefiller.database.entities.SCPInstance;
import io.github._7isenko.databasefiller.misc.MathHelper;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.sql.*;
import java.util.List;

/**
 * @author 7isenko
 */
public class DBRepository {
    static private Connection connection;
    static private String DB_URL = "jdbc:postgresql://localhost:5432/postgres"; // jdbc:postgresql://pg:5432/studs
    static private String USER = "postgres";
    static private String PASS = "ZXcv";

    public DBRepository() {

        Configurations configs = new Configurations();
        try {
            Configuration config = configs.properties(new File("config.properties"));
            DB_URL = config.getString("host");
            USER = config.getString("user");
            PASS = config.getString("password");
        } catch (ConfigurationException cex) {
            System.out.println("Конфиг не был найден. Используются стандартные значения.");
        }
        start();
    }

    public boolean addFoundation(Location location) {
        int locationId = 0;
        try {
            try {
                String sql = String.format("select id from location where latitude = %f and longitude = %f",
                        location.getLatitude(), location.getLongitude());
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(sql);
                rs.next();
                locationId = (int) rs.getObject("id");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.printf("Невозможно добавить фонд, находящийся в локации с latitude = %f и " +
                        "longitude = %f = %d%n", location.getLatitude(), location.getLongitude());
                return false;
            }

            String sql = "insert into foundation (location_id) VALUES (?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, locationId);
            st.executeUpdate();
            System.out.printf("Добавлен фонд, находящийся в локации с id = %d%n", locationId);
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.printf("Невозможно добавить фонд, находящийся в локации с id = %d%n", locationId);
            return false;
        }
    }

    public void addLocationList(List<Location> locationList) {
        for (Location location : locationList) {
            addLocation(location);
        }
    }

    public boolean addLocation(Location location) {
        return addLocation(location.getLatitude(), location.getLongitude());
    }

    public boolean addLocation(double latitude, double longitude) {
        try {
            String sql = "insert into location (latitude, longitude) VALUES (?,?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setDouble(1, MathHelper.roundAvoid(latitude, 6));
            st.setDouble(2, MathHelper.roundAvoid(longitude, 6));
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.printf("Локация %f, %f не будет добавлена в таблицу", latitude, longitude);
            return false;
        }
    }

    public void addScpList(List<SCPInstance> scpList) {
        scpList.removeIf(scpInstance -> !addSCP(scpInstance));
    }

    public boolean addSCP(SCPInstance scpInstance) {
        return addSCP(scpInstance.getId(), scpInstance.getClazz(), scpInstance.getName(), scpInstance.getDescription());
    }

    public boolean addSCP(int scpId, String scpClass, String name, String description) {
        try {
            String sql = "insert into scp_object (id, name, description, object_class) VALUES (?,?,?,?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, scpId);
            st.setString(2, name);
            st.setString(3, description);
            st.setObject(4, scpClass, Types.OTHER);
            st.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.printf("Объект SCP-%d не будет добавлен в таблицу.%n%n", scpId);
            return false;
        }
    }


    private void start() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver не найден.");
            e.printStackTrace();
            return;
        }

        System.out.println("PostgreSQL JDBC Driver успешно подключен.");

        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);

        } catch (SQLException e) {
            System.out.println("Соединение к базе данных установить не получилось.");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("Вы успешно подключились к базе данных.");
        } else {
            System.out.println("Вы не смогли подключиться к базе данных.");
        }
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
