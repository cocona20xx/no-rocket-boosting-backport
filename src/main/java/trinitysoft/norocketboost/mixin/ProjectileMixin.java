package trinitysoft.norocketboost.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import trinitysoft.norocketboost.NoRocketBoostMod;

@Mixin(Projectile.class)
public abstract class ProjectileMixin extends Entity implements TraceableEntity {
    public ProjectileMixin(EntityType<?> $entityType, Level $level) {
        super($entityType, $level);
    }
    @Inject(
            at = @At("HEAD"),
            method = "checkLeftOwner()Z",
            cancellable = true
    )
    // allow arrows to hit their owner before leaving owners hitbox while owner is flying
    protected void checkLeftOwner(CallbackInfoReturnable<Boolean> cir) {
        if((Entity)this instanceof AbstractArrow) {
            Entity owner = this.getOwner();

            if (owner instanceof ServerPlayer) {
                boolean punchBoostingRuleValue = owner.level().getGameRules().getRule(NoRocketBoostMod.RULE_PUNCH_BOOSTING).get();

                if (((ServerPlayer) owner).isFallFlying() && punchBoostingRuleValue) {
                    Vec3 deltaMovement = this.getDeltaMovement();
                    Vec3 deltaMovementOwner = owner.getDeltaMovement();
                    double deltaLengthDiff = deltaMovement.subtract(deltaMovementOwner).length();

                    if(owner.getXRot() > -47 && deltaLengthDiff < 0.45 && deltaLengthDiff > 0) {
                        cir.setReturnValue(true);
                    }
                }
            }
        }
    }
}
