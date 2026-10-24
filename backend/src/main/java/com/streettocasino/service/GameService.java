package com.streettocasino.service;

import com.streettocasino.dto.GameState;
import com.streettocasino.model.*;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manages game sessions and coordinates the core gameplay components.
 * Provides operations for starting games, cleaning shoes,
 * purchasing brush upgrades and retrieving the current game state.
 *
 * Game sessions are stored in memory and identified by unique IDs.
 */
@Service
public class GameService {
    private final ShoeCatalog shoeCatalog = new ShoeCatalog();
    private final BrushCatalog brushCatalog = new BrushCatalog();
    private final BrushShop brushShop = new BrushShop(brushCatalog);

    private final Map<UUID, GameContext> gameSessionMap = new ConcurrentHashMap<>();

    /**
     * Creates a new game session with a player and the required
     * gameplay components.
     *
     * @return the unique identifier of the new game session
     */
    public UUID startGame() {
        Player player = new Player();
        Gameplay gameplay = new Gameplay(player);
        ShoeGenerator shoeGenerator = new ShoeGenerator(shoeCatalog);
        GameSession gameSession = new GameSession(gameplay, shoeGenerator);
        GameContext gameContext = new GameContext(player, gameSession);

        UUID sessionId = UUID.randomUUID();
        gameSessionMap.put(sessionId, gameContext);

        return sessionId;
    }

    /**
     * Performs one cleaning action in the specified game session.
     *
     * @param sessionId the identifier of the game session
     * @throws IllegalArgumentException if the session ID is null
     *                                  or the session does not exist
     */
    public void clean(UUID sessionId) {
        GameContext gameContext = getContext(sessionId);
        gameContext.gameSession().clean();
    }

    /**
     * Attempts to purchase the next brush upgrade for the specified player.
     *
     * @param sessionId the identifier of the game session
     * @return true if the upgrade was purchased, false otherwise
     * @throws IllegalArgumentException if the session ID is null
     *                                  or the session does not exist
     */
    public boolean buyBrushUpgrade(UUID sessionId) {
        GameContext gameContext = getContext(sessionId);
        Player player = gameContext.player();
        return brushShop.upgradeBrush(player);
    }

    /**
     * Retrieves a snapshot of the current game state.
     * Includes player resources, cleaning progress and the next
     * available brush upgrade.
     *
     * @param sessionId the identifier of the game session
     * @return the current game state
     * @throws IllegalArgumentException if the session ID is null
     *                                  or the session does not exist
     */
    public GameState getGameState(UUID sessionId) {
        GameContext context = getContext(sessionId);

        Player player = context.player();
        GameSession session = context.gameSession();

        Brush currentBrush = player.getBrush();
        Shoe currentShoe = session.getCurrentShoe();
        BrushOffer nextUpgrade = brushCatalog.getNextUpgrade(currentBrush);

        return new GameState(
                player.getMoney(),
                player.getRareCoins(),
                player.getReputation(),
                currentBrush.getBrushName(),
                currentBrush.getBrushEfficiency(),
                currentShoe.getDirtLevel(),
                session.getProgress(),
                currentShoe.getMoneyReward(),
                nextUpgrade != null ? nextUpgrade.brush().getBrushName() : null,
                nextUpgrade != null ? nextUpgrade.brush().getBrushEfficiency() : null,
                nextUpgrade != null ? nextUpgrade.price() : null
        );
    }

    /**
     * Retrieves the game context associated with a session identifier.
     *
     * @param sessionId the identifier of the game session
     * @return the corresponding game context
     * @throws IllegalArgumentException if the session ID is null
     *                                  or the session does not exist
     */
    private GameContext getContext(UUID sessionId) {
        if (sessionId == null) {
            throw new IllegalArgumentException("Session ID is null");
        }
        GameContext context = gameSessionMap.get(sessionId);
        if (context == null) {
            throw new IllegalArgumentException("Session ID is not found");
        }
        return context;
    }

}
