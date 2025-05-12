package top.redstarmc.redstarprohibit.common;

import top.redstarmc.redstarprohibit.common.api.MapBuilder;

import java.util.Map;

public class Configs {
    public static final Map<String, Object> default_config = MapBuilder.of(String.class, Object.class)
            .set("Debug", false)
            .set("PluginPrefix", "§b§l[RedStarProhibit]")
            .set("DateBase.mode","Embedded")
            .set("DateBase.Driver","org.h2.Driver")
            .set("DateBase.Url","jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;MODE=MYSQL;")
            .set("DateBase.username","root")
            .set("DateBase.password","password")
            .set("Setting.confirm",true)
            .set("Setting.broadcast",true)
            .toMap();


    public static final Map<String, Object> default_language = MapBuilder.of(String.class, Object.class)
            .set("database.prefix", "[数据库]")
            .set("database.load", "")
            .set("", "")
            .toMap();


    public static final Map<String, Object> default_message = MapBuilder.of(String.class, Object.class)
            .set("a","a")
            .toMap();
}
