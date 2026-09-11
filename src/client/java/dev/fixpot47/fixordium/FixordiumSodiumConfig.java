package dev.fixpot47.fixordium;

import net.caffeinemc.mods.sodium.api.config.ConfigEntryPoint;
import net.caffeinemc.mods.sodium.api.config.StorageEventHandler;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public final class FixordiumSodiumConfig implements ConfigEntryPoint {
    private static final Identifier ENABLED = Identifier.parse("fixordium:enabled");
    private static final Identifier ICON = Identifier.parse("fixordium:icon.png");
    private final StorageEventHandler storageHandler = FixordiumClient::saveConfig;

    @Override
    public void registerConfigLate(ConfigBuilder builder) {
        builder.registerOwnModOptions()
                .setNonTintedIcon(ICON)
                .addPage(builder.createOptionPage()
                        .setName(Component.translatable("fixordium.page.general"))
                        .addOptionGroup(builder.createOptionGroup()
                                .setName(Component.translatable("fixordium.group.rendering"))
                                .addOption(builder.createBooleanOption(ENABLED)
                                        .setName(Component.translatable("fixordium.option.enabled"))
                                        .setTooltip(Component.translatable("fixordium.option.enabled.tooltip"))
                                        .setStorageHandler(this.storageHandler)
                                        .setBinding(FixordiumClient::setEnabled, FixordiumClient::isEnabled)
                                        .setDefaultValue(true)
                                )
                        )
                );
    }
}
