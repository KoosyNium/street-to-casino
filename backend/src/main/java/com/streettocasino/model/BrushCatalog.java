package com.streettocasino.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a catalog of predefined brush upgrades.
 *
 * <p>Each offer contains a brush and its purchase price.
 * Offers are ordered by upgrade progression.</p>
 */
public class BrushCatalog {
    private final List<BrushOffer> offers = List.of(
            new BrushOffer(new Brush("Basic Brush", 1), BigDecimal.ZERO),
            new BrushOffer(new Brush("Better Brush", 2), BigDecimal.valueOf(50)),
            new BrushOffer(new Brush("Professional Brush", 4), BigDecimal.valueOf(200)),
            new BrushOffer(new Brush("Electric Brush", 8), BigDecimal.valueOf(1000))
    );


    /**
     * Returns the list of available brush upgrades.
     *
     * @return an unmodifiable list of predefined brush upgrades
     */
    public List<BrushOffer> getOffers() {
        return offers;
    }

    /**
     * Returns the next available brush upgrade for the given brush.
     *
     * <p>Upgrades follow the order defined in the catalog.</p>
     *
     * @param currentBrush the currently equipped brush
     * @return the next brush offer, or null if the current brush is the last upgrade
     * @throws IllegalArgumentException if the current brush is not in the catalog
     */
    public BrushOffer getNextUpgrade(Brush currentBrush) {
        for (int i = 0; i < offers.size(); i++) {
            if (offers.get(i).brush().equals(currentBrush)) {
                if (i+1 < offers.size()) {
                    return offers.get(i+1);
                }
                return  null;
            }
        }
        throw new IllegalArgumentException("Current Brush is not in the catalog");
    }
}
