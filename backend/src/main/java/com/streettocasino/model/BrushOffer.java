package com.streettocasino.model;

import java.math.BigDecimal;

/**
 * Represents a brush available for purchase and its price.
 *
 * @param brush the brush associated with this offer
 * @param price the non-negative purchase price of the brush
 */
public record BrushOffer(Brush brush, BigDecimal price) {
    public BrushOffer {
        if (brush == null) {
            throw new IllegalArgumentException("Brush is null");
        }

        if (price == null || price.signum() < 0) {
            throw new IllegalArgumentException("Price must be non-null and non-negative");
        }
    }
}
