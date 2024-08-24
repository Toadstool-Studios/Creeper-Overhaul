package tech.thatgravyboat.creeperoverhaul.client.cosmetics.ui;

import com.teamresourceful.resourcefulconfig.api.client.ResourcefulConfigScreen;
import com.teamresourceful.resourcefulconfig.client.components.base.ContainerWidget;
import com.teamresourceful.resourcefulconfig.client.components.base.CustomButton;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.network.chat.Component;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.Cosmetic;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.service.CosmeticsApi;

import java.util.ArrayList;
import java.util.List;

public class CosmeticsModal extends ContainerWidget {

    public CosmeticsModal(int x, int y, int width, int height) {
        super(x, y, width, height);

        LinearLayout layout = LinearLayout.horizontal().spacing(5);

        int previewWidth = width / 4;

        List<Cosmetic> cosmetics = new ArrayList<>();
        for (String s : CosmeticsApi.getAvailableCosmetics()) {
            Cosmetic cosmetic = CosmeticsApi.getCosmetic(s);
            if (cosmetic == null) continue;
            cosmetics.add(cosmetic);
        }

        CosmeticGridWidget grid = new CosmeticGridWidget(width - previewWidth - 5, height, cosmetics);

        LinearLayout sidebar = LinearLayout.vertical().spacing(5);

        sidebar.addChild(new CosmeticPreview(previewWidth, height - 25));
        sidebar.addChild(new CustomButton(previewWidth, 20, Component.literal("Claim Code"), () -> {
            ResourcefulConfigScreen.openModal(Component.empty(), CosmeticsClaimModal::new);
        }));

        layout.addChild(sidebar);
        layout.addChild(grid);

        layout.setPosition(x, y);
        layout.arrangeElements();
        layout.visitWidgets(this::addRenderableWidget);
    }


}
