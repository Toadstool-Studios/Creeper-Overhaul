package tech.thatgravyboat.creeperoverhaul.client.cosmetics;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import org.joml.Vector3f;

public record CosmeticTransformation(
        Vector3f scale,
        Vector3f translation,
        Vector3f rotation
) {

    private static final CosmeticTransformation DEFAULT = new CosmeticTransformation(new Vector3f(1, 1, 1), new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));

    public static CosmeticTransformation fromJson(JsonObject object) {
        if (object == null) return DEFAULT;
        JsonArray scale = object.getAsJsonArray("scale");
        Vector3f scaleVec = scale != null && scale.size() == 3
                ? new Vector3f(scale.get(0).getAsFloat(), scale.get(1).getAsFloat(), scale.get(2).getAsFloat())
                : new Vector3f(1, 1, 1);
        JsonArray translation = object.getAsJsonArray("translation");
        Vector3f translationVec = translation != null && translation.size() == 3
                ? new Vector3f(translation.get(0).getAsFloat(), translation.get(1).getAsFloat(), translation.get(2).getAsFloat())
                : new Vector3f(0, 0, 0);
        JsonArray rotation = object.getAsJsonArray("rotation");
        Vector3f rotationVec = rotation != null && rotation.size() == 3
                ? new Vector3f(rotation.get(0).getAsFloat(), rotation.get(1).getAsFloat(), rotation.get(2).getAsFloat())
                : new Vector3f(0, 0, 0);

        return new CosmeticTransformation(scaleVec, translationVec, rotationVec);
    }

    public void applyScale(PoseStack stack) {
        stack.scale(scale.x(), scale.y(), scale.z());
    }

    public void applyTranslation(PoseStack stack) {
        stack.translate(translation.x(), translation.y(), translation.z());
    }

    public void applyRotation(PoseStack stack) {
        stack.mulPose(Axis.XP.rotationDegrees(rotation.x()));
        stack.mulPose(Axis.YP.rotationDegrees(rotation.y()));
        stack.mulPose(Axis.ZP.rotationDegrees(rotation.z()));
    }
}
