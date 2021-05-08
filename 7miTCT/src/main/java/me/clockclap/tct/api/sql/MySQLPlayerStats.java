package me.clockclap.tct.api.sql;

import me.clockclap.tct.game.Game;
import me.clockclap.tct.game.data.PlayerStat;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomItems;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MySQLPlayerStats {

    private Game game;
    private MySQLConnection connection;

    private Map<UUID, PlayerStat> playerStatMap;

    public MySQLPlayerStats(MySQLConnection connection, Game game) {
        this.game = game;
        this.connection = connection;
        this.playerStatMap = new HashMap<>();
    }

    public MySQLConnection getConnection() {
        return connection;
    }

    public Game getGame() {
        return game;
    }

    public void insert(PlayerStat stat) throws SQLException {
        if(connection.getConnection() != null) {
            connection.openConnection();
            PreparedStatement statement = connection.getConnection().prepareStatement("INSERT INTO tct_stats (uuid,vil,hea,det,wol,fan,fox,imm,death," +
                    "found_deadbody,use_item,victory,defeat,use_heal_station,place_heal_station,bought_item,play_count) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, stat.getUUID().toString());
            statement.setInt(2, stat.getCountVillager());
            statement.setInt(3, stat.getCountHealer());
            statement.setInt(4, stat.getCountDetective());
            statement.setInt(5, stat.getCountWolf());
            statement.setInt(6, stat.getCountFanatic());
            statement.setInt(7, stat.getCountFox());
            statement.setInt(8, stat.getCountImmoral());
            statement.setInt(9, stat.getCountDeath());
            statement.setInt(10, stat.getTotalFoundDeadBodies());
            statement.setInt(11, stat.getCountUsedItem());
            statement.setInt(12, stat.getTotalVictories());
            statement.setInt(13, stat.getTotalDefeats());
            statement.setInt(14, stat.getTotalUseHealStation());
            statement.setInt(15, stat.getTotalPlaceHealStation());
            statement.setInt(16, stat.getTotalBoughtItems());
            statement.setInt(17, stat.getTotalPlayingCount());
            statement.execute();
            playerStatMap.put(stat.getUUID(), stat);
            statement.close();
            connection.getConnection().close();
        }
    }

    public void update(UUID uuid, String key, int value) throws SQLException {
        if(connection.getConnection() != null) {
            connection.openConnection();
            PreparedStatement statement = connection.getConnection().prepareStatement("UPDATE tct_stats SET ? = ? WHERE uuid = ?");
            statement.setString(1, key);
            statement.setInt(2, value);
            statement.setString(3, uuid.toString());
            statement.execute();
            statement.close();
            connection.getConnection().close();
        }
    }

    public void delete(UUID uuid) throws SQLException {
        if(connection.getConnection() != null) {
            connection.openConnection();
            PreparedStatement statement = connection.getConnection().prepareStatement("DELETE FROM tct_stats WHERE uuid = ?");
            statement.setString(1, uuid.toString());
            statement.execute();
            playerStatMap.remove(uuid);
            statement.close();
            connection.getConnection().close();
        }
    }

    public Map<UUID, PlayerStat> getPlayerStats() {
        return playerStatMap;
    }

    public PlayerStat getStat(UUID uuid) {
        if(playerStatMap.containsKey(uuid)) return playerStatMap.get(uuid);
        return null;
    }

}
