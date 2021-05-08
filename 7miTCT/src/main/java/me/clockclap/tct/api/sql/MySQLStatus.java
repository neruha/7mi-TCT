package me.clockclap.tct.api.sql;

public class MySQLStatus {

    private static boolean sqlEnabled = false;

    public static boolean isSqlEnabled() {
        return sqlEnabled;
    }

    public static void setSqlEnabled(boolean sqlEnabled) {
        MySQLStatus.sqlEnabled = sqlEnabled;
    }

}
