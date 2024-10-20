package game;

import java.util.Scanner;

public class Player {
    private char symbol;
    private String name;
    private Scanner scanner;

    public Player(char symbol, String name) {
        this.symbol = symbol;
        this.name = name;
        this.scanner = new Scanner(System.in);
    }

    public char getSymbol() {
        return symbol;
    }

    public String getName() {
        return name;
    }

    public int chooseColumn(Board board) {
        while (true) {
            System.out.print(name + " (" + symbol + "), choose a column (A-G): ");
            String columnInput = scanner.nextLine().toUpperCase();
            int column = columnInput.charAt(0) - 'A';

            if (column >= 0 && column < board.getCols()) {
                return column;
            } else {
                System.out.println("Invalid column. Try again.");
            }
        }
    }
}
