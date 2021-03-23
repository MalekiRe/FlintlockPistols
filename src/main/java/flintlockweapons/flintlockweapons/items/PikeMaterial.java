package flintlockweapons.flintlockweapons.items;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class PikeMaterial implements ToolMaterial {

    public static final PikeMaterial INSTANCE = new PikeMaterial();
    @Override
    public int getDurability() {
        return -1;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return 0;
    }

    @Override
    public float getAttackDamage() {
        return 1F;
    }

    @Override
    public int getMiningLevel() {
        return 0;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return null;
    }
}
