package me.clockclap.tct.item;

import me.clockclap.tct.game.data.CustomBlockData;
import me.clockclap.tct.item.blocks.TctItemHealStation;
import me.clockclap.tct.item.blocks.TctItemLandmine;
import me.clockclap.tct.item.blocks.TctItemSponge;
import me.clockclap.tct.item.blocks.TctItemTnt;
import me.clockclap.tct.item.items.*;
import me.clockclap.tct.item.items.co.*;
import me.clockclap.tct.item.items.quickchat.QuickChatA;
import me.clockclap.tct.item.items.quickchat.QuickChatB;
import me.clockclap.tct.item.items.quickchat.QuickChatC;
import me.clockclap.tct.item.items.quickchat.QuickChatD;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public final class CustomItems {

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
    public static final CustomItem STRONG_BOW = new TctItemStrongBow();

    public static final CustomItem SPEED_POTION = new TctItemPotionSpeed();
    public static final CustomItem STICK = new TctItemStick();
    public static final CustomItem EMPTY_BOTTLE = new TctItemEmptyBottle();
    public static final CustomItem MILK = new TctItemMilk();
    public static final CustomItem HEAL_POTION = new TctItemPotionSplashHeal();
    public static final CustomItem CRACKER = new TctItemCracker();
    public static final CustomItem COMPASS = new TctItemCompass();
    public static final CustomSpecialItem CLOCK = new TctItemClock();
    public static final CustomSpecialItem SEED = new TctItemSeed();

    public static final CustomItem SNOWBALL = new TctItemSnowball();

    public static final CustomItem CO_VILLAGER = new TctItemCoVillager();
    public static final CustomItem CO_DETECTIVE = new TctItemCoDetective();
    public static final CustomItem CO_HEALER = new TctItemCoHealer();
    public static final CustomItem CO_WOLF = new TctItemCoWolf();
    public static final CustomItem CO_FANATIC = new TctItemCoFanatic();
    public static final CustomItem CO_FOX = new TctItemCoFox();
    public static final CustomItem CO_IMMORAL = new TctItemCoImmoral();

    public static final CustomItem DIAMOND_HELMET = new TctItemDiamondHelmet();

    public static final TctLogBook LOGBOOK = new TctLogBook();

    public static final CustomSpecialItem WOLF_SWORD = new TctItemWolfSword();
    public static final CustomSpecialItem DETECTIVE_SWORD = new TctItemDetectiveSword();
    public static final CustomSpecialItem HEALER_SWORD = new TctItemHealerSword();

    public static final CustomSpecialItem QUICKCHAT_A = new QuickChatA();
    public static final CustomSpecialItem QUICKCHAT_B = new QuickChatB();
    public static final CustomSpecialItem QUICKCHAT_C = new QuickChatC();
    public static final CustomSpecialItem QUICKCHAT_D = new QuickChatD();

    public static final CustomBlock SPONGE = new TctItemSponge();
    public static final CustomBlock HEAL_STATION = new TctItemHealStation();
    public static final CustomBlock TNT = new TctItemTnt();
    public static final CustomBlock LANDMINE = new TctItemLandmine();

    public static final CustomBlock TORCH = new TctItemTorch();

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
        registerItem(WOOD_SWORD);
        registerItem(BOW);
        registerItem(ARROW);
        registerItem(STONE_SWORD);
        registerItem(STRONG_BOW);
        registerItem(HEALER_SWORD);
        registerItem(DETECTIVE_SWORD);
        registerItem(WOLF_SWORD);
        registerBlock(HEAL_STATION);
        registerItem(DIAMOND_HELMET);
        registerBlock(TNT);
        registerItem(SNOWBALL);
        registerItem(COMPASS);
        registerItem(SPEED_POTION);
        registerItem(CLOCK);
        registerItem(STICK);
        registerBlock(TORCH);
        registerBlock(SPONGE);
        registerItem(SEED);
        registerItem(EMPTY_BOTTLE);
        registerItem(MILK);
        registerItem(HEAL_POTION);
        registerItem(CRACKER);
        registerBlock(LANDMINE);

        registerItem(CO_VILLAGER);
        registerItem(CO_DETECTIVE);
        registerItem(CO_HEALER);
        registerItem(CO_WOLF);
        registerItem(CO_FANATIC);
        registerItem(CO_FOX);
        registerItem(CO_IMMORAL);

        registerLogItem(LOGBOOK);

        registerItem(QUICKCHAT_A);
        registerItem(QUICKCHAT_B);
        registerItem(QUICKCHAT_C);
        registerItem(QUICKCHAT_D);
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
