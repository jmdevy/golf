package com.jmdevy.golf.client.hud;

import com.jmdevy.golf.Golf;
import com.jmdevy.golf.client.ClientStanceHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;


@Mod.EventBusSubscriber(modid = Golf.MODID, value = Dist.CLIENT)
public class ClientGolfHUDHandler {

    private static int barWidthPx = 206;
    private static int barHeightPx = 8;


    private static void renderBar(GuiGraphics graphics, int screenWidth, int screenHeight){
        // General information about the bar
        int screenCenterX = screenWidth / 2;
        int barHalfWidthPx = barWidthPx / 2;

        // #1: Bar background
        // Get top left and bottom right corner positions
        // for the background and then render it
        int topLeftX = screenCenterX-barHalfWidthPx;
        int topLeftY = screenHeight - 34;

        int bottomRightX = screenCenterX+barHalfWidthPx;
        int bottomRightY = topLeftY + barHeightPx;

        graphics.fill(topLeftX, topLeftY, bottomRightX, bottomRightY, 0xffffffff);

        // #2: Bar power
        // Bottom right XY and top left Y are always the same
        // for the inner power bar (just moved in a little),
        // but the X needs to be re-calculated based on
        // a percentage
        bottomRightX -= 1;
        bottomRightY -= 1;
        topLeftY += 1;
        topLeftX += 1;
        topLeftX = bottomRightX - (int)((float)(bottomRightX - topLeftX) * 0.5f);
        graphics.fill(topLeftX, topLeftY, bottomRightX, bottomRightY, 0xffff00ff);
    }


    public static void updateSwingCursor(float swingCursor){
        Golf.LOGGER.info(Float.toString(swingCursor));
    }


    public static void updateSwingPower(float swingPower){

    }


    // Disable player cursor in center of screen
    @SubscribeEvent
    public static void onRenderGuiOverlayEvent(RenderGuiOverlayEvent.Pre event) {
        if (!ClientStanceHandler.isBallFocused()) {
            return; // Skip custom control logic if not focused on a ball
        }

        // Check if it's the overlay rendering stage and if
        // the overlay is the crosshair, disable the crosshair
        if (event.getOverlay() != null && event.getOverlay().id().toString().equals("minecraft:crosshair")) {
            event.setCanceled(true);
        }
    }


    // Render player golf power bar and cursor
    @SubscribeEvent
    public static void onRenderGuiOverlayEvent(RenderGuiOverlayEvent.Post event) {
        if (!ClientStanceHandler.isBallFocused()) {
            return; // Skip custom control logic if not focused on a ball
        }

        Minecraft minecraft = Minecraft.getInstance();
        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        GuiGraphics graphics = event.getGuiGraphics();
        renderBar(graphics, screenWidth, screenHeight);
    }
}