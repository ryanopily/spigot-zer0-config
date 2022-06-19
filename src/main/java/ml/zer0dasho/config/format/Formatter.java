package ml.zer0dasho.config.format;

public interface Formatter {

	<T> String write(T object);
	<T> T read(String raw, Class<T> type);
	
}