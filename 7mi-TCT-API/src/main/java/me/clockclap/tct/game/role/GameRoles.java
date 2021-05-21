package me.clockclap.tct.game.role;

import me.clockclap.tct.game.role.roles.*;

public class GameRoles {

    public static final GameRole NONE = new RoleNone();

    public static final GameRole SPEC = new RoleSpectator();

    public static final GameRole VILLAGER = new RoleVillager();
    public static final GameRole HEALER = new RoleHealer();
    public static final GameRole DETECTIVE = new RoleDetective();
    public static final GameRole WOLF = new RoleWolf();
    public static final GameRole FANATIC = new RoleFanatic();
    public static final GameRole FOX = new RoleFox();
    public static final GameRole IMMORAL = new RoleImmoral();

    public static final GameRole CONFIRM_DETECTIVE = new ConfirmDetective();

}
