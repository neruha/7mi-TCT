package me.clockclap.tct.game.death;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.role.GameRole;
import org.bukkit.entity.Player;

public class Killer {

    public enum KillerCategory {
        PLAYER, TNT, AIR
    }

    private KillerCategory killerCategory;
    private String killer;
    private GameRole role;

    public Killer(String killer, GameRole role, KillerCategory category) {
        this.killer = killer;
        this.role = role;
        this.killerCategory = category;
    }

    public Killer(Player killer, GameRole role) {
        this.killer = NanamiTct.utilities.resetColor(killer.getName());
        this.role = role;
        this.killerCategory = KillerCategory.PLAYER;
    }

    public KillerCategory getCategory() {
        return killerCategory;
    }

    public String getName() {
        return killer;
    }

    public boolean isPlayer() {
        return killerCategory == KillerCategory.PLAYER;
    }

    public GameRole getRole() {
        return role;
    }
}
