package com.streettocasino.model;

import java.math.BigDecimal;

/**
 * Represents a brush available for purchase.
 *
 * @param brush the brush associated with this offer
 * @param price the purchase price of the brush
 */
public record BrushOffer(Brush brush, BigDecimal price) {
}
