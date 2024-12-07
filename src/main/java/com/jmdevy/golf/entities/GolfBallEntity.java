package com.jmdevy.golf.entities;

import com.jmdevy.golf.Golf;

import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.EntityDimensions;


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
    public EntityDimensions getDimensions(net.minecraft.world.entity.Pose pose) {
        return EntityDimensions.scalable(1.0F/16.0F, 1.0F/16.0F);
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

    @Override public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean isPickable() {
        return true;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        Golf.LOGGER.info("Right clicked golf ball! interact");
        return InteractionResult.PASS;
    }

    @Override public InteractionResult interactAt(Player player, Vec3 hitVec, InteractionHand hand) {
        Golf.LOGGER.info("Right clicked entity golf ball! interact at");
        return InteractionResult.PASS;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Golf.LOGGER.info("Left clicked golf ball! hurt");
        return false;
    }

    // @Override
    // public void push(Entity entity) {
    //     super.push(entity);
    //     Golf.LOGGER.info("Collision detected with golf ball: " + entity.getName().getString());
    // }

    // @Override
    // public boolean canCollideWith(Entity entity) {
    //     // You can add logic here to filter out which entities can collide
    //     if (entity instanceof Player) {
    //         Golf.LOGGER.info("Detected potential collision with player: " + entity.getName().getString());
    //         return true;
    //     }
    //     return super.canCollideWith(entity);
    // }

}
