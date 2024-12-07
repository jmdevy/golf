package com.jmdevy.golf.entities;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.jmdevy.golf.entities.GolfBallEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.client.model.obj.ObjLoader;


public class GolfBallEntityModel extends EntityModel<GolfBallEntity> {
    private final ModelPart ball;

    public static final float BALL_DIMENSIONS = 1.5f;

    public GolfBallEntityModel(ModelPart root) {
        super(RenderType::entitySolid);
        this.ball = root.getChild("golf_ball");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("golf_ball", CubeListBuilder.create().texOffs(0, 0).addBox(-BALL_DIMENSIONS/2, -BALL_DIMENSIONS/2, -BALL_DIMENSIONS/2, BALL_DIMENSIONS, BALL_DIMENSIONS, BALL_DIMENSIONS), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 1, 1);
    }

    @Override
    public void setupAnim(GolfBallEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Add animation logic here if needed
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        ball.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}