package me.clockclap.tct.game.role;

import me.clockclap.tct.game.role.teams.*;

public class GameTeams {

    public static final GameTeam NONE = new TeamNone();

    public static final GameTeam SPEC = new TeamSpectator();

    public static final GameTeam VILLAGERS = new TeamVillager();
    public static final GameTeam WOLVES = new TeamWolves();
    public static final GameTeam FOXES = new TeamFoxes();

}
