package com.khanhpham.advancementplus.event;

import com.khanhpham.advancementplus.AdvancementPlus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = AdvancementPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Test {

    public Test() {}

    @SubscribeEvent
    public static void tradeEvent(PlayerMadeTradeEvent event) {
    }
}
