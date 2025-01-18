public class Configurations {
    private char[][] board; // Game board represented as a 2D char array
    private int boardSize; // Size of the board (boardSize x boardSize)
    private int lengthToWin; // Sequence length required to win
    private int maxLevels; // Max depth for game tree exploration

    public Configurations(int boardSize, int lengthToWin, int maxLevels) {
        this.boardSize = boardSize;
        this.lengthToWin = lengthToWin;
        this.maxLevels = maxLevels;
        this.board = new char[boardSize][boardSize];

        // Initialize all board positions with a space character
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = ' ';
            }
        }
    }

    /**
     * Creates and returns a new HashDictionary with a specified size.
     * This size should be a prime number between 6000 and 10000.
     *
     * @return a HashDictionary with an initial size of 7000.
     */
    public HashDictionary createDictionary() {
        return new HashDictionary(7000);
    }

    /**
     * Checks if the current board configuration exists in the dictionary.
     * Returns the associated score if found, or -1 if not found.
     *
     * @param hashTable the dictionary to search in.
     * @return the score of the configuration if it exists, or -1 otherwise.
     */
    public int repeatedConfiguration(HashDictionary hashTable) {
        String config = boardToString();
        return hashTable.get(config);
    }

    /**
     * Adds the current board configuration and its score to the dictionary.
     *
     * @param hashTable the dictionary to add to.
     * @param score the score associated with the configuration.
     */
    public void addConfiguration(HashDictionary hashTable, int score) {
        String config = boardToString();
        try {
            hashTable.put(new Data(config, score));
        } catch (DictionaryException e) {
            e.printStackTrace(); // Print error if configuration already exists
        }
    }

    /**
     * Saves a play on the board by placing the specified symbol at given coordinates.
     *
     * @param row the row on the board.
     * @param col the column on the board.
     * @param symbol the symbol to place ('X' or 'O').
     */
    public void savePlay(int row, int col, char symbol) {
        board[row][col] = symbol;
    }

    /**
     * Checks if the specified board square is empty.
     *
     * @param row the row of the square.
     * @param col the column of the square.
     * @return true if the square is empty, false otherwise.
     */
    public boolean squareIsEmpty(int row, int col) {
        return board[row][col] == ' ';
    }

    /**
     * Checks if there is a winning sequence for the specified symbol.
     *
     * @param symbol the symbol to check for a win ('X' or 'O').
     * @return true if there is a winning sequence, false otherwise.
     */
    public boolean wins(char symbol) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (checkWinInDirection(i, j, 0, 1, symbol) ||  // Horizontal
                    checkWinInDirection(i, j, 1, 0, symbol) ||  // Vertical
                    checkWinInDirection(i, j, 1, 1, symbol) ||  // Diagonal top-left to bottom-right
                    checkWinInDirection(i, j, 1, -1, symbol)) { // Diagonal top-right to bottom-left
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the game is a draw, meaning there are no empty spaces left and no winners.
     *
     * @return true if the game is a draw, false otherwise.
     */
    public boolean isDraw() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return !wins('X') && !wins('O');
    }

    /**
     * Evaluates the board state and returns an integer representing the game outcome.
     *
     * @return 3 if the computer has won, 0 if the human player has won,
     *         2 if the game is a draw, or 1 if the game is still undecided.
     */
    public int evalBoard() {
        if (wins('O')) {
            return 3; // Computer wins
        } else if (wins('X')) {
            return 0; // Human wins
        } else if (isDraw()) {
            return 2; // Game is a draw
        } else {
            return 1; // Game is undecided
        }
    }

    /**
     * Uses a recursive method to evaluate the best move for the computer
     * up to a maximum depth specified by maxLevels.
     *
     * @param depth the current depth of the recursion.
     * @param symbol the current player symbol ('X' for human, 'O' for computer).
     * @return an evaluation score based on the board state.
     */
    public int evaluateMove(int depth, char symbol) {
        if (depth >= maxLevels || isDraw() || wins('X') || wins('O')) {
            return evalBoard(); // Evaluate the board at maximum depth or terminal state
        }

        int bestScore = (symbol == 'O') ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (squareIsEmpty(i, j)) {
                    savePlay(i, j, symbol);

                    int score = evaluateMove(depth + 1, (symbol == 'O') ? 'X' : 'O');

                    if (symbol == 'O') {
                        bestScore = Math.max(score, bestScore);
                    } else {
                        bestScore = Math.min(score, bestScore);
                    }

                    savePlay(i, j, ' '); // Undo move
                }
            }
        }
        return bestScore;
    }

    /**
     * Converts the board to a string representation for storage in the dictionary.
     *
     * @return a string representing the current board configuration.
     */
    private String boardToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                sb.append(board[i][j]);
            }
        }
        return sb.toString();
    }

    /**
     * Generalized helper method to check for a winning sequence in any direction.
     *
     * @param row the starting row.
     * @param col the starting column.
     * @param rowDir the row direction increment.
     * @param colDir the column direction increment.
     * @param symbol the symbol to check for ('X' or 'O').
     * @return true if a winning sequence is found, false otherwise.
     */
    private boolean checkWinInDirection(int row, int col, int rowDir, int colDir, char symbol) {
        int count = 0;
        for (int k = 0; k < lengthToWin; k++) {
            int newRow = row + k * rowDir;
            int newCol = col + k * colDir;
            if (newRow >= 0 && newRow < boardSize && newCol >= 0 && newCol < boardSize &&
                board[newRow][newCol] == symbol) {
                count++;
            } else {
                break;
            }
        }
        return count == lengthToWin;
    }
}
