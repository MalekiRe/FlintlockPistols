package flintlockweapons.flintlockweapons.renderers;

import flintlockweapons.flintlockweapons.entities.MusketBallEntity;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;

public class MusketBallEntityModel extends EntityModel<MusketBallEntity> {
    private final ModelPart base;

    public MusketBallEntityModel(){
        textureHeight = 2;
        textureWidth = 4;
        base = new ModelPart(this, 0, 0);
        //base.addCuboid(-6f, -6f, -6f, 1, 1, 1);
        base.addCuboid(-0.5f,4f, -0.5f, 1, 1, 1);
    }

    @Override
    public void setAngles(MusketBallEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }



    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
                       float blue, float alpha) {
        matrices.translate(0, -.25, 0);
        // render cube
        base.render(matrices, vertices, light, overlay);


    }
}
