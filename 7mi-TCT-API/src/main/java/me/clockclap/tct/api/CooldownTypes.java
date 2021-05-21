package me.clockclap.tct.api;

import me.clockclap.tct.NanamiTctApi;
import me.clockclap.tct.api.cooldowntypes.CooldownTypeHealStation;
import me.clockclap.tct.api.cooldowntypes.CooldownTypeLandmine;

public class CooldownTypes {

    public static final TctCooldownType HEAL_STATION = new CooldownTypeHealStation(NanamiTctApi.game);
    public static final TctCooldownType LANDMINE = new CooldownTypeLandmine(NanamiTctApi.game);

}
