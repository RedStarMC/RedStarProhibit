package top.redstarmc.redstarprohibit.common.datebase.operates;

import cc.carm.lib.easysql.api.SQLManager;
import top.redstarmc.redstarprohibit.common.manager.H2Manager;
import top.redstarmc.redstarprohibit.common.manager.ServerManager;

/**
 * <h1>执行数据库 删除 相关操作</h1>
 * 该类操作全为静态方法，可直接调用，调用时需使用ServerManager类辅助
 */
public class DeleteOperates {

    private static final ServerManager serverManager = ServerManager.getManager();

    public static void Bans(SQLManager sqlManager, String uuid) {
        sqlManager.createDelete(H2Manager.tablePrefix+"_BANS")
                .addCondition("uuid", uuid)
                .build()
                .execute(((exception, sqlAction) -> {
                    serverManager.warn("[数据库]  删除异常",exception.getMessage());
                    serverManager.debug(exception);
                }));
    }
}
