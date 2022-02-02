package com.khanhpham.advancementplus.utils;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.ServerAdvancementManager;

@Deprecated
public class AdvancementGetter {
    public ServerAdvancementManager getManager() {
        return manager;
    }

    private final ServerAdvancementManager manager;

    public AdvancementGetter(ServerAdvancementManager manager) {
        this.manager = manager;
    }

    public Advancement getAdvancement(ResourceLocation location) {
        return manager.getAdvancement(location);
    }

    public AdvancementChapters getTypeFromAdvancement(Advancement advancement) {
        ResourceLocation loc = advancement.getId();
        return AdvancementChapters.getTypeFromAdvancement(loc);
    }
}
