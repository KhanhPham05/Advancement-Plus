package com.khanhpham.advancementplus.mixin;

import com.khanhpham.advancementplus.AdvancementPlus;
import com.khanhpham.advancementplus.event.PlayerMadeTradeEvent;
import com.khanhpham.advancementplus.triggers.EmeraldFromTradeTrigger;
import com.khanhpham.advancementplus.triggers.ModCriteriaTriggers;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractVillager.class)
public abstract class PlayerTradeData extends AgeableMob {
    @Shadow
    private Player tradingPlayer;

    private int tradedEmeralds;

    protected PlayerTradeData(EntityType<? extends AgeableMob> p_146738_, Level p_146739_) {
        super(p_146738_, p_146739_);
    }

    @Inject(at = @At("HEAD"), method = "notifyTrade")
    private void notifyTrade(@NotNull MerchantOffer offer, CallbackInfo ci) {
        ItemStack emeraldTraded = offer.getResult();

        CompoundTag tag = new CompoundTag();
        tag.putInt("TradedEmeralds", tradedEmeralds);

        MinecraftForge.EVENT_BUS.post(new PlayerMadeTradeEvent(tradingPlayer, emeraldTraded));

        if (emeraldTraded.is(Items.EMERALD)) {
            int count = emeraldTraded.getCount();
            tradedEmeralds += count;
            System.out.println("You traded " + count + " Emeralds, " + tradedEmeralds);
        }

    }



   /* @Inject(at = @At("RETURN"), method = "addAdditionalSaveData")
    private void save(CompoundTag p_35301_, CallbackInfo ci) {
        p_35301_.putInt("TradedEmeralds", tradedEmeralds);
       // System.out.println("saved");
    }

    @Inject(at = @At("RETURN"), method = "readAdditionalSaveData")
    private void readAdditionalSaveData(CompoundTag p_35290_, CallbackInfo info) {
        tradedEmeralds = p_35290_.getInt("TradedEmeralds");
    }*/
}
