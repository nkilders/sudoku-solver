package de.nkilders.sudokusolver;

import java.util.Scanner;

/**
 * @author Noah Kilders
 */
public class SudokuSolver {
    int[][] grid;

    public SudokuSolver() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            grid = new int[9][9];

            System.out.println("Enter your grid row wise, separated by commas");
            System.out.println("For empty fields enter 0");

            for (int i = 0; i < 9; i++) {
                System.out.print((i + 1) + ": ");

                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    if (line == null || line.equals("")) {
                        i--;
                        continue;
                    }

                    String[] args = line.split(",");
                    if (args.length != 9) {
                        System.out.println("You must enter 9 values!");
                        i--;
                        continue;
                    }

                    for (int j = 0; j < 9; j++) {
                        grid[i][j] = Integer.parseInt(args[j]);
                    }
                }
            }

            System.out.println();
            System.out.println("Your input:");
            print();

            solve();
        }
    }

    private void solve() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) {
                    for (int num = 1; num < 10; num++) {
                        if (canBe(i, j, num)) {
                            grid[i][j] = num;
                            solve();
                            grid[i][j] = 0;
                        }
                    }

                    return;
                }
            }
        }

        System.out.println("Solution:");
        print();
    }

    private void print() {
        for (int i = 0; i < 9; i++) {
            System.out.print(i != 0 && i % 3 == 0 ? "------+-------+------\n" : "");

            for (int j = 0; j < 9; j++) {
                if (j != 0) {
                    System.out.print(j % 3 == 0 ? " | " : " ");
                }

                System.out.print(grid[i][j] == 0 ? " " : grid[i][j]);
            }

            System.out.println();
        }

        System.out.println();
    }

    private boolean canBe(int row, int col, int num) {
        // row
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num) {
                return false;
            }
        }

        // col
        for (int i = 0; i < 9; i++) {
            if (grid[i][col] == num) {
                return false;
            }
        }

        // box
        int boxMinRow = (row / 3) * 3;
        int boxMinCol = (col / 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[boxMinRow + i][boxMinCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }
}