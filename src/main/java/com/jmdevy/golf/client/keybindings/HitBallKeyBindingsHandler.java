package com.jmdevy.golf.client.keybindings;

import com.jmdevy.golf.Golf;
import com.jmdevy.golf.client.ClientStanceHandler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.world.entity.player.Player;


@Mod.EventBusSubscriber(modid = Golf.MODID, value = Dist.CLIENT)
public class HitBallKeyBindingsHandler {


    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        if (!ClientStanceHandler.isBallFocused()) {
            return; // Skip custom control logic if ball not focused
        }

        Player player = event.player;
        if (player == null) return;

        // Override WASD controls
        if (HitBallKeyBindings.forwardKey.isDown()) {
            Golf.LOGGER.info("forward");
            // player.setDeltaMovement(player.getDeltaMovement().add(0, 0, 0.05));
        }
        if (HitBallKeyBindings.backKey.isDown()) {
            Golf.LOGGER.info("backwards");
            // player.setDeltaMovement(player.getDeltaMovement().add(0, 0, -0.05));
        }
        if (HitBallKeyBindings.leftKey.isDown()) {
            Golf.LOGGER.info("left");
            ClientStanceHandler.adjustAimYawDeg(-0.35f);
            // player.setYRot(player.getYRot() + 0.01f);
            // player.setDeltaMovement(player.getDeltaMovement().add(-0.05, 0, 0));
        }
        if (HitBallKeyBindings.rightKey.isDown()) {
            Golf.LOGGER.info("right");
            ClientStanceHandler.adjustAimYawDeg(0.35f);
            // player.setYRot(player.getYRot() - 0.01f);
            // player.setDeltaMovement(player.getDeltaMovement().add(0.05, 0, 0));
        }
        if (HitBallKeyBindings.spaceKey.isDown()) {
            Golf.LOGGER.info("space");
            ClientStanceHandler.setBallFocus(false, null, null);
        }
    }
}
