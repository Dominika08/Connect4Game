package game;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GameController {
    private Board board;
    private Player playerYellow;
    private Player playerRed;
    private Player currentPlayer;
    private Scanner scanner;
    private String saveFilePath;
    private DatabaseManager databaseManager;

    public GameController() {
        scanner = new Scanner(System.in);
        databaseManager = new DatabaseManager();
    }

    public void startGame() {
        System.out.println("\nWelcome to Connect4! :D\n");
        System.out.println("Connect4 is a two-player game where players take turns \n" +
                "dropping colored discs into a vertical grid.\n" +
                "The objective is to connect four of your discs in a row, either \n" +
                "HORIZONTALLY, VERTICALLY, or DIAGONALLY. \n" +
                "The first player to achieve this wins the game.\n" +
                "You will be player Yellow and your opponent (computer) will be RED!\n\n" +
                "GOOD LUCK!\n\n\n");
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();

        playerYellow = new Player('Y', playerName);
        playerRed = new Player('R', "Computer");
        currentPlayer = playerYellow;

        board = new Board();


        System.out.print("Enter the name of the file to load the game (or press Enter to start a new game): ");
        String loadFilePath = scanner.nextLine();
        if (!loadFilePath.isEmpty()) {
            File file = new File(loadFilePath);
            if (file.exists()) {
                try {
                    board.loadFromFile(loadFilePath);
                    System.out.println("Game loaded successfully!");
                } catch (IOException e) {
                    System.out.println("Failed to load the game. Starting a new one.");
                }
            } else {
                System.out.println("File does not exist. Starting a new game.");
            }
        }

        System.out.print("Enter the name of the file to save the game: ");
        saveFilePath = scanner.nextLine();

        while (true) {
            board.printBoard();

            int column;
            if (currentPlayer == playerYellow) {
                column = playerYellow.chooseColumn(board);
            } else {
                column = new Random().nextInt(Board.COLS);
                System.out.println("Computer chooses column: " + (char) ('A' + column));
            }

            if (board.dropPiece(column, currentPlayer.getSymbol())) {
                if (board.checkWin(currentPlayer.getSymbol())) {
                    board.printBoard();
                    System.out.println(currentPlayer.getName() + " wins!");

                    databaseManager.addWin(currentPlayer.getName());

                    saveGame();
                    break;
                }
                switchPlayer();
                saveGame();
            } else {
                System.out.println("Column full! Try again.");
            }
        }


        databaseManager.printPlayerStats();
    }

    private void saveGame() {
        try {
            board.saveToFile(saveFilePath);
            System.out.println("Game saved successfully!");
        } catch (IOException e) {
            System.out.println("Failed to save the game.");
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == playerYellow) ? playerRed : playerYellow;
    }
}
