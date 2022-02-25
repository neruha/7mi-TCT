package me.clockclap.tct.game.role;

import me.clockclap.tct.NanamiTctApi;
import me.clockclap.tct.game.TCTGame;

import java.util.HashMap;
import java.util.Map;

public class CustomRoleCount {

    private TCTGame game;
    private Map<GameRole, Integer> map;

    public CustomRoleCount(TCTGame game) {
        this.game = game;
        map = new HashMap<>();
        if(!NanamiTctApi.roleRegistry.isEmpty()) {
            for(GameRole r : NanamiTctApi.roleRegistry.getRegisteredRoles()) {
                set(r, 0);
            }
        }
    }

    public int get(GameRole role) {
        if(map.containsKey(role)) return map.get(role);
        return 0;
    }

    public void set(GameRole role, int count) {
        map.put(role, count);
    }

    public void initialize() {
        map.clear();
        if(!NanamiTctApi.roleRegistry.isEmpty()) {
            for(GameRole r : NanamiTctApi.roleRegistry.getRegisteredRoles()) {
                set(r, 0);
            }
        }
    }

}
