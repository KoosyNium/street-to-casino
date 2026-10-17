package com.streettocasino.model;

/**
 * Manages a game session by coordinating gameplay and shoe generation.
 * Automatically starts a new shoe when the current one is completed.
 */
public class GameSession {

    private final Gameplay gameplay;
    private final ShoeGenerator shoeGenerator;

    /**
     * Creates a game session and generates the first shoe.
     *
     * @param gameplay the gameplay instance managing cleaning and rewards
     * @param shoeGenerator the generator providing new shoes
     * @throws IllegalArgumentException if gameplay or shoeGenerator is null
     */
    public GameSession(Gameplay gameplay, ShoeGenerator shoeGenerator) {
        if (gameplay == null) {
            throw new IllegalArgumentException("Gameplay is null");
        }
        if (shoeGenerator == null) {
            throw new IllegalArgumentException("Shoe Generator is null");
        }

        this.gameplay = gameplay;
        this.shoeGenerator = shoeGenerator;

        gameplay.startNewShoe(shoeGenerator.generateShoe());
    }

    /**
     * Performs one cleaning action.
     * Automatically generates and starts a new shoe when the current
     * shoe is completed.
     */
    public void clean() {
        boolean completed = gameplay.clean();

        if (completed) {
            gameplay.startNewShoe(shoeGenerator.generateShoe());
        }
    }
}
