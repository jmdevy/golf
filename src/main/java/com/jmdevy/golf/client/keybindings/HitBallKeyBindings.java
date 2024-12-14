package com.jmdevy.golf.client.keybindings;

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
        "key.golf.forward",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_W),
        "key.categories.movement");

    public static final KeyMapping backKey = new KeyMapping(
        "key.golf.back",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_S),
        "key.categories.movement");

    public static final KeyMapping leftKey = new KeyMapping(
        "key.golf.left",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_A),
        "key.categories.movement");

    public static final KeyMapping rightKey = new KeyMapping(
        "key.golf.right",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_D),
        "key.categories.movement");

    public static final KeyMapping spaceKey = new KeyMapping(
        "key.golf.space",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.KEYSYM.getOrCreate(GLFW.GLFW_KEY_SPACE),
        "key.categories.movement");
}

