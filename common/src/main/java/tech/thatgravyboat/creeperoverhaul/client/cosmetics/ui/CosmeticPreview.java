package tech.thatgravyboat.creeperoverhaul.client.cosmetics.ui;

import com.teamresourceful.resourcefullib.client.utils.ScreenUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class CosmeticPreview extends AbstractWidget {

    private final Player player;
    private float rotation = Mth.PI / 4;

    public CosmeticPreview(int k, int l) {
        super(0, 0, k, l, CommonComponents.EMPTY);

        this.player = Minecraft.getInstance().player;
    }

    @Override
    protected void renderWidget(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (this.player != null) {
            Quaternionf quaternion = new Quaternionf().rotateZ(Mth.PI).rotateY(this.rotation);
            float yHeadRot = this.player.yBodyRot;
            float yRot = this.player.getYRot();
            float xRot = this.player.getXRot();
            float yHeadRotO = this.player.yHeadRotO;
            float yBodyRot = this.player.yHeadRot;
            this.player.yBodyRot = 180.0F;
            this.player.setYRot(180.0F);
            this.player.setXRot(0f);
            this.player.yHeadRot = this.player.getYRot();
            this.player.yHeadRotO = this.player.getYRot();
            InventoryScreen.renderEntityInInventory(
                    graphics,
                    getX() + getWidth() / 2f, getY() + getHeight(),
                    50, new Vector3f(), quaternion, null, this.player
            );
            this.player.yBodyRot = yHeadRot;
            this.player.setYRot(yRot);
            this.player.setXRot(xRot);
            this.player.yHeadRot = yHeadRotO;
            this.player.yHeadRotO = yBodyRot;
        } else if (this.isHovered) {
            ScreenUtils.setTooltip(CosmeticsUI.PREVIEW_IN_GAME);
        }
    }

    @Override
    public boolean mouseDragged(double d, double e, int i, double f, double g) {
        this.rotation += (float) f * 0.15F;
        return true;
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {

    }
}
