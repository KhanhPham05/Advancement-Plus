package com.khanhpham.advancementplus.utils;

import net.minecraft.advancements.Advancement;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public enum AdvancementChapters {
    ADVENTURE("adventure", "fall_from_world_height", "play_jukebox_in_meadows", "spyglass_at_dragon", "spyglass_at_parrot", "spyglass_at_ghast", "lightning_rod_with_villager_no_fire", "bullseye", "two_birds_one_arrows", "whos_the_pillager_now", "ol_betsy", "totem_of_undying", "sniper_duel", "summon_iron_golem", "very_very_frightening", "throw_trident", "shoot_arrow", "kill_a_mob", "kill_all_mobs", "trade", "sleep_in_bed", "adventuring_time", "arbalistic", "hero_of_the_village", "honey_block_slide", "voluntary_exile", "walk_on_powder_snow_with_leather_boost"),
    END("end", "dragon_breath", "dragon_egg", "elytra", "enter_end_gateway", "find_end_city", "kill_dragon", "levitate", "respawn_dragon"),
    HUSBANDRY("husbandry", "axolotl_in_a_bucket", "balanced_diet", "bred_all_animals", "complete_catalogue", "fishy_business", "kill_axolotl_target", "make_a_sign_glow", "obtain_netherite_hoe", "plant_seed", "ride_a_boat_with_goat", "safe_harvest_honey", "silk_touch_nest", "tactical_fishing", "tame_an_animal", "wax_off", "wax_on"),
    NETHER("nether", "all_effects", "all_potions", "brew_potion", "charge_respawn_anchor", "create_beacon", "create_full_beacon", "distract_piglin", "explore_nether", "fast_travel", "find_bastion", "find_fortress", "get_wither_skull", "loot_bastion", "netherite_armor", "obtain_ancient_debris", "obtain_blaze_rod", "obtain_crying_obsidian", "return_to_sender", "rise_strider", "ride_strider_in_overworld_lava", "summon_wither", "uneasy_alliance", "use_lodestone"),
    STORY("story", "cure_zombie_villager", "deflect_arrow", "enchant_item", "enter_the_end", "enter_the_nether", "follow_ender_eye", "form_obsidian", "iron_tools", "lava_bucket", "mine_diamond", "mine_stone", "obtain_armor", "shiny_gear", "smelt_iron", "upgrade_tools");



    private final List<ResourceLocation> advancements;

    AdvancementChapters(String type, String... names) {
        List<ResourceLocation> rls = new ArrayList<>();
        String name;
        for (String s : names) {
            name = type + '/' + s;
            //ModUtils.info("advancement with type "+ type.toUpperCase(Locale.ROOT) +"[{}]", name);
            rls.add(new ResourceLocation(name));
        }

        this.advancements = rls;
    }

    @Nullable
    public static AdvancementChapters getTypeFromAdvancement(ResourceLocation advancement) {
        for (AdvancementChapters value : AdvancementChapters.values()) {
            ResourceLocation[] a = value.getAdvancements().toArray(new ResourceLocation[0]);
            for (ResourceLocation resourceLocation : a) {
                if (advancement.toString().equals(resourceLocation.toString())) {
                    //ModUtils.info("Advancement with id [" + advancement + "] is match with type [{" + value.toString().toUpperCase(Locale.ROOT) + "}]");
                    return value;
                }
            }
        }

        return null;
    }

    @Deprecated
    public void action(Advancement except, Consumer<ResourceLocation> action) {
        ResourceLocation rl = except.getId();
        getAdvancements().stream().filter(a -> a != rl).forEach(action);
    }

    public List<ResourceLocation> getAdvancements() {
        ModUtils.info("Type " + this + " has " + this.advancements.size() + " advancements");
        return advancements;
    }

    @Deprecated
    public Advancement valueOf(AdvancementGetter getter, ResourceLocation rl) {
        AtomicReference<Advancement> advancement = new AtomicReference<>();
        advancements.stream().filter(location -> location.toString().equals(rl.toString())).forEach(loc -> advancement.set(getter.getAdvancement(loc)));
        return advancement.get();
    }
}
