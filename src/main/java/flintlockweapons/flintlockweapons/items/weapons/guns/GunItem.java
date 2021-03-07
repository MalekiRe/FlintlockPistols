package flintlockweapons.flintlockweapons.items.weapons.guns;

import flintlockweapons.flintlockweapons.items.ItemInitializer;
import flintlockweapons.flintlockweapons.items.ammo.AmmoItem;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;


import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import static flintlockweapons.flintlockweapons.Flintlockweapons.MOD_ID;


/**
 * This class is what all guns like the flintlock pistol extend
 */
public class GunItem extends RangedWeaponItem implements Vanishable {

    public float range;
    public float damage;
    public float damageVariance;
    public float fireVolume = .55F;
    public float hitVolume = 1.0F;
    public int bulletAmount = 1;
    public float bulletSpread = 0;
    public AmmoItem[] ammo;
    public Identifier[] fireSoundNames;
    public Identifier[] hitSoundNames;
    public Identifier[] loadSoundNames;
    public GunItem(Settings settings, AmmoItem[] ammo, int bulletAmount, float bulletSpread, float range, float damage, float damageVariance, Identifier[] fireSoundNames, Identifier[] hitSoundNames, Identifier[] loadSoundNames) {
        super(settings);
        this.range = range;
        this.damage = damage;
        this.damageVariance = damageVariance;
        this.ammo = ammo;
        this.fireSoundNames = fireSoundNames;
        this.hitSoundNames = hitSoundNames;
        this.loadSoundNames = loadSoundNames;
        this.bulletAmount = bulletAmount;
        this.bulletSpread = bulletSpread;


    }
    public boolean getIsCriticalHit() {
        return RANDOM.nextBoolean();
    }
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

        ItemStack itemStack = user.getStackInHand(hand);

