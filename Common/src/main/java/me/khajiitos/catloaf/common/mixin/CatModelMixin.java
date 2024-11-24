package me.khajiitos.catloaf.common.mixin;

import me.khajiitos.catloaf.common.loaf.ILoafable;
import me.khajiitos.catloaf.common.loaf.Loafify;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.FelineModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.CatRenderState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CatModel.class)
public abstract class CatModelMixin extends FelineModel<CatRenderState> {

    public CatModelMixin(ModelPart $$0) {
        super($$0);
    }

    @Override
    public void setupAnim(@NotNull CatRenderState catRenderState) {
        super.setupAnim(catRenderState);

        if (catRenderState instanceof ILoafable loafable && catRenderState.isSitting && loafable.isLoafing()) {
            // Exported into a separate function for easier debugging,
            // since "Compile And Reload File" doesn't work on Mixins

            Loafify.loafify(this.leftHindLeg, this.rightHindLeg, this.leftFrontLeg, this.rightFrontLeg, this.tail1, this.tail2, this.head, this.body);
        }
    }
}
