package me.clockclap.tct.game;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.data.TctPlayerData;
import me.clockclap.tct.game.role.GameRole;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.game.role.RoleCount;
import me.clockclap.tct.item.CustomItems;
import net.minecraft.server.v1_12_R1.CommandReplaceItem;
import net.minecraft.server.v1_12_R1.MathHelper;
import org.bukkit.*;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.sql.Ref;
import java.util.Collection;
import java.util.Random;
import java.util.prefs.InvalidPreferencesFormatException;

public class Game {

    private NanamiTct plugin;
    private GameReference reference;
    private BossBar bar;
    private int remainingSeconds;
    private int realRemainingSeconds;
    private BukkitTask timer;
    private BukkitTask preTimer;
    private RoleCount roleCount;
    private int neededPlayers;

    public Game(NanamiTct plugin) {
        this.plugin = plugin;
        this.reference = new GameReference(plugin);
        this.remainingSeconds = 0;
        this.realRemainingSeconds = 0;
        this.roleCount = new RoleCount(this);
        this.neededPlayers = 0;
    }

    public boolean preStart(Location loc) {
        boolean success = false;
        FileConfiguration config = plugin.getTctConfig().getConfig();
        String villagersCount = /*config.getString("roles.count.villagers", "1:-1")*/ "1:-1";
        String healersCount = config.getString("roles.count.healers", "1");
        String detectivesCount = config.getString("roles.count.detectives", "1");
        String wolvesCount = config.getString("roles.count.wolves", "1");
        String fanaticsCount = config.getString("roles.count.fanatics", "1");
        String foxesCount = config.getString("roles.count.foxes", "1");
        int villagersMin = 1;
        int villagersMax = -1;
        int healersMin = 1;
        int healersMax = 1;
        int detectivesMin = 1;
        int detectivesMax = 1;
        int wolvesMin = 1;
        int wolvesMax = 1;
        int fanaticsMin = 1;
        int fanaticsMax = 1;
        int foxesMin = 1;
        int foxesMax = 1;
        if(villagersCount.contains(":")) {
            String[] count = villagersCount.split(":", 0);
            String min_ = count[0];
            String max_ = count[1];
            int min = Integer.parseInt(min_);
            int max = Integer.parseInt(max_);
            villagersMin = min;
            villagersMax = max;
        } else {
            int num = Integer.parseInt(villagersCount);
            villagersMax = num;
            villagersMin = num;
        }
        if(healersCount.contains(":")) {
            String[] count = healersCount.split(":");
            int min = Integer.parseInt(count[0]);
            int max = Integer.parseInt(count[1]);
            healersMin = min;
            healersMax = max;
        } else {
            int num = Integer.parseInt(healersCount);
            healersMax = num;
            healersMin = num;
        }
        if(detectivesCount.contains(":")) {
            String[] count = detectivesCount.split(":");
            int min = Integer.parseInt(count[0]);
            int max = Integer.parseInt(count[1]);
            detectivesMin = min;
            detectivesMax = max;
        } else {
            int num = Integer.parseInt(detectivesCount);
            detectivesMax = num;
            detectivesMin = num;
        }
        if(wolvesCount.contains(":")) {
            String[] count = wolvesCount.split(":");
            int min = Integer.parseInt(count[0]);
            int max = Integer.parseInt(count[1]);
            wolvesMin = min;
            wolvesMax = max;
        } else {
            int num = Integer.parseInt(wolvesCount);
            wolvesMax = num;
            wolvesMin = num;
        }
        if(fanaticsCount.contains(":")) {
            String[] count = fanaticsCount.split(":");
            int min = Integer.parseInt(count[0]);
            int max = Integer.parseInt(count[1]);
            fanaticsMin = min;
            fanaticsMax = max;
        } else {
            int num = Integer.parseInt(fanaticsCount);
            fanaticsMax = num;
            fanaticsMin = num;
        }
        if(foxesCount.contains(":")) {
            String[] count = foxesCount.split(":");
            int min = Integer.parseInt(count[0]);
            int max = Integer.parseInt(count[1]);
            foxesMin = min;
            foxesMax = max;
        } else {
            int num = Integer.parseInt(foxesCount);
            foxesMax = num;
            foxesMin = num;
        }
        int resultCount = villagersMin + healersMin + detectivesMin + wolvesMin + fanaticsMin + foxesMin;
        neededPlayers = resultCount;
        if(resultCount <= Bukkit.getOnlinePlayers().size()) {
            success = true;
        }
        if(success == true) {
            getReference().setGameState(GameState.STARTING);
            final int[] sec = {plugin.getTctConfig().getConfig().getInt("countdown.prestart", 10) + 1};
            getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_STARTING.replaceAll("%SECOND%", String.valueOf(sec[0])));
            Bukkit.getServer().broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_GAME_STARTED);
            BukkitTask timer = new BukkitRunnable() {
                @Override
                public void run() {
                    sec[0] = sec[0] - 1;
                    int j = sec[0];
                    getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_STARTING.replaceAll("%SECOND%", String.valueOf(j)));
                    getBar().setProgress((1.0 / plugin.getTctConfig().getConfig().getInt("countdown.prestart", 10)) * j);
                    if (j <= 5 && j > 0) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.5F, 1F);
                        }
                    }
                    if (j <= 0) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.5F, 1F);
                        }
                        start(loc);
                        this.cancel();
                    }
                }
            }.runTaskTimer(plugin, 0L, 20L);
            setPreTimer(timer);
            /**/
            return true;
        }
        return false;
    }

    private void giveRole(int playersCount) {
        FileConfiguration config = plugin.getTctConfig().getConfig();
        String villagersCount = /*config.getString("roles.count.villagers", "1:-1")*/ "1:-1";
        String healersCount = config.getString("roles.count.healers", "1");
        String detectivesCount = config.getString("roles.count.detectives", "1");
        String wolvesCount = config.getString("roles.count.wolves", "1");
        String fanaticsCount = config.getString("roles.count.fanatics", "1");
        String foxesCount = config.getString("roles.count.foxes", "1");
        int villagersMin = 1;
        int villagersMax = -1;
        int healersMin = 1;
        int healersMax = 1;
        int detectivesMin = 1;
        int detectivesMax = 1;
        int wolvesMin = 1;
        int wolvesMax = 1;
        int fanaticsMin = 1;
        int fanaticsMax = 1;
        int foxesMin = 1;
        int foxesMax = 1;
        if(villagersCount.contains(":")) {
            String[] count = villagersCount.split(":", 0);
            String min_ = count[0];
            String max_ = count[1];
            int min = Integer.parseInt(min_);
            int max = Integer.parseInt(max_);
            villagersMin = min;
            villagersMax = max;
        } else {
            int num = Integer.parseInt(villagersCount);
            villagersMax = num;
            villagersMin = num;
        }
        if(healersCount.contains(":")) {
            String[] count = healersCount.split(":");
            int min = Integer.parseInt(count[0]);
            int max = Integer.parseInt(count[1]);
            healersMin = min;
            healersMax = max;
        } else {
            int num = Integer.parseInt(healersCount);
            healersMax = num;
            healersMin = num;
        }
        if(detectivesCount.contains(":")) {
            String[] count = detectivesCount.split(":");
            int min = Integer.parseInt(count[0]);
            int max = Integer.parseInt(count[1]);
            detectivesMin = min;
            detectivesMax = max;
        } else {
            int num = Integer.parseInt(detectivesCount);
            detectivesMax = num;
            detectivesMin = num;
        }
        if(wolvesCount.contains(":")) {
            String[] count = wolvesCount.split(":");
            int min = Integer.parseInt(count[0]);
            int max = Integer.parseInt(count[1]);
            wolvesMin = min;
            wolvesMax = max;
        } else {
            int num = Integer.parseInt(wolvesCount);
            wolvesMax = num;
            wolvesMin = num;
        }
        if(fanaticsCount.contains(":")) {
            String[] count = fanaticsCount.split(":");
            int min = Integer.parseInt(count[0]);
            int max = Integer.parseInt(count[1]);
            fanaticsMin = min;
            fanaticsMax = max;
        } else {
            int num = Integer.parseInt(fanaticsCount);
            fanaticsMax = num;
            fanaticsMin = num;
        }
        if(foxesCount.contains(":")) {
            String[] count = foxesCount.split(":");
            int min = Integer.parseInt(count[0]);
            int max = Integer.parseInt(count[1]);
            foxesMin = min;
            foxesMax = max;
        } else {
            int num = Integer.parseInt(foxesCount);
            foxesMax = num;
            foxesMin = num;
        }
        int resultCount = villagersMin + healersMin + detectivesMin + wolvesMin + fanaticsMin + foxesMin;
        if(playersCount > resultCount) {
            Collection<? extends Player> players = Bukkit.getOnlinePlayers();
            for(Player p : Bukkit.getOnlinePlayers()) {
                if (getReference().PLAYERDATA.get(p.getName()).getRole() != GameRoles.VILLAGER) {
                    players.remove(p);
                }
            }
            for(Player p : players) {
                int remaining = playersCount;
                PlayerData data = getReference().PLAYERDATA.get(p.getName());
                Random rand = new Random();
                int role = rand.nextInt(5) + 1;
                if (role == GameRoles.HEALER.getIndex()) {
                    if(getRoleCount().getHealersCount() < healersMax) {
                        data.setRole(GameRoles.HEALER);
                        getRoleCount().setHealersCount(getRoleCount().getHealersCount() + 1);
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_YOU_ARE_HEALER);
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_DESCRIPTION_HEALER);
                        p.setFoodLevel(1);
                    }
                    continue;
                }
                if (role == GameRoles.DETECTIVE.getIndex()) {
                    if(getRoleCount().getDetectivesCount() < detectivesMax) {
                        data.setRole(GameRoles.DETECTIVE);
                        getRoleCount().setDetectivesCount(getRoleCount().getDetectivesCount() + 1);
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_YOU_ARE_DETECTIVE);
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_DESCRIPTION_DETECTIVE);
                        p.setFoodLevel(1);
                    }
                    continue;
                }
                if (role == GameRoles.WOLF.getIndex()) {
                    if(getRoleCount().getWolvesCount() < wolvesMax) {
                        data.setRole(GameRoles.WOLF);
                        getRoleCount().setWolvesCount(getRoleCount().getWolvesCount() + 1);
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_YOU_ARE_WOLF);
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_DESCRIPTION_WOLF);
                        p.setFoodLevel(1);
                    }
                    continue;
                }
                if (role == GameRoles.FANATIC.getIndex()) {
                    if(getRoleCount().getFanaticsCount() < fanaticsMax) {
                        data.setRole(GameRoles.FANATIC);
                        getRoleCount().setFanaticsCount(getRoleCount().getFanaticsCount() + 1);
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_YOU_ARE_FANATIC);
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_DESCRIPTION_FANATIC);
                        p.setFoodLevel(1);
                    }
                    continue;
                }
                if (role == GameRoles.FOX.getIndex()) {
                    if(getRoleCount().getFoxesCount() < foxesMax) {
                        data.setRole(GameRoles.FOX);
                        getRoleCount().setFoxesCount(getRoleCount().getFoxesCount() + 1);
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_YOU_ARE_FOX);
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_DESCRIPTION_FOX);
                        p.setFoodLevel(1);
                    }
                    continue;
                }
            }
        }
    }

    public void giveItem() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            PlayerData data = getReference().PLAYERDATA.get(p.getName());
            if(!data.isSpectator()) {
                p.getInventory().clear();
                p.getInventory().setItem(5, CustomItems.QUICKCHAT_A.getItemStack());
                p.getInventory().setItem(6, CustomItems.QUICKCHAT_B.getItemStack());
                p.getInventory().setItem(7, CustomItems.QUICKCHAT_C.getItemStack());
                p.getInventory().setItem(8, CustomItems.QUICKCHAT_D.getItemStack());
            }
        }
    }

    public void start(Location loc) {
        getReference().setGameState(GameState.GAMING);
        Bukkit.getServer().broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_READY_END);
        int sec = plugin.getTctConfig().getConfig().getInt("countdown.game", 240) + 1;
        setRemainingSeconds(sec);
        setRealRemainingSeconds(sec);
        int playersCount = Bukkit.getOnlinePlayers().size();
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.getGameMode() == GameMode.CREATIVE || p.getGameMode() == GameMode.SPECTATOR) {
                playersCount--;
                getReference().PLAYERDATA.get(p.getName()).setRole(GameRoles.SPEC);
                getReference().PLAYERDATA.get(p.getName()).setSpectator(true);
            } else {
                p.setGameMode(GameMode.SURVIVAL);
                getReference().PLAYERDATA.get(p.getName()).setRole(GameRoles.VILLAGER);
                getReference().PLAYERDATA.get(p.getName()).setSpectator(false);
            }
        }
        if(playersCount < neededPlayers) {
            Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_PLAYERS_NEEDED);
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 1.5F, 1F);
                getReference().PLAYERDATA.get(p.getName()).setRole(GameRoles.SPEC);
                getReference().PLAYERDATA.get(p.getName()).setSpectator(true);
            }
            getReference().setGameState(GameState.WAITING);
            getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_WAITING);
            return;
        }
        giveRole(playersCount);
        giveItem();
        for(Player p : Bukkit.getOnlinePlayers()) {
            PlayerData data = getReference().PLAYERDATA.get(p.getName());
            if(data.getRole() == GameRoles.VILLAGER) {
                getRoleCount().setVillagersCount(getRoleCount().getVillagersCount() + 1);
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_YOU_ARE_VILLAGER);
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_DESCRIPTION_VILLAGER);
                p.setFoodLevel(1);
            }
        }
        getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_GAMING.replaceAll("%SECOND%", String.valueOf(sec)));
        BukkitTask timer = new BukkitRunnable() {
            @Override
            public void run() {
                if(getRealRemainingSeconds() > 0) {
                    setRealRemainingSeconds(getRealRemainingSeconds() - 1);
                } else {
                    stop(GameRoles.VILLAGER);
                    this.cancel();
                }
                if(getRemainingSeconds() > 0) {
                    setRemainingSeconds(getRemainingSeconds() - 1);
                    getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_GAMING.replaceAll("%SECOND%", String.valueOf(getRemainingSeconds())));
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
        setTimer(timer);
    }

    public void stop(GameRole winners) {
        getReference().setGameState(GameState.WAITING);
        getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_WAITING);
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.setFoodLevel(20);
        }
        if(winners == GameRoles.NONE) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle(Reference.TCT_TITLE_MAIN_NO_VICTORY, Reference.TCT_TITLE_SUB_NO_VICTORY, 5, 20, 5);
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_NO_VICTORY);
            }
        }
        Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_GAMEEND_ROLE_RESULT);
    }

    public NanamiTct getPlugin() {
        return this.plugin;
    }

    public BossBar getBar() { return this.bar; }

    public int getRemainingSeconds() {
        return this.remainingSeconds;
    }

    public int getRealRemainingSeconds() {
        return this.realRemainingSeconds;
    }

    public GameReference getReference() {
        return this.reference;
    }

    public RoleCount getRoleCount() {
        return this.roleCount;
    }

    public BukkitTask getTimer() {
        return this.timer;
    }

    public BukkitTask getPreTimer() {
        return this.preTimer;
    }

    public void setRoleCount(RoleCount value) {
        this.roleCount = value;
    }

    public void setTimer(BukkitTask timer) {
        this.timer = timer;
    }

    public void setPreTimer(BukkitTask timer) {
        this.preTimer = timer;
    }

    public void setRemainingSeconds(int second) {
        this.remainingSeconds = second;
    }

    public void setRealRemainingSeconds(int second) {
        this.realRemainingSeconds = second;
    }

    public void setReference(GameReference reference) {
        this.reference = reference;
    }

    public void setBar(BossBar bar) { this.bar = bar; }

}
