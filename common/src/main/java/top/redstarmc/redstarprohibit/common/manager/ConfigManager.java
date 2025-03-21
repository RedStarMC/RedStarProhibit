package top.redstarmc.redstarprohibit.common.manager;

import org.yaml.snakeyaml.Yaml;
import top.redstarmc.redstarprohibit.common.RedStarProhibit;
import top.redstarmc.redstarprohibit.common.api.MapBuilder;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ConfigManager {

    public static final String PLUGIN_NAME = "RedStarProhibit";

    public static final Map<String, Object> default_config = MapBuilder.of(String.class,Object.class)
            .set("Debug", false)
            .set("PluginPrefix", "§b§l[RedStarProhibit]")
            .set("","")

            .toMap();

    private static final File config_file = new File(RedStarProhibit.getInstance().getDataFolder(),"config.yml");
    public static int versioning = 0;
    public static ConfigManager configManager;
    private static Yaml yamlLoader;
    private static LinkedHashMap<String, Object> config;
    private static boolean isDebugMode;

    /*
        静态方块块
     */
    static {
        if (!getConfigFile().exists()) {
            try {
                getConfigFile().getParentFile().mkdirs();
                getConfigFile().createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 构造器
     */
    protected ConfigManager(){
        configManager = this;
    }

    /*
      get方法区
     */
    public Map<String, Object> getConfig() {
        return config;
    }
    public static ConfigManager getConfigManager() {
        return configManager;
    }
    public static File getConfigFile(){
        return config_file;
    }
}
