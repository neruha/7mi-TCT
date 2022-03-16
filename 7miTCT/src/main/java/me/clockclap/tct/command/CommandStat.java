package me.clockclap.tct.command;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.sql.MySQLStatus;
import me.clockclap.tct.game.data.PlayerStat;
import me.clockclap.tct.game.data.TCTPlayerStat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class CommandStat implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (MySQLStatus.isSqlEnabled()) {
                PlayerStat stat = NanamiTct.playerStats.getStat(p.getUniqueId());
                if(stat == null) {
                    PlayerStat newStat = new TCTPlayerStat(p.getUniqueId());
                    try {
                        NanamiTct.playerStats.insert(newStat);
                        stat = newStat;
                    } catch(SQLException ex) {
                        ex.printStackTrace();
                        MySQLStatus.setSqlEnabled(false);
                        p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "* 統計の記録が無効化されているため表示できません *");
                        return true;
                    }
                }
                String msg = ChatColor.YELLOW + "=-=-=- ななみTCTの統計 -=-=-=" + "\n" +
                ChatColor.AQUA + "プレイ回数: " + ChatColor.RED + stat.getTotalPlayingCount() + ChatColor.AQUA + ", " + "死体発見回数: " + ChatColor.RED + stat.getTotalFoundDeadBodies() + "\n" +
                ChatColor.AQUA + "村人になった回数: " + ChatColor.RED + stat.getCountVillager() + ChatColor.AQUA + ", " + "アイテム購入回数: " + ChatColor.RED + stat.getTotalBoughtItems() + "\n" +
                ChatColor.AQUA + "医者になった回数: " + ChatColor.RED + stat.getCountHealer() + ChatColor.AQUA + ", " + "勝利回数: " + ChatColor.RED + stat.getTotalVictories() + "\n" +
                ChatColor.AQUA + "探偵になった回数: " + ChatColor.RED + stat.getCountDetective() + ChatColor.AQUA + ", " + "敗北回数: " + ChatColor.RED + stat.getTotalDefeats() + "\n" +
                ChatColor.AQUA + "人狼になった回数: " + ChatColor.RED + stat.getCountWolf() + ChatColor.AQUA + ", " + "アイテム使用回数: " + ChatColor.RED + stat.getCountUsedItem() + "\n" +
                ChatColor.AQUA + "狂人になった回数: " + ChatColor.RED + stat.getCountFanatic() + ChatColor.AQUA + ", " + "回復使用回数: " + ChatColor.RED + stat.getTotalUseHealStation() + "\n" +
                ChatColor.AQUA + "妖狐になった回数: " + ChatColor.RED + stat.getCountFox() + ChatColor.AQUA + ", " + "回復設置回数: " + ChatColor.RED + stat.getTotalPlaceHealStation() + "\n" +
                ChatColor.AQUA + "背徳者になった回数: " + ChatColor.RED + stat.getCountImmoral() + ChatColor.AQUA + ", " + "殺害回数: " + ChatColor.RED + stat.getCountKill() + "\n" +
                ChatColor.AQUA + "死亡回数: " + ChatColor.RED + stat.getCountDeath() + "\n" +
                ChatColor.YELLOW + "=-=-=-=-=-=-=-=-=-=-=-=-=";
                p.sendMessage(msg);
                return true;
            }
            p.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "* 統計の記録が無効化されているため表示できません *");
            return true;
        }
        sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "* このコマンドはプレイヤーからのみ実行できます *");
        return true;
    }

}
