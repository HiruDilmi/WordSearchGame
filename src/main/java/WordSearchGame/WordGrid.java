package WordSearchGame;

import java.util.List;
import java.util.Random;

public class WordGrid {
    private char[][] grid;
    private RandomWordPlacementStrategy placementStrategy;

    public WordGrid(List<String> words) {
        this.grid = new char[10][10];
        this.placementStrategy = new RandomWordPlacementStrategy();

        initializeGrid();
        placeWords(words);
        fillEmptySpaces();
    }

    private void initializeGrid() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    private void placeWords(List<String> words) {
        for (String word : words) {
            placementStrategy.placeWord(grid, word);
        }
    }

    private void fillEmptySpaces() {
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (grid[i][j] == ' ') {
                    grid[i][j] = (char) ('A' + random.nextInt(26));
                }
            }
        }
    }

    public char[][] getGrid() {
        return grid;
    }
}
