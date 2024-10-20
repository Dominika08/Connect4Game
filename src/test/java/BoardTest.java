package test;

import game.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board();
    }

    @Test
    public void testDropPieceValid() {
        assertTrue(board.dropPiece(0, 'Y'), "Dropping a piece in an empty column should succeed.");
        assertEquals('Y', board.getBoard()[5][0], "The piece should be placed at the lowest empty row.");
    }

    @Test
    public void testDropPieceFullColumn() {
        for (int i = 0; i < Board.ROWS; i++) {
            board.dropPiece(0, 'Y');
        }
        assertFalse(board.dropPiece(0, 'R'), "Dropping a piece in a full column should fail.");
    }
}
