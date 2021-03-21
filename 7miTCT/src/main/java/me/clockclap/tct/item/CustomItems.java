package me.clockclap.tct.item;

import me.clockclap.tct.item.items.TctItemArrow;
import me.clockclap.tct.item.items.TctItemBow;
import me.clockclap.tct.item.items.TctItemWoodSword;
import me.clockclap.tct.item.items.TctLogBook;
import me.clockclap.tct.item.items.quickchat.QuickChatA;
import me.clockclap.tct.item.items.quickchat.QuickChatB;
import me.clockclap.tct.item.items.quickchat.QuickChatC;
import me.clockclap.tct.item.items.quickchat.QuickChatD;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomItems {

    public static List<CustomSpecialItem> specialItems = new ArrayList<>();
    public static List<CustomWeaponItem> weaponItems = new ArrayList<>();
    public static List<CustomItem> generalItems = new ArrayList<>();
    public static List<CustomItem> logItems = new ArrayList<>();

    public static List<CustomBlock> generalBlocks = new ArrayList<>();

    public static final CustomWeaponItem WOOD_SWORD = new TctItemWoodSword();
    public static final CustomItem BOW = new TctItemBow();
    public static final CustomItem ARROW = new TctItemArrow();

    public static final CustomItem LOGBOOK = new TctLogBook();

    public static final CustomSpecialItem QUICKCHAT_A = new QuickChatA();
    public static final CustomSpecialItem QUICKCHAT_B = new QuickChatB();
    public static final CustomSpecialItem QUICKCHAT_C = new QuickChatC();
    public static final CustomSpecialItem QUICKCHAT_D = new QuickChatD();

    public static void registerItem(CustomItem item) {
        generalItems.add(item);
    }

    public static void registerItem(CustomWeaponItem item) {
        weaponItems.add(item);
    }

    public static void registerItem(CustomSpecialItem item) {
        specialItems.add(item);
    }

    public static void registerLogItem(CustomItem item) {
        logItems.add(item);
    }

    public static void registerBlock(CustomBlock block) {
        generalBlocks.add(block);
        CustomBlockInfo.blockLocationMap.put(block, new ArrayList<>());
    }

    public static void register() {
        registerItem(QUICKCHAT_A);
        registerItem(QUICKCHAT_B);
        registerItem(QUICKCHAT_C);
        registerItem(QUICKCHAT_D);

        registerItem(WOOD_SWORD);

        registerItem(BOW);
        registerItem(ARROW);

        registerLogItem(LOGBOOK);
    }

    public static void unregister() {
        generalItems = new ArrayList<>();
        weaponItems = new ArrayList<>();
        logItems = new ArrayList<>();
        specialItems = new ArrayList<>();

        generalBlocks = new ArrayList<>();
        CustomBlockInfo.blockLocationMap = new HashMap<>();
    }

}
