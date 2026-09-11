package dev.fixpot47.fixordium;

import net.caffeinemc.mods.sodium.api.config.ConfigEntryPoint;
import net.caffeinemc.mods.sodium.api.config.StorageEventHandler;
import net.caffeinemc.mods.sodium.api.config.option.OptionImpact;
import net.caffeinemc.mods.sodium.api.config.structure.ConfigBuilder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public final class FixordiumSodiumConfig implements ConfigEntryPoint {
    private static final Identifier ENABLED = Identifier.parse("fixordium:enabled");
    private static final Identifier DEBUG_COUNTER = Identifier.parse("fixordium:debug_culling_counter");
    private static final Identifier CONTAINER_ANIMATIONS = Identifier.parse("fixordium:container_animations");
    private static final Identifier BLOCK_ENTITY_ANIMATIONS = Identifier.parse("fixordium:block_entity_animations");
    private static final Identifier ICON = Identifier.parse("fixordium:icon.png");

    private final StorageEventHandler storageHandler = FixordiumClient::saveConfig;

    @Override
    public void registerConfigLate(ConfigBuilder builder) {
        builder.registerOwnModOptions()
                .setNonTintedIcon(ICON)
                .addPage(builder.createOptionPage()
                        .setName(Component.translatable("fixordium.page.performance"))
                        .addOptionGroup(builder.createOptionGroup()
                                .setName(Component.translatable("fixordium.group.entity_culling"))
                                .addOption(builder.createBooleanOption(ENABLED)
                                        .setName(Component.translatable("fixordium.option.enabled"))
                                        .setTooltip(Component.translatable("fixordium.option.enabled.tooltip"))
                                        .setImpact(OptionImpact.HIGH)
                                        .setStorageHandler(this.storageHandler)
                                        .setBinding(FixordiumClient::setEnabled, FixordiumClient::isEnabled)
                                        .setDefaultValue(true)
                                )
                                .addOption(builder.createBooleanOption(DEBUG_COUNTER)
                                        .setName(Component.translatable("fixordium.option.debug_counter"))
                                        .setTooltip(Component.translatable("fixordium.option.debug_counter.tooltip"))
                                        .setImpact(OptionImpact.LOW)
                                        .setStorageHandler(this.storageHandler)
                                        .setBinding(FixordiumClient::setDebugCullingCounterEnabled, FixordiumClient::isDebugCullingCounterEnabled)
                                        .setDefaultValue(false)
                                )
                        )
                )
                .addPage(builder.createOptionPage()
                        .setName(Component.translatable("fixordium.page.animations"))
                        .addOptionGroup(builder.createOptionGroup()
                                .setName(Component.translatable("fixordium.group.animations"))
                                .addOption(builder.createBooleanOption(CONTAINER_ANIMATIONS)
                                        .setName(Component.translatable("fixordium.option.container_animations"))
                                        .setTooltip(Component.translatable("fixordium.option.container_animations.tooltip"))
                                        .setImpact(OptionImpact.LOW)
                                        .setStorageHandler(this.storageHandler)
                                        .setBinding(FixordiumClient::setContainerAnimationsEnabled, FixordiumClient::areContainerAnimationsEnabled)
                                        .setDefaultValue(true)
                                )
                                .addOption(builder.createBooleanOption(BLOCK_ENTITY_ANIMATIONS)
                                        .setName(Component.translatable("fixordium.option.block_entity_animations"))
                                        .setTooltip(Component.translatable("fixordium.option.block_entity_animations.tooltip"))
                                        .setImpact(OptionImpact.MEDIUM)
                                        .setStorageHandler(this.storageHandler)
                                        .setBinding(FixordiumClient::setBlockEntityAnimationsEnabled, FixordiumClient::areBlockEntityAnimationsEnabled)
                                        .setDefaultValue(true)
                                )
                        )
                );
    }
}
