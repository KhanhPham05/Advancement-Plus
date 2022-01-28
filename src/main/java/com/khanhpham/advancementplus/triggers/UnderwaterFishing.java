package com.khanhpham.advancementplus.triggers;

import com.google.gson.JsonObject;
import com.khanhpham.advancementplus.utils.ModUtils;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class UnderwaterFishing extends SimpleCriterionTrigger<UnderwaterFishing.TriggerInstance> {
    public static final ResourceLocation ID = ModUtils.modLoc("underwater_fishing");

    @Override
    protected TriggerInstance createInstance(JsonObject p_66248_, EntityPredicate.Composite p_66249_, DeserializationContext p_66250_) {
        ItemPredicate fishItem = ItemPredicate.fromJson(p_66248_.get("item"));
        return new TriggerInstance(fishItem);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void activate(ServerPlayer serverPlayer, List<ItemStack> loot) {
        //AdvancementPlus.LOG.info("Checking player...");
        super.trigger(serverPlayer, instance -> instance.matches(serverPlayer, loot));
    }

    public static final class TriggerInstance extends AbstractCriterionTriggerInstance {
        private final ItemPredicate fishItem;

        public TriggerInstance(ItemPredicate fishItem) {
            super(ID, EntityPredicate.Composite.ANY);
            this.fishItem = fishItem;
        }

        public static TriggerInstance create(ItemPredicate predicate) {
            return new TriggerInstance(predicate);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext p_16979_) {
            JsonObject json = super.serializeToJson(p_16979_);
            json.add("item", fishItem.serializeToJson());
            return json;
        }

        private boolean matches(ServerPlayer player, List<ItemStack> loot) {
            boolean flag = false;
            if (fishItem == ItemPredicate.ANY) {
                flag = true;
            } else {
                for (ItemStack item : loot) {
                    if (fishItem.matches(item)) {
                        flag = true;
                        break;
                    }
                }
            }

            boolean flag1 = player.isUnderWater();
            // System.out.printf("Player is underwater: %s" , flag1);
            //System.out.printf("Loot is correct : %s", flag);
            return flag && flag1;
        }

    }
}
