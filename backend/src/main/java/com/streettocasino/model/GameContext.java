package com.streettocasino.model;

/**
 * Holds the player and gameplay session associated with a game.
 *
 * @param player the player owning the game
 * @param gameSession the session managing shoe cleaning and generation
 */
public record GameContext(Player player, GameSession gameSession) {
    public GameContext {
        if (player == null) {
            throw new NullPointerException("Player cannot be null");
        }
        if (gameSession == null) {
            throw new NullPointerException("GameSession cannot be null");
        }
    }
}
