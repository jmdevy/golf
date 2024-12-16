package com.jmdevy.golf.client.keybindings;

import com.jmdevy.golf.Golf;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.common.Mod;
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
    
    public static final KeyMapping rightMouseKey = new KeyMapping(
        "key.golf.rightMouse",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.MOUSE.getOrCreate(GLFW.GLFW_MOUSE_BUTTON_RIGHT),
        "key.categories.movement");
    
    public static final KeyMapping leftMouseKey = new KeyMapping(
        "key.golf.leftMouse",
        KeyConflictContext.IN_GAME,
        InputConstants.Type.MOUSE.getOrCreate(GLFW.GLFW_MOUSE_BUTTON_LEFT),
        "key.categories.movement");
}

