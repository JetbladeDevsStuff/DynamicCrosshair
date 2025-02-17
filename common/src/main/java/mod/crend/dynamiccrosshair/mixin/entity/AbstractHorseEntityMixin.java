package mod.crend.dynamiccrosshair.mixin.entity;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractHorseEntity.class)
public abstract class AbstractHorseEntityMixin extends AnimalEntityMixin implements DynamicCrosshairEntity {
	@Shadow public abstract boolean isTame();

	@Shadow public abstract boolean canBeSaddled();

	@Shadow public abstract boolean isSaddled();

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (!this.hasPassengers() && !this.isBaby()) {
			if (this.isTame() && context.getPlayer().shouldCancelInteraction()) {
				return InteractionType.INTERACT_WITH_ENTITY;
			} else {
				ItemStack itemStack = context.getItemStack();
				if (!itemStack.isEmpty()) {
					if (this.canBeSaddled() && !this.isSaddled() && itemStack.isOf(Items.SADDLE)) {
						return InteractionType.PLACE_ITEM_ON_ENTITY;
					}

					if (this.canUseSlot(EquipmentSlot.BODY) && this.isHorseArmor(itemStack) && !this.isWearingBodyArmor()) {
						return InteractionType.PLACE_ITEM_ON_ENTITY;
					}
				}

				return InteractionType.MOUNT_ENTITY;
			}
		}
		return super.dynamiccrosshair$compute(context);
	}
}
