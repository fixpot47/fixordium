package dev.fixpot47.fixordium;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.Screens;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public final class FixordiumClient implements ClientModInitializer {
    public static final String MOD_ID = "fixordium";

    // Keep nearby entities rendered to prevent visible pop-in around the player.
    private static final double MIN_CULL_DISTANCE_SQ = 16.0;

    // Dot product below this value means the entity is safely outside the forward view.
    // 0.15 is deliberately conservative so entities near the edge of the screen are kept.
    private static final double VIEW_DOT_THRESHOLD = 0.15;

    private static FixordiumConfig config;

    @Override
    public void onInitializeClient() {
        config = FixordiumConfig.load();

        ScreenEvents.AFTER_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (!(screen instanceof VideoSettingsScreen)) {
                return;
            }

            Button button = Button.builder(toggleText(), pressed -> {
                        config.enabled = !config.enabled;
                        config.save();
                        pressed.setMessage(toggleText());
                    })
                    .bounds(Math.max(6, scaledWidth - 116), 6, 110, 20)
                    .tooltip(Tooltip.create(Component.literal(
                            "Skips rendering living entities that are safely outside your view to reduce unnecessary rendering work."
                    )))
                    .build();

            Screens.getButtons(screen).add(button);
        });
    }

    public static boolean isEnabled() {
        return config != null && config.enabled;
    }

    public static boolean shouldCull(Entity entity, double cameraX, double cameraY, double cameraZ) {
        if (!isEnabled() || !(entity instanceof LivingEntity)) {
            return false;
        }

        Minecraft client = Minecraft.getInstance();
        if (client.player == null || entity == client.player) {
            return false;
        }

        double dx = entity.getX() - cameraX;
        double dy = entity.getY() + entity.getBbHeight() * 0.5 - cameraY;
        double dz = entity.getZ() - cameraZ;
        double distanceSq = dx * dx + dy * dy + dz * dz;

        if (distanceSq <= MIN_CULL_DISTANCE_SQ) {
            return false;
        }

        double length = Math.sqrt(distanceSq);
        if (length <= 0.0001) {
            return false;
        }

        Vec3 look = client.player.getLookAngle();
        if (client.options.getCameraType() == CameraType.THIRD_PERSON_FRONT) {
            look = look.scale(-1.0);
        }

        double dot = (dx * look.x + dy * look.y + dz * look.z) / length;
        return dot < VIEW_DOT_THRESHOLD;
    }

    private static Component toggleText() {
        return Component.literal("Fixordium: " + (isEnabled() ? "ON" : "OFF"));
    }
}
