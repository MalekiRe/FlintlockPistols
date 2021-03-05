package flintlockweapons.flintlockweapons.renderers;

import flintlockweapons.flintlockweapons.Flintlockweapons;
import flintlockweapons.flintlockweapons.entities.MusketBallEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.Identifier;

public class MusketBallEntityRenderer extends EntityRenderer<MusketBallEntity> {
    public static final Identifier texture = new Identifier(Flintlockweapons.MOD_ID, "textures/entities/bullet.png");

    protected MusketBallEntityModel model;

    public MusketBallEntityRenderer(EntityRenderDispatcher entityRenderDispatcher, MusketBallEntityModel entityModel) {
        super(entityRenderDispatcher);
        model = entityModel;
    }

    public MusketBallEntityRenderer(EntityRenderDispatcher entityRenderDispatcher)
    {
        super(entityRenderDispatcher);
        model = new MusketBallEntityModel();
    }

    @Override
    public Identifier getTexture(MusketBallEntity entity) {
        return texture;
    }


    /*
    @Override
    public void render(MusketBallEntity entity, float yaw, float tickDelta, MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers, int light) {
        System.out.println("hai");
        model.render(matrices, vertexConsumers.getBuffer(model.getLayer(getTexture(entity))), light, OverlayTexture.DEFAULT_UV, 1, 1, 1, 1);
        if (this.hasLabel(entity)) {
            this.renderLabelIfPresent(entity, entity.getDisplayName(), matrices, vertexConsumers, light);
        }
    }*/
}
