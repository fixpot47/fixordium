package dev.fixpot47.fixordium.mixin;

import dev.fixpot47.fixordium.FixordiumClient;
import dev.fixpot47.fixordium.FixordiumRuntime;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public abstract class ModernEntityRenderDispatcherMixin {
    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private <E extends Entity> void fixordium$preCull(
            E entity,
            Frustum frustum,
            double cameraX,
            double cameraY,
            double cameraZ,
            float partialTicks,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (FixordiumRuntime.shouldCull(entity, frustum)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "extractEntity", at = @At("RETURN"))
    private <E extends Entity> void fixordium$optimizeEntityRenderState(
            E entity,
            float partialTicks,
            CallbackInfoReturnable<EntityRenderState> cir
    ) {
        EntityRenderState state = cir.getReturnValue();
        if (state != null && FixordiumClient.isEnabled() && !FixordiumClient.shouldRenderEntityShadows()) {
            state.shadowPieces.clear();
        }
    }
}
