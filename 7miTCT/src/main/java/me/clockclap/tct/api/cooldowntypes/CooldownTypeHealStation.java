package me.clockclap.tct.api.cooldowntypes;

import me.clockclap.tct.api.TctCooldownType;
import me.clockclap.tct.game.Game;

public final class CooldownTypeHealStation extends TctCooldownType {


    public CooldownTypeHealStation(Game game) {
        super(game, "HEAL_STATION", game.getPlugin().getTctConfig().getConfig().getInt("heal-station-cooldown", 20));
    }
}
