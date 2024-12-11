package com.jmdevy.golf.keybindings;

import com.jmdevy.golf.Golf;
import com.jmdevy.golf.keybindings.HitBallKeyBindings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;


@Mod.EventBusSubscriber(modid = Golf.MODID, value = Dist.CLIENT)
public class HitBallKeyBindingsHandler {
    private static boolean isCustomControlEnabled = true;

    public static void toggleCustomControl() {
        isCustomControlEnabled = !isCustomControlEnabled;
    }
    

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        if (!isCustomControlEnabled) {
            return; // Skip custom control logic if it's disabled
        }

        // LocalPlayer player = Minecraft.getInstance().player;
        Player player = event.player;
        if (player == null) return;

        player.setYRot(0);
        player.setXRot(0);

        // Override WASD controls
        if (HitBallKeyBindings.forwardKey.isDown()) {
            Golf.LOGGER.info("forward");
            player.setDeltaMovement(player.getDeltaMovement().add(0, 0, 0.05));
        }
        if (HitBallKeyBindings.backKey.isDown()) {
            Golf.LOGGER.info("backwards");
            player.setDeltaMovement(player.getDeltaMovement().add(0, 0, -0.05));
        }
        if (HitBallKeyBindings.leftKey.isDown()) {
            Golf.LOGGER.info("left");
            player.setDeltaMovement(player.getDeltaMovement().add(-0.05, 0, 0));
        }
        if (HitBallKeyBindings.rightKey.isDown()) {
            Golf.LOGGER.info("right");
            player.setDeltaMovement(player.getDeltaMovement().add(0.05, 0, 0));
        }
    }
}
