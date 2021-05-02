package me.clockclap.tct.game.role;

import me.clockclap.tct.game.Game;

public class RoleCount {

    private Game game;
    private CustomRoleCount customRoleCount;
    private int villagersCount;
    private int healersCount;
    private int detectivesCount;
    private int wolvesCount;
    private int fanaticsCount;
    private int foxesCount;
    private int immoralCount;

    public RoleCount(Game game) {
        this.game = game;
        customRoleCount = new CustomRoleCount(game);
        villagersCount = 0;
        healersCount = 0;
        detectivesCount = 0;
        wolvesCount = 0;
        fanaticsCount = 0;
        foxesCount = 0;
        immoralCount = 0;
    }

    public CustomRoleCount getCustomRoleCount() {
        return customRoleCount;
    }

    public int getVillagersCount() {
        return this.villagersCount;
    }
    public int getHealersCount() {
        return this.healersCount;
    }
    public int getDetectivesCount() {
        return this.detectivesCount;
    }
    public int getWolvesCount() {
        return this.wolvesCount;
    }
    public int getFanaticsCount() {
        return this.fanaticsCount;
    }
    public int getFoxesCount() {
        return this.foxesCount;
    }
    public int getImmoralCount() {
        return this.immoralCount;
    }

    public void setVillagersCount(int value) {
        this.villagersCount = value;
    }
    public void setHealersCount(int value) {
        this.healersCount = value;
    }
    public void setDetectivesCount(int value) {
        this.detectivesCount = value;
    }
    public void setWolvesCount(int value) {
        this.wolvesCount = value;
    }
    public void setFanaticsCount(int value) {
        this.fanaticsCount = value;
    }
    public void setFoxesCount(int value) {
        this.foxesCount = value;
    }
    public void setImmoralCount(int value) {
        this.immoralCount = value;
    }

}
