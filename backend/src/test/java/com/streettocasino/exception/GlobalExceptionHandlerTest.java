
package com.streettocasino.exception;

import com.streettocasino.controller.GameController;
import com.streettocasino.mapper.GameStateMapper;
import com.streettocasino.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
@Import({GameStateMapper.class, GlobalExceptionHandler.class})
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GameService gameService;

    @Test
    void shouldReturn404WhenGameSessionNotFound() throws Exception {
        UUID sessionId = UUID.randomUUID();

        when(gameService.getGameState(sessionId))
                .thenThrow(new GameSessionNotFoundException(
                        "Game session not found"
                ));

        mockMvc.perform(get("/api/games/{sessionId}", sessionId))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.error").value("Not Found"))
                .andExpect(jsonPath("$.message").value("Game session not found"));
    }

    @Test
    void shouldReturn400WhenSessionIdIsMalformed() throws Exception {
        mockMvc.perform(get("/api/games/invalid-uuid"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Invalid request parameter"));
    }

    @Test
    void shouldReturn400ForIllegalArgumentException() throws Exception {
        UUID sessionId = UUID.randomUUID();

        when(gameService.getGameState(sessionId))
                .thenThrow(new IllegalArgumentException(
                        "Invalid game action"
                ));

        mockMvc.perform(get("/api/games/{sessionId}", sessionId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Invalid game action"));
    }

    @Test
    void shouldReturn500WithoutExposingInternalDetails() throws Exception {
        UUID sessionId = UUID.randomUUID();

        when(gameService.getGameState(sessionId))
                .thenThrow(new IllegalStateException(
                        "Sensitive internal details"
                ));

        mockMvc.perform(get("/api/games/{sessionId}", sessionId))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.message").value("An unexpected error occurred"))
                .andExpect(content().string(not(containsString("Sensitive internal details"))
                ));
    }
}
