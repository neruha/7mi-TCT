package me.clockclap.tct.game.role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomRoles {

    private List<GameRole> allRoles = new ArrayList<>();

    public CustomRoles() { }

    public void register(GameRole role) {
        if(!allRoles.contains(role)) allRoles.add(role);
    }

    public void registerAll(Collection<? extends GameRole> roles) {
        if(!allRoles.containsAll(roles)) allRoles.addAll(roles);
    }

    public void unregister(GameRole role) {
        if(allRoles.contains(role)) allRoles.remove(role);
    }

    public void unregisterAll(Collection<? extends GameRole> roles) {
        if(allRoles.containsAll(roles)) allRoles.removeAll(roles);
    }

    public boolean contains(GameRole role) {
        return allRoles.contains(role);
    }

    public boolean containsAll(Collection<? extends GameRole> roles) {
        return allRoles.containsAll(roles);
    }

    public void clear() {
        allRoles.clear();
    }

    public boolean isEmpty() {
        return allRoles.isEmpty();
    }

    public int indexOf(GameRole role) {
        if(allRoles.contains(role)) return allRoles.indexOf(role);
        return -1;
    }

    public int size() {
        return allRoles.size();
    }

    public GameRole get(int index) {
        if(allRoles.size() > index) return allRoles.get(index);
        return GameRoles.NONE;
    }

    public List<GameRole> getRegisteredRoles() {
        return allRoles;
    }

}
