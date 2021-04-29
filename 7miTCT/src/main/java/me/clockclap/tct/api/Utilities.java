package me.clockclap.tct.api;

import com.mojang.authlib.GameProfile;
import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.profile.TctProperty;
import me.clockclap.tct.game.role.GameRoles;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandExecutor;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.*;

public class Utilities {

    public JavaPlugin plugin;

    public Utilities(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void addCommand(String command, CommandExecutor executor) {
        plugin.getCommand(command).setExecutor(executor);
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
            PlayerData d = tct.getGame().getReference().PLAYERDATA.get(resetColor(player.getName()));
            PlayerData e = tct.getGame().getReference().PLAYERDATA.get(resetColor(target.getName()));
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
            if(distance < lastDistance) {
                lastDistance = distance;
                result = p;
            }
        }
        return result;
    }

}
