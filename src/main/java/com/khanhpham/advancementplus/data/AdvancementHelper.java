package com.khanhpham.advancementplus.data;

import com.google.common.collect.ImmutableSet;
import com.khanhpham.advancementplus.triggers.UnderwaterFishing;
import com.khanhpham.advancementplus.utils.ModUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
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

import java.awt.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
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
    public Advancement tradeMap(Advancement parent, String saveName, ItemLike iconItem, TranslatableComponent translateComponent, FrameType frame, boolean toChat, StructureFeature<?> destination) {
        String desc = translateComponent.getKey();
        Component descComponent = new TranslatableComponent(desc + ".desc");
        Advancement.Builder builder = builder().display(iconItem, translateComponent, descComponent, null, frame, true, toChat, false);

        String mapName = "filled_map." + destination.getFeatureName().toLowerCase(Locale.ROOT);

        CompoundTag nbt = new CompoundTag();
        CompoundTag nbt1 = new CompoundTag();

        nbt.putString("Name", Component.Serializer.toJson(new TranslatableComponent(mapName)));
        nbt.putInt("MapColor", 3830373);
        nbt1.put("display", nbt);

        //System.out.println(mapName);

        builder.addCriterion("trade_" + Registry.ITEM.getKey(iconItem.asItem()).getPath() + "_" + destination.getFeatureName(),
                new TradeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, EntityPredicate.Composite.ANY,
                        ItemPredicate.Builder.item().of(Items.FILLED_MAP).hasNbt(nbt1).build()));

        builder.parent(parent);
        return builder.save(consumer, ModUtils.modLoc(saveName), fileHelper);
    }

    /**
     * @see net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity
     */
    public Advancement gatherItems(Advancement parent, String saveName, Component title, Component desc, FrameType frame, boolean toChat, ItemLike... items) {
        var builder = builder().display(items[0], title, desc, null, frame, true, toChat, false).parent(parent);
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

        return builder.save(consumer, ModUtils.modLoc(saveName), fileHelper);
    }

    public Advancement gatherItems(Advancement parent, String saveName, TranslatableComponent translate, FrameType frame, boolean toChat, ItemLike... items) {
        Component component = new TranslatableComponent(translate.getKey() + ".desc");
        return this.gatherItems(parent, saveName, translate, component, frame, toChat, items);
    }

    public InventoryChangeTrigger.TriggerInstance hasItem(Item item) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(item);
    }

    public Advancement locateFeature(Advancement parent, String saveName, ItemLike icon, TranslatableComponent title, FrameType frame, boolean toChat, StructureFeature<?> destination) {
        Advancement.Builder builder = builder();
        builder.parent(parent);

        Component description = new TranslatableComponent(title.getKey() + ".desc");
        builder.display(icon, title, description, null, frame, true, toChat, false);

        builder.addCriterion("locate" + destination.getFeatureName().toLowerCase(Locale.ROOT), LocationTrigger.TriggerInstance.located(LocationPredicate.inFeature(destination)));

        return builder.save(consumer, ModUtils.modLoc(saveName), fileHelper);
    }

    public Advancement defeatMob(Advancement parent, String saveName, ItemLike icon, TranslatableComponent title, FrameType frame, boolean toChat, EntityType<?> entity, DamageSourcePredicate.Builder damageSource) {
        Advancement.Builder builder = builder();
        builder.display(icon, title, getDescription(title), null, frame, true ,toChat, false)
                .parent(parent);

        EntityPredicate entityPredicate = EntityPredicate.Builder.entity().of(entity).build();
        DamageSourcePredicate damageSourcePredicate = damageSource.build();;

        builder.addCriterion("kill_" + Objects.requireNonNull(entity.getRegistryName()).getPath(), KilledTrigger.TriggerInstance.playerKilledEntity(entityPredicate, damageSourcePredicate));
        return builder.save(consumer, ModUtils.modLoc(saveName), fileHelper);
    }

    public Advancement breed(Advancement parent, String saveName, ItemLike icon, TranslatableComponent title, FrameType frame, boolean toChat, EntityPredicate parentAnimal, EntityPredicate partner) {
        Advancement.Builder builder = builder();
        builder.display(icon, title, getDescription(title), null, frame, true, toChat, false);
        builder.parent(parent);

        builder.addCriterion("breed", BredAnimalsTrigger.TriggerInstance.bredAnimals(parentAnimal, partner, EntityPredicate.ANY));
        return builder.save(consumer, ModUtils.modLoc(saveName), fileHelper);
    }


    public Advancement enterCave(Advancement parent, String saveName, ItemLike icon, TranslatableComponent title, FrameType frame, boolean toChat, Set<ResourceKey<Biome>> caveBiomes) {
        Advancement.Builder builder = builder();
        builder.display(icon, title, getDescription(title), null, frame, true, toChat, false);
        builder.parent(parent);
        caveBiomes.forEach(caveBiome -> builder.addCriterion("to_cave_" + caveBiome.getRegistryName().getPath(), LocationTrigger.TriggerInstance.located(LocationPredicate.inBiome(caveBiome))));
        return builder.save(consumer, ModUtils.modLoc(saveName), fileHelper);
    }

    public Advancement fishUnderwater(Advancement parent, String saveName, ItemLike icon, TranslatableComponent title, FrameType frame, boolean toChat, boolean hide, ItemPredicate fishItems) {
        Advancement.Builder builder = setDisplay(parent, icon, title, frame, true, toChat, hide);
        builder.addCriterion("modded_trigger", UnderwaterFishing.TriggerInstance.create(fishItems));
        return builder.save(consumer, ModUtils.modLoc(saveName), fileHelper);
    }

    public Advancement.Builder setDisplay(Advancement parent, ItemLike icon, TranslatableComponent component, FrameType frame, boolean toast, boolean toChat, boolean hidden) {
        return builder().display(icon, component, getDescription(component), null, frame, toast, toChat, hidden).parent(parent);
    }

    public Advancement enterCave(Advancement parent, String saveName, ItemLike icon, TranslatableComponent title, FrameType frame, boolean toChat, ResourceKey<Biome> biome) {
        return this.enterCave(parent, saveName, icon, title, frame, toChat, ImmutableSet.of(biome));
    }

    public TranslatableComponent getDescription(TranslatableComponent title) {
        return new TranslatableComponent(title.getKey() + ".desc");
    }

}
