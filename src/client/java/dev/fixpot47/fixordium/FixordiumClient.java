package dev.fixpot47.fixordium;

import net.fabricmc.api.ClientModInitializer;

public final class FixordiumClient implements ClientModInitializer {
    public static final String MOD_ID = "fixordium";
    private static FixordiumConfig config;

    @Override
    public void onInitializeClient() {
        config = FixordiumConfig.load();
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

    public static void saveConfig() {
        config().save();
    }
}
