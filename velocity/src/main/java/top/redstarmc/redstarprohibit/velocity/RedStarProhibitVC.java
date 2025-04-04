package top.redstarmc.redstarprohibit.velocity;


import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;
import top.redstarmc.redstarprohibit.common.RedStarProhibit;
import top.redstarmc.redstarprohibit.common.manager.ServerManager;
import top.redstarmc.redstarprohibit.velocity.manager.VCConfigManager;
import top.redstarmc.redstarprohibit.velocity.manager.VCH2Manager;
import top.redstarmc.redstarprohibit.velocity.manager.VCServerManager;

import java.io.File;
import java.nio.file.Path;

@Plugin(
        id = "red_star_prohibit",
        name = "RedStarProhibit",
        authors = {"RedStarMC","pingguomc"},
        version = "${version}",
        url = "https://github.com/RedStarMC/RedStarProhibit"
)
public class RedStarProhibitVC implements RedStarProhibit {
    private final Logger logger;
    private final ProxyServer server;
    private final File data_folder;

    @Inject
    public RedStarProhibitVC(Logger logger, ProxyServer server,@DataDirectory Path DataDirectory){
        this.logger = logger;
        this.server = server;
        this.data_folder = DataDirectory.toFile();
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event){
        RedStarProhibit.super.onEnable();
        logger.info("加载成功");
        ServerManager.getManager().info("测试ServerManager发送info");
        ServerManager.getManager().warn("测试ServerManager发送warn");
        ServerManager.getManager().error("测试ServerManager发送error");
    }

    @Subscribe
    public void onProxyShutdown(ProxyShutdownEvent event){
        RedStarProhibit.super.onDisable();
        logger.info("卸载成功");
    }

    @Override
    public void loadManagers() {
        new VCConfigManager().init();
        new VCServerManager();
        new VCH2Manager().init();
    }

    @Override
    public void loadCommands() {

    }

    public static RedStarProhibitVC getInstance(){
        return RedStarProhibit.getInstance();
    }

    @Override
    public File getDataFolder() {
        return data_folder;
    }

    public ProxyServer getServer() {
        return server;
    }
}
