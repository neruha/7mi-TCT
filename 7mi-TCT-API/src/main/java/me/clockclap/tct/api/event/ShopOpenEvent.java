package me.clockclap.tct.api.event;

import me.clockclap.tct.game.TCTGame;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class ShopOpenEvent extends GamePlayerEvent {

    private static HandlerList handlers;

    public ShopOpenEvent(TCTGame game, Player who) {
        super(game, who);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
