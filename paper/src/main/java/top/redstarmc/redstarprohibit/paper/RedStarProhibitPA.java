package top.redstarmc.redstarprohibit.paper;

import org.bukkit.plugin.java.JavaPlugin;
import top.redstarmc.redstarprohibit.common.RedStarProhibit;
import top.redstarmc.redstarprohibit.paper.manager.PAConfigManager;
import top.redstarmc.redstarprohibit.paper.manager.PAH2Manager;
import top.redstarmc.redstarprohibit.paper.manager.PAServerManager;

public class RedStarProhibitPA extends JavaPlugin implements RedStarProhibit{

    @Override
    public void onDisable() {
        RedStarProhibit.super.onClose();
    }

    @Override
    public void onEnable() {
        RedStarProhibit.super.onStart();
    }

    @Override
    public void loadManagers() {
        new PAConfigManager().init();
        new PAServerManager();
        new PAH2Manager().init();

    }

    public static RedStarProhibitPA getInstance() {
        return RedStarProhibit.getInstance();
    }

}
