package dev.fixpot47.fixordium.mixin;

import dev.fixpot47.fixordium.FixordiumAnimationOptimizer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityRenderDispatcher.class)
public abstract class BlockEntityRenderDispatcherMixin {
    @Inject(method = "tryExtractRenderState", at = @At("RETURN"))
    private void fixordium$optimizeBlockEntityAnimation(
            BlockEntity blockEntity,
            float partialTicks,
            ModelFeatureRenderer.CrumblingOverlay breakProgress,
            CallbackInfoReturnable<BlockEntityRenderState> cir
    ) {
        BlockEntityRenderState state = cir.getReturnValue();
        if (state != null) {
            FixordiumAnimationOptimizer.optimize(state);
        }
    }
}
