package com.streettocasino.mapper;

import com.streettocasino.dto.*;
import org.springframework.stereotype.Component;

/**
 * Maps internal game state data to REST API response DTOs.
 * Converts game information into a structured representation
 * without exposing domain model objects.
 */
@Component
public class GameStateMapper {

    /**
     * Converts an internal game state into a response DTO.
     * Maps player resources, equipped brush, current shoe
     * and the next available brush upgrade.
     *
     * @param gameState the internal game state to convert
     * @return the corresponding game state response
     */
    public GameStateResponse toResponse(GameState gameState) {
        BrushResponse equippedBrush = new BrushResponse(
                gameState.brushName(),
                gameState.brushEfficiency(),
                null
        );
        BrushResponse nextBrush = null;
        if (gameState.nextBrushName() != null) {
            nextBrush = new BrushResponse(
                    gameState.nextBrushName(),
                    gameState.nextBrushEfficiency(),
                    gameState.nextBrushPrice()
            );
        }
        PlayerResponse player = new PlayerResponse(
                gameState.money(),
                gameState.rareCoins(),
                gameState.reputation(),
                equippedBrush
        );
        ShoeResponse shoeResponse = new ShoeResponse(
                gameState.shoeDirtLevel(),
                gameState.shoeReward(),
                gameState.cleaningProgress(),
                gameState.shoeDirtLevel()
        );
        return new GameStateResponse(player,shoeResponse,nextBrush);
    }
}
