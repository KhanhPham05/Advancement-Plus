package com.khanhpham.advancementplus.mixin;

import com.khanhpham.advancementplus.AdvancementPlus;
import com.khanhpham.advancementplus.triggers.ModCriteriaTriggers;
import net.minecraft.advancements.Advancement;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(PlayerAdvancements.class)
public abstract class AdvancementCompleteMixin {

    @Shadow
    private ServerPlayer player;

    @Inject(at = @At("RETURN"), method = "award(Lnet/minecraft/advancements/Advancement;Ljava/lang/String;)Z")
    private void award(Advancement p_135989_, String p_135990_, CallbackInfoReturnable<Boolean> cir) {
        if (!(player instanceof FakePlayer)) {
            ModCriteriaTriggers.COMPLETE_ADVANCEMENT.activate(player, p_135989_);
            AdvancementPlus.LOG.info("Advancement Completed {} " , p_135989_.getId().toString());
        }
    }
}
