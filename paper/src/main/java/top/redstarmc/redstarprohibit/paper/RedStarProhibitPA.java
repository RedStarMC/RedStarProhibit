package top.redstarmc.redstarprohibit.paper;

import org.bukkit.plugin.java.JavaPlugin;
import top.redstarmc.redstarprohibit.common.RedStarProhibit;

public class RedStarProhibitPA extends JavaPlugin implements RedStarProhibit{

    @Override
    public void onDisable() {
        RedStarProhibit.super.onDisable();
    }

    @Override
    public void onEnable() {
        RedStarProhibit.super.onEnable();
    }

    @Override
    public void loadManagers() {

    }

    @Override
    public void loadCommands() {

    }

    public static RedStarProhibitPA getInstance() {
        return RedStarProhibit.getInstance();
    }
}
