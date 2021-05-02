package me.clockclap.tct.japanize;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.UnsupportedEncodingException;

public class Japanizer {

    public static String japanize(String input) throws UnsupportedEncodingException {
        String msg = input;
        String result = "";
        boolean ime = true;
        byte[] buf = msg.getBytes("SJIS");
        String chr0 = NanamiTct.plugin.getTctConfig().getChat().getString("japanize-disabler", "#");
        String chr1 = NanamiTct.plugin.getTctConfig().getChat().getString("ime-disabler", "!");
        String chr2 = NanamiTct.plugin.getTctConfig().getChat().getString("escape", ".");
        boolean color = NanamiTct.plugin.getTctConfig().getChat().getBoolean("coloured-chat", true);
        boolean jpn = NanamiTct.plugin.getTctConfig().getChat().getBoolean("disable-playername-japanize", true);
        if(msg.length() == buf.length && !msg.startsWith(chr0)) {
            if(msg.startsWith(chr1)) {
                msg = msg.substring(1);
                ime = false;
            }
            if(msg.startsWith(chr2)) {
                msg = msg.substring(1);
            }
            String beforeconvert = msg;
            if(color) {
                beforeconvert = beforeconvert.replaceAll("&0", "§0");
                beforeconvert = beforeconvert.replaceAll("&1", "§1");
                beforeconvert = beforeconvert.replaceAll("&2", "§2");
                beforeconvert = beforeconvert.replaceAll("&3", "§3");
                beforeconvert = beforeconvert.replaceAll("&4", "§4");
                beforeconvert = beforeconvert.replaceAll("&5", "§5");
                beforeconvert = beforeconvert.replaceAll("&6", "§6");
                beforeconvert = beforeconvert.replaceAll("&7", "§7");
                beforeconvert = beforeconvert.replaceAll("&8", "§8");
                beforeconvert = beforeconvert.replaceAll("&9", "§9");
                beforeconvert = beforeconvert.replaceAll("&a", "§a");
                beforeconvert = beforeconvert.replaceAll("&b", "§b");
                beforeconvert = beforeconvert.replaceAll("&c", "§c");
                beforeconvert = beforeconvert.replaceAll("&d", "§d");
                beforeconvert = beforeconvert.replaceAll("&e", "§e");
                beforeconvert = beforeconvert.replaceAll("&f", "§f");
                beforeconvert = beforeconvert.replaceAll("&l", "§l");
                beforeconvert = beforeconvert.replaceAll("&m", "§m");
                beforeconvert = beforeconvert.replaceAll("&n", "§n");
                beforeconvert = beforeconvert.replaceAll("&o", "§o");
                beforeconvert = beforeconvert.replaceAll("&k", "§k");
                beforeconvert = beforeconvert.replaceAll("&r", "§7");
                beforeconvert = beforeconvert.replaceAll("&A", "§a");
                beforeconvert = beforeconvert.replaceAll("&B", "§b");
                beforeconvert = beforeconvert.replaceAll("&C", "§c");
                beforeconvert = beforeconvert.replaceAll("&D", "§d");
                beforeconvert = beforeconvert.replaceAll("&E", "§e");
                beforeconvert = beforeconvert.replaceAll("&F", "§f");
                beforeconvert = beforeconvert.replaceAll("&L", "§l");
                beforeconvert = beforeconvert.replaceAll("&M", "§m");
                beforeconvert = beforeconvert.replaceAll("&N", "§n");
                beforeconvert = beforeconvert.replaceAll("&O", "§o");
                beforeconvert = beforeconvert.replaceAll("&K", "§k");
                beforeconvert = beforeconvert.replaceAll("&R", "§7");
                msg = msg.replaceAll("&0", "%COLOR_CH0719682009%");
                msg = msg.replaceAll("&1", "%COLOR_CH1968829860%");
                msg = msg.replaceAll("&2", "%COLOR_CH2386997101%");
                msg = msg.replaceAll("&3", "%COLOR_CH3708126056%");
                msg = msg.replaceAll("&4", "%COLOR_CH4675978972%");
                msg = msg.replaceAll("&5", "%COLOR_CH5061000271%");
                msg = msg.replaceAll("&6", "%COLOR_CH6999177200%");
                msg = msg.replaceAll("&7", "%COLOR_CH7215630099%");
                msg = msg.replaceAll("&8", "%COLOR_CH8017596028%");
                msg = msg.replaceAll("&9", "%COLOR_CH9683716961%");
                msg = msg.replaceAll("&a", "%COLOR_CHS0581265105%");
                msg = msg.replaceAll("&b", "%COLOR_CHS1983716587%");
                msg = msg.replaceAll("&c", "%COLOR_CHS2689102282%");
                msg = msg.replaceAll("&d", "%COLOR_CHS3611000193%");
                msg = msg.replaceAll("&e", "%COLOR_CHS4871006899%");
                msg = msg.replaceAll("&f", "%COLOR_CHS5839195606%");
                msg = msg.replaceAll("&l", "%COLOR_CHR0158849715%");
                msg = msg.replaceAll("&m", "%COLOR_CHR1857120067%");
                msg = msg.replaceAll("&n", "%COLOR_CHR2547106916%");
                msg = msg.replaceAll("&o", "%COLOR_CHR3050819923%");
                msg = msg.replaceAll("&k", "%COLOR_CHR4175912001%");
                msg = msg.replaceAll("&r", "%COLOR_CHRESET%");
                msg = msg.replaceAll("&A", "%COLOR_CHS0581265105%");
                msg = msg.replaceAll("&B", "%COLOR_CHS1983716587%");
                msg = msg.replaceAll("&C", "%COLOR_CHS2689102282%");
                msg = msg.replaceAll("&D", "%COLOR_CHS3611000193%");
                msg = msg.replaceAll("&E", "%COLOR_CHS4871006899%");
                msg = msg.replaceAll("&F", "%COLOR_CHS5839195606%");
                msg = msg.replaceAll("&L", "%COLOR_CHR0158849715%");
                msg = msg.replaceAll("&M", "%COLOR_CHR1857120067%");
                msg = msg.replaceAll("&N", "%COLOR_CHR2547106916%");
                msg = msg.replaceAll("&O", "%COLOR_CHR3050819923%");
                msg = msg.replaceAll("&K", "%COLOR_CHR4175912001%");
                msg = msg.replaceAll("&R", "%COLOR_CHRESET%");
            }
            result = KanaConverter.conv(msg);
            if(ime) {
                result = GoogleIME.convByGoogleIME(result);
            }
            if(jpn) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    String name = NanamiTct.utilities.resetColor(p.getName());
                    String japanizedName = KanaConverter.conv(name);
                    if(ime) japanizedName = GoogleIME.convByGoogleIME(japanizedName);
                    result = result.replaceAll(japanizedName, name);
                }
            }
            if(color) {
                result = result.replaceAll("§０", "§0");
                result = result.replaceAll("§１", "§1");
                result = result.replaceAll("§２", "§2");
                result = result.replaceAll("§３", "§3");
                result = result.replaceAll("§４", "§4");
                result = result.replaceAll("§５", "§5");
                result = result.replaceAll("§６", "§6");
                result = result.replaceAll("§７", "§7");
                result = result.replaceAll("§８", "§8");
                result = result.replaceAll("§９", "§9");
            }
            String format = NanamiTct.plugin.getTctConfig().getChat().getString("japanize-format", Reference.JAPANIZE_FORMAT);
            format = ChatColor.translateAlternateColorCodes('&', format);
            result = format.replaceAll("%MESSAGE%", beforeconvert).replaceAll("%JAPANIZE%", result);
        } else {
            if(msg.indexOf(chr0) == 0) {
                if(msg.indexOf(chr1) == 1) {
                    msg = msg.substring(2);
                } else {
                    msg = msg.substring(1);
                }
            } else if(msg.indexOf(chr1) == 0) {
                if(msg.indexOf(chr0) == 1) {
                    msg = msg.substring(2);
                } else {
                    msg = msg.substring(1);
                }
            }
            if(color) {
                msg = msg.replaceAll("&0", "§0");
                msg = msg.replaceAll("&1", "§1");
                msg = msg.replaceAll("&2", "§2");
                msg = msg.replaceAll("&3", "§3");
                msg = msg.replaceAll("&4", "§4");
                msg = msg.replaceAll("&5", "§5");
                msg = msg.replaceAll("&6", "§6");
                msg = msg.replaceAll("&7", "§7");
                msg = msg.replaceAll("&8", "§8");
                msg = msg.replaceAll("&9", "§9");
                msg = msg.replaceAll("&a", "§a");
                msg = msg.replaceAll("&b", "§b");
                msg = msg.replaceAll("&c", "§c");
                msg = msg.replaceAll("&d", "§d");
                msg = msg.replaceAll("&e", "§e");
                msg = msg.replaceAll("&f", "§f");
                msg = msg.replaceAll("&l", "§l");
                msg = msg.replaceAll("&m", "§m");
                msg = msg.replaceAll("&n", "§n");
                msg = msg.replaceAll("&o", "§o");
                msg = msg.replaceAll("&k", "§k");
                msg = msg.replaceAll("&r", "§r");
            }
            result = msg;
        }

        return result;
    }

}
