package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.api.event.ArmorEquipEvent;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomItem;
import me.clockclap.tct.item.CustomItems;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;

public class InventoryEvent implements Listener {

    private NanamiTct plugin;

    public InventoryEvent(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if(e.getClickedInventory() == null) {
            return;
        }
        if(e.getClickedInventory().getTitle().equalsIgnoreCase(plugin.getCustomInventory().getGeneralShop().getTitle()) ||
                e.getClickedInventory().getTitle().equalsIgnoreCase(plugin.getCustomInventory().getDetectiveShop().getTitle()) ||
                e.getClickedInventory().getTitle().equalsIgnoreCase(plugin.getCustomInventory().getWolfShop().getTitle())) {
            if(e.getCurrentItem().getType() != Material.AIR) {
                e.setCancelled(true);
                if(e.getWhoClicked() instanceof Player) {
                    Player p = (Player) e.getWhoClicked();
                    if(e.getCurrentItem() != null) {
                        if (e.getCurrentItem().hasItemMeta()) {
                            ItemMeta meta = e.getCurrentItem().getItemMeta();
                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
                            if (data.getCoin() <= 0) {
                                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_NOT_ENOUGH_COINS);
                                return;
                            }
                            for (CustomItem i : CustomItems.allItems) {
                                if (meta.getDisplayName().equalsIgnoreCase(i.getItemStack().getItemMeta().getDisplayName())) {
                                    if (data.getBoughtItem().contains(i.getName())) {
                                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ALREADY_BOUGHT);
                                        return;
                                    }
                                    if (meta.getDisplayName().equalsIgnoreCase(CustomItems.SPONGE.getItemStack().getItemMeta().getDisplayName())) {
                                        data.setSponge(true);
                                    }
                                    if (i.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(CustomItems.DIAMOND_HELMET.getItemStack().getItemMeta().getDisplayName())) {
                                        FileConfiguration config = plugin.getTctConfig().getConfig();
                                        int sec = config.getInt("detective-confirm-time", 180);
                                        if(plugin.getGame().getElapsedTime() < sec) {
                                            p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DIAMOND_HELMET.replaceAll("%SECOND%", String.valueOf(sec)));
                                            return;
                                        }
                                    }
                                    data.addBoughtItem(i.getName());
                                    data.setCoin(data.getCoin() - 1);
                                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_BOUGHT_ITEM.replaceAll("%ITEM%", i.getName()));
                                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,  1F, 1F);
                                    p.getInventory().addItem(e.getCurrentItem());
                                    p.closeInventory();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onInventoryPickUp(InventoryPickupItemEvent e) {
        if(e.getInventory() != null) {
            if (e.getInventory().getTitle().equalsIgnoreCase(plugin.getCustomInventory().getGeneralShop().getTitle()) ||
                    e.getInventory().getTitle().equalsIgnoreCase(plugin.getCustomInventory().getDetectiveShop().getTitle()) ||
                    e.getInventory().getTitle().equalsIgnoreCase(plugin.getCustomInventory().getWolfShop().getTitle())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryMove(InventoryMoveItemEvent e) {
        if(e.getDestination() != null) {
            if (e.getDestination().getTitle().equalsIgnoreCase(plugin.getCustomInventory().getGeneralShop().getTitle()) ||
                    e.getDestination().getTitle().equalsIgnoreCase(plugin.getCustomInventory().getDetectiveShop().getTitle()) ||
                    e.getDestination().getTitle().equalsIgnoreCase(plugin.getCustomInventory().getWolfShop().getTitle())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        Player p = e.getPlayer();
        PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
        if(data.isSpectator()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPickUpItem(EntityPickupItemEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
            if(data.isSpectator()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPickUpArrow(PlayerPickupArrowEvent e) {
        Player p = e.getPlayer();
        PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
        if(data.isSpectator()) {
            e.setCancelled(true);
            
        }
    }

    @EventHandler
    public void onEquipArmor(ArmorEquipEvent e) {
        if(plugin.getGame().getReference().getGameState() == GameState.GAMING) {
            if (e.getType() == ArmorEquipEvent.ArmorType.HELMET) {
                if(e.getNewArmorPiece() == null) {
                    plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName())).setCO(GameRoles.NONE);
                    return;
                }
                if (e.getNewArmorPiece().hasItemMeta()) {
                    ItemStack newArmor = e.getNewArmorPiece();
                    long delay = 20;
                    if (CustomItems.CO_VILLAGER.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(newArmor.getItemMeta().getDisplayName())) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if(e.getPlayer().getInventory().getHelmet() != null) {
                                    if(e.getPlayer().getInventory().getHelmet().hasItemMeta()) {
                                        if (CustomItems.CO_VILLAGER.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(e.getPlayer().getInventory().getHelmet().getItemMeta().getDisplayName())) {
                                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                            data.setCO(GameRoles.VILLAGER);
                                            Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_CO.replaceAll("%PLAYER%", NanamiTct.utilities.resetColor(e.getPlayer().getName())).replaceAll("%CO%", Reference.TCT_CHAT_ROLE_CO_VILLAGER));
                                            plugin.getGame().getLog().addLine(Reference.TCT_LOGBOOK_CO_VILLAGER);
                                            plugin.getGame().getLog().addLine(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                            plugin.getGame().getLog().update();
                                        }
                                    }
                                }
                            }
                        }.runTaskLater(plugin, delay);
                    }
                    if (CustomItems.CO_DETECTIVE.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(newArmor.getItemMeta().getDisplayName())) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if(e.getPlayer().getInventory().getHelmet() != null) {
                                    if(e.getPlayer().getInventory().getHelmet().hasItemMeta()) {
                                        if (CustomItems.CO_DETECTIVE.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(e.getPlayer().getInventory().getHelmet().getItemMeta().getDisplayName())) {
                                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                            data.setCO(GameRoles.DETECTIVE);
                                            Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_CO.replaceAll("%PLAYER%", NanamiTct.utilities.resetColor(e.getPlayer().getName())).replaceAll("%CO%", Reference.TCT_CHAT_ROLE_CO_DETECTIVE));
                                            plugin.getGame().getLog().addLine(Reference.TCT_LOGBOOK_CO_DETECTIVE);
                                            plugin.getGame().getLog().addLine(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                            plugin.getGame().getLog().update();
                                        }
                                    }
                                }
                            }
                        }.runTaskLater(plugin, delay);
                    }
                    if (CustomItems.CO_HEALER.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(newArmor.getItemMeta().getDisplayName())) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if(e.getPlayer().getInventory().getHelmet() != null) {
                                    if(e.getPlayer().getInventory().getHelmet().hasItemMeta()) {
                                        if (CustomItems.CO_HEALER.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(e.getPlayer().getInventory().getHelmet().getItemMeta().getDisplayName())) {
                                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                            data.setCO(GameRoles.HEALER);
                                            Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_CO.replaceAll("%PLAYER%", NanamiTct.utilities.resetColor(e.getPlayer().getName())).replaceAll("%CO%", Reference.TCT_CHAT_ROLE_CO_HEALER));
                                            plugin.getGame().getLog().addLine(Reference.TCT_LOGBOOK_CO_HEALER);
                                            plugin.getGame().getLog().addLine(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                            plugin.getGame().getLog().update();
                                        }
                                    }
                                }
                            }
                        }.runTaskLater(plugin, delay);
                    }
                    if (CustomItems.CO_WOLF.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(newArmor.getItemMeta().getDisplayName())) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if(e.getPlayer().getInventory().getHelmet() != null) {
                                    if(e.getPlayer().getInventory().getHelmet().hasItemMeta()) {
                                        if (CustomItems.CO_WOLF.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(e.getPlayer().getInventory().getHelmet().getItemMeta().getDisplayName())) {
                                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                            data.setCO(GameRoles.WOLF);
                                            Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_CO.replaceAll("%PLAYER%", NanamiTct.utilities.resetColor(e.getPlayer().getName())).replaceAll("%CO%", Reference.TCT_CHAT_ROLE_CO_WOLF));
                                            plugin.getGame().getLog().addLine(Reference.TCT_LOGBOOK_CO_WOLF);
                                            plugin.getGame().getLog().addLine(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                            plugin.getGame().getLog().update();
                                        }
                                    }
                                }
                            }
                        }.runTaskLater(plugin, delay);
                    }
                    if (CustomItems.CO_FANATIC.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(newArmor.getItemMeta().getDisplayName())) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if(e.getPlayer().getInventory().getHelmet() != null) {
                                    if(e.getPlayer().getInventory().getHelmet().hasItemMeta()) {
                                        if (CustomItems.CO_FANATIC.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(e.getPlayer().getInventory().getHelmet().getItemMeta().getDisplayName())) {
                                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                            data.setCO(GameRoles.FANATIC);
                                            Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_CO.replaceAll("%PLAYER%", NanamiTct.utilities.resetColor(e.getPlayer().getName())).replaceAll("%CO%", Reference.TCT_CHAT_ROLE_CO_FANATIC));
                                            plugin.getGame().getLog().addLine(Reference.TCT_LOGBOOK_CO_FANATIC);
                                            plugin.getGame().getLog().addLine(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                            plugin.getGame().getLog().update();
                                        }
                                    }
                                }
                            }
                        }.runTaskLater(plugin, delay);
                    }
                    if (CustomItems.CO_FOX.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(newArmor.getItemMeta().getDisplayName())) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if(e.getPlayer().getInventory().getHelmet() != null) {
                                    if(e.getPlayer().getInventory().getHelmet().hasItemMeta()) {
                                        if (CustomItems.CO_FOX.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(e.getPlayer().getInventory().getHelmet().getItemMeta().getDisplayName())) {
                                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                            data.setCO(GameRoles.FOX);
                                            Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_CO.replaceAll("%PLAYER%", NanamiTct.utilities.resetColor(e.getPlayer().getName())).replaceAll("%CO%", Reference.TCT_CHAT_ROLE_CO_FOX));
                                            plugin.getGame().getLog().addLine(Reference.TCT_LOGBOOK_CO_FOX);
                                            plugin.getGame().getLog().addLine(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                            plugin.getGame().getLog().update();
                                        }
                                    }
                                }
                            }
                        }.runTaskLater(plugin, delay);
                    }
                    if (CustomItems.CO_IMMORAL.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(newArmor.getItemMeta().getDisplayName())) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if(e.getPlayer().getInventory().getHelmet() != null) {
                                    if(e.getPlayer().getInventory().getHelmet().hasItemMeta()) {
                                        if (CustomItems.CO_IMMORAL.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(e.getPlayer().getInventory().getHelmet().getItemMeta().getDisplayName())) {
                                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                            data.setCO(GameRoles.IMMORAL);
                                            Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_CO.replaceAll("%PLAYER%", NanamiTct.utilities.resetColor(e.getPlayer().getName())).replaceAll("%CO%", Reference.TCT_CHAT_ROLE_CO_IMMORAL));
                                            plugin.getGame().getLog().addLine(Reference.TCT_LOGBOOK_CO_IMMORAL);
                                            plugin.getGame().getLog().addLine(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                            plugin.getGame().getLog().update();
                                        }
                                    }
                                }
                            }
                        }.runTaskLater(plugin, delay);
                    }
                    if (CustomItems.DIAMOND_HELMET.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(newArmor.getItemMeta().getDisplayName())) {
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                if(e.getPlayer().getInventory().getHelmet() != null) {
                                    if(e.getPlayer().getInventory().getHelmet().hasItemMeta()) {
                                        if (CustomItems.DIAMOND_HELMET.getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(e.getPlayer().getInventory().getHelmet().getItemMeta().getDisplayName())) {
                                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                            data.setCO(GameRoles.CONFIRM_DETECTIVE);
                                            Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DETECTIVE.replaceAll("%PLAYER%", NanamiTct.utilities.resetColor(e.getPlayer().getName())));
                                            plugin.getGame().getLog().addLine(Reference.TCT_LOGBOOK_CONFIRM_DETECTIVE);
                                            plugin.getGame().getLog().addLine(NanamiTct.utilities.resetColor(e.getPlayer().getName()));
                                            plugin.getGame().getLog().update();
                                        }
                                    }
                                }
                            }
                        }.runTaskLater(plugin, delay);
                    }
                }
                if (e.getNewArmorPiece().getType() == Material.AIR) {
                    plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(e.getPlayer().getName())).setCO(GameRoles.NONE);
                }
            }
        }
    }

}
