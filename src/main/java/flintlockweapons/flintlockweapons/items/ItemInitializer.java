package flintlockweapons.flintlockweapons.items;

import flintlockweapons.flintlockweapons.items.ammo.AmmoItem;
import flintlockweapons.flintlockweapons.items.ammo.MusketBallItem;
import flintlockweapons.flintlockweapons.items.weapons.guns.GunItem;
import flintlockweapons.flintlockweapons.items.weapons.guns.GunMelee;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;

import static flintlockweapons.flintlockweapons.Flintlockweapons.MOD_ID;

public class ItemInitializer {

    public static String[] itemNames = {"cartridge", "gun_barrel"};

    public static MusketBallItem musketBallItem = new MusketBallItem(new FabricItemSettings().group(ItemGroup.MISC));

    public static ToolItem PIKE_ITEM = new SwordItem(PikeMaterial.INSTANCE, 3, 0.3F, new FabricItemSettings().group(ItemGroup.COMBAT));


    static Identifier pistolLoad1 = new Identifier(MOD_ID, "flintlock_pistol_1");
    static Identifier pistolLoad2 = new Identifier(MOD_ID, "flintlock_pistol_2");
    static Identifier[] gunFireNoises = {pistolLoad1, pistolLoad2};
    static Identifier loadGun = new Identifier(MOD_ID, "gun_load");
    static Identifier[] loadGunNoises = {loadGun};
    static Identifier[] bulletHitNoise = {};

    static AmmoItem[] gunAmmo = {musketBallItem};
    public static GunItem flintLockPistolItem = new GunItem(new FabricItemSettings().group(ItemGroup.MISC).maxDamage(10),
            new AmmoItem[]{musketBallItem}, 100, 1, 0, 3.5F, 9, 2,
            new Identifier[]{new Identifier(MOD_ID, "flintlock_pistol_1"), new Identifier(MOD_ID, "flintlock_pistol_2")}, //Firing Sound effects
            new Identifier[]{new Identifier("minecraft","block.gravel.hit")},  //Hitting mob sound effects (currently not implemented)
            new Identifier[]{new Identifier(MOD_ID, "gun_load")}); //All gun loading sound effect
    public static GunItem blunderbussItem = new GunItem(new FabricItemSettings().group(ItemGroup.MISC).maxDamage(50),
            gunAmmo, 120, 5, 20, 2.1F, 2, 1,
            gunFireNoises, new Identifier[]{}, new Identifier[]{loadGun});
    public static GunItem musketItem = new GunItem(new FabricItemSettings().group(ItemGroup.MISC).maxDamage(100),
            gunAmmo, 120, 1, 0, 10F, 12, 0,
            gunFireNoises, bulletHitNoise, loadGunNoises);
    public static GunMelee musketWithBayonetItem = new GunMelee(new FabricItemSettings().group(ItemGroup.MISC).maxDamage(100),
            gunAmmo, 120, 1, 0, 10F, 12, 0,
            gunFireNoises, bulletHitNoise, loadGunNoises, 4F, 4F, ToolMaterials.IRON);

    public static ArrayList<ItemHelper> items = new ArrayList<ItemHelper>();
    public static void init()
    {
        items.add(new ItemHelper(flintLockPistolItem, "flintlock_pistol"));
        items.add(new ItemHelper(blunderbussItem, "blunderbuss"));
        items.add(new ItemHelper(musketItem, "musket"));
        items.add(new ItemHelper(musketWithBayonetItem, "musket_with_bayonet"));
        items.add(new ItemHelper(PIKE_ITEM, "pike"));
        for(String s : itemNames)
        {
            items.add(new ItemHelper(new Item(new FabricItemSettings().group(ItemGroup.MISC)), s));
        }
    }
}
