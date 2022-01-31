package com.khanhpham.advancementplus.data;

import com.google.common.collect.ImmutableSet;
import com.khanhpham.advancementplus.AdvancementPlus;
import com.khanhpham.advancementplus.triggers.CompleteAdvancementTrigger;
import com.khanhpham.advancementplus.triggers.ResourceLocationPredicate;
import com.khanhpham.advancementplus.triggers.UnderwaterFishing;
import com.khanhpham.advancementplus.utils.ModUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.*;
import java.util.function.Consumer;

public class AdvancementHelper {
    public final Consumer<Advancement> consumer;
    public final ExistingFileHelper fileHelper;

    public final FrameType frameTask = FrameType.TASK;
    public final FrameType frameChallenge = FrameType.CHALLENGE;
    public final FrameType frameGoal = FrameType.GOAL;

    public AdvancementHelper(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {
        this.consumer = consumer;
        this.fileHelper = fileHelper;
    }

    public Advancement.Builder builder() {
        return Advancement.Builder.advancement();
    }

    @SuppressWarnings("deprecation")
    public Advancement tradeMap(Advancement parent, String saveName, ItemLike icon, TranslatableComponent title, FrameType frame, boolean toChat, StructureFeature<?> destination) {
        Advancement.Builder builder = setDisplay(parent, icon, title, frame, true, toChat, false);

        String mapName = "filled_map." + destination.getFeatureName().toLowerCase(Locale.ROOT);

        CompoundTag nbt = new CompoundTag();
        CompoundTag nbt1 = new CompoundTag();

        nbt.putString("Name", Component.Serializer.toJson(new TranslatableComponent(mapName)));
        nbt.putInt("MapColor", 3830373);
        nbt1.put("display", nbt);

        builder.addCriterion("trade_" + Registry.ITEM.getKey(icon.asItem()).getPath() + "_" + destination.getFeatureName(),
                new TradeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, EntityPredicate.Composite.ANY,
                        ItemPredicate.Builder.item().of(Items.FILLED_MAP).hasNbt(nbt1).build()));

        return save(builder, ModUtils.modLoc(saveName));
    }

    /**
     * @see net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
     */
    public Advancement gatherItems(Advancement parent, String saveName, TranslatableComponent title, FrameType frame, boolean toChat, ItemLike... items) {
        var builder = setDisplay(parent, items[0], title, frame, true, toChat, false);
        if (items.length > 1) {
            Arrays.stream(items).map(ItemLike::asItem).forEach(item -> {
                ResourceLocation rl = item.getRegistryName();
                if (rl == null) {
                    throw new IllegalStateException("Item " + item + " not represent");
                }
                builder.addCriterion("has_" + rl.getPath(), hasItem(item));
            });
        } else {
            Item item = items[0].asItem();
            ResourceLocation rl = item.getRegistryName();
            if (rl == null) {
                throw new IllegalStateException("Item " + item + " not represent");
            }
            builder.addCriterion("has_" + rl.getPath(), hasItem(item));
        }

        return save(builder, ModUtils.modLoc(saveName));
    }

