package me.clockclap.tct.api.event;

import me.clockclap.tct.game.TctGame;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class ShopOpenEvent extends GamePlayerEvent {

    private static HandlerList handlers;

    public ShopOpenEvent(TctGame game, Player who) {
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
