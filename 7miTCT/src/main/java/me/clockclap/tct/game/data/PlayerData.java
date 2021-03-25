package me.clockclap.tct.game.data;

import me.clockclap.tct.api.PlayerWatcher;
import me.clockclap.tct.game.death.Killer;
import me.clockclap.tct.game.death.TctDeathCause;
import me.clockclap.tct.game.role.GameRole;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface PlayerData extends EntityData {

    public GameRole getCO();

    public boolean isSpectator();

    public int getQuickChatCooldown();

    public int getCoin();

    public PlayerWatcher getWatcher();

    public Killer getKilledBy();

    public Player getPlayer();

    public Block getTargetBlock(int range);

    public void startQCCCountdown();

    public void setCoin(int coin);

    public void setCO(GameRole role);

    public void setSpectator(boolean bool);

    public void setQuickChatCooldown(int second);

    public void setWatcher(PlayerWatcher watcher);

    public void setKilledBy(Killer killer);

    public void kill(TctDeathCause cause);

}
