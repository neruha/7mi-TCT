package me.clockclap.tct.game.role;

import me.clockclap.tct.game.role.teams.TeamNone;
import me.clockclap.tct.game.role.teams.TeamSpectator;
import me.clockclap.tct.game.role.teams.TeamVillager;

public class GameTeams {

    public static final GameTeam NONE = new TeamNone();

    public static final GameTeam SPEC = new TeamSpectator();

    public static final GameTeam VILLAGERS = new TeamVillager();

}
