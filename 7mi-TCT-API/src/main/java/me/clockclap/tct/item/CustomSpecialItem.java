package me.clockclap.tct.item;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface CustomSpecialItem extends CustomItem {

    public boolean isQuickChatItem();

    public default void onRightClick(Player player, ItemStack itemStack) { }

    public default void onLeftClick(Player player) { }

    public default void onAttackPlayer(Player source, Player target) { }

    public default void onAttackPlayerWithCooldown(Player source, Player target) { }

    public default void onSneak(Player player) { }

}
