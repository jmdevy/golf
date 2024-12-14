package com.jmdevy.golf.common.mixin;

import com.jmdevy.golf.Golf;
import com.jmdevy.golf.client.ClientStanceHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


// https://github.com/henkelmax/camera-utils/blob/outdated/1.20/src/main/java/de/maxhenkel/camerautils/mixin/GameRendererMixin.java
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    

    static{
        Golf.LOGGER.info("Golf: GameRenderer class mixin");
    }

    @Inject(at = @At("HEAD"), method = "bobView", cancellable = true)
    private void bobView(PoseStack poseStack, float f, CallbackInfo info) {
        if(ClientStanceHandler.isBallFocused()){
            // Disable view bob by injecting at start of function can canceling
            info.cancel();
        }
    }
}
