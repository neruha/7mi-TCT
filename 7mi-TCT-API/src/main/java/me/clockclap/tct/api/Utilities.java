package me.clockclap.tct.api;

import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.PlayerStat;
import me.clockclap.tct.item.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface Utilities {

    public void addCommand(String label, CommandExecutor executor);

    public void addCommand(String label, String fallbackPrefix, CommandExecutor executor);

    public void addCommand(String label, String fallbackPrefix, String usageMessage, String description, List<String> aliases, CommandExecutor executor);

    @Deprecated
    public Range getYawRange(double range, double center);

    @Deprecated
    public Vector generateVector(Location a, Location b);

    @Deprecated
    public float getYaw(Vector vector);

    public boolean canSee(Player player, Player target);

    public String resetColor(String input);

    public void modifyName(Player player, String name);

    public void reloadPlayer();

    public void hidePlayer(Player player, Player target);

    public void showPlayer(Player player, Player target);

    public Player getNearestPlayer(Player player);

    @Deprecated
    public PlayerData getPlayerData(String coloredName);

    public PlayerData getPlayerData(Player player);

    public PlayerData getPlayerData(UUID uuid);

    public Collection<? extends PlayerData> getOnlinePlayersData();

    public CustomItem getCustomItemByItemStack(ItemStack item);

    public PlayerStat createNewStat(UUID uuid);

    public PlayerStat getPlayerStat(UUID uuid);

    public void runAfterLoad(Runnable runnable);

}
