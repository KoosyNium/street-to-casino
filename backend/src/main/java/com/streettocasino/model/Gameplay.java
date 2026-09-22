package com.streettocasino.model;

/**
 * Coordinates the core gameplay loop between the player, shoe and cleaning system.
 *
 * <p>The gameplay manages the current shoe being cleaned, performs cleaning
 * actions using the player's equipped brush and grants the shoe reward when
 * the cleaning is completed.</p>
 */
public class Gameplay {
    private final Player player;
    private Shoe currentShoe;
    private Cleaning currentCleaning;
    private boolean rewardGranted = false;

    /**
     * Creates a new gameplay session for the given player.
     *
     * @param player the player participating in the gameplay
     */
    public Gameplay(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("Player is null");
        }
        this.player = player;
    }

    /**
     * Starts a new cleaning task for the given shoe.
     *
     * <p>A new shoe cannot be started while the current cleaning task
     * is still in progress.</p>
     *
     * @param shoe the shoe to start cleaning
     * @throws IllegalArgumentException if the shoe is null
     * @throws IllegalStateException if the current shoe is not fully cleaned
     */
    public void startNewShoe(Shoe shoe) {
        if (shoe == null) {
            throw new IllegalArgumentException("Shoe is null");
        }
        if (currentCleaning != null && !currentCleaning.isCompleted()) {
            throw new IllegalStateException("Shoe is not done");
        }
        currentShoe = shoe;
        currentCleaning = new Cleaning(currentShoe);
        rewardGranted = false;
    }

    /**
     * Performs one cleaning action using the player's currently equipped brush.
     *
     * <p>If the cleaning is completed by this action, the shoe's money reward
     * is granted to the player. A shoe can only grant its reward once.</p>
     *
     * @throws IllegalStateException if no cleaning task has been started
     */
    public void clean() {
        if (currentShoe == null) {
            throw new IllegalStateException("No cleaning task has been started");
        }
        int efficiency = player.getBrush().getBrushEfficiency();
        currentCleaning.clean(efficiency);
        if (currentCleaning.isCompleted() && !rewardGranted) {
            player.addMoney(currentShoe.getMoneyReward());
            rewardGranted = true;
        }
    }
}
