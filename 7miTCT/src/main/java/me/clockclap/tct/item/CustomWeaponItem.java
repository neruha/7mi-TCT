package me.clockclap.tct.item;

import org.bukkit.entity.Player;

public interface CustomWeaponItem extends CustomItem {

    public float getDamage();

    public void setDamage(float value);

    public default void onAttack(Player attacker, Player target) { }

}
