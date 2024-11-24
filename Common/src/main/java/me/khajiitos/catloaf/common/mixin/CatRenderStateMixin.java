package me.khajiitos.catloaf.common.mixin;

import me.khajiitos.catloaf.common.loaf.ILoafable;
import net.minecraft.client.renderer.entity.state.CatRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(CatRenderState.class)
public class CatRenderStateMixin implements ILoafable {
    @Unique
    private boolean catLoaf$isLoafing = false;

    @Override
    public boolean isLoafing() {
        return this.catLoaf$isLoafing;
    }

    @Override
    public void setLoafing(boolean loafing) {
        this.catLoaf$isLoafing = loafing;
    }
}
