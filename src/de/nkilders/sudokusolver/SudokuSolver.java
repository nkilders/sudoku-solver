package de.nkilders.sudokusolver;

import java.util.Scanner;

/**
 * @author Noah Kilders
 */
public class SudokuSolver {
    private int[][] grid;

    public SudokuSolver() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            grid = new int[9][9];

            System.out.println("Enter your grid row wise, separated by commas");
            System.out.println("For empty fields enter 0");

            for (int row = 0; row < 9; row++) {
                System.out.print((row + 1) + ": ");

                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    if (line == null || line.equals("")) {
                        row--;
                        continue;
                    }

                    String[] args = line.split(",");
                    if (args.length != 9) {
                        System.out.println("You must enter 9 values!");
                        row--;
                        continue;
                    }

                    for (int col = 0; col < 9; col++) {
                        grid[row][col] = Integer.parseInt(args[col]);
                    }
                }
            }

            System.out.println();
            System.out.println("Your input:");
            print();

            solve();
        }
    }

    /**
     * Solves the Sudoku saved in {@link SudokuSolver#grid}
     */
    private void solve() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    for (int num = 1; num < 10; num++) {
                        if (canBe(row, col, num)) {
                            grid[row][col] = num;
                            solve();
                            grid[row][col] = 0;
                        }
                    }

                    return;
                }
            }
        }

        System.out.println("Solution:");
        print();
    }

    /**
     * Prints the Sudoku saved in {@link SudokuSolver#grid} to the console
     */
    private void print() {
        for (int row = 0; row < 9; row++) {
            System.out.print(row != 0 && row % 3 == 0 ? "------+-------+------\n" : "");

            for (int col = 0; col < 9; col++) {
                if (col != 0) {
                    System.out.print(col % 3 == 0 ? " | " : " ");
                }

                System.out.print(grid[row][col] == 0 ? " " : grid[row][col]);
            }

            System.out.println();
        }

        System.out.println();
    }

    /**
     * @return {@code true} if the field in row {@code row} and column {@code col} can be {@code num} without any conflicts.<br>
     * Otherwise returns {@code false}
     */
    private boolean canBe(int row, int col, int num) {
        // Check the field's row
        for (int c = 0; c < 9; c++) {
            if (grid[row][c] == num) {
                return false;
            }
        }

        // Check the field's column
        for (int r = 0; r < 9; r++) {
            if (grid[r][col] == num) {
                return false;
            }
        }

        // Check the field's 3x3-square
        int boxMinRow = (row / 3) * 3;
        int boxMinCol = (col / 3) * 3;

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                if (grid[boxMinRow + r][boxMinCol + c] == num) {
                    return false;
                }
            }
        }

        return true;
    }
}