package me.clockclap.tct.inventory;

import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.Game;
import me.clockclap.tct.item.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class CustomInventory {

    private Game game;
    private Inventory generalShop;
    private Inventory detectiveShop;
    private Inventory wolfShop;

    /**
     * Needs to register more quickly than registering items.
     * @param game
     */
    public CustomInventory(Game game) {
        this.game = game;
        this.generalShop = Bukkit.createInventory(null, 27, "null");
        this.detectiveShop = Bukkit.createInventory(null, 27, "null");
        this.wolfShop = Bukkit.createInventory(null, 27, "null");
    }

    public Inventory getGeneralShop() {
        return this.generalShop;
    }

    public Inventory getDetectiveShop() {
        return this.detectiveShop;
    }

    public Inventory getWolfShop() {
        return this.wolfShop;
    }

    public void setGeneralShop(Inventory inv) {
        this.generalShop = inv;
    }

    public void setDetectiveShop(Inventory inv) {
        this.detectiveShop = inv;
    }

    public void setWolfShop(Inventory inv) {
        this.wolfShop = inv;
    }

    public void initialize() {
        setGeneralShop(Bukkit.createInventory(null, 27, Reference.TCT_GUI_TITLE_GENERAL_SHOP));
        setDetectiveShop(Bukkit.createInventory(null, 27, Reference.TCT_GUI_TITLE_DETECTIVE_SHOP));
        setWolfShop(Bukkit.createInventory(null, 27, Reference.TCT_GUI_TITLE_WOLF_SHOP));
        Inventory inv0 = getGeneralShop();
        Inventory inv1 = getDetectiveShop();
        Inventory inv2 = getWolfShop();
        inv0.setItem(0, CustomItems.STONE_SWORD.getItemStack());
        inv0.setItem(1, CustomItems.STRONG_BOW.getItemStack());

        inv1.setItem(0, CustomItems.STONE_SWORD.getItemStack());
        inv1.setItem(1, CustomItems.STRONG_BOW.getItemStack());
        inv1.setItem(2, CustomItems.HEAL_STATION.getItemStack());
        inv1.setItem(3, CustomItems.SPONGE.getItemStack());

        inv2.setItem(0, CustomItems.STONE_SWORD.getItemStack());
        inv2.setItem(1, CustomItems.STRONG_BOW.getItemStack());
        inv2.setItem(2, CustomItems.SNOWBALL.getItemStack());
        setGeneralShop(inv0);
        setDetectiveShop(inv1);
        setWolfShop(inv2);
    }

}
