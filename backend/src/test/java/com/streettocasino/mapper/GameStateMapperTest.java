package com.streettocasino.mapper;

import com.streettocasino.dto.GameState;
import com.streettocasino.dto.GameStateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GameStateMapperTest {
    private GameStateMapper gameStateMapper;

    @BeforeEach
    void setUp() {
        gameStateMapper = new GameStateMapper();
    }

    @Test
    void shouldMapGameStateToResponse() {
        GameState gameState = new GameState(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                "Basic Brush",
                1,
                10,
                0,
                BigDecimal.valueOf(15),
                "Better Brush",
                2,
                BigDecimal.valueOf(50)
        );

        GameStateResponse response = gameStateMapper.toResponse(gameState);

        assertEquals(BigDecimal.ZERO, response.player().money());
        assertEquals("Basic Brush", response.player().equippedBrush().brushName());
        assertNull(response.player().equippedBrush().price());
        assertEquals(1, response.player().equippedBrush().brushEfficiency());
        assertEquals(10, response.shoe().shoeDirtLevel());
        assertEquals(0, response.shoe().cleaningProgress());
        assertEquals(10, response.shoe().requiredProgress());
        assertEquals("Better Brush", response.nextUpgrade().brushName());
        assertEquals(BigDecimal.valueOf(50), response.nextUpgrade().price());
    }

    @Test
    void shouldMapGameStateWithNoNextUpgrade() {
        GameState gameState = new GameState(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                "Electric Brush",
                8,
                10,
                0,
                BigDecimal.valueOf(15),
                null,
                null,
                null
        );

        GameStateResponse response = gameStateMapper.toResponse(gameState);

        assertNull(response.nextUpgrade());
    }

    @Test
    void shouldPreserveMonetaryValues() {
        BigDecimal money = new BigDecimal("123456789.123456789");
        BigDecimal rareCoins = new BigDecimal("12.50");
        BigDecimal reputation = new BigDecimal("0.001");
        BigDecimal shoeReward = new BigDecimal("15.75");
        BigDecimal nextBrushPrice = new BigDecimal("200.00");
        GameState gameState = new GameState(
                money,
                rareCoins,
                reputation,
                "Basic Brush",
                1,
                10,
                0,
                shoeReward,
                "Better Brush",
                2,
                nextBrushPrice
        );

        GameStateResponse response = gameStateMapper.toResponse(gameState);

        assertEquals(money, response.player().money());
        assertEquals(rareCoins, response.player().rareCoins());
        assertEquals(reputation, response.player().reputation());
        assertEquals(shoeReward, response.shoe().shoeReward());
        assertEquals(nextBrushPrice, response.nextUpgrade().price());
    }

}