package me.khajiitos.catloaf.forge;

import me.khajiitos.catloaf.common.CatLoaf;
import me.khajiitos.catloaf.common.config.CatLoafConfig;
import me.khajiitos.catloaf.common.config.cloth.ClothConfigCheck;
import me.khajiitos.catloaf.common.config.cloth.ClothConfigScreenMaker;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(CatLoaf.MOD_ID)
public class CatLoafForge {
    public CatLoafForge() {
        CatLoafConfig.init();

        if (FMLLoader.getDist() == Dist.CLIENT && ClothConfigCheck.isInstalled()) {
            ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class, () -> new ConfigGuiHandler.ConfigGuiFactory(ClothConfigScreenMaker::create));
        }
    }
}