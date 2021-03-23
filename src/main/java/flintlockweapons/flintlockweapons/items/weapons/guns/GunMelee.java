package flintlockweapons.flintlockweapons.items.weapons.guns;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import flintlockweapons.flintlockweapons.items.ammo.AmmoItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Identifier;

import java.util.function.Consumer;

public class GunMelee extends GunItem{
    private final float attackDamage;
    private final float attackSpeed;
    private final ToolMaterial material;
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    public GunMelee(Settings settings, AmmoItem[] ammo, int loadTime, int bulletAmount, float bulletSpread, float range, float damage, float damageVariance, Identifier[] fireSoundNames, Identifier[] hitSoundNames, Identifier[] loadSoundNames, float attackDamage, float attackSpeed, ToolMaterial material) {
        super(settings, ammo, loadTime, bulletAmount, bulletSpread, range, damage, damageVariance, fireSoundNames, hitSoundNames, loadSoundNames);
        this.attackDamage = attackDamage;
        this.attackSpeed = attackSpeed;
        this.material = material;
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double)this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double)attackSpeed, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }


}
