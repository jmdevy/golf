package com.jmdevy.golf.client;

import com.jmdevy.golf.Golf;
import com.jmdevy.golf.common.entities.GolfBallEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.gui.overlay.GuiOverlayManager;


@Mod.EventBusSubscriber(modid = Golf.MODID, value = Dist.CLIENT)
public class ClientStanceHandler {
    private static boolean isClientBallFocused = false; // By default, the client is not focusing on the ball
    private static GolfBallEntity focusedBall = null;   // No focused ball entity by default
    private static float aimYawDeg = 0.0f;              // The yaw at which the player is aiming, used for a lot of stuff
    private static double stanceBallDistance = 1.5;     // The distance the player model should be from the ball (TODO: should depend on club length)
    private static double cameraBallDistance = 2.5; 

    
    // Call this to make the player focus a golf ball entity
    public static void setBallFocus(boolean focused, GolfBallEntity newFocusedBall, Player player){
        Golf.LOGGER.info("Focusing on ball...");

        // Track focus state
        isClientBallFocused = focused;

        // Track information at time of focus
        focusedBall = newFocusedBall;   // The new golf ball entity to focus
        aimYawDeg = 0.0f;                  // The yaw to start aiming at (TODO: Figure out a way of auto aiming towards flag)

        // Disable/enable rendering the player's hand 
        // or selection when the ball is focused
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.gameRenderer.setRenderHand(!focused);
        minecraft.gameRenderer.setRenderBlockOutline(!focused);
    }


    public static boolean isBallFocused(){
        return isClientBallFocused;
    }


    // Adjust the internal aim using a positive or negative offset
    public static void adjustAimYawDeg(float aimYawOffset){
        aimYawDeg += aimYawOffset;
    }


    // Needs to be called every tick to maintain the player
    // model's stance and camera position/rotation
    private static void stanceTick(Player player){
        if (!isClientBallFocused) {
            return; // Skip custom control logic if not focused on a ball
        }

        // Cancel any player movement vector offset
        player.setDeltaMovement(Vec3.ZERO);

        // Adjust the player model stance (note: this would adjust the
        // camera too, but a mixing overrides that logic)
        player.setXRot(25.0f);
        player.setYRot(aimYawDeg + 90.0f);
        player.setYHeadRot(aimYawDeg + 90.0f);
        player.setYBodyRot(aimYawDeg + 90.0f);

        // Convert aim yaw to radians
        float aimYawRad = aimYawDeg * (float)Math.PI / 180.0f;

        double playerX = focusedBall.getX()+stanceBallDistance*Math.cos(aimYawRad);
        double playerY = focusedBall.getY();
        double playerZ = focusedBall.getZ()+stanceBallDistance*Math.sin(aimYawRad);

        player.setPos(new Vec3(playerX, playerY, playerZ));
    }


    // Calculate the camera yaw from player stance information
    public static float calculateCameraYaw(){
        return aimYawDeg;
    }


    // Calculate the camera pitch from player stance information
    public static float calculateCameraPitch(){
        return 15.0f;
    }


    // Caltculate the camera position from player stance information
    public static Vec3 calculateCameraPos(){
        float cameraYaw = calculateCameraYaw() - 90;
        float cameraYawRad = cameraYaw * (float)Math.PI / 180.0f;

        double cameraX = focusedBall.getX()+cameraBallDistance*Math.cos(cameraYawRad);
        double cameraY = focusedBall.getY() + 2;
        double cameraZ = focusedBall.getZ()+cameraBallDistance*Math.sin(cameraYawRad);

        Vec3 cameraPosition = new Vec3(cameraX, cameraY, cameraZ);

        return cameraPosition;
    }


    // Disable player being able to break blocks
    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.LeftClickBlock event) {
        if (!isClientBallFocused) {
            return; // Skip custom control logic if not focused on a ball
        }

        event.setCanceled(true);
    }

    
    // Disable player cursor in center of screen
    @SubscribeEvent
    public static void onRenderLevelStage(RenderGuiOverlayEvent.Pre event) {
        if (!isClientBallFocused) {
            return; // Skip custom control logic if not focused on a ball
        }

        // Check if it's the overlay rendering stage and if
        // the overlay is the crosshair, disable the crosshair
        if (event.getOverlay() != null && event.getOverlay().id().toString().equals("minecraft:crosshair")) {
            event.setCanceled(true);
        }
    }


    // Mostly used to get the player and call stance stick on player tick event
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        if (!isClientBallFocused) {
            return; // Skip custom control logic if not focused on a ball
        }

        Player player = event.player;
        if (player == null) {
            return;
        }

        stanceTick(player);
    }
}
