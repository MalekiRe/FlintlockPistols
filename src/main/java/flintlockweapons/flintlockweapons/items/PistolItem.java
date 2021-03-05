package flintlockweapons.flintlockweapons.items;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class PistolItem extends RangedWeaponItem implements Vanishable {


    public static final float RANGE = 1.1F;

    public PistolItem(Settings settings) {
        super(settings);
    }

    public ItemStack getMusketBall(LivingEntity user)
    {
        PlayerEntity playerEntity = (PlayerEntity) user;
        for(int i = 0; i < playerEntity.inventory.size(); i++)
        {
            if(playerEntity.inventory.getStack(i).getItem()==ItemInitializer.musketBallItem)
            {
                return playerEntity.inventory.getStack(i);
            }
        }
        return ItemStack.EMPTY;
    }
    public void fireArrow(ItemStack stack, World world, LivingEntity user, int remaningUseTicks, float f)
    {

        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity) user;
            boolean bl = playerEntity.abilities.creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemStack = getMusketBall(user);
            if (!itemStack.isEmpty() || bl) {
                if (itemStack.isEmpty()) {
                    itemStack = new ItemStack(ItemInitializer.musketBallItem);
                }


                boolean bl2 = bl && itemStack.getItem() == ItemInitializer.musketBallItem;
                if (!world.isClient) {
                    MusketBallItem musketBallItem = (MusketBallItem) ((MusketBallItem) (itemStack.getItem() instanceof MusketBallItem ? itemStack.getItem() : ItemInitializer.musketBallItem));
                    PersistentProjectileEntity persistentProjectileEntity = musketBallItem.createMusketBall(world, itemStack, playerEntity);

                    persistentProjectileEntity.setProperties(playerEntity, playerEntity.pitch, playerEntity.yaw, 0.0F, RANGE, 2.5F);

                    persistentProjectileEntity.setCritical(true);


                    int j = EnchantmentHelper.getLevel(Enchantments.POWER, stack);
                    if (j > 0) {
                        persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() + (double) j * 0.5D + 0.5D);
                    }

                    int k = EnchantmentHelper.getLevel(Enchantments.PUNCH, stack);
                    if (k > 0) {
                        persistentProjectileEntity.setPunch(k);
                    }

                    if (EnchantmentHelper.getLevel(Enchantments.FLAME, stack) > 0) {
                        persistentProjectileEntity.setOnFireFor(100);
                    }

                    if (bl2 || playerEntity.abilities.creativeMode && (itemStack.getItem() == Items.SPECTRAL_ARROW || itemStack.getItem() == Items.TIPPED_ARROW)) {
                        persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                    }

                    world.spawnEntity(persistentProjectileEntity);
                }

                world.playSound((PlayerEntity) null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F) + f * 0.5F);


                playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
            }

        }

    }

    public static boolean isCharged(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        return compoundTag != null && compoundTag.getBoolean("Charged");
    }

    public static void setCharged(ItemStack stack, boolean charged) {
        CompoundTag compoundTag = stack.getOrCreateTag();
        compoundTag.putBoolean("Charged", charged);
    }
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)user;
            boolean bl = playerEntity.abilities.creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, stack) > 0;
            ItemStack itemStack = getMusketBall(user);
            if (!itemStack.isEmpty() || bl) {
                if (itemStack.isEmpty()) {
                    itemStack = new ItemStack(ItemInitializer.musketBallItem);
                }

                int i = this.getMaxUseTime(stack) - remainingUseTicks;
                float f = getPullProgress(i);
                if(f == 1.0F)
                {

                    if (!playerEntity.abilities.creativeMode) {
                        System.out.println("REMOVE");
                        itemStack.decrement(1);
                        if (itemStack.isEmpty()) {
                            playerEntity.inventory.removeOne(itemStack);
                        }
                    }
                    setCharged(stack, true);
                    //stack.addEnchantment(Enchantments.FIRE_PROTECTION, 1);
                    stack.setDamage(stack.getDamage()-1);
                    world.playSound((PlayerEntity)null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, SoundCategory.PLAYERS, 1.0F, 1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                }


            }
        }
    }

    public static float getPullProgress(int useTicks) {
        float f = (float)useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }

    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {



        ItemStack itemStack = user.getStackInHand(hand);
        boolean bl = !user.getArrowType(itemStack).isEmpty();
        if(this.isCharged(itemStack))
        {
            fireArrow(itemStack, world, user, 0, 1.0F);
            this.setCharged(itemStack, false);

            itemStack.setDamage(0);
        }
        if (!user.abilities.creativeMode && !bl) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }

    }

    public Predicate<ItemStack> getProjectiles() {
        return BOW_PROJECTILES;
    }

    public int getRange() {
        return 15;
    }
}
