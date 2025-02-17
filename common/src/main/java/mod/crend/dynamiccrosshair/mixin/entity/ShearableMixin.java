package mod.crend.dynamiccrosshair.mixin.entity;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairEntity;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.mob.BoggedEntity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({BoggedEntity.class, MooshroomEntity.class, SheepEntity.class, SnowGolemEntity.class})
public abstract class ShearableMixin extends MobEntityMixin implements DynamicCrosshairEntity, Shearable {

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		if (isShearable()) {
			return InteractionType.USE_ITEM_ON_ENTITY;
		}
		return super.dynamiccrosshair$compute(context);
	}
}
