package me.clockclap.tct.game.data;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.sql.MySQLStatus;
import me.clockclap.tct.game.role.GameRole;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TCTPlayerStat implements PlayerStat {

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
    private int kill;
    private int totalFoundDeadBodies;
    private int usedItem;
    private int totalVictories;
    private int totalDefeats;
    private int totalUseHealStation;
    private int totalPlaceHealStation;
    private int totalBoughtItems;
    private int totalPlayCount;

    public TCTPlayerStat(UUID uuid) {
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
        this.kill = 0;
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
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("vil");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getCountHealer() {
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("hea");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getCountDetective() {
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("det");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getCountWolf() {
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("wol");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getCountFanatic() {
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("fan");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getCountFox() {
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("fox");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getCountImmoral() {
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("imm");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getCountCustomRole(GameRole role) {
        if (customRole.containsKey(role)) return customRole.get(role);
        return 0;
    }

    @Override
    public int getCountDeath() {
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("death");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getCountKill() {
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("kills");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getTotalFoundDeadBodies() {
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("found_deadbody");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getCountUsedItem() {
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("use_item");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getTotalVictories() {
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("victory");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getTotalDefeats() {
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("defeat");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getTotalUseHealStation() {
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("use_heal_station");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getTotalPlaceHealStation() {
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("place_heal_station");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getTotalBoughtItems() {
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("bought_item");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public int getTotalPlayingCount() {
        if (NanamiTct.sqlConnection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            try {
                NanamiTct.sqlConnection.openConnection();
                PreparedStatement statement = NanamiTct.sqlConnection.getConnection().prepareStatement("SELECT * FROM tct_stats");
                ResultSet set = statement.executeQuery();
                int result = 0;
                while (set.next()) {
                    result = set.getInt("play_count");
                }
                set.close();
                statement.close();
                NanamiTct.sqlConnection.getConnection().close();
                return result;
            } catch (SQLException ex) {
                MySQLStatus.setSqlEnabled(false);
                return 0;
            }
        }
        return 0;
    }

    @Override
    public void setCountVillager(int count) {
        this.villager = count;
        try {
            NanamiTct.playerStats.update(uuid, "vil", count);
        } catch (SQLException ex) {
            //MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
        }
    }

    @Override
    public void setCountHealer(int count) {
        this.healer = count;
        try {
            NanamiTct.playerStats.update(uuid, "hea", count);
        } catch (SQLException ex) {
            //MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
        }
    }

    @Override
    public void setCountDetective(int count) {
        this.detective = count;
        try {
            NanamiTct.playerStats.update(uuid, "det", count);
        } catch (SQLException ex) {
            //MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
        }
    }

    @Override
    public void setCountWolf(int count) {
        this.wolf = count;
        try {
            NanamiTct.playerStats.update(uuid, "wol", count);
        } catch (SQLException ex) {
            //MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
        }
    }

    @Override
    public void setCountFanatic(int count) {
        this.fanatic = count;
        try {
            NanamiTct.playerStats.update(uuid, "fan", count);
        } catch (SQLException ex) {
            //MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
        }
    }

    @Override
    public void setCountFox(int count) {
        this.fox = count;
        try {
            NanamiTct.playerStats.update(uuid, "fox", count);
        } catch (SQLException ex) {
            //MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
        }
    }

    @Override
    public void setCountImmoral(int count) {
        this.immoral = count;
        try {
            NanamiTct.playerStats.update(uuid, "imm", count);
        } catch (SQLException ex) {
            //MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
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
            //MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
        }
    }

    @Override
    public void setCountKill(int count) {
        this.kill = count;
        try {
            NanamiTct.playerStats.update(uuid, "kills", count);
        } catch (SQLException ex) {
            //MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
        }
    }

    @Override
    public void setTotalFoundDeadBodies(int count) {
        this.totalFoundDeadBodies = count;
        try {
            NanamiTct.playerStats.update(uuid, "found_deadbody", count);
        } catch (SQLException ex) {
            //MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
        }
    }

    @Override
    public void setCountUsedItem(int count) {
        this.usedItem = count;
        try {
            NanamiTct.playerStats.update(uuid, "use_item", count);
        } catch (SQLException ex) {
            //MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
        }
    }

    @Override
    public void setTotalVictories(int count) {
        this.totalVictories = count;
        try {
            NanamiTct.playerStats.update(uuid, "victory", count);
        } catch (SQLException ex) {
            //MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
        }
    }

    @Override
    public void setTotalDefeats(int count) {
        this.totalDefeats = count;
        try {
            NanamiTct.playerStats.update(uuid, "defeat", count);
        } catch (SQLException ex) {
            //MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
        }
    }

    @Override
    public void setTotalUseHealStation(int count) {
        this.totalUseHealStation = count;
        try {
            NanamiTct.playerStats.update(uuid, "use_heal_station", count);
        } catch (SQLException ex) {
            //MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
        }
    }

    @Override
    public void setTotalPlaceHealStation(int count) {
        this.totalPlaceHealStation = count;
        try {
            NanamiTct.playerStats.update(uuid, "place_heal_station", count);
        } catch (SQLException ex) {
            //MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
        }
    }

    @Override
    public void setTotalBoughtItems(int count) {
        this.totalBoughtItems = count;
        try {
            NanamiTct.playerStats.update(uuid, "bought_item", count);
        } catch (SQLException ex) {
            //MySQLStatus.setSqlEnabled(false);
            ex.printStackTrace();
        }
    }

    @Override
    public void setTotalPlayingCount(int count) {
        this.totalPlayCount = count;
        try {
            NanamiTct.playerStats.update(uuid, "play_count", count);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void increaseItemUsed() {
        if (checkSQLConnection()) {
            this.usedItem++;
            try {
                NanamiTct.playerStats.update(uuid, "use_item", this.usedItem);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean checkSQLConnection() {
        return MySQLStatus.isSqlEnabled() && NanamiTct.sqlConnection.getConnection() != null;
    }
}