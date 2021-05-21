package me.clockclap.tct;

import me.clockclap.tct.api.ITctConfiguration;
import me.clockclap.tct.api.Utilities;
import me.clockclap.tct.api.sql.MySQLConnection;
import me.clockclap.tct.api.sql.MySQLPlayerStats;
import me.clockclap.tct.game.TctGame;
import me.clockclap.tct.game.role.CustomRoles;
import me.clockclap.tct.item.CustomTeams;
import org.bukkit.plugin.Plugin;

public final class NanamiTctApi {

    public static ITctConfiguration config;
    public static Plugin plugin;
    public static Utilities utilities;
    public static TctGame game;
    public static MySQLConnection connection;
    public static MySQLPlayerStats playerStats;
    public static CustomRoles roleRegistry;
    public static CustomTeams teamRegistry;

}
