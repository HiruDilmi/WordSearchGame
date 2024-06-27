package WordSearchGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class SearchWordGUI extends JFrame {
    private WordGrid wordGrid;
    private JPanel gridPanel;
    private List<String> wordsToFind;
    private List<String> wordsFound;
    private Map<String, JLabel> wordsToFindLabels;
    private Map<String, JLabel> wordsFoundLabels;
    private List<JButton> selectedButtons;
    private StringBuilder selectedWord;

    public SearchWordGUI() {
        selectDifficultyLevel();
        initializeUI();
    }

    private void selectDifficultyLevel() {
        String[] difficulties = {"Easy", "Medium", "Hard"};
        String difficulty = (String) JOptionPane.showInputDialog(null, "Choose difficulty level:",
                "Difficulty Selection", JOptionPane.QUESTION_MESSAGE, null, difficulties, difficulties[0]);

        int numWords = 0;
        switch (difficulty) {
            case "Hard":
                numWords = 10;
                break;
            case "Medium":
                numWords = 8;
                break;
            case "Easy":
                numWords = 5;
                break;
        }

        List<String> allWords = new ArrayList<>(List.of(
                "APPLE", "BANANA", "ORANGE", "GRAPES", "MANGO", "PEACH", "CHERRY", "PLUM", "KIWI", "PEAR",
                "LEMON", "MELON", "BERRY", "FIG", "GUAVA", "LYCHEE", "PAPAYA", "DATE", "AVOCADO", "COCONUT",
                "PINEAPPLE", "APRICOT", "KIWI", "TAMARIND", "POMEGRANATE", "CRANBERRY", "MULBERRY", "CLEMENTINE",
                "RAMBUTAN", "DURIAN", "INDIA", "BRAZIL", "CANADA", "MEXICO", "ITALY", "JAPAN", "EGYPT", "GREECE",
                "NORWAY", "SPAIN", "CHINA", "RUSSIA", "GERMANY", "ARGENTINA", "AUSTRALIA", "TURKEY", "VIETNAM",
                "CHILE", "KENYA", "SWEDEN", "TIGER", "LION", "ELEPHANT", "GIRAFFE", "ZEBRA", "MONKEY", "KOALA",
                "PANDA", "RABBIT", "EAGLE", "DOLPHIN", "OWL", "LEOPARD", "PENGUIN", "KANGAROO", "CHEETAH", "SQUIRREL",
                "WALRUS", "HIPPO", "JAGUAR", "SWAN", "ANTELOPE", "CAMEL", "GORILLA", "HEDGEHOG", "LYNX", "OSTRICH",
                "QUOKKA", "RACCOON", "YAK", "PIZZA", "BURGER", "PASTA", "SALAD", "SUSHI", "STEAK", "RICE", "NOODLE",
                "CHEESE", "BREAD", "CAKE", "COOKIE", "SOUP", "TACO", "DONUT", "PASTRY", "SANDWICH", "LASAGNA", "CURRY",
                "CHIPS", "WAFFLE", "POPCORN", "PRETZEL", "MUFFIN", "FRIES", "PUDDING", "PITA", "BAGEL", "CEREAL", "BURRITO"
        ));

        Collections.shuffle(allWords);
        wordsToFind = new ArrayList<>(allWords.subList(0, numWords));
        wordGrid = new WordGrid(wordsToFind);
        wordsFound = new ArrayList<>();
        selectedButtons = new ArrayList<>();
        selectedWord = new StringBuilder();
        wordsToFindLabels = new HashMap<>();
        wordsFoundLabels = new HashMap<>();
    }

    private void initializeUI() {
        setTitle("Word Search Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(new BorderLayout());
        setResizable(false); // Prevent resizing
        setLocationRelativeTo(null); // Center the window

        gridPanel = new JPanel(new GridLayout(10, 10));
        initializeGridPanel();

        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBackground(Color.LIGHT_GRAY);
        sidePanel.setPreferredSize(new Dimension(100, getHeight()));
        initializeWordLists(sidePanel);

        add(gridPanel, BorderLayout.CENTER);
        add(sidePanel, BorderLayout.EAST);

        setVisible(true);
    }

    private void initializeGridPanel() {
        char[][] grid = wordGrid.getGrid();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                JButton button = new JButton(String.valueOf(grid[i][j]));
                button.setBackground(Color.WHITE);
                button.setOpaque(true);
                button.setFocusPainted(false); // Remove focus and border painted effects
                button.setBorderPainted(true);
                button.addActionListener(new GridButtonListener(i, j));

                gridPanel.add(button);
            }
        }
    }

    private void initializeWordLists(JPanel sidePanel) {
        JLabel wordsToFindLabel = new JLabel("Words to find:");
        wordsToFindLabel.setForeground(Color.WHITE);
        sidePanel.add(wordsToFindLabel);

        for (String word : wordsToFind) {
            JLabel label = new JLabel(word);
//            label.setForeground(Color.WHITE);
            wordsToFindLabels.put(word, label);
            sidePanel.add(label);
        }

        sidePanel.add(Box.createVerticalStrut(20));

        JLabel wordsFoundLabel = new JLabel("Words found:");
        wordsFoundLabel.setForeground(Color.WHITE);
        sidePanel.add(wordsFoundLabel);

        for (String word : wordsFound) {
            JLabel label = new JLabel(word);
//            label.setForeground(Color.YELLOW);
            wordsFoundLabels.put(word, label);
            sidePanel.add(label);  // Add found words labels to side panel
        }
    }


    private void updateWordLists() {
        // Hide all labels initially
        for (JLabel label : wordsToFindLabels.values()) {
            label.setVisible(false);
        }
        for (JLabel label : wordsFoundLabels.values()) {
            label.setVisible(false);
        }

        // Show labels for words that are in wordsToFind
        for (String word : wordsToFind) {
            JLabel label = wordsToFindLabels.get(word);
            if (label != null) {
                label.setVisible(true);
            }
        }

        // Clear and re-add labels for wordsFound
        JPanel sidePanel = (JPanel) getContentPane().getComponent(1);

        // Remove existing labels for wordsFound
        for (JLabel label : wordsFoundLabels.values()) {
            sidePanel.remove(label);
        }
        wordsFoundLabels.clear(); // Clear the map

        // Add new labels for current wordsFound
        for (String word : wordsFound) {
            JLabel label = new JLabel(word);
            wordsFoundLabels.put(word, label);
            sidePanel.add(label);
        }

        sidePanel.revalidate(); // Refresh side panel to reflect changes
        sidePanel.repaint();
    }


    private class GridButtonListener implements ActionListener {
        private int row, col;
        private String currentSelectedWord;

        public GridButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
            this.currentSelectedWord = "";
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();

            if (selectedButtons.contains(button)) {
                // Deselect the button
                button.setBackground(Color.WHITE);
                selectedButtons.remove(button);
                selectedWord.delete(0, selectedWord.length() - 1);
            } else {
                // Select the button
                button.setBackground(Color.YELLOW);
                selectedButtons.add(button);
                selectedWord.append(button.getText());
            }

            // Check if the selected word is in the list of words to find
            String word = selectedWord.toString();
            if (wordsToFind.contains(word)) {
                wordsToFind.remove(word); // Remove found words from list
                wordsFound.add(word); // Add to list of found words
                updateWordLists(); // Update the word lists display

                for (JButton btn : selectedButtons) {
                    btn.setBackground(Color.GREEN);
                }
                selectedButtons.clear();
                selectedWord.setLength(0);

                // Check if all words are found
                if (wordsToFind.isEmpty()) {
                    JOptionPane.showMessageDialog(SearchWordGUI.this, "Congratulations! You found all the words.");
                    resetGame(); // Start a new game after congratulatory message
                }
            } else if (selectedButtons.size() > 1 && !isPotentialWord(selectedWord.toString())) {
                // Invalid selection, deselect all buttons
                for (JButton btn : selectedButtons) {
                    btn.setBackground(Color.WHITE);
                }
                selectedButtons.clear();
                selectedWord.setLength(0);
            }
        }
    }

    private boolean isPotentialWord(String word) {
        for (String w : wordsToFind) {
            if (w.startsWith(word) || w.endsWith(word)) {
                return true;
            }
        }
        return false;
    }

    private void resetGame() {
        wordsToFind.clear();
        wordsFound.clear();
        selectedButtons.clear();
        selectedWord.setLength(0);

        selectDifficultyLevel(); // Reset game with a new set of words

        // Clear and reinitialize the grid panel
        gridPanel.removeAll();
        initializeGridPanel();

        // Clear and reinitialize the word lists panel
        JPanel sidePanel = (JPanel) getContentPane().getComponent(1); // Assuming side panel is the second component
        sidePanel.removeAll();
        initializeWordLists(sidePanel);

        // Refresh the UI
        revalidate();
        repaint();
    }

}

