package tech.thatgravyboat.creeperoverhaul.client.cosmetics.ui;

import com.teamresourceful.resourcefulconfig.client.components.base.ContainerWidget;
import com.teamresourceful.resourcefulconfig.client.components.base.SpriteButton;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import tech.thatgravyboat.creeperoverhaul.Creepers;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.Cosmetic;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.service.CosmeticsApi;

import java.util.List;

public class CosmeticGridWidget extends ContainerWidget {

    private static final ResourceLocation NONE = ResourceLocation.fromNamespaceAndPath(Creepers.MODID, "none");

    private final GridLayout layout;
    private float scrollAmount = 0;

    public CosmeticGridWidget(int width, int height, List<Cosmetic> cosmetics) {
        super(0, 0, width, height);

        this.layout = new GridLayout().spacing(5);
        var rowHelper = this.layout.createRowHelper(Math.floorDiv(width, 35));
        rowHelper.addChild(SpriteButton.builder(35, 35).
                tooltip(Component.literal("None"))
                .sprite(NONE)
                .onPress(() -> CosmeticsApi.setCosmetic(null))
                .build()
        );
        for (Cosmetic cosmetic : cosmetics) {
            rowHelper.addChild(new CosmeticButton(cosmetic));
        }

        this.layout.arrangeElements();
        this.layout.visitWidgets(this::addRenderableWidget);
    }

    @Override
    protected void positionUpdated() {
        this.layout.setPosition(this.getX(), (int) (this.getY() - this.scrollAmount));
        this.layout.arrangeElements();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalScroll, double verticalScroll) {
        this.scrollAmount += (float) (verticalScroll * 10f);
        this.scrollAmount = Mth.clamp(this.scrollAmount, 0, Math.max(0, this.layout.getHeight() - this.getHeight()));
        this.layout.setPosition(this.getX(), (int) (this.getY() - this.scrollAmount));
        this.layout.arrangeElements();
        return true;
    }
}
