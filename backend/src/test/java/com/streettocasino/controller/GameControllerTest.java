package com.streettocasino.controller;

import com.streettocasino.dto.GameState;
import com.streettocasino.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameService gameService;

    @Test
    void shouldCreateGameAndReturn201() throws Exception {
        UUID sessionId = UUID.randomUUID();
        when(gameService.startGame()).thenReturn(sessionId);

        mockMvc.perform(post("/api/games"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(sessionId.toString()));
    }

    @Test
    void shouldGetGameStateAndReturn200() throws Exception {
        UUID sessionId = UUID.randomUUID();
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
        when(gameService.getGameState(sessionId)).thenReturn(gameState);

        mockMvc.perform(get("/api/games/{sessionId}", sessionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.money").value(0))
                .andExpect(jsonPath("$.rareCoins").value(0))
                .andExpect(jsonPath("$.reputation").value(0))
                .andExpect(jsonPath("$.brushName").value("Basic Brush"))
                .andExpect(jsonPath("$.brushEfficiency").value(1))
                .andExpect(jsonPath("$.shoeDirtLevel").value(10))
                .andExpect(jsonPath("$.cleaningProgress").value(0))
                .andExpect(jsonPath("$.shoeReward").value(15))
                .andExpect(jsonPath("$.nextBrushName").value("Better Brush"))
                .andExpect(jsonPath("$.nextBrushEfficiency").value(2))
                .andExpect(jsonPath("$.nextBrushPrice").value(50));
    }

    @Test
    void shouldCleanShoeAndReturnUpdatedGameState() throws Exception {
        UUID sessionId = UUID.randomUUID();
        GameState gameState = new GameState(
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                "Basic Brush",
                1,
                10,
                1,
                BigDecimal.valueOf(15),
                "Better Brush",
                2,
                BigDecimal.valueOf(50)
        );
        when(gameService.getGameState(sessionId)).thenReturn(gameState);

        mockMvc.perform(post("/api/games/{sessionId}/clean", sessionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cleaningProgress").value(1));
        verify(gameService).clean(sessionId);
    }

    @Test
    void shouldBuyBrushUpgradeAndReturnUpdatedGameState() throws Exception {
        UUID sessionId = UUID.randomUUID();
        GameState gameState = new GameState(
                BigDecimal.valueOf(25),
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                "Better Brush",
                2,
                10,
                0,
                BigDecimal.valueOf(15),
                "Professional Brush",
                4,
                BigDecimal.valueOf(200)
        );
        when(gameService.getGameState(sessionId)).thenReturn(gameState);
        when(gameService.buyBrushUpgrade(sessionId)).thenReturn(true);

        mockMvc.perform(post("/api/games/{sessionId}/brush/upgrade", sessionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brushName").value("Better Brush"))
                .andExpect(jsonPath("$.brushEfficiency").value(2))
                .andExpect(jsonPath("$.nextBrushName").value("Professional Brush"));

        verify(gameService).buyBrushUpgrade(sessionId);
    }

    @Test
    void shouldRejectBrushUpgradeWhenInsufficientMoney() throws Exception {
        UUID sessionId = UUID.randomUUID();
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
        when(gameService.getGameState(sessionId)).thenReturn(gameState);
        when(gameService.buyBrushUpgrade(sessionId)).thenReturn(false);

        mockMvc.perform(post("/api/games/{sessionId}/brush/upgrade", sessionId))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.money").value(0))
                .andExpect(jsonPath("$.brushName").value("Basic Brush"));
        ;
        verify(gameService).buyBrushUpgrade(sessionId);
    }
}