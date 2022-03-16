package me.clockclap.tct.api;

import com.mojang.authlib.GameProfile;
import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.PlayerStat;
import me.clockclap.tct.game.data.TctPlayerStat;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.game.role.GameTeams;
import me.clockclap.tct.item.CustomItem;
import me.clockclap.tct.item.CustomItems;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
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
            CommandMap map = Bukkit.getServer().getCommandMap();
            map.getKnownCommands().put(command.getName(), command);
            for(String alias : command.getAliases()) {
                map.getKnownCommands().put(alias, command);
            }
            Bukkit.getServer().getCommandMap().register(fallbackPrefix, command);
            plugin.getLogger().info("Succeeded in registering command: /" + command.getName());
        } catch(Exception e) {
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
            CommandMap map = Bukkit.getServer().getCommandMap();
            Command command = executor instanceof TabExecutor ? new Command(label, description, usageMessage, aliases) {
                @Override
                public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                    return executor.onCommand(sender, this, commandLabel, args);
                }

                @Override
                public List<String> tabComplete(CommandSender sender, String commandLabel, String[] args) {
                    return ((TabExecutor) executor).onTabComplete(sender,this,commandLabel,args);
                }
            } : new Command(label, description, usageMessage, aliases) {
                @Override
                public boolean execute(CommandSender sender, String commandLabel, String[] args) {
                    return executor.onCommand(sender, this, commandLabel, args);
                }
            };
            map.getKnownCommands().put(label, command);
            for(String alias : command.getAliases()) {
                map.getKnownCommands().put(alias, command);
            }
            Bukkit.getServer().getCommandMap().register(fallbackPrefix, command);
            plugin.getLogger().info("Succeeded in registering command: /" + label);
        } catch(Exception e) {
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
        Validate.notNull(player);
        if(!player.canSee(target)) return false;
        if(player.getWorld().getUID().equals(target.getWorld().getUID())) {
            Collection<PotionEffect> c = target.getActivePotionEffects();
            for(PotionEffect e : c) if (e.getType() == PotionEffectType.INVISIBILITY) return false;

            Location f = player.getEyeLocation();
            Location t = target.getEyeLocation();
            World w = target.getWorld();
            double xf = f.getX();
            double yf = f.getY();
            double zf = f.getZ();
            double xt = t.getX();
            double yt = t.getY();
            double zt = t.getZ();

            double dx = xt - xf;
            double dy = yt - yf;
            double dz = zt - zf;

            Vector v = new Vector(dx, dy, dz);
            double d = v.length();
            if(d > 200) return false;
            double nx = dx / d;
            double ny = dy / d;
            double nz = dz / d;

            for(double cx = xf, cy = yf, cz = zf;
                cx <= xt && cy <= yt && cz <= zt;
                cx += nx,cy += ny,cz += nz) {

                int xi = (int)Math.round(cx);
                int yi = (int)Math.round(cy);
                int zi = (int)Math.round(cz);

                Block b = w.getBlockAt(xi, yi, zi);
                if(!BlockShape.isTransparentBlock(b.getType()) && BlockShape.isInBlock(b, cx, cy, cz)) return false;
            }
            return true;
        }
        return false;
    }

    public String resetColor(String input) {
        String result = input;
        for(String str : Reference.colorChars()) {
            result = result.replaceAll(Reference.colorChar() + str, "");
        }
        return result;
    }

    public void modifyName(Player player, String name) {
        GameProfile gameProfile = ((CraftPlayer) player).getHandle().getProfile();
        try {
            Field field = gameProfile.getClass().getDeclaredField("name");
            field.setAccessible(true);
            field.set(gameProfile, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadPlayer() {
        for (Player pl : Bukkit.getServer().getOnlinePlayers()) {
            for(Player player : Bukkit.getServer().getOnlinePlayers()) {
                if(player != null && pl != null) {
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
        if(this.plugin instanceof NanamiTct) {
            NanamiTct tct = ((NanamiTct) this.plugin);
            PlayerData d = tct.getGame().getReference().PLAYERDATA.get(player.getUniqueId());
            PlayerData e = tct.getGame().getReference().PLAYERDATA.get(target.getUniqueId());
            modifyName(target, ChatColor.GREEN + resetColor(target.getName()));
            if (d != null && e != null) {
                if(!e.isSpectator()) {
                    modifyName(target, ChatColor.GREEN + resetColor(target.getName()));
                    if(d.getRole() == GameRoles.WOLF) {
                        modifyName(target, ChatColor.GREEN + resetColor(target.getName()));
                        if(e.getRole() == GameRoles.WOLF) {
                            modifyName(target, ChatColor.RED + resetColor(target.getName()));
                        } else if(e.getRole() == GameRoles.FANATIC) {
                            modifyName(target, ChatColor.DARK_PURPLE + resetColor(target.getName()));
                        }
                    } else if(d.getRole() == GameRoles.FOX || d.getRole() == GameRoles.IMMORAL) {
                        modifyName(target, ChatColor.GREEN + resetColor(target.getName()));
                        if(e.getRole() == GameRoles.FOX) {
                            modifyName(target, ChatColor.GOLD + resetColor(target.getName()));
                        } else if(e.getRole() == GameRoles.IMMORAL) {
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
        for(Player p : player.getWorld().getPlayers()) {
            if(player == p)
                continue;

            double distance = player.getLocation().distance(p.getLocation());
            PlayerData data = NanamiTct.utilities.getPlayerData(p.getUniqueId());
            if(data != null) {
                if(!data.isSpectator() && data.getRole().getTeam() != GameTeams.WOLVES) {
                    if(distance < lastDistance) {
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
        for(Player p : players) {
            if(p != null) {
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
            if(item.getItemMeta().getDisplayName().equalsIgnoreCase(item_.getItemStack().getItemMeta().getDisplayName())) {
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
        if(NanamiTct.isLoaded) runnable.run();
    }

}
