package mod.crend.dynamiccrosshair.config.gui;

import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.debug.DebugProperties;
import dev.isxander.yacl3.gui.AbstractWidget;
import mod.crend.dynamiccrosshairapi.DynamicCrosshair;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class DrawCrosshairWidget extends AbstractWidget {
	boolean focused = false;
	boolean wasMouseOver = false;
	SelectCrosshairController control;

	public static final Identifier BACKGROUND = DynamicCrosshair.identifier("crosshair-background");

	public DrawCrosshairWidget(Dimension<Integer> dim, SelectCrosshairController control) {
		super(dim);
		this.control = control;
		control.init(false);
	}

	public boolean isMouseOverCanvas(int mouseX, int mouseY) {
		return super.isMouseOver(mouseX, mouseY);
	}

	private int getHoveredPixelI(int mouseX) {
		return ((mouseX - getDimension().x()) / 3);
	}
	private int getHoveredPixelJ(int mouseY) {
		return ((mouseY - getDimension().y()) / 3);
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		if (control.editStyle == null) return;
		int x = getDimension().x();
		int y = getDimension().y();
		context.getMatrices().push();
		context.getMatrices().translate(getDimension().x(), getDimension().y(), 105);
		context.getMatrices().scale(3, 3, 0);
		if (DebugProperties.IMAGE_FILTERING) {
			GlStateManager._texParameter(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MAG_FILTER, GlConst.GL_LINEAR);
			GlStateManager._texParameter(GlConst.GL_TEXTURE_2D, GlConst.GL_TEXTURE_MIN_FILTER, GlConst.GL_LINEAR);
		}
		context.drawGuiTexture(BACKGROUND, 0, 0, 15, 15);
		context.drawTexture(control.editStyle.identifier, 0, 0, 0, 0, 15, 15, 15, 15);
		context.getMatrices().pop();
		if (isMouseOverCanvas(mouseX, mouseY)) {
			if (!wasMouseOver) {
				wasMouseOver = true;
				GLFW.glfwSetInputMode(client.getWindow().getHandle(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_HIDDEN);
			}
			int hoveredPixelX = getHoveredPixelI(mouseX) * 3;
			int hoveredPixelY = getHoveredPixelJ(mouseY) * 3;
			context.fill(
					x + hoveredPixelX,
					y + hoveredPixelY,
					x + hoveredPixelX + 3,
					y + hoveredPixelY + 3,
					110,
					0xCCCCCCCC
			);
		} else if (wasMouseOver) {
			wasMouseOver = false;
			GLFW.glfwSetInputMode(client.getWindow().getHandle(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_NORMAL);
		}
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		if (isMouseOverCanvas((int) mouseX, (int) mouseY)
				&& (button == GLFW.GLFW_MOUSE_BUTTON_1 || button == GLFW.GLFW_MOUSE_BUTTON_2)
		) {
			int i = getHoveredPixelI((int) mouseX);
			int j = getHoveredPixelJ((int) mouseY);
			if (i < 0 || i >= 15 || j < 0 || j >= 15) return false;
			if (button == GLFW.GLFW_MOUSE_BUTTON_1) {
				control.editImage.setRGB(i, j, 0xFFFFFFFF);
			} else {
				control.editImage.setRGB(i, j, 0x00000000);
			}
			control.registerTexture();
			return true;
		}
		return false;
	}
	@Override
	public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
		if (isMouseOverCanvas((int) mouseX, (int) mouseY)
				&& (button == GLFW.GLFW_MOUSE_BUTTON_1 || button == GLFW.GLFW_MOUSE_BUTTON_2)
		) {

			/* Algorithm from https://en.wikipedia.org/wiki/Bresenham's_line_algorithm#All_cases */
			int x0 = getHoveredPixelI((int) mouseX);
			int y0 = getHoveredPixelJ((int) mouseY);
			int x1 = getHoveredPixelI((int) (mouseX + deltaX));
			int y1 = getHoveredPixelJ((int) (mouseY + deltaY));

			int dx = Math.abs(x1 - x0);
			int dy = Math.abs(y1 - y0);
			int sx = (x0 < x1) ? 1 : -1;
			int sy = (y0 < y1) ? 1 : -1;
			int error = dx + dy;

			int color = (button == GLFW.GLFW_MOUSE_BUTTON_1 ? 0xFFFFFFFF : 0x00000000);

			while (true) {
				if (x0 < 0 || x0 >= 15 || y0 < 0 || y0 >= 15) break;
				control.editImage.setRGB(x0, y0, color);

				if (x0 == x1 && y0 == y1) break;

				int e2 = 2 * error;

				if (e2 >= dy) {
					if (x0 == x1) break;
					error = error + dy;
					x0 = x0 + sx;
				}

				if (e2 <= dx) {
					if (y0 == y1) break;
					error = error + dx;
					y0 = y0 + sy;
				}
			}
			control.registerTexture();
			return true;
		}
		return false;
	}

	@Override
	public void setFocused(boolean focused) {
		this.focused = focused;
	}

	@Override
	public boolean isFocused() {
		return focused;
	}
}
