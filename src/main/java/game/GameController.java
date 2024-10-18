package game;

import java.util.Random;
import java.util.Scanner;

public class GameController {
    private Board board;
    private Player playerYellow;
    private Player playerRed;
    private Player currentPlayer;
    private Scanner scanner;
    private String playerName;
    private int totalGames;

    public GameController() {
        scanner = new Scanner(System.in);
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

        System.out.print("Enter your username: ");
        playerName = scanner.nextLine();

        totalGames = getNumberOfGames();

        for (int i = 0; i < totalGames; i++) {
            System.out.println("\nStarting Game " + (i + 1) + "!");
            board = new Board();
            playerYellow = new Player('Y');
            playerRed = new Player('R');
            currentPlayer = playerYellow;

            String columnInput;

            System.out.print("Please enter the name of the game state file: ");
            String filePath = scanner.nextLine();

            try {
                System.out.print("Please enter the name (and path) of the file where you would like to save the game state: ");
                String saveFilePath = scanner.nextLine();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            while (true) {
                board.printBoard();
                if (currentPlayer.getSymbol() == 'Y') {
                    System.out.print(playerName + " (Yellow), choose a column (A-G): ");
                    columnInput = scanner.nextLine().toUpperCase();
                    int column = columnInput.charAt(0) - 'A';

                    if (column < 0 || column >= board.getCols() || !board.dropPiece(column, currentPlayer.getSymbol())) {
                        System.out.println("Invalid move. Try again.");
                        continue;
                    }
                } else {
                    System.out.println("Computer (Red) is making a move...");
                    computerMove();
                }

                if (board.checkWin(currentPlayer.getSymbol())) {
                    board.printBoard();
                    System.out.println("Player " + (currentPlayer.getSymbol() == 'Y' ? playerName : "Computer") + " wins!");
                    break;
                }

                if (isBoardFull()) {
                    board.printBoard();
                    System.out.println("It's a TIE!");
                    break;
                }

                switchPlayer();
            }
        }
        scanner.close();
    }

    private int getNumberOfGames() {
        int games = 0;
        while (true) {
            System.out.print("How many games would you like to play (1-5)? ");
            String input = scanner.nextLine();

            try {
                games = Integer.parseInt(input);
                if (games > 0 && games <= 5) {
                    break;
                } else {
                    System.out.println("Please choose a number greater than 0 and less than or equal to 5.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 5.");
            }
        }
        return games;
    }

    private boolean isBoardFull() {
        for (int col = 0; col < board.getCols(); col++) {
            if (board.dropPiece(col, ' ')) {
                return false;
            }
        }
        return true;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == playerYellow) ? playerRed : playerYellow;
    }

    private void computerMove() {
        Random random = new Random();
        int col;
        do {
            col = random.nextInt(board.getCols());
        } while (!board.dropPiece(col, playerRed.getSymbol()));
    }
}