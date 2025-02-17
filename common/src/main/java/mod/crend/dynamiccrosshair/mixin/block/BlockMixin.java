package mod.crend.dynamiccrosshair.mixin.block;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;
import mod.crend.dynamiccrosshairapi.type.DynamicCrosshairBlock;
import mod.crend.dynamiccrosshairapi.interaction.InteractionType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Block.class)
public abstract class BlockMixin extends AbstractBlock implements DynamicCrosshairBlock {

	public BlockMixin(Settings settings) {
		super(settings);
	}

	@Override
	public InteractionType dynamiccrosshair$compute(CrosshairContext context) {
		BlockState blockState = context.getBlockState();
		if (context.api().isAlwaysInteractable(blockState)) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}
		if (context.getPlayer().isCreative() && context.api().isAlwaysInteractableInCreativeMode(blockState)) {
			return InteractionType.INTERACT_WITH_BLOCK;
		}

		return InteractionType.EMPTY;
	}
}
