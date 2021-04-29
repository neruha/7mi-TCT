package me.clockclap.tct.api;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.game.Game;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.CustomBlockData;
import me.clockclap.tct.game.data.PlayerData;
import me.clockclap.tct.game.role.GameRoles;
import me.clockclap.tct.item.CustomBlockInfo;
import me.clockclap.tct.item.CustomItems;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class PlayerWatcher {

    private Player player;
    private BukkitRunnable runnable;
    private BukkitRunnable runnable10;
    private BukkitRunnable runnableFox;
    private Location lastBlock;
    private int count;
    private Scoreboard scoreboard;
    private Objective objective;
    private PlayerData data;

    private final Game game;

    public PlayerWatcher(Game game, Player player) {
        this.game = game;
        this.player = player;
        this.data = this.game.getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(this.player.getName()));
        this.count = 0;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective(NanamiTct.utilities.resetColor(player.getName()), "dummy");
        this.objective.setDisplayName(Reference.TCT_SIDEBAR_TITLE);
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public Player getPlayer() {
        return this.player;
    }

    public PlayerData getPlayerData() {
        return this.data;
    }

    private void setPlayerData(PlayerData data) {
        this.data = data;
    }

    public Game getGame() {
        return this.game;
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public Objective getObjective() {
        return this.objective;
    }

    public void setObjective(Objective objective) {
        this.objective = objective;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void startWatchWith10Ticks() {
        runnable10 = new BukkitRunnable() {
            @Override
            public void run() {
                getObjective().unregister();
                setObjective(getScoreboard().registerNewObjective(NanamiTct.utilities.resetColor(getPlayer().getName()), "dummy"));
                getObjective().setDisplayName(Reference.TCT_SIDEBAR_TITLE);
                getObjective().setDisplaySlot(DisplaySlot.SIDEBAR);
                setPlayerData(getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(getPlayer().getName())));
                if(getGame().getReference().getGameState() == GameState.WAITING) {
                    Score line0 = getObjective().getScore(ChatColor.RESET + Reference.TCT_UI_SEPARATOR);
                    Score line1 = getObjective().getScore(Reference.TCT_UI_GAME_STATUS + ": " + Reference.TCT_STATE_WAITING);
                    Score line2 = getObjective().getScore(Reference.TCT_UI_PLEASE_WAIT);
                    Score line3 = getObjective().getScore(Reference.TCT_UI_SEPARATOR);
                    line0.setScore(4);
                    line1.setScore(3);
                    line2.setScore(2);
                    line3.setScore(1);

                    getPlayer().setScoreboard(getScoreboard());
                } else if(getGame().getReference().getGameState() == GameState.ENDING) {
                    Score line0 = getObjective().getScore(ChatColor.RESET + Reference.TCT_UI_SEPARATOR);
                    Score line1 = getObjective().getScore(Reference.TCT_UI_GAME_STATUS + ": " + Reference.TCT_STATE_END_PROCESSING);
                    Score line2 = getObjective().getScore(Reference.TCT_UI_SEPARATOR);
                    line0.setScore(3);
                    line1.setScore(2);
                    line2.setScore(1);

                    getPlayer().setScoreboard(getScoreboard());
                } else if(getGame().getReference().getGameState() == GameState.STARTING) {
                    Score line0 = getObjective().getScore(ChatColor.RESET + Reference.TCT_UI_SEPARATOR);
                    Score line1 = getObjective().getScore(Reference.TCT_UI_GAME_STATUS + ": " + Reference.TCT_STATE_READY);
                    Score line2 = getObjective().getScore(Reference.TCT_UI_GAME_STARTING_IN.replaceAll("%SECOND%", String.valueOf(getGame().getStartingIn())));
                    Score line3 = getObjective().getScore(Reference.TCT_UI_SEPARATOR);
                    line0.setScore(4);
                    line1.setScore(3);
                    line2.setScore(2);
                    line3.setScore(1);

                    getPlayer().setScoreboard(getScoreboard());
                } else if(getGame().getReference().getGameState() == GameState.GAMING) {
                    PlayerData data = getPlayerData();
                    if(!data.isSpectator()) {
                        if(data.getRole() == GameRoles.WOLF) {
                            Score line0 = getObjective().getScore(ChatColor.RESET + Reference.TCT_UI_SEPARATOR);
                            Score line1 = getObjective().getScore(Reference.TCT_UI_GAME_STATUS + ": " + Reference.TCT_STATE_PLAYING);
                            Score line2 = getObjective().getScore(Reference.TCT_UI_REMAINING_TIME + ": " + getGame().getRemainingSeconds());
                            Score line3 = getObjective().getScore(Reference.TCT_UI_SEPARATOR);
                            Score line4 = getObjective().getScore(Reference.TCT_UI_YOUR_ROLE + ": " + data.getRole().getDisplayName());
                            Score line5 = getObjective().getScore(ChatColor.GRAY + Reference.TCT_UI_SEPARATOR);
                            Score line6 = getObjective().getScore(Reference.TCT_UI_REAL_REMAINING_TIME + ": " + getGame().getRealRemainingSeconds());
                            Score line7 = getObjective().getScore(Reference.TCT_UI_REAL_REMAINING_PLAYER + ": " + getGame().getRemainingPlayers(true).size());
                            Score line8 = getObjective().getScore(Reference.TCT_UI_REMAINING_PLAYER + ": " + getGame().getRemainingPlayers(false).size());
                            Score line9 = getObjective().getScore(Reference.TCT_UI_COIN_COUNT + ": " + data.getCoin());
                            Score line10 = getObjective().getScore(ChatColor.RED + Reference.TCT_UI_SEPARATOR);
                            line0.setScore(11);
                            line1.setScore(10);
                            line2.setScore(9);
                            line3.setScore(8);
                            line4.setScore(7);
                            line5.setScore(6);
                            line6.setScore(5);
                            line7.setScore(4);
                            line8.setScore(3);
                            line9.setScore(2);
                            line10.setScore(1);

                            getPlayer().setScoreboard(getScoreboard());
                        } else {
                            Score line0 = getObjective().getScore(ChatColor.RESET + Reference.TCT_UI_SEPARATOR);
                            Score line1 = getObjective().getScore(Reference.TCT_UI_GAME_STATUS + ": " + Reference.TCT_STATE_PLAYING);
                            Score line2 = getObjective().getScore(Reference.TCT_UI_REMAINING_TIME + ": " + getGame().getRemainingSeconds());
                            Score line3 = getObjective().getScore(Reference.TCT_UI_SEPARATOR);
                            Score line4 = getObjective().getScore(Reference.TCT_UI_YOUR_ROLE + ": " + data.getRole().getDisplayName());
                            Score line5 = getObjective().getScore(ChatColor.GRAY + Reference.TCT_UI_SEPARATOR);
                            Score line6 = getObjective().getScore(Reference.TCT_UI_REMAINING_PLAYER + ": " + getGame().getRemainingPlayers(false).size());
                            Score line7 = getObjective().getScore(Reference.TCT_UI_COIN_COUNT + ": " + data.getCoin());
                            Score line8 = getObjective().getScore(ChatColor.RED + Reference.TCT_UI_SEPARATOR);
                            line0.setScore(9);
                            line1.setScore(8);
                            line2.setScore(7);
                            line3.setScore(6);
                            line4.setScore(5);
                            line5.setScore(4);
                            line6.setScore(3);
                            line7.setScore(2);
                            line8.setScore(1);

                            getPlayer().setScoreboard(getScoreboard());
                        }
                    } else {
                        Score line0 = getObjective().getScore(ChatColor.RESET + Reference.TCT_UI_SEPARATOR);
                        Score line1 = getObjective().getScore(Reference.TCT_UI_GAME_STATUS + ": " + Reference.TCT_STATE_PLAYING);
                        Score line2 = getObjective().getScore(Reference.TCT_UI_REMAINING_TIME + ": " + getGame().getRemainingSeconds());
                        Score line3 = getObjective().getScore(Reference.TCT_UI_SEPARATOR);
                        Score line4 = getObjective().getScore(Reference.TCT_UI_YOUR_ROLE + ": " + Reference.TCT_ROLE_SPEC);
                        Score line5 = getObjective().getScore(ChatColor.GRAY + Reference.TCT_UI_SEPARATOR);
                        Score line6 = getObjective().getScore(Reference.TCT_UI_REAL_REMAINING_TIME + ": " + getGame().getRealRemainingSeconds());
                        Score line7 = getObjective().getScore(Reference.TCT_UI_REAL_REMAINING_PLAYER + ": " + getGame().getRemainingPlayers(true).size());
                        Score line8 = getObjective().getScore(Reference.TCT_UI_REMAINING_PLAYER + ": " + getGame().getRemainingPlayers(false).size());
                        Score line9 = getObjective().getScore(Reference.TCT_UI_COIN_COUNT + ": " + "0");
                        Score line10 = getObjective().getScore(ChatColor.RED + Reference.TCT_UI_SEPARATOR);
                        line0.setScore(11);
                        line1.setScore(10);
                        line2.setScore(9);
                        line3.setScore(8);
                        line4.setScore(7);
                        line5.setScore(6);
                        line6.setScore(5);
                        line7.setScore(4);
                        line8.setScore(3);
                        line9.setScore(2);
                        line10.setScore(1);

                        getPlayer().setScoreboard(getScoreboard());
                    }
                }
            }
        };
        runnable10.runTaskTimer(game.getPlugin(), 0, 10);
    }

    public void startWatch() {
        startWatchWith10Ticks();
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if(!data.isSpectator()) {
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        PlayerData data = game.getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
                        if(!data.isInvisible()) {
                            if (data.isSpectator()) {
                                player.hidePlayer(game.getPlugin(), p);
                            } else {
                                if (NanamiTct.utilities.canSee(player, p)) {
                                    NanamiTct.utilities.showPlayer(player, p);
                                } else {
                                    NanamiTct.utilities.hidePlayer(player, p);
                                }
                            }
                        } else {
                            NanamiTct.utilities.hidePlayer(player, p);
                        }
                    }
                } else {
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        NanamiTct.utilities.showPlayer(player, p);
                    }
                }
//                new BukkitRunnable() {
//                    @Override
//                    public void run() {
//                        NanamiTct.utilities.modifyName(player, NanamiTct.utilities.resetColor(player.getName()));
//                    }
//                }.runTaskLater(NanamiTct.plugin, 0);
                NanamiTct.utilities.modifyName(player, NanamiTct.utilities.resetColor(player.getName()));
                if(player.getExp() > 0F) {
                    player.setExp(0F);
                }
                if(player.getLevel() > 0) {
                    if(getGame().getReference().getGameState() == GameState.WAITING || getGame().getReference().getGameState() == GameState.ENDING) {
                        player.setLevel(0);
                    }
                }
                Player p = NanamiTct.utilities.getNearestPlayer(player);
                if(p != null) player.setCompassTarget(p.getLocation());
                if(getPlayer().getGameMode() == GameMode.SURVIVAL || getPlayer().getGameMode() == GameMode.ADVENTURE) {
                    if (!getPlayerData().isSpectator()) {
                        if(getPlayerData().getRole() != GameRoles.WOLF) {
                            for (CustomBlockData data : CustomBlockInfo.blockDataList) {
                                if (data.getCustomBlock().getItemStack().getItemMeta().getDisplayName().equalsIgnoreCase(CustomItems.LANDMINE.getItemStack().getItemMeta().getDisplayName())) {
                                    if(data.isEnabled()) {
                                        double maxFar = NanamiTct.plugin.getTctConfig().getConfig().getInt("landmine-range", 5);
                                        double far = data.getLocation().distance(getPlayer().getLocation());
                                        if (far <= maxFar) {
                                            getPlayer().damage(getPlayer().getHealth() + 1);
                                            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1F, 1F);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        };
        runnable.runTaskTimer(this.game.getPlugin(), 0, 1);
    }

    @SuppressWarnings("unchecked")
    public void startCountFox() {
        int defaultCount = getGame().getPlugin().getTctConfig().getConfig().getInt("fox-reveal-time-default", 70);
        if(defaultCount > 0) {
            setCountFox(defaultCount + 1);
            runnableFox = new BukkitRunnable() {
                @Override
                public void run() {
                    if(getCountFox() > 0) {
                        subtractCountFox(1);
                        if(getCountFox() == 60 || getCountFox() == 30 || getCountFox() == 10) {
                            getPlayer().sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_FOX_ATTACK_ANYONE.replaceAll("%SECOND%", String.valueOf(getCountFox())));
                        }
                        if(getCountFox() <= 5 && getCountFox() != 0) {
                            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
                            getPlayer().sendMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_FOX_ATTACK_ANYONE.replaceAll("%SECOND%", String.valueOf(getCountFox())));
                        }
                    }
                    if(getCountFox() == 0) {
                        Game game = NanamiTct.plugin.getGame();
                        String str = String.join(", ", game.foxes);
                        Bukkit.broadcastMessage(Reference.TCT_CHATPREFIX + " " + Reference.TCT_CHAT_FOX_IS.replaceAll("%PLAYER%", str));
                        for(String name : game.foxes) {
                            if(game.getReference().PLAYERDATA.containsKey(name)) {
                                PlayerData data = game.getReference().PLAYERDATA.get(name);
                                if(data.getWatcher() != null) {
                                    data.getWatcher().setCountFox(-1);
                                }
                            }
                        }
                    }
                    if(getCountFox() == -1) {
                        if(!getPlayerData().isSpectator()) {
                            FileConfiguration config = NanamiTct.plugin.getTctConfig().getConfig();
                            Firework fw = (Firework) getPlayer().getWorld().spawnEntity(getPlayer().getLocation(), EntityType.FIREWORK);
                            FireworkMeta meta = fw.getFireworkMeta();
                            FireworkEffect.Builder builder = FireworkEffect.builder();
                            try {
                                FireworkEffect.Type type = FireworkEffect.Type.valueOf(config.getString("fireworks.type", "BURST"));
                                builder.with(type);
                            } catch (Exception e) {
                                builder.with(FireworkEffect.Type.BURST);
                            }
                            try {
                                for (String color : (List<String>) config.getList("fireworks.colors", Arrays.asList("0xff0000"))) {
                                    int c;
                                    if (color.startsWith("0x")) {
                                        c = (int) Long.parseLong(color, 16);
                                    } else {
                                        c = (int) Long.parseLong(color, 10);
                                    }
                                    builder.withColor(Color.fromRGB(c));
                                }
                            } catch(Exception e) {
                                builder.withColor(Color.RED);
                            }
                            try {
                                for (String color : (List<String>) config.getList("fireworks.fades", Arrays.asList("0xff0000"))) {
                                    int c;
                                    if (color.startsWith("0x")) {
                                        c = (int) Long.parseLong(color, 16);
                                    } else {
                                        c = (int) Long.parseLong(color, 10);
                                    }
                                    builder.withFade(Color.fromRGB(c));
                                }
                            } catch(Exception e) {
                                builder.withFade(Color.RED);
                            }
                            if(builder != null) {
                                FireworkEffect effect = builder.build();
                                meta.addEffect(effect);
                                meta.setPower(config.getInt("fireworks.power"));
                            } else {
                                FireworkEffect effect = FireworkEffect.builder().with(FireworkEffect.Type.BURST).withColor(Color.RED).withFade(Color.RED).build();
                                meta.addEffect(effect);
                                meta.setPower(1);
                            }
                            fw.setFireworkMeta(meta);
                        }
                    }
                }
            };
            runnableFox.runTaskTimer(getGame().getPlugin(), 0, 20);
        }
    }

    public void cancelCountFox() {
        if(runnableFox != null) {
            runnableFox.cancel();
        }
    }

    public int getCountFox() {
        return this.count;
    }

    public void setCountFox(int value) {
        this.count = value;
    }

    public void addCountFox(int value) {
        this.count += value;
    }

    public void subtractCountFox(int value) {
        this.count -= value;
    }

    public void cancelPlayerWatcher() {
        if(runnable != null) {
            runnable.cancel();
            runnable10.cancel();
        }
    }

    public void setLastStoodBlock(Location loc) {
        this.lastBlock = loc;
    }

}
