# Config
## What is config?
Config is a library used for managing configuration files in Spigot plugins.  

## Version
This plugin is made for Spigot 1.19  

## How to use

To use in your plugin, create a Java Bean that represents the configuration you want, and extends ml.zer0dasho.config.Config;
To load from a file, use Config.load();
To save, you can use javaBean.save();

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