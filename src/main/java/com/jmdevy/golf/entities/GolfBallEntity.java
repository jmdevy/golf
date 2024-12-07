package com.jmdevy.golf.entities;

import com.jmdevy.golf.Golf;
import com.jmdevy.golf.entities.GolfBallEntityModel;

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
        this.updateBoundingBox();
    }

    private void updateBoundingBox() {
        double halfSize = GolfBallEntityModel.BALL_DIMENSIONS / 2.0 / 16.0;
        this.setBoundingBox(new AABB(
            this.getX() - halfSize,
            this.getY() - halfSize,
            this.getZ() - halfSize,
            this.getX() + halfSize,
            this.getY() + halfSize,
            this.getZ() + halfSize
        ));
    }

    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        this.updateBoundingBox(); // Ensure bounding box is set when entity is added to the world
    }

    @Override
    public void tick() {
        super.tick();
        this.updateBoundingBox();
    }

    @Override
    public AABB getBoundingBoxForCulling() {
        this.updateBoundingBox();
        return this.getBoundingBox();
    }
    
    @Override public EntityDimensions getDimensions(Pose pose) {
        this.updateBoundingBox();
        float size = GolfBallEntityModel.BALL_DIMENSIONS / 16.0F; 
        return EntityDimensions.scalable(size, size);
    }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {}

    @Override
    public boolean canBeCollidedWith() {
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

    @Override
    public InteractionResult interactAt(Player player, Vec3 hitVec, InteractionHand hand) {
        Golf.LOGGER.info("Right clicked entity golf ball! interact at");
        return InteractionResult.PASS;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Golf.LOGGER.info("Left clicked golf ball! hurt");
        return false;
    }
}
