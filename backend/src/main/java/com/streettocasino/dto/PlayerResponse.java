package com.streettocasino.dto;

import java.math.BigDecimal;

/**
 * Represents the player information exposed by the REST API.
 *
 * @param money the player's current money balance
 * @param rareCoins the player's rare coin balance
 * @param reputation the player's current reputation
 * @param equippedBrush the player's currently equipped brush
 */
public record PlayerResponse (
    BigDecimal money,
    BigDecimal rareCoins,
    BigDecimal reputation,
    BrushResponse equippedBrush
) {
}
