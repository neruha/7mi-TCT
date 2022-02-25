package me.clockclap.tct.game.data;

import me.clockclap.tct.game.role.GameRole;

import java.util.UUID;

public interface PlayerStat {

    UUID getUUID();

    int getCountVillager();

    int getCountHealer();

    int getCountDetective();

    int getCountWolf();

    int getCountFanatic();

    int getCountFox();

    int getCountImmoral();

    int getCountCustomRole(GameRole role);

    int getCountDeath();

    int getCountKill();

    int getTotalFoundDeadBodies();

    int getCountUsedItem();

    int getTotalVictories();

    int getTotalDefeats();

    int getTotalUseHealStation();

    int getTotalPlaceHealStation();

    int getTotalBoughtItems();

    int getTotalPlayingCount();

    void setCountVillager(int count);

    void setCountHealer(int count);

    void setCountDetective(int count);

    void setCountWolf(int count);

    void setCountFanatic(int count);

    void setCountFox(int count);

    void setCountImmoral(int count);

    void setCountCustomRole(GameRole role, int count);

    void setCountDeath(int count);

    void setCountKill(int count);

    void setTotalFoundDeadBodies(int count);

    void setCountUsedItem(int count);

    void setTotalVictories(int count);

    void setTotalDefeats(int count);

    void setTotalUseHealStation(int count);

    void setTotalPlaceHealStation(int count);

    void setTotalBoughtItems(int count);

    void setTotalPlayingCount(int count);

    void increaseItemUsed();
}