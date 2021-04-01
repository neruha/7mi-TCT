package me.clockclap.tct.game.data;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.game.Game;
import me.clockclap.tct.game.role.GameRole;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class CustomProjectileData extends TctEntityData implements EntityData {

    private Game game;
    private Projectile projectile;
    private BukkitRunnable runnable;

    public CustomProjectileData(Game game, Projectile projectile, GameRole role) {
        super(game.getPlugin(), projectile, role);
        this.game = game;
        this.projectile = projectile;
    }

    public Location getLocation() {
        return this.projectile.getLocation();
    }

    public void startTimer() {
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                for(Player p : Bukkit.getOnlinePlayers()) {
                    p.playSound(getLocation(), Sound.ENTITY_FIREWORK_BLAST, 1.0F, 1.0F);
                }
            }
        };
        runnable.runTaskTimer(game.getPlugin(), 0, 2);
    }

    public void cancelTimer() {
        runnable.cancel();
    }

    @Override
    public GameRole getRole() {
        return super.role;
    }

    @Override
    public Entity getEntity() {
        return super.entity;
    }

    @Override
    public UUID getUniqueId() {
        return super.entity.getUniqueId();
    }

    @Override
    public String getName() {
        return NanamiTct.utilities.resetColor(super.entity.getName());
    }

    @Override
    public void setRole(GameRole role) {
        super.role = role;
    }
}
