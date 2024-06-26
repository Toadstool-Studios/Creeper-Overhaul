package tech.thatgravyboat.creeperoverhaul.client.cosmetics;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.teamresourceful.resourcefullib.common.lib.Constants;
import com.teamresourceful.resourcefullib.common.utils.files.GlobalStorage;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.loading.json.typeadapter.KeyFramesAdapter;
import software.bernie.geckolib.loading.object.BakedModelFactory;
import software.bernie.geckolib.loading.object.GeometryTree;
import software.bernie.geckolib.model.GeoModel;
import tech.thatgravyboat.creeperoverhaul.Creepers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.util.function.Consumer;

public class CosmeticModel extends GeoModel<Cosmetic> {

    private static final Path CACHE = GlobalStorage.getCacheDirectory(Creepers.MODID)
            .resolve("cosmetics")
            .resolve("models");

    private final BakedGeoModel model;
    private final CosmeticTexture texture;

    private boolean loaded = false;

    private CosmeticModel(JsonElement model, CosmeticTexture texture) {
        this.model = BakedModelFactory.getForNamespace(Creepers.MODID)
                .constructGeoModel(GeometryTree.fromModel(
                        KeyFramesAdapter.GEO_GSON.fromJson(model, Model.class)
                ));
        this.texture = texture;
    }

    public static void create(String url, CosmeticTexture texture, Consumer<CosmeticModel> factory) throws FileNotFoundException {
        File file = CACHE.resolve(DownloadedAsset.getUrlHash(url)).toFile();
        if (file.exists() && file.isFile()) {
            JsonObject json = Constants.GSON.fromJson(new JsonReader(new FileReader(file)), JsonObject.class);
            factory.accept(new CosmeticModel(json, texture));
        } else {
            DownloadedAsset.runDownload(url, file, stream -> {
                JsonObject json = Constants.GSON.fromJson(new JsonReader(new InputStreamReader(stream)), JsonObject.class);
                factory.accept(new CosmeticModel(json, texture));
            });
        }
    }

    @Override
    public BakedGeoModel getBakedModel(ResourceLocation location) {
        if (!this.loaded) {
            this.getAnimationProcessor().setActiveModel(this.model);
            this.loaded = true;
        }
        return this.model;
    }

    @Override
    public ResourceLocation getModelResource(Cosmetic animatable) {
        return ResourceLocation.fromNamespaceAndPath(Creepers.MODID, "geo/cosmetic.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(Cosmetic animatable) {
        return this.texture.getResourceLocation();
    }

    @Override
    public ResourceLocation getAnimationResource(Cosmetic animatable) {
        return ResourceLocation.fromNamespaceAndPath(Creepers.MODID, "animations/empty.animation.json");
    }
}
