package top.redstarmc.redstarprohibit.common.datebase.operates;

import cc.carm.lib.easysql.api.SQLManager;
import top.redstarmc.redstarprohibit.common.datebase.Result;
import top.redstarmc.redstarprohibit.common.manager.H2Manager;
import top.redstarmc.redstarprohibit.common.manager.ServerManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <h1>执行数据库  查询  相关操作</h1>
 * 该类操作全为静态方法，可直接调用，调用时需使用ServerManager类辅助
 */
public class QueryOperates {

    private static final ServerManager serverManager = ServerManager.getManager();

    public static Result Bans(SQLManager sqlManager, String uuid) {
        AtomicReference<Result> result = new AtomicReference<>();
        sqlManager.createQuery()
                .inTable(H2Manager.tablePrefix+"_BANS")
                .selectColumns("uuid", "operator", "until", "issuedAt", "reason", "isForever")
                .addCondition("uuid", uuid)
                .build().execute(
                        (query) -> {
                            ResultSet result_set = query.getResultSet();
                            try {
                                if (!result_set.next()) {
                                    result_set.close();
                                    return 0;
                                }
                                String reason = result_set.getString("reason");
                                String operator = result_set.getString("operator");
                                int isForeverInt = result_set.getInt("isForever");
                                boolean isForever = isForeverInt == 1;
                                Timestamp until = result_set.getTimestamp("until");
                                Timestamp issuedAt = result_set.getTimestamp("issuedAt");

                                Timestamp now = new Timestamp(System.currentTimeMillis());

                                if (until != null && until.compareTo(now) < 0) {
                                    // TODO 移动到历史记录里
                                    DeleteOperates.Bans(sqlManager, uuid);
                                    return 0;
                                }

                                result.set(new Result(uuid, operator, until, issuedAt, reason, isForever));

                                return 1;
                            } finally {
                                try {
                                    if (result_set != null) {
                                        result_set.close();
                                    }
                                } catch (SQLException e) {
                                    // 处理关闭结果集时的异常
                                    serverManager.warn("[数据库]  查询异常",e.getMessage());
                                    serverManager.debug(e);
                                }
                            }
                        },
                        ((exception, sqlAction) -> {
                            serverManager.warn("[数据库]  查询异常",exception.getMessage());
                            serverManager.debug(exception);
                        })
                );
        return result.get();
    }
// TODO 数据库查询

//    public static ResultSet Bans(SQLManager sqlManager, String operator,boolean isOperator) {
//        AtomicReference<Result> result = new AtomicReference<>();
//        sqlManager.createQuery()
//                .inTable(H2Manager.tablePrefix+"_BANS")
//                .selectColumns("uuid")
//                .addCondition(uuid)
//                .build().execute(
//                        (query) -> {
//                            long time = query.getExecuteTime();
//                            ServerManager.getManager().debug("查询耗时,"+time);
//
//                            ResultSet result_set = query.getResultSet();
//                            if(!result_set.next()){
//                                return 0;
//                            }
//                            String reason = result_set.getString("reason");
//                            String operator = result_set.getString("operator");
//                            String type = result_set.getString("type");
//                            boolean isForever = result_set.getBoolean("isForever");
//                            Timestamp until = result_set.getTimestamp("until");
//                            Timestamp issuedAt = result_set.getTimestamp("issuedAt");
//
//                            Timestamp now = new Timestamp(System.currentTimeMillis());
//
//                            if(until.compareTo(now) < 0){
//                                return 0;
//                            }
//
//                            result.set(new Result(uuid, operator, until, issuedAt, reason, type, isForever));
//
//                            return 1;
//                        },
//                        ((exception, sqlAction) -> {
//
//                        })
//                );
//        return result.get();
//    }

    public static String UUIDs(SQLManager sqlManager, String name) {
        AtomicReference<String> uuid = new AtomicReference<>();
        sqlManager.createQuery()
                .inTable(H2Manager.tablePrefix+"_USER_UUID")
                .selectColumns("name", "uuid")
                .addCondition("name", name)
                .build().execute(
                        (query) -> {
                            ResultSet result_set = query.getResultSet();
                            try {
                                if (!result_set.next()) {
                                    result_set.close();
                                    uuid.set(null);
                                    return 0;
                                }

                                String uuid_ = result_set.getString("uuid");
                                uuid.set(uuid_);

                                return 1;
                            } finally {
                                try {
                                    if (result_set != null) {
                                        result_set.close();
                                    }
                                } catch (SQLException e) {
                                    // 处理关闭结果集时的异常
                                    serverManager.warn("[数据库] 查询异常", e.getMessage());
                                    serverManager.debug(e);
                                }
                            }
                        },
                        ((exception, sqlAction) -> {
                            serverManager.warn("[数据库] 查询异常", exception.getMessage());
                            serverManager.debug(exception);
                        })
                );
        return uuid.get();
    }
}
