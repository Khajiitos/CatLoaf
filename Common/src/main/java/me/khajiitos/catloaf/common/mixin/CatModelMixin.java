package me.khajiitos.catloaf.common.mixin;

import me.khajiitos.catloaf.common.loaf.ILoafable;
import me.khajiitos.catloaf.common.loaf.Loafify;
import net.minecraft.client.model.CatModel;
import net.minecraft.client.model.OcelotModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.animal.Cat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CatModel.class)
public abstract class CatModelMixin extends OcelotModel<Cat> {

    public CatModelMixin(ModelPart $$0) {
        super($$0);
    }

    @Inject(at = @At(value = "TAIL"), method = "prepareMobModel(Lnet/minecraft/world/entity/animal/Cat;FFF)V")
    public void prepareMobModel(Cat cat, float $$1, float $$2, float $$3, CallbackInfo ci) {
        // May be overriden by the loafify function
        // so we are restoring it every setupAnim call, because it isn't restored by the game
        // hopefully this doesn't clash with functionality of other mods
        tail1.visible = true;
        tail2.visible = true;

        if (cat instanceof ILoafable loafable && cat.isInSittingPose() && loafable.isLoafing()) {
            // Exported into a separate function for easier debugging,
            // since "Compile And Reload File" doesn't work on Mixins

            Loafify.loafify(this.leftHindLeg, this.rightHindLeg, this.leftFrontLeg, this.rightFrontLeg, this.tail1, this.tail2, this.head, this.body);
        }
    }
}
