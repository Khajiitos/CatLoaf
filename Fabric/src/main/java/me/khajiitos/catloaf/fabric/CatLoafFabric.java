package me.khajiitos.catloaf.fabric;

import me.khajiitos.catloaf.common.config.CatLoafConfig;
import net.fabricmc.api.ClientModInitializer;

public class CatLoafFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CatLoafConfig.init();
    }
}
