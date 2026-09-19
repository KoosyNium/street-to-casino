package com.streettocasino.model;

import java.math.BigDecimal;

/**
 * Manages the player's money and rare coins.
 *
 * <p>The wallet is responsible for validating monetary operations
 * and preventing invalid or negative transactions.</p>
 */
public class Wallet {
    private BigDecimal money;
    private BigDecimal rareCoins;

    public Wallet() {
        this.money = BigDecimal.ZERO;
        this.rareCoins = BigDecimal.ZERO;
    }

    /**
     * Spends the specified amount of money if the wallet has enough funds.
     *
     * @param amount the amount of money to spend
     * @throws IllegalArgumentException if the amount is null or negative
     */
    public void spendMoney(BigDecimal amount) {
        if (amount == null){
            throw new IllegalArgumentException("amount is null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount is negative");
        }
        if (this.money.compareTo(amount) >= 0) {
            this.money = this.money.subtract(amount);
        }
    }

    /**
     * Adds the specified amount of money to the wallet.
     *
     * @param amount the amount of money to add
     * @throws IllegalArgumentException if the amount is null or negative
     */
    public void addMoney(BigDecimal amount) {
        if (amount == null){
            throw new IllegalArgumentException("amount is null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount is negative");
        }
        this.money = this.money.add(amount);
    }

    /**
     * Spends the specified amount of rare coins if the wallet has enough funds.
     *
     * @param amount the amount of rare coins to spend
     * @throws IllegalArgumentException if the amount is null or negative
     */
    public void spendRareCoins(BigDecimal amount) {
        if (amount == null){
            throw new IllegalArgumentException("amount is null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount is negative");
        }
        if (this.rareCoins.compareTo(amount) >= 0) {
            this.rareCoins = this.rareCoins.subtract(amount);
        }
    }

    /**
     * Adds the specified amount of rare coins to the wallet.
     *
     * @param amount the amount of rare coins to add
     * @throws IllegalArgumentException if the amount is null or negative
     */
    public void addRareCoins(BigDecimal amount) {
        if (amount == null){
            throw new IllegalArgumentException("amount is null");
        }
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("amount is negative");
        }
        this.rareCoins = this.rareCoins.add(amount);
    }

    /**
     * Returns the current amount of rare coins in the wallet.
     *
     * @return the current rare coins balance
     */
    public BigDecimal getRareCoins() {
        return rareCoins;
    }

    /**
     * Returns the current amount of money in the wallet.
     *
     * @return the current money balance
     */
    public BigDecimal getMoney() {
        return money;
    }
}
