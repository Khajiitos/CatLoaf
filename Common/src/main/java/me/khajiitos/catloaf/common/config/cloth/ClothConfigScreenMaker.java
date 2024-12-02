package me.khajiitos.catloaf.common.config.cloth;

import me.khajiitos.catloaf.common.config.CatLoafConfig;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ClothConfigScreenMaker {

    public static Screen create(Minecraft minecraft, Screen parent) {
        return create(parent);
    }

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("catloaf.config.header"))
                .setSavingRunnable(CatLoafConfig::save);

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory generalCategory = builder.getOrCreateCategory(Component.translatable("catloaf.config.category.general"));

        generalCategory.addEntry(entryBuilder.startIntField(Component.translatable("catloaf.config.loafChance"), CatLoafConfig.loafChance.get())
                .setDefaultValue(CatLoafConfig.loafChance::getDefault)
                .setMin(CatLoafConfig.loafChance.getMin())
                .setMax(CatLoafConfig.loafChance.getMax())
                .setTooltip(Component.translatable("catloaf.config.loafChance.description"))
                .setSaveConsumer(CatLoafConfig.loafChance::set)
                .build());

        generalCategory.addEntry(entryBuilder.startBooleanToggle(Component.translatable("catloaf.config.overrideChanceWithBread"), CatLoafConfig.overrideChanceWithBread.get())
                .setDefaultValue(CatLoafConfig.overrideChanceWithBread::getDefault)
                .setTooltip(Component.translatable("catloaf.config.overrideChanceWithBread.description"))
                .setSaveConsumer(CatLoafConfig.overrideChanceWithBread::set)
                .build());

        generalCategory.addEntry(entryBuilder.startBooleanToggle(Component.translatable("catloaf.config.hideTailWhenLoafing"), CatLoafConfig.hideTailWhenLoafing.get())
                .setDefaultValue(CatLoafConfig.hideTailWhenLoafing::getDefault)
                .setTooltip(Component.translatable("catloaf.config.hideTailWhenLoafing.description"))
                .setSaveConsumer(CatLoafConfig.hideTailWhenLoafing::set)
                .build());

        return builder.build();
    }
}
