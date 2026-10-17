package com.streettocasino.model;

/**
 * Handles brush upgrades and purchases for players.
 *
 * <p>The shop uses a brush catalog to determine the next available
 * upgrade and its price.</p>
 */
public class BrushShop {
    private final BrushCatalog catalog;

    /**
     * Creates a brush shop using the given catalog.
     *
     * @param catalog the catalog containing available brush upgrades
     * @throws IllegalArgumentException if the catalog is null
     */
    public BrushShop(BrushCatalog catalog) {
        if (catalog == null) {
            throw new IllegalArgumentException("Brush catalog is null");
        }
        this.catalog = catalog;
    }

    /**
     * Attempts to purchase and equip the next brush upgrade for a player.
     *
     * <p>The purchase succeeds only if another upgrade is available
     * and the player has enough money. On success, the price is deducted
     * from the player's wallet and the current brush is replaced.</p>
     *
     * @param player the player purchasing the upgrade
     * @return true if the upgrade was purchased, false if no upgrade is
     *         available or the player has insufficient funds
     * @throws IllegalArgumentException if the player is null or the
     *         player's current brush is not in the catalog
     */
    public boolean upgradeBrush(Player player) {
        if (player == null) {
            throw new IllegalArgumentException("player is null");
        }
        BrushOffer nextBrushOffer = catalog.getNextUpgrade(player.getBrush());
        if (nextBrushOffer == null) {
            return false;
        }

        if (player.getMoney().compareTo(nextBrushOffer.price()) >= 0) {
            player.spendMoney(nextBrushOffer.price());
            player.changeBrush(nextBrushOffer.brush());
            return true;
        }
        return false;
    }
}
