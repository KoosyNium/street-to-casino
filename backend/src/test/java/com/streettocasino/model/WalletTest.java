package com.streettocasino.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {

    @Test
    void shouldStartWithZeroMoney() {
        Wallet wallet = new Wallet();
        assertEquals(BigDecimal.ZERO, wallet.getMoney());
    }

    @Test
    void shouldAddMoney() {
        Wallet wallet = new Wallet();
        wallet.addMoney(BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(10), wallet.getMoney());
    }

    @Test
    void shouldSpendMoney() {
        Wallet wallet = new Wallet();
        wallet.addMoney(BigDecimal.valueOf(10));
        wallet.spendMoney(BigDecimal.valueOf(5));
        assertEquals(BigDecimal.valueOf(5), wallet.getMoney());
    }

    @Test
    void shouldNotSpendMoreMoneyThanAvailable() {
        Wallet wallet = new Wallet();
        wallet.addMoney(BigDecimal.valueOf(10));
        wallet.spendMoney(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(10), wallet.getMoney());
    }

    @Test
    void shouldAffordWhenEnoughMoney() {
        Wallet wallet = new Wallet();
        wallet.addMoney(BigDecimal.valueOf(10));
        assertTrue(wallet.canAfford(BigDecimal.valueOf(5)));
    }

    @Test
    void shouldAffordWhenExactMoney() {
        Wallet wallet = new Wallet();
        wallet.addMoney(BigDecimal.valueOf(10));
        assertTrue(wallet.canAfford(BigDecimal.valueOf(10)));
    }

    @Test
    void shouldAffordZeroAmount() {
        Wallet wallet = new Wallet();
        wallet.addMoney(BigDecimal.valueOf(0));
        assertTrue(wallet.canAfford(BigDecimal.valueOf(0)));
    }

    @Test
    void shouldNotAffordWhenNotEnoughMoney() {
        Wallet wallet = new Wallet();
        wallet.addMoney(BigDecimal.valueOf(10));
        assertFalse(wallet.canAfford(BigDecimal.valueOf(20)));
    }

    @Test
    void shouldRejectNegativeAmountWhenCheckingAffordability() {
        Wallet wallet = new Wallet();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> wallet.canAfford(BigDecimal.valueOf(-100))
        );
        assertEquals("amount is negative", exception.getMessage());
    }

    @Test
    void shouldRejectNullAmountWhenCheckingAffordability() {

        Wallet wallet = new Wallet();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> wallet.canAfford(null)
        );
        assertEquals("amount is null", exception.getMessage());
    }

    @Test
    void shouldRejectNegativeMoneyWhenAdding() {
        Wallet wallet = new Wallet();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> wallet.addMoney(BigDecimal.valueOf(-100))
        );
        assertEquals("amount is negative", exception.getMessage());
    }

    @Test
    void shouldRejectNullMoneyWhenAdding() {
        Wallet wallet = new Wallet();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> wallet.addMoney(null)
        );
        assertEquals("amount is null", exception.getMessage());
    }

    @Test
    void shouldRejectNegativeMoneyWhenSpending() {
        Wallet wallet = new Wallet();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> wallet.spendMoney(BigDecimal.valueOf(-100))
        );
        assertEquals("amount is negative", exception.getMessage());
    }

    @Test
    void shouldRejectNullMoneyWhenSpending() {
        Wallet wallet = new Wallet();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> wallet.spendMoney(null)
        );
        assertEquals("amount is null", exception.getMessage());
    }

    @Test
    void shouldStartWithZeroRareCoins() {
        Wallet wallet = new Wallet();
        assertEquals(BigDecimal.ZERO, wallet.getRareCoins());
    }

    @Test
    void shouldAddRareCoins() {
        Wallet wallet = new Wallet();
        wallet.addRareCoins(BigDecimal.valueOf(10));
        assertEquals(BigDecimal.valueOf(10), wallet.getRareCoins());
    }

    @Test
    void shouldSpendRareCoins() {
        Wallet wallet = new Wallet();
        wallet.addRareCoins(BigDecimal.valueOf(10));
        wallet.spendRareCoins(BigDecimal.valueOf(5));
        assertEquals(BigDecimal.valueOf(5), wallet.getRareCoins());
    }

    @Test
    void shouldNotSpendMoreRareCoinsThanAvailable() {
        Wallet wallet = new Wallet();
        wallet.addRareCoins(BigDecimal.valueOf(10));
        wallet.spendRareCoins(BigDecimal.valueOf(50));
        assertEquals(BigDecimal.valueOf(10), wallet.getRareCoins());
    }

    @Test
    void shouldRejectNegativeRareCoinsWhenAdding() {
        Wallet wallet = new Wallet();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> wallet.addRareCoins(BigDecimal.valueOf(-100))
        );
        assertEquals("amount is negative", exception.getMessage());
    }

    @Test
    void shouldRejectNullRareCoinsWhenAdding() {
        Wallet wallet = new Wallet();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> wallet.addRareCoins(null)
        );
        assertEquals("amount is null", exception.getMessage());
    }

    @Test
    void shouldRejectNegativeRareCoinsWhenSpending() {
        Wallet wallet = new Wallet();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> wallet.spendRareCoins(BigDecimal.valueOf(-100))
        );
        assertEquals("amount is negative", exception.getMessage());
    }

    @Test
    void shouldRejectNullRareCoinsWhenSpending() {
        Wallet wallet = new Wallet();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> wallet.spendRareCoins(null)
        );
        assertEquals("amount is null", exception.getMessage());
    }

}