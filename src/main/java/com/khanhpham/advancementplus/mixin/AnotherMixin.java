package com.khanhpham.advancementplus.mixin;

import com.khanhpham.advancementplus.triggers.ModCriteriaTriggers;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CriteriaTriggers.class)
public abstract class AnotherMixin {

    @Inject(at = @At("RETURN"), method = "register")
    private static <T extends CriterionTrigger<?>> void reg(T p_10596_, CallbackInfoReturnable<T> cir) {
        ModCriteriaTriggers.init();
    }


}
