package tech.thatgravyboat.creeperoverhaul.client.cosmetics;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import tech.thatgravyboat.creeperoverhaul.Creepers;

public class CosmeticModel extends GeoModel<Cosmetic> {

    private final CosmeticGeoModel model;
    private final CosmeticTexture texture;

    private boolean loaded = false;

    public CosmeticModel(CosmeticGeoModel model, CosmeticTexture texture) {
        this.model = model;
        this.texture = texture;
    }

    @Override
    public BakedGeoModel getBakedModel(ResourceLocation location) {
        if (!this.loaded && this.model.isLoaded()) {
            this.getAnimationProcessor().setActiveModel(this.model.get());
            this.loaded = true;
        }
        return this.model.get();
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

    public boolean isLoaded() {
        return this.model.isLoaded();
    }
}
