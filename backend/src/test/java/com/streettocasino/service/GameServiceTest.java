package com.streettocasino.service;

import com.streettocasino.dto.GameState;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GameServiceTest {

    @Test
    void shouldCreateNewGameSession() {
        GameService gameService = new GameService();
        UUID sessionId = gameService.startGame();
        GameState gameState = gameService.getGameState(sessionId);

        assertNotNull(sessionId);
        assertNotNull(gameState);
        assertEquals("Basic Brush", gameState.brushName());
        assertEquals(1, gameState.brushEfficiency());
        assertEquals(BigDecimal.ZERO, gameState.money());
    }

    @Test
    void shouldCreateIndependentGameSessions() {
        GameService gameService = new GameService();
        UUID sessionIdA = gameService.startGame();
        UUID sessionIdB = gameService.startGame();

        assertNotEquals(sessionIdA, sessionIdB);

        gameService.clean(sessionIdA);

        GameState stateA = gameService.getGameState(sessionIdA);
        GameState stateB = gameService.getGameState(sessionIdB);

        assertEquals(1, stateA.cleaningProgress());
        assertEquals(0, stateB.cleaningProgress());

        earnMoney(gameService, sessionIdA, BigDecimal.valueOf(50));

        stateA = gameService.getGameState(sessionIdA);
        stateB = gameService.getGameState(sessionIdB);

        assertTrue(stateA.money().compareTo(BigDecimal.ZERO) > 0);
        assertEquals(BigDecimal.ZERO, stateB.money());
    }

    @Test
    void shouldCleanExistingGameSession() {
        GameService gameService = new GameService();
        UUID sessionId = gameService.startGame();

        GameState initialState = gameService.getGameState(sessionId);

        gameService.clean(sessionId);

        GameState updatedState = gameService.getGameState(sessionId);

        assertEquals(
                initialState.cleaningProgress() + 1,
                updatedState.cleaningProgress()
        );
    }

    @Test
    void shouldRejectNullSessionId() {
        GameService gameService = new GameService();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gameService.clean(null)
        );

        assertEquals("Session ID is null", exception.getMessage());
    }

    @Test
    void shouldRejectUnknownSessionId() {
        GameService gameService = new GameService();
        UUID sessionId = UUID.randomUUID();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gameService.clean(sessionId)
        );

        assertEquals("Session ID is not found", exception.getMessage());
    }

    @Test
    void shouldPurchaseBrushUpgrade() {
        GameService gameService = new GameService();
        UUID sessionId = gameService.startGame();

        BigDecimal upgradePrice = BigDecimal.valueOf(50);
        earnMoney(gameService, sessionId, upgradePrice);

        GameState before = gameService.getGameState(sessionId);

        boolean purchased = gameService.buyBrushUpgrade(sessionId);

        GameState after = gameService.getGameState(sessionId);

        assertTrue(purchased);
        assertEquals("Better Brush", after.brushName());
        assertEquals(2, after.brushEfficiency());

        assertEquals(
                0,
                before.money().subtract(upgradePrice)
                        .compareTo(after.money())
        );
    }

    @Test
    void shouldRejectBrushUpgradeWhenInsufficientMoney() {
        GameService gameService = new GameService();
        UUID sessionId = gameService.startGame();

        GameState before = gameService.getGameState(sessionId);

        boolean purchased = gameService.buyBrushUpgrade(sessionId);

        GameState after = gameService.getGameState(sessionId);

        assertFalse(purchased);
        assertEquals(before.money(), after.money());
        assertEquals(before.brushName(), after.brushName());
        assertEquals(before.brushEfficiency(), after.brushEfficiency());
    }

    @Test
    void shouldReturnCorrectInitialGameState() {
        GameService gameService = new GameService();
        UUID sessionId = gameService.startGame();

        GameState gameState = gameService.getGameState(sessionId);

        assertEquals("Basic Brush", gameState.brushName());
        assertEquals(1, gameState.brushEfficiency());

        assertEquals(BigDecimal.ZERO, gameState.money());
        assertEquals(BigDecimal.ZERO, gameState.rareCoins());
        assertEquals(BigDecimal.ZERO, gameState.reputation());

        assertTrue(gameState.shoeDirtLevel() > 0);
        assertEquals(0, gameState.cleaningProgress());
        assertTrue(gameState.shoeReward().compareTo(BigDecimal.ZERO) > 0);

        assertEquals("Better Brush", gameState.nextBrushName());
        assertEquals(2,gameState.nextBrushEfficiency());
        assertEquals(BigDecimal.valueOf(50), gameState.nextBrushPrice());
    }

    @Test
    void shouldUpdateGameStateAfterCleaning() {
        GameService gameService = new GameService();
        UUID sessionId = gameService.startGame();

        GameState before = gameService.getGameState(sessionId);

        gameService.clean(sessionId);

        GameState after = gameService.getGameState(sessionId);

        assertEquals(before.cleaningProgress() + 1, after.cleaningProgress());

        assertEquals(before.shoeDirtLevel(), after.shoeDirtLevel());
        assertEquals(before.shoeReward(), after.shoeReward());
        assertEquals(before.money(), after.money());
    }

    @Test
    void shouldUpdateGameStateAfterBrushUpgrade() {
        GameService gameService = new GameService();
        UUID sessionId = gameService.startGame();

        earnMoney(gameService, sessionId, BigDecimal.valueOf(50));

        GameState before = gameService.getGameState(sessionId);

        assertTrue(gameService.buyBrushUpgrade(sessionId));

        GameState after = gameService.getGameState(sessionId);

        assertEquals("Better Brush", after.brushName());
        assertEquals(2, after.brushEfficiency());

        assertEquals("Professional Brush", after.nextBrushName());
        assertEquals(4, after.nextBrushEfficiency());
        assertEquals(BigDecimal.valueOf(200), after.nextBrushPrice());

        assertEquals(
                0,
                before.money().subtract(BigDecimal.valueOf(50)).compareTo(after.money())
        );
    }

    @Test
    void shouldHandleNoMoreBrushUpgrades() {
        GameService gameService = new GameService();
        UUID sessionId = gameService.startGame();

        earnMoney(gameService, sessionId, BigDecimal.valueOf(50));
        assertTrue(gameService.buyBrushUpgrade(sessionId));

        earnMoney(gameService, sessionId, BigDecimal.valueOf(200));
        assertTrue(gameService.buyBrushUpgrade(sessionId));

        earnMoney(gameService, sessionId, BigDecimal.valueOf(1000));
        assertTrue(gameService.buyBrushUpgrade(sessionId));

        GameState maxState = gameService.getGameState(sessionId);

        assertEquals("Electric Brush", maxState.brushName());
        assertEquals(8, maxState.brushEfficiency());

        assertNull(maxState.nextBrushName());
        assertNull(maxState.nextBrushEfficiency());
        assertNull(maxState.nextBrushPrice());

        assertFalse(gameService.buyBrushUpgrade(sessionId));

        GameState after = gameService.getGameState(sessionId);

        assertEquals(maxState.money(), after.money());
        assertEquals(maxState.brushName(), after.brushName());
    }

    /**
     * Performs cleaning actions until the player has at least
     * the specified amount of money.
     *
     * @param gameService the service managing the game
     * @param sessionId   the session to clean shoes in
     * @param target      the minimum money balance to reach
     */
    private void earnMoney(GameService gameService, UUID sessionId, BigDecimal target) {
        int maxActions = 10_000;

        for (int i = 0; i < maxActions; i++) {
            GameState state = gameService.getGameState(sessionId);

            if (state.money().compareTo(target) >= 0) {
                return;
            }

            gameService.clean(sessionId);
        }

        fail("Player did not reach the target money balance");
    }
}
