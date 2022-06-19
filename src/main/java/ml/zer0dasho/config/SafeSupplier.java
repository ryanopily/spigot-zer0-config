package ml.zer0dasho.config;

public interface SafeSupplier<T> {

	T get() throws Exception;
	
}
