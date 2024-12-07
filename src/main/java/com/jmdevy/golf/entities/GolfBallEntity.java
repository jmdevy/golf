package com.jmdevy.golf.entities;

import com.jmdevy.golf.Golf;

import net.minecraft.client.multiplayer.chat.LoggedChatMessage.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;


public class GolfBallEntity extends Entity {

    public GolfBallEntity(EntityType<? extends GolfBallEntity> type, Level world) {
        super(type, world);
        this.setBoundingBox(new AABB(this.position(), this.position().add(this.getDimensions(Pose.STANDING).width, this.getDimensions(Pose.STANDING).height, this.getDimensions(Pose.STANDING).width)));
        this.refreshDimensions();
    }

    @Override
    public void tick() {
        super.tick();
    }


    @Override
    public AABB getBoundingBoxForCulling() {
        return this.getBoundingBox();
    }
    
    @Override
    public net.minecraft.world.entity.EntityDimensions getDimensions(net.minecraft.world.entity.Pose pose) {
        return net.minecraft.world.entity.EntityDimensions.scalable(1.0F, 1.0F);
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {
        
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        
    }

    public InteractionResult interact(Player player, InteractionHand hand) {
        Golf.LOGGER.info("Touched entity!");
        return InteractionResult.PASS;
    }
}
