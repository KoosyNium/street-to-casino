package com.streettocasino.dto;

public record GameStateResponse (
        PlayerResponse player,
        ShoeResponse shoe,
        BrushResponse nextUpgrade
){
}
