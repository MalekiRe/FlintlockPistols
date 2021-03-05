package flintlockweapons.flintlockweapons;

import flintlockweapons.flintlockweapons.renderers.MusketBallEntityRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class FlintlockweaponsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(Flintlockweapons.MUSKET_BALL, (dispatcher, context) -> {
            return new MusketBallEntityRenderer(dispatcher);
        });
    }
}
