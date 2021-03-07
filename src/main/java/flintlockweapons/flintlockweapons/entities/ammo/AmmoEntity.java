package flintlockweapons.flintlockweapons.entities.ammo;

import flintlockweapons.flintlockweapons.entities.MusketBallEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AmmoEntity extends PersistentProjectileEntity {
    public AmmoEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public AmmoEntity(EntityType<MusketBallEntity> musketBall, double x, double y, double z, World world) {
        super(musketBall, x, y, z, world);

    }

    public AmmoEntity(EntityType<MusketBallEntity> musketBall, LivingEntity owner, World world) {
        super(musketBall, owner, world);
    }


    @Override
    protected ItemStack asItemStack() {
        return null;
    }
}
