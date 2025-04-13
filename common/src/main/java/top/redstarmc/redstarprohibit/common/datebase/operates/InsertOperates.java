package top.redstarmc.redstarprohibit.common.datebase.operates;

import cc.carm.lib.easysql.api.SQLManager;
import top.redstarmc.redstarprohibit.common.manager.H2Manager;

import java.sql.Timestamp;

/**
 * <h1>执行数据库 插入 相关操作</h1>
 * 该类操作全为静态方法，可直接调用，调用时需使用ServerManager类辅助
 */
public class InsertOperates {
    public static void Bans(SQLManager sqlManager, String uuid, String operator, Timestamp until,
                                  Timestamp issuedAt, String reason, boolean isForever) {
        sqlManager.createInsert(H2Manager.tablePrefix+"_BANS")
                .setColumnNames("uuid", "operator", "until","issuedAt","reason","isForever")
                .setParams(uuid, operator, until, issuedAt, reason, isForever)
                .executeAsync();
    }

    public static void BanHistory(SQLManager sqlManager, String uuid, String operator, Timestamp until,
                                        Timestamp issuedAt, String reason, String type , boolean isForever) {
        sqlManager.createInsert(H2Manager.tablePrefix+"_BAN_HISTORY")
                .setColumnNames("uuid", "operator", "until","issuedAt","reason","type","isForever")
                .setParams(uuid, operator, until, issuedAt, reason, type, isForever)
                .executeAsync();
    }


}
