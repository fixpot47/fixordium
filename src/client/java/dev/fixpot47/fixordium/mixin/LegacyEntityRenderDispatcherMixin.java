package dev.fixpot47.fixordium.mixin;

import dev.fixpot47.fixordium.FixordiumRuntime;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.class_898", remap = false)
public abstract class LegacyEntityRenderDispatcherMixin {
    @Inject(method = "method_3950", at = @At("HEAD"), cancellable = true, remap = false)
    private void fixordium$preCull(
            @Coerce Object entity,
            @Coerce Object frustum,
            double cameraX,
            double cameraY,
            double cameraZ,
            CallbackInfoReturnable<Boolean> cir
    ) {
        if (FixordiumRuntime.shouldCull(entity, frustum)) {
            cir.setReturnValue(false);
        }
    }
}
