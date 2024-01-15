package org.sodemc.bytetags.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckLicense {

    public static boolean isRowActive(Connection connection, String licenseValue) throws SQLException {
        String query = "SELECT * FROM Licenses WHERE License = ? AND Active = 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, licenseValue);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                // If a row is found, return true; otherwise, return false
                return resultSet.next();
            }
        }
    }
}
