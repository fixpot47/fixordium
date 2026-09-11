package dev.fixpot47.fixordium;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public final class FixordiumRuntime {
    private FixordiumRuntime() {
    }

    public static boolean shouldCull(Entity entity, Frustum frustum) {
        if (!FixordiumClient.isEnabled() || !(entity instanceof LivingEntity)) {
            return false;
        }

        // This is deliberately limited to a fast camera-frustum pre-check.
        // It never unloads, freezes, or changes entity logic, and it does not
        // interfere with wall-occlusion, particle, chunk, or block culling mods.
        return !frustum.isVisible(entity.getBoundingBox());
    }
}
