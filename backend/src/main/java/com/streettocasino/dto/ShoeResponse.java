package com.streettocasino.dto;

import java.math.BigDecimal;

public record ShoeResponse (
        int shoeDirtLevel,
        BigDecimal shoeReward,
        int cleaningProgress,
        int requiredProgress
) {
}
