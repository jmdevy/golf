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


public class GolfBallEntityModel extends EntityModel<GolfBallEntity> {
    private final ModelPart ball;

    public GolfBallEntityModel(ModelPart root) {
        super(RenderType::entityTranslucent);
        this.ball = root.getChild("ball");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("ball", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(GolfBallEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // Add animation logic here if needed
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        ball.render(poseStack, buffer, packedLight, packedOverlay);
    }
}





// package com.jmdevy.golf.entities;

// import com.jmdevy.golf.entities.GolfBallEntity;
// import com.mojang.blaze3d.vertex.PoseStack;
// import com.mojang.blaze3d.vertex.VertexConsumer;

// import net.minecraft.client.model.EntityModel;
// import net.minecraft.client.model.geom.ModelLayerLocation;
// import net.minecraft.client.model.geom.ModelPart;
// import net.minecraft.client.model.geom.PartPose;
// import net.minecraft.client.model.geom.builders.CubeDeformation;
// import net.minecraft.client.model.geom.builders.CubeListBuilder;
// import net.minecraft.client.model.geom.builders.LayerDefinition;
// import net.minecraft.client.model.geom.builders.MeshDefinition;
// import net.minecraft.client.model.geom.builders.PartDefinition;
// import net.minecraft.resources.ResourceLocation;
// import net.minecraft.util.Mth;

// public class GolfBallEntityModel<T extends GolfBallEntity> extends EntityModel<T> {
//     public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("golf", "golf_ball_entity"), "main");
//     private final ModelPart body;

//     public GolfBallEntityModel(ModelPart root) {
//         this.body = root.getChild("body");
//     }

//     public static LayerDefinition createBodyLayer() {
//         MeshDefinition meshdefinition = new MeshDefinition();
//         PartDefinition partdefinition = meshdefinition.getRoot();

//         partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

//         return LayerDefinition.create(meshdefinition, 64, 32);
//     }

//     @Override
//     public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//         // Animation logic
//     }

//     @Override
//     public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//         body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
//     }
// }
