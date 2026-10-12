package com.streettocasino.model;

import java.util.List;
import java.util.Random;

/**
 * Generates random shoes using predefined configurations from a shoe catalog.
 *
 * <p>Each generation randomly selects a shoe configuration
 * and creates a new Shoe instance with the same dirt level
 * and money reward.</p>
 */
public class ShoeGenerator {
    private final List<Shoe> shoeList;
    private final Random random = new Random();

    /**
     * Creates a shoe generator using the given catalog.
     *
     * @param shoeCatalog the catalog containing available shoe configurations
     * @throws IllegalArgumentException if the shoe catalog is null
     */
    public ShoeGenerator(ShoeCatalog shoeCatalog) {
        if (shoeCatalog == null) {
            throw new IllegalArgumentException("Shoe catalog is null");
        }
        this.shoeList = shoeCatalog.getShoeList();
    }

    /**
     * Generates a new shoe from a randomly selected catalog configuration.
     *
     * <p>Each call returns a new Shoe instance, even if the same
     * configuration is selected multiple times.</p>
     *
     * @return a newly generated shoe
     */
    public Shoe generateShoe() {
        int shoeIndex = random.nextInt(shoeList.size());
        Shoe selectedShoe = shoeList.get(shoeIndex);

        return new Shoe(
                selectedShoe.getDirtLevel(),
                selectedShoe.getMoneyReward()
        );
    }

}
