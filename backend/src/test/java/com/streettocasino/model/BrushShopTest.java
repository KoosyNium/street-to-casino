package com.streettocasino.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class BrushShopTest {
    private final BrushCatalog brushCatalog = new BrushCatalog();
    private final BrushShop brushShop = new BrushShop(brushCatalog);

    private static final Brush basicBrush = new Brush("Basic Brush", 1);
    private static final Brush betterBrush = new Brush("Better Brush", 2);
    private static final Brush professionalBrush = new Brush("Professional Brush", 4);
    private static final Brush electricBrush = new Brush("Electric Brush", 8);

    @Test
    void shouldRejectNullCatalog() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new BrushShop(null)
        );
        assertEquals("Brush catalog is null", exception.getMessage());
    }

    @Test
    void shouldRejectNullPlayer() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> brushShop.upgradeBrush(null)
        );
        assertEquals("player is null", exception.getMessage());
    }

    @Test
    void shouldUpgradeBrushWhenEnoughMoney() {
        Player player = new Player();

        player.addMoney(BigDecimal.valueOf(60));

        assertTrue(brushShop.upgradeBrush(player));
        assertEquals(BigDecimal.valueOf(10), player.getMoney());
        assertEquals(betterBrush, player.getBrush());
    }

    @Test
    void shouldUpgradeBrushWithExactMoney() {
        Player player = new Player();

        player.addMoney(BigDecimal.valueOf(50));

        assertTrue(brushShop.upgradeBrush(player));
        assertEquals(BigDecimal.ZERO, player.getMoney());
        assertEquals(betterBrush, player.getBrush());
    }

    @Test
    void shouldNotUpgradeBrushWhenNotEnoughMoney() {
        Player player = new Player();

        player.addMoney(BigDecimal.valueOf(30));

        assertFalse(brushShop.upgradeBrush(player));
        assertEquals(BigDecimal.valueOf(30), player.getMoney());
        assertEquals(basicBrush, player.getBrush());
    }

    @Test
    void shouldNotUpgradeWhenMaxLevelReached() {
        Player player = new Player();

        player.changeBrush(electricBrush);
        player.addMoney(BigDecimal.valueOf(30));

        assertFalse(brushShop.upgradeBrush(player));
        assertEquals(BigDecimal.valueOf(30), player.getMoney());
        assertEquals(electricBrush, player.getBrush());
    }

    @Test
    void shouldAllowMultipleUpgrades() {
        Player player = new Player();

        player.addMoney(BigDecimal.valueOf(250));

        assertTrue(brushShop.upgradeBrush(player));
        assertTrue(brushShop.upgradeBrush(player));
        assertEquals(BigDecimal.ZERO, player.getMoney());
        assertEquals(professionalBrush, player.getBrush());
    }

    @Test
    void shouldRejectUnknownPlayerBrush() {
        Player player = new Player();
        Brush wrongBrush = new Brush("Wrong Brush", 1);
        player.changeBrush(wrongBrush);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> brushShop.upgradeBrush(player)
        );
        assertEquals("Current Brush is not in the catalog", exception.getMessage());
    }
}
