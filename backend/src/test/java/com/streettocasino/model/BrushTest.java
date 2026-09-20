package com.streettocasino.model;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class BrushTest {
    @Test
    void shouldHaveName() {
        Brush brush = new Brush("Brush test", 5);
        assertEquals("Brush test", brush.getBrushName());
    }

    @Test
    void shouldHaveEfficiency() {
        Brush brush = new Brush("Brush test", 5);
        assertEquals(5, brush.getBrushEfficiency());
    }

    @Test
    void shouldRejectBlankName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Brush("  ", 5)
        );
        assertEquals("Brush name is blank", exception.getMessage());
    }

    @Test
    void shouldRejectNullName() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Brush(null, 5)
        );
        assertEquals("Brush name is null", exception.getMessage());
    }

    @Test
    void shouldRejectNegativeEfficiency() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Brush("Brush test", -5)
        );
        assertEquals("Brush efficiency must be greater than 0", exception.getMessage());
    }

    @Test
    void shouldRejectZeroEfficiency() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Brush("Brush test", 0)
        );
        assertEquals("Brush efficiency must be greater than 0", exception.getMessage());
    }

}