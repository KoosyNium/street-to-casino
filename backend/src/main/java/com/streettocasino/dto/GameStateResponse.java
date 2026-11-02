package com.streettocasino.dto;

/**
 * Represents the complete game state exposed by the REST API.
 * Groups player information, the current shoe and the next
 * available brush upgrade.
 *
 * @param player the current player information
 * @param shoe the current shoe and cleaning progress
 * @param nextUpgrade the next available brush upgrade,
 *                    or null if no upgrade is available
 */
public record GameStateResponse (
        PlayerResponse player,
        ShoeResponse shoe,
        BrushResponse nextUpgrade
){
}
