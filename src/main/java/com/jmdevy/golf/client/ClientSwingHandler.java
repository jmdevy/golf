package com.jmdevy.golf.client;

import com.jmdevy.golf.Golf;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = Golf.MODID, value = Dist.CLIENT)
public class ClientSwingHandler {
    private enum SWING_STATE {NONE, DEFINING_POWER, DEFINING_ACCURACY}
    private enum SPIN_TYPE {NONE, FORWARDs, BACKWARDS}
    public enum BUTTON_TYPE {MAIN, SECONDARY}                   // By default, left-click is main and right-click is the secondary swing modifier for spin

    private static SWING_STATE swingState = SWING_STATE.NONE;
    private static float swingCursor = 0.0f;                    // The cursor that moves in a standard way that allows the player to define a swing
    private static float swingPower = 0.0f;                     // The power of the swing defined by player inputs and timing (0.0 to 1.0)
    private static float swingAccuracy = 0.0f;                  // The accuracy of the swing defined by player inputs and timing (-1.0 to 1.0)
    private static SPIN_TYPE swingSpinType = SPIN_TYPE.NONE;    // The type of spin defined by the order of the last button clicks

    private static float swingCursorStep = 0.01f;               // How much to move the swing cursor each tick


    // Reset all parameters of the swing
    private static void swingReset(){
        swingState = SWING_STATE.NONE;
        swingCursor = 0.0f;
        swingPower = 0.0f;
        swingAccuracy = 0.0f;
        swingSpinType = SPIN_TYPE.NONE;
        swingCursorStep = 0.01f;
    }


    public static float getSwingCursor(){
        return swingCursor;
    }


    public static float getSwingPower(){
        return swingPower;
    }


    // This needs to be called three times
    // #1: First left click event (can only be a left-click)
    // #2: Left or right click that defines the power of the shot
    // #3: Left or right click that defines accuracy and forward or back spin
    public static void swing(BUTTON_TYPE buttonType){
        if(swingState == SWING_STATE.NONE){                     // #1: Move from non-started swing to defining power of swing
            swingState = SWING_STATE.DEFINING_POWER;
        }else if(swingState == SWING_STATE.DEFINING_POWER){     // #2: Move from defining power of swing to defining accuracy
            swingState = SWING_STATE.DEFINING_ACCURACY;
        }else if(swingState == SWING_STATE.DEFINING_ACCURACY){  // #2: Move from defining defining accuracy to stopping the swing
            swingReset();
        }
    }


    // Mostly used to get the player and call stance stick on player tick event
    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        // Don't do anything if swing not started
        if (swingState == SWING_STATE.NONE) {
            return;
        }

        // Move the cursor while to swing is happening
        swingCursor += swingCursorStep;

        // Clamp cursor and if reached the end, flip direction of travel
        if(swingCursor >= 1.0f){
            swingCursor = 1.0f;
            swingCursorStep = -swingCursorStep;
        }

        // Stop and reset swing if reach the start again
        // and the power was still being defined
        if(swingCursor < 0.0f){
            swingReset();
        }
    }
}
