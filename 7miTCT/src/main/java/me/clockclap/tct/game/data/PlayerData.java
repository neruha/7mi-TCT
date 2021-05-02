package me.clockclap.tct.game.data;

import me.clockclap.tct.api.PlayerWatcher;
import me.clockclap.tct.game.data.profile.TctPlayerProfile;
import me.clockclap.tct.game.death.Killer;
import me.clockclap.tct.game.death.TctDeathCause;
import me.clockclap.tct.game.role.GameRole;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;

public interface PlayerData extends EntityData {

    public GameRole getCO();

    public boolean isSpectator();

    public int getQuickChatCooldown();

    public int getCoin();

    public PlayerWatcher getWatcher();

    public Killer getKilledBy();

    public Player getPlayer();

    public Block getTargetBlock(int range);

    public List<String> getBoughtItem();

    public int getTogether();

    public int getVillager();

    public int getSuspicious();

    public int getWolf();

    public TctPlayerProfile getProfile();

    public List<String> getKilledPlayers();

    public boolean isClickableItem();

    public boolean isClickableBlock();

    public boolean isClickableEntity();

    public void setClickableItem(boolean value);

    public void setClickableBlock(boolean value);

    public void setClickableEntity(boolean value);

    public boolean hasSponge();

    public boolean isInvisible();

    public void setInvisible(boolean bool);

    public void setSponge(boolean bool);

    public void addKilledPlayer(String name);

    public void removeKilledPlayer(String name);

    public void removeKilledPlayer(int index);

    public void resetKilledPlayers();

    public void setKilledPlayers(List<String> list);

    public void setTogether(int value);

    public void setVillager(int value);

    public void setSuspicious(int value);

    public void setWolf(int value);

    public void setBoughtItem(List<String> list);

    public void resetBoughtItem();

    public void addBoughtItem(String item);

    public void removeBoughtItem(String item);

    public void startQCCCountdown();

    public void setCoin(int coin);

    public void setCO(GameRole role);

    public void setSpectator(boolean bool);

    public void setQuickChatCooldown(int second);

    public void setWatcher(PlayerWatcher watcher);

    public void setKilledBy(Killer killer);

    public void kill(TctDeathCause cause);

}
