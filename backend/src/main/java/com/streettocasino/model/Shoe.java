package com.streettocasino.model;

import java.math.BigDecimal;

/**
 * Represents a shoe the player needs to clean.
 *
 * <p>Each shoe has a money reward and a dirt level.
 * The dirt level represents how much the shoe needs to be cleaned.
 * The money reward represents how much the player will earn after cleaning the shoe</p>
 */
public class Shoe {
    private final int dirtLevel;
    private final BigDecimal moneyReward;

    public Shoe(int dirtLevel, BigDecimal moneyReward) {
        if (moneyReward == null) {
            throw new IllegalArgumentException("money reward is null");
        }
        if (moneyReward.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("money reward is negative");
        }
        if (dirtLevel <= 0) {
            throw new IllegalArgumentException("dirt level must be greater than 0");
        }
        this.moneyReward = moneyReward;
        this.dirtLevel = dirtLevel;
    }

    /**
     * Returns the current money reward.
     *
     * @return the current money reward
     */
    public BigDecimal getMoneyReward() {
        return moneyReward;
    }

    /**
     * Returns the current dirt level.
     *
     * @return the current dirt level
     */
    public int getDirtLevel() {
        return dirtLevel;
    }
}
