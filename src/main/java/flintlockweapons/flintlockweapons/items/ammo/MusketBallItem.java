package flintlockweapons.flintlockweapons.items.ammo;

import flintlockweapons.flintlockweapons.entities.MusketBallEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MusketBallItem extends AmmoItem {

    public MusketBallItem(Settings settings) {
        super(settings);
    }

    @Override
    public PersistentProjectileEntity createProjectile(World world, LivingEntity shooter) {
        MusketBallEntity musketBallEntity = new MusketBallEntity(world, shooter);
        return musketBallEntity;
    }

    public PersistentProjectileEntity createMusketBall(World world, ItemStack stack, LivingEntity shooter) {
        MusketBallEntity musketBallEntity = new MusketBallEntity(world, shooter);
        return musketBallEntity;
    }
}
