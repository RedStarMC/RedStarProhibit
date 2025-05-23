package top.redstarmc.redstarprohibit.common.manager;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import top.redstarmc.redstarprohibit.common.Configs;
import top.redstarmc.redstarprohibit.common.RedStarProhibit;
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
import java.util.Objects;

/**
 * <h1>本抽象类负责创建，检查，操作所有的配置文件</h1>
 * 继承本类并适当重写方法
 */
public abstract class ConfigManager {

    /**
     * 默认的配置文件，从 {@link Configs} 中获取
     */
    public static final Map<String, Object> default_config = Configs.default_config;

    /**
     * 默认的语言配置文件，从 {@link Configs} 中获取
     */
    public static final Map<String,Object> default_language = Configs.default_language;


    /**
     * {@link File} 格式的 配置文件 ，用于创建文件实体
     */
    private static final File config_file = new File(RedStarProhibit.getInstance().getDataFolder(),"config.yml");

    /**
     * {@link File} 格式的 语言配置文件 ，用于创建文件实体
     */
    private static final File language_file = new File(RedStarProhibit.getInstance().getDataFolder(),"language.yml");


    /**
     * 用于YML文件操作的 配置文件
     */
    private static LinkedHashMap<String, Object> config;

    /**
     * 用于YML文件操作的 语言配置文件
     */
    private static LinkedHashMap<String, Object> language;

    /**
     * {@link ConfigManager} 实例
     */
    public static ConfigManager configManager;


    /**
     * {@link Yaml} 配置文件实例
     */
    private static Yaml yamlConfigLoader;

    /**
     * {@link Yaml} 语言配置文件实例
     */
    private static Yaml yamlLanguageLoader;


    /**
     * 插件的版本号
     */
    public static String versioning = "0.0.0";
    /**
     * 是否为Debug模式
     */
    private static boolean isDebugMode;
    /**
     * 是否需要 确认 命令
     */
    private static boolean isConfirm;
    /**
     * 是否需要广播封禁消息
     */
    private static boolean isBroadcast;


