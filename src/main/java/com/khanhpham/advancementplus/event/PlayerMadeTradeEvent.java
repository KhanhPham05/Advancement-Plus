package com.khanhpham.advancementplus.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;

/**
 * @see net.minecraftforge.event.village.VillagerTradesEvent
 */

public class PlayerMadeTradeEvent extends Event implements IModBusEvent {
    private final Player tradingPlayer;
    private final ItemStack tradedItems;

    public PlayerMadeTradeEvent(Player tradingPlayer, ItemStack tradedItems) {
        this.tradedItems = tradedItems;
        this.tradingPlayer = tradingPlayer;
    }

    public Player getTradingPlayer() {
        return tradingPlayer;
    }

    public ItemStack getTradedItems() {
        return tradedItems;
    }
}
