package dev.fixpot47.fixordium;

import net.fabricmc.api.ClientModInitializer;

public final class FixordiumClient implements ClientModInitializer {
    public static final String MOD_ID = "fixordium";

    private static FixordiumConfig config;

    @Override
    public void onInitializeClient() {
        config = FixordiumConfig.load();
        FixordiumRuntime.initialize();
    }

    public static boolean isEnabled() {
        if (config == null) {
            config = FixordiumConfig.load();
        }
        return config.enabled;
    }

    public static void toggle() {
        if (config == null) {
            config = FixordiumConfig.load();
        }
        config.enabled = !config.enabled;
        config.save();
    }
}
