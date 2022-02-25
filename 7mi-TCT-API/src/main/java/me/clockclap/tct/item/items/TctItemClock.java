package me.clockclap.tct.item.items;

import me.clockclap.tct.NanamiTctApi;
import me.clockclap.tct.api.sql.MySQLStatus;
import me.clockclap.tct.game.TCTGame;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.PlayerStat;
import me.clockclap.tct.game.death.DeadBody;
import me.clockclap.tct.game.death.TctDeathCause;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomSpecialItem;
import me.clockclap.tct.item.ItemIndex;
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
        this.material = Material.LEGACY_WATCH;
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
    public void onRightClick(Player player, ItemStack item) {
        if (player != null) {
            TCTGame game = NanamiTctApi.game;
            PlayerData data = game.getReference().PLAYERDATA.get(player.getUniqueId());
            Location loc = player.getLocation();
            Location blockLoc = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            DeadBody deadBody = new DeadBody(game, data, TctDeathCause.AIR, blockLoc);
            deadBody.setKilledPlayers(data.getKilledPlayers());
            deadBody.setFake(true);
            game.getReference().DEADBODIES.add(deadBody);
            deadBody.process();
            int tick = 200;
            FileConfiguration config = NanamiTctApi.config.getConfig();
            try {
                if (config.getString("effect.wolf-invisible.duration", "200t").endsWith("t")) {
                    String str = config.getString("effect.wolf-invisible.duration", "200t");
                    str = str.substring(0, str.length() - 1);
                    try {
                        tick = Integer.parseInt(str);
                    } catch (NumberFormatException e) {
                        tick = 200;
                    }
                } else if (config.getString("effect.wolf-invisible.duration", "10s").endsWith("s")) {
                    String str = config.getString("effect.wolf-invisible.duration", "10s");
                    str = str.substring(0, str.length() - 1);
                    try {
                        tick = Integer.parseInt(str) * 20;
                    } catch (NumberFormatException e) {
                        tick = 200;
                    }
                }
            } catch (NullPointerException e) {
                tick = 200;
            }
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, tick, 1));
            data.setInvisible(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    data.setInvisible(false);
                }
            }.runTaskLater(NanamiTctApi.plugin, tick);

            item.setAmount(item.getAmount() - 1);

            if (MySQLStatus.isSqlEnabled() && NanamiTctApi.playerStats != null) {
                PlayerStat stat = NanamiTctApi.playerStats.getStat(player.getUniqueId());
                if (stat != null) stat.setCountUsedItem(stat.getCountUsedItem() + 1);
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
