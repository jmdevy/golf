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

    private static HashMap<String, Long> keyDebounceMap = new HashMap<String, Long>();
    private static long debounceKeyTimeMS = 200;


    private static boolean isKeyPressedDebounced(KeyMapping key){
        String keyName = key.getName();

        // If the key is not pressed, return false
        if(!key.isDown()){
            return false;
        }

        // If the map doesn't contain the key, add it as zero
        // so that when time difference is compared it is long
        // enough the first time
        if(!keyDebounceMap.containsKey(keyName)){
            keyDebounceMap.put(keyName, Long.valueOf(0));
        }

        // Figure out how long it has been since the last time the key
        // was pressed. Return true if it has been long enough
        long keyLastPressedTimeMS = keyDebounceMap.get(keyName).longValue();
        long currentTimeMS = System.currentTimeMillis();

        if(currentTimeMS - keyLastPressedTimeMS > debounceKeyTimeMS){
            keyDebounceMap.put(keyName, Long.valueOf(currentTimeMS));
            return true;
        }else{
            return false;
        }
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
        if (isKeyPressedDebounced(HitBallKeyBindings.rightMouseKey)) {
            Golf.LOGGER.info("right mouse");
            ClientSwingHandler.swing(ClientSwingHandler.BUTTON_TYPE.SECONDARY);
        }
        if (isKeyPressedDebounced(HitBallKeyBindings.leftMouseKey)) {
            Golf.LOGGER.info("left mouse");
            ClientSwingHandler.swing(ClientSwingHandler.BUTTON_TYPE.MAIN);
        }
    }
}
