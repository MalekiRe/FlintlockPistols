package flintlockweapons.flintlockweapons;

import flintlockweapons.flintlockweapons.entities.MusketBallEntity;
import flintlockweapons.flintlockweapons.items.ItemHelper;
import flintlockweapons.flintlockweapons.items.ItemInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Flintlockweapons implements ModInitializer {
    public static final String MOD_ID = "flintlockweapons";
    public static final EntityType<MusketBallEntity> MUSKET_BALL = Registry.register(Registry.ENTITY_TYPE, new Identifier(MOD_ID, "musket_ball_entity"), FabricEntityTypeBuilder.<MusketBallEntity>create(SpawnGroup.MISC, MusketBallEntity::new).dimensions(EntityDimensions.fixed(.5f, .5f)).build());

    public static SoundEvent pistol1 = new SoundEvent(new Identifier("flintlockweapons","flintlock_pistol_1"));
    public static SoundEvent pistol2 = new SoundEvent(new Identifier("flintlockweapons","flintlock_pistol_2"));
    public static String[] soundEventNames = {"flintlock_pistol_1", "flintlock_pistol_2", "gun_load"};


    @Override
    public void onInitialize() {

        //FabricDefaultAttributeRegistry.register(MUSKET_BALL, Att);
        initializeFlintlockSoundEvents();
        System.out.println("HELLO");
        //Registry.register(Registry.SOUND_EVENT, new Identifier("flintlockweapons","flintlock_pistol_1"), pistol1);
        //Registry.register(Registry.SOUND_EVENT, new Identifier("flintlockweapons","flintlock_pistol_2"), pistol2);
        ItemInitializer.init();
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "musket_ball"), ItemInitializer.musketBallItem);

        for(ItemHelper item : ItemInitializer.items)
            Registry.register(Registry.ITEM, new Identifier(MOD_ID, item.name), item.item);

    }

    public static void initializeFlintlockSoundEvents() {
        for(int i = 0; i < soundEventNames.length; i++)
            Registry.register(Registry.SOUND_EVENT, new Identifier(MOD_ID, soundEventNames[i]), new SoundEvent(new Identifier(MOD_ID, soundEventNames[i])));
    }
}
