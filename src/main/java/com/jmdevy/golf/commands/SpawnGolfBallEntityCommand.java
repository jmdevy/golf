package com.jmdevy.golf.commands;

import com.jmdevy.golf.Golf;
import com.jmdevy.golf.entities.GolfBallEntity;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;


public class SpawnGolfBallEntityCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("spawnGolfBallentity")
            .executes(context -> {
                CommandSourceStack source = context.getSource();
                ServerPlayer player = source.getPlayerOrException();
                Vec3 position = player.position();

                GolfBallEntity entity = new GolfBallEntity(Golf.GOLF_BALL.get(), player.level());
                entity.setPos(position.x, position.y, position.z);
                player.level().addFreshEntity(entity);

                source.sendSuccess(() -> Component.literal("Spawned golf ball entity!"), true);
                return 1;
            }));
    }
};
