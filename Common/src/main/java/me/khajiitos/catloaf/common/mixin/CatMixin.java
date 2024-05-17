package me.khajiitos.catloaf.common.mixin;

import me.khajiitos.catloaf.common.config.CatLoafConfig;
import me.khajiitos.catloaf.common.loaf.ILoafable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Cat.class)
public abstract class CatMixin extends TamableAnimal implements ILoafable {
    @Unique
    private boolean catloaf$forceLoaf = false;

    @Unique
    @SuppressWarnings("all")
    private static final EntityDataAccessor<Boolean> LOAFING = SynchedEntityData.defineId(Cat.class, EntityDataSerializers.BOOLEAN);

    protected CatMixin(EntityType<? extends TamableAnimal> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Inject(at = @At("TAIL"), method = "defineSynchedData")
    public void defineSynchedData(SynchedEntityData.Builder builder, CallbackInfo ci) {
        builder.define(LOAFING, false);
    }

    @Override
    public boolean isLoafing() {
        return this.entityData.get(LOAFING);
    }

    @Override
    public void setLoafing(boolean loafing) {
        this.entityData.set(LOAFING, loafing);
    }

    @Override
    public void setInSittingPose(boolean sitting) {
        super.setInSittingPose(sitting);
        this.setLoafing(this.isOrderedToSit() && sitting && (catloaf$forceLoaf || this.random.nextInt(100) < CatLoafConfig.loafChance.get()));
        catloaf$forceLoaf = false;
    }

    @Inject(at = @At("TAIL"), method = "addAdditionalSaveData")
    public void addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        if (isLoafing()) {
            compoundTag.putBoolean("IsLoafing", true);
        }
    }

    @Inject(at = @At("TAIL"), method = "readAdditionalSaveData")
    public void readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        this.setLoafing(compoundTag.getBoolean("IsLoafing"));
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Cat;setOrderedToSit(Z)V", ordinal = 0, shift = At.Shift.AFTER), method = "mobInteract")
    public void mobInteract(Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> callbackInfo) {
        if (CatLoafConfig.overrideChanceWithBread.get() && player.getItemInHand(interactionHand).is(Items.BREAD) && this.isOrderedToSit()) {
            catloaf$forceLoaf = true;
        }
    }
}
