package com.khanhpham.advancementplus.data;

import com.khanhpham.advancementplus.AdvancementPlus;
import net.minecraft.data.DataGenerator;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {

    public static final TranslatableComponent ROOT = translate("root");
    public static final TranslatableComponent ROOT_DESC = translate("root.desc");
    public static final TranslatableComponent STAR_TRADER = translate("star_trader");
    public static final TranslatableComponent ARCHER = translate("archer");
    public static final TranslatableComponent AHOY = translate("ahoy");
    public static final TranslatableComponent GET_THE_COALS = translate("get_the_coals");
    public static final TranslatableComponent GET_THE_GOLDS = translate("get_the_golds");
    public static final TranslatableComponent GET_THE_NETHERITE = translate("get_the_netherite");
    public static final TranslatableComponent ALTERNATIVE_FUEL = translate("alternative_fuel");
    public static final TranslatableComponent ALTERNATIVE_FOOD = translate("alternative_food");
    public static final TranslatableComponent HOTTER_ALTERNATIVE_FUEl = translate("hotter_alternative_fuel");
    public static final TranslatableComponent FORTUNATE_FISHERMAN = translate("fortunate_fisherman");
    public static final TranslatableComponent ISN_THIS_DIAMOND_PICK = translate("isn_this_diamond_pick");
    public static final TranslatableComponent PICKAXE_FROM_THE_NETHER = translate("pickaxe_from_the_nether");
    public static final TranslatableComponent FULLY_COVERED = translate("fully_covered");
    public static final TranslatableComponent ROAD_TO_THE_MONUMENT = translate("road_to_the_monument");
    public static final TranslatableComponent ROAD_TO_THE_MANSION = translate("road_to_the_mansion");
    public static final TranslatableComponent THE_CITY_AT_THE_BOTTOM_OF_THE_OCEAN = translate("the_city_at_the_bottom_of_the_ocean");
    public static final TranslatableComponent A_TERRIFYING_MANSION = translate("a_terrifying_mansion");
    public static final TranslatableComponent UNDERGROUND_NATURE = translate("underground_nature");
    public static final TranslatableComponent SPIKY_CLIFFS = translate("spiky_cliffs");
    public static final TranslatableComponent ARTIFICIAL_SELECTION = translate("artificial_selection");
    public static final TranslatableComponent DIVING_FISHERMAN = translate("diving_fisherman");
    public static final TranslatableComponent THE_HAGGLER = translate("the_haggler");

    public ModLanguageProvider(DataGenerator gen) {
        super(gen, AdvancementPlus.MOD_ID, "en_us");
    }

    public static TranslatableComponent translate(String key, Object... args) {
        return new TranslatableComponent(AdvancementPlus.MOD_ID + ".advancement." + key, args);
    }

    @Override
    protected void addTranslations() {
        add(ROOT, "Welcome To Advancement Plus");
        add(ROOT_DESC, "Advancement Plus ! Now 1.18");
        advancement(GET_THE_COALS, "Get The Coals", "Collect a single piece of coal");
        advancement(GET_THE_GOLDS, "Get The Golds", "Collect a single piece of gold ingot");
        advancement(GET_THE_NETHERITE, "Get The Netherite", "Craft an ingot of Netherite, use for tool upgrade");
        advancement(ALTERNATIVE_FOOD, "Alternative Food", "Gather the food of the sea");
        advancement(ALTERNATIVE_FUEL, "Alternative Fuel", "A common type of fuel that can replace coal");
        advancement(HOTTER_ALTERNATIVE_FUEl, "Hotter Alternative Fuel", "A less common type of burning fuel that can only exist in the Nether");
        advancement(FORTUNATE_FISHERMAN, "Fortunate Fisherman", "Get something enchanted just from fishing");
        advancement(ISN_THIS_DIAMOND_PICK, "Isn't This Diamond Pick", "Craft the diamond pickaxe");
        advancement(PICKAXE_FROM_THE_NETHER, "Pickaxe From The Nether", "Upgrade your diamond pickaxe to netherite pickaxe");
        advancement(FULLY_COVERED, "Fully Covered", "Protect yourself with a full set of diamond armor");
        advancement(ROAD_TO_THE_MONUMENT, "Road To The Monument", "Obtain Ocean Monument Explorer Map just by trading with Cartographer");
        advancement(ROAD_TO_THE_MANSION, "Road To The Mansion", "Obtain Woodland Mansion Explorer Map just by trading with Cartographer");
        advancement(THE_CITY_AT_THE_BOTTOM_OF_THE_OCEAN, "The City At The Bottom Of The Ocean", "Locate an Ocean Monument");
        advancement(A_TERRIFYING_MANSION, "A Terrifying Mansion", "Enter a Woodland Mansion");
        advancement(UNDERGROUND_NATURE, "Underground Nature", "Locate a Lush Cave underground");
        advancement(SPIKY_CLIFFS, "Spiky Cliffs", "Locate a Dripstone Caves");
        advancement(STAR_TRADER, "star Trader", "Trade with a Villager on the maximum building height");
        advancement(AHOY, "Ahoy!", "Find a Shipwreck");
        advancement(ARCHER, "Archer", "Kill a Creeper with your cross/bow and arrow");
        advancement(ARTIFICIAL_SELECTION, "Artificial Selection", "Breed a mule from a Horse and a Donkey");
        advancement(DIVING_FISHERMAN, "Diving Fisherman", "Catch a fish underwater");
        advancement(THE_HAGGLER, "The Haggler", "Not Yet Implemented");
    }

    private void advancement(TranslatableComponent component, String title, String desc) {
        this.add(component, title);
        TranslatableComponent c = new TranslatableComponent(component.getKey() + ".desc");
        this.add(c, desc);
    }

    private void add(TranslatableComponent component, String value) {
        super.add(component.getKey(), value);
    }
}
