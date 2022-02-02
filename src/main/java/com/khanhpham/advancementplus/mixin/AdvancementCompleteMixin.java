package com.khanhpham.advancementplus.mixin;

import com.khanhpham.advancementplus.triggers.ModCriteriaTriggers;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.util.FakePlayer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @see net.minecraft.client.gui.screens.advancements.AdvancementsScreen
 */
@Mixin(PlayerAdvancements.class)
public abstract class AdvancementCompleteMixin {

    @Shadow
    private ServerPlayer player;

    @Shadow
    @Final
    private Map<Advancement, AdvancementProgress> advancements;

    //  /advancement revoke @s only adventure/sleep_in_bed
    @Inject(at = @At("RETURN"), method = "award(Lnet/minecraft/advancements/Advancement;Ljava/lang/String;)Z")
    private void award(Advancement p_135989_, String p_135990_, CallbackInfoReturnable<Boolean> cir) {
        if (!(player instanceof FakePlayer)) {
            List<Advancement> completedAdvancements = new LinkedList<>();
            for (Map.Entry<Advancement, AdvancementProgress> progressEntry : this.advancements.entrySet()) {
                if (progressEntry.getValue().isDone()) {
                    completedAdvancements.add(progressEntry.getKey());
                }
            }

            completedAdvancements.stream().filter(a -> !a.getId().toString().contains("recipes")).forEach(this::trigger);
        }
    }

    private void trigger(Advancement advancement) {
        ModCriteriaTriggers.COMPLETE_ADVANCEMENT.activate(this.player, advancement);
    }
}
