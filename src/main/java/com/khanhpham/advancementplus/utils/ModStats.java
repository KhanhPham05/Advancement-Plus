package com.khanhpham.advancementplus.utils;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

/**
 * @see net.minecraft.stats.Stats
 */
public class ModStats {
    public static final ResourceLocation TOTAL_EMERALD_TRADED;
    public static final Stat<Item> CRAFTED_EMERALD = Stats.ITEM_CRAFTED.get(Items.EMERALD, StatFormatter.DEFAULT);

    static {
        TOTAL_EMERALD_TRADED = custom("total_emerald_traded", StatFormatter.DEFAULT);
    }

    private static ResourceLocation custom(String name, StatFormatter formatter) {
        ResourceLocation rl = ModUtils.modLoc(name);
        Registry.register(Registry.CUSTOM_STAT, rl, rl);
        Stats.CUSTOM.get(rl, formatter);
        return rl;
    }
}
