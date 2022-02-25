package me.clockclap.tct.api.sql;

import me.clockclap.tct.NanamiTctApi;
import me.clockclap.tct.game.TCTGame;
import me.clockclap.tct.game.data.PlayerStat;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MySQLPlayerStats {

    private final TCTGame game;
    private final MySQLConnection connection;

    private final Map<UUID, PlayerStat> playerStatMap;

    public MySQLPlayerStats(MySQLConnection connection, TCTGame game) {
        this.game = game;
        this.connection = connection;
        this.playerStatMap = new HashMap<>();
    }

    public MySQLConnection getConnection() {
        return connection;
    }

    public TCTGame getGame() {
        return game;
    }

    public void createTable() throws SQLException {
        if (connection.getConnection() != null && MySQLStatus.isSqlEnabled()) {
            connection.openConnection();
            PreparedStatement statement = connection.getConnection().prepareStatement("" +
                    "CREATE TABLE IF NOT EXISTS tct_stats (uuid VARCHAR(50),vil INT,hea INT,det INT,wol INT,fan INT,fox INT,imm INT,death INT,kills INT,found_deadbody INT," +
                    "use_item INT,victory INT,defeat INT,use_heal_station INT,place_heal_station INT,bought_item INT,play_count INT)");
            statement.execute();
            statement.close();
            NanamiTctApi.connection.getConnection().close();
        }
    }

    public void insert(PlayerStat stat) throws SQLException {
        if (connection.getConnection() != null) {
            connection.openConnection();
            //(uuid,vil,hea,det,wol,fan,fox,imm,death,kills,found_deadbody,use_item,victory,defeat,use_heal_station,place_heal_station,bought_item,play_count)
            String sql = "INSERT INTO `tct_stats` (uuid,vil,hea,det,wol,fan,fox,imm,death,kills,found_deadbody,use_item,victory,defeat,use_heal_station,place_heal_station,bought_item,play_count)" +
                    " SELECT * FROM (SELECT ? as uuid,0 as vil,0 as hea,0 as det,0 as fol,0 as fan,0 as fox,0 as imm,0 as death,0 as kills,0 as found_deadbody,0 as use_item," +
                    "0 as victory,0 as defeat,0 as use_heal_station,0 as place_heal_station,0 as bought_item,0 as play_count) AS tmp WHERE NOT EXISTS (SELECT * FROM `tct_stats` WHERE `uuid`=?) LIMIT 1;";
            PreparedStatement statement = connection.getConnection().prepareStatement(sql);
            statement.setString(1, stat.getUUID().toString());
            statement.setString(2, stat.getUUID().toString());
            statement.execute();
            if (playerStatMap.containsKey(stat.getUUID())) playerStatMap.put(stat.getUUID(), stat);
            statement.close();
            connection.getConnection().close();
        }
    }

    public void update(UUID uuid, String key, int value) throws SQLException {
        if (connection.getConnection() != null) {
            connection.openConnection();
            PreparedStatement statement = connection.getConnection().prepareStatement("UPDATE tct_stats SET `" + key + "`=" + value + " WHERE uuid=?");
            statement.setString(1, uuid.toString());
            statement.execute();
            statement.close();
            connection.getConnection().close();
        }
    }

    public void delete(UUID uuid) throws SQLException {
        if (connection.getConnection() != null) {
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
        if (playerStatMap.containsKey(uuid)) return playerStatMap.get(uuid);
        PlayerStat stat = NanamiTctApi.utilities.createNewStat(uuid);
        playerStatMap.put(uuid, stat);
        return stat;
    }
}