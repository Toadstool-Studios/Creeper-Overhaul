package tech.thatgravyboat.creeperoverhaul.client.cosmetics.ui;

import com.teamresourceful.resourcefulconfig.api.client.ResourcefulConfigScreen;
import net.minecraft.network.chat.Component;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.service.CosmeticsApi;

public class CosmeticsUI {

    public static final Component COSMETICS = Component.translatable("gui.creeperoverhaul.cosmetics");
    public static final Component LOGIN = Component.translatable("gui.creeperoverhaul.cosmetics.login");
    public static final Component PREVIEW_IN_GAME = Component.translatable("gui.creeperoverhaul.cosmetics.preview_in_game");

    public static void open() {
        if (CosmeticsApi.hasSessionToken()) {
            ResourcefulConfigScreen.openModal(CosmeticsUI.COSMETICS, CosmeticsModal::new);
        } else {
            ResourcefulConfigScreen.openModal(CosmeticsUI.LOGIN, LoginModal::new);
        }
    }
}
