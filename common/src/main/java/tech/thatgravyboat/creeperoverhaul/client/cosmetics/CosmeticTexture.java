package tech.thatgravyboat.creeperoverhaul.client.cosmetics;

import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.platform.TextureUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.teamresourceful.resourcefullib.common.utils.files.GlobalStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tech.thatgravyboat.creeperoverhaul.Creepers;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class CosmeticTexture {

    private static final String NAMESPACE = "creeperoverhaul_cosmetic";
    private static final Path CACHE = GlobalStorage.getCacheDirectory(Creepers.MODID)
            .resolve("cosmetics")
            .resolve("textures");

    private final String id;
    private final String texture;
    private final ResourceLocation location;
    private SimpleTexture img;

    public CosmeticTexture(String texture) {
        this.id = DownloadedAsset.getUrlHash(texture);
        this.texture = texture;
        this.location = ResourceLocation.fromNamespaceAndPath(
                NAMESPACE, "textures/cosmetics/" + this.id
        );
    }

    public ResourceLocation getResourceLocation() {
        checkOrDownload();
        return location;
    }

    public String getTexture() {
        return texture;
    }

    public void checkOrDownload() {
        if (this.img != null || this.texture == null) return;

        this.img = new CosmeticTexture.DownloadableTexture(CACHE.resolve(this.id).toFile(), this.texture, this.location);
        Minecraft.getInstance().getTextureManager().register(this.location, this.img);
    }

    public static class DownloadableTexture extends SimpleTexture {

        @Nullable
        private final File file;
        @Nullable
        private final String url;
        @Nullable
        private CompletableFuture<Void> future;
        private boolean uploaded;

        public DownloadableTexture(@Nullable File file, @Nullable String url, ResourceLocation location) {
            super(location);
            this.file = file;
            this.url = url;
        }

        private void loadCallback(NativeImage image) {
            Minecraft.getInstance().execute(() -> {
                this.uploaded = true;
                if (!RenderSystem.isOnRenderThread()) {
                    RenderSystem.recordRenderCall(() -> this.upload(image));
                } else {
                    this.upload(image);
                }
            });
        }

        private void upload(NativeImage image) {
            TextureUtil.prepareImage(this.getId(), image.getWidth(), image.getHeight());
            image.upload(0, 0, 0, true);
        }

        @Override
        public void load(@NotNull ResourceManager manager) {
            Minecraft.getInstance().execute(() -> {
                if (!this.uploaded) {
                    try {
                        super.load(manager);
                    } catch (Exception ignored) {
                        /*Do Nothing*/
                    }
                    this.uploaded = true;
                }
            });
            if (this.future == null) {
                Optional<NativeImage> nativeimage = this.file != null && this.file.isFile() ?
                        this.load(this.file) : Optional.empty();

                if (nativeimage.isPresent()) {
                    this.loadCallback(nativeimage.get());
                } else {
                    this.future = DownloadedAsset.runDownload(
                            this.url,
                            this.file,
                            stream -> this.load(this.file).ifPresent(this::loadCallback)
                    );
                }
            }
        }

        private Optional<NativeImage> load(File file) {
            try {
                return Optional.of(NativeImage.read(new FileInputStream(file)));
            } catch (Exception ignored) {
                return Optional.empty();
            }
        }

    }
}
