package me.clockclap.tct.item;

import org.bukkit.entity.Player;

public interface CustomSpecialItem extends CustomItem {

    public default void onRightClick(Player player) { }

    public default void onLeftClick(Player player) { }

    public default void onAttackPlayer(Player source, Player target) { }

    public default void onAttackPlayerWithCooldown(Player source, Player target) { }

    public default void onSneak(Player player) { }

}
