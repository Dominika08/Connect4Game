package game;

import java.sql.*;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:sqlite:connect4.db";

    public DatabaseManager() {
        createPlayersTable();
    }


    private void createPlayersTable() {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS players (
                    name TEXT PRIMARY KEY,
                    wins INTEGER DEFAULT 0
                );
                """;

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            System.out.println("Failed to create players table: " + e.getMessage());
        }
    }

    public void addWin(String playerName) {
        String insertOrUpdateSQL = """
                INSERT INTO players (name, wins)
                VALUES (?, 1)
                ON CONFLICT(name) DO UPDATE SET wins = wins + 1;
                """;

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertOrUpdateSQL)) {
            preparedStatement.setString(1, playerName);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Failed to update player data: " + e.getMessage());
        }
    }


    public void printPlayerStats() {
        String querySQL = "SELECT name, wins FROM players ORDER BY wins DESC";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(querySQL)) {
            System.out.println("HIGH SCORE:");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("name") + " - Wins: " + resultSet.getInt("wins"));
            }
        } catch (SQLException e) {
            System.out.println("Failed to fetch player statistics: " + e.getMessage());
        }
    }
}
