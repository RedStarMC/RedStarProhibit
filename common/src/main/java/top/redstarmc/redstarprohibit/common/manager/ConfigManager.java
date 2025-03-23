package top.redstarmc.redstarprohibit.common.manager;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import top.redstarmc.redstarprohibit.common.RedStarProhibit;
import top.redstarmc.redstarprohibit.common.utils.MapBuilder;
import top.redstarmc.redstarprohibit.common.utils.Stream;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class ConfigManager {

    public static final Map<String, Object> default_config = MapBuilder.of(String.class,Object.class)
            .set("Debug", false)
            .set("PluginPrefix", "§b§l[RedStarProhibit]")
            .set("DateBase.mode","Embedded")
            .set("DateBase.Driver","org.h2.Driver")
            .set("DateBase.Url","jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=MYSQL;")
            .set("DateBase.username","root")
            .set("DateBase.password","password")
            .toMap();

    private static final File config_file = new File(RedStarProhibit.getInstance().getDataFolder(),"config.yml");
    public static ConfigManager configManager;
    private static Yaml yamlLoader;
    public static String versioning = "0.0.0";
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
     * 初始化方法
     */
    public void init(){
        DumperOptions dumperoptions = new DumperOptions();
        dumperoptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yamlLoader = new Yaml(dumperoptions);

        try (InputStream is = Files.newInputStream(getConfigFile().toPath())) {
            config = yamlLoader.load(is);
            if (config == null) {
                config = new LinkedHashMap<>();
            }
            if (!getConfig().containsKey("Versioning") ||  get("Versioning") != versioning) {
                set("Versioning", versioning);
                setDefaultConfig();
                save();
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 构造器
     */
    protected ConfigManager(){
        configManager = this;
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("Version")) {
            if (inputStream != null) {
                versioning = new String(Stream.readInputStream(inputStream));
            } else {
                System.out.println("无法获取版本号...默认设定为0.0.0版本！");
                System.out.println("versioning = " + versioning);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }


    /*
      方法区
     */
    public void setDefaultConfig() {
        default_config.forEach(this::setDefaultPath);
        //
    }

    public void setDefaultPath(String key, Object value) {
        if (!config.containsKey(key)) {
            set(key, value);
        }
    }

    public void save() {
        try (Writer writer = Files.newBufferedWriter(getConfigFile().toPath(), StandardCharsets.UTF_8)) {
            yamlLoader.dump(config, writer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public Map<String, Object> getConfig() {
        return config;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public static File getConfigFile(){
        return config_file;
    }

    @SuppressWarnings("unchecked")
    public void set(String path, Object value) {
        LinkedHashMap<String, Object> tmap = config;
        while (path.contains(".")) {
            String tpath = path.substring(0, path.indexOf("."));
            path = path.substring(tpath.length() + 1);

            tmap.putIfAbsent(tpath, new LinkedHashMap<>());
            tmap = (LinkedHashMap<String, Object>) tmap.get(tpath);
        }
        tmap.put(path, value);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String path) {
        LinkedHashMap<String, Object> tmap = config;
        while (path.contains(".")) {
            String tpath = path.substring(0, path.indexOf("."));
            path = path.substring(tpath.length() + 1);
            if (!tmap.containsKey(tpath)) {
                return null;
            }
            tmap = (LinkedHashMap<String, Object>) tmap.get(tpath);
        }
        return (T) tmap.get(path);
    }

    public long getLong(String key) {
        Object got = get(key);
        if (got == null)
            return 0;
        return (got instanceof Number) ? ((Number) got).longValue() : (long) got;
    }

    public int getInt(String key) {
        Object got = get(key);
        if (got == null)
            return 0;
        if (got instanceof Number) {
            return ((Number) got).intValue();
        }
        try {
            return Integer.parseInt(String.valueOf(got));
        } catch (NumberFormatException ignored1) {
        }
        return 0;
    }

    public boolean getBoolean(String key) {
        Object got = get(key);
        return (got instanceof Boolean) ? (Boolean) got : "true".equalsIgnoreCase(String.valueOf(got));
    }

    public String getString(String key) {
        return String.valueOf((Object) get(key));
        //
    }

    public boolean isDebugMode() {
        return isDebugMode;
        //
    }

    public static void setDebugMode(boolean isDebugMode) {
        ConfigManager.isDebugMode = isDebugMode;
        //
    }

    public List<String> getStringList(String key) {
        if (get(key) instanceof List) {
            return get(key);
        }
        return null;
    }
}
