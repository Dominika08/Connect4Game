package test;

import game.DatabaseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class DatabaseManagerTest {
    private DatabaseManager databaseManager;

    @BeforeEach
    public void setUp() {
        databaseManager = new DatabaseManager();


        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:connect4.db");
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM players");
        } catch (SQLException e) {
            fail("Failed to clear the database: " + e.getMessage());
        }
    }

    @Test
    public void testAddWin() {
        databaseManager.addWin("Player1");
        databaseManager.addWin("Player1");


        databaseManager.printPlayerStats();
    }
}
