package tech.thatgravyboat.creeperoverhaul.client.cosmetics;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.teamresourceful.resourcefullib.common.lib.Constants;
import com.teamresourceful.resourcefullib.common.utils.files.GlobalStorage;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.loading.json.raw.Model;
import software.bernie.geckolib.loading.json.typeadapter.KeyFramesAdapter;
import software.bernie.geckolib.loading.object.BakedModelFactory;
import software.bernie.geckolib.loading.object.GeometryTree;
import tech.thatgravyboat.creeperoverhaul.Creepers;

import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.file.Path;

public final class CosmeticGeoModel {

    private static final Path CACHE = GlobalStorage.getCacheDirectory(Creepers.MODID)
            .resolve("cosmetics")
            .resolve("models");

    private final String url;
    private boolean isLoading = false;
    private BakedGeoModel model;

    public CosmeticGeoModel(String url) {
        this.url = url;
    }

    private void setFromJson(JsonElement json) {
        this.model = BakedModelFactory.getForNamespace(Creepers.MODID)
                .constructGeoModel(GeometryTree.fromModel(
                        KeyFramesAdapter.GEO_GSON.fromJson(json, Model.class)
                ));
    }

    public BakedGeoModel get() {
        if (!this.isLoading) {
            this.isLoading = true;
            try {
                File file = CACHE.resolve(DownloadedAsset.getUrlHash(url)).toFile();
                if (file.exists() && file.isFile()) {
                    this.setFromJson(Constants.GSON.fromJson(new JsonReader(new FileReader(file)), JsonObject.class));
                } else {
                    DownloadedAsset.runDownload(
                            url, file,
                            stream -> this.setFromJson(
                                    Constants.GSON.fromJson(new JsonReader(new InputStreamReader(stream)), JsonObject.class)
                            )
                    );
                }
            }catch (Exception e) {
                return null;
            }
        }
        return this.model;
    }

    public boolean isLoaded() {
        return this.model != null;
    }
}