        if(isLoaded(itemStack))
        {
            fireAmmo(itemStack, world, user, getAmmo(itemStack));
            setLoaded(itemStack, false);
        }
        if (!user.abilities.creativeMode && !hasArrayAmmoInInventory(user.inventory, ammo)) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }

    }
    public static boolean hasArrayAmmoInInventory(Inventory inventory, AmmoItem[] ammoItems)
    {
        for(AmmoItem ammoItem : ammoItems)
            if(hasAmmoInInventory(inventory, ammoItem))
                return true;
        return false;
    }
    public void fireAmmo(ItemStack weaponStack, World world, PlayerEntity playerEntity, AmmoItem ammoToUse)
    {
            boolean isCreative = playerEntity.abilities.creativeMode;
            ItemStack ammoStack = getAmmoInInventory(playerEntity.inventory, ammoToUse);
            if(!ammoStack.isEmpty() || isCreative)
                if (!world.isClient) {
                    PersistentProjectileEntity persistentProjectileEntity = ammoToUse.createProjectile(world, playerEntity);
                    persistentProjectileEntity.setProperties(playerEntity, playerEntity.pitch, playerEntity.yaw, 0.0F, range, RANDOM.nextFloat()*bulletSpread);
                    persistentProjectileEntity.setCritical(getIsCriticalHit());
                    persistentProjectileEntity.setDamage(damage);

                    if(isCreative)
                        persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;

                    world.spawnEntity(persistentProjectileEntity);

                }
                playFireSound(world, playerEntity);
                playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        setLoaded(weaponStack, false);
    }

    public void onStoppedUsing(ItemStack weaponStack, World world, LivingEntity user, int remainingUseTicks) {
        if (user instanceof PlayerEntity) {
            PlayerEntity playerEntity = (PlayerEntity)user;
            boolean isCreative = playerEntity.abilities.creativeMode;
            for(AmmoItem ammoItem : ammo) {
                if(hasAmmoInInventory(playerEntity.inventory, ammoItem)) {
                    isLoadable(weaponStack, world, playerEntity, remainingUseTicks, isCreative, ammoItem);
                    return;
                }
            }
            isLoadable(weaponStack, world, playerEntity, remainingUseTicks, isCreative, ammo[0]);
            }
    }

    public boolean isLoadable(ItemStack weaponStack, World world, PlayerEntity user, int useTicks, boolean isCreative, AmmoItem ammoItem) {

        if(getPullProgress(this.getMaxUseTime(weaponStack)-useTicks) == 1.0F)
        {
            playLoadSound(world, user);
            if(!isCreative)
            {
                ItemStack ammoStack = getAmmoInInventory(user.inventory, ammoItem);
                ammoStack.decrement(1);
                if(ammoStack.isEmpty())
                    user.inventory.removeOne(ammoStack);
                weaponStack.damage(1, RANDOM, null);

            }
            setLoaded(weaponStack, true);
            setAmmo(weaponStack, ammoItem);
            return true;
        }
        return false;

    }

    public static float getPullProgress(int useTicks) {
        float f = (float)useTicks / 20.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }

        return f;
    }
    /**
     * Plays a random sound from the array of fireSounds
     */
    public void playFireSound(World world, PlayerEntity playerEntity) {
        world.playSound((PlayerEntity) null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), Registry.SOUND_EVENT.get(fireSoundNames[RANDOM.nextInt(fireSoundNames.length)]), SoundCategory.PLAYERS, fireVolume, 1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F) + 0.5F);
    }

    /**
     * Plays a random sound from the array of hitSounds
     */
    public void playHitSound(World world, PlayerEntity playerEntity) {
        world.playSound((PlayerEntity) null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), Registry.SOUND_EVENT.get(hitSoundNames[RANDOM.nextInt(hitSoundNames.length)]), SoundCategory.PLAYERS, fireVolume, 1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F) + 0.5F);
    }
    public void playLoadSound(World world, PlayerEntity playerEntity) {
        world.playSound((PlayerEntity) null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), Registry.SOUND_EVENT.get(loadSoundNames[RANDOM.nextInt(loadSoundNames.length)]), SoundCategory.PLAYERS, fireVolume, 1.0F / (RANDOM.nextFloat() * 0.4F + 1.2F) + 0.5F);
    }
    /**
     * Using this instead of .contians in inventory, because .contains requires a Set of items, or an itemstack.
     * Using Inventory instead of player inventory in case of future compatiablity with ammo bags and the like.
     * @param inventory - the inventory to check
     * @param ammoType - the item to check
     * @return - gets if the specific inventory has that item in its slot
     */
    public static boolean hasAmmoInInventory(Inventory inventory, AmmoItem ammoType) {
        for(int i = 0; i < inventory.size(); i++)
            if(inventory.getStack(i).getItem() == ammoType)
                return true;
        return false;
    }

    /**
     * @return gets the first itemstack that contains the item of ammoType
     */
    public static ItemStack getAmmoInInventory(Inventory inventory, AmmoItem ammoType)
    {
        for(int i = 0; i < inventory.size(); i++)
            if(inventory.getStack(i).getItem() == ammoType)
                return inventory.getStack(i);
        return ItemStack.EMPTY;
    }

    public static AmmoItem getAmmo(ItemStack stack)
    {
        CompoundTag compoundTag = stack.getTag();
        return (AmmoItem)Registry.ITEM.get(new Identifier(MOD_ID, compoundTag.getString("ammoType")));
    }
    public static void setAmmo(ItemStack stack, AmmoItem ammoItem)
    {
        CompoundTag compoundTag = stack.getTag();
        String id = String.valueOf(Registry.ITEM.getId(ammoItem));
        compoundTag.putString("ammoType", id.substring(id.indexOf(':')+1));
    }
    /**
     * sets the nbt value of the itemstack to either loaded or unloaded
     * @param stack - the itemstack to edit the nbt data of
     * @param loaded - whether or not the gun is loaded
     */
    public static void setLoaded(ItemStack stack, boolean loaded) {
        CompoundTag compoundTag = stack.getOrCreateTag();
        compoundTag.putBoolean("ammo_loaded", loaded);
    }

    /**
     * checks the itemstack, to see if it is loaded with a bullet.
     * @param stack - itemstack
     * @return - if the itemstack is loaded with a bullet
     */
    public static boolean isLoaded(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        return compoundTag != null && compoundTag.getBoolean("ammo_loaded");
    }
    /**
     * Gun can be fired and loaded even if someone is hitting an entity, like a horse.
     */
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        return use(user.getEntityWorld(), user, hand).getResult();

    }
    /**
     * Gun can be fired and loaded even when right clicking a block
    */
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        return use(context.getWorld(), context.getPlayer(), context.getHand()).getResult();
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return true;
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return null;
    }

    @Override
    public int getRange() {
        return 0;
    }

    public int getMaxUseTime(ItemStack stack) {
        return 72000;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }
}
