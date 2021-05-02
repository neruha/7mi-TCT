package me.clockclap.tct.item.items;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.Game;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.death.DeadBody;
import me.clockclap.tct.game.death.TctDeathCause;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomSpecialItem;
import me.clockclap.tct.item.ItemIndex;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class TctItemClock implements CustomSpecialItem {

    private ItemStack item;
    private Material material;
    private String name;
    private String displayName;
    private String title;
    private String description;
    private boolean attackable;
    private boolean quickchat;

    private final GameRole role;
    private final boolean isdefault;
    private final int index;

    public TctItemClock() {
        this.index = ItemIndex.WOLVES_SHOP_ITEM_SLOT_5;
        this.isdefault = false;
        this.material = Material.WATCH;
        this.name = "CLOCK";
        this.displayName = "Clock";
        this.title = "Clock";
        this.description = ChatColor.RED + "Wolf Item";
        this.role = GameRoles.WOLF;
        this.attackable = false;
        this.quickchat = false;
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.WHITE + displayName);
        List<String> lore = new ArrayList<>();
        lore.add(description);
        meta.setLore(lore);
        item.setItemMeta(meta);
        this.item = item;
    }

    @Override
    public void onRightClick(Player player) {
        if(player != null) {
            Game game = NanamiTct.plugin.getGame();
            PlayerData data = game.getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(player.getName()));
            Location loc = player.getLocation();
            Location blockLoc = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            DeadBody deadBody = new DeadBody(game, data, TctDeathCause.AIR, blockLoc);
            deadBody.setKilledPlayers(data.getKilledPlayers());
            deadBody.setFake(true);
            NanamiTct.plugin.getGame().getReference().DEADBODIES.add(deadBody);
            deadBody.process();
            int tick = 2000;
            FileConfiguration config = NanamiTct.plugin.getTctConfig().getConfig();
            try {
                if (config.getString("effect.wolf-invisible.duration").endsWith("t")) {
                    String str = config.getString("effect.wolf-invisible.duration");
                    str = str.substring(0, str.length() - 1);
                    try {
                        tick = Integer.parseInt(str);
                    } catch (NumberFormatException e) {
                        tick = 2000;
                    }
                } else if (config.getString("effect.wolf-invisible.duration").endsWith("s")) {
                    String str = config.getString("effect.wolf-invisible.duration");
                    str = str.substring(0, str.length() - 1);
                    try {
                        tick = Integer.parseInt(str) * 20;
                    } catch (NumberFormatException e) {
                        tick = 2000;
                    }
                }
            } catch(NullPointerException e) {
                tick = 2000;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, tick, 1));
            data.setInvisible(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    data.setInvisible(false);
                }
            }.runTaskLater(NanamiTct.plugin, tick);
            for (int i = 0; i < player.getInventory().getSize(); i++) {
                ItemStack item = player.getInventory().getItem(i);
                if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName())) {
                    int amt = item.getAmount() - 1;
                    item.setAmount(amt);
                    player.getInventory().setItem(i, amt > 0 ? item : null);
                    player.updateInventory();
                    break;
                }
            }
        }
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    @Override
    public ItemStack getItemStack() {
        return this.item;
    }

    @Override
    public Material getMaterial() {
        return this.material;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public GameRole getRole() {
        return this.role;
    }

    @Override
    public boolean isQuickChatItem() {
        return this.quickchat;
    }

    @Override
    public boolean isDefault() {
        return this.isdefault;
    }

    @Override
    public boolean isAttackable() {
        return this.attackable;
    }

    @Override
    public void setAttackable(boolean value) {
        this.attackable = value;
    }

    @Override
    public void setItemStack(ItemStack item) {
        this.item = item;
    }

    @Override
    public void setMaterial(Material material) {
        this.material = material;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setDisplayName(String name) {
        this.displayName = name;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

}
