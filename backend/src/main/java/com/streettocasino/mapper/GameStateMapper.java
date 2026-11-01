package com.streettocasino.mapper;

import com.streettocasino.dto.*;

public class GameStateMapper {
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
