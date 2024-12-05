package com.jmdevy.golf.entities;

import com.jmdevy.golf.entities.GolfBallEntityModel;
import com.jmdevy.golf.entities.GolfBallEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.ModelLayerLocation;

// import net.minecraft.world.phys.Vec3;




public class GolfBallEntityRenderer extends EntityRenderer<GolfBallEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("yourmod", "textures/entity/golf_ball.png");
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(new ResourceLocation("golf", "golf_ball"), "main");
    private final GolfBallEntityModel model;

    public GolfBallEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        model = new GolfBallEntityModel(context.bakeLayer(MODEL_LAYER));
    }

    @Override
    public void render(GolfBallEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0.0D, 0.0D, 0.0D);
        model.renderToBuffer(poseStack, buffer.getBuffer(model.renderType(TEXTURE)), packedLight, 0, 1.0F, 1.0F, 1.0F, 1.0F);
        // poseStack.mulPose(Vec3.YP.rotationDegrees(-entityYaw));
        // Add custom rendering logic here
        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(GolfBallEntity entity) {
        return TEXTURE;
    }
}
