package me.clockclap.tct.api;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.NanamiTctApi;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.PlayerStat;
import me.clockclap.tct.game.data.TctPlayerStat;
import me.clockclap.tct.game.death.Killer;
import me.clockclap.tct.game.death.TctDeathCause;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.game.role.GameTeams;
import me.clockclap.tct.item.CustomItem;
import me.clockclap.tct.item.CustomItems;
import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.*;

public class TctUtilities implements Utilities {

    public JavaPlugin plugin;

    public TctUtilities(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommand(String fallbackPrefix, Command command) {
        try {
            final Field bukkit = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkit.setAccessible(true);
            CommandMap map = (CommandMap) bukkit.get(Bukkit.getServer());

            map.register(command.getName(), command);
            for (String alias : command.getAliases()) {
                map.register(alias, command);
            }
            map.register(fallbackPrefix, command);
            plugin.getLogger().info("Succeeded in registering command: /" + command.getName());
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to register command: /" + command.getName());
            e.printStackTrace();
        }
    }

    public void addCommand(String label, CommandExecutor executor) {
        addCommand(label, plugin.getName(), "", "", new ArrayList<>(), executor);
    }

    public void addCommand(String label, String fallbackPrefix, CommandExecutor executor) {
        addCommand(label, fallbackPrefix, "", "", new ArrayList<>(), executor);
    }

    public void addCommand(String label, String fallbackPrefix, String usageMessage, String description, List<String> aliases, CommandExecutor executor) {
        try {
            final Field bukkit = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkit.setAccessible(true);
            CommandMap map = (CommandMap) bukkit.get(Bukkit.getServer());
            Command command = executor instanceof TabExecutor ? new Command(label, description, usageMessage, aliases) {
                @Override
                public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                    return executor.onCommand(sender, this, commandLabel, args);
                }

                @Override
                public List<String> tabComplete(CommandSender sender, String commandLabel, String[] args) {
                    return ((TabExecutor) executor).onTabComplete(sender, this, commandLabel, args);
                }
            } : new Command(label, description, usageMessage, aliases) {
                @Override
                public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                    return executor.onCommand(sender, this, commandLabel, args);
                }
            };
            map.register(label, command);
            // v5 - fixed error
            //for (String alias : command.getAliases()) {
            //    map.register(alias, command);
            //}

            map.register(fallbackPrefix, command);
            plugin.getLogger().info("Succeeded in registering command: /" + label);
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to register command: /" + label);
            e.printStackTrace();
        }
    }

    @Deprecated
    public Range getYawRange(double range, double center) {
        double min = center - (range / 2);
        double max = center + (range / 2);
        return new Range(min, max);
    }

    @Deprecated
    public Vector generateVector(Location a, Location b) {
        double dX = a.getX() - b.getX();
        double dY = a.getY() - b.getY();
        double dZ = a.getZ() - b.getZ();
        double yaw = Math.atan2(dZ, dX);
        double pitch = Math.atan2(Math.sqrt(dZ * dZ + dX * dX), dY) + Math.PI;
        double x = Math.sin(pitch) * Math.cos(yaw);
        double y = Math.sin(pitch) * Math.sin(yaw);
        double z = Math.cos(pitch);

        Vector vector = new Vector(x, z, y);

        return vector;
    }

    @Deprecated
    public float getYaw(Vector vector) {
        double dx = vector.getX();
        double dz = vector.getZ();
        double yaw = 0;
        if (dx != 0) {
            if (dx < 0) {
                yaw = 1.5 * Math.PI;
            } else {
                yaw = 0.5 * Math.PI;
            }
            yaw -= Math.atan(dz / dx);
        } else if (dz < 0) {
            yaw = Math.PI;
        }
        return (float) (-yaw * 180 / Math.PI - 90);
    }

    public boolean canSee(Player player, Player target) {
        if (player.getWorld() != target.getWorld()) {
            return false;
        }

        World world = player.getWorld();
        Location from = player.getEyeLocation();
        Location to = target.getEyeLocation();
        int x = to.getBlockX();
        int y = to.getBlockY();
        int z = to.getBlockZ();
        int x_ = from.getBlockX();
        int y_ = from.getBlockY();
        int z_ = from.getBlockZ();
        for (int traceDistance = 100; traceDistance >= 0; traceDistance--) {
            byte b0;
            if (x_ == x && y_ == y && z_ == z) {
                return true;
            }
            double x0 = 999.0D;
            double y0 = 999.0D;
            double z0 = 999.0D;
            double x1 = 999.0D;
            double y1 = 999.0D;
            double z1 = 999.0D;
            double dx = to.getX() - from.getX();
            double dy = to.getY() - from.getY();
            double dz = to.getZ() - from.getZ();
            if (x > x_) {
                x0 = x_ + 1.0D;
                x1 = (x0 - from.getX()) / dx;
            } else if (x < x_) {
                x0 = x_ + 0.0D;
                x1 = (x0 - from.getX()) / dx;
            }
            if (y > y_) {
                y0 = y_ + 1.0D;
                y1 = (y0 - from.getY()) / dy;
            } else if (y < y_) {
                y0 = y_ + 0.0D;
                y1 = (y0 - from.getY()) / dy;
            }
            if (z > z_) {
                z0 = z_ + 1.0D;
                z1 = (z0 - from.getZ()) / dz;
            } else if (z < z_) {
                z0 = z_ + 0.0D;
                z1 = (z0 - from.getZ()) / dz;
            }
            if (x1 < y1 && x1 < z1) {
                if (x > x_) {
                    b0 = 4;
                } else {
                    b0 = 5;
                }
                from.setX(x0);
                from.add(0.0D, dy * x1, dz * x1);
            } else if (y1 < z1) {
                if (y > y_) {
                    b0 = 0;
                } else {
                    b0 = 1;
                }
                from.add(dx * y1, 0.0D, dz * y1);
                from.setY(y0);
            } else {
                if (z > z_) {
                    b0 = 2;
                } else {
                    b0 = 3;
                }
                from.add(dx * z1, dy * z1, 0.0D);
                from.setZ(z0);
            }
            x_ = from.getBlockX();
            y_ = from.getBlockY();
            z_ = from.getBlockZ();
            if (b0 == 5) {
                x_--;
            }
            if (b0 == 1) {
                y_--;
            }
            if (b0 == 3) {
                z_--;
            }
            if (world.getBlockAt(x_, y_, z_).getType().isOccluding()) {
                return false;
            }
        }
        return true;
    }

    public String resetColor(String input) {
        String result = input;
        for (String str : Reference.colorChars()) {
            result = result.replaceAll(Reference.colorChar() + str, "");
        }
        return result;
    }

    public void modifyName(Player player, String name) {

    }

    public void reloadPlayer() {
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                if (player != null && pl != null) {
                    pl.hidePlayer(plugin, player);
                    pl.showPlayer(plugin, player);
                }
            }
        }
    }

    public void hidePlayer(Player player, Player target) {
        player.hidePlayer(this.plugin, target);
    }

    public void showPlayer(Player player, Player target) {
        if (this.plugin instanceof NanamiTct) {
            NanamiTct tct = ((NanamiTct) this.plugin);
            PlayerData d = tct.getGame().getReference().PLAYERDATA.get(player.getUniqueId());
            PlayerData e = tct.getGame().getReference().PLAYERDATA.get(target.getUniqueId());
            modifyName(target, ChatColor.GREEN + resetColor(target.getName()));
            if (d != null && e != null) {
                if (!e.isSpectator()) {
                    modifyName(target, ChatColor.GREEN + resetColor(target.getName()));
                    if (d.getRole() == GameRoles.WOLF) {
                        modifyName(target, ChatColor.GREEN + resetColor(target.getName()));
                        if (e.getRole() == GameRoles.WOLF) {
                            modifyName(target, ChatColor.RED + resetColor(target.getName()));
                        } else if (e.getRole() == GameRoles.FANATIC) {
                            modifyName(target, ChatColor.DARK_PURPLE + resetColor(target.getName()));
                        }
                    } else if (d.getRole() == GameRoles.FOX || d.getRole() == GameRoles.IMMORAL) {
                        modifyName(target, ChatColor.GREEN + resetColor(target.getName()));
                        if (e.getRole() == GameRoles.FOX) {
                            modifyName(target, ChatColor.GOLD + resetColor(target.getName()));
                        } else if (e.getRole() == GameRoles.IMMORAL) {
                            modifyName(target, ChatColor.DARK_GRAY + resetColor(target.getName()));
                        } else {
                            modifyName(target, ChatColor.GREEN + resetColor(target.getName()));
                        }
                    } else {
                        modifyName(target, ChatColor.GREEN + resetColor(target.getName()));
                    }
                }
            }
            player.showPlayer(plugin, target);
            new BukkitRunnable() {
                @Override
                public void run() {
                    modifyName(target, resetColor(target.getName()));
                }
            }.runTaskLater(plugin, 1);
        }
    }

    public Player getNearestPlayer(Player player) {
        Player result = null;
        double lastDistance = Double.MAX_VALUE;
        for (Player p : player.getWorld().getPlayers()) {
            if (player == p)
                continue;

            double distance = player.getLocation().distance(p.getLocation());
            PlayerData data = NanamiTct.utilities.getPlayerData(p.getUniqueId());
            if (data != null) {
                if (!data.isSpectator() && data.getRole().getTeam() != GameTeams.WOLVES) {
                    if (distance < lastDistance) {
                        lastDistance = distance;
                        result = p;
                    }
                }
            }
        }
        return result;
    }

    @Deprecated
    public PlayerData getPlayerData(String coloredName) {
        String name = resetColor(coloredName);
        Player p = Bukkit.getPlayer(name);
        return NanamiTct.plugin.getGame().getReference().PLAYERDATA.get(p.getUniqueId());
    }

    public PlayerData getPlayerData(Player player) {
        return NanamiTct.plugin.getGame().getReference().PLAYERDATA.get(player.getUniqueId());
    }

    public PlayerData getPlayerData(UUID uuid) {
        return NanamiTct.plugin.getGame().getReference().PLAYERDATA.get(uuid);
    }

    public Collection<? extends PlayerData> getOnlinePlayersData() {
        Collection<? extends Player> players = Bukkit.getOnlinePlayers();
        Collection<PlayerData> datas = new ArrayList<>();
        for (Player p : players) {
            if (p != null) {
                String name = resetColor(p.getName());
                PlayerData data = getPlayerData(p);
                if (data != null) {
                    datas.add(data);
                }
            }
        }
        return datas;
    }

    public CustomItem getCustomItemByItemStack(ItemStack item) {
        for (CustomItem item_ : CustomItems.allItems) {
            if (item.getItemMeta().getDisplayName().equalsIgnoreCase(item_.getItemStack().getItemMeta().getDisplayName())) {
                return item_;
            }
        }
        return null;
    }

    public PlayerStat createNewStat(UUID uuid) {
        return new TctPlayerStat(uuid);
    }

    public PlayerStat getPlayerStat(UUID uuid) {
        return NanamiTct.playerStats.getStat(uuid);
    }

    public void runAfterLoad(Runnable runnable) {
        if (NanamiTct.isLoaded) runnable.run();
    }

    public void summonFoxFirework(Location location) {
        FileConfiguration config = NanamiTctApi.config.getConfig();

        Firework fw = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta meta = fw.getFireworkMeta();
        FireworkEffect.Builder builder = FireworkEffect.builder();
        try {
            FireworkEffect.Type type = FireworkEffect.Type.valueOf(config.getString("fireworks.type", "BURST"));
            builder.with(type);
        } catch (Exception ex) {
            builder.with(FireworkEffect.Type.BURST);
        }
        try {
            for (String color : (List<String>) config.getList("fireworks.colors", Collections.singletonList("0xff0000"))) {
                int c;
                if (color.startsWith("0x")) {
                    c = (int) Long.parseLong(color, 16);
                } else {
                    c = (int) Long.parseLong(color, 10);
                }
                builder.withColor(Color.fromRGB(c));
            }
        } catch (Exception ex) {
            builder.withColor(Color.RED);
        }
        try {
            for (String color : (List<String>) config.getList("fireworks.fades", Collections.singletonList("0xff0000"))) {
                int c;
                if (color.startsWith("0x")) {
                    c = (int) Long.parseLong(color, 16);
                } else {
                    c = (int) Long.parseLong(color, 10);
                }
                builder.withFade(Color.fromRGB(c));
            }
        } catch (Exception ex) {
            builder.withFade(Color.RED);
        }
        {
            FireworkEffect effect = builder.build();
            meta.addEffect(effect);
            meta.setPower(config.getInt("fireworks.power"));
        }
        fw.setFireworkMeta(meta);
    }

    public boolean hasSlownessGlassBottle(Player player) {
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null && item.hasItemMeta() && item.getItemMeta().getDisplayName().equalsIgnoreCase(CustomItems.EMPTY_BOTTLE.getItemStack().getItemMeta().getDisplayName())) {
                return true;
            }
        }
        return false;
    }

    public void addSlownessPotion(Player player) {
        FileConfiguration config = NanamiTctApi.config.getConfig();

        int tick = 200;
        int level = 2;
        try {
            if (config.getString("potion-effect.slowness.duration").endsWith("t")) {
                String str = config.getString("potion-effect.slowness.duration");
                str = str.substring(0, str.length() - 1);
                try {
                    tick = Integer.parseInt(str);
                } catch (NumberFormatException ignored) {
                }
            } else if (config.getString("potion-effect.slowness.duration").endsWith("s")) {
                String str = config.getString("potion-effect.slowness.duration");
                str = str.substring(0, str.length() - 1);
                try {
                    tick = Integer.parseInt(str) * 20;
                } catch (NumberFormatException ignored) {
                }
            }
        } catch (NullPointerException ignored) {
        }
        try {
            level = config.getInt("potion-effect.slowness.level");
        } catch (Exception ignored) {
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, tick, level));
    }

    public boolean isEnded() {
        List<PlayerData> villagers = new ArrayList<>();
        List<PlayerData> wolves = new ArrayList<>();
        List<PlayerData> foxes = new ArrayList<>();

        NanamiTct plugin = NanamiTct.plugin;

        for (PlayerData d : plugin.getGame().getRemainingPlayers(true)) {
            if (d.getRole().getTeam().parent() == GameTeams.VILLAGERS) {
                villagers.add(d);
                continue;
            }
            if (d.getRole().getTeam().parent() == GameTeams.WOLVES) {
                wolves.add(d);
                continue;
            }
            if (d.getRole() == GameRoles.FOX) {
                foxes.add(d);
            }
        }
        if (wolves.size() > 0 && villagers.size() <= 0) {
            if (foxes.size() > 0) {
                plugin.getGame().stop(GameTeams.FOXES);
                return true;
            }
            plugin.getGame().stop(GameTeams.WOLVES);
            return true;
        }
        if (villagers.size() > 0 && wolves.size() <= 0) {
            if (foxes.size() > 0) {
                plugin.getGame().stop(GameTeams.FOXES);
                return true;
            }
            plugin.getGame().stop(GameTeams.VILLAGERS);
            return true;
        }
        if (villagers.size() <= 0) {
            if (foxes.size() > 0) {
                plugin.getGame().stop(GameTeams.FOXES);
                return true;
            }
            plugin.getGame().stop(GameTeams.VILLAGERS);
            return true;
        }

        if (foxes.size() <= 0) {
            for (PlayerData d : plugin.getGame().getRemainingPlayers(true)) {
                if (d.getRole() == GameRoles.IMMORAL) {
                    d.setKilledBy(new Killer("AIR", GameRoles.NONE, Killer.KillerCategory.AIR));
                    d.kill(TctDeathCause.AIR);

                    return false;
                }
            }
        }

        return false;
    }
}