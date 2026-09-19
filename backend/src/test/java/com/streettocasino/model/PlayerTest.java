package com.streettocasino.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    void shouldStartWithZeroMoney() {
        Player player = new Player();
        assertEquals(BigDecimal.ZERO, player.getMoney());
    }

    @Test
    void shouldStartWithZeroRareCoins() {
        Player player = new Player();
        assertEquals(BigDecimal.ZERO, player.getRareCoins());
    }

    @Test
    void shouldStartWithZeroReputation() {
        Player player = new Player();
        assertEquals(BigDecimal.ZERO, player.getReputation());
    }

    @Test
    void shouldStartWithBasicBrush() {
        Player player = new Player();
        assertNotNull(player.getBrush());
    }

    @Test
    void shouldChangeBrush() {
        Player player = new Player();
        Brush brush = new Brush("Brush test", 5);
        player.changeBrush(brush);
        assertEquals(brush, player.getBrush());
    }

    @Test
    void shouldRejectNullBrush() {
        Player player = new Player();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> player.changeBrush(null)
        );
        assertEquals("Brush is null", exception.getMessage());
    }

    @Test
    void shouldAddMoney() {
        Player player = new Player();
        player.addMoney(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100), player.getMoney());
    }

    @Test
    void shouldSpendMoney() {
        Player player = new Player();
        player.addMoney(BigDecimal.valueOf(100));
        player.spendMoney(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(50), player.getMoney());
    }

    @Test
    void shouldAddRareCoins() {
        Player player = new Player();
        player.addRareCoins(BigDecimal.valueOf(100));
        assertEquals(BigDecimal.valueOf(100), player.getRareCoins());
    }

    @Test
    void shouldSpendRareCoins() {
        Player player = new Player();
        player.addRareCoins(BigDecimal.valueOf(100));
        player.spendRareCoins(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(50), player.getRareCoins());
    }

    @Test
    void shouldAddReputation() {
        Player player = new Player();
        player.addReputation(BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(10), player.getReputation());
    }

    @Test
    void shouldSpendReputation() {
        Player player = new Player();
        player.addReputation(BigDecimal.valueOf(10));
        player.spendReputation(BigDecimal.valueOf(8));
        assertEquals(BigDecimal.valueOf(2), player.getReputation());
    }

    @Test
    void shouldNotSpendMoreReputationThanAvailable() {
        Player player = new Player();
        player.addReputation(BigDecimal.valueOf(10));
        player.spendReputation(BigDecimal.valueOf(80));
        assertEquals(BigDecimal.valueOf(10), player.getReputation());
    }

    @Test
    void shouldRejectNegativeReputation() {
        Player player = new Player();
        player.addReputation(BigDecimal.valueOf(100));
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> player.spendReputation(BigDecimal.valueOf(-50))
        );
        assertEquals("amount is negative", exception.getMessage());
    }

    @Test
    void shouldRejectNullReputation() {
        Player player = new Player();
        player.addReputation(BigDecimal.valueOf(100));
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> player.spendReputation(null)
        );
        assertEquals("amount is null", exception.getMessage());
    }
}
