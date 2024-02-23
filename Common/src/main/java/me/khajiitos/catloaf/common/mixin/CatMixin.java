package me.khajiitos.catloaf.common.mixin;

import me.khajiitos.catloaf.common.ILoafable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Cat.class)
public abstract class CatMixin extends TamableAnimal implements ILoafable {
    @Unique
    private static final EntityDataAccessor<Boolean> LOAFING = SynchedEntityData.defineId(Cat.class, EntityDataSerializers.BOOLEAN);;

    protected CatMixin(EntityType<? extends TamableAnimal> $$0, Level $$1) {
        super($$0, $$1);
    }

    @Inject(at = @At("TAIL"), method = "defineSynchedData")
    public void defineSynchedData(CallbackInfo ci) {
        this.entityData.define(LOAFING, false);
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
        this.setLoafing(sitting && this.random.nextBoolean());
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
}
