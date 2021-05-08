package me.clockclap.tct.game.data;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.sql.MySQLStatus;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.item.CustomItem;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TctPlayerStat implements PlayerStat {

    private final UUID uuid;

    private int villager;
    private int healer;
    private int detective;
    private int wolf;
    private int fanatic;
    private int fox;
    private int immoral;
    private Map<GameRole, Integer> customRole;
    private int death;
    private int totalFoundDeadBodies;
    private int usedItem;
    private int totalVictories;
    private int totalDefeats;
    private int totalUseHealStation;
    private int totalPlaceHealStation;
    private int totalBoughtItems;
    private int totalPlayCount;

    public TctPlayerStat(UUID uuid) {
        this.uuid = uuid;
        this.villager = 0;
        this.healer = 0;
        this.detective = 0;
        this.wolf = 0;
        this.fanatic = 0;
        this.fox = 0;
        this.immoral = 0;
        this.customRole = new HashMap<>();
        this.death = 0;
        this.totalFoundDeadBodies = 0;
        this.usedItem = 0;
        this.totalVictories = 0;
        this.totalDefeats = 0;
        this.totalUseHealStation = 0;
        this.totalPlaceHealStation = 0;
        this.totalBoughtItems = 0;
        this.totalPlayCount = 0;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int getCountVillager() {
        return villager;
    }

    @Override
    public int getCountHealer() {
        return healer;
    }

    @Override
    public int getCountDetective() {
        return detective;
    }

    @Override
    public int getCountWolf() {
        return wolf;
    }

    @Override
    public int getCountFanatic() {
        return fanatic;
    }

    @Override
    public int getCountFox() {
        return fox;
    }

    @Override
    public int getCountImmoral() {
        return immoral;
    }

    @Override
    public int getCountCustomRole(GameRole role) {
        if(customRole.containsKey(role)) return customRole.get(role);
        return 0;
    }

    @Override
    public int getCountDeath() {
        return death;
    }

    @Override
    public int getTotalFoundDeadBodies() {
        return totalFoundDeadBodies;
    }

    @Override
    public int getCountUsedItem() {
        return usedItem;
    }

    @Override
    public int getTotalVictories() {
        return totalVictories;
    }

    @Override
    public int getTotalDefeats() {
        return totalDefeats;
    }

    @Override
    public int getTotalUseHealStation() {
        return totalUseHealStation;
    }

    @Override
    public int getTotalPlaceHealStation() {
        return totalPlaceHealStation;
    }

    @Override
    public int getTotalBoughtItems() {
        return totalBoughtItems;
    }

    @Override
    public int getTotalPlayingCount() {
        return totalPlayCount;
    }

    @Override
    public void setCountVillager(int count) {
        this.villager = count;
        try {
            NanamiTct.playerStats.update(uuid, "vil", count);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
        }
    }

    @Override
    public void setCountHealer(int count) {
        this.healer = count;
        try {
            NanamiTct.playerStats.update(uuid, "hea", count);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
        }
    }

    @Override
    public void setCountDetective(int count) {
        this.detective = count;
        try {
            NanamiTct.playerStats.update(uuid, "det", count);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
        }
    }

    @Override
    public void setCountWolf(int count) {
        this.wolf = count;
        try {
            NanamiTct.playerStats.update(uuid, "wol", count);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
        }
    }

    @Override
    public void setCountFanatic(int count) {
        this.fanatic = count;
        try {
            NanamiTct.playerStats.update(uuid, "fan", count);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
        }
    }

    @Override
    public void setCountFox(int count) {
        this.fox = count;
        try {
            NanamiTct.playerStats.update(uuid, "fox", count);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
        }
    }

    @Override
    public void setCountImmoral(int count) {
        this.immoral = count;
        try {
            NanamiTct.playerStats.update(uuid, "imm", count);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
        }
    }

    @Override
    public void setCountCustomRole(GameRole role, int count) {
        this.customRole.put(role, count);
    }

    @Override
    public void setCountDeath(int count) {
        this.death = count;
        try {
            NanamiTct.playerStats.update(uuid, "death", count);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
        }
    }

    @Override
    public void setTotalFoundDeadBodies(int count) {
        this.totalFoundDeadBodies = count;
        try {
            NanamiTct.playerStats.update(uuid, "found_deadbody", count);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
        }
    }

    @Override
    public void setCountUsedItem(int count) {
        this.usedItem = count;
        try {
            NanamiTct.playerStats.update(uuid, "use_item", count);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
        }
    }

    @Override
    public void setTotalVictories(int count) {
        this.totalVictories = count;
        try {
            NanamiTct.playerStats.update(uuid, "victory", count);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
        }
    }

    @Override
    public void setTotalDefeats(int count) {
        this.totalDefeats = count;
        try {
            NanamiTct.playerStats.update(uuid, "defeat", count);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
        }
    }

    @Override
    public void setTotalUseHealStation(int count) {
        this.totalUseHealStation = count;
        try {
            NanamiTct.playerStats.update(uuid, "use_heal_station", count);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
        }
    }

    @Override
    public void setTotalPlaceHealStation(int count) {
        this.totalPlaceHealStation = count;
        try {
            NanamiTct.playerStats.update(uuid, "place_heal_station", count);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
        }
    }

    @Override
    public void setTotalBoughtItems(int count) {
        this.totalBoughtItems = count;
        try {
            NanamiTct.playerStats.update(uuid, "bought_item", count);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
        }
    }

    @Override
    public void setTotalPlayingCount(int count) {
        this.totalPlayCount = count;
        try {
            NanamiTct.playerStats.update(uuid, "play_count", count);
        } catch (SQLException ex) {
            MySQLStatus.setSqlEnabled(false);
        }
    }
}
