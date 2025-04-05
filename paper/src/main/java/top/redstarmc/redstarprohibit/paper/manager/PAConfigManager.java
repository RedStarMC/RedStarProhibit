package top.redstarmc.redstarprohibit.paper.manager;

import org.bukkit.configuration.file.YamlConfiguration;
import top.redstarmc.redstarprohibit.common.manager.ConfigManager;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PAConfigManager extends ConfigManager {
    private static YamlConfiguration config;

    private static void tryTransformOldConfig() {
        YamlConfiguration oldConfig = YamlConfiguration.loadConfiguration(getConfigFile());
        if (!oldConfig.isInt("Version")) {
            return;
        }
        getConfigFile().delete();
        try {
            getConfigFile().createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> getConfig() {
        return config.getValues(true);
        //
    }

    @Override
    public void init() {
        if (getConfigFile().exists()) {
            tryTransformOldConfig();
        }
        config = YamlConfiguration.loadConfiguration(getConfigFile());
        if (!Objects.equals(config.getString("Versioning"), versioning)) {
            setDefaultConfig();
            config.set("Versioning", versioning);
            save();
        }
        setDebugMode(getBoolean("Debug"));
    }

    @Override
    public void setDefaultConfig() {
        try {
            ConfigManager.default_config.forEach(this::setDefaultPath);
        } catch (Exception ex) {
            config = new YamlConfiguration();
            setDefaultConfig();
        }
    }

    @Override
    public void setDefaultPath(String path, Object value) {
        if (!config.contains(path)) {
            config.set(path, value);
        }
    }

    @Override
    public void save() {
        try {
            config.save(getConfigFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void set(String key, Object value) {
        config.set(key, value);
        //
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(String key) {
        return (T) config.get(key);
        //
    }

    @Override
    public long getLong(String key) {
        return config.getLong(key);
        //
    }

    @Override
    public int getInt(String key) {
        return config.getInt(key);
        //
    }

    @Override
    public boolean getBoolean(String key) {
        return config.getBoolean(key);
        //
    }

    @Override
    public String getString(String key) {
        return config.getString(key);
        //
    }

    @Override
    public List<String> getStringList(String key) {
        return config.getStringList(key);
        //
    }
}
