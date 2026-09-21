package com.streettocasino.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ShoeTest {
    @Test
    void shouldHaveDirtLevel() {
        Shoe shoe = new Shoe(10, BigDecimal.valueOf(10));
        assertEquals(10, shoe.getDirtLevel());
    }

    @Test
    void shouldHaveMoneyReward() {
        Shoe shoe = new Shoe(10, BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(10), shoe.getMoneyReward());
    }

    @Test
    void shouldAcceptZeroMoneyReward(){
        Shoe shoe = new Shoe(10, BigDecimal.ZERO);
        assertEquals(BigDecimal.ZERO, shoe.getMoneyReward());
    }

    @Test
    void shouldRejectNullMoneyReward() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Shoe(10, null)
        );
        assertEquals("money reward is null", exception.getMessage());
    }

    @Test
    void shouldRejectNegativeMoneyReward() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Shoe(10, BigDecimal.valueOf(-5))
        );
        assertEquals("money reward is negative", exception.getMessage());
    }

    @Test
    void shouldRejectNegativeDirtLevel() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Shoe(-5, BigDecimal.valueOf(10))
        );
        assertEquals("dirt level must be greater than 0", exception.getMessage());
    }

    @Test
    void shouldRejectZeroDirtLevel() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Shoe(0, BigDecimal.valueOf(10))
        );
        assertEquals("dirt level must be greater than 0", exception.getMessage());
    }
}