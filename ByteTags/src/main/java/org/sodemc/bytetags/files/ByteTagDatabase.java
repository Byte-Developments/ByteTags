package org.sodemc.bytetags.files;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ByteTagDatabase {

    private static final String DATABASE_URL = "jdbc:sqlite:plugins/ByteTags/test.db";

    public static void createTable() {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS Players (" +
                             "Player TEXT," +
                             "Tag TEXT," +
                             "UUID TEXT)"
             )) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertData(String player, String tag, String uuid) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Players (Player, Tag, UUID) VALUES (?, ?, ?)"
             )) {
            statement.setString(1, player);
            statement.setString(2, tag);
            statement.setString(3, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateData(String newValue, String uuid, String columnName) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Players SET " + columnName + " = ? WHERE UUID = ?"
             )) {
            statement.setString(1, newValue);
            statement.setString(2, uuid);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String findTagByUUID(String uuid) {
        String tag = null;
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT Tag FROM Players WHERE UUID = ?"
             )) {
            statement.setString(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tag = resultSet.getString("Tag");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tag;
    }
}
