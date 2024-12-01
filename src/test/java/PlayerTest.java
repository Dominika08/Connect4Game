package test;

import game.Board;
import game.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    private Player player;

    @BeforeEach
    public void setUp() {
        player = new Player('Y', "Player1");
    }

    @Test
    public void testPlayerInitialization() {
        assertEquals('Y', player.getSymbol(), "The player's symbol should be correctly initialized.");
        assertEquals("Player1", player.getName(), "The player's name should be correctly initialized.");
    }
}
