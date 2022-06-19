package ml.zer0dasho.config.format.json;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class CustomTypeAdapter implements JsonSerializer<ConfigurationSerializable>, JsonDeserializer<ConfigurationSerializable> {

	@Override
	public ConfigurationSerializable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		Map<String, Object> map = context.deserialize(json, new TypeToken<Map<String, Object>> () {}.getType());
		
        try {
            return ConfigurationSerialization.deserializeObject(map);
        } catch (IllegalArgumentException ex) {
            throw new RuntimeException("Could not deserialize object", ex);
        }
	}

	@Override
	public JsonElement serialize(ConfigurationSerializable src, Type typeOfSrc, JsonSerializationContext context) {
        Map<String, Object> values = new LinkedHashMap<String, Object>();
        
        values.put(ConfigurationSerialization.SERIALIZED_TYPE_KEY, ConfigurationSerialization.getAlias(src.getClass()));
        values.putAll(src.serialize());
		return context.serialize(values);
	}
}