package com.streettocasino.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class GameplayTest {

    @Test
    void shouldRejectNullPlayer(){
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Gameplay(null)
        );
        assertEquals("Player is null", exception.getMessage());
    }

    @Test
    void shouldRejectNullShoe(){
        Player player = new Player();
        Gameplay gameplay = new Gameplay(player);
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> gameplay.startNewShoe(null)
        );
        assertEquals("Shoe is null", exception.getMessage());
    }

    @Test
    void shouldRejectNewShoeWhileCurrentShoeIsNotCompleted(){
        Player player = new Player();
        Shoe shoeA = new Shoe(10, BigDecimal.valueOf(30));
        Gameplay gameplay = new Gameplay(player);
        gameplay.startNewShoe(shoeA);
        Shoe shoeB = new Shoe(100, BigDecimal.valueOf(30));
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> gameplay.startNewShoe(shoeB)
        );
        assertEquals("Shoe is not done", exception.getMessage());
    }

    @Test
    void shouldAllowNewShoeAfterCurrentShoeIsCompleted(){
        Player player = new Player();
        Shoe shoeA = new Shoe(1, BigDecimal.valueOf(30));
        Gameplay gameplay = new Gameplay(player);
        gameplay.startNewShoe(shoeA);
        gameplay.clean();
        Shoe shoeB = new Shoe(100, BigDecimal.valueOf(30));
        gameplay.startNewShoe(shoeB);
    }

    @Test
    void shouldRejectCleaningWhenNoShoeHasBeenStarted(){
        Player player = new Player();
        Gameplay gameplay = new Gameplay(player);
        IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                gameplay::clean
        );
        assertEquals("No cleaning task has been started", exception.getMessage());
    }

    @Test
    void shouldNotRewardPlayerBeforeCleaningIsCompleted(){
        Player player = new Player();
        Shoe shoeA = new Shoe(10, BigDecimal.valueOf(30));
        Gameplay gameplay = new Gameplay(player);
        gameplay.startNewShoe(shoeA);
        gameplay.clean();
        assertEquals(BigDecimal.ZERO, player.getMoney());
    }

    @Test
    void shouldRewardPlayerWhenCleaningIsCompleted(){
        Player player = new Player();
        Shoe shoeA = new Shoe(1, BigDecimal.valueOf(30));
        Gameplay gameplay = new Gameplay(player);
        gameplay.startNewShoe(shoeA);
        gameplay.clean();
        assertEquals(BigDecimal.valueOf(30), player.getMoney());
    }

    @Test
    void shouldUsePlayerBrushEfficiency(){
        Player player = new Player();
        Shoe shoeA = new Shoe(10, BigDecimal.valueOf(30));
        Brush brush = new Brush("Advanced Brush", 9);
        Gameplay gameplay = new Gameplay(player);
        gameplay.startNewShoe(shoeA);
        gameplay.clean();  //Use basic brush with 1 efficiency
        player.changeBrush(brush);
        gameplay.clean();
        assertEquals(BigDecimal.valueOf(30), player.getMoney());
    }

    @Test
    void shouldRewardPlayerOnlyOnce(){
        Player player = new Player();
        Shoe shoeA = new Shoe(1, BigDecimal.valueOf(30));
        Gameplay gameplay = new Gameplay(player);
        gameplay.startNewShoe(shoeA);
        gameplay.clean();
        gameplay.clean();
        assertEquals(BigDecimal.valueOf(30), player.getMoney());
    }

    @Test
    void shouldRewardPlayerForMultipleCompletedShoes(){
        Player player = new Player();
        Shoe shoeA = new Shoe(1, BigDecimal.valueOf(30));
        Shoe shoeB = new Shoe(1, BigDecimal.valueOf(20));
        Gameplay gameplay = new Gameplay(player);
        gameplay.startNewShoe(shoeA);
        gameplay.clean();
        gameplay.startNewShoe(shoeB);
        gameplay.clean();
        assertEquals(BigDecimal.valueOf(50), player.getMoney());
    }
}