package com.streettocasino.model;

import java.math.BigDecimal;

/**
 * Represents the player and manages the player's main game state.
 *
 * <p>The player owns a wallet and a brush and manages the player's reputation.</p>
 */
public class Player {
    private static final String BRUSH_NAME = "Basic Brush";
    private static final int BRUSH_EFFICIENCY = 1;

    private Brush brush;
    private final Wallet wallet;
    private BigDecimal reputation;

    public Player() {
        this.brush = new Brush(BRUSH_NAME, BRUSH_EFFICIENCY);
        this.wallet = new Wallet();
        this.reputation = BigDecimal.ZERO;
    }


    /**
     * Returns the current brush owned by the player.
     *
     * @return the current brush owned by the player.
     */
    public Brush getBrush() {
        return brush;
    }

    /**
     * Changes the player's current brush.
     *
     * @param newBrush the new brush to equip
     * @throws IllegalArgumentException if the brush is null
     */
    public void changeBrush(Brush newBrush) {
        if (newBrush == null) {
            throw new IllegalArgumentException("Brush is null");
        }
        this.brush = newBrush;
    }

    /**
     * Returns the amount of money in the player's wallet.
     *
     * @return the current money balance
     */
    public BigDecimal getMoney() {
        return wallet.getMoney();
    }

    /**
     * Adds the specified amount of money to the player's wallet.
     *
     * @param amount the amount of money to add
     */
    public void addMoney(BigDecimal amount) {
        wallet.addMoney(amount);
    }

    /**
     * Spends the specified amount of money if the player's wallet has enough funds.
     *
     * @param amount the amount of money to spend
     */
    public void spendMoney(BigDecimal amount) {
        wallet.spendMoney(amount);
    }

    /**
     * Returns the amount of rare coins in the player's wallet.
     *
     * @return the current rare coins balance
     */
    public BigDecimal getRareCoins() {
        return wallet.getRareCoins();
    }

    /**
     * Adds the specified amount of rare coins to the player's wallet.
     *
     * @param amount the amount of rare coins to add
     */
    public void addRareCoins(BigDecimal amount) {
        wallet.addRareCoins(amount);
    }

    /**
     * Spends the specified amount of rare coins if the player's wallet has enough rare coins.
     *
     * @param amount the amount of rare coins to spend
     */
    public void spendRareCoins(BigDecimal amount) {
        wallet.spendRareCoins(amount);
    }

    /**
     * Returns the amount of reputation of the player.
     *
     * @return the amount of reputation
     */
    public BigDecimal getReputation() {
        return reputation;
    }

    /**
     * Spends the specified amount of reputation if the player has enough reputation.
     *
     * @param amount the amount of reputation to spend
     * @throws IllegalArgumentException if the amount is null or negative
     */
    public void spendReputation(BigDecimal amount) {
        if (amount == null){
            throw new IllegalArgumentException("amount is null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount is negative");
        }
        if (this.reputation.compareTo(amount) >= 0) {
            this.reputation = this.reputation.subtract(amount);
        }
    }

    /**
     * Adds the specified amount of reputation to the player.
     *
     * @param amount the amount of reputation to add
     * @throws IllegalArgumentException if the amount is null or negative
     */
    public void addReputation(BigDecimal amount) {
        if (amount == null){
            throw new IllegalArgumentException("amount is null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount is negative");
        }
        this.reputation = this.reputation.add(amount);
    }
}
