package me.clockclap.tct.inventory;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.api.TctConfiguration;
import me.clockclap.tct.game.Game;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomItem;
import me.clockclap.tct.item.CustomItems;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomInventory {

    private Game game;
    private Inventory generalShop;
    private Inventory detectiveShop;
    private Inventory wolfShop;
    private Inventory foxShop;
    private Inventory fanaticShop;
    private Inventory immoralShop;
    private Inventory healerShop;

    /**
     * Needs to register more quickly than registering items.
     * @param game
     */
    public CustomInventory(Game game) {
        this.game = game;
        this.generalShop = Bukkit.createInventory(null, 27, "null");
        this.detectiveShop = Bukkit.createInventory(null, 27, "null");
        this.wolfShop = Bukkit.createInventory(null, 27, "null");
        this.foxShop = Bukkit.createInventory(null, 27, "null");
        this.fanaticShop = Bukkit.createInventory(null, 27, "null");
        this.immoralShop = Bukkit.createInventory(null, 27, "null");
        this.healerShop = Bukkit.createInventory(null, 27, "null");
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

    public Inventory getFoxShop() {
        return this.foxShop;
    }

    public Inventory getFanaticShop() {
        return this.fanaticShop;
    }

    public Inventory getImmoralShop() {
        return this.immoralShop;
    }

    public Inventory getHealerShop() {
        return this.healerShop;
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

    public void setFoxShop(Inventory inv) {
        this.foxShop = inv;
    }

    public void setFanaticShop(Inventory inv) {
        this.fanaticShop = inv;
    }

    public void setImmoralShop(Inventory inv) {
        this.immoralShop = inv;
    }

    public void setHealerShop(Inventory inv) {
        this.healerShop = inv;
    }

    public void initialize() {
        setGeneralShop(Bukkit.createInventory(null, 27, Reference.TCT_GUI_TITLE_GENERAL_SHOP));
        setDetectiveShop(Bukkit.createInventory(null, 27, Reference.TCT_GUI_TITLE_DETECTIVE_SHOP));
        setWolfShop(Bukkit.createInventory(null, 27, Reference.TCT_GUI_TITLE_WOLF_SHOP));
        setFoxShop(Bukkit.createInventory(null, 27, Reference.TCT_GUI_TITLE_WOLF_SHOP));
        setFanaticShop(Bukkit.createInventory(null, 27, Reference.TCT_GUI_TITLE_WOLF_SHOP));
        setImmoralShop(Bukkit.createInventory(null, 27, Reference.TCT_GUI_TITLE_WOLF_SHOP));
        setHealerShop(Bukkit.createInventory(null, 27, Reference.TCT_GUI_TITLE_WOLF_SHOP));

        Inventory inv0 = getGeneralShop();
        Inventory inv1 = getDetectiveShop();
        Inventory inv2 = getWolfShop();
        Inventory inv3 = getFoxShop();
        Inventory inv4 = getFanaticShop();
        Inventory inv5 = getImmoralShop();
        Inventory inv6 = getHealerShop();

        List<ItemStack> vil = new ArrayList<>();
        List<ItemStack> det = new ArrayList<>();
        List<ItemStack> wol = new ArrayList<>();
        List<ItemStack> fox = new ArrayList<>();
        List<ItemStack> fan = new ArrayList<>();
        List<ItemStack> imm = new ArrayList<>();
        List<ItemStack> hea = new ArrayList<>();

        FileConfiguration config = NanamiTct.plugin.getTctConfig().getConfig();

        for(CustomItem i : CustomItems.allItems) {
            if(i != null) {
                if (config.getBoolean("roles.shop.villager." + i.getName().replaceAll("_", "-").toLowerCase(), i.getRole() == GameRoles.VILLAGER)) {
                    vil.add(i.getItemStack());
                }
                if (config.getBoolean("roles.shop.detective." + i.getName().replaceAll("_", "-").toLowerCase(), i.getRole() == GameRoles.DETECTIVE)) {
                    det.add(i.getItemStack());
                }
                if (config.getBoolean("roles.shop.wolf." + i.getName().replaceAll("_", "-").toLowerCase(), i.getRole() == GameRoles.WOLF)) {
                    wol.add(i.getItemStack());
                }
                if (config.getBoolean("roles.shop.fox." + i.getName().replaceAll("_", "-").toLowerCase(), i.getRole() == GameRoles.FOX)) {
                    fox.add(i.getItemStack());
                }
                if (config.getBoolean("roles.shop.fanatic." + i.getName().replaceAll("_", "-").toLowerCase(), i.getRole() == GameRoles.FANATIC)) {
                    fan.add(i.getItemStack());
                }
                if (config.getBoolean("roles.shop.immoral." + i.getName().replaceAll("_", "-").toLowerCase(), i.getRole() == GameRoles.IMMORAL)) {
                    imm.add(i.getItemStack());
                }
                if (config.getBoolean("roles.shop.healer." + i.getName().replaceAll("_", "-").toLowerCase(), i.getRole() == GameRoles.HEALER)) {
                    hea.add(i.getItemStack());
                }
            }
        }

        for(ItemStack i : vil) {
            int index = vil.indexOf(i);
            inv0.setItem(index, i);
        }

        for(ItemStack i : det) {
            int index = det.indexOf(i);
            inv1.setItem(index, i);
        }

        for(ItemStack i : wol) {
            int index = wol.indexOf(i);
            inv2.setItem(index, i);
        }

        for(ItemStack i : fox) {
            int index = fox.indexOf(i);
            inv3.setItem(index, i);
        }

        for(ItemStack i : fan) {
            int index = fan.indexOf(i);
            inv4.setItem(index, i);
        }

        for(ItemStack i : imm) {
            int index = imm.indexOf(i);
            inv5.setItem(index, i);
        }

        for(ItemStack i : hea) {
            int index = hea.indexOf(i);
            inv6.setItem(index, i);
        }
        setGeneralShop(inv0);
        setDetectiveShop(inv1);
        setWolfShop(inv2);
        setFoxShop(inv3);
        setFanaticShop(inv4);
        setImmoralShop(inv5);
        setHealerShop(inv6);
    }

}
