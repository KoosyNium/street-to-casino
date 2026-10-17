package com.streettocasino.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GameSessionTest {
    private final ShoeCatalog shoeCatalog =  new ShoeCatalog();
    private final ShoeGenerator shoeGenerator = new ShoeGenerator(shoeCatalog);
    private final Player player = new Player();
    private final Gameplay gameplay = new Gameplay(player);
    private final ShoeGenerator shoeGeneratorMocked = mock(ShoeGenerator.class);

    @Test
    void shouldRejectNullGameplay() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new GameSession(null,shoeGenerator)
        );
        assertEquals("Gameplay is null", exception.getMessage());
    }

    @Test
    void shouldRejectNullShoeGenerator() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new GameSession(gameplay,null)
        );
        assertEquals("Shoe Generator is null", exception.getMessage());
    }

    @Test
    void shouldGenerateFirstShoeOnInitialization() {
        when(shoeGeneratorMocked.generateShoe())
                .thenReturn(new Shoe(3, BigDecimal.valueOf(5)));
        GameSession gameSession = new GameSession(gameplay,shoeGeneratorMocked);
        verify(shoeGeneratorMocked, times(1)).generateShoe();
    }

    @Test
    void shouldCompleteShoeWithSufficientBrushEfficiency() {
        GameSession gameSession = new GameSession(gameplay,shoeGenerator);
        Brush specialBrush = new Brush("speciel brush", 50);
        player.changeBrush(specialBrush);
        gameSession.clean();
        assertTrue(player.getMoney().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void shouldNotGenerateNewShoeBeforeCompletion() {
        when(shoeGeneratorMocked.generateShoe())
                .thenReturn(new Shoe(3, BigDecimal.valueOf(5)));
        GameSession gameSession = new GameSession(gameplay,shoeGeneratorMocked);

        gameSession.clean();
        verify(shoeGeneratorMocked, times(1)).generateShoe();
        gameSession.clean();
        verify(shoeGeneratorMocked, times(1)).generateShoe();
    }

    @Test
    void shouldGenerateNewShoeAfterCompletion() {
        when(shoeGeneratorMocked.generateShoe())
                .thenReturn(new Shoe(3, BigDecimal.valueOf(5)));
        GameSession gameSession = new GameSession(gameplay,shoeGeneratorMocked);

        gameSession.clean();
        verify(shoeGeneratorMocked, times(1)).generateShoe();
        gameSession.clean();
        verify(shoeGeneratorMocked, times(1)).generateShoe();
        gameSession.clean();
        verify(shoeGeneratorMocked, times(2)).generateShoe();
    }

    @Test
    void shouldGrantRewardWhenShoeIsCompleted() {
        when(shoeGeneratorMocked.generateShoe())
                .thenReturn(new Shoe(1, BigDecimal.valueOf(5)));
        GameSession gameSession = new GameSession(gameplay,shoeGeneratorMocked);

        gameSession.clean();
        assertEquals(0, player.getMoney().compareTo(BigDecimal.valueOf(5)));
    }

    @Test
    void shouldAllowMultipleShoeCompletions() {
        when(shoeGeneratorMocked.generateShoe())
                .thenReturn(new Shoe(1, BigDecimal.valueOf(5)));
        GameSession gameSession = new GameSession(gameplay,shoeGeneratorMocked);

        gameSession.clean();
        gameSession.clean();
        gameSession.clean();
        assertEquals(0, player.getMoney().compareTo(BigDecimal.valueOf(15)));
    }

    @Test
    void shouldUseNewGeneratedShoeAfterCompletion() {
        Shoe shoeA = new Shoe(1, BigDecimal.valueOf(5));
        Shoe shoeB = new Shoe(2, BigDecimal.valueOf(10));
        when(shoeGeneratorMocked.generateShoe())
                .thenReturn(shoeA, shoeB);

        GameSession gameSession = new GameSession(gameplay, shoeGeneratorMocked);
        gameSession.clean();
        gameSession.clean();
        assertEquals(0, player.getMoney().compareTo(BigDecimal.valueOf(5)));
        gameSession.clean();
        assertEquals(0, player.getMoney().compareTo(BigDecimal.valueOf(15)));
    }
}
