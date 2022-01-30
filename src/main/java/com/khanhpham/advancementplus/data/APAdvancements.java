package com.khanhpham.advancementplus.data;

import com.khanhpham.advancementplus.AdvancementPlus;
import com.khanhpham.advancementplus.triggers.EmeraldFromTradeTrigger;
import com.khanhpham.advancementplus.utils.ModUtils;
import com.khanhpham.advancementplus.icon.Icons;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class APAdvancements extends AdvancementProvider {
    public APAdvancements(DataGenerator generatorIn, ExistingFileHelper fileHelperIn) {
        super(generatorIn, fileHelperIn);
    }

    private final Item[] FORTUNATE_FISHERMAN_ITEMS = new Item[]{Items.ENCHANTED_BOOK, Items.BOW, Items.FISHING_ROD};

    /**
     * @see net.minecraft.world.level.block.entity.BlockEntity
     */
    @Override
    protected void registerAdvancements(Consumer<Advancement> consumer, ExistingFileHelper fileHelper) {
        AdvancementPlus.LOG.info("Building advancements");
        AdvancementHelper helper = new AdvancementHelper(consumer, fileHelper);
        Advancement root = helper.builder().addCriterion("tick", new TickTrigger.TriggerInstance(EntityPredicate.Composite.ANY)).display(Icons.MOD_ICON, ModLanguageProvider.ROOT, ModLanguageProvider.ROOT_DESC, new ResourceLocation("textures/block/dark_oak_planks.png"), FrameType.CHALLENGE, false, false, false).save(consumer, ModUtils.modLoc("root"), fileHelper);
        Advancement coal = helper.gatherItems(root, "get_the_coals", ModLanguageProvider.GET_THE_COALS, helper.frameTask, false, Items.COAL);
        Advancement gold = helper.gatherItems(coal, "get_the_golds", ModLanguageProvider.GET_THE_GOLDS, helper.frameTask, false, Items.GOLD_INGOT);
        helper.gatherItems(gold, "get_the_netherite", ModLanguageProvider.GET_THE_NETHERITE, helper.frameGoal, true, Items.NETHERITE_INGOT);

        //Isn't this dia/netherite pickaxe
        Advancement diaPick = helper.gatherItems(root, "isn_this_diamond_pick", ModLanguageProvider.ISN_THIS_DIAMOND_PICK, helper.frameTask, true, Items.DIAMOND_PICKAXE);
        helper.gatherItems(diaPick, "pickaxe_from_the_nether", ModLanguageProvider.PICKAXE_FROM_THE_NETHER, helper.frameGoal, true, Items.NETHERITE_PICKAXE);

        Advancement alternativeFood = helper.gatherItems(root, "alternative_food", ModLanguageProvider.ALTERNATIVE_FOOD, helper.frameTask, true, Items.DRIED_KELP);
        Advancement alternativeFuel = helper.gatherItems(alternativeFood, "alternative_fuel", ModLanguageProvider.ALTERNATIVE_FUEL, helper.frameTask, true, Items.DRIED_KELP_BLOCK);
        helper.gatherItems(alternativeFuel, "hotter_alternative_fuel", ModLanguageProvider.HOTTER_ALTERNATIVE_FUEl, helper.frameTask, false, Items.BLAZE_POWDER);

        //misc
        helper.gatherItems(root, "fully_covered", ModLanguageProvider.FULLY_COVERED, helper.frameGoal, true, Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS);
        fortunateFisherman(consumer, helper, fileHelper, root, FORTUNATE_FISHERMAN_ITEMS);

        //adventure
        Advancement ahoy = helper.locateFeature(root, "bedrock/ahoy", Blocks.STRIPPED_OAK_WOOD, ModLanguageProvider.AHOY, helper.frameTask, false, StructureFeature.SHIPWRECK);
        Advancement roadToTheMonument = helper.tradeMap(ahoy, "road_to_the_monument", Items.FILLED_MAP, ModLanguageProvider.ROAD_TO_THE_MONUMENT, helper.frameTask, true, StructureFeature.OCEAN_MONUMENT);
        helper.locateFeature(roadToTheMonument, "the_city_at_the_bottom_of_the_ocean",Icons.MONUMENT_ICON, ModLanguageProvider.THE_CITY_AT_THE_BOTTOM_OF_THE_OCEAN, helper.frameTask, true, StructureFeature.OCEAN_MONUMENT);

        Advancement roadToTheMansion = helper.tradeMap(roadToTheMonument, "road_to_the_mansion", Items.FILLED_MAP, ModLanguageProvider.ROAD_TO_THE_MANSION, helper.frameTask, true, StructureFeature.WOODLAND_MANSION);
        helper.locateFeature(roadToTheMansion, "a_terrifying_mansion", Icons.MANSION_ICON, ModLanguageProvider.A_TERRIFYING_MANSION, helper.frameGoal, true, StructureFeature.WOODLAND_MANSION);

        //locate cave biomes
        Advancement undergroundNature = helper.enterCave(root, "underground_nature", Blocks.MOSS_BLOCK, ModLanguageProvider.UNDERGROUND_NATURE, helper.frameTask, true, Biomes.LUSH_CAVES);
        helper.enterCave(undergroundNature, "spiky_cliffs", Blocks.DRIPSTONE_BLOCK, ModLanguageProvider.SPIKY_CLIFFS, helper.frameTask, true, Biomes.DRIPSTONE_CAVES);

        //Old / Cross-port Advancement
        Advancement archer = helper.defeatMob(ahoy, "bedrock/archer", Blocks.CREEPER_HEAD, ModLanguageProvider.ARCHER, helper.frameTask, false, EntityType.CREEPER, DamageSourcePredicate.Builder.damageType().isProjectile(true).source(EntityPredicate.Builder.entity().of(EntityType.PLAYER)));
        Advancement starTrader = starTrader(helper, archer);
        Advancement artificialSelection = helper.breed(starTrader, "bedrock/artificial_selection", Icons.HEART, ModLanguageProvider.ARTIFICIAL_SELECTION, helper.frameTask, false, EntityPredicate.Builder.entity().of(EntityType.HORSE).build(), EntityPredicate.Builder.entity().of(EntityType.DONKEY).build());
        Advancement divingFisherman = helper.fishUnderwater(artificialSelection, "underwater_fisherman", Items.FISHING_ROD, ModLanguageProvider.DIVING_FISHERMAN, helper.frameGoal, true, true, ItemPredicate.ANY);
        Advancement theHaggler = theHaggler(helper, divingFisherman);

        overwriteVanilla(helper, root);
        completeVanillaAdvancements(helper);
    }

    private void completeVanillaAdvancements(AdvancementHelper helper) {

        ResourceLocation[] adventureAdvancements = getLocations("adventure", "adventuring_time", "arbalistic", "bullseye", "hero_of_the_village", "honey_block_slide", "kill_a_mob", "kill_all_mobs", "lightning_rod_with_villager_no_fire", "ol_betsy", "shoot_arrow", "sleep_in_bed", "sniper_duel", "spyglass_at_dragon", "spyglass_at_parrot", "spyglass_at_ghast", "summon_iron_golem", "throw_trident", "totem_of_undying", "trade", "two_birds_one_arrows", "very_very_frightening", "voluntary_exile", "walk_on_powder_snow_with_leather_boost", "whos_the_pillager_now");
        helper.completeAdvancements("adventure/root", "complete/all_adventure", Items.FILLED_MAP, ModLanguageProvider.ALL_ADVENTURE, helper.frameChallenge, true, adventureAdvancements);
    }

    private ResourceLocation[] getLocations(String suf, String... locations) {
        Set<ResourceLocation> locationSet = new HashSet<>();
        for (String location : locations) {
            locationSet.add(ModUtils.mcLoc(suf + '/' + location));
        }

        return locationSet.toArray(new ResourceLocation[0]);
    }

    @SuppressWarnings("deprecation")
    private void fortunateFisherman(Consumer<Advancement> consumer, AdvancementHelper helper, ExistingFileHelper fileHelper, Advancement root, Item... items) {
        Advancement.Builder builder = helper.builder().display(Items.ENCHANTED_BOOK, ModLanguageProvider.FORTUNATE_FISHERMAN, new TranslatableComponent(AdvancementPlus.MOD_ID + ".advancement.fortunate_fisherman.desc"), null, helper.frameGoal, true, true, false);
        builder.parent(root);

        for (Item item : items) {
            builder.addCriterion(Registry.ITEM.getKey(item).getPath(), FishingRodHookedTrigger.TriggerInstance.fishedItem(ItemPredicate.ANY, EntityPredicate.ANY, ItemPredicate.Builder.item().of(item).hasEnchantment(EnchantmentPredicate.ANY).build()));
        }
        builder.requirements(RequirementsStrategy.OR).save(consumer, ModUtils.modLoc("fortunate_fisherman"), fileHelper);
    }

    private Advancement starTrader(AdvancementHelper helper, Advancement root) {
        Advancement.Builder builder = helper.builder().display(Items.EMERALD,
                ModLanguageProvider.STAR_TRADER,
                helper.getDescription(ModLanguageProvider.STAR_TRADER),
                null, helper.frameChallenge, true, true, false);
        builder.parent(root);
        TradeTrigger.TriggerInstance trigger = new TradeTrigger.TriggerInstance( EntityPredicate.Composite.ANY, EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().of(EntityType.VILLAGER).located(LocationPredicate.atYLocation(MinMaxBounds.Doubles.atLeast(318.0d))).build()), ItemPredicate.ANY);
        builder.addCriterion("trade", trigger);
        return builder.save(helper.consumer, ModUtils.modLoc("star_trader"), helper.fileHelper);
    }

    private void overwriteVanilla(AdvancementHelper helper, Advancement root) {
        Advancement.Builder.advancement().display(Items.EMERALD, new TranslatableComponent("advancements.adventure.trade_at_world_height.title"), new TranslatableComponent("advancements.adventure.trade_at_world_height.description"), null, FrameType.TASK, false, false, true)
                .parent(root)
                .addCriterion("trade_at_world_height", new ImpossibleTrigger.TriggerInstance())
                .save(helper.consumer, "adventure/trade_at_world_height");
    }

    private Advancement theHaggler(AdvancementHelper helper, Advancement parent) {
        Advancement.Builder builder = helper.builder().parent(parent).display(Items.EMERALD, ModLanguageProvider.THE_HAGGLER, helper.getDescription(ModLanguageProvider.THE_HAGGLER), null, helper.frameTask, true, true, false);
        builder.addCriterion("coded_trigger", EmeraldFromTradeTrigger.Instance.trade(MinMaxBounds.Ints.atLeast(30)));

        return builder.save(helper.consumer, ModUtils.modLoc("bedrock/the_haggler"), helper.fileHelper);
    }
}
