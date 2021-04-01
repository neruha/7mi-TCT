package me.clockclap.tct.event;

import me.clockclap.tct.NanamiTct;
import me.clockclap.tct.game.GameState;
import me.clockclap.tct.game.role.GameRoles;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class ProtectWorld implements Listener {

    private NanamiTct plugin;

    public ProtectWorld(NanamiTct plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if(!p.isOp()) {
            e.setCancelled(true);
        }
        if(plugin.getGame().getReference().getGameState() == GameState.GAMING) {
            if (plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName())).getRole() != GameRoles.SPEC ||
                    plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName())).getRole() != GameRoles.NONE ||
                    plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName())).isSpectator()) {
                e.setCancelled(true);
            }
        }
    }

    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if(!p.isOp()) {
            e.setCancelled(true);
        }
        if(plugin.getGame().getReference().getGameState() == GameState.GAMING) {
            if (plugin.getGame().getReference().PLAYERDATA.get(NanamiTct.utilities.resetColor(p.getName())).getRole() != GameRoles.SPEC || plugin.getGame().getReference().PLAYERDATA.get(p.getName()).getRole() != GameRoles.NONE) {
                e.setCancelled(true);
            }
        }
    }

}
