package me.clockclap.tct.api.event;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;

public class ArmorEquipEvent extends PlayerEvent implements Cancellable {

    public enum ArmorType {
        HELMET(5), CHESTPLATE(6), LEGGINGS(7), BOOTS(8), SHIELD(45), OFF_HAND(45);

        private final int slot;

        ArmorType(int slot){
            this.slot = slot;
        }

        public final static ArmorType matchType(final ItemStack itemStack){
            if(itemStack == null) {
                return null;
            }

            switch (itemStack.getType()){
                case DIAMOND_HELMET:
                case GOLD_HELMET:
                case IRON_HELMET:
                case CHAINMAIL_HELMET:
                case LEATHER_HELMET:
                    return HELMET;
                case DIAMOND_CHESTPLATE:
                case GOLD_CHESTPLATE:
                case IRON_CHESTPLATE:
                case CHAINMAIL_CHESTPLATE:
                case LEATHER_CHESTPLATE:
                    return CHESTPLATE;
                case DIAMOND_LEGGINGS:
                case GOLD_LEGGINGS:
                case IRON_LEGGINGS:
                case CHAINMAIL_LEGGINGS:
                case LEATHER_LEGGINGS:
                    return LEGGINGS;
                case DIAMOND_BOOTS:
                case GOLD_BOOTS:
                case IRON_BOOTS:
                case CHAINMAIL_BOOTS:
                case LEATHER_BOOTS:
                    return BOOTS;
                case SHIELD:
                    return SHIELD;
                case AIR:
                    return null;
                default:
                    return OFF_HAND;
            }
        }

        public int getSlot(){
            return slot;
        }
    }

    private static final HandlerList handlers = new HandlerList();
    private boolean cancel = false;
    private final EquipMethod equipType;
    private final ArmorType type;
    private ItemStack oldArmorPiece, newArmorPiece;

    public ArmorEquipEvent(final Player player, final EquipMethod equipType, final ArmorType type, final ItemStack oldArmorPiece, final ItemStack newArmorPiece){
        super(player);
        this.equipType = equipType;
        this.type = type;
        this.oldArmorPiece = oldArmorPiece;
        this.newArmorPiece = newArmorPiece;
    }

    @Contract(pure = true)
    public final static HandlerList getHandlerList(){
        return handlers;
    }

    @Contract(pure = true)
    @Override
    public final HandlerList getHandlers(){
        return handlers;
    }

    public final void setCancelled(final boolean cancel){
        this.cancel = cancel;
    }

    @Contract(pure = true)
    public final boolean isCancelled(){
        return cancel;
    }

    @Contract(pure = true)
    public final ArmorType getType(){
        return type;
    }

    @Contract(pure = true)
    public final ItemStack getOldArmorPiece(){
        return oldArmorPiece;
    }

    public final void setOldArmorPiece(final ItemStack oldArmorPiece){
        this.oldArmorPiece = oldArmorPiece;
    }

    @Contract(pure = true)
    public final ItemStack getNewArmorPiece(){
        return newArmorPiece;
    }

    public final void setNewArmorPiece(final ItemStack newArmorPiece){
        this.newArmorPiece = newArmorPiece;
    }

    @Contract(pure = true)
    public EquipMethod getMethod(){
        return equipType;
    }

    public enum EquipMethod {
        SHIFT_CLICK,
        DRAG,
        HOTBAR,
        HOTBAR_SWAP,
        DISPENSER,
        BROKE,
        DEATH,
        ;
    }

    public static ItemStack getFromPlayer(ArmorType type, HumanEntity who) {
        if (type == null || who == null) {
            return null;
        }

        ItemStack item = null;

        switch (type) {
            case HELMET:
                item = who.getInventory().getHelmet();
                break;
            case CHESTPLATE:
                item = who.getInventory().getChestplate();
                break;
            case LEGGINGS:
                item = who.getInventory().getLeggings();
                break;
            case BOOTS:
                item = who.getInventory().getBoots();
                break;
            case SHIELD:
            case OFF_HAND:
                item = who.getInventory().getItemInOffHand();
                break;
        }

        return item;
    }

}
