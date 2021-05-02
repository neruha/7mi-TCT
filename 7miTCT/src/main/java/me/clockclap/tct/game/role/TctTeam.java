package me.clockclap.tct.game.role;

import me.clockclap.tct.api.Reference;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public abstract class TctTeam implements GameTeam {

    private String displayName;
    private String name;
    private int index;
    private ChatColor color;
    private String winMessage;
    private String winTitle;
    private String winSubtitle;

    public TctTeam(int index) {
        this(index, "", "", ChatColor.RESET);
    }

    public TctTeam(int index, String name) {
        this(index, name, "", ChatColor.RESET);
    }

    public TctTeam(int index, String name, String displayName) {
        this(index, name, displayName, ChatColor.RESET);
    }

    public TctTeam(int index, String name, String displayName, ChatColor color) {
        this.name = name;
        this.displayName = displayName;
        this.index = index;
        this.color = color;
        this.winMessage = Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_VICTORY.replaceAll("%COLOR%", color.toString()).replaceAll("%TEAM%", displayName);
        this.winTitle = Reference.TCT_TITLE_MAIN_VICTORY.replaceAll("%COLOR%", color.toString()).replaceAll("%TEAM%", displayName);
        this.winSubtitle = Reference.TCT_TITLE_SUB_VICTORY;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getIndex() {
        return this.index;
    }

    public String getWinMessage() {
        return winMessage;
    }

    public String getWinTitle() {
        return winTitle;
    }

    public String getWinSubtitle() {
        return winSubtitle;
    }

    public void onVictory(Player p) {
        p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_SHOOT, 1.0F, 1.0F);
        p.sendTitle(getWinTitle(), getWinSubtitle(), 5, 40, 5);
        p.sendMessage(getWinMessage());
    }

    @Override
    public ChatColor getColor() {
        return this.color;
    }

    @Override
    public void setDisplayName(String name) {
        this.displayName = name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void setColor(ChatColor color) {
        this.color = color;
    }

    public void setWinMessage(String msg) {
        this.winMessage = msg;
    }

    public void setWinTitle(String title) {
        this.winTitle = title;
    }

    public void setWinSubtitle(String subtitle) {
        this.winSubtitle = subtitle;
    }
}
