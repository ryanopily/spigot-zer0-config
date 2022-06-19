package ml.zer0dasho.config.format.json;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ml.zer0dasho.config.format.Formatter;

public class JSONFormat implements Formatter {

	public static final JSONFormat FORMATTER = new JSONFormat();
	
	private static final Gson CUSTOM_GSON = new GsonBuilder()
			.registerTypeHierarchyAdapter(ConfigurationSerializable.class, new CustomTypeAdapter())
			.setPrettyPrinting()
			.disableHtmlEscaping()
			.create();
	
	protected JSONFormat() {}
	
	@Override
	public <T> String write(T object) {
		return CUSTOM_GSON.toJson(object);
	}

	@Override
	public <T> T read(String raw, Class<T> type) {
		return CUSTOM_GSON.fromJson(raw, type);
	}
}