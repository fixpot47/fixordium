package dev.fixpot47.fixordium;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;

public final class FixordiumPerformanceController {
    private static final int SAMPLE_TICKS = 20;
    private static final int ENABLE_HYSTERESIS = 3;
    private static final int DISABLE_HYSTERESIS = 10;

    private static int ticksSinceSample;
    private static boolean smartBoostActive;
    private static boolean pauseScreenOpen;
    private static int lastSampledFps;

    private FixordiumPerformanceController() {
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(FixordiumPerformanceController::tick);
    }

    private static void tick(Minecraft client) {
        Screen currentScreen = client.gui.screen();
        handlePauseMusic(client, currentScreen);
        updateSmartMode(client, currentScreen);
    }

    private static void handlePauseMusic(Minecraft client, Screen currentScreen) {
        boolean nowPaused = currentScreen instanceof PauseScreen;
        if (nowPaused
                && !pauseScreenOpen
                && FixordiumClient.isEnabled()
                && FixordiumClient.isStopMusicOnPauseEnabled()) {
            client.getMusicManager().stopPlaying();
        }
        pauseScreenOpen = nowPaused;
    }

    private static void updateSmartMode(Minecraft client, Screen currentScreen) {
        if (!FixordiumClient.isEnabled() || !FixordiumClient.isSmartModeEnabled() || client.level == null) {
            smartBoostActive = false;
            ticksSinceSample = 0;
            lastSampledFps = Math.max(0, client.getFps());
            return;
        }

        // Menu FPS is not representative of gameplay, so only sample while actually playing.
        if (currentScreen != null) {
            return;
        }

        if (++ticksSinceSample < SAMPLE_TICKS) {
            return;
        }
        ticksSinceSample = 0;

        int fps = client.getFps();
        if (fps <= 0) {
            return;
        }

        lastSampledFps = fps;
        int target = FixordiumClient.getSmartTargetFps();

        if (!smartBoostActive && fps < Math.max(1, target - ENABLE_HYSTERESIS)) {
            smartBoostActive = true;
        } else if (smartBoostActive && fps >= target + DISABLE_HYSTERESIS) {
            smartBoostActive = false;
        }
    }

    public static boolean isSmartBoostActive() {
        return FixordiumClient.isEnabled() && FixordiumClient.isSmartModeEnabled() && smartBoostActive;
    }

    public static int getLastSampledFps() {
        return lastSampledFps;
    }
}
