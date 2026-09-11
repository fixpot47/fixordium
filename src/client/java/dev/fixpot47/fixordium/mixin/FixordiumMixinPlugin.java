package dev.fixpot47.fixordium.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public final class FixordiumMixinPlugin implements IMixinConfigPlugin {
    private boolean legacyMinecraft;

    @Override
    public void onLoad(String mixinPackage) {
        String version = FabricLoader.getInstance()
                .getModContainer("minecraft")
                .map(ModContainer::getMetadata)
                .map(metadata -> metadata.getVersion().getFriendlyString())
                .orElse("");

        legacyMinecraft = version.startsWith("1.21.");
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        if (legacyMinecraft) {
            return List.of(
                    "LegacyEntityRenderDispatcherMixin",
                    "LegacyVideoSettingsScreenMixin"
            );
        }

        return List.of(
                "ModernEntityRenderDispatcherMixin",
                "ModernVideoSettingsScreenMixin"
        );
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
