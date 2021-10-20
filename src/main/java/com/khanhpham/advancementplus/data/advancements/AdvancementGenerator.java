package com.khanhpham.advancementplus.data.advancements;

import com.khanhpham.advancementplus.Names;
import com.khanhpham.advancementplus.icon.IconRegistries;
import net.minecraft.advancements.*;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Consumer;

import static net.minecraft.world.item.Items.*;

public class AdvancementGenerator extends AdvancementProvider {

    private String key;

    public AdvancementGenerator(DataGenerator data, ExistingFileHelper helper) {
        super(data, helper);
    }

    /**
     * @see net.minecraft.data.advancements.TheEndAdvancements
     *
     */
    @Override
    protected void registerAdvancements(Consumer<Advancement> c, ExistingFileHelper h) {
        var root = get().display(IconRegistries.mainIcon.get(), title("root"), desc(), new ResourceLocation("textures/block/oak_planks.png"), FrameType.CHALLENGE, false, false, false)
                .addCriterion("get_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.CRAFTING_TABLE))
                .save(c, rl("root"), h);
        var diamondPick = build(root, c, h, Items.DIAMOND_PICKAXE, "diamond_pick", FrameType.TASK, "has_d_pick", hasItem(Items.DIAMOND_PICKAXE));

        var musicBox = build(diamondPick, c, h, Blocks.JUKEBOX, "music_box", FrameType.TASK, "has_jukebox", hasItem(Blocks.JUKEBOX));

        /* --------- Building disc advancement -------- */
        var discBuilder = get()
                .display(Items.MUSIC_DISC_WAIT, title("disc"), desc(), null, FrameType.TASK, true, true, false)
                .parent(musicBox)
                .rewards(AdvancementRewards.Builder.function(rl("function/reward_diamond")).build());

        for (Item discItem : DISC_ITEMS) {
            discBuilder.addCriterion("has_" + discItem.getRegistryName().getPath(), hasItem(discItem));
        }

        //TODO L Declare an Advancement type when needed
        discBuilder.requirements(RequirementsStrategy.OR)
                .save(c, rl("disc"), h);
        /* ------------- Building Ended ----------*/

        var netherite = build(diamondPick, c, h, NETHERITE_INGOT, "netherite_ed", FrameType.GOAL, "has_netherite_ingot", hasItem(NETHERITE_INGOT));

        var nTools = get().display(NETHERITE_PICKAXE, title("cover_me_with_netherite_tools"), desc(), null, FrameType.GOAL, true, true, false)
                .addCriterion("has_pick", hasItem(NETHERITE_PICKAXE))
                .addCriterion("has_axe", hasItem(NETHERITE_AXE))
                .addCriterion("has_sh", hasItem(NETHERITE_SHOVEL))
                ;
    }

    /**
     * Stores all the disc types
     */

    private Advancement build(Advancement parent, Consumer<Advancement> c, ExistingFileHelper h, ItemLike icon, String transKey, FrameType frame, String criterionName, CriterionTriggerInstance instance) {
        return get().display(icon, title(transKey), desc(),null, frame, true, true, false)
                .parent(parent)
                .addCriterion(criterionName, instance)
                .save(c, rl(transKey), h);
    }

    private final NonNullList<Item> DISC_ITEMS = NonNullList.of(MUSIC_DISC_13, MUSIC_DISC_CAT, MUSIC_DISC_BLOCKS, MUSIC_DISC_CHIRP, MUSIC_DISC_FAR, MUSIC_DISC_MALL, MUSIC_DISC_MELLOHI, MUSIC_DISC_STAL, MUSIC_DISC_STRAD, MUSIC_DISC_WARD, MUSIC_DISC_11, MUSIC_DISC_WAIT, MUSIC_DISC_PIGSTEP);

    private InventoryChangeTrigger.TriggerInstance hasItem(ItemLike... items) {
        return InventoryChangeTrigger.TriggerInstance.hasItems(items);
    }

    private Advancement.Builder get() {
        return Advancement.Builder.advancement();
    }

    private Component desc() {
        return new TranslatableComponent("advancement." + this.key + ".d");
    }

    private Component title(String key) {
        this.key = key;
        return new TranslatableComponent("advancement." + key + ".t");
    }

    private ResourceLocation rl(String path) {
        return new ResourceLocation(Names.MOD_ID, path);
    }
}
