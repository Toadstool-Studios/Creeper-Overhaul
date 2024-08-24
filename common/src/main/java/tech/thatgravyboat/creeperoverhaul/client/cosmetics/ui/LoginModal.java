package tech.thatgravyboat.creeperoverhaul.client.cosmetics.ui;

import com.teamresourceful.resourcefulconfig.api.client.ResourcefulConfigScreen;
import com.teamresourceful.resourcefulconfig.client.components.base.ContainerWidget;
import com.teamresourceful.resourcefulconfig.client.components.base.CustomButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.service.CosmeticsApi;

import java.util.concurrent.atomic.AtomicBoolean;

public class LoginModal extends ContainerWidget {

    private final StringWidget label;

    public LoginModal(int x, int y, int width, int height) {
        super(x, y, width, height);

        LinearLayout layout = LinearLayout.vertical().spacing(5);

        this.label = layout.addChild(new StringWidget(0, 12, Component.literal("Login"), Minecraft.getInstance().font))
                .alignCenter();

        AtomicBoolean loggingIn = new AtomicBoolean(false);

        layout.addChild(new CustomButton(
                Math.min(width, 100), 12,
                Component.literal("Login"),
                () -> {
                    if (loggingIn.get()) return;
                    loggingIn.set(true);
                    label.setMessage(Component.literal("Logging in..."));

                    CosmeticsApi.login().thenAcceptAsync(status -> {
                        switch (status) {
                            case OK -> {
                                label.setMessage(Component.literal("Getting available cosmetics..."));
                                CosmeticsApi.getCosmetics().thenAcceptAsync(cosmeticsStatus -> {
                                    switch (cosmeticsStatus) {
                                        case OK -> Minecraft.getInstance().tell(() -> {
                                            Screen screen = Minecraft.getInstance().screen;
                                            if (screen != null) {
                                                screen.onClose();
                                                ResourcefulConfigScreen.openModal(Component.empty(), CosmeticsModal::new);
                                            }
                                        });
                                        case UNAUTHORIZED -> label.setMessage(Component.literal("Failed to get cosmetics, try again later."));
                                        case INTERNAL_SERVER_ERROR -> label.setMessage(Component.literal("Error occurred on server side while getting cosmetics."));
                                        case UNKOWN_ERROR -> label.setMessage(Component.literal("Unknown error occurred while getting cosmetics."));
                                    }
                                });
                            }
                            case UNAUTHORIZED -> label.setMessage(Component.literal("Failed to login, check if you are logged in to Minecraft."));
                            case INTERNAL_SERVER_ERROR -> label.setMessage(Component.literal("Error occurred on server side while logging in."));
                            case UNKOWN_ERROR -> label.setMessage(Component.literal("Unknown error occurred while logging in."));
                        }
                    });
                })
        );

        layout.setPosition(x, y);
        layout.arrangeElements();
        FrameLayout.centerInRectangle(layout, x, y, width, height);
        this.label.setWidth(width);
        this.label.setX(x);
        layout.visitWidgets(this::addRenderableWidget);
    }
}
