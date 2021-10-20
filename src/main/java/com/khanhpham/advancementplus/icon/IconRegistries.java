package com.khanhpham.advancementplus.icon;

import com.khanhpham.advancementplus.Names;
import net.minecraft.world.item.Item;
import net.minecraftforge.fmllegacy.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class IconRegistries {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Names.MOD_ID);

    public static final RegistryObject<Item> mainIcon ;

    static {
        mainIcon = ITEMS.register("icon", () -> new Item(new Item.Properties()));
    }
}
