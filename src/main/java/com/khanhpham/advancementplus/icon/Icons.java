package com.khanhpham.advancementplus.icon;

import com.khanhpham.advancementplus.AdvancementPlus;
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
    public Icons() {}

    private static final Helper helper = new Helper();

    public static final Item MOD_ICON;
    public static final Item MONUMENT_ICON;
    public static final Item MANSION_ICON;
    public static final Item HEART;

    static  {
        MOD_ICON = helper.register("mod_icon");
        MONUMENT_ICON = helper.register("monument_icon");
        MANSION_ICON = helper.register("mansion_icon");
        HEART = helper.register("heart");
    }

    @SubscribeEvent
    public static void registerIcons(RegistryEvent.Register<Item> reg) {
        helper.init(reg.getRegistry());
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

            @SuppressWarnings("deprecation")  String name = Registry.ITEM.getKey(icon).getPath();
            withExistingParent(name, parent).texture("layer0", modLoc("item/"+name));
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
