package com.khanhpham.advancementplus.mixin;

import net.minecraft.advancements.Advancement;
import net.minecraft.server.PlayerAdvancements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerAdvancements.class)
public abstract class AdvancementMixin {

    public AdvancementMixin() {
    }

    @Inject(at = @At("RETURN"), method = "award")
    private void award(Advancement pAdvancement, String pCriterionKey, CallbackInfoReturnable<Boolean> cir) {
        System.out.printf("Completed Advancement With Location: %s", pAdvancement.getId());
    }
}
