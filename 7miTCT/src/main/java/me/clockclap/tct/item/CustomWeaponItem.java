package me.clockclap.tct.item;

import org.bukkit.entity.Player;

public interface CustomWeaponItem extends CustomItem {

    public float getAttackDamage();

    public float getAttackSpeed();

    public void setAttackDamage(float value);

    public void setAttackSpeed(float value);

    public default void onAttack(Player attacker, Player target) { }

}
