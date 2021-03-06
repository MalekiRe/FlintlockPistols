package flintlockweapons.flintlockweapons;

import flintlockweapons.flintlockweapons.entities.EntityPacketUtils;
import flintlockweapons.flintlockweapons.renderers.MusketBallEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class FlintlockweaponsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        System.out.println("HEHEHEHEHEHHEEHEHEHHEHEHEHEHHEHE");
        FlintlockweaponsModelProvider.registerModels();

        EntityRendererRegistry.INSTANCE.register(Flintlockweapons.MUSKET_BALL, (dispatcher, context) -> {
            return new MusketBallEntityRenderer(dispatcher); //This is working
        });
        ClientSidePacketRegistry.INSTANCE.register(EntityPacketUtils.SPAWN_PACKET_ID, (context, byteBuf) ->
        {
            final EntityType<?> type = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
            final UUID entityUUID = byteBuf.readUuid();
            final int entityID = byteBuf.readVarInt();
            final double x = byteBuf.readDouble();
            final double y = byteBuf.readDouble();
            final double z = byteBuf.readDouble();
            final float pitch = (byteBuf.readByte() * 360) / 256.0F;
            final float yaw = (byteBuf.readByte() * 360) / 256.0F;

            context.getTaskQueue().execute(() ->
            {
                final MinecraftClient client = MinecraftClient.getInstance();
                final ClientWorld world = client.world;
                final Entity entity = type.create(world);
                if (world != null && entity != null)
                {
                    entity.updatePosition(x, y, z);
                    entity.updateTrackedPosition(x, y, z);
                    entity.pitch = pitch;
                    entity.yaw = yaw;
                    entity.setEntityId(entityID);
                    entity.setUuid(entityUUID);
                    world.addEntity(entityID, entity);
                }
            });
        });



    }
}
