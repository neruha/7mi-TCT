package me.clockclap.tct.game.data;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import org.bukkit.entity.Entity;

import java.util.UUID;

public class TctEntityData implements EntityData {

    protected NanamiTct plugin;

    protected GameRole role;
    protected Entity entity;

    public TctEntityData(NanamiTct plugin, Entity entity, GameRole role) {
        this.plugin = plugin;
        this.entity = entity;
        this.role = role;
    }

    @Override
    public GameRole getRole() {
        return this.role;
    }

    @Override
    public Entity getEntity() {
        return this.entity;
    }

    @Override
    public UUID getUniqueId() {
        return this.entity.getUniqueId();
    }

    @Override
    public String getName() {
        return NanamiTct.utilities.resetColor(this.entity.getName());
    }

    @Override
    public void setRole(GameRole role) {
        this.role = role;
    }
}
