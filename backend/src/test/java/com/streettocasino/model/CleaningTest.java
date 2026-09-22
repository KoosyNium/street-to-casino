package com.streettocasino.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class CleaningTest {
    @Test
    void shouldStartWithZeroProgress() {
        Shoe shoe = new Shoe(10, BigDecimal.ONE);
        Cleaning cleaning = new Cleaning(shoe);
        assertEquals(0, cleaning.getProgress());
    }

    @Test
    void shouldRejectNullShoe() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Cleaning(null)
        );
        assertEquals("Shoe is null", exception.getMessage());
    }

    @Test
    void shouldRejectZeroEfficiency() {
        Shoe shoe = new Shoe(10, BigDecimal.ONE);
        Cleaning cleaning = new Cleaning(shoe);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cleaning.clean(0)
        );
        assertEquals("efficiency must be greater than 0", exception.getMessage());
    }

    @Test
    void shouldRejectNegativeEfficiency() {
        Shoe shoe = new Shoe(10, BigDecimal.ONE);
        Cleaning cleaning = new Cleaning(shoe);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> cleaning.clean(-5)
        );
        assertEquals("efficiency must be greater than 0", exception.getMessage());
    }

    @Test
    void shouldIncreaseProgressWhenCleaning() {
        Shoe shoe = new Shoe(10, BigDecimal.ONE);
        Cleaning cleaning = new Cleaning(shoe);
        cleaning.clean(1);
        assertEquals(1, cleaning.getProgress());
    }

    @Test
    void shouldAccumulateProgressAfterMultipleCleaningActions() {
        Shoe shoe = new Shoe(10, BigDecimal.ONE);
        Cleaning cleaning = new Cleaning(shoe);
        cleaning.clean(2);
        cleaning.clean(2);
        cleaning.clean(2);
        assertEquals(6, cleaning.getProgress());
    }

    @Test
    void shouldUseDifferentEfficiencies() {
        Shoe shoe = new Shoe(10, BigDecimal.ONE);
        Cleaning cleaning = new Cleaning(shoe);
        cleaning.clean(1);
        cleaning.clean(2);
        cleaning.clean(3);
        assertEquals(6, cleaning.getProgress());
    }

    @Test
    void shouldNotExceedDirtLevel() {
        Shoe shoe = new Shoe(10, BigDecimal.ONE);
        Cleaning cleaning = new Cleaning(shoe);
        cleaning.clean(8);
        cleaning.clean(5);
        assertEquals(10, cleaning.getProgress());
    }

    @Test
    void shouldReturnTrueWhenCompleted() {
        Shoe shoe = new Shoe(10, BigDecimal.ONE);
        Cleaning cleaning = new Cleaning(shoe);
        cleaning.clean(10);
        assertTrue(cleaning.isCompleted());
    }

    @Test
    void shouldReturnFalseWhenNotCompleted() {
        Shoe shoe = new Shoe(10, BigDecimal.ONE);
        Cleaning cleaning = new Cleaning(shoe);
        cleaning.clean(2);
        assertFalse(cleaning.isCompleted());
    }

    @Test
    void shouldNotIncreaseProgressAfterCompletion() {
        Shoe shoe = new Shoe(10, BigDecimal.ONE);
        Cleaning cleaning = new Cleaning(shoe);
        cleaning.clean(10);
        assertEquals(10, cleaning.getProgress());
        cleaning.clean(2);
        assertEquals(10, cleaning.getProgress());
    }


}