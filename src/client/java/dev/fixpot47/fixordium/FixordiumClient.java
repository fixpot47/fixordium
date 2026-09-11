package dev.fixpot47.fixordium;

import net.fabricmc.api.ClientModInitializer;

public final class FixordiumClient implements ClientModInitializer {
    public static final String MOD_ID = "fixordium";
    private static FixordiumConfig config;

    @Override
    public void onInitializeClient() {
        config = FixordiumConfig.load();
        FixordiumDebugHud.register();
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

    public static boolean areContainerAnimationsEnabled() {
        return config().containerAnimations;
    }

    public static void setContainerAnimationsEnabled(boolean enabled) {
        config().containerAnimations = enabled;
    }

    public static boolean areBlockEntityAnimationsEnabled() {
        return config().blockEntityAnimations;
    }

    public static void setBlockEntityAnimationsEnabled(boolean enabled) {
        config().blockEntityAnimations = enabled;
    }

    public static void saveConfig() {
        config().save();
    }
}
