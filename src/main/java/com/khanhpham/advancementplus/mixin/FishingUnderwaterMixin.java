package com.khanhpham.advancementplus.mixin;

import com.khanhpham.advancementplus.AdvancementPlus;
import com.khanhpham.advancementplus.triggers.ModCriteriaTriggers;
import net.minecraft.advancements.Advancement;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.Objects;

@Mixin(FishingHook.class)
public abstract class FishingUnderwaterMixin extends Projectile {

    @Shadow @Nullable public abstract Player getPlayerOwner();

    @Shadow protected abstract boolean shouldStopFishing(Player p_37137_);

    @Shadow @Final private int luck;

    @Shadow private int nibble;

    protected FishingUnderwaterMixin(EntityType<? extends Projectile> p_37248_, Level p_37249_) {
        super(p_37248_, p_37249_);
    }

    @Inject(at = @At("HEAD"), method = "retrieve(Lnet/minecraft/world/item/ItemStack;)I")
    private void retrieve(ItemStack itemStack, CallbackInfoReturnable<Integer> callback) {
        //AdvancementPlus.LOG.info("Fishing gook retrieved");
        Player player = getPlayerOwner();
        if (!this.level.isClientSide && player!= null && !this.shouldStopFishing(player) && this.nibble > 0) {
            LootContext.Builder builder = (new LootContext.Builder((ServerLevel)this.level)).withParameter(LootContextParams.ORIGIN, this.position()).withParameter(LootContextParams.TOOL, itemStack).withParameter(LootContextParams.THIS_ENTITY, this).withRandom(this.random).withLuck((float)this.luck + player.getLuck());
            builder.withParameter(LootContextParams.KILLER_ENTITY, Objects.requireNonNull(this.getOwner())).withParameter(LootContextParams.THIS_ENTITY, this);
            LootTable lootTable = Objects.requireNonNull(level.getServer()).getLootTables().get(BuiltInLootTables.FISHING);
           // AdvancementPlus.LOG.info("Player is underwater: " + player.isUnderWater());
            ModCriteriaTriggers.UNDERWATER_FISHING.activate((ServerPlayer) player, lootTable.getRandomItems(builder.create(LootContextParamSets.FISHING)));
            //AdvancementPlus.LOG.info("triggering");
        }
    }
}
