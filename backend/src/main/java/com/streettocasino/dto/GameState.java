package com.streettocasino.dto;

import java.math.BigDecimal;

/**
 * Represents a snapshot of the current game state.
 * Contains player resources, equipped brush information,
 * cleaning progress and the next available brush upgrade.
 *
 * @param money the player's current money balance
 * @param rareCoins the player's rare coin balance
 * @param reputation the player's current reputation
 * @param brushName the name of the equipped brush
 * @param brushEfficiency the efficiency of the equipped brush
 * @param shoeDirtLevel the dirt level required to clean the current shoe
 * @param cleaningProgress the current shoe cleaning progress
 * @param shoeReward the money earned when the current shoe is completed
 * @param nextBrushName the next brush name, or null if no upgrade is available
 * @param nextBrushEfficiency the next brush efficiency, or null if unavailable
 * @param nextBrushPrice the next brush price, or null if unavailable
 */
public record GameState(
        BigDecimal money,
        BigDecimal rareCoins,
        BigDecimal reputation,
        String brushName,
        int brushEfficiency,
        int shoeDirtLevel,
        int cleaningProgress,
        BigDecimal shoeReward,
        String nextBrushName,
        Integer nextBrushEfficiency,
        BigDecimal nextBrushPrice
) {
}