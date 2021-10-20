package com.khanhpham.advancementplus.data.lang;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageGenerator extends LanguageProvider {
    public LanguageGenerator(DataGenerator gen, String modid, String locale) {
        super(gen, modid, locale);
    }

    @Override
    protected void addTranslations() {
        add("root", "This Is Advancement Plus", "Getting Started by getting some wood to craft a workbench");
        add("diamond_pick", "Isn't This Diamond Pick ?", "It function as same as Iron Pick, but it's Diamond");
        add("music_box", "Musician Box", "Craft the Jukebox and put the discs you found in. Enjoy the music !");
        add("disc", "The World Of Music", "Obtain for yourself one particular type of music disc");
    }

    private void add(String name, String title, String description) {
        super.add("advancement."+name+".t", title);
        super.add("advancement."+name+".d", description);
    }
}
