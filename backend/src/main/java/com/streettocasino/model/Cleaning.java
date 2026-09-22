package com.streettocasino.model;

public class Cleaning {
    private final Shoe shoe;
    private int progress;

    public Cleaning(Shoe shoe) {
        if (shoe == null) {
            throw new IllegalArgumentException("Shoe is null");
        }
        this.shoe = shoe;
        this.progress = 0;
    }

    public void clean(int efficiency) {
        if (efficiency <= 0) {
            throw new IllegalArgumentException("efficiency must be greater than 0");
        }
        if (progress < shoe.getDirtLevel()) {
            progress = (Math.min(progress + efficiency, shoe.getDirtLevel()));
        }
    }

    public boolean isCompleted() {
        return shoe.getDirtLevel() <= progress;
    }

    public int getProgress() {
        return progress;
    }
}
