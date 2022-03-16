package me.clockclap.tct.api.sql;

import me.clockclap.tct.NanamiTctApi;
import me.clockclap.tct.api.Reference;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private static final String hostDef = "localhost";
    private static final int portDef = 3306;
    private static final String databaseDef = "";
    private static final String usernameDef = "";
    private static final String passwordDef = "";
    private static final String optionDef = "?allowPublicKeyRetrieval=true&useSSL=false";

    private Connection connection;

    private String host;
    private int port;
    private String database;
    private String username;
    private String password;
    private String option;

    public MySQLConnection() {
        this(hostDef, portDef, databaseDef, usernameDef, passwordDef, optionDef);
    }

    public MySQLConnection(String host, int port, String database, String username, String password, String option) {
        this.host = host;
        this.port = port;
        this.database = database;
        this.username = username;
        this.password = password;
        this.option = option;
    }

    public void openConnection() throws SQLException {
        if(connection != null && !connection.isClosed()) {
            return;
        }
        synchronized (this) {
            /**
             * v5 - [Patched] don't need this
            boolean found = false;
            Enumeration<Driver> drivers = DriverManager.getDrivers();

            while (drivers.hasMoreElements()){
                Driver driver = drivers.nextElement();
                if (driver.equals(new com.mysql.cj.jdbc.Driver())){
                    found = true;
                    break;
                }
            }

            if (!found) DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
             */

            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + this.option, this.username, this.password);
            } catch (Exception e) {
                NanamiTctApi.plugin.getLogger().warning(Reference.SQL_ERROR);

                MySQLStatus.setSqlEnabled(false);

                connection = null;
            }
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setDefault() {
        this.host = hostDef;
        this.port = portDef;
        this.database = databaseDef;
        this.username = usernameDef;
        this.password = passwordDef;
        this.option = optionDef;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getOption() {
        return option;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOption(String option) {
        this.option = option;
    }

}
