package me.clockclap.tct.api.cooldowntypes;

import me.clockclap.tct.api.TctCooldownType;
import me.clockclap.tct.game.Game;

public final class CooldownTypeLandmine extends TctCooldownType {

    public CooldownTypeLandmine(Game game) {
        super(game, "LANDMINE", game.getPlugin().getTctConfig().getConfig().getInt("landmine-cooldown", 5));
    }

}
