package WordSearchGame;

import java.util.Random;

public class RandomWordPlacementStrategy implements WordPlacementStrategy{
   private Random random;

    public RandomWordPlacementStrategy() {
        random = new Random();
    }

    @Override
    public void placeWord(char[][] grid, String word) {
        int direction = random.nextInt(4);
        int length = word.length();
        boolean placed = false;

        while (!placed) {
            int row = random.nextInt(10);
            int col = random.nextInt(10);

            switch (direction) {
                case 0: // Horizontal right
                    if (col + length <= 10 && canPlaceWord(grid, row, col, length, 0)) {
                        placeWordInGrid(grid, word, row, col, 0);
                        placed = true;
                    }
                    break;
                case 1: // Horizontal left
                    if (col - length >= -1 && canPlaceWord(grid, row, col, length, 1)) {
                        placeWordInGrid(grid, word, row, col, 1);
                        placed = true;
                    }
                    break;
                case 2: // Vertical down
                    if (row + length <= 10 && canPlaceWord(grid, row, col, length, 2)) {
                        placeWordInGrid(grid, word, row, col, 2);
                        placed = true;
                    }
                    break;
                case 3: // Vertical up
                    if (row - length >= -1 && canPlaceWord(grid, row, col, length, 3)) {
                        placeWordInGrid(grid, word, row, col, 3);
                        placed = true;
                    }
                    break;
            }
        }
    }
    private boolean canPlaceWord(char[][] grid, int row, int col, int length, int direction) {
        switch (direction) {
            case 0: // Horizontal right
                for (int i = 0; i < length; i++) {
                    if (grid[row][col + i] != ' ') {
                        return false;
                    }
                }
                break;
            case 1: // Horizontal left
                for (int i = 0; i < length; i++) {
                    if (grid[row][col - i] != ' ') {
                        return false;
                    }
                }
                break;
            case 2: // Vertical down
                for (int i = 0; i < length; i++) {
                    if (grid[row + i][col] != ' ') {
                        return false;
                    }
                }
                break;
            case 3: // Vertical up
                for (int i = 0; i < length; i++) {
                    if (grid[row - i][col] != ' ') {
                        return false;
                    }
                }
                break;
        }
        return true;
    }

    private void placeWordInGrid(char[][] grid, String word, int row, int col, int direction) {
        switch (direction) {
            case 0: // Horizontal right
                for (int i = 0; i < word.length(); i++) {
                    grid[row][col + i] = word.charAt(i);
                }
                break;
            case 1: // Horizontal left
                for (int i = 0; i < word.length(); i++) {
                    grid[row][col - i] = word.charAt(i);
                }
                break;
            case 2: // Vertical down
                for (int i = 0; i < word.length(); i++) {
                    grid[row + i][col] = word.charAt(i);
                }
                break;
            case 3: // Vertical up
                for (int i = 0; i < word.length(); i++) {
                    grid[row - i][col] = word.charAt(i);
                }
                break;
        }
   }
}
