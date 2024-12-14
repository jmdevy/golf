package com.jmdevy.golf.common.items;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import com.jmdevy.golf.Golf;
import com.jmdevy.golf.common.entities.GolfBallEntity;
import com.jmdevy.golf.common.entities.GolfBallEntityModel;


public class GolfBallItem extends Item {
    public GolfBallItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (world.isClientSide) {
            return InteractionResultHolder.success(itemStack);
        }

        BlockHitResult blockHitResult = (BlockHitResult) player.pick(5.0D, 1.0F, false);  // 5.0D is the distance to look
        Direction blockFaceDirection = blockHitResult.getDirection();

        // Do not allow the player to place the ball on walls or ceilings
        if(blockFaceDirection.getStepX() != 0 || blockFaceDirection.getStepY() != 1 || blockFaceDirection.getStepZ() != 0){
            return InteractionResultHolder.fail(itemStack);
        }

        Vec3 offset = new Vec3(0,
                               (blockFaceDirection.getStepY()*GolfBallEntityModel.BALL_DIMENSIONS/2)/16.0f,
                               0);

        Vec3 hitVec = blockHitResult.getLocation();

        offset = offset.add(hitVec);
        
        if (blockHitResult.getType() != HitResult.Type.MISS) {
            if (!world.isClientSide) {
                GolfBallEntity entity = new GolfBallEntity(Golf.GOLF_BALL.get(), player.level());
                entity.setPos(offset.x(), offset.y(), offset.z());
                world.addFreshEntity(entity);
                if (!player.isCreative()) {
                    itemStack.shrink(1);
                }
            }
            return InteractionResultHolder.success(itemStack);
        }
        return InteractionResultHolder.pass(itemStack);
    }
}
