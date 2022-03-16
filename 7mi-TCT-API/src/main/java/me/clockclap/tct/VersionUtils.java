package me.clockclap.tct;

import org.bukkit.Bukkit;

import static org.bukkit.Bukkit.getServer;

public class VersionUtils {

    public static int V1_12_2 = 1122;
    public static int V1_8_8 = 188;
    public static int V1_8 = 180;

    public static int server_version = -1;

    public static boolean isHigherThanVersion(int version) {
        if (server_version == -1) {
            setVersion();
        }

        return server_version > version;
    }

    public static boolean isLowerThanVersion(int version) {
        if (server_version == -1) {
            setVersion();
        }

        return server_version < version;
    }

    public static void setVersion() {
        String semi_version = getServer().getClass().getPackage().getName();
        String server_version = semi_version.substring(semi_version.lastIndexOf('.') + 1);

        Bukkit.broadcastMessage(String.valueOf(server_version.indexOf("_")));
        Bukkit.broadcastMessage(server_version);

        if (server_version.indexOf("_") == 2) {
            VersionUtils.server_version = Integer.parseInt(server_version.replaceAll("[^0-9]", ""));
        } else {
            VersionUtils.server_version = Integer.parseInt(server_version.replaceAll("[^0-9]", "") + "0");
        }

        Bukkit.broadcastMessage(String.valueOf(VersionUtils.server_version));
    }
}