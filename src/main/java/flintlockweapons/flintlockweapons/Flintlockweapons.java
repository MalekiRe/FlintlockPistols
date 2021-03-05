package flintlockweapons.flintlockweapons;

import flintlockweapons.flintlockweapons.entities.EntityInitializer;
import flintlockweapons.flintlockweapons.entities.MusketBallEntity;
import flintlockweapons.flintlockweapons.items.ItemHelper;
import flintlockweapons.flintlockweapons.items.ItemInitializer;
import flintlockweapons.flintlockweapons.items.MusketBallItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Flintlockweapons implements ModInitializer {
    public static final String MOD_ID = "flintlockweapons";
    public static final EntityType<ArrowEntity> MUSKET_BALL = Registry.register(Registry.ENTITY_TYPE, new Identifier(MOD_ID, "musket_ball_entity"), FabricEntityTypeBuilder.<ArrowEntity>create(SpawnGroup.MISC, ArrowEntity::new).dimensions(EntityDimensions.fixed(.5f, .5f)).build());

    @Override
    public void onInitialize() {
        System.out.println("HELLO");
        ItemInitializer.init();
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "musket_ball"), ItemInitializer.musketBallItem);
        for(ItemHelper item : ItemInitializer.items)
            Registry.register(Registry.ITEM, new Identifier(MOD_ID, item.name), item.item);

    }
}
