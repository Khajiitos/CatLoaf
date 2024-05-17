package me.khajiitos.catloaf.neoforged;

import me.khajiitos.catloaf.common.CatLoaf;
import me.khajiitos.catloaf.common.config.CatLoafConfig;
import me.khajiitos.catloaf.common.config.cloth.ClothConfigCheck;
import me.khajiitos.catloaf.common.config.cloth.ClothConfigScreenMaker;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(CatLoaf.MOD_ID)
public class CatLoafNeoForged {
    public CatLoafNeoForged() {
        CatLoafConfig.init();

        if (FMLLoader.getDist() == Dist.CLIENT && ClothConfigCheck.isInstalled()) {
            ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class, () -> ClothConfigScreenMaker::create);
        }
    }
}