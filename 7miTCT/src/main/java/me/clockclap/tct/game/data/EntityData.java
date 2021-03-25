package me.clockclap.tct.game.data;

import me.clockclap.tct.game.role.GameRole;
import org.bukkit.entity.Entity;

import java.util.UUID;

public interface EntityData extends CustomData {

    public GameRole getRole();

    public Entity getEntity();

    public UUID getUniqueId();

    public String getName();

    public void setRole(GameRole role);

}
