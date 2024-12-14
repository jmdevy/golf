package com.jmdevy.golf.common.entities;

import com.jmdevy.golf.Golf;
import com.jmdevy.golf.client.ClientStanceHandler;
import com.jmdevy.golf.common.entities.GolfBallEntityModel;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ChunkMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeConfig.Server;
import net.minecraftforge.server.ServerLifecycleHooks;
import net.minecraft.world.entity.EntityDimensions;





// Note: there might be a bug where the hitbox is being rest by some
//       some other MineCraft code. This causes a blinking/flickering.
//       Might not be able to see it when close to the ball for some reason
//       and causes the player to hit through the ball sometimes (FIX: TODO)
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
        ClientStanceHandler.setBallFocus(true, this, player);

        // Vec3 hitStart = new Vec3(this.getX(), this.getY(), this.getZ());
        // Vec3 hitEnd = hitStart;
        // Vec3 lastHitEnd = hitEnd;

        // float hitAngleDeg = -60.0f;
        // float hitDirYaw = player.getYRot();
        // float hitVelocity = 15.0f;
        // float gravity = -9.81f;  // m/s

        // Vec3 hitDirection = Vec3.directionFromRotation(hitAngleDeg, hitDirYaw);
        // Vec3 ballVelocity = new Vec3(hitDirection.x()*hitVelocity, hitDirection.y()*hitVelocity, hitDirection.z()*hitVelocity);

        // // Now step simulate path
        // double timeStep = 0.005;

        // while(hitEnd.y() > -72.0){
        //     ballVelocity = ballVelocity.add(0, gravity*timeStep, 0);

        //     lastHitEnd = hitEnd;
        //     hitEnd = hitEnd.add(ballVelocity.x() * timeStep, ballVelocity.y() * timeStep, ballVelocity.z() * timeStep);

        //     BlockPos blockPos = new BlockPos((int)hitEnd.x(), (int)hitEnd.y(), (int)hitEnd.z());

        //     ClipContext context = new ClipContext(lastHitEnd, hitEnd, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player);
        //     BlockHitResult hit = player.level().clip(context);

        //     if(hit.getType() != HitResult.Type.MISS){
        //         Golf.LOGGER.info(hit.getLocation().toString());
        //         Golf.LOGGER.info(hit.getDirection().toString());
        //         this.setPos(hit.getLocation());
        //         break;
        //     }
        // }


        return InteractionResult.PASS;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Golf.LOGGER.info("Left clicked golf ball! hurt");

        // Indicate that the entity was not removed if a
        // non-player entity deals damage to the ball
        if (!(source.getEntity() instanceof Player)) {
            return false;
        }

        // Remove the entity as long as a player dealt the damage
        this.remove(RemovalReason.KILLED);

        // Now need to handle the item that will be given back
        // Is player, get the player object
        Player player = (Player)source.getEntity();

        // Don't do anything with the potential item if in creative mode
        if(player.isCreative()){
            return true;    // Entity removed
        }

        // Give the item back to the player
        ItemStack golfBallItemStack = new ItemStack(Golf.GOLF_BALL_ITEM.get());

        if (!player.addItem(golfBallItemStack)) {
            player.drop(golfBallItemStack, false);
        }

        return true; // Indicate that the entity was hurt and removed
    }

}
