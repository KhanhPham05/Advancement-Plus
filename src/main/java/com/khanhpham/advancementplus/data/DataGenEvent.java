package com.khanhpham.advancementplus.data;

import com.khanhpham.advancementplus.Names;
import com.khanhpham.advancementplus.data.advancements.AdvancementGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Names.MOD_ID,  bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenEvent {
    private DataGenEvent() {}

    @SubscribeEvent
    public static void dataGenEvent(GatherDataEvent event) {
        DataGenerator data = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        //advancements
        data.addProvider(new AdvancementGenerator(data, helper));
        //icons

        //lang
    }
}
