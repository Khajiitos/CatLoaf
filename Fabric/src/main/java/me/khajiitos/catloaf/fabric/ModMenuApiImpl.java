package me.khajiitos.catloaf.fabric;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.khajiitos.catloaf.common.config.cloth.ClothConfigCheck;
import me.khajiitos.catloaf.common.config.cloth.ClothConfigScreenMaker;

public class ModMenuApiImpl implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (ClothConfigCheck.isInstalled()) {
            return ClothConfigScreenMaker::create;
        }

        return ModMenuApi.super.getModConfigScreenFactory();
    }
}
