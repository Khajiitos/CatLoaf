package me.khajiitos.catloaf.common.mixin;

import me.khajiitos.catloaf.common.loaf.ILoafable;
import net.minecraft.client.renderer.entity.CatRenderer;
import net.minecraft.client.renderer.entity.state.CatRenderState;
import net.minecraft.world.entity.animal.Cat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CatRenderer.class)
public class CatRendererMixin {

    @Inject(at = @At("TAIL"), method = "extractRenderState(Lnet/minecraft/world/entity/animal/Cat;Lnet/minecraft/client/renderer/entity/state/CatRenderState;F)V")
    public void extractRenderState(Cat cat, CatRenderState catRenderState, float v, CallbackInfo ci) {
        if (cat instanceof ILoafable catLoafable && catRenderState instanceof ILoafable renderStateLoafable) {
            renderStateLoafable.setLoafing(catLoafable.isLoafing());
        }
    }
}
