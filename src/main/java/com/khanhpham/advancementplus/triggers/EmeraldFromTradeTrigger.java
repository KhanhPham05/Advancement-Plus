package com.khanhpham.advancementplus.triggers;

import com.google.gson.JsonObject;
import com.khanhpham.advancementplus.utils.ModUtils;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class EmeraldFromTradeTrigger extends SimpleCriterionTrigger<EmeraldFromTradeTrigger.Instance> {
    public static final ResourceLocation ID = ModUtils.modLoc("emerald_from_trade");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    protected Instance createInstance(JsonObject json, EntityPredicate.Composite player, DeserializationContext context) {
        MinMaxBounds.Ints tradeAmount = MinMaxBounds.Ints.fromJson(json.get("amount"));
        return new Instance(player, tradeAmount);
    }

    public void activate(ServerPlayer player, int tradedCount) {
        super.trigger(player, (i) ->
            i.matches(tradedCount)
        );
    }

    public static final class Instance extends AbstractCriterionTriggerInstance {
        final MinMaxBounds.Ints tradeAmount;

        public Instance(EntityPredicate.Composite player, MinMaxBounds.Ints tradeAmount) {
            super(ID, player);
            this.tradeAmount = tradeAmount;
        }

        public static Instance trade(MinMaxBounds.Ints tradeAmount) {
            return new Instance(EntityPredicate.Composite.ANY, tradeAmount);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject object = super.serializeToJson(context);
            object.add("amount", tradeAmount.serializeToJson());
            return object;
        }

        public boolean matches(int count) {
            return tradeAmount.matches(count);
        }
    }
}
