package com.khanhpham.advancementplus.icon;

import com.khanhpham.advancementplus.AdvancementPlus;
import com.khanhpham.advancementplus.utils.ModUtils;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = AdvancementPlus.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Icons {
    public static final Item MOD_ICON;
    public static final Item MONUMENT_ICON;
    public static final Item MANSION_ICON;
    public static final Item HEART;
    private static final Helper helper = new Helper();

    static {
        MOD_ICON = icon("mod_icon");
        MONUMENT_ICON = icon("monument_icon");
        MANSION_ICON = icon("mansion_icon");
        HEART = icon("heart");
    }

    public Icons() {
    }

    @SubscribeEvent
    public static void registerIcons(RegistryEvent.Register<Item> reg) {
        helper.init(reg.getRegistry());
    }

    private static Item icon(String name) {
        return helper.register(name);
    }

    public static final class IconModel extends ItemModelProvider {
        final ResourceLocation parent = new ResourceLocation("item/generated");

        public IconModel(DataGenerator generator, ExistingFileHelper existingFileHelper) {
            super(generator, AdvancementPlus.MOD_ID, existingFileHelper);
        }

        @Override
        protected void registerModels() {
            single(Icons.MOD_ICON);
            single(Icons.MANSION_ICON);
            single(Icons.MONUMENT_ICON);
            single(Icons.HEART);
        }

        private void single(Item icon) {

            @SuppressWarnings("deprecation") String name = Registry.ITEM.getKey(icon).getPath();
            withExistingParent(name, parent).texture("layer0", modLoc("item/" + name));
        }
    }

    private static final class Helper {
        private final Set<Item> set = new HashSet<>();

        private Item register(String name) {
            Item item = new Item(new Item.Properties()).setRegistryName(new ResourceLocation(AdvancementPlus.MOD_ID, name));
            set.add(item);
            return item;
        }

        private void init(IForgeRegistry<Item> r) {
            set.forEach(r::register);
        }
    }
}