    public InventoryChangeTrigger.TriggerInstance hasItem(Item item) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(item);
    }

    public Advancement locateFeature(Advancement parent, String saveName, ItemLike icon, TranslatableComponent title, FrameType frame, boolean toChat, StructureFeature<?> destination) {
        Advancement.Builder builder = setDisplay(parent, icon, title, frame, true, toChat, false);

        builder.addCriterion("locate" + destination.getFeatureName().toLowerCase(Locale.ROOT), LocationTrigger.TriggerInstance.located(LocationPredicate.inFeature(destination)));

        return save(builder, ModUtils.modLoc(saveName));
    }

    public Advancement defeatMob(Advancement parent, String saveName, ItemLike icon, TranslatableComponent title, FrameType frame, boolean toChat, EntityType<?> entity, DamageSourcePredicate.Builder damageSource) {
        Advancement.Builder builder = setDisplay(parent, icon, title, frame, true, toChat, false);

        EntityPredicate entityPredicate = EntityPredicate.Builder.entity().of(entity).build();
        DamageSourcePredicate damageSourcePredicate = damageSource.build();


        builder.addCriterion("kill_" + Objects.requireNonNull(entity.getRegistryName()).getPath(), KilledTrigger.TriggerInstance.playerKilledEntity(entityPredicate, damageSourcePredicate));
        return save(builder, ModUtils.modLoc(saveName));
    }

    public Advancement breed(Advancement parent, String saveName, ItemLike icon, TranslatableComponent title, FrameType frame, boolean toChat, EntityPredicate parentAnimal, EntityPredicate partner) {
        Advancement.Builder builder = setDisplay(parent, icon, title, frame, true, toChat, false);

        builder.addCriterion("breed", BredAnimalsTrigger.TriggerInstance.bredAnimals(parentAnimal, partner, EntityPredicate.ANY));
        return save(builder, ModUtils.modLoc(saveName));
    }


    public Advancement enterCave(Advancement parent, String saveName, ItemLike icon, TranslatableComponent title, FrameType frame, boolean toChat, Set<ResourceKey<Biome>> caveBiomes) {
        Advancement.Builder builder = setDisplay(parent, icon, title, frame, true, toChat, false);
        caveBiomes.forEach(caveBiome -> builder.addCriterion("to_cave_" + caveBiome.getRegistryName().getPath(), LocationTrigger.TriggerInstance.located(LocationPredicate.inBiome(caveBiome))));
        return save(builder, ModUtils.modLoc(saveName));
    }

    public Advancement fishUnderwater(Advancement parent, String saveName, ItemLike icon, TranslatableComponent title, FrameType frame, boolean toChat, boolean hide, ItemPredicate fishItems) {
        Advancement.Builder builder = setDisplay(parent, icon, title, frame, true, toChat, hide);
        builder.addCriterion("modded_trigger", UnderwaterFishing.TriggerInstance.create(fishItems));
        return save(builder, ModUtils.modLoc(saveName));
    }

    public Advancement playerGotKilled(Advancement parent, String saveName, ItemLike icon, TranslatableComponent title, FrameType frame, boolean toChat, EntityPredicate.Builder killerEntity, DamageSourcePredicate.Builder damageSource) {
        Advancement.Builder builder = setDisplay(parent, icon, title, frame, true, toChat, false);

        builder.addCriterion("req", KilledTrigger.TriggerInstance.entityKilledPlayer(killerEntity, damageSource));
        return save(builder, ModUtils.modLoc(saveName));
    }

    public Advancement.Builder setDisplay(ResourceLocation parent, ItemLike icon, TranslatableComponent component, FrameType frame, boolean toast, boolean toChat, boolean hidden) {
        return builder().display(icon, component, getDescription(component), null, frame, toast, toChat, hidden).parent(parent);
    }

    public Advancement.Builder setDisplay(Advancement parent, ItemLike icon, TranslatableComponent component, FrameType frame, boolean toast, boolean toChat, boolean hidden) {
        return builder().display(icon, component, getDescription(component), null, frame, toast, toChat, hidden)
                .parent(parent);
    }
    public void completeAdvancements(String parent, String saveName, ItemLike icon, TranslatableComponent title, FrameType frame, boolean toChat, ResourceLocation... advancementLocations) {
        Advancement.Builder builder = setDisplay(new ResourceLocation(parent), icon, title, frame, true, toChat, false);

        Set<ResourceLocation> locationSet = Set.of(advancementLocations);

        for (ResourceLocation id : locationSet) {
            builder.addCriterion("complete_" + id.getPath(), CompleteAdvancementTrigger.TriggerInstance.of(ResourceLocationPredicate.of(id)));
        }

        builder.requirements(RequirementsStrategy.AND);
        save(builder, ModUtils.modLoc(saveName));
    }

    public void completeAdvancements(String parent, ItemLike icon, TranslatableComponent title, boolean toChat, List<ResourceLocation> advancementLocations) {
        this.completeAdvancements(parent + "/root", "complete/all_" + parent, icon, title, frameChallenge, toChat, advancementLocations.toArray(new ResourceLocation[0]));
    }

        //FOR TESTING ONLY
    public Advancement save(Advancement.Builder builder, ResourceLocation rl) {
        AdvancementPlus.LOG.info("Building Advancement [{}]", rl);
        return builder.save(consumer, rl , fileHelper);
    };

    public Advancement enterCave(Advancement parent, String saveName, ItemLike icon, TranslatableComponent title, FrameType frame, boolean toChat, ResourceKey<Biome> biome) {
        return this.enterCave(parent, saveName, icon, title, frame, toChat, ImmutableSet.of(biome));
    }

    public TranslatableComponent getDescription(TranslatableComponent title) {
        return new TranslatableComponent(title.getKey() + ".desc");
    }
}