package ml.zer0dasho.config.format.yaml;

import org.bukkit.configuration.file.YamlRepresenter;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import ml.zer0dasho.config.format.Formatter;

public class YAMLFormat implements Formatter {
	
	public static final YAMLFormat FORMATTER = new YAMLFormat();
	
	private static final Yaml CUSTOM_YAML = new Yaml(new CustomConstructor(), new YamlRepresenter()) {
		{
			Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
	
			this.dumperOptions = new DumperOptions() {
				{
				    setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
				    setPrettyFlow(true);
				    setIndent(4);
				}
			};
		}
	};
	
	protected YAMLFormat() {}
	
	@Override
	public <T> String write(T object) {
		return CUSTOM_YAML.dumpAsMap(object);
	}

	@Override
	public <T> T read(String raw, Class<T> type) {
		return CUSTOM_YAML.loadAs(raw, type);
	}
}