package dev.fixpot47.fixordium;

import net.fabricmc.api.ClientModInitializer;

public final class FixordiumClient implements ClientModInitializer {
    public static final String MOD_ID = "fixordium";
    private static FixordiumConfig config;

    @Override
    public void onInitializeClient() {
        config = FixordiumConfig.load();
        FixordiumDebugHud.register();
        FixordiumPerformanceController.register();
    }

    private static FixordiumConfig config() {
        if (config == null) config = FixordiumConfig.load();
        return config;
    }

    public static boolean isEnabled() {
        return config().enabled;
    }

    public static void setEnabled(boolean enabled) {
        config().enabled = enabled;
    }

    public static boolean isDebugCullingCounterEnabled() {
        return config().debugCullingCounter;
    }

    public static void setDebugCullingCounterEnabled(boolean enabled) {
        config().debugCullingCounter = enabled;
    }

    public static boolean isDecorationCullingEnabled() {
        return config().decorationCulling;
    }

    public static void setDecorationCullingEnabled(boolean enabled) {
        config().decorationCulling = enabled;
    }

    public static boolean areEntityShadowsEnabled() {
        return config().entityShadows;
    }

    public static void setEntityShadowsEnabled(boolean enabled) {
        config().entityShadows = enabled;
    }

    public static boolean shouldRenderEntityShadows() {
        return config().entityShadows && !FixordiumPerformanceController.isSmartBoostActive();
    }

    public static boolean isSmartModeEnabled() {
        return config().smartMode;
    }

    public static void setSmartModeEnabled(boolean enabled) {
        config().smartMode = enabled;
    }

    public static int getSmartTargetFps() {
        return config().smartTargetFps;
    }

    public static void setSmartTargetFps(int fps) {
        config().smartTargetFps = Math.max(30, Math.min(240, fps));
    }

    public static boolean isStopMusicOnPauseEnabled() {
        return config().stopMusicOnPause;
    }

    public static void setStopMusicOnPauseEnabled(boolean enabled) {
        config().stopMusicOnPause = enabled;
    }

    public static boolean areContainerAnimationsEnabled() {
        return config().containerAnimations;
    }

    public static void setContainerAnimationsEnabled(boolean enabled) {
        config().containerAnimations = enabled;
    }

    public static boolean shouldAnimateContainers() {
        return config().containerAnimations && !FixordiumPerformanceController.isSmartBoostActive();
    }

    public static boolean areBlockEntityAnimationsEnabled() {
        return config().blockEntityAnimations;
    }

    public static void setBlockEntityAnimationsEnabled(boolean enabled) {
        config().blockEntityAnimations = enabled;
    }

    public static boolean shouldAnimateBlockEntities() {
        return config().blockEntityAnimations && !FixordiumPerformanceController.isSmartBoostActive();
    }

    public static void saveConfig() {
        config().save();
    }
}
