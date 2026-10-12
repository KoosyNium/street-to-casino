package com.streettocasino.model;

import java.math.BigDecimal;
import java.util.List;

/**
 * Represents a catalog of predefined shoes available for generation.
 *
 * <p>The catalog contains a fixed list of shoes, each defined by
 * a dirt level and a money reward.</p>
 */
public class ShoeCatalog {

    private final List<Shoe> shoeList = List.of(
            new Shoe(10, BigDecimal.valueOf(5)),
            new Shoe(25, BigDecimal.valueOf(12)),
            new Shoe(50, BigDecimal.valueOf(30))
    );

    /**
     * Returns the list of available shoe configurations.
     *
     * @return an unmodifiable list of predefined shoes
     */
    public List<Shoe> getShoeList() {
        return shoeList;
    }
}
