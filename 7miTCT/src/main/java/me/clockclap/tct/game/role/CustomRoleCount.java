package me.clockclap.tct.game.role;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.game.Game;

import java.util.HashMap;
import java.util.Map;

public class CustomRoleCount {

    private Game game;
    private Map<GameRole, Integer> map;

    public CustomRoleCount(Game game) {
        this.game = game;
        map = new HashMap<>();
        if(!NanamiTct.roleRegisterer.isEmpty()) {
            for(GameRole r : NanamiTct.roleRegisterer.getRegisteredRoles()) {
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
        if(!NanamiTct.roleRegisterer.isEmpty()) {
            for(GameRole r : NanamiTct.roleRegisterer.getRegisteredRoles()) {
                set(r, 0);
            }
        }
    }

}
