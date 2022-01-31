package com.khanhpham.advancementplus.triggers;

import com.google.gson.JsonObject;
import com.khanhpham.advancementplus.AdvancementPlus;
import com.khanhpham.advancementplus.utils.ModUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class CompleteAdvancementTrigger extends SimpleCriterionTrigger<CompleteAdvancementTrigger.TriggerInstance> {
    public static final ResourceLocation ID = ModUtils.modLoc("complete_advancements");

    @Override
    protected TriggerInstance createInstance(JsonObject p_66248_, EntityPredicate.Composite p_66249_, DeserializationContext p_66250_) {
        ResourceLocationPredicate reqAdvancement = ResourceLocationPredicate.fromJson(p_66248_);
        return new TriggerInstance(reqAdvancement);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    /**
     * @see net.minecraft.server.PlayerAdvancements
     */
    public void activate(ServerPlayer player, Advancement requiresAdvancement) {
        super.trigger(player, instance -> instance.matches(requiresAdvancement));
    }

    public static final class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ResourceLocationPredicate requiredAdvancement;

        public TriggerInstance(ResourceLocationPredicate requiredAdvancement) {
            super(ID, EntityPredicate.Composite.ANY);
            this.requiredAdvancement = requiredAdvancement;
        }

        public static TriggerInstance of(ResourceLocationPredicate predicate) {
            return new TriggerInstance(predicate);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext p_16979_) {
            JsonObject object = super.serializeToJson(p_16979_);
            object.add(ResourceLocationPredicate.LOCATION, requiredAdvancement.toJson());
            return object;
        }

        private boolean matches(Advancement req) {
            //AdvancementPlus.LOG.info("Requirement advancement [{}]", req.getId());
            return requiredAdvancement.matches(req.getId());
        }
    }
}
