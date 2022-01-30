package com.khanhpham.advancementplus;

import com.khanhpham.advancementplus.icon.Icons;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AdvancementPlus.MOD_ID)
@Mod.EventBusSubscriber(modid = AdvancementPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class AdvancementPlus {
    public static final String MOD_ID = "advancementplus";
    public static final Logger LOG = LogManager.getLogger("APlus");

    public AdvancementPlus() {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
