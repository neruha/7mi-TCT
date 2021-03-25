package me.clockclap.tct.item;

import me.clockclap.tct.game.data.CustomBlockData;
import me.clockclap.tct.item.blocks.TctItemHealStation;
import me.clockclap.tct.item.blocks.TctItemSponge;
import me.clockclap.tct.item.items.*;
import me.clockclap.tct.item.items.quickchat.QuickChatA;
import me.clockclap.tct.item.items.quickchat.QuickChatB;
import me.clockclap.tct.item.items.quickchat.QuickChatC;
import me.clockclap.tct.item.items.quickchat.QuickChatD;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomItems {

    public static List<CustomSpecialItem> specialItems = new ArrayList<>();
    public static List<CustomWeaponItem> weaponItems = new ArrayList<>();
    public static List<CustomItem> generalItems = new ArrayList<>();
    public static TctLogBook logItem;

    public static List<CustomBlock> generalBlocks = new ArrayList<>();

    public static List<CustomItem> allItems = new ArrayList<>();

    public static final CustomWeaponItem WOOD_SWORD = new TctItemWoodSword();
    public static final CustomWeaponItem STONE_SWORD = new TctItemStoneSword();
    public static final CustomItem BOW = new TctItemBow();
    public static final CustomItem ARROW = new TctItemArrow();

    public static final CustomItem SNOWBALL = new TctItemSnowball();

    public static final TctLogBook LOGBOOK = new TctLogBook();

    public static final CustomSpecialItem HEALER_SWORD = new TctItemHealerSword();

    public static final CustomSpecialItem QUICKCHAT_A = new QuickChatA();
    public static final CustomSpecialItem QUICKCHAT_B = new QuickChatB();
    public static final CustomSpecialItem QUICKCHAT_C = new QuickChatC();
    public static final CustomSpecialItem QUICKCHAT_D = new QuickChatD();

    public static final CustomBlock SPONGE = new TctItemSponge();
    public static final CustomBlock HEAL_STATION = new TctItemHealStation();

    public static void registerItem(CustomItem item) {
        generalItems.add(item);
        allItems.add(item);
    }

    public static void registerItem(CustomWeaponItem item) {
        weaponItems.add(item);
        allItems.add(item);
    }

    public static void registerItem(CustomSpecialItem item) {
        specialItems.add(item);
        allItems.add(item);
    }

    public static void registerLogItem(TctLogBook book) {
        logItem = book;
        allItems.add(book);
    }

    public static void registerBlock(CustomBlock block) {
        generalBlocks.add(block);
        allItems.add(block);
    }

    public static void register() {
        registerItem(QUICKCHAT_A);
        registerItem(QUICKCHAT_B);
        registerItem(QUICKCHAT_C);
        registerItem(QUICKCHAT_D);

        registerItem(WOOD_SWORD);
        registerItem(STONE_SWORD);
        registerItem(HEALER_SWORD);

        registerItem(BOW);
        registerItem(ARROW);
        registerItem(SNOWBALL);

        registerLogItem(LOGBOOK);

        registerBlock(SPONGE);
        registerBlock(HEAL_STATION);
    }

    public static void unregister() {
        generalItems = new ArrayList<>();
        weaponItems = new ArrayList<>();
        logItem = null;
        specialItems = new ArrayList<>();

        generalBlocks = new ArrayList<>();
        resetBlockData();
    }

    public static void resetBlockData() {
        for(CustomBlockData data : CustomBlockInfo.blockDataList) {
            data.getBlock().setType(Material.AIR);
        }
        CustomBlockInfo.blockDataList = new ArrayList<>();
    }

}
