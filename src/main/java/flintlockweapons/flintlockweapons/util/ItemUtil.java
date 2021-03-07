package flintlockweapons.flintlockweapons.util;

import net.minecraft.item.Item;

public class ItemUtil {
    public static boolean itemArrayContainsItem(Item[] itemArray, Item item) {
        for(Item i : itemArray)
            if(i == item)
                return true;
        return false;
    }
}
