package com.khanhpham.advancementplus.utils;

import com.khanhpham.advancementplus.AdvancementPlus;
import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;

public class ModUtils {
    public static ResourceLocation modLoc(String loc) {
        return new ResourceLocation(AdvancementPlus.MOD_ID, loc);
    }

    @Deprecated(forRemoval = true)
    public static ResourceLocation image(String loc) {
        return modLoc("textures/" + loc + ".png");
    }

    @Deprecated(forRemoval = true)
    public static ResourceLocation blockBackground(Block block) {
        ResourceLocation rl = block.getRegistryName();
        if (rl != null) {
            String path = rl.getPath();
            String modid = rl.getNamespace();
            return new ResourceLocation(modid,"textures/block/" +  path + ".png");
        } else  throw new IllegalStateException("Registry does not represent");
    }

    public static ResourceLocation mcLoc(String string) {
        return new ResourceLocation(string);
    }

    public static <T, A extends T> T returnIfNonNull(@Nullable T t, A ifNull) {
        return t != null ? t : ifNull;
    }

    public static void info(String message, Object... param) {
        AdvancementPlus.LOG.info(message, param);
    }

    public static void printDebugLocation(Advancement advancement) {
        ModUtils.info("Advancement [{}]", advancement.getId().toString());
    }

    public static void info(Object object) {
        AdvancementPlus.LOG.info(object);
    }
}
