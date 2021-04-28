package me.clockclap.tct.api;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.cooldowntypes.CooldownTypeHealStation;
import me.clockclap.tct.api.cooldowntypes.CooldownTypeLandmine;

public class CooldownTypes {

    public static final TctCooldownType HEAL_STATION = new CooldownTypeHealStation(NanamiTct.plugin.getGame());
    public static final TctCooldownType LANDMINE = new CooldownTypeLandmine(NanamiTct.plugin.getGame());

}
