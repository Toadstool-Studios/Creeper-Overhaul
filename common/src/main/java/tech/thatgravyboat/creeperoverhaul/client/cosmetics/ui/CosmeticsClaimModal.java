package tech.thatgravyboat.creeperoverhaul.client.cosmetics.ui;

import com.teamresourceful.resourcefulconfig.client.components.base.ContainerWidget;
import com.teamresourceful.resourcefulconfig.client.components.base.CustomButton;
import com.teamresourceful.resourcefulconfig.client.components.options.types.StringOptionWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.MultiLineTextWidget;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import tech.thatgravyboat.creeperoverhaul.client.cosmetics.service.CosmeticsApi;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class CosmeticsClaimModal extends ContainerWidget {

    public CosmeticsClaimModal(int x, int y, int width, int height) {
        super(x, y, width, height);

        LinearLayout layout = LinearLayout.vertical().spacing(5);

        Runnable centerLayout = () -> layout.visitChildren(element -> element.setX(x + (width - element.getWidth()) / 2));

        layout.addChild(new MultiLineTextWidget(Component.literal("""
        Enter the code you received,
        into the text box below, to claim your cosmetic.
        """), Minecraft.getInstance().font).setMaxWidth(width).setCentered(true));
        var label = layout.addChild(new MultiLineTextWidget(Component.literal("\n"), Minecraft.getInstance().font)
                .setMaxWidth(width)
                .setCentered(true));

        AtomicReference<String> input = new AtomicReference<>("");

        layout.addChild(new StringOptionWidget(input::get, s -> {
            input.set(s);
            return true;
        }, false));

        layout.addChild(new CustomButton(76, 12, Component.literal("Claim"), () ->
            claimReward(input.get(), message -> {
                label.setMessage(Component.literal(message));
                centerLayout.run();
            })
        ));

        layout.setPosition(x, y);
        layout.arrangeElements();
        layout.visitWidgets(this::addRenderableWidget);

        centerLayout.run();
    }

    public void claimReward(String code, Consumer<String> messageSetter) {
        CosmeticsApi.claimReward(code)
                .thenAcceptAsync(status -> {
                    switch (status) {
                        case OK -> Minecraft.getInstance().tell(() -> {
                            Screen screen = Minecraft.getInstance().screen;
                            if (screen != null) {
                                screen.onClose();
                            }
                        });
                        case FORBIDDEN -> messageSetter.accept("§cCode claimed already!");
                        case NOT_FOUND -> messageSetter.accept("§cInvalid code!");
                        case UNKOWN_ERROR -> messageSetter.accept("§cClient error!");
                        default -> messageSetter.accept("§cUnknown error!");
                    }
                });
    }
}
