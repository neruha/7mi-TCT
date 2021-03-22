package me.clockclap.tct.api;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.cooldowntypes.CooldownTypeHealStation;

public class CooldownTypes {

    public static final TctCooldownType HEAL_STATION = new CooldownTypeHealStation(NanamiTct.plugin.getGame());

}
