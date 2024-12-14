package com.jmdevy.golf.common.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// https://github.com/henkelmax/camera-utils/blob/outdated/1.20/src/main/java/de/maxhenkel/camerautils/mixin/PlayerRendererMixin.java
@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin {

    // @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    // private void getCameraEntity(AbstractClientPlayer abstractClientPlayer, float f, float g, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, CallbackInfo info) {
    //     if (abstractClientPlayer == Minecraft.getInstance().player) {
    //         // Do not show player
    //         info.cancel();
    //     }
    // }

}
