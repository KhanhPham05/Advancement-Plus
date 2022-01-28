package com.khanhpham.advancementplus.mixin;

import com.khanhpham.advancementplus.AdvancementPlus;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin({TitleScreen.class})
public abstract class MixinTest extends Screen {
    protected MixinTest(Component p_96550_) {
        super(p_96550_);
    }

    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo ci) {
        AdvancementPlus.LOG.debug(" -------------- MIXIN WORKED ---------------");
    }
}
