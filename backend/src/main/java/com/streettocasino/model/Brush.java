package com.streettocasino.model;

/**
 * Represents a brush used by the player to clean shoes.
 *
 * <p>Each brush has a name and an efficiency value.
 * The efficiency represents the cleaning speed multiplier of the brush.</p>
 */
public class Brush {
    private final String brushName;
    private final int brushEfficiency;

    public Brush(String brushName, int brushEfficiency) {
        if (brushName == null) {
            throw new IllegalArgumentException("Brush name is null");
        }
        if (brushName.isBlank()) {
            throw new IllegalArgumentException("Brush name is blank");
        }
        if (brushEfficiency <= 0) {
            throw new IllegalArgumentException("Brush efficiency must be greater than 0");
        }
        this.brushName = brushName;
        this.brushEfficiency = brushEfficiency;
    }

    /**
     * Returns the current brush name.
     *
     * @return the current brush name
     */
    public String getBrushName() {
        return brushName;
    }

    /**
     * Returns the current brush efficiency.
     *
     * @return the current brush efficiency
     */
    public int getBrushEfficiency() {
        return brushEfficiency;
    }
}
