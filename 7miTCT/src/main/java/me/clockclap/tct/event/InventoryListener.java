package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.VersionUtils;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.api.event.ArmorEquipEvent;
import me.clockclap.tct.api.sql.MySQLStatus;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.PlayerStat;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomItem;
import me.clockclap.tct.item.CustomItems;
import org.bukkit.*;
import org.bukkit.block.Container;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.lang.reflect.InvocationTargetException;

public class InventoryListener implements Listener {

    private final NanamiTct plugin;

    public InventoryListener(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || e.getCurrentItem() == null) {
            return;
        }

        if (isGUI(e) && e.getCurrentItem().getType() != Material.AIR) {
            e.setCancelled(true);
            if (e.getWhoClicked() instanceof Player) {
                Player p = (Player) e.getWhoClicked();
                if (e.getCurrentItem() != null) {
                    if (e.getCurrentItem().hasItemMeta()) {
                        ItemMeta meta = e.getCurrentItem().getItemMeta();
                        PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(p.getUniqueId());
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
                                    if (plugin.getGame().getElapsedTime() < sec) {
                                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_DIAMOND_HELMET.replaceAll("%SECOND%", String.valueOf(sec)));
                                        return;
                                    }
                                }
                                data.addBoughtItem(i.getName());
                                data.setCoin(data.getCoin() - 1);
                                if (NanamiTct.playerStats != null && MySQLStatus.isSqlEnabled()) {
                                    PlayerStat stat = NanamiTct.playerStats.getStat(p.getUniqueId());
                                    if (stat != null) {
                                        stat.setTotalBoughtItems(stat.getTotalBoughtItems() + 1);
                                    }
                                }
                                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_BOUGHT_ITEM.replaceAll("%ITEM%", i.getName()));
                                p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
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

    public boolean isGUI(InventoryClickEvent e) {
        try {
            Inventory inventory = e.getClickedInventory();

            String title = (String) Inventory.class.getMethod("getTitle").invoke(inventory);

            return title.equalsIgnoreCase(Reference.TCT_GUI_TITLE_GENERAL_SHOP) ||
                    title.equalsIgnoreCase(Reference.TCT_GUI_TITLE_DETECTIVE_SHOP) ||
                    title.equalsIgnoreCase(Reference.TCT_GUI_TITLE_WOLF_SHOP) ||
                    title.equalsIgnoreCase(Reference.TCT_GUI_TITLE_FANATIC_SHOP) ||
                    title.equalsIgnoreCase(Reference.TCT_GUI_TITLE_FOX_SHOP) ||
                    title.equalsIgnoreCase(Reference.TCT_GUI_TITLE_IMMORAL_SHOP) ||
                    title.equalsIgnoreCase(Reference.TCT_GUI_TITLE_HEALER_SHOP) ||
                    title.equalsIgnoreCase(Reference.TCT_GUI_TITLE_SHOP);
        } catch (NoSuchMethodError | NoSuchMethodException | InvocationTargetException | IllegalAccessException ex) {
            try {
                InventoryView inventory = e.getView();

                return inventory.getTitle().equalsIgnoreCase(Reference.TCT_GUI_TITLE_GENERAL_SHOP) ||
                        inventory.getTitle().equalsIgnoreCase(Reference.TCT_GUI_TITLE_DETECTIVE_SHOP) ||
                        inventory.getTitle().equalsIgnoreCase(Reference.TCT_GUI_TITLE_WOLF_SHOP) ||
                        inventory.getTitle().equalsIgnoreCase(Reference.TCT_GUI_TITLE_FANATIC_SHOP) ||
                        inventory.getTitle().equalsIgnoreCase(Reference.TCT_GUI_TITLE_FOX_SHOP) ||
                        inventory.getTitle().equalsIgnoreCase(Reference.TCT_GUI_TITLE_IMMORAL_SHOP) ||
                        inventory.getTitle().equalsIgnoreCase(Reference.TCT_GUI_TITLE_HEALER_SHOP) ||
                        inventory.getTitle().equalsIgnoreCase(Reference.TCT_GUI_TITLE_SHOP);
            } catch (Exception exception) {
                throw new UnsupportedClassVersionError();
            }
        }
    }

    @EventHandler
    public void onInventoryPickUp(InventoryPickupItemEvent e) {
        if (equalsInventory(e.getInventory(), Reference.TCT_GUI_TITLE_GENERAL_SHOP) ||
                equalsInventory(e.getInventory(), Reference.TCT_GUI_TITLE_DETECTIVE_SHOP) ||
                equalsInventory(e.getInventory(), Reference.TCT_GUI_TITLE_WOLF_SHOP)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryMove(InventoryMoveItemEvent e) {
        if (equalsInventory(e.getDestination(), Reference.TCT_GUI_TITLE_GENERAL_SHOP) ||
                equalsInventory(e.getDestination(), Reference.TCT_GUI_TITLE_DETECTIVE_SHOP) ||
                equalsInventory(e.getDestination(), Reference.TCT_GUI_TITLE_WOLF_SHOP)) {
            e.setCancelled(true);
        }
    }

    public boolean equalsInventory(Inventory inventory, String equals) {
        if (VersionUtils.isHigherThanVersion(VersionUtils.V1_12_2)) {
            Container container = (Container) inventory.getHolder();

            if (container == null || container.getCustomName() == null) return false;

            return equals.equalsIgnoreCase(container.getCustomName());
        } else {
            try {
                String title = (String) Inventory.class.getMethod("getTitle").invoke(inventory);

                return equals.equalsIgnoreCase(title);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }

        throw new UnsupportedClassVersionError();
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPickUpItem(EntityPickupItemEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(p.getUniqueId());
            if(data.isSpectator()) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPickUpArrow(PlayerPickupArrowEvent e) {
        Player p = e.getPlayer();
        PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(p.getUniqueId());
        if(data.isSpectator()) {
            e.setCancelled(true);
            
        }
    }

    @EventHandler
    public void onEquipArmor(ArmorEquipEvent e) {
        if(plugin.getGame().getReference().getGameState() == GameState.GAMING) {
            if (e.getType() == ArmorEquipEvent.ArmorType.HELMET) {
                if(e.getNewArmorPiece() == null) {
                    plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId()).setCO(GameRoles.NONE);
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
                                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId());
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
                                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId());
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
                                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId());
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
                                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId());
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
                                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId());
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
                                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId());
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
                                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId());
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
                                            PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId());
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
                    plugin.getGame().getReference().PLAYERDATA.get(e.getPlayer().getUniqueId()).setCO(GameRoles.NONE);
                }
            }
        }
    }

}
