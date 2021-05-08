package me.clockclap.tct.game.data;

import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.item.CustomItem;

import java.util.UUID;

public interface PlayerStat {

    public UUID getUUID();

    public int getCountVillager();

    public int getCountHealer();

    public int getCountDetective();

    public int getCountWolf();

    public int getCountFanatic();

    public int getCountFox();

    public int getCountImmoral();

    public int getCountCustomRole(GameRole role);

    public int getCountDeath();

    public int getTotalFoundDeadBodies();

    public int getCountUsedItem();

    public int getTotalVictories();

    public int getTotalDefeats();

    public int getTotalUseHealStation();

    public int getTotalPlaceHealStation();

    public int getTotalBoughtItems();

    public int getTotalPlayingCount();

    public void setCountVillager(int count);

    public void setCountHealer(int count);

    public void setCountDetective(int count);

    public void setCountWolf(int count);

    public void setCountFanatic(int count);

    public void setCountFox(int count);

    public void setCountImmoral(int count);

    public void setCountCustomRole(GameRole role, int count);

    public void setCountDeath(int count);

    public void setTotalFoundDeadBodies(int count);

    public void setCountUsedItem(int count);

    public void setTotalVictories(int count);

    public void setTotalDefeats(int count);

    public void setTotalUseHealStation(int count);

    public void setTotalPlaceHealStation(int count);

    public void setTotalBoughtItems(int count);

    public void setTotalPlayingCount(int count);

}
