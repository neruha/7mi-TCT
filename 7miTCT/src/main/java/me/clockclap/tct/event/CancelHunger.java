package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.data.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class CancelHunger implements Listener {

    private NanamiTct plugin;

    public CancelHunger(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent e) {
        if(e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if(p != null) {
                PlayerData data = plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName()));
                if(data != null) {
                    if (data.isSpectator() || (data.isInvisible() && plugin.getGame().getElapsedTime() > 1)) {
                        e.setFoodLevel(20);
                    } else {
                        e.setFoodLevel(1);
                    }
                }
            }
        }
    }

}
