package me.khajiitos.catloaf.common.config.cloth;

import me.khajiitos.catloaf.common.config.CatLoafConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class ClothConfigScreenMaker {

    public static Screen create(Minecraft minecraft, Screen parent) {
        return create(parent);
    }

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new TranslatableComponent("catloaf.config.header"))
                .setSavingRunnable(CatLoafConfig::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory generalCategory = builder.getOrCreateCategory(new TranslatableComponent("catloaf.config.category.general"));

        generalCategory.addEntry(entryBuilder.startIntField(new TranslatableComponent("catloaf.config.loafChance"), CatLoafConfig.loafChance.get())
                .setDefaultValue(CatLoafConfig.loafChance::getDefault)
                .setMin(CatLoafConfig.loafChance.getMin())
                .setMax(CatLoafConfig.loafChance.getMax())
                .setTooltip(new TranslatableComponent("catloaf.config.loafChance.description"))
                .setSaveConsumer(CatLoafConfig.loafChance::set)
                .build());

        generalCategory.addEntry(entryBuilder.startBooleanToggle(new TranslatableComponent("catloaf.config.overrideChanceWithBread"), CatLoafConfig.overrideChanceWithBread.get())
                .setDefaultValue(CatLoafConfig.overrideChanceWithBread::getDefault)
                .setTooltip(new TranslatableComponent("catloaf.config.overrideChanceWithBread.description"))
                .setSaveConsumer(CatLoafConfig.overrideChanceWithBread::set)
                .build());

        return builder.build();
    }
}
