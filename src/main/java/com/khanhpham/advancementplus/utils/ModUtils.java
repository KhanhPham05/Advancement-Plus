package com.khanhpham.advancementplus.utils;

import com.khanhpham.advancementplus.AdvancementPlus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class ModUtils {
    public static ResourceLocation modLoc(String loc) {
        return new ResourceLocation(AdvancementPlus.MOD_ID, loc);
    }

    public static ResourceLocation image(String loc) {
        return modLoc("textures/" + loc + ".png");
    }

    public static ResourceLocation blockBackground(Block block) {
        ResourceLocation rl = block.getRegistryName();
        if (rl != null) {
            String path = rl.getPath();
            String modid = rl.getNamespace();
            return new ResourceLocation(modid,"textures/block/" +  path + ".png");
        } else  throw new IllegalStateException("Registry does not represent");
    }

    public static ResourceLocation advancement(String loc) {
        return modLoc("advancement/" + loc);
    }
}
