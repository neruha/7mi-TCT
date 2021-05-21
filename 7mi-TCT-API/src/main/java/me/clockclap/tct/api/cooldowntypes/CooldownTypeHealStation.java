package me.clockclap.tct.api.cooldowntypes;

import me.clockclap.tct.NanamiTctApi;
import me.clockclap.tct.api.TctCooldownType;
import me.clockclap.tct.game.TctGame;

public final class CooldownTypeHealStation extends TctCooldownType {


    public CooldownTypeHealStation(TctGame game) {
        super(game, "HEAL_STATION", NanamiTctApi.config.getConfig().getInt("heal-station-cooldown", 20));
    }
}
