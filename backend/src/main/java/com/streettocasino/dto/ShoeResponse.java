package com.streettocasino.dto;

import java.math.BigDecimal;

/**
 * Represents the current shoe and its cleaning state.
 *
 * @param shoeDirtLevel the dirt level of the current shoe
 * @param shoeReward the money earned when cleaning is completed
 * @param cleaningProgress the current cleaning progress
 * @param requiredProgress the progress required to complete cleaning
 */
public record ShoeResponse (
        int shoeDirtLevel,
        BigDecimal shoeReward,
        int cleaningProgress,
        int requiredProgress
) {
}
