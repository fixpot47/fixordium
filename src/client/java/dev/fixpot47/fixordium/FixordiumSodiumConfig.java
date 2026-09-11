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
    private static final Identifier DECORATION_CULLING = Identifier.parse("fixordium:decoration_culling");
    private static final Identifier ENTITY_SHADOWS = Identifier.parse("fixordium:entity_shadows");
    private static final Identifier SMART_MODE = Identifier.parse("fixordium:smart_mode");
    private static final Identifier SMART_TARGET_FPS = Identifier.parse("fixordium:smart_target_fps");
    private static final Identifier STOP_MUSIC_ON_PAUSE = Identifier.parse("fixordium:stop_music_on_pause");
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
                                .addOption(builder.createBooleanOption(DECORATION_CULLING)
                                        .setName(Component.translatable("fixordium.option.decoration_culling"))
                                        .setTooltip(Component.translatable("fixordium.option.decoration_culling.tooltip"))
                                        .setImpact(OptionImpact.MEDIUM)
                                        .setStorageHandler(this.storageHandler)
                                        .setBinding(FixordiumClient::setDecorationCullingEnabled, FixordiumClient::isDecorationCullingEnabled)
                                        .setDefaultValue(true)
                                )
                                .addOption(builder.createBooleanOption(ENTITY_SHADOWS)
                                        .setName(Component.translatable("fixordium.option.entity_shadows"))
                                        .setTooltip(Component.translatable("fixordium.option.entity_shadows.tooltip"))
                                        .setImpact(OptionImpact.MEDIUM)
                                        .setStorageHandler(this.storageHandler)
                                        .setBinding(FixordiumClient::setEntityShadowsEnabled, FixordiumClient::areEntityShadowsEnabled)
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
                        .addOptionGroup(builder.createOptionGroup()
                                .setName(Component.translatable("fixordium.group.smart_mode"))
                                .addOption(builder.createBooleanOption(SMART_MODE)
                                        .setName(Component.translatable("fixordium.option.smart_mode"))
                                        .setTooltip(Component.translatable("fixordium.option.smart_mode.tooltip"))
                                        .setImpact(OptionImpact.HIGH)
                                        .setStorageHandler(this.storageHandler)
                                        .setBinding(FixordiumClient::setSmartModeEnabled, FixordiumClient::isSmartModeEnabled)
                                        .setDefaultValue(false)
                                )
                                .addOption(builder.createIntegerOption(SMART_TARGET_FPS)
                                        .setName(Component.translatable("fixordium.option.smart_target_fps"))
                                        .setTooltip(Component.translatable("fixordium.option.smart_target_fps.tooltip"))
                                        .setImpact(OptionImpact.MEDIUM)
                                        .setStorageHandler(this.storageHandler)
                                        .setBinding(FixordiumClient::setSmartTargetFps, FixordiumClient::getSmartTargetFps)
                                        .setDefaultValue(60)
                                        .setRange(30, 240, 10)
                                        .setValueFormatter(value -> Component.literal(value + " FPS"))
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
                )
                .addPage(builder.createOptionPage()
                        .setName(Component.translatable("fixordium.page.extras"))
                        .addOptionGroup(builder.createOptionGroup()
                                .setName(Component.translatable("fixordium.group.pause_optimizations"))
                                .addOption(builder.createBooleanOption(STOP_MUSIC_ON_PAUSE)
                                        .setName(Component.translatable("fixordium.option.stop_music_on_pause"))
                                        .setTooltip(Component.translatable("fixordium.option.stop_music_on_pause.tooltip"))
                                        .setImpact(OptionImpact.LOW)
                                        .setStorageHandler(this.storageHandler)
                                        .setBinding(FixordiumClient::setStopMusicOnPauseEnabled, FixordiumClient::isStopMusicOnPauseEnabled)
                                        .setDefaultValue(false)
                                )
                        )
                );
    }
}
