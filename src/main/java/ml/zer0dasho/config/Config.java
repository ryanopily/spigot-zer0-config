package ml.zer0dasho.config;

import java.io.File;

import org.apache.commons.io.FileUtils;

import ml.zer0dasho.config.format.Formatter;

public abstract class Config {

	transient File file;
	transient Formatter formatter;
	transient SafeSupplier<String> defaultResource;
	
	/**
	 * Saves the current state to the config file.
	 */
	public void save() {
		try {
			this.createIfNotExists();
			FileUtils.write(file, formatter.write(this), "UTF-8");
		} 
		catch(Exception ex) {
			System.err.println("Failed to save config...");
			System.err.println(ex.getMessage());
		}
	}
	
	void createIfNotExists() throws Exception {
		if(!file.exists()) {
			file.getParentFile().mkdirs(); 
			file.createNewFile();
			FileUtils.writeStringToFile(file, defaultResource.get(), "UTF-8");
		}
	}
	
	/**
	 * Loads the Config object with the provided parameters.
	 * 
	 * @param <T>
	 * @param config - Config class
	 * @param file - Config file
	 * @param formatter - Config format
	 * @return
	 */
	public static <T extends Config> T load(Class<T> config, File file, Formatter formatter) {
		return Config.load(config, file, formatter, null);
	}

	/**
	 * Loads the Config object with the provided parameters.
	 * 
	 * @param <T>
	 * @param config - Config class
	 * @param file - Config file
	 * @param formatter - Config format
	 * @param defaultResource - Default config file contents.
	 * @return
	 */
	public static <T extends Config> T load(Class<T> config, File file, Formatter formatter, SafeSupplier<String> defaultResource) {
		T result = null;
		
		try {
			if(!file.exists()) {
				file.getParentFile().mkdirs();
				file.createNewFile();
				FileUtils.writeStringToFile(file, defaultResource.get(), "UTF-8");
			}
			
			result = formatter.read(FileUtils.readFileToString(file, "UTF-8"), config);
			result.file = file;
			result.formatter = formatter;
			result.defaultResource = defaultResource;
		}
		catch(Exception ex) {	
			System.err.println("Failed to load config...");
			System.err.println(ex.getMessage());
		}
		
		return result;
	}
}