package me.clockclap.tct.game.data;

import me.clockclap.tct.api.PlayerWatcher;
import me.clockclap.tct.game.data.profile.TctPlayerProfile;
import me.clockclap.tct.game.death.Killer;
import me.clockclap.tct.game.death.TctDeathCause;
import me.clockclap.tct.game.role.GameRole;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.List;

public interface PlayerData extends EntityData {

    GameRole getCO();

    boolean isSpectator();

    int getQuickChatCooldown();

    int getCoin();

    PlayerWatcher getWatcher();

    Killer getKilledBy();

    Player getPlayer();

    Block getTargetBlock(int range);

    List<String> getBoughtItem();

    int getTogether();

    int getVillager();

    int getSuspicious();

    int getWolf();

    TctPlayerProfile getProfile();

    Location getSavedLocation();

    void saveLocation(Location location);

    boolean isAfterSaved();

    void setAfterSaved(boolean afterSaved);

    boolean isTeleporting();

    void setTeleporting(boolean teleporting);

    List<String> getKilledPlayers();

    boolean isClickableItem();

    boolean isClickableBlock();

    boolean isClickableEntity();

    void setClickableItem(boolean value);

    void setClickableBlock(boolean value);

    void setClickableEntity(boolean value);

    boolean hasSponge();

    boolean isInvisible();

    void setInvisible(boolean bool, boolean clock);

    void setSponge(boolean bool);

    void addKilledPlayer(String name);

    void removeKilledPlayer(String name);

    void removeKilledPlayer(int index);

    void resetKilledPlayers();

    void setKilledPlayers(List<String> list);

    void setTogether(int value);

    void setVillager(int value);

    void setSuspicious(int value);

    void setWolf(int value);

    void setBoughtItem(List<String> list);

    void resetBoughtItem();

    void addBoughtItem(String item);

    void removeBoughtItem(String item);

    void startQCCCountdown();

    void setCoin(int coin);

    void setCO(GameRole role);

    void setSpectator(boolean bool);

    void setQuickChatCooldown(int second);

    void setWatcher(PlayerWatcher watcher);

    void setKilledBy(Killer killer);

    void kill(TctDeathCause cause);

}
