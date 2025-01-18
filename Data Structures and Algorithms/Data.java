public class Data {
    private String config; // Represents the board configuration as a string
    private int score;     // Score associated with the configuration

    /**
     * Constructs a Data object with the specified configuration and score.
     *
     * @param config the string representing the board configuration.
     * @param score the score associated with this configuration.
     */
    public Data(String config, int score) {
        this.config = config;
        this.score = score;
    }

    /**
     * Retrieves the board configuration string.
     *
     * @return the board configuration as a string.
     */
    public String getConfiguration() {
        return this.config;
    }

    /**
     * Retrieves the score associated with this configuration.
     *
     * @return the configuration score.
     */
    public int getScore() {
        return this.score;
    }
}
