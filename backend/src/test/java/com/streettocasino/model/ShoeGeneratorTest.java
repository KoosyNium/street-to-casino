package com.streettocasino.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShoeGeneratorTest {

    @Test
    void shouldRejectNullCatalog(){
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new ShoeGenerator(null)
        );
        assertEquals("Shoe catalog is null", exception.getMessage());
    }

    @Test
    void shouldGenerateShoeFromCatalog(){
        ShoeCatalog catalog = new ShoeCatalog();
        ShoeGenerator generator = new ShoeGenerator(catalog);
        Shoe generatedShoe = generator.generateShoe();

        boolean validShoe = catalog.getShoeList().stream()
                .anyMatch(shoe ->
                        shoe.getDirtLevel() == generatedShoe.getDirtLevel()
                                && shoe.getMoneyReward().compareTo(generatedShoe.getMoneyReward()) == 0
                );
        assertTrue(validShoe);
    }

    @Test
    void shouldGenerateNewShoeInstance(){
        ShoeCatalog catalog = new ShoeCatalog();
        ShoeGenerator generator = new ShoeGenerator(catalog);

        Shoe shoe1 = generator.generateShoe();
        Shoe shoe2 = generator.generateShoe();

        assertNotSame(shoe1, shoe2);
    }

}