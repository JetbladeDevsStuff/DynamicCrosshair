package mod.crend.dynamiccrosshair.mixin.item;

import mod.crend.dynamiccrosshair.api.CrosshairContext;
import mod.crend.dynamiccrosshair.api.DynamicCrosshairItem;
import mod.crend.dynamiccrosshair.api.InteractionType;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SwordItem.class)
public class SwordItemMixin extends ToolItem implements DynamicCrosshairItem {
	public SwordItemMixin(ToolMaterial material, Settings settings) {
		super(material, settings);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		return InteractionType.MELEE_WEAPON;
	}
}
