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

/**
 * This is utilities of Nanami TCT.
 * <br>
 * You don't have to add NMS.
 * <br>
 * <br>
 * To use, {@link me.clockclap.tct.NanamiTctApi}{@code .utilities}
 */
public interface Utilities {

    /**
     * Registers new command
     *
     * @param fallbackPrefix command prefix
     * @param command the command
     */
    public void registerCommand(String fallbackPrefix, Command command);

    /**
     * Registers new command
     *
     * @param label command name
     * @param executor the command executor
     */
    public void addCommand(String label, CommandExecutor executor);

    /**
     * Registers new command
     *
     * @param label command name
     * @param fallbackPrefix command prefix
     * @param executor the command executor
     */
    public void addCommand(String label, String fallbackPrefix, CommandExecutor executor);

    /**
     * Registers new command
     *
     * @param label command name
     * @param fallbackPrefix command prefix
     * @param usageMessage command usage
     * @param description command description
     * @param aliases command aliases
     * @param executor the command executor
     */
    public void addCommand(String label, String fallbackPrefix, String usageMessage, String description, List<String> aliases, CommandExecutor executor);

    /**
     * Returns yaw range as {@link Range} by double
     *
     * @param range range with
     * @param center center
     * @return entity yaw range as {@link Range}
     */
    @Deprecated
    public Range getYawRange(double range, double center);

    /**
     * Returns {@link Vector} {@code a} to {@code b}
     *
     * @param a location A
     * @param b location B
     * @return {@link Vector} location A to location B
     */
    @Deprecated
    public Vector generateVector(Location a, Location b);

    /**
     * Returns yaw as {@link Float} by vector
     *
     * @param vector {@link Vector} location A to location B
     * @return yaw as {@link Float}
     */
    @Deprecated
    public float getYaw(Vector vector);

    /**
     * Returns {@link Boolean} player A can see player B
     *
     * @param player player A
     * @param target player B
     * @return if player A can see player B, returns {@code true}
     */
    public boolean canSee(Player player, Player target);

    /**
     * Returns {@link String} removed all containing colour codes
     *
     * @param input coloured {@link String}
     * @return if inputted {@link String} contains any colour codes, removes them.
     */
    public String resetColor(String input);

    /**
     * Changes player's tag name
     *
     * @param player target {@link Player}
     * @param name new name as {@link String}
     */
    public void modifyName(Player player, String name);

    /**
     * Reloads player names.
     */
    public void reloadPlayer();

    /**
     * Hides player
     *
     * @param player seeing {@link Player}
     * @param target target {@link Player}
     */
    public void hidePlayer(Player player, Player target);

    /**
     * Shows player
     *
     * @param player seeing {@link Player}
     * @param target target {@link Player}
     */
    public void showPlayer(Player player, Player target);

    /**
     * Returns {@link Player} nearest by player
     *
     * @param player player
     * @return Nearest player by specific player as {@link Player}
     */
    public Player getNearestPlayer(Player player);

    /**
     * Gets {@link PlayerData} by coloured name.
     *
     * @param colouredName coloured player name or player name non-coloured
     * @return {@link PlayerData} by specific player name.
     */
    @Deprecated
    public PlayerData getPlayerData(String colouredName);

    /**
     * Gets {@link PlayerData} by specific player
     *
     * @param player target player with {@link Player}
     * @return {@link PlayerData} by specific player
     */
    public PlayerData getPlayerData(Player player);

    /**
     * Gets {@link PlayerData} by specific player's {@link UUID}
     *
     * @param uuid uuid of specific player with {@link UUID}
     * @return {@link PlayerData} by specific player's {@link UUID}
     */
    public PlayerData getPlayerData(UUID uuid);

    /**
     * Gets all online {@link Player}'s data as {@link PlayerData}
     *
     * @return all {@link PlayerData} online
     */
    public Collection<? extends PlayerData> getOnlinePlayersData();

    /**
     * Gets registered {@link CustomItem} by bukkit {@link ItemStack}
     *
     * @param item bukkit {@link ItemStack}
     * @return {@link CustomItem} by specific {@link ItemStack}
     */
    public CustomItem getCustomItemByItemStack(ItemStack item);

    /**
     * Creates new specific player's stats as {@link PlayerStat} if the stat is not exists
     * <br>
     * Needs MySQL connection
     *
     * @param uuid uuid of target player as {@link UUID}
     * @return new {@link PlayerStat} of specific player
     */
    public PlayerStat createNewStat(UUID uuid);

    /**
     * Gets specific player's stats as {@link PlayerStat} if the stat is exists
     * <br>
     * If the stat is not exists, creates new stat
     * <br>
     * Needs MySQL connection
     *
     * @param uuid uuid of target player as {@link UUID}
     * @return {@link PlayerStat} of specific player
     */
    public PlayerStat getPlayerStat(UUID uuid);

    /**
     * This method does nothing
     *
     * @param runnable {@link Runnable}
     */
    public void runAfterLoad(Runnable runnable);

}
