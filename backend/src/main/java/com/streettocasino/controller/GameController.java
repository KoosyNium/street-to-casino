package com.streettocasino.controller;

import com.streettocasino.dto.GameState;
import com.streettocasino.dto.GameStateResponse;
import com.streettocasino.mapper.GameStateMapper;
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
    private  final GameStateMapper gameStateMapper;

    /**
     * Creates a controller with the required game service and state mapper.
     *
     * @param gameService the service managing game sessions
     * @param gameStateMapper the mapper converting game states to response DTOs
     */
    public GameController(GameService gameService, GameStateMapper gameStateMapper) {
        this.gameService = gameService;
        this.gameStateMapper = gameStateMapper;
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
    public ResponseEntity<GameStateResponse> clean(@PathVariable UUID sessionId) {
        gameService.clean(sessionId);

        GameState gameState = gameService.getGameState(sessionId);

        return ResponseEntity.ok(gameStateMapper.toResponse(gameState));
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
    public ResponseEntity<GameStateResponse> buyBrushUpgrade(
            @PathVariable UUID sessionId) {

        boolean result = gameService.buyBrushUpgrade(sessionId);
        GameState gameState = gameService.getGameState(sessionId);
        GameStateResponse response = gameStateMapper.toResponse(gameState);

        if (result) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
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
    public ResponseEntity<GameStateResponse> getGameState(@PathVariable UUID sessionId) {

        GameState gameState = gameService.getGameState(sessionId);

        return ResponseEntity.ok(gameStateMapper.toResponse(gameState));
    }
}
