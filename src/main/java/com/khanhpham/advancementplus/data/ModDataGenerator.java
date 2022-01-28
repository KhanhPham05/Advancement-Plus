package com.khanhpham.advancementplus.data;

import com.khanhpham.advancementplus.AdvancementPlus;
import com.khanhpham.advancementplus.icon.Icons;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = AdvancementPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDataGenerator {
    public ModDataGenerator() {
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        AdvancementPlus.LOG.info("APlus Data Event Fired");
        DataGenerator data = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();

        data.addProvider(new Icons.IconModel(data, fileHelper));
        data.addProvider(new APAdvancements(data, fileHelper));
        data.addProvider(new ModLanguageProvider(data));

    }
}
