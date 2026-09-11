package dev.fixpot47.fixordium;

import net.minecraft.client.renderer.blockentity.state.BannerRenderState;
import net.minecraft.client.renderer.blockentity.state.BeaconRenderState;
import net.minecraft.client.renderer.blockentity.state.BellRenderState;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.renderer.blockentity.state.ConduitRenderState;
import net.minecraft.client.renderer.blockentity.state.EnchantTableRenderState;
import net.minecraft.client.renderer.blockentity.state.EndGatewayRenderState;
import net.minecraft.client.renderer.blockentity.state.ShulkerBoxRenderState;
import net.minecraft.client.renderer.blockentity.state.SkullBlockRenderState;
import net.minecraft.client.renderer.blockentity.state.SpawnerRenderState;
import net.minecraft.client.renderer.blockentity.state.VaultRenderState;

public final class FixordiumAnimationOptimizer {
    private FixordiumAnimationOptimizer() {
    }

    public static void optimize(BlockEntityRenderState state) {
        if (!FixordiumClient.isEnabled()) {
            return;
        }

        if (!FixordiumClient.areContainerAnimationsEnabled()) {
            optimizeContainerAnimation(state);
        }

        if (!FixordiumClient.areBlockEntityAnimationsEnabled()) {
            optimizeWorldAnimation(state);
        }
    }

    private static void optimizeContainerAnimation(BlockEntityRenderState state) {
        if (state instanceof ChestRenderState chest) {
            // Preserve the useful open/closed state, but remove smooth interpolation.
            chest.open = snap(chest.open);
        } else if (state instanceof ShulkerBoxRenderState shulker) {
            shulker.progress = snap(shulker.progress);
        }
    }

    private static void optimizeWorldAnimation(BlockEntityRenderState state) {
        if (state instanceof BannerRenderState banner) {
            banner.phase = 0.0F;
        } else if (state instanceof BellRenderState bell) {
            bell.ticks = 0.0F;
            bell.shakeDirection = null;
        } else if (state instanceof EnchantTableRenderState enchantingTable) {
            enchantingTable.time = 0.0F;
            enchantingTable.flip = 0.0F;
            enchantingTable.open = 1.0F;
            enchantingTable.yRot = 0.0F;
        } else if (state instanceof SkullBlockRenderState skull) {
            skull.animationProgress = 0.0F;
        } else if (state instanceof SpawnerRenderState spawner) {
            // Used by both normal mob spawners and trial spawners.
            spawner.spin = 0.0F;
        } else if (state instanceof BeaconRenderState beacon) {
            beacon.animationTime = 0.0F;
        } else if (state instanceof ConduitRenderState conduit) {
            conduit.activeRotation = 0.0F;
            conduit.animTime = 0.0F;
            conduit.animationPhase = 0;
        } else if (state instanceof VaultRenderState vault) {
            vault.spin = 0.0F;
        } else if (state instanceof EndGatewayRenderState gateway) {
            // Keep spawn/cooldown beam height and color, only stop texture rotation/scroll.
            gateway.animationTime = 0.0F;
        }
    }

    private static float snap(float value) {
        return value >= 0.5F ? 1.0F : 0.0F;
    }
}
