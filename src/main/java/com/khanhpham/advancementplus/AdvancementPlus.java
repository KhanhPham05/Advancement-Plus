package com.khanhpham.advancementplus;

import com.khanhpham.advancementplus.icon.IconRegistries;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Names.MOD_ID)
public class AdvancementPlus {

    public AdvancementPlus() {
        MinecraftForge.EVENT_BUS.register(this);

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        IconRegistries.ITEMS.register(bus);
    }
    
}
