package me.clockclap.tct.game.role;

import me.clockclap.tct.game.role.GameTeam;
import me.clockclap.tct.game.role.GameTeams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomTeams {

    private List<GameTeam> allTeams = new ArrayList<>();

    public CustomTeams() { }

    public void register(GameTeam team) {
        if(!allTeams.contains(team)) allTeams.add(team);
    }

    public void registerAll(Collection<? extends GameTeam> teams) {
        if(!allTeams.containsAll(teams)) allTeams.addAll(teams);
    }

    public void unregister(GameTeam team) {
        if(allTeams.contains(team)) allTeams.remove(team);
    }

    public void unregisterAll(Collection<? extends GameTeam> teams) {
        if(allTeams.containsAll(teams)) allTeams.removeAll(teams);
    }

    public boolean contains(GameTeam team) {
        return allTeams.contains(team);
    }

    public boolean containsAll(Collection<? extends GameTeam> teams) {
        return allTeams.containsAll(teams);
    }

    public void clear() {
        allTeams.clear();
    }

    public int size() {
        return allTeams.size();
    }

    public boolean isEmpty() {
        return allTeams.isEmpty();
    }

    public int indexOf(GameTeam team) {
        if(allTeams.contains(team)) return allTeams.indexOf(team);
        return -1;
    }

    public GameTeam get(int index) {
        if(allTeams.size() > index) return allTeams.get(index);
        return GameTeams.NONE;
    }

    public List<GameTeam> getRegisteredTeams() {
        return allTeams;
    }
    
}
