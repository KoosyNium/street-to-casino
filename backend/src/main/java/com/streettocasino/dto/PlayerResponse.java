package com.streettocasino.dto;

import java.math.BigDecimal;

public record PlayerResponse (
    BigDecimal money,
    BigDecimal rareCoins,
    BigDecimal reputation,
    BrushResponse equippedBrush
) {
}
