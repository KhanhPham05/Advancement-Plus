package com.khanhpham.advancementplus.triggers;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.khanhpham.advancementplus.AdvancementPlus;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;

import javax.annotation.Nonnull;

/**
 * @see LocationPredicate
 */
public class ResourceLocationPredicate {
    private final ResourceLocation location;
    public static final String REQ_ID = "required_location";
    public static final String LOCATION = "resource_location";

    public ResourceLocationPredicate(@Nonnull ResourceLocation requiredLocation) {
        this.location = requiredLocation;
    }

    public static ResourceLocationPredicate of(ResourceLocation location) {
        return new ResourceLocationPredicate(location);
    }

    /**
     * @see LocationPredicate
     */
    public static ResourceLocationPredicate fromJson(JsonElement element) {
        if (!element.isJsonNull()) {
            JsonObject object = GsonHelper.convertToJsonObject(element, LOCATION);
            JsonObject object1 = (JsonObject) object.get(LOCATION);
            ResourceLocation loc;
            if (object1.has(REQ_ID)) {
                loc = new ResourceLocation(GsonHelper.getAsString(object1, REQ_ID));
            } else {
                throw new NullPointerException();
            }

            return new ResourceLocationPredicate(loc);
        }

        throw new NullPointerException();
    }

    public boolean matches(ResourceLocation location) {
        return matchesUpdate(location);
    }

    //FOR TEST ONLY
    private boolean matchesUpdate(ResourceLocation location) {
        final boolean flag = location.toString().equals(this.location.toString());
        /*AdvancementPlus.LOG.info("Given location [{}] - Required Location [{}]", location, this.location);
        AdvancementPlus.LOG.info("Location matches: " + flag);*/
        return flag;
    }

    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty(REQ_ID, location.toString());
        return object;
    }
}
