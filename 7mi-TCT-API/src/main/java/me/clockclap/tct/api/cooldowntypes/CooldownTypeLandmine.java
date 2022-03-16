package me.clockclap.tct.api.cooldowntypes;

import me.clockclap.tct.NanamiTctApi;
import me.clockclap.tct.api.TctCooldownType;
import me.clockclap.tct.game.TCTGame;

public final class CooldownTypeLandmine extends TctCooldownType {

    public CooldownTypeLandmine(TCTGame game) {
        super(game, "LANDMINE", NanamiTctApi.config.getConfig().getInt("landmine-cooldown", 5));
    }

}
