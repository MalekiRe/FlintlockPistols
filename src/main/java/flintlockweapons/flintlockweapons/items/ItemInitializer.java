package flintlockweapons.flintlockweapons.items;

import flintlockweapons.flintlockweapons.items.ammo.AmmoItem;
import flintlockweapons.flintlockweapons.items.ammo.MusketBallItem;
import flintlockweapons.flintlockweapons.items.weapons.guns.GunItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;

import static flintlockweapons.flintlockweapons.Flintlockweapons.MOD_ID;

public class ItemInitializer {

    public static String[] itemNames = {"blunderbuss", "musket", "cartridge", "gun_barrel", "musket_with_bayonet"};

    public static MusketBallItem musketBallItem = new MusketBallItem(new FabricItemSettings().group(ItemGroup.MISC));
    //public static PistolItem flintlockPistolItem = new PistolItem(new FabricItemSettings().group(ItemGroup.MISC).maxDamage(100));
    public static GunItem flintLockPistolItem = new GunItem(new FabricItemSettings().group(ItemGroup.MISC).maxDamage(10),
            new AmmoItem[]{musketBallItem}, 1, 0, 3.5F, 9, 2,
            new Identifier[]{new Identifier(MOD_ID, "flintlock_pistol_1"), new Identifier(MOD_ID, "flintlock_pistol_2")}, //Firing Sound effects
            new Identifier[]{new Identifier("minecraft","block.gravel.hit")},  //Hitting mob sound effects (currently not implemented)
            new Identifier[]{new Identifier(MOD_ID, "gun_load")}); //All gun loading sound effect

    public static ArrayList<ItemHelper> items = new ArrayList<ItemHelper>();
    public static void init()
    {
        items.add(new ItemHelper(flintLockPistolItem, "flintlock_pistol"));
        for(String s : itemNames)
        {
            items.add(new ItemHelper(new Item(new FabricItemSettings().group(ItemGroup.MISC)), s));
        }
    }
}
