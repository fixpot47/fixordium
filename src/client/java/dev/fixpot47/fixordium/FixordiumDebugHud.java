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
        String secondLine = "Culled: " + FixordiumRuntime.getCullsPerSecond() + "/s";
        String thirdLine = "Total: " + FixordiumRuntime.getTotalCulls();

        int margin = 5;
        int width = Math.max(client.font.width(firstLine), Math.max(client.font.width(secondLine), client.font.width(thirdLine)));
        int x = graphics.guiWidth() - width - margin;
        int y = margin;

        graphics.text(client.font, firstLine, x, y, 0xFFFFFFFF, true);
        graphics.text(client.font, secondLine, x, y + 10, 0xFFFFFFFF, true);
        graphics.text(client.font, thirdLine, x, y + 20, 0xFFAAAAAA, true);
    }
}
