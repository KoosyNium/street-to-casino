package com.streettocasino.dto;

import java.math.BigDecimal;

/**
 * Represents brush information exposed by the REST API.
 * Used for both equipped brushes and available upgrades.
 *
 * @param brushName the name of the brush
 * @param brushEfficiency the cleaning efficiency of the brush
 * @param price the upgrade price, or null for an equipped brush
 */
public record BrushResponse (
    String brushName,
    int brushEfficiency,
    BigDecimal price
){
}
