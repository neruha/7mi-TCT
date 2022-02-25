package me.clockclap.tct.japanize;

import me.clockclap.tct.NanamiTct;
import org.bukkit.ChatColor;

import java.io.UnsupportedEncodingException;

public class Japanizer {

    public static String japanize(String input) throws UnsupportedEncodingException {
        //byte[] buf = input.getBytes("SJIS");
        String chr0 = NanamiTct.plugin.getTctConfig().getChat().getString("japanize-disabler", "#");
        String chr1 = NanamiTct.plugin.getTctConfig().getChat().getString("ime-disabler", "!");
        //v5 chr2: これは何？
        String chr2 = NanamiTct.plugin.getTctConfig().getChat().getString("escape", ".");
        //v5 already: すでに翻訳済み
        boolean color = NanamiTct.plugin.getTctConfig().getChat().getBoolean("coloured-chat", true);
        //v5 removed: ラグ爆弾
        boolean jpn = NanamiTct.plugin.getTctConfig().getChat().getBoolean("disable-playername-japanize", true);

        if (input.startsWith(chr0)) {
            return GoogleIME.convByGoogleIME(input.substring(1));
        } else if (input.startsWith(chr1)) {
            return KanaConverter.conv(input.substring(1));
        } else {
            return GoogleIME.convByGoogleIME(KanaConverter.conv(input));
        }
    }

    public static String japanizeColorCode(String input) throws UnsupportedEncodingException {
        return japanize(ChatColor.translateAlternateColorCodes('&', input));
    }
}