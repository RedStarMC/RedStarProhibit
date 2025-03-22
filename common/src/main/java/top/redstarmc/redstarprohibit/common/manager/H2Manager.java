package top.redstarmc.redstarprohibit.common.manager;

import cc.carm.lib.easysql.api.SQLManager;

public abstract class H2Manager {
    private SQLManager sqlManager;
    public void init(){
        String diver = ConfigManager.getConfigManager().getString("DateBase.Driver");
        String url = ConfigManager.getConfigManager().getString("DateBase.Url");

    }

}
