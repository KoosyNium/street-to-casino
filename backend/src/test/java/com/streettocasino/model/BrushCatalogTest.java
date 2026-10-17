package com.streettocasino.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BrushCatalogTest {
    private final BrushCatalog brushCatalog = new BrushCatalog();

    @Test
    void shouldReturnNextUpgrade() {
        Brush basicBrush = new Brush("Basic Brush", 1);
        Brush betterBrush = new Brush("Better Brush", 2);

        BrushOffer brushOffer = brushCatalog.getNextUpgrade(basicBrush);

        assertEquals(betterBrush,brushOffer.brush());
        assertEquals(BigDecimal.valueOf(50),brushOffer.price());

    }

    @Test
    void shouldReturnNextUpgradeFromMiddle() {
        Brush betterBrush = new Brush("Better Brush", 2);
        Brush professionalBrush = new Brush("Professional Brush", 4);

        BrushOffer brushOffer = brushCatalog.getNextUpgrade(betterBrush);

        assertEquals(professionalBrush,brushOffer.brush());
        assertEquals(BigDecimal.valueOf(200),brushOffer.price());
    }

    @Test
    void shouldReturnNullWhenLastBrush() {
        Brush electricBrush = new Brush("Electric Brush", 8);
        assertNull(brushCatalog.getNextUpgrade(electricBrush));
    }

    @Test
    void shouldRejectUnknownBrush() {
        Brush electricBrush = new Brush("Electric Brash", 2);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> brushCatalog.getNextUpgrade(electricBrush)
        );

        assertEquals("Current Brush is not in the catalog", exception.getMessage());
    }

    @Test
    void shouldRejectNullBrush() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> brushCatalog.getNextUpgrade(null)
        );

        assertEquals("Current Brush is not in the catalog", exception.getMessage());
    }
}
