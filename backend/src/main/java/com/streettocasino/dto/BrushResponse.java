package com.streettocasino.dto;

import java.math.BigDecimal;

public record BrushResponse (
    String brushName,
    int brushEfficiency,
    BigDecimal price
){
}
