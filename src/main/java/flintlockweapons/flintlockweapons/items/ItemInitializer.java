package flintlockweapons.flintlockweapons.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

import java.util.ArrayList;

public class ItemInitializer {

    public static String[] itemNames = {"blunderbuss", "musket", "cartridge", "gun_barrel", "musket_with_bayonet"};
    public static MusketBallItem musketBallItem = new MusketBallItem(new FabricItemSettings().group(ItemGroup.MISC));
    public static PistolItem flintlockPistolItem = new PistolItem(new FabricItemSettings().group(ItemGroup.MISC).maxDamage(100));
    public static ArrayList<ItemHelper> items = new ArrayList<ItemHelper>();
    public static void init()
    {
        items.add(new ItemHelper(flintlockPistolItem, "flintlock_pistol"));
        //items.add(new ItemHelper(musketBallItem, "musket_ball"));
        for(String s : itemNames)
        {
            items.add(new ItemHelper(new Item(new FabricItemSettings().group(ItemGroup.MISC)), s));
        }
    }
}
