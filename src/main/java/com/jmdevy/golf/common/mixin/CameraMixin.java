package com.jmdevy.golf.common.mixin;

import com.jmdevy.golf.Golf;
import com.jmdevy.golf.client.ClientStanceHandler;

import net.minecraft.client.Camera;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


// https://github.com/henkelmax/camera-utils/blob/outdated/1.20/src/main/java/de/maxhenkel/camerautils/mixin/CameraMixin.java
// https://github.com/CreativeMD/CMDCam/blob/1.20/src/main/java/team/creative/cmdcam/client/mixin/CameraMixin.java
@Mixin(Camera.class)
public abstract class CameraMixin {
    static{
        Golf.LOGGER.info("Golf: Camera class mixin");
    }

    @Inject(at = @At("HEAD"), method = "Lnet/minecraft/client/Camera;isDetached()Z", cancellable = true)
    public void isDetached(CallbackInfoReturnable<Boolean> info) {
        // Only detach camera for custom camera manipulation if a ball is focused
        if(ClientStanceHandler.isBallFocused()){
            info.setReturnValue(true);
        }
    }

    @Redirect(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;move(DDD)V", ordinal = 0))
    private void move(Camera camera, double x, double y, double z) {
        
    }

    @Inject(method = "setup", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Camera;setPosition(DDD)V", shift = At.Shift.AFTER))
    private void setPosition(CallbackInfo ci) {
        // Only manipulate the camera if a a ball is focused
        if(ClientStanceHandler.isBallFocused()){
            float cameraPitch = ClientStanceHandler.calculateCameraPitch();
            float cameraYaw = ClientStanceHandler.calculateCameraYaw();
            Vec3 cameraPos = ClientStanceHandler.calculateCameraPos();

            setRotation(cameraYaw, cameraPitch);
            setPosition(cameraPos.x, cameraPos.y, cameraPos.z);
        }
    }

    @Shadow
    protected abstract void setRotation(float f, float g);

    @Shadow
    protected abstract void setPosition(double d, double e, double f);
}
