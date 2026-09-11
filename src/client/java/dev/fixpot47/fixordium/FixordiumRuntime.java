package dev.fixpot47.fixordium;

import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public final class FixordiumRuntime {
    private static final long SAMPLE_NANOS = 1_000_000_000L;

    private static long sampleStartedAt = System.nanoTime();
    private static long cullsInWindow;
    private static long displayedCullsPerSecond;
    private static long totalCulls;

    private FixordiumRuntime() {
    }

    public static boolean shouldCull(Entity entity, Frustum frustum) {
        if (!FixordiumClient.isEnabled() || !(entity instanceof LivingEntity)) {
            return false;
        }

        // Fast early camera-frustum check before vanilla resolves the entity renderer.
        // The entity is never removed, frozen or changed in the world.
        boolean culled = !frustum.isVisible(entity.getBoundingBox());
        if (culled) {
            recordCull();
        }
        return culled;
    }

    private static void recordCull() {
        rollCounterWindow();
        cullsInWindow++;
        totalCulls++;
    }

    private static void rollCounterWindow() {
        long now = System.nanoTime();
        long elapsed = now - sampleStartedAt;
        if (elapsed < SAMPLE_NANOS) {
            return;
        }

        displayedCullsPerSecond = elapsed > 0
                ? Math.round(cullsInWindow * (1_000_000_000.0 / elapsed))
                : cullsInWindow;
        cullsInWindow = 0L;
        sampleStartedAt = now;
    }

    public static long getCullsPerSecond() {
        rollCounterWindow();
        return displayedCullsPerSecond;
    }

    public static long getTotalCulls() {
        return totalCulls;
    }
}
