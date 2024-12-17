package com.jmdevy.golf.client.keybindings;

import com.jmdevy.golf.Golf;
import com.jmdevy.golf.client.ClientStanceHandler;
import com.jmdevy.golf.client.ClientSwingHandler;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.entity.player.Player;
import java.util.HashMap;


@Mod.EventBusSubscriber(modid = Golf.MODID, value = Dist.CLIENT)
public class HitBallKeyBindingsHandler {

    private static HashMap<String, Boolean> lastKeyStateMap = new HashMap<String, Boolean>();


    private static boolean isJustPressed(KeyMapping key){
        String keyName = key.getName();

        // Say that the last state was not pressed if not already in the map
        if(!lastKeyStateMap.containsKey(keyName)){
            lastKeyStateMap.put(keyName, Boolean.valueOf(false));
        }

        boolean lastKeyState = lastKeyStateMap.get(keyName).booleanValue();
        boolean currKeyState = key.isDown();
        boolean justPressed = false;    // Assume not just pressed

        // Is the key just pressed?
        if(lastKeyState == false && currKeyState == true){
            justPressed = true;
        }

        // Track the state
        lastKeyStateMap.put(keyName, currKeyState);

        return justPressed;
    }


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
        if (isJustPressed(HitBallKeyBindings.rightMouseKey)) {
            Golf.LOGGER.info("right mouse");
            ClientSwingHandler.swing(ClientSwingHandler.BUTTON_TYPE.SECONDARY);
        }
        if (isJustPressed(HitBallKeyBindings.leftMouseKey)) {
            Golf.LOGGER.info("left mouse");
            ClientSwingHandler.swing(ClientSwingHandler.BUTTON_TYPE.MAIN);
        }
    }
}
