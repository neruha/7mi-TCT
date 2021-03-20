package me.clockclap.tct.item;

import me.clockclap.tct.item.items.TctItemArrow;
import me.clockclap.tct.item.items.TctItemBow;
import me.clockclap.tct.item.items.TctItemWoodSword;
import me.clockclap.tct.item.items.TctLogBook;
import me.clockclap.tct.item.items.quickchat.QuickChatA;
import me.clockclap.tct.item.items.quickchat.QuickChatB;
import me.clockclap.tct.item.items.quickchat.QuickChatC;
import me.clockclap.tct.item.items.quickchat.QuickChatD;

public class CustomItems {

    public static final CustomWeaponItem WOOD_SWORD = new TctItemWoodSword();
    public static final CustomItem BOW = new TctItemBow();
    public static final CustomItem ARROW = new TctItemArrow();

    public static final CustomItem LOGBOOK = new TctLogBook();

    public static final CustomSpecialItem QUICKCHAT_A = new QuickChatA();
    public static final CustomSpecialItem QUICKCHAT_B = new QuickChatB();
    public static final CustomSpecialItem QUICKCHAT_C = new QuickChatC();
    public static final CustomSpecialItem QUICKCHAT_D = new QuickChatD();

}