    /*
        静态块
     */
    static {
        //创建Config
        if (!getConfigFile().exists()) {
            try {
                getConfigFile().getParentFile().mkdirs();
                getConfigFile().createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        //创建Language
        if(!getLanguageFile().exists()) {
            try {
                getLanguageFile().getParentFile().mkdir();
                getLanguageFile().createNewFile();
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * <h2>本抽象类的初始化方法，也是入口</h2>
     * 读取文件，创建实例化配置文件
     */
    public void init(){
        DumperOptions dumperoptions = new DumperOptions();
        dumperoptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        //Config 初始化过程
        yamlConfigLoader = new Yaml(dumperoptions);

        try (InputStream is = Files.newInputStream(getConfigFile().toPath())) {
            config = yamlConfigLoader.load(is);
            if (config == null) {
                config = new LinkedHashMap<>();
            }
            if (!getConfig().containsKey("Versioning") || !Objects.equals(getString("Versioning", config), versioning)) {
                set("Versioning", versioning, config);
                setDefaultConfig();
                saveConfig();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        setDebugMode(getBoolean("Debug", config));
        setIsConfirm(getBoolean("Setting.confirm", config));
        setIsBroadcast(getBoolean("Setting.broadcast", config));


        //Language 初始化过程
        yamlLanguageLoader = new Yaml(dumperoptions);

        try (InputStream is = Files.newInputStream(getLanguageFile().toPath())) {
            language = yamlLanguageLoader.load(is);
            if (language == null) {
                language = new LinkedHashMap<>();
            }
            if (!getLanguage().containsKey("Versioning") || !Objects.equals(getString("Versioning", language), versioning)) {
                set("Versioning", versioning, language);
                setDefaultLanguage();
                saveLanguage();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    /**
     * <h2>{@link ConfigManager} 的构造器</h2>
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

    /**
     * 获得本抽象类实例
     * @return {@link ConfigManager}
     */
    public static ConfigManager getConfigManager() {
        return configManager;
    }



    private void setDefaultConfig() {
        default_config.forEach(this::setDefaultConfigPath);
        //
    }

    private void setDefaultConfigPath(String key, Object value) {
        if (!config.containsKey(key)) {
            set(key, value, config);
        }
    }

    private void saveConfig() {
        try (Writer writer = Files.newBufferedWriter(getConfigFile().toPath(), StandardCharsets.UTF_8)) {
            yamlConfigLoader.dump(config, writer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    private void setDefaultLanguage() {
        default_language.forEach(this::setDefaultLanguagePath);
        //
    }

    private void setDefaultLanguagePath(String key, Object value) {
        if (!language.containsKey(key)) {
            set(key, value, language);
        }
    }

    private void saveLanguage() {
        try (Writer writer = Files.newBufferedWriter(getLanguageFile().toPath(), StandardCharsets.UTF_8)) {
            yamlLanguageLoader.dump(language, writer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private Map<String, Object> getConfig() {
        return config;
    }
    private Map<String, Object> getLanguage(){
        return language;
    }


    private static File getConfigFile(){
        return config_file;
    }
    private static File getLanguageFile(){
        return language_file;
    }

    /**
     * 获得配置文件Map实例
     * @return {@link LinkedHashMap} 实例
     */
    public LinkedHashMap<String,Object> getConfigMap(){
        return config;
    }
    /**
     * 获得语言配置文件Map实例
     * @return {@link LinkedHashMap} 实例
     */
    public LinkedHashMap<String,Object> getLanguageMap(){
        return config;
    }


    /**
     * 设置对应配置文件的内容
     * @param path yml路径
     * @param value 值
     * @param map 配置文件集合
     */
    @SuppressWarnings("unchecked")
    public void set(String path, Object value, LinkedHashMap<String,Object> map) {
        LinkedHashMap<String, Object> tmap = map;
        while (path.contains(".")) {
            String tpath = path.substring(0, path.indexOf("."));
            path = path.substring(tpath.length() + 1);

            tmap.putIfAbsent(tpath, new LinkedHashMap<>());
            tmap = (LinkedHashMap<String, Object>) tmap.get(tpath);
        }
        tmap.put(path, value);
    }

    /**
     * 获得对应配置文件的内容
     * @param path yml路径
     * @param map 配置文件集合
     * @return 获得的内容
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String path, LinkedHashMap<String,Object> map) {
        LinkedHashMap<String, Object> tmap = map;
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

    /**
     * 通过 {@link #get(String, LinkedHashMap)} 方法获得 long 类型的内容
     * @param key yml路径
     * @param map 配置文件集合
     * @return long 类型的内容
     */
    public long getLong(String key, LinkedHashMap<String,Object> map) {
        Object got = get(key,map);
        if (got == null)
            return 0;
        return (got instanceof Number) ? ((Number) got).longValue() : (long) got;
    }

    /**
     * 通过 {@link #get(String, LinkedHashMap)} 方法获得 int 类型的内容
     * @param key yml路径
     * @param map 配置文件集合
     * @return int 类型的内容
     */
    public int getInt(String key, LinkedHashMap<String,Object> map) {
        Object got = get(key,map);
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

    /**
     * 通过 {@link #get(String, LinkedHashMap)} 方法获得 boolean 类型的内容
     * @param key yml路径
     * @param map 配置文件集合
     * @return boolean 类型的内容
     */
    public boolean getBoolean(String key, LinkedHashMap<String,Object> map) {
        Object got = get(key,map);
        return (got instanceof Boolean) ? (Boolean) got : "true".equalsIgnoreCase(String.valueOf(got));
    }

    /**
     * 通过 {@link #get(String, LinkedHashMap)} 方法获得 {@link String} 类型的内容
     * @param key yml路径
     * @param map 配置文件集合
     * @return {@link String} 类型的内容
     */
    public String getString(String key, LinkedHashMap<String,Object> map) {
        return String.valueOf((Object) get(key,map));
        //
    }

    /**
     * 通过 {@link #get(String, LinkedHashMap)} 方法获得 {@link List} 类型的内容
     * @param key yml路径
     * @param map 配置文件集合
     * @return {@link List} 类型的内容
     */
    public List<String> getStringList(String key, LinkedHashMap<String,Object> map) {
        if (get(key,map) instanceof List) {
            return get(key,map);
        }
        return null;
    }

    /**
     * 是否为Debug模式
     * @return boolean，代表是否启用Debug模式
     */
    public boolean isDebugMode() {
        return isDebugMode;
        //
    }

    private static void setDebugMode(boolean isDebugMode) {
        ConfigManager.isDebugMode = isDebugMode;
        //
    }

    public static boolean isIsConfirm() {
        return isConfirm;
        //
    }

    public static void setIsConfirm(boolean isConfirm) {
        ConfigManager.isConfirm = isConfirm;
        //
    }

    public static boolean isIsBroadcast() {
        return isBroadcast;
        //
    }

    public static void setIsBroadcast(boolean isBroadcast) {
        ConfigManager.isBroadcast = isBroadcast;
        //
    }
}
