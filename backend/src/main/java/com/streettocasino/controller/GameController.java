package com.streettocasino.controller;

import com.streettocasino.dto.GameState;
import com.streettocasino.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<UUID> startNewGame() {
        UUID sessionId = gameService.startGame();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sessionId);
    }

    @PostMapping("/{sessionId}/clean")
    public ResponseEntity<GameState> clean(@PathVariable UUID sessionId) {
        gameService.clean(sessionId);

        GameState gameState = gameService.getGameState(sessionId);

        return ResponseEntity.ok(gameState);
    }

    @PostMapping("/{sessionId}/brush/upgrade")
    public ResponseEntity<GameState> buyBrushUpgrade(@PathVariable UUID sessionId) {
        boolean result = gameService.buyBrushUpgrade(sessionId);
        GameState gameState = gameService.getGameState(sessionId);

        if (result) {
            return ResponseEntity.ok(gameState);
        }
        return ResponseEntity.status(HttpStatus.CONFLICT).body(gameState);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<GameState> getGameState(@PathVariable UUID sessionId) {
        GameState gameState = gameService.getGameState(sessionId);
        return ResponseEntity
                .ok(gameState);
    }
}
