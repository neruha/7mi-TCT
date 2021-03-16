package me.clockclap.tct.game.role;

import me.clockclap.tct.game.role.roles.RoleNone;
import me.clockclap.tct.game.role.roles.RoleSpectator;
import me.clockclap.tct.game.role.roles.RoleVillager;

public class GameRoles {

    public static final GameRole NONE = new RoleNone();

    public static final GameRole SPEC = new RoleSpectator();

    public static final GameRole VILLAGER = new RoleVillager();

}
