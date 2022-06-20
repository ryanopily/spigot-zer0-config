## What is Config?

**Version**: 1.19
  
Config is a Bukkit library used for managing configuration files.

## How to use

Config works by populating a [JavaBean](https://stackoverflow.com/questions/3295496/what-is-a-javabean-exactly) with values from a configuration file.  
  
- Use Config.load(Class<? extends Config> type, File path, Formatter format, SafeSupplier<String> defaultResource) to populate a JavaBean.
- Once the JavaBean is populated, you can use javaBean.save() to save its current state to the configuration file.

### Example

```
/*
    world: "lobby",
    timer: 60,
    weapon:
        ==: org.bukkit.invenotry.ItemStack
        v: 3105
        type: STONE_SWORD
*/
public class ConfigSettings extends ml.zer0dasho.config.Config {

    private JavaPlugin plugin = /* Plugin instance here */;

    /* These can be set to null, if no default value should be provided */
    public String world = "lobby";
    public int timer = 60;
    public ItemStack weapon = new ItemStack(Material.STONE_SWORD);
    
    protected ConfigSettings {}
    
    @Override
    public void save() {
        super.save();
    }
    
    public static ConfigSettings getYAML() {
        return Config.load(
            ConfigSettings.class, 
            new File("plugins/Test/config.yml"), 
            ml.zer0dasho.config.format.yaml.YAMLFormat.FORMATTER, 
            () ->  ml.zer0dasho.config.format.yaml.YAMLFormat.FORMATTER.write(new ConfigSettings()) /* Default configuration from default instance */
        );
    }
    
    public static ConfigSettings getJSON() {
        return Config.load(
            ConfigSettings.class,
            new File("plugins/Test/config.json"),
             ml.zer0dasho.config.format.json.JSONFormat.FORMATTER,
            () -> new String(plugin.getResource("config.yml").readAllBytes()) /* Default configuration from jar resource */
        );
    }
}
```