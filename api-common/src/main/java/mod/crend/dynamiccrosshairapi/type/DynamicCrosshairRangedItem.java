package mod.crend.dynamiccrosshairapi.type;

import mod.crend.dynamiccrosshairapi.crosshair.CrosshairContext;

public interface DynamicCrosshairRangedItem {
	/**
	 * This is used to mark ranged weapons as "being charged".
	 * @return true if being charged
	 */
	boolean dynamiccrosshair$isCharging(CrosshairContext context);

	/**
	 * This is used to mark ranged weapons as "fully charged".
	 * @return true if fully charged
	 */
	boolean dynamiccrosshair$isCharged(CrosshairContext context);
}
