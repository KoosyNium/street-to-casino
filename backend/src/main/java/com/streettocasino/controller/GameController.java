package com.streettocasino.controller;

import com.streettocasino.dto.GameState;
import com.streettocasino.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * REST controller exposing endpoints for managing game sessions.
 * Provides operations to create games, retrieve game states,
 * clean shoes and purchase brush upgrades.
 * <p>
 * All endpoints are available under /api/games.
 */
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    /**
     * Creates a controller with the required game service.
     *
     * @param gameService the service managing game sessions
     */
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Creates a new game session.
     * <p>
     * POST /api/games
     *
     * @return HTTP 201 Created with the new session UUID
     */
    @PostMapping
    public ResponseEntity<UUID> startNewGame() {
        UUID sessionId = gameService.startGame();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(sessionId);
    }

    /**
     * Performs one cleaning action in the specified game session.
     * <p>
     * POST /api/games/{sessionId}/clean
     *
     * @param sessionId the identifier of the game session
     * @return HTTP 200 OK with the updated game state
     * @throws IllegalArgumentException if the session is invalid
     */
    @PostMapping("/{sessionId}/clean")
    public ResponseEntity<GameState> clean(@PathVariable UUID sessionId) {
        gameService.clean(sessionId);

        GameState gameState = gameService.getGameState(sessionId);

        return ResponseEntity.ok(gameState);
    }

    /**
     * Attempts to purchase the next available brush upgrade.
     * <p>
     * POST /api/games/{sessionId}/brush/upgrade
     *
     * @param sessionId the identifier of the game session
     * @return HTTP 200 OK with the updated game state if the
     * purchase succeeds, or HTTP 409 Conflict with
     * the current game state if the purchase is rejected
     * @throws IllegalArgumentException if the session is invalid
     */
    @PostMapping("/{sessionId}/brush/upgrade")
    public ResponseEntity<GameState> buyBrushUpgrade(@PathVariable UUID sessionId) {

        boolean result = gameService.buyBrushUpgrade(sessionId);
        GameState gameState = gameService.getGameState(sessionId);

        if (result) {
            return ResponseEntity.ok(gameState);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(gameState);
    }

    /**
     * Retrieves the current state of an existing game session.
     * <p>
     * GET /api/games/{sessionId}
     *
     * @param sessionId the identifier of the game session
     * @return HTTP 200 OK with the current game state
     * @throws IllegalArgumentException if the session is invalid
     */
    @GetMapping("/{sessionId}")
    public ResponseEntity<GameState> getGameState(@PathVariable UUID sessionId) {

        GameState gameState = gameService.getGameState(sessionId);

        return ResponseEntity.ok(gameState);
    }
}
