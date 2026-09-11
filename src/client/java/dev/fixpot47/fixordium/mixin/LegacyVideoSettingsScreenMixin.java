package dev.fixpot47.fixordium.mixin;

import dev.fixpot47.fixordium.FixordiumUi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.class_446", remap = false)
public abstract class LegacyVideoSettingsScreenMixin {
    @Inject(method = "method_60325", at = @At("TAIL"), remap = false, require = 0)
    private void fixordium$addToggle(CallbackInfo ci) {
        FixordiumUi.addToggle(this);
    }
}
