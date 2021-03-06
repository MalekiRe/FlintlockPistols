package flintlockweapons.flintlockweapons.entities;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import flintlockweapons.flintlockweapons.Flintlockweapons;
import flintlockweapons.flintlockweapons.items.ItemInitializer;
import flintlockweapons.flintlockweapons.items.MusketBallItem;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

public class MusketBallEntity extends PersistentProjectileEntity {

    float damage = 8;
    private SoundEvent mySound = SoundEvents.BLOCK_GRAVEL_HIT;

    public MusketBallEntity(EntityType<? extends MusketBallEntity > entityType, World world) {
            super(entityType, world);
        }

   public MusketBallEntity(World world, double x, double y, double z) {
            super(Flintlockweapons.MUSKET_BALL, x, y, z, world);
        }

   public MusketBallEntity(World world, LivingEntity owner) {
            super(Flintlockweapons.MUSKET_BALL, owner, world);
        }

        public MusketBallEntity(World world) {
            super(Flintlockweapons.MUSKET_BALL, world);
        }

    @Override
    public Packet<?> createSpawnPacket()
    {
        return EntityPacketUtils.createPacket(this);
    }


    protected void initDataTracker() {
            super.initDataTracker();
        }

        public void tick() {
            super.tick();
            if (this.world.isClient) {
                if (this.inGround) {
                    if (this.inGroundTime % 5 == 0) {
                        this.spawnParticles(1);
                    }
                } else {
                    this.spawnParticles(2);
                }
            } else if (this.inGround && this.inGroundTime != 0 && this.inGroundTime >= 600) {
                this.world.sendEntityStatus(this, (byte)0);

            }

        }

        private void spawnParticles(int i) {


        }



        public void writeCustomDataToTag(CompoundTag tag) {
            super.writeCustomDataToTag(tag);

        }

        public void readCustomDataFromTag(CompoundTag tag) {
            super.readCustomDataFromTag(tag);


        }

        protected void onHit(LivingEntity target) {
            super.onHit(target);

        }

        protected ItemStack asItemStack() {

                return new ItemStack(ItemInitializer.musketBallItem);

        }
        @Override
        protected SoundEvent getHitSound() {
        return SoundEvents.BLOCK_GRAVEL_HIT;
    }






        @Environment(EnvType.CLIENT)
        public void handleStatus(byte status) {

                super.handleStatus(status);


        }
        @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        Entity entity = entityHitResult.getEntity();
        float f = (float)this.getVelocity().length();
        int i = MathHelper.ceil(MathHelper.clamp(this.damage, 0.0D, 2.147483647E9D));


        if (this.isCritical()) {
            long l = (long)this.random.nextInt(i / 2 + 2);
            i = (int)Math.min(l + (long)i, 2147483647L);
        }

        Entity entity2 = this.getOwner();
        DamageSource damageSource2;
        if (entity2 == null) {
            damageSource2 = DamageSource.arrow(this, this);
        } else {
            damageSource2 = DamageSource.arrow(this, entity2);
            if (entity2 instanceof LivingEntity) {
                ((LivingEntity)entity2).onAttacking(entity);
            }
        }

        boolean bl = entity.getType() == EntityType.ENDERMAN;
        int j = entity.getFireTicks();
        if (this.isOnFire() && !bl) {
            entity.setOnFireFor(5);
        }

        if (entity.damage(damageSource2, (float)i)) {
            if (bl) {
                return;
            }

            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)entity;
                if (!this.world.isClient && this.getPierceLevel() <= 0) {
                    livingEntity.setStuckArrowCount(livingEntity.getStuckArrowCount() + 1);
                }



                if (!this.world.isClient && entity2 instanceof LivingEntity) {
                    EnchantmentHelper.onUserDamaged(livingEntity, entity2);
                    EnchantmentHelper.onTargetDamaged((LivingEntity)entity2, livingEntity);
                }

                this.onHit(livingEntity);
                if (entity2 != null && livingEntity != entity2 && livingEntity instanceof PlayerEntity && entity2 instanceof ServerPlayerEntity && !this.isSilent()) {
                    ((ServerPlayerEntity)entity2).networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, 0.0F));
                }



                if (!this.world.isClient && entity2 instanceof ServerPlayerEntity) {
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity2;
                    if (!entity.isAlive() && this.isShotFromCrossbow()) {
                        Criteria.KILLED_BY_CROSSBOW.trigger(serverPlayerEntity, Arrays.asList(entity));
                    }
                }
            }

            this.playSound(this.mySound, 1.0F, 1.2F / (this.random.nextFloat() * 0.2F + 0.9F));
            if (this.getPierceLevel() <= 0) {
                this.remove();
            }
        } else {
            entity.setFireTicks(j);
            this.setVelocity(this.getVelocity().multiply(-0.1D));
            this.yaw += 180.0F;
            this.prevYaw += 180.0F;
            if (!this.world.isClient && this.getVelocity().lengthSquared() < 1.0E-7D) {
                if (this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
                    this.dropStack(this.asItemStack(), 0.1F);
                }

                this.remove();
            }
        }

    }


}
