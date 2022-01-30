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

    public ResourceLocationPredicate(@Nonnull ResourceLocation requiredLocation) {
        this.location = requiredLocation;
    }

    public static ResourceLocationPredicate of(ResourceLocation location) {
        return new ResourceLocationPredicate(location);
    }

    public static ResourceLocationPredicate fromJson(JsonElement element) {
        if (!element.isJsonNull()) {
            JsonObject object = GsonHelper.convertToJsonObject(element, "location");
            ResourceLocation loc;
            if (object.has("id")) {
                loc = new ResourceLocation(GsonHelper.getAsString(object, "id"));
            } else {
                //THIS RETURNS NULL ???
                AdvancementPlus.LOG.info(object.get("id"));

                AdvancementPlus.LOG.info("JsonElement with member 'id' is missing");
                throw new NullPointerException("line 52");
            }

            return new ResourceLocationPredicate(loc);
        }

        throw new NullPointerException("Line 58");
    }

    public boolean matches(ResourceLocation location) {
        return matchesUpdate(location);
    }

    //FOR TEST ONLY
    private boolean matchesUpdate(ResourceLocation location) {
        final boolean flag = location == this.location;
        AdvancementPlus.LOG.info("Given location [{}] - Required Location [{}]", location, this.location);
        AdvancementPlus.LOG.info("Location matches: " + flag);
        return flag;
    }

    public JsonElement toJson() {
        JsonObject object = new JsonObject();
        object.addProperty("id", location.toString());
        return object;
    }
}
