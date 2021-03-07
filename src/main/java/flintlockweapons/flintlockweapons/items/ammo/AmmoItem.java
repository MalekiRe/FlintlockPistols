package flintlockweapons.flintlockweapons.items.ammo;

import flintlockweapons.flintlockweapons.entities.MusketBallEntity;
import flintlockweapons.flintlockweapons.entities.ammo.AmmoEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class AmmoItem extends Item {
    public AmmoItem(Settings settings) {
        super(settings);
    }

    public abstract PersistentProjectileEntity createProjectile(World world, LivingEntity shooter);
}
