package com.jmdevy.golf.entities;

import com.jmdevy.golf.entities.GolfBallEntityModel;
import com.jmdevy.golf.Golf;
import com.jmdevy.golf.entities.GolfBallEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.ModelLayerLocation;


public class GolfBallEntityRenderer extends EntityRenderer<GolfBallEntity> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Golf.MODID, "textures/entity/golf_ball.png");
    public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(new ResourceLocation(Golf.MODID, "golf_ball"), "main");
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



// Sort of close to loading a model
// package com.jmdevy.golf.entities;

// import com.jmdevy.golf.Golf;
// import com.mojang.blaze3d.vertex.PoseStack;
// import com.mojang.blaze3d.vertex.VertexConsumer;

// import net.minecraft.client.renderer.MultiBufferSource;
// import net.minecraft.client.renderer.RenderType;
// import net.minecraft.client.renderer.entity.EntityRenderer;
// import net.minecraft.client.renderer.entity.EntityRendererProvider;
// import net.minecraft.client.renderer.texture.OverlayTexture;
// import net.minecraft.resources.ResourceLocation;
// import net.minecraft.client.Minecraft;
// import net.minecraft.client.model.geom.ModelLayerLocation;
// import net.minecraft.client.resources.model.BakedModel;


// public class GolfBallEntityRenderer extends EntityRenderer<GolfBallEntity> {
//     private static final ResourceLocation TEXTURE = new ResourceLocation(Golf.MODID, "textures/golf_ball.png");
//     public static final ModelLayerLocation MODEL_LAYER = new ModelLayerLocation(new ResourceLocation(Golf.MODID, "golf_ball"), "main");
//     private final BakedModel model;

//     public GolfBallEntityRenderer(EntityRendererProvider.Context context) {
//         super(context);
//         this.model = Minecraft.getInstance().getModelManager().getModel(new ResourceLocation(Golf.MODID, "models/golf_ball.json"));
//     }

//     @Override
//     public void render(GolfBallEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
//         poseStack.pushPose();
//         // Apply necessary transformations here
//         VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entitySolid(this.getTextureLocation(entity)));
//         Minecraft.getInstance().getBlockRenderer().getModelRenderer().renderModel(poseStack.last(), vertexConsumer, null, model, 1.0F, 1.0F, 1.0F, packedLight, OverlayTexture.NO_OVERLAY);
        
//         poseStack.popPose();
//         super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
//     }

//     @Override
//     public ResourceLocation getTextureLocation(GolfBallEntity entity) {
//         return TEXTURE;
//     }
// }