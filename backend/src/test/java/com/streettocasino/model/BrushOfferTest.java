package com.streettocasino.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BrushOfferTest {
    @Test
    void shouldRejectNullBrush() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new BrushOffer(null, BigDecimal.ONE)
        );
        assertEquals("Brush is null", exception.getMessage());
    }

    @Test
    void shouldRejectNullPrice() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new BrushOffer(new Brush("test",1), null)
        );
        assertEquals("Price must be non-null and non-negative", exception.getMessage());
    }

    @Test
    void shouldRejectNegativePrice() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new BrushOffer(new Brush("test",1), BigDecimal.ONE.negate())
        );
        assertEquals("Price must be non-null and non-negative", exception.getMessage());
    }

    @Test
    void shouldAcceptZeroPrice() {
        Brush brush = new Brush("test", 1);
        BrushOffer offer = new BrushOffer(brush, BigDecimal.ZERO);

        assertEquals(brush, offer.brush());
        assertEquals(BigDecimal.ZERO, offer.price());
    }
}
