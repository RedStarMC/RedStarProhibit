package top.redstarmc.redstarprohibit.common.manager;

import cc.carm.lib.easysql.EasySQL;
import cc.carm.lib.easysql.api.SQLManager;
import cc.carm.lib.easysql.hikari.HikariConfig;
import cc.carm.lib.easysql.hikari.HikariDataSource;
import cc.carm.lib.easysql.manager.SQLManagerImpl;
import top.redstarmc.redstarprohibit.common.RedStarProhibit;

import java.sql.SQLException;

public abstract class H2Manager {

    private SQLManagerImpl sqlManager;

    public void init(){
        String mode = ConfigManager.getConfigManager().getString("DateBase.mode");
        if(mode.equals("Embedded")){
            ServerManager.getManager().info("[数据库]");
            initEmbedded();
        } else if (mode.equals("Server")) {
            ServerManager.getManager().info("[数据库]");
            initServer();
        }else {
            ServerManager.getManager().error("[数据库] 无法识别数据库模式！加载默认的嵌入式（Embedded）数据库");
            initEmbedded();
        }

        sqlManager.setDebugMode(ConfigManager.getConfigManager().isDebugMode());

        try {
            if (!sqlManager.getConnection().isValid(5)) {
                ServerManager.getManager().error("[数据库] 连接超时！");
            }


        } catch (SQLException e) {
            ServerManager.getManager().error("[数据库] 连接数据库失败！");
            ServerManager.getManager().debug("SQLException",e);
        }
    }

    private void initEmbedded(){
        String diver = ConfigManager.getConfigManager().getString("DateBase.Driver");
        String url = ConfigManager.getConfigManager().getString("DateBase.Url");
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(diver);
        config.setJdbcUrl(url);

        sqlManager = new SQLManagerImpl(new HikariDataSource(config), "test");

    }

    private void initServer(){
        String diver = ConfigManager.getConfigManager().getString("DateBase.Driver");
        String url = ConfigManager.getConfigManager().getString("DateBase.Url");
        String username = ConfigManager.getConfigManager().getString("DateBase.username");
        String password = ConfigManager.getConfigManager().getString("DateBase.password");

        sqlManager = EasySQL.createManager(diver,url,username,password);
    }

    public SQLManager getSqlManager() {
        return sqlManager;
    }
}
