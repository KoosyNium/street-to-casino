package com.streettocasino.model;

public class BrushShop {
    private final BrushCatalog catalog;

    public BrushShop(BrushCatalog catalog) {
        if (catalog == null) {
            throw new IllegalArgumentException("Brush catalog is null");
        }
        this.catalog = catalog;
    }

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
