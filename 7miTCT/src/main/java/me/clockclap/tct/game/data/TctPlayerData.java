package me.clockclap.tct.game.data;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.PlayerWatcher;
import me.clockclap.tct.game.death.DeadBody;
import me.clockclap.tct.game.death.Killer;
import me.clockclap.tct.game.death.TctDeathCause;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TctPlayerData extends TctEntityData implements PlayerData {

    private GameRole co;
    private String name;
    private boolean spec;
    private int quickchatcooldown;
    private int coin;
    private PlayerWatcher watcher;
    private List<String> boughtItem = new ArrayList<>();
    private Killer killer;

    public TctPlayerData(NanamiTct plugin, GameRole role, String name) {
        super(plugin, Bukkit.getPlayer(name), role);
        this.name = name;
        this.co = GameRoles.NONE;
        this.spec = true;
        this.quickchatcooldown = 0;
        this.coin = 0;
    }

    @Override
    public GameRole getRole() {
        return super.role;
    }

    @Override
    public GameRole getCO() {
        return this.co;
    }

    @Override
    public Entity getEntity() {
        return Bukkit.getPlayer(this.name);
    }

    @Override
    public UUID getUniqueId() {
        return Bukkit.getPlayer(this.name).getUniqueId();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isSpectator() {
        return this.spec;
    }

    @Override
    public int getQuickChatCooldown() {
        return this.quickchatcooldown;
    }

    @Override
    public int getCoin() {
        return this.coin;
    }

    @Override
    public PlayerWatcher getWatcher() {
        return this.watcher;
    }

    @Override
    public Killer getKilledBy() {
        return this.killer;
    }

    @Override
    public Player getPlayer() {
        return Bukkit.getPlayer(this.name);
    }

    @Override
    public Block getTargetBlock(int range) {
        Player p = getPlayer();
        BlockIterator iter = new BlockIterator(p, range);
        Block lastBlock = iter.next();
        while (iter.hasNext()) {
            lastBlock = iter.next();
            if (lastBlock.getType() == Material.AIR) {
                continue;
            }
            break;
        }
        return lastBlock;
    }

    @Override
    public List<String> getBoughtItem() {
        return this.boughtItem;
    }

    @Override
    public void setBoughtItem(List<String> list) {
        this.boughtItem = list;
    }

    @Override
    public void resetBoughtItem() {
        this.boughtItem = new ArrayList<>();
    }

    @Override
    public void addBoughtItem(String item) {
        this.boughtItem.add(item);
    }

    @Override
    public void removeBoughtItem(String item) {
        this.boughtItem.remove(item);
    }

    @Override
    public void startQCCCountdown() {
        int defaultSec = plugin.getTctConfig().getConfig().getInt("quickchat-cooldown", 20) + 1;
        setQuickChatCooldown(defaultSec);
        new BukkitRunnable() {
            @Override
            public void run() {
                if(getQuickChatCooldown() > 0) {
                    setQuickChatCooldown(getQuickChatCooldown() - 1);
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    @Override
    public void setCoin(int coin) {
        this.coin = coin;
    }

    @Override
    public void setRole(GameRole role) {
        super.role = role;
    }

    @Override
    public void setCO(GameRole role) {
        this.co = role;
    }

    @Override
    public void setSpectator(boolean bool) {
        this.spec = bool;
    }

    @Override
    public void setQuickChatCooldown(int second) {
        this.quickchatcooldown = second;
    }

    @Override
    public void setWatcher(PlayerWatcher watcher) {
        this.watcher = watcher;
    }

    @Override
    public void setKilledBy(Killer killer) {
        this.killer = killer;
    }

    @Override
    public void kill(TctDeathCause cause) {
        Location loc = this.getPlayer().getLocation();
        Location blockLoc = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        DeadBody deadBody = new DeadBody(plugin.getGame(), this, cause, blockLoc);
        plugin.getGame().getReference().DEADBODIES.add(deadBody);
        plugin.getGame().removeRemainingPlayers(this, true);
        if(cause == TctDeathCause.KILL) {
            plugin.getGame().setRealRemainingSeconds(plugin.getGame().getRealRemainingSeconds() + plugin.getTctConfig().getConfig().getInt("countdown.addcount.kill", 20));
        }
        deadBody.process();
        this.getPlayer().setGameMode(GameMode.SPECTATOR);
        this.setSpectator(true);
    }

}
