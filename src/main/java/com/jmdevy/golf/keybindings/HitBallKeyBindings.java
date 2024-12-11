package com.jmdevy.golf.keybindings;

import com.jmdevy.golf.Golf;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Golf.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class HitBallKeyBindings {
    public static final KeyMapping forwardKey = new KeyMapping(
            "key.yourmodid.forward",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_W),
            "key.categories.movement");
    public static final KeyMapping backKey = new KeyMapping(
            "key.yourmodid.back",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_S),
            "key.categories.movement");
    public static final KeyMapping leftKey = new KeyMapping(
            "key.yourmodid.left",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_A),
            "key.categories.movement");
    public static final KeyMapping rightKey = new KeyMapping(
            "key.yourmodid.right",
            KeyConflictContext.IN_GAME,
            InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_D),
            "key.categories.movement");
}

