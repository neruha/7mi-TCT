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
        inv0.setItem(2, CustomItems.SPEED_POTION.getItemStack());
        inv0.setItem(3, CustomItems.STICK.getItemStack());
        inv0.setItem(4, CustomItems.EMPTY_BOTTLE.getItemStack());
        inv0.setItem(5, CustomItems.MILK.getItemStack());
        inv0.setItem(6, CustomItems.HEAL_POTION.getItemStack());
        inv0.setItem(7, CustomItems.CRACKER.getItemStack());

        inv1.setItem(0, CustomItems.STONE_SWORD.getItemStack());
        inv1.setItem(1, CustomItems.STRONG_BOW.getItemStack());
        inv1.setItem(2, CustomItems.DETECTIVE_SWORD.getItemStack());
        inv1.setItem(3, CustomItems.HEAL_STATION.getItemStack());
        inv1.setItem(4, CustomItems.SPEED_POTION.getItemStack());
        inv1.setItem(5, CustomItems.STICK.getItemStack());
        inv1.setItem(6, CustomItems.EMPTY_BOTTLE.getItemStack());
        inv1.setItem(7, CustomItems.MILK.getItemStack());
        inv1.setItem(8, CustomItems.SPONGE.getItemStack());
        inv1.setItem(9, CustomItems.HEAL_POTION.getItemStack());
        inv1.setItem(10, CustomItems.CRACKER.getItemStack());

        inv2.setItem(0, CustomItems.STONE_SWORD.getItemStack());
        inv2.setItem(1, CustomItems.STRONG_BOW.getItemStack());
        inv2.setItem(2, CustomItems.WOLF_SWORD.getItemStack());
        inv2.setItem(3, CustomItems.TNT.getItemStack());
        inv2.setItem(4, CustomItems.SNOWBALL.getItemStack());
        inv2.setItem(5, CustomItems.SPEED_POTION.getItemStack());
        inv2.setItem(6, CustomItems.STICK.getItemStack());
        inv2.setItem(7, CustomItems.EMPTY_BOTTLE.getItemStack());
        inv2.setItem(8, CustomItems.MILK.getItemStack());
        inv2.setItem(9, CustomItems.HEAL_POTION.getItemStack());
        inv2.setItem(10, CustomItems.CRACKER.getItemStack());
        setGeneralShop(inv0);
        setDetectiveShop(inv1);
        setWolfShop(inv2);
    }

}
