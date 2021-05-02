package me.clockclap.tct.game;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.api.Reference;
import me.clockclap.tct.api.event.GameItemDistributeEvent;
import me.clockclap.tct.api.event.GamePreStartEvent;
import me.clockclap.tct.api.event.GameStartEvent;
import me.clockclap.tct.api.event.GameStopEvent;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.death.DeadBody;
import me.clockclap.tct.game.death.Killer;
import me.clockclap.tct.game.role.*;
import me.clockclap.tct.item.CustomItems;
import me.clockclap.tct.item.TctLog;
import net.minecraft.server.v1_12_R1.CommandKill;
import org.bukkit.*;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

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
    private TctLog log;
    private Location loc;
    private List<PlayerData> remainingPlayers;
    private List<PlayerData> realRemainingPlayers;
    private boolean timeOut = false;
    private int startingIn = 0;
    private int elapsedTime = 0;
    public List<String> villagers = new ArrayList<>();
    public List<String> healers = new ArrayList<>();
    public List<String> detectives = new ArrayList<>();
    public List<String> wolves = new ArrayList<>();
    public List<String> fanatics = new ArrayList<>();
    public List<String> foxes = new ArrayList<>();
    public List<String> immoral = new ArrayList<>();
    public Map<GameRole, List<String>> customRoles = new HashMap<>();

    public Game(NanamiTct plugin) {
        this.plugin = plugin;
        this.reference = new GameReference(plugin);
        this.remainingSeconds = 0;
        this.realRemainingSeconds = 0;
        this.remainingPlayers = new ArrayList<>();
        this.realRemainingPlayers = new ArrayList<>();
        this.roleCount = new RoleCount(this);
        this.neededPlayers = 0;
        this.log = new TctLog(this);
    }

    public boolean preStart(Location loc) {
        boolean success = false;
        GamePreStartEvent gamePreStartEvent = new GamePreStartEvent(this, loc, Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_SYSTEM_STOPPED_GAME);
        Bukkit.getServer().getPluginManager().callEvent(gamePreStartEvent);

        if(gamePreStartEvent.isCancelled()) {
            Bukkit.broadcastMessage(gamePreStartEvent.getCancelMessage());
            return true;
        }
        FileConfiguration config = plugin.getTctConfig().getConfig();
        String villagersCount = config.getString("roles.count.villagers", "1");
        String healersCount = config.getString("roles.count.healers", "1");
        String detectivesCount = config.getString("roles.count.detectives", "1");
        String wolvesCount = config.getString("roles.count.wolves", "1");
        String fanaticsCount = config.getString("roles.count.fanatics", "1");
        String foxesCount = config.getString("roles.count.foxes", "1");
        String immoralCount = config.getString("roles.count.immoral", "1");
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
        int immoralMin = 1;
        int immoralMax = 1;
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
        if(immoralCount.contains(":")) {
            String[] count = immoralCount.split(":");
            int min = Integer.parseInt(count[0]);
            int max = Integer.parseInt(count[1]);
            immoralMin = min;
            immoralMax = max;
        } else {
            int num = Integer.parseInt(immoralCount);
            immoralMax = num;
            immoralMin = num;
        }
        int resultCount = villagersMin + healersMin + detectivesMin + wolvesMin + fanaticsMin + foxesMin + immoralMin;
        if(!NanamiTct.roleRegisterer.isEmpty()) {
            for(GameRole r : NanamiTct.roleRegisterer.getRegisteredRoles()) {
                int count = config.getInt("roles.count.custom." + r.getName().toLowerCase(), 1);
                resultCount += count;
            }
        }
        neededPlayers = resultCount;
        if(resultCount > Bukkit.getOnlinePlayers().size()) {
            return false;
        }
        success = true;
        if(success) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                NanamiTct.utilities.modifyName(p, ChatColor.GREEN + NanamiTct.utilities.resetColor(p.getName()));
                NanamiTct.utilities.reloadPlayer();
            }
            getReference().setGameState(GameState.STARTING);
            this.loc = loc;
            for(Player p : Bukkit.getOnlinePlayers()) {
                PlayerData data = getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
                data.resetBoughtItem();
                data.setTogether(0);
                data.setVillager(0);
                data.setSuspicious(0);
                data.setWolf(0);
                data.setSponge(false);
                data.setKilledBy(new Killer("AIR", GameRoles.NONE, Killer.KillerCategory.AIR));
                p.setGameMode(GameMode.SURVIVAL);
                p.setMaxHealth(20.0D);
                p.setHealth(20.0D);
                p.getInventory().clear();
                p.teleport(loc);

            }
            final int[] sec = {plugin.getTctConfig().getConfig().getInt("countdown.prestart", 10) + 1};
            setStartingIn(sec[0]);
            getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_STARTING.replaceAll("%SECOND%", String.valueOf(sec[0])));
            Bukkit.getServer().broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_GAME_STARTED);
            BukkitTask timer = new BukkitRunnable() {
                @Override
                public void run() {
                    sec[0] = sec[0] - 1;
                    int j = sec[0];
                    if(j > 0) {
                        getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_STARTING.replaceAll("%SECOND%", String.valueOf(j)));
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.setLevel(j);
                        }
                        getBar().setProgress((1.0 / plugin.getTctConfig().getConfig().getInt("countdown.prestart", 10)) * j);
                        setStartingIn(j);
                    }
                    if (j <= 5 && j > 0) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.5F, 1F);
                        }
                    }
                    if (j <= 0) {
                        this.cancel();
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.5F, 1F);
                        }
                        getBar().setProgress(1.0);
                        start(loc);
                    }
                }
            }.runTaskTimer(plugin, 0L, 20L);
            setPreTimer(timer);
            return true;
        }
        return false;
    }

    private void giveRole(int playersCount) {
        FileConfiguration config = plugin.getTctConfig().getConfig();
        String villagersCount = config.getString("roles.count.villagers", "1");
        String healersCount = config.getString("roles.count.healers", "1");
        String detectivesCount = config.getString("roles.count.detectives", "1");
        String wolvesCount = config.getString("roles.count.wolves", "1");
        String fanaticsCount = config.getString("roles.count.fanatics", "1");
        String foxesCount = config.getString("roles.count.foxes", "1");
        String immoralCount = config.getString("roles.count.immoral", "1");
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
        int immoralMin = 1;
        int immoralMax = 1;
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
        if(immoralCount.contains(":")) {
            String[] count = immoralCount.split(":");
            int min = Integer.parseInt(count[0]);
            int max = Integer.parseInt(count[1]);
            immoralMin = min;
            immoralMax = max;
        } else {
            int num = Integer.parseInt(immoralCount);
            immoralMax = num;
            immoralMin = num;
        }
        int resultCount = villagersMin + healersMin + detectivesMin + wolvesMin + fanaticsMin + foxesMin + immoralMin;
        if(playersCount >= resultCount) {
            Collection<? extends Player> players = Bukkit.getOnlinePlayers();
            for(Player p : Bukkit.getOnlinePlayers()) {
                if (getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName())).getRole() != GameRoles.VILLAGER) {
                    players.remove(p);
                }
            }
            boolean canStart = false;
            int i = 0;
            while(!canStart) {
                if(getReference().getGameState() != GameState.GAMING) {
                    break;
                }
                villagers.clear();
                healers.clear();
                detectives.clear();
                wolves.clear();
                fanatics.clear();
                foxes.clear();
                immoral.clear();
                getRoleCount().setVillagersCount(0);
                getRoleCount().setHealersCount(0);
                getRoleCount().setDetectivesCount(0);
                getRoleCount().setWolvesCount(0);
                getRoleCount().setFanaticsCount(0);
                getRoleCount().setFoxesCount(0);
                getRoleCount().setImmoralCount(0);
                for (Player p : players) {
                    int remaining = playersCount;
                    PlayerData data = getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
                    Random rand = new Random();
                    int role = rand.nextInt(6) + 1;
                    if (role == GameRoles.HEALER.getIndex()) {
                        if (getRoleCount().getHealersCount() < healersMax) {
                            int coin = plugin.getTctConfig().getConfig().getInt("roles.coin.healers", 0);
                            data.setRole(GameRoles.HEALER);
                            data.setCoin(coin);
                            getRoleCount().setHealersCount(getRoleCount().getHealersCount() + 1);
                            healers.add(NanamiTct.utilities.resetColor(p.getName()));
                            continue;
                        }
                        role++;
                    }
                    if (role == GameRoles.DETECTIVE.getIndex()) {
                        if (getRoleCount().getDetectivesCount() < detectivesMax) {
                            int coin = plugin.getTctConfig().getConfig().getInt("roles.coin.detectives", 2);
                            data.setRole(GameRoles.DETECTIVE);
                            data.setCoin(coin);
                            getRoleCount().setDetectivesCount(getRoleCount().getDetectivesCount() + 1);
                            detectives.add(NanamiTct.utilities.resetColor(p.getName()));
                            continue;
                        }
                        role++;
                    }
                    if (role == GameRoles.WOLF.getIndex()) {
                        if (getRoleCount().getWolvesCount() < wolvesMax) {
                            int coin = plugin.getTctConfig().getConfig().getInt("roles.coin.wolves", 2);
                            data.setRole(GameRoles.WOLF);
                            data.setCoin(coin);
                            getRoleCount().setWolvesCount(getRoleCount().getWolvesCount() + 1);
                            wolves.add(NanamiTct.utilities.resetColor(p.getName()));
                            continue;
                        }
                        role++;
                    }
                    if (role == GameRoles.FANATIC.getIndex()) {
                        if (getRoleCount().getFanaticsCount() < fanaticsMax) {
                            int coin = plugin.getTctConfig().getConfig().getInt("roles.coin.fanatics", 0);
                            data.setRole(GameRoles.FANATIC);
                            data.setCoin(coin);
                            getRoleCount().setFanaticsCount(getRoleCount().getFanaticsCount() + 1);
                            fanatics.add(NanamiTct.utilities.resetColor(p.getName()));
                            continue;
                        }
                        role++;
                    }
                    if (role == GameRoles.FOX.getIndex()) {
                        if (getRoleCount().getFoxesCount() < foxesMax) {
                            int coin = plugin.getTctConfig().getConfig().getInt("roles.coin.foxes", 1);
                            data.setRole(GameRoles.FOX);
                            data.setCoin(coin);
                            getRoleCount().setFoxesCount(getRoleCount().getFoxesCount() + 1);
                            foxes.add(NanamiTct.utilities.resetColor(p.getName()));
                            continue;
                        }
                        role++;
                    }
                    if (role == GameRoles.IMMORAL.getIndex()) {
                        if (getRoleCount().getImmoralCount() < immoralMax) {
                            int coin = plugin.getTctConfig().getConfig().getInt("roles.coin.immoral", 0);
                            data.setRole(GameRoles.IMMORAL);
                            data.setCoin(coin);
                            getRoleCount().setImmoralCount(getRoleCount().getImmoralCount() + 1);
                            immoral.add(NanamiTct.utilities.resetColor(p.getName()));
                            continue;
                        }
                    }
                }
                if(getRoleCount().getHealersCount() >= healersMin && getRoleCount().getHealersCount() <= healersMax &&
                        getRoleCount().getDetectivesCount() >= detectivesMin && getRoleCount().getDetectivesCount() <= detectivesMax &&
                        getRoleCount().getWolvesCount() >= wolvesMin && getRoleCount().getWolvesCount() <= wolvesMax &&
                        getRoleCount().getFanaticsCount() >= fanaticsMin && getRoleCount().getFanaticsCount() <= fanaticsMax &&
                        getRoleCount().getFoxesCount() >= foxesMin && getRoleCount().getFoxesCount() <= foxesMax &&
                        getRoleCount().getImmoralCount() >= immoralMin && getRoleCount().getImmoralCount() <= immoralMax) {
                    canStart = true;
                }
                i++;
            }
            boolean canStart_ = false;
            if(!NanamiTct.roleRegisterer.isEmpty()) {
                while (!canStart_) {
                    if (getReference().getGameState() != GameState.GAMING) {
                        break;
                    }
                    getRoleCount().getCustomRoleCount().initialize();
                    customRoles.clear();
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (p != null) {
                            PlayerData data = getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
                            if (data != null) {
                                if (data.getRole() == GameRoles.VILLAGER) {
                                    Random rand = new Random();
                                    int role = rand.nextInt(NanamiTct.roleRegisterer.size() - 1) + 1;
                                    for (GameRole r : NanamiTct.roleRegisterer.getRegisteredRoles()) {
                                        int count = config.getInt("roles.count.custom." + r.getName().toLowerCase(), 1);
                                        if (role == r.getIndex()) {
                                            if(getRoleCount().getCustomRoleCount().get(r) < count) {
                                                int coin = plugin.getTctConfig().getConfig().getInt("roles.coin.custom." + r.getName().toLowerCase(), 0);
                                                data.setRole(r);
                                                data.setCoin(coin);
                                                getRoleCount().getCustomRoleCount().set(r, getRoleCount().getCustomRoleCount().get(r) + 1);
                                                if(!customRoles.containsKey(r)) {
                                                    customRoles.put(r, new ArrayList<>());
                                                }
                                                customRoles.get(r).add(NanamiTct.utilities.resetColor(p.getName()));
                                                break;
                                            }
                                            role++;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void giveItem() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            PlayerData data = getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
            if(!data.isSpectator()) {
                p.getInventory().setItem(4, getLog().getItem());
                p.getInventory().setItem(5, CustomItems.QUICKCHAT_A.getItemStack());
                p.getInventory().setItem(6, CustomItems.QUICKCHAT_B.getItemStack());
                p.getInventory().setItem(7, CustomItems.QUICKCHAT_C.getItemStack());
                p.getInventory().setItem(8, CustomItems.QUICKCHAT_D.getItemStack());
                p.getInventory().setItem(18, CustomItems.CO_VILLAGER.getItemStack());
                p.getInventory().setItem(19, CustomItems.CO_DETECTIVE.getItemStack());
                p.getInventory().setItem(20, CustomItems.CO_HEALER.getItemStack());
                p.getInventory().setItem(21, CustomItems.CO_WOLF.getItemStack());
                p.getInventory().setItem(22, CustomItems.CO_FANATIC.getItemStack());
                p.getInventory().setItem(23, CustomItems.CO_FOX.getItemStack());
                p.getInventory().setItem(24, CustomItems.CO_IMMORAL.getItemStack());
            }
        }

        GameItemDistributeEvent gameItemDistributeEvent = new GameItemDistributeEvent(this);
        Bukkit.getServer().getPluginManager().callEvent(gameItemDistributeEvent);
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
                getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName())).setRole(GameRoles.SPEC);
                getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName())).setSpectator(true);
            } else {
                p.getInventory().clear();
                p.setGameMode(GameMode.SURVIVAL);
                getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName())).setRole(GameRoles.VILLAGER);
                getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName())).setSpectator(false);
            }
        }
        if(playersCount < neededPlayers) {
            Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ERROR_PLAYERS_NEEDED);
            for(Player p : Bukkit.getOnlinePlayers()) {
                p.playSound(p.getLocation(), Sound.BLOCK_DISPENSER_FAIL, 1.5F, 1F);
                getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName())).setRole(GameRoles.SPEC);
                getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName())).setSpectator(true);
                p.setFoodLevel(20);
                RoleCount count = new RoleCount(this);
                count.setVillagersCount(0);
                count.setHealersCount(0);
                count.setDetectivesCount(0);
                count.setWolvesCount(0);
                count.setFanaticsCount(0);
                count.setFoxesCount(0);
                setRoleCount(count);
                p.getInventory().clear();
                p.teleport(this.loc);
            }
            getReference().setGameState(GameState.WAITING);
            getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_WAITING);
            return;
        }
        giveRole(playersCount);
        giveItem();
        for(Player p : Bukkit.getOnlinePlayers()) {
            PlayerData data = getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
            data.setCO(GameRoles.NONE);
            p.setPlayerListName("");
            NanamiTct.utilities.modifyName(p, ChatColor.GREEN + NanamiTct.utilities.resetColor(p.getName()));
            NanamiTct.utilities.reloadPlayer();
            if(data.getRole() == GameRoles.VILLAGER) {
                int coin = plugin.getTctConfig().getConfig().getInt("roles.coin.villagers", 0);
                getRoleCount().setVillagersCount(getRoleCount().getVillagersCount() + 1);
                data.setCoin(coin);
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_YOU_ARE_VILLAGER);
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_DESCRIPTION_VILLAGER);
                p.getInventory().setItem(0, CustomItems.WOOD_SWORD.getItemStack());
                p.getInventory().setItem(1, CustomItems.BOW.getItemStack());
                p.getInventory().setItem(2, CustomItems.ARROW.getItemStack());
                p.setFoodLevel(1);
                villagers.add(NanamiTct.utilities.resetColor(p.getName()));
            }
            if(data.getRole() == GameRoles.HEALER) {
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_YOU_ARE_HEALER);
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_DESCRIPTION_HEALER);
                p.getInventory().setItem(0, CustomItems.HEALER_SWORD.getItemStack());
                p.getInventory().setItem(1, CustomItems.WOOD_SWORD.getItemStack());
                p.getInventory().setItem(2, CustomItems.BOW.getItemStack());
                p.getInventory().setItem(3, CustomItems.ARROW.getItemStack());
                p.setFoodLevel(1);
            }
            if(data.getRole() == GameRoles.DETECTIVE) {
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_YOU_ARE_DETECTIVE);
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_DESCRIPTION_DETECTIVE);
                p.getInventory().setItem(0, CustomItems.WOOD_SWORD.getItemStack());
                p.getInventory().setItem(1, CustomItems.BOW.getItemStack());
                p.getInventory().setItem(2, CustomItems.ARROW.getItemStack());
                p.setFoodLevel(1);
            }
            if(data.getRole() == GameRoles.WOLF) {
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_YOU_ARE_WOLF);
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_DESCRIPTION_WOLF);
                p.getInventory().setItem(0, CustomItems.WOOD_SWORD.getItemStack());
                p.getInventory().setItem(1, CustomItems.BOW.getItemStack());
                p.getInventory().setItem(2, CustomItems.ARROW.getItemStack());
                p.setFoodLevel(1);
                String str0 = String.join(", ", wolves);
                String str1 = String.join(", ", fanatics);
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_WOLF_LIST + ": [" + str0 + "]");
                if(getRoleCount().getFanaticsCount() > 0) {
                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_FANATIC_LIST + ": [" + str1 + "]");
                }
            }
            if(data.getRole() == GameRoles.FANATIC) {
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_YOU_ARE_FANATIC);
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_DESCRIPTION_FANATIC);
                p.getInventory().setItem(0, CustomItems.WOOD_SWORD.getItemStack());
                p.getInventory().setItem(1, CustomItems.BOW.getItemStack());
                p.getInventory().setItem(2, CustomItems.ARROW.getItemStack());
                p.setFoodLevel(1);
            }
            if(data.getRole() == GameRoles.FOX) {
                List<String> foxesTeam = new ArrayList<>();
                foxesTeam.addAll(foxes);
                foxesTeam.addAll(immoral);
                if(data.getWatcher() != null) {
                    data.getWatcher().startCountFox();
                }
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_YOU_ARE_FOX);
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_DESCRIPTION_FOX);
                p.getInventory().setItem(0, CustomItems.WOOD_SWORD.getItemStack());
                p.getInventory().setItem(1, CustomItems.BOW.getItemStack());
                p.getInventory().setItem(2, CustomItems.ARROW.getItemStack());
                p.setFoodLevel(1);
                String str0 = String.join(", ", foxes);
                String str1 = String.join(", ", immoral);
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_FOX_LIST + ": [" + str0 + "]");
                if(getRoleCount().getImmoralCount() > 0) {
                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_IMMORAL_LIST + ": [" + str1 + "]");
                }
            }
            if(data.getRole() == GameRoles.IMMORAL) {
                List<String> foxesTeam = new ArrayList<>();
                foxesTeam.addAll(foxes);
                foxesTeam.addAll(immoral);
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_YOU_ARE_IMMORAL);
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_ROLE_DESCRIPTION_IMMORAL);
                p.getInventory().setItem(0, CustomItems.WOOD_SWORD.getItemStack());
                p.getInventory().setItem(1, CustomItems.BOW.getItemStack());
                p.getInventory().setItem(2, CustomItems.ARROW.getItemStack());
                p.setFoodLevel(1);
                String str0 = String.join(", ", foxes);
                String str1 = String.join(", ", immoral);
                if(getRoleCount().getFoxesCount() > 0) {
                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_FOX_LIST + ": [" + str0 + "]");
                }
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_IMMORAL_LIST + ": [" + str1 + "]");
            }
            if(!data.isSpectator()) {
                remainingPlayers.add(data);
                realRemainingPlayers.add(data);
            }
            for(Player pl : Bukkit.getOnlinePlayers()) {
                NanamiTct.utilities.hidePlayer(pl, p);
                data.setInvisible(true);
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        NanamiTct.utilities.showPlayer(pl, p);
                        data.setInvisible(false);
                    }
                }.runTaskLater(getPlugin(), 10);
            }
        }
        String str = "";
        String trvi = "";
        String trhe = "";
        String trde = "";
        String trwo = "";
        String trfa = "";
        String trfo = "";
        String trim = "";
        if(getRoleCount().getVillagersCount() > 0) {
            trvi = Reference.TCT_ROLE_VILLAGER + ": " + getRoleCount().getVillagersCount() + " / ";
            str += trvi;
        }
        if(getRoleCount().getHealersCount() > 0) {
            trhe = Reference.TCT_ROLE_HEALER + ": " + getRoleCount().getHealersCount() + " / ";
            str += trhe;
        }
        if(getRoleCount().getDetectivesCount() > 0) {
            trde = Reference.TCT_ROLE_DETECTIVE + ": " + getRoleCount().getDetectivesCount() + " / ";
            str += trde;
        }
        if(getRoleCount().getWolvesCount() > 0) {
            trwo = Reference.TCT_ROLE_WOLF + ": " + getRoleCount().getWolvesCount() + " / ";
            str += trwo;
        }
        if(getRoleCount().getFanaticsCount() > 0) {
            trfa = Reference.TCT_ROLE_FANATIC + ": " + getRoleCount().getFanaticsCount() + " / ";
            str += trfa;
        }
        if(getRoleCount().getFoxesCount() > 0) {
            trfo = Reference.TCT_ROLE_FOX + ": " + getRoleCount().getFoxesCount() + " / ";
            str += trfo;
        }
        if(getRoleCount().getImmoralCount() > 0) {
            trim = Reference.TCT_ROLE_IMMORAL + ": " + getRoleCount().getFoxesCount() + " / ";
            str += trim;
        }
        if(!NanamiTct.roleRegisterer.isEmpty()) {
            for(GameRole r : NanamiTct.roleRegisterer.getRegisteredRoles()) {
                str += r.getDisplayName() + ": " + getRoleCount().getCustomRoleCount().get(r) + " / ";
            }
        }
        Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_GAME_PLAYERS.replaceAll("%COUNT%", String.valueOf(playersCount)));
        Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_GAME_ROLE_SORTING + ": " + ChatColor.GOLD + str);

        // Update Log Book
        getLog().addLine(Reference.TCT_LOGBOOK_GAME_STARTED);
        getLog().addLine(Reference.TCT_LOGBOOK_ROLES);
        if(!NanamiTct.roleRegisterer.isEmpty()) {
            List<GameRole> roleList = new ArrayList<>(NanamiTct.roleRegisterer.getRegisteredRoles());
            Collections.reverse(roleList);
            for (GameRole r : roleList) {
                getLog().addLine(" " + " " + r.getDisplayName() + ": " + ChatColor.GREEN + getRoleCount().getCustomRoleCount().get(r));
            }
        }
        getLog().addLine(" " + " " + Reference.TCT_ROLE_IMMORAL + ": " + ChatColor.GREEN + getRoleCount().getImmoralCount());
        getLog().addLine(" " + " " + Reference.TCT_ROLE_FOX + ": " + ChatColor.GREEN + getRoleCount().getFoxesCount());
        getLog().addLine(" " + " " + Reference.TCT_ROLE_FANATIC + ": " + ChatColor.GREEN + getRoleCount().getFanaticsCount());
        getLog().addLine(" " + " " + Reference.TCT_ROLE_WOLF + ": " + ChatColor.GREEN + getRoleCount().getWolvesCount());
        getLog().addLine(" " + " " + Reference.TCT_ROLE_DETECTIVE + ": " + ChatColor.GREEN + getRoleCount().getDetectivesCount());
        getLog().addLine(" " + " " + Reference.TCT_ROLE_HEALER + ": " + ChatColor.GREEN + getRoleCount().getHealersCount());
        getLog().addLine(" " + " " + Reference.TCT_ROLE_VILLAGER + ": " + ChatColor.GREEN + getRoleCount().getVillagersCount());
        getLog().addLine(Reference.TCT_LOGBOOK_SEPARATOR);
        getLog().update();

        getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_GAMING.replaceAll("%SECOND%", String.valueOf(sec)));
        final int[] time = {-1};
        BukkitTask timer = new BukkitRunnable() {
            @Override
            public void run() {
                if(getRealRemainingSeconds() > 0) {
                    time[0]++;
                    elapsedTime = time[0];
                    if(time[0] != 0 && time[0] % getPlugin().getTctConfig().getConfig().getInt("first-coin-time", 180) == 0) {
                        time[0] = 0;
                        for(Player p : Bukkit.getOnlinePlayers()) {
                            PlayerData data = getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
                            data.setCoin(data.getCoin() + 1);
                        }
                        Bukkit.broadcastMessage(Reference.TCT_CHAT_COIN_DISTRIBUTION);
                    }
                    setRealRemainingSeconds(getRealRemainingSeconds() - 1);
                } else {
                    timeOut = true;
                    stop(GameTeams.VILLAGERS);
                    this.cancel();
                }
                if(getRemainingSeconds() > 0) {
                    setRemainingSeconds(getRemainingSeconds() - 1);
                    getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_GAMING.replaceAll("%SECOND%", String.valueOf(getRemainingSeconds())));
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        p.setLevel(getRemainingSeconds());
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
        setTimer(timer);

        GameStartEvent gameStartEvent = new GameStartEvent(this, loc);
        Bukkit.getServer().getPluginManager().callEvent(gameStartEvent);
    }

    public void stop(GameTeam winners) {
        List<PlayerData> vil = new ArrayList<>();
        List<PlayerData> wol = new ArrayList<>();
        for (PlayerData d : plugin.getGame().getRemainingPlayers(true)) {
            if (d.getRole().getTeam() == GameTeams.VILLAGERS) {
                vil.add(d);
                continue;
            }
            if (d.getRole().getTeam() == GameTeams.WOLVES) {
                wol.add(d);
                continue;
            }
        }
        boolean gaming = false;
        if(getReference().getGameState() == GameState.GAMING) {
            gaming = true;
        }
        if(getReference().DEADBODIES.size() > 0) {
            for (DeadBody deadBody : getReference().DEADBODIES) {
                deadBody.remove();
            }
            getReference().DEADBODIES = new ArrayList<>();
        }
        getReference().setGameState(GameState.WAITING);
        getLog().initialize();
        CustomItems.resetBlockData();
        resetRemainingPlayers();
        plugin.getGame().getBar().setProgress(1.0);
        getBar().setTitle(Reference.TCT_BOSSBAR_FORMAT_WAITING);
        if(gaming) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                PlayerData data = getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
                p.setPlayerListName(ChatColor.GREEN + NanamiTct.utilities.resetColor(p.getName()));
                data.resetBoughtItem();
                data.setTogether(0);
                data.setVillager(0);
                data.setSuspicious(0);
                data.setWolf(0);
                NanamiTct.utilities.modifyName(p, ChatColor.GREEN + NanamiTct.utilities.resetColor(p.getName()));
                NanamiTct.utilities.reloadPlayer();
                if(data.getRole() == GameRoles.FOX && !data.isSpectator()) {
                    if(data.getWatcher() != null) {
                        data.getWatcher().cancelCountFox();
                        data.getWatcher().setCountFox(getPlugin().getTctConfig().getConfig().getInt("fox-reveal-time-default", 70));
                    }
                }
                if (winners == GameTeams.NONE || winners == GameTeams.SPEC) {
                    p.playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1.0F, 1.0F);
                    p.playSound(p.getLocation(), Sound.ENTITY_BAT_DEATH, 1.0F, 0.5F);
                    p.sendTitle(Reference.TCT_TITLE_MAIN_NO_VICTORY, Reference.TCT_TITLE_SUB_NO_VICTORY, 5, 40, 5);
                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_NO_VICTORY);
                }
                if (winners == GameTeams.VILLAGERS) {
                    p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1.0F, 1.0F);
                    if(timeOut) {
                        timeOut = false;
                        p.sendTitle(Reference.TCT_TITLE_MAIN_VILLAGERS_VICTORY, Reference.TCT_TITLE_SUB_VILLAGERS_VICTORY_FOR_TIMEOUT, 5, 40, 5);
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_VILLAGERS_VICTORY_FOR_TIMEOUT);
                    } else {
                        p.sendTitle(Reference.TCT_TITLE_MAIN_VILLAGERS_VICTORY, Reference.TCT_TITLE_SUB_VILLAGERS_VICTORY, 5, 40, 5);
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_VILLAGERS_VICTORY);
                    }
                }
                if (winners == GameTeams.FOXES) {
                    p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_LAUNCH, 1.0F, 1.0F);
                    if (vil.size() > 0 && wol.size() <= 0) {
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_FOX_VICTORY_A);
                        p.sendTitle(Reference.TCT_TITLE_MAIN_FOX_VICTORY, Reference.TCT_TITLE_SUB_FOX_VICTORY_A, 5, 40, 5);
                    } else if (wol.size() > 0 && vil.size() <= 0) {
                        p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_FOX_VICTORY_B);
                        p.sendTitle(Reference.TCT_TITLE_MAIN_FOX_VICTORY, Reference.TCT_TITLE_SUB_FOX_VICTORY_B, 5, 40, 5);
                    }
                }
                if (winners == GameTeams.WOLVES) {
                    p.playSound(p.getLocation(), Sound.ENTITY_WOLF_HOWL, 1.0F, 1.0F);
                    p.sendTitle(Reference.TCT_TITLE_MAIN_WOLVES_VICTORY, Reference.TCT_TITLE_SUB_WOLVES_VICTORY, 5, 40, 5);
                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_WOLVES_VICTORY);
                }
                if(!NanamiTct.teamRegisterer.isEmpty()) {
                    for(GameTeam t : NanamiTct.teamRegisterer.getRegisteredTeams()) {
                        if(winners == t) {
                            if(t instanceof TctTeam) {
                                TctTeam tt = (TctTeam) t;
                                tt.onVictory(p);
                            } else {
                                p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_SHOOT, 1.0F, 1.0F);
                                p.sendTitle(Reference.TCT_TITLE_MAIN_VICTORY.replaceAll("%COLOR%", ChatColor.RESET.toString()).replaceAll("%TEAM%", t.getDisplayName()), Reference.TCT_TITLE_SUB_VICTORY, 5, 40, 5);
                                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_VICTORY.replaceAll("%COLOR%", t.getColor().toString()).replaceAll("%TEAM%", t.getDisplayName()));
                            }
                        }
                    }
                }
                p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_GAMEEND_ROLE_RESULT);
            }
            if(villagers.size() > 0) {
                String str = String.join(", ", villagers);
                Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.GREEN + Reference.TCT_ROLE_VILLAGER + ": [" + str + "]");
            }
            if(healers.size() > 0) {
                String str = String.join(", ", healers);
                Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.LIGHT_PURPLE + Reference.TCT_ROLE_HEALER + ": [" + str + "]");
            }
            if(detectives.size() > 0) {
                String str = String.join(", ", detectives);
                Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.AQUA + Reference.TCT_ROLE_DETECTIVE + ": [" + str + "]");
            }
            if(wolves.size() > 0) {
                String str = String.join(", ", wolves);
                Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_ROLE_WOLF + ": [" + str + "]");
            }
            if(fanatics.size() > 0) {
                String str = String.join(", ", fanatics);
                Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.RED + Reference.TCT_ROLE_FANATIC + ": [" + str + "]");
            }
            if(foxes.size() > 0) {
                String str = String.join(", ", foxes);
                Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.GOLD + Reference.TCT_ROLE_FOX + ": [" + str + "]");
            }
            if(immoral.size() > 0) {
                String str = String.join(", ", immoral);
                Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + ChatColor.DARK_GRAY + Reference.TCT_ROLE_IMMORAL + ": [" + str + "]");
            }
            if(customRoles.size() > 0) {
                for(Map.Entry<GameRole, List<String>> entry : customRoles.entrySet()) {
                    GameRole r = entry.getKey();
                    String str = String.join(", ", entry.getValue());
                    Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + r.getColor() + r.getDisplayName() + ": [" + str + "]");
                }
            }
            villagers.clear();
            healers.clear();
            detectives.clear();
            wolves.clear();
            fanatics.clear();
            foxes.clear();
            immoral.clear();
            customRoles.clear();
        }
        for(Player p : Bukkit.getOnlinePlayers()) {
            PlayerData data = getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
            p.spigot().respawn();
            p.setFoodLevel(20);
            p.setMaxHealth(20.0D);
            p.setHealth(20.0D);
            data.setRole(GameRoles.SPEC);
            data.setSpectator(true);
            data.setCoin(0);
            data.setSponge(false);
            RoleCount count = new RoleCount(this);
            count.setVillagersCount(0);
            count.setHealersCount(0);
            count.setDetectivesCount(0);
            count.setWolvesCount(0);
            count.setFanaticsCount(0);
            count.setFoxesCount(0);
            setRoleCount(count);
            p.getInventory().clear();
            p.teleport(loc);
            p.setGameMode(GameMode.SURVIVAL);
            if(data.getKilledBy() != null) {
                if (data.getKilledBy().getCategory() != Killer.KillerCategory.AIR) {
                    p.sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_GAMEEND_YOU_ARE_KILLED_BY.replaceAll("%PLAYER%", data.getKilledBy().getName()).replaceAll("%ROLE%", data.getKilledBy().getRole().getDisplayName()));
                }
            }
            data.setKilledBy(new Killer("AIR", GameRoles.NONE, Killer.KillerCategory.AIR));
        }

        GameStopEvent gameStopEvent = new GameStopEvent(this, loc, winners);
        Bukkit.getServer().getPluginManager().callEvent(gameStopEvent);
    }

    public NanamiTct getPlugin() {
        return this.plugin;
    }

    public BossBar getBar() { return this.bar; }

    public TctLog getLog() {
        return this.log;
    }

    public void setLog(TctLog log) {
        this.log = log;
    }

    public Location getLocation() {
        return this.loc;
    }

    public int getElapsedTime() {
        return this.elapsedTime;
    }

    public void setLocation(Location loc) {
        this.loc = loc;
    }

    public int getStartingIn() {
        return this.startingIn;
    }

    public void setStartingIn(int value) {
        this.startingIn = value;
    }

    public int getRemainingSeconds() {
        return this.remainingSeconds;
    }

    public int getRealRemainingSeconds() {
        return this.realRemainingSeconds;
    }

    public int getNeededPlayers() {
        return this.neededPlayers;
    }

    public List<PlayerData> getRemainingPlayers(boolean isReal) {
        if(isReal) {
            return this.realRemainingPlayers;
        }
        return this.remainingPlayers;
    }

    public void addRemainingPlayers(PlayerData data, boolean isReal) {
        if(isReal) {
            this.realRemainingPlayers.add(data);
            return;
        }
        this.remainingPlayers.add(data);
    }

    public void removeRemainingPlayers(PlayerData data, boolean isReal) {
        if(isReal) {
            this.realRemainingPlayers.remove(data);
            return;
        }
        this.remainingPlayers.remove(data);
    }

    public void resetRemainingPlayers(boolean isReal) {
        if(isReal) {
            this.realRemainingPlayers = new ArrayList<>();
            return;
        }
        this.remainingPlayers = new ArrayList<>();
    }

    public void resetRemainingPlayers() {
        this.realRemainingPlayers = new ArrayList<>();
        this.remainingPlayers = new ArrayList<>();
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
