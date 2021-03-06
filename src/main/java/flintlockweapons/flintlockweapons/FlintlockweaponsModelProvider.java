package flintlockweapons.flintlockweapons;

import flintlockweapons.flintlockweapons.items.ItemInitializer;
import flintlockweapons.flintlockweapons.items.PistolItem;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class FlintlockweaponsModelProvider {
    private static void registerBowModels() {

        FabricModelPredicateProviderRegistry.register(ItemInitializer.flintlockPistolItem, new Identifier("charged"), (itemStack, clientWorld, livingEntity) -> {
            if (livingEntity == null) {
                return 0.0F;
            }
            return PistolItem.isCharged(itemStack) ? 1.0F : 0.0F;
        });


    }
    public static void registerModels()
    {
        registerBowModels();
    }

}
