package top.redstarmc.redstarprohibit.common.manager;

import cc.carm.lib.easysql.api.SQLManager;

public abstract class H2Manager {
    private SQLManager sqlManager;
    public void init(){
        System.out.println("数据库初始化");
    }

}
