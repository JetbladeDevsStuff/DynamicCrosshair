package mod.crend.dynamiccrosshairapi.registry;

import mod.crend.dynamiccrosshairapi.DynamicCrosshair;
import net.minecraft.util.Identifier;

public class DynamicCrosshairStyles {
	public static final Identifier DEFAULT = Identifier.ofVanilla("hud/crosshair");
	public static final Identifier CROSS_OPEN = of("cross-open");
	public static final Identifier CROSS_OPEN_DIAGONAL = of("cross-open-diagonal");
	public static final Identifier CIRCLE = of("circle");
	public static final Identifier CIRCLE_LARGE = of("circle-large");
	public static final Identifier SQUARE = of("square");
	public static final Identifier SQUARE_LARGE = of("square-large");
	public static final Identifier DIAMOND = of("diamond");
	public static final Identifier DIAMOND_LARGE = of("diamond-large");
	public static final Identifier CARET = of("caret");
	public static final Identifier DOT = of("dot");
	public static final Identifier CROSS_DIAGONAL_SMALL = of("cross-diagonal-small");
	public static final Identifier BRACKETS = of("brackets");
	public static final Identifier BRACKETS_BOTTOM = of("brackets-bottom");
	public static final Identifier BRACKETS_TOP = of("brackets-top");
	public static final Identifier BRACKETS_ROUND = of("brackets-round");
	public static final Identifier LINES = of("lines");
	public static final Identifier LINE_BOTTOM = of("line-bottom");

	public static Identifier of(String path) {
		return Identifier.of(DynamicCrosshair.MOD_ID, "crosshair/" + path);
	}
}
