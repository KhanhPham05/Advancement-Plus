package com.khanhpham.advancementplus.triggers;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;

public class ModCriteriaTriggers {
    public static final EmeraldFromTradeTrigger EMERALD_FROM_TRADE;
    public static final UnderwaterFishing UNDERWATER_FISHING;
    public static final CompleteAdvancementTrigger COMPLETE_ADVANCEMENT;

    static {
         EMERALD_FROM_TRADE = register(new EmeraldFromTradeTrigger());
         UNDERWATER_FISHING = register(new UnderwaterFishing());
         COMPLETE_ADVANCEMENT = register(new CompleteAdvancementTrigger());
    }

    private static <T extends CriterionTrigger<?>> T register(T instance) {
        return CriteriaTriggers.register(instance);
    };

    public static void init() {}
}
