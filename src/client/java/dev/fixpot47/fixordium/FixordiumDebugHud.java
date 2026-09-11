package dev.fixpot47.fixordium;

import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

public final class FixordiumDebugHud {
    private static final Identifier HUD_ID = Identifier.parse("fixordium:culling_counter");

    private FixordiumDebugHud() {
    }

    public static void register() {
        HudElementRegistry.attachElementAfter(VanillaHudElements.HOTBAR, HUD_ID, FixordiumDebugHud::render);
    }

    private static void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (!FixordiumClient.isEnabled() || !FixordiumClient.isDebugCullingCounterEnabled()) {
            return;
        }

        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            return;
        }

        String firstLine = "Fixordium Debug";
        String secondLine = "Skipped renders: " + FixordiumRuntime.getCullsPerSecond() + "/s";
        String thirdLine = "Total: " + FixordiumRuntime.getTotalCulls();
        String fourthLine = smartLine();

        int margin = 5;
        int width = Math.max(
                Math.max(client.font.width(firstLine), client.font.width(secondLine)),
                Math.max(client.font.width(thirdLine), client.font.width(fourthLine))
        );
        int x = graphics.guiWidth() - width - margin;
        int y = margin;

        graphics.text(client.font, firstLine, x, y, 0xFFFFFFFF, true);
        graphics.text(client.font, secondLine, x, y + 10, 0xFFFFFFFF, true);
        graphics.text(client.font, thirdLine, x, y + 20, 0xFFAAAAAA, true);
        graphics.text(client.font, fourthLine, x, y + 30, 0xFFAAAAAA, true);
    }

    private static String smartLine() {
        if (!FixordiumClient.isSmartModeEnabled()) {
            return "Smart: OFF";
        }

        int fps = FixordiumPerformanceController.getLastSampledFps();
        int target = FixordiumClient.getSmartTargetFps();
        String state = FixordiumPerformanceController.isSmartBoostActive() ? "ACTIVE" : "MONITORING";
        return "Smart: " + state + " (" + fps + "/" + target + " FPS)";
    }
}
